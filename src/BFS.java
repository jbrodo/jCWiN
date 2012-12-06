import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class BFS implements IStrategy {
	private String home;
	private int numeroMaxRicette;
	
	public BFS(String home,int numeroMaxRicette){
		this.home=home;
		this.numeroMaxRicette=numeroMaxRicette;
	}

	public ArrayList<String> find() {
		HashMap<String,ArrayList<String>> pagine = new HashMap<String,ArrayList<String>>();
		HashMap<String,String> pagineVisitate = new HashMap<String,String>();
		ArrayList<String> ricette = new ArrayList<String>();

		String htmlhome = ScannerHTML.getHTML(home);
		System.out.println(home);
		System.out.println(htmlhome);
		pagine.put(home, ScannerHTML.getAllLinks(home,htmlhome,home));
		pagineVisitate.put(home,"g");
		int j=0;
		for(String s :pagine.get(home)){
			if(!pagineVisitate.containsKey(s)) pagineVisitate.put(s,"g");
			String r = ScannerHTML.getHTML(s);
			System.out.println((++j)+"/"+pagine.get(home).size()+" - "+ScannerHTML.isRicetta(r)+" - "+s);
			if(ScannerHTML.isRicetta(r) && !ricette.contains(r)) ricette.add(r);
		}
		pagineVisitate.put(home,"b");
		System.out.println(pagine.get(home).size());

		//
		int k=0;
		
		while(!ScannerHTML.isAllBlack(pagineVisitate)&&ricette.size()<numeroMaxRicette){
			HashMap<String,String> copia = new HashMap<String,String>();
			Iterator<String> l = pagineVisitate.keySet().iterator();
			while(l.hasNext()&&ricette.size()<numeroMaxRicette){
				String s= l.next();
				k++;
				String htmls=ScannerHTML.getHTML(s);
				if(ScannerHTML.isRobotsEnabled(htmls)){
					if(pagineVisitate.get(s).equals("g")){
						pagine.put(s, ScannerHTML.getAllLinks(s,htmls,home));
					}
					System.out.println(k+"/"+pagineVisitate.keySet().size()+" - Numero ricette trovate: "+ricette.size());
					j=0;
					for(String s1 :pagine.get(s)){
						if(!pagineVisitate.containsKey(s1)) copia.put(s1,"g");
						String r = ScannerHTML.getHTML(s1);
						System.out.println((++j)+"/"+pagine.get(s).size()+" - "+s+" - "+ScannerHTML.isRicetta(r)+" - "+s1);
						if(ScannerHTML.isRicetta(r) && !ricette.contains(r)) ricette.add(r);
					}
				}
				copia.put(s,"b");
			}

			for(String s:copia.keySet())
				pagineVisitate.put(s,copia.get(s));

		}
		return ricette;
	}

}
