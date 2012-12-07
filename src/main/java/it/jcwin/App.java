package it.jcwin;

import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App 
{
	public static void main(String[] args) {
		
		Parser parser = new Parser();
		SearchEngine searchEngine = new SearchEngine();
		
		//parsing part
		try
		{
			parser.init();
			String fileName = "data/Agnolotti.html";
			System.out.println("FILE: "+fileName);
			File test = new File(fileName);
			Document doc = Jsoup.parse(test, "UTF-8");
			parser.addDocument(doc);
			
			fileName = "data/Agnello-gratinato-con-carciofi-e-piselli.html";
			System.out.println("\nFILE: "+fileName);
			test = new File(fileName);
			doc = Jsoup.parse(test, "UTF-8");
			parser.addDocument(doc);
			
			parser.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//search engine part
		String query = "Parmigiano Reggiano = 50gr";
		searchEngine.getResults(query);
	}
}