package it.jcwin;

import it.jcwin.crawling.BFS;
import it.jcwin.crawling.Crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App 
{
	public static void main(String[] args) {
		String home = "http://www.giallozafferano.it";
		
		try
		{
			System.out.println("Enter a command (type help to see the documentation and quit to exit): ");
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			String input = "";
			String[] commands;
			while (!(input.equals("quit"))){
				System.out.print("> ");
				input = in.readLine().trim();
				//System.out.println("You typed: " + input);
				commands = input.split(" ");
				//System.out.println("You typed commands: " + commands[0]);
				
				if (commands[0].equals("index")){
					Integer maxNumber = Integer.parseInt(commands[1]);
					Crawler src= new Crawler(new BFS(home, maxNumber));
					Parser parser = new Parser();
					
					//gathering part
					ArrayList<String> ricette = src.findReceipt();
					
					//parsing part
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
				}
				
				if (commands[0].equals("search")){
					SearchEngine searchEngine = new SearchEngine();
					
					//search engine part
					String query = "Parmigiano Reggiano = 50gr";
					searchEngine.getResults(query);
				}
				
				if (commands[0].equals("help")){
					System.out.println("type ");
					System.out.println("- index N where N is the max number of receipts to download from www.giallozafferano.com");
					System.out.println("- search Q where Q is the query to the index of www.giallozafferano.com");
					System.out.println("- help to see this");
					System.out.println("- quit to exit");
				}
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}