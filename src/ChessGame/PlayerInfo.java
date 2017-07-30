package ChessGame;

public class PlayerInfo {
	private String playerNickname;
	private String externalName;
	private int playerType ; // 1 - human ,   2 - Internal AI , 3- External AI
	private ChessSide playerColor ; // 1 = red , 0 = black
	private String colorWord ; // 紅子 or 黑子
	private String colorChess ; // #word color

	public void setPlayerNickname(String nickname){
		this.playerNickname = nickname ;
	}
	
	public String getPlayerNickname(){
		return this.playerNickname;
	}
	
	public void setExternalName(String externalName){
		this.externalName = externalName ;
	}
	
	public String getExternalname(){
		return this.externalName;
	}
	
	public void setPlayerType(int type){
		this.playerType = type ;
	}
	
	public int getPlayerType(){
		return this.playerType;
	}
	
	public void setPlayerColor(ChessSide side){
		this.playerColor = side;
	}
	
	public ChessSide getPlayerColor(){
		return this.playerColor;
	}
	
	public void setColorWord(String colorWord){
		this.colorWord = colorWord;
	}
	
	public String getColorWord(){
		return this.colorWord;
	}
	
	public void setColorChess(String colorChess){
		this.colorChess = colorChess;
	}
	
	public String getColorChess(){
		return this.colorChess;
	}

}
