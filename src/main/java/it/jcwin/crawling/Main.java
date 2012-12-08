package it.jcwin.crawling;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static String home = "http://www.giallozafferano.it";
	public static PrintWriter outputStream;

	public static void main(String [] args){
		String htmlp;
		//Pattern script = Pattern.compile("<script\\b[^>]*type=\"text/javascript\"[^>]*>(.*?)</script>");
		//<script: <script type="text/javascript"><!--var IDRicetta = 412; var numComTot = 6; var contCom = 0;--></script>
		//Pattern javaScript = Pattern.compile("<!*(.*?)var\\sIDRicetta*[^>]*(.*?);");

		htmlp=ScannerHTML.getHTML("http://www.giallozafferano.it/ricetta/Alici-marinate");
		System.out.println(htmlp);

		Crawler src= new Crawler(new BFS(home,151));
		
		HashMap<String,String> ricette = src.findReceipt();
		
		int i=0;
		for(String s :ricette.keySet()){
			System.out.println((i++)+") "+ricette.get(s));
			apriScritturaFile("ricette/"+String.valueOf(i)+".html");
			write(ricette.get(s));
			chiudiScritturaFile();
		}
		System.out.println(ricette.size());
	}

	public static void apriScritturaFile(final String output) {
		try {
			outputStream =new PrintWriter(new FileOutputStream(output));
			System.out.println("Ho aperto il file in scrittura");
		} catch (final FileNotFoundException e) {
			System.err.println("Errore: " + e.getMessage());
		}
	}

	public static void apriScritturaAppendFile(final String output) {
		try {
			outputStream =new PrintWriter(new FileOutputStream(output, true));
			System.out.println("Ho aperto il file in scrittura");
		} catch (final FileNotFoundException e) {
			System.err.println("Errore: " + e.getMessage());
		}
	}
	
	public static void chiudiScritturaFile() {
		outputStream.close();
	}

	public static void scrivi(final String s) {
		outputStream.println(s);
	}

	public static void write(final String s) {
		outputStream.write(s);
	}
	
	public static void main2(String[] args) {

		String alfabeto[]={"A","B","C","D","E","F","G","H","I","L","M","N","O","P","Q"};

		ArrayList<String> listaLinkRicette = new ArrayList<String>();
		String url;
		String html;
		//String aSlash = "<a\\b[^>]*href=\"[^>]*>(.*?)</a>";
		//Pattern h4 = Pattern.compile("<h4\\b[^>]*class=\"[^>]*>(.*?)"+aSlash+"</h4>");
		Pattern h4 = Pattern.compile("<h4\\b[^>]*class=\"[^>]*>(.*?)</h4>");
		Pattern href = Pattern.compile("href=\"[^>]*\"\\s+");
		Pattern title = Pattern.compile("title=\"[^>]*\"");
		int i=0;
		String sito = "http://www.giallozafferano.it";
		for(String l:alfabeto){
			url=sito+"/Ricette-A-Z/lettera-"+l;
			html = ScannerHTML.getHTML(url);
			System.out.println(url);
			System.out.println(html);
			Matcher mh4 = h4.matcher(html);
			while(mh4.find()){
				System.out.println(i+") mh4: "+mh4.group());
				Matcher mhref = href.matcher(mh4.group());
				while(mhref.find()){
					//ci sono degli spazzi finali
					System.out.println(i+") href: "+mhref.group());
					String s[]=mhref.group().split("\"");
					System.out.println(s[1]);
					listaLinkRicette.add(s[1]);
				}
				Matcher mtitle = title.matcher(mh4.group());
				while(mtitle.find()){
					System.out.println(i+") title: "+mtitle.group());
				}
				i++;
			}
		}
		int numeroRicette=listaLinkRicette.size();
		i=1;
		for(String l:listaLinkRicette){
			url=sito+l;
			System.out.println(i+"/"+numeroRicette+") "+url);
			html=ScannerHTML.getHTML(url);
			System.out.println(i+"/"+numeroRicette+") "+html);
			i++;
		}
	}
}