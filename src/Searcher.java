import java.util.ArrayList;


public class Searcher {
	private IStrategy s;
	public Searcher(IStrategy s){
		this.s=s;
	}
	public ArrayList<String> findReceipt(){
		return s.find();
	}
}
