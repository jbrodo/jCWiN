package it.jcwin.crawling;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScannerHTML {
	
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
			//e.printStackTrace();
			System.out.println("non ho scaricato per motivi vari la pagina HTML");
		}
		return result;
	}
	
	public static boolean isRobotsEnabled(String html){
		Pattern robots = Pattern.compile("<meta\\b[^>]*name=\"Robots\"\\scontent=\"ALL\"[^>]*>(.*?)/>");
		Matcher mRobots = robots.matcher(html);
		while(mRobots.find()){
			//System.out.println(mRobots.group());
			return true;
		}
		return false;
	}

	public static ArrayList<String> getAllLinks(String link,String html, String home){
		Pattern divID = Pattern.compile("<div\\b[^>]*id=\"[^>]*>(.*?)</div>");
		Pattern divClass = Pattern.compile("<div\\b[^>]*class=\"[^>]*>(.*?)</div>");
		Pattern h4 = Pattern.compile("<h4\\b[^>]*class=\"[^>]*>(.*?)</h4>");
		Pattern href = Pattern.compile("href=\"[^>]*\\s");

		int i =0;
		ArrayList<String> links = new ArrayList<String>();
		Matcher mh4 = h4.matcher(html);
		while(mh4.find()){
			//System.out.println(i+") mh4: "+mh4.group());
			Matcher mhref = href.matcher(mh4.group());
			while(mhref.find()){
				String s[]=mhref.group().split("\"");
				if(!s[1].contains("javascript:")){
					System.out.println(i+")mh4 mhref: "+s[1]);
					if(!links.contains(s[1])){
						if(!isWellFormedLink(s[1])){
							links.add(home+s[1]);
						}
						else{
							links.add(s[1]);
						}
					}
					i++;
				}
			}
		}

		Matcher mdivId = divID.matcher(html);
		while(mdivId.find()){
			//System.out.println(i+") mdivId: "+mdivId.group());
			Matcher mhref = href.matcher(mdivId.group());
			while(mhref.find()){
				String s[]=mhref.group().split("\"");
				if(!s[1].contains("javascript:")){
					System.out.println(i+")mdivId mhref: "+s[1]);
					if(!links.contains(s[1])){
						if(!isWellFormedLink(s[1])){
							links.add(home+s[1]);
						}
						else{
							links.add(s[1]);
						}
					}
					i++;
				}
			}
		}

		Matcher mdivClass = divClass.matcher(html);
		while(mdivClass.find()){
			//System.out.println(i+") mdivClass: "+mdivId.group());
			Matcher mhref = href.matcher(mdivClass.group());
			while(mhref.find()){
				String s[]=mhref.group().split("\"");
				if(!s[1].contains("javascript:")){
					System.out.println(i+")mdivClass mhref: "+s[1]);
					if(!links.contains(s[1])){
						if(!isWellFormedLink(s[1])){
							links.add(home+s[1]);
						}
						else{
							links.add(s[1]);
						}
					}
					i++;
				}
			}
		}
		return links;
	}

	public static boolean isWellFormedLink(String s){
		if(!s.contains("http://")&&!s.contains("Http://")&&!s.contains("https://")&&!s.contains("Https://")){
			return false;
		}
		else{
			return true;
		}
	}

	public static boolean isRicetta(String html){
		Pattern title = Pattern.compile("<title>Ricetta\\s*[^>]*(.*?)</title>");
		Matcher mTitle = title.matcher(html);

		while(mTitle.find()){
			//System.out.println(true+" - "+html+" <title>Ricetta: "+mTitle.group());
			return true;
		}
		return false;
	}
	
	public static boolean isAllBlack(HashMap<String,String> h){
		for(String s:h.keySet()){
			if(!s.equals("b"))return false;
		}
		return true;
	}
}
