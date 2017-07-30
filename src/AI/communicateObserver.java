package AI;

import java.util.Observable;
import java.util.Observer;

import ChessGame.ChessSide;
import ObserverData.MyMove;
import ObserverData.OpponentMove;

public class communicateObserver extends Observable implements Observer{
	
	private AbstractPlayer player ;
	private ChessSide myChessSide ; /* 這場遊戲的我方的顏色 */
	
	public communicateObserver(AbstractPlayer player, ChessSide myChessSide){
		this.player = player;
		this.myChessSide = myChessSide;
	}

	/**
	 * 藉由Observer pattern進行AI之的溝通橋樑，呼叫後依序執行下列步驟<br>
	 * 1.接收記錄對方所移動的資料結構物件 (OpponentMove)<br>
	 * 2.呼叫nextMove，並回傳記錄我方決定移動棋子與位置的物件(MyMove)<br>
	 * 3.將我方決定移動的資訊通知對方AI
	 */
	@Override
	final public void update(Observable o, Object arg) {
		if(arg instanceof OpponentMove){
			OpponentMove data = (OpponentMove) arg ;
			if(data.getSide() == this.myChessSide){
				/* 更新棋盤資訊到類別變數 */
				this.player.chess = data.getAllChess() ;
				/* 決定下一手 */
				MyMove myMove = this.player.nextMove(data);
				/* 傳送選擇移動的資訊給對方 */
				this.setChanged();
				this.notifyObservers(myMove);
			}
		}
		
	}
}