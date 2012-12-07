package it.jcwin.crawling;

import java.util.ArrayList;

public class Searcher {
	private CrawlingAlgorithm s;
	public Searcher(CrawlingAlgorithm s){
		this.s=s;
	}
	public ArrayList<String> findReceipt(){
		return s.find();
	}
}
