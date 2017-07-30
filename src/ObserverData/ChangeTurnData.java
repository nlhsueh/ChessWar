package ObserverData;

import ChessGame.ChessSide;

public class ChangeTurnData {
	
	private ChessSide turn ;
	
	public ChangeTurnData(ChessSide turn){
		this.turn = turn ;
	}
	
	public ChessSide getTurn(){
		return this.turn;
	}
}
