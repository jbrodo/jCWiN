import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		
		String alfabeto[]={"A","B","C","D","E","F","G","H","I","L","M","N","O","P","Q"};
		
		ArrayList<String> listaLinkRicette = new ArrayList<String>();
		
		String url;
		String html;
		String aSlash = "<a\\b[^>]*href=\"[^>]*>(.*?)</a>";
		Pattern h4 = Pattern.compile("<h4\\b[^>]*class=\"[^>]*>(.*?)"+aSlash+"</h4>");
		Pattern href = Pattern.compile("href=\"[^>]*\"\\s+");
		Pattern title = Pattern.compile("title=\"[^>]*\"");
		int i=0;
		String sito = "http://www.giallozafferano.it";
		for(String l:alfabeto){
			url=sito+"/Ricette-A-Z/lettera-"+l;
			html = getHTML(url);
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
					for(String h:s){
						System.out.println(h);
					}
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
			html=getHTML(url);
			System.out.println(i+"/"+numeroRicette+") "+html);
			i++;
		}
	}

	public static String getHTML(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

