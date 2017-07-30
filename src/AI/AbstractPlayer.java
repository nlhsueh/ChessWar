package AI;

import ChessGame.Chess;
import ChessGame.ChessSide;
import ObserverData.MyMove;
import ObserverData.OpponentMove;

/**
 * 撰寫時必須繼承並實作的AI class<br>
 * 使用者在撰寫時無需為此類別建立任何物件，也請不要覆蓋建構子<br>
 * 載入的主程式jar會自動為此class帶入參數初始化並建立物件<br>
 * 撰寫者只需實作nextMove，由傳入的資訊決定下一步，並回傳即可
 */
public abstract class AbstractPlayer{
	
	/**
	 * 包含所有棋子最新資訊的Chess陣列
	 */
	protected Chess[] chess = new Chess[32];
	/**
	 * 這場遊戲，我方棋子的顏色，ChessSide.BLACK為黑、ChessSide.Red為紅
	 */
	protected ChessSide myChessSide ; /* 這場遊戲我方的顏色 */
	/**
	 * 這場遊戲，對方棋子的顏色，ChessSide.BLACK為黑、ChessSide.Red為紅
	 */
	protected ChessSide opponentSide; /* 這場遊戲對方的顏色 */
	
	/**
	 * 建構子接收Chess陣列，包含32顆棋子的初始狀態，與這場比賽我方棋子的顏色
	 * @param chess 32顆棋子的初始狀態
	 * @param myChessSide 我方AI在這場遊戲中的顏色ChessSide.BLACK為黑、ChessSide.Red為紅
	 */
	public AbstractPlayer(Chess[] chess, ChessSide myChessSide){
		for(int i=0;i<32;i++){
			this.chess[i] = chess[i] ;
		}
		this.myChessSide = myChessSide;
		if(this.myChessSide == ChessSide.BLACK){
			this.opponentSide = ChessSide.RED;
		}else{
			this.opponentSide = ChessSide.BLACK;
		}
	}
	
	/**
	 * 藉由傳入的對方移動資訊，實作AI決定的下一手，並將我方AI選擇的移動棋子與位置回傳
	 * @param opponentMove 接收對方移動的訊息
	 * @return 回傳AIMoveData，包含欲選擇移動的棋子與欲移動的座標
	 */
	protected abstract MyMove nextMove(OpponentMove opponentMove);
	
	/**
	 * 複製傳入棋子之所有棋子之狀態
	 * @param chess 欲複製的棋子陣列
	 * @return 回傳Chess陣列，包含傳入賽局的所有棋子狀態，可做為模擬下一步時使用
	 */
	protected Chess[] getAllCopyChess(Chess[] chess){
		Chess[] copyChess = new Chess[32];
		for(int i=0;i<32;i++){
			copyChess[i] = new Chess(chess[i]);
		}
		return copyChess;
	}
	
}
