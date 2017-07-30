package ObserverData;

import java.awt.Point;

import ChessGame.Chess;
import ChessGame.ChessSide;

public class MoveRecordData{
	
	/**
	 * 	索引值分別設定如下：
	 *  紅軍：帥 1 仕 2 仕 3 相 4 相 5 俥 6 俥 7 傌 8 傌 9 炮10 炮11 兵12~16
	 *  黑軍：將17 士18 士19 象20 象21 車22 車23 馬24 馬25 砲26 砲27 卒28~32
	 */
	
	private int round ; // 第幾手
	private int index ; // 移動棋子的 index
	private String name ; // 棋子的名稱
	private String chessWord ; // 棋子的漢字
	private String moveWord ; // 棋譜文字紀錄，如砲二平五，象七進五，馬八進七
	private Point oldPos ; // 移動前的方格座標
	private Point newPos ; // 移動後的方格座標
	private ChessSide side ; // 象棋顏色 1 = 紅 , 0 = 黑
	private String status ; // 是否為「eat」吃棋動作、「break」違規動作、「timeout」超過思考時間
	private int beEatedChessIndex; // 被吃棋的位置
	private String breakWord ; // 違規資訊
	private String timeoutWord = ""; // 超過時間資訊
	
	/* move & break */
	public MoveRecordData(int round,int index,String chessWord,Point oldPos,Point newPos,ChessSide side, String status){
		this.round = round ;
		this.index = index ;
		this.name = Chess.chessName[index-1];
		this.chessWord = chessWord;
		this.oldPos = oldPos ;
		this.newPos = newPos ;
		this.side = side ;
		this.status = new String(status);
		
		this.setMoveWord();
		this.setBreakWord();
	}
	
	/* eat */
	public MoveRecordData(int round,int index,String chessWord,Point oldPos,Point newPos,ChessSide side, String status, int beEatedChessIndex){
		this.round = round ;
		this.index = index ;
		this.name = Chess.chessName[index-1];
		this.chessWord = chessWord;
		this.oldPos = oldPos ;
		this.newPos = newPos ;
		this.side = side ;
		this.status = new String(status);
		this.beEatedChessIndex = beEatedChessIndex ;
		
		this.setMoveWord();
		this.setBreakWord();
	}
	
	/* timeout */
	public MoveRecordData(int round , ChessSide side, String status){
		this.round = round ;
		this.side = side ;
		this.status = status ;
		if(this.status.equals("timeout")){
			String chessColor;
			if(side == ChessSide.RED)
				chessColor = "紅棋";
			else
				chessColor = "黑棋";
			this.moveWord = "超過思考時間";
			this.timeoutWord = "<html>在第<b>"+this.round+"</>回合 <b>"+chessColor+"</> 超過思考時間，視為投降，遊戲結束</html>" ;
		}
	}
	
	private void setMoveWord(){
		String word1 = this.chessWord ; // 第一個文字為「當下移動的棋子名稱」
		String word2 = ""; // 第二個文字為「移動前位於己方直線上的位置」
		String word3 = ""; // 第三個文字為「說明棋子垂直移動的方向」
		String word4 = ""; // 第四個文字為「移動後己方直線上的位置」
		
		if(this.side == ChessSide.RED){
			// 紅棋 第二個字為漢字
			switch(this.oldPos.x){
				case 0:
					word2 = "九" ;
					break;
				case 1:
					word2 = "八" ;
					break;
				case 2:
					word2 = "七" ;
					break;
				case 3:
					word2 = "六" ;
					break;
				case 4:
					word2 = "五" ;
					break;
				case 5:
					word2 = "四" ;
					break;
				case 6:
					word2 = "三" ;
					break;
				case 7:
					word2 = "二" ;
					break;
				case 8:
					word2 = "一" ;
					break;
			}
			// 紅棋 第三個字為平行移動的方向
			if(this.oldPos.y == this.newPos.y){
				word3 = "平" ;
			}else if(this.oldPos.y > this.newPos.y){
				word3 = "進" ;
			}else if(this.oldPos.y < this.newPos.y){
				word3 = "退" ;
			}
			// 紅棋 第四個字為移動後的位置
			switch(this.index){
				// 仕、象、馬，非直線移動，說明移動後的位置
				case 2:
				case 3:
				case 4:
				case 5:
				case 8:
				case 9:
					switch(this.newPos.x){
					case 0:
						word4 = "九" ;
						break;
					case 1:
						word4 = "八" ;
						break;
					case 2:
						word4 = "七" ;
						break;
					case 3:
						word4 = "六" ;
						break;
					case 4:
						word4 = "五" ;
						break;
					case 5:
						word4 = "四" ;
						break;
					case 6:
						word4 = "三" ;
						break;
					case 7:
						word4 = "二" ;
						break;
					case 8:
						word4 = "一" ;
						break;
					}
					break;
				// 其餘直線移動，說明前進或後退幾格
				default:
					if(this.oldPos.y > this.newPos.y){
						// 進
						switch(this.oldPos.y - this.newPos.y){
						case 1:
							word4 = "一" ;
							break;
						case 2:
							word4 = "二" ;
							break;
						case 3:
							word4 = "三" ;
							break;
						case 4:
							word4 = "四" ;
							break;
						case 5:
							word4 = "五" ;
							break;
						case 6:
							word4 = "六" ;
							break;
						case 7:
							word4 = "七" ;
							break;
						case 8:
							word4 = "八" ;
							break;
						case 9:
							word4 = "九" ;
							break;
						}
					}else if(this.oldPos.y < this.newPos.y){
						// 退
						switch(this.newPos.y - this.oldPos.y){
						case 1:
							word4 = "一" ;
							break;
						case 2:
							word4 = "二" ;
							break;
						case 3:
							word4 = "三" ;
							break;
						case 4:
							word4 = "四" ;
							break;
						case 5:
							word4 = "五" ;
							break;
						case 6:
							word4 = "六" ;
							break;
						case 7:
							word4 = "七" ;
							break;
						case 8:
							word4 = "八" ;
							break;
						case 9:
							word4 = "九" ;
							break;
						}
					}else if(this.newPos.y == this.oldPos.y){
						// 平移
						switch(this.newPos.x){
						case 0:
							word4 = "九" ;
							break;
						case 1:
							word4 = "八" ;
							break;
						case 2:
							word4 = "七" ;
							break;
						case 3:
							word4 = "六" ;
							break;
						case 4:
							word4 = "五" ;
							break;
						case 5:
							word4 = "四" ;
							break;
						case 6:
							word4 = "三" ;
							break;
						case 7:
							word4 = "二" ;
							break;
						case 8:
							word4 = "一" ;
							break;
						}
					}
					break;
			}
			
		}else{
			// 黑棋 第二個字為平行移動前的數字
			word2 = Integer.toString(this.oldPos.x+1) ;
			// 黑棋 第三個字為平行移動的方向
			if(this.oldPos.y == this.newPos.y){
				word3 = "平" ;
			}else if(this.oldPos.y > this.newPos.y){
				word3 = "退" ;
			}else if(this.oldPos.y < this.newPos.y){
				word3 = "進" ;
			}
			// 黑棋 第四個字為移動後的位置
			word4 = Integer.toString(this.newPos.x+1) ;
			switch(this.index){
				// 仕、象、馬，非直線移動，說明移動後的位置
				case 2:
				case 3:
				case 4:
				case 5:
				case 8:
				case 9:
				word4 = Integer.toString(this.newPos.x+1) ;
				// 其餘直線移動，說明前進或後退幾格
				default:
					if(this.oldPos.y > this.newPos.y){
						// 進
						word4 = Integer.toString(this.oldPos.y-this.newPos.y) ;
					}else if(this.oldPos.y < this.newPos.y){
						// 退
						word4 = Integer.toString(this.newPos.y-this.oldPos.y) ;
					}else if(this.newPos.y == this.oldPos.y){
						// 平移
						word4 = Integer.toString(this.newPos.x+1) ;
					}
					break;
				}
		}
		
		this.moveWord = word1+word2+word3+word4 ;
	}
	
	private void setBreakWord(){
		if(this.status.equals("break") == false){
			return;
		}else{
			String colorS ;
			if(this.side == ChessSide.RED){
				colorS = "紅棋違規\n" ;
			}else{
				colorS = "黑棋違規\n" ;
			}
			this.breakWord = colorS+"違規資訊：\n選擇棋子："+this.name+"\nfrom "+this.oldPos.x+","+this.oldPos.y+" to "+this.newPos.x+","+this.newPos.y+" (棋盤最左上角為0,0)\n遊戲結束";
		}
	}
	
	public boolean isEat(){
		if(this.status.equals("eat"))
			return true;
		else
			return false;
	}
	
	public boolean isBreak(){
		if(this.status.equals("break"))
			return true;
		else
			return false;
	}
	
	public boolean isTimeout(){
		if(this.status.equals("timeout"))
			return true;
		else
			return false;
	}
	
	public String getBreakWord(){
		return this.breakWord;
	}
	
	public String getMoveWord(){
		return this.moveWord;
	}
	
	public String getTimeoutWord(){
		return this.timeoutWord;
	}
	
	public ChessSide getSide(){
		return this.side;
	}
	
	public int getRound(){
		return this.round;
	}
	
	public Point getOldPos(){
		return this.oldPos;
	}
	
	public Point getNewPos(){
		return this.newPos;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public int getBeEatedChessIndex(){
		return this.beEatedChessIndex;
	}
	
}