package CompetitionPattern;

public class CompetitionGameSetting {

	private int timeout ;
	
	public CompetitionGameSetting(){
		this.timeout = 10 ;
	}
	
	public void setTimeout(int timeout){
		this.timeout = timeout;
	}
	
	public int getTimeout(){
		return this.timeout;
	}
}
