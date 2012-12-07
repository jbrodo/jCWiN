package it.jcwin;

import it.jcwin.crawling.BFS;
import it.jcwin.crawling.Crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App 
{
	public static void main(String[] args) {
		String home = "http://www.giallozafferano.it";
		Integer maxNumber = 151;
		
		Crawler src= new Crawler(new BFS(home, maxNumber));
		Parser parser = new Parser();
		SearchEngine searchEngine = new SearchEngine();
		
		//gathering part
		ArrayList<String> ricette = src.findReceipt();
		
		//parsing part
		try
		{
			parser.init();
			/*String fileName = "data/Agnolotti.html";
			System.out.println("FILE: "+fileName);
			File test = new File(fileName);
			Document doc = Jsoup.parse(test, "UTF-8");
			parser.parseDocument(doc);
			
			fileName = "data/Agnello-gratinato-con-carciofi-e-piselli.html";
			System.out.println("\nFILE: "+fileName);
			test = new File(fileName);
			doc = Jsoup.parse(test, "UTF-8");
			parser.parseDocument(doc);*/
			
			for(String ricetta : ricette){
				Document doc = Jsoup.parse(ricetta);
				parser.parseDocument(doc);
			}
			
			parser.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//search engine part
		String query = "Parmigiano Reggiano = 50gr";
		searchEngine.getResults(query);
	}
}