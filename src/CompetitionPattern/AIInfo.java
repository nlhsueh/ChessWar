package CompetitionPattern;

import AI.AbstractPlayer;
import ViewComponent.CButton;

public class AIInfo{
	
	private String teamName;
	private String AIName ;
	private int no;
	private CButton btn ;
	
	public AbstractPlayer AIClass;
	
	public AIInfo(String teamName, String AIName, int no){
		this.teamName = teamName;
		this.AIName = AIName;
		this.no = no ;
	}
	
	public void setCButton(CButton btn){
		this.btn = btn ;
	}
	
	public CButton getCButton(){
		return this.btn;
	}
	
	public String getTeamName(){
		return this.teamName;
	}
	
	public String getAIName(){
		return this.AIName;
	}
	
	public int getNo(){
		return this.no;
	}
	
	public void setNo(int no){
		this.no = no ;
	}
	
}
