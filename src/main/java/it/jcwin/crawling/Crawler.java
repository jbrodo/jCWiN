package it.jcwin.crawling;

import java.util.ArrayList;
import java.util.HashMap;

public class Crawler {
	private CrawlingAlgorithm s;
	
	public Crawler(CrawlingAlgorithm s){
		this.s=s;
	}
	public HashMap<String,String> findReceipt(){
		return s.find();
	}
}
