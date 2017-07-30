package Share;

import java.io.Serializable;
import java.util.ArrayList;

public class ExternalAIData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> AIMainName = new ArrayList<String>();
	private ArrayList<ArrayList<String>> AISubName = new ArrayList<ArrayList<String>>();
	
	public ArrayList<String> getAIMainNameSet(){
		return this.AIMainName;
	}
	
	public ArrayList<ArrayList<String>> getAISubNameSet(){
		return this.AISubName;
	}
}
