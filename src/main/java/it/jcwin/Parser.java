package it.jcwin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	private IndexWriter index;
	
	private HashMap<String, String> getIngredientsAndAmount(Document doc) {
		HashMap<String, String> ingredientsAndAmount = new HashMap<String, String>();
		Elements liIngredients = doc.getElementsByClass("ingredient");
		
		for(Element liIngredient : liIngredients) {
			Element aIngredient = liIngredient.getElementsByClass("ingredient").get(0);
			Element ingredient = aIngredient.getElementsByTag("a").get(0);
			Element amount = liIngredient.getElementsByClass("amount").get(0);
			ingredientsAndAmount.put(ingredient.html(), amount.html());
		}
		
		return ingredientsAndAmount;
	}
	
	private String getPrepTime(Document doc) {
		Element prepTime = doc.getElementsByClass("preptime").get(0);
		return prepTime.ownText();
	}
	
	private String getCookTime(Document doc) {
		Element cooktime = doc.getElementsByClass("cooktime").get(0);
		return cooktime.ownText();
	}
	
	private String getDifficultyOfPreparation(Document doc) {
		Element difficultyOfPreparation = doc.getElementsByClass("difficolta").get(0);
		return difficultyOfPreparation.ownText();
	}
	
	private String getDoses(Document doc) {
		Elements spanDoses = doc.getElementsByClass("dosixpers");
		
		if(spanDoses.size() == 0) {
			return null;
		}
		
		Elements doses = spanDoses.get(0).getElementsByClass("yield");
		
		if(doses.size() == 0) {
			return null;
		}
		
		return doses.get(0).ownText();
	}
	
	private String getCost(Document doc) {
		Elements costs = doc.getElementsByClass("costo");
		
		if(costs.size() == 0) {
			return null;
		}
		
		Element cost = costs.get(0);
		return cost.ownText();
	}
	
	private List<String> getCategories(Document doc) {
		List<String> categories = new ArrayList<String>();
		Elements tags = doc.getElementsByClass("tags");
		
		if(tags.size() == 0) {
			return null;
		}
		
		Elements tag = tags.get(0).getElementsByClass("tag");
		
		for(Element category : tag) {
			categories.add(category.html());
		}
		
		return categories;
	}
	
	public void parseDocument(Document doc) throws IOException {
		//prendo gli Ingredienti e le quantita
		HashMap<String, String> ingredientsAndAmount = getIngredientsAndAmount(doc); 
		
		//prendo il tempo di preparazione
		String prepTime = getPrepTime(doc);
		
		//prendo il tempo di cottura
		String cookTime = getCookTime(doc);
		
		//prendo la difficoltà di preparazione
		String difficultyOfPreparation = getDifficultyOfPreparation(doc);
		
		//prendo le dosi
		String doses = getDoses(doc);
		
		//prendo il costo
		String cost = getCost(doc);
		
		//prendo le categorie
		List<String> categories = getCategories(doc);
		
		System.out.println("ingredientsAndAmount: "+ingredientsAndAmount);
		System.out.println("prepTime: "+prepTime);
		System.out.println("cooktime: "+cookTime);
		System.out.println("difficultyOfPreparation: "+difficultyOfPreparation);
		System.out.println("doses: "+doses);
		System.out.println("cost: "+cost);
		System.out.println("categories: "+categories);
		
		//Add document to index
		org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
		
		Field fprepTime = new StringField("prepTime", prepTime, Field.Store.YES);
	    document.add(fprepTime);
	    
	    Field fcookTime = new StringField("cookTime", cookTime, Field.Store.YES);
	    document.add(fcookTime);
	    
	    Field fdifficultyOfPreparation = new StringField("difficultyOfPreparation", difficultyOfPreparation, Field.Store.YES);
	    document.add(fdifficultyOfPreparation);
	    
	    if(doses != null) {
	    	Field fdoses = new StringField("doses", doses, Field.Store.YES);
	    	document.add(fdoses);
	    }
	    
	    if(cost != null) {
	    	Field fcost = new StringField("cost", cost, Field.Store.YES);
	    	document.add(fcost);
	    }
	    
	    index.addDocument(document);
	    index.commit();
	}
	
	private IndexWriter getIndexWriter() throws IOException {
	    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
	    Directory directory = FSDirectory.open(new File("index"));
	    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
	    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
	    return new IndexWriter(directory, config);
	}
	
	public void init() throws IOException {
		//Create index
		index = getIndexWriter();
	}
	
	public void close() throws IOException {
		//Close index
		index.close();
	}	
}