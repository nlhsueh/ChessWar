package ObserverData;

import ChessGame.Chess;

public class DragData {
	public Chess c ;
	public int x ;
	public int y ;
	public String task = "drag" ;
	
	public DragData(Chess c,int x, int y){
		this.c = c ;
		this.x = x ;
		this.y = y ;
	}
}
