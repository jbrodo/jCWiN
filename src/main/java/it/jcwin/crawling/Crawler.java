package it.jcwin.crawling;

import java.util.ArrayList;

public class Crawler {
	private CrawlingAlgorithm s;
	
	public Crawler(CrawlingAlgorithm s){
		this.s=s;
	}
	public ArrayList<String> findReceipt(){
		return s.find();
	}
}
