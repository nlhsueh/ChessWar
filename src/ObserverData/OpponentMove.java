package ObserverData;

import java.awt.Point;

import ChessGame.Chess;
import ChessGame.ChessSide;

/**
 * 用來接收對手AI移動的資料結構類別<br>
 * 撰寫者在實作nextMove時，每回合都會接一個傳入的此物件<br>
 * 並且可以使用get取得相關資訊（請見下方method）
 */
public class OpponentMove {
	
	private Chess[] chess = new Chess[32] ; // 移動後的所有棋子狀況
	private ChessSide side ; // 目前輪到的棋子顏色
	private int index ; // 移動的棋子序號
	private Point oldLoc ; // 移動前的方格座標
	private Point newLoc ; // 移動後的方格座標
	
	public OpponentMove(Chess[] chess, ChessSide side , int index , Point oldLoc, Point newLoc){
		for(int i=0;i<32;i++){
			this.chess[i] = chess[i] ;
		}
		this.side = side;
		this.index = index;
		this.oldLoc = oldLoc;
		this.newLoc = newLoc;
	}
	
	/**
	 * 取得對方棋子的顏色
	 * @return 對方棋子的顏色，Chess.RED為紅棋、Chess.BLACK為黑棋
	 */
	public ChessSide getSide(){
		if(this.side == ChessSide.BLACK)
			return ChessSide.BLACK;
		else
			return ChessSide.RED;
	}
	
	/**
	 * 取得對方選擇移動棋子的index
	 * @return 對方選擇移動棋子的index（請參考索引表）
	 */
	public int getChessIndex(){
		return this.index;
	}
	
	/**
	 * 取得對方選擇移動棋子，在移動之前的座標軸位置
	 * @return 對方移動前的座標軸位置
	 */
	public Point getOriginalLoc(){
		return this.oldLoc;
	}
	
	/**
	 * 取得對方選擇移動棋子，在移動之後的座標軸位置
	 * @return 對方移動後的座標軸位置
	 */
	public Point getTargetLoc(){
		return this.newLoc;
	}
	
	/**
	 * 取得移動後棋盤上的所有棋子物件所組成的陣列
	 * @return 完成移動後的Chess陣列
	 */
	public Chess[] getAllChess(){
		return this.chess;
	}

}
