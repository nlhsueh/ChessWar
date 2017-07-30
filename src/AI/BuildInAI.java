package AI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import ChessGame.Chess;
import ChessGame.ChessSide;
import ObserverData.MyMove;
import ObserverData.OpponentMove;

public class BuildInAI extends AbstractPlayer{
	
	public BuildInAI(Chess[] chess, ChessSide chessColor) {
		super(chess, chessColor);
	}

	@Override
	protected MyMove nextMove(OpponentMove opponentMoveData) {
		
		Chess c = this.chooseChess();
		Point p = decideMove(c); // 選擇一顆旗子，並選擇要移動到哪
		// 當選擇的棋子無路可走，重新選擇一顆棋子
		while(p == null){
			c = this.chooseChess();
			p = decideMove(c);
		}
//		System.out.println(c.getChessName()+" "+c.getChessSide()+": from "+c.getChessLoc().x+","+c.getChessLoc().y+" to "+p.x+","+p.y);
		
		return new MyMove(c,p);
	}
	
	
	// 選擇要移動的棋子，在此先採隨機取得
	private Chess chooseChess(){
		// 隨機選取一顆活著的己方棋子
		ArrayList<Chess> myChess = new ArrayList<Chess>();
		if(this.myChessSide == ChessSide.RED){
			for(int i=0;i<16;i++){
				if(this.chess[i].isAlive() == true){
					myChess.add(this.chess[i]);
				}
			}
		}else{
			for(int i=16;i<32;i++){
				if(this.chess[i].isAlive() == true){
					myChess.add(this.chess[i]);
				}
			}
		}
		Random ran = new Random();
		int x = ran.nextInt(myChess.size());
		
		return myChess.get(x);
	}
	
	/**
	 * 	索引值分別設定如下：
	 *  紅軍：帥 1 仕 2 仕 3 像 4 像 5 俥 6 俥 7 傌 8 傌 9 炮10 炮11 兵12~16
	 *  黑軍：將17 士18 士19 象20 象21 車22 車23 馬24 馬25 砲26 砲27 卒28~32
	 */
	// 選擇該棋子的可移動點，在此先採隨機取得
	private Point decideMove(Chess c){
		ArrayList<Point> moveSet = new ArrayList<Point>();
		// 判斷該棋子的可移動範圍，隨機選取一步
		
		Point locP = c.getChessLoc() ;
		int locX = locP.x;
		int locY = locP.y ;

		switch(c.getChessIndex()){
			// 帥
			case 1:
				// 九宮格
				for(int i=3;i<=5;i++){
					for(int j=7;j<=9;j++){
						Point p = new Point(i,j);
						if(!HelpFunction.hasMyChess(chess, p,this.myChessSide) && HelpFunction.calDistance(locP, p) == 1){
							moveSet.add(p);
						}
					}
				}
				// 王衝王
				if(this.chess[16].getChessLoc().x == locX && HelpFunction.calObstacle(chess, c, this.chess[16].getChessLoc()) == 0){
					 moveSet.add(new Point(chess[16].getChessLoc().x,chess[16].getChessLoc().y));
				}
				break;
			// 將
			case 17:
				// 九宮格
				for(int i=3;i<=5;i++){
					for(int j=0;j<=2;j++){
						Point p = new Point(i,j);
						if(!HelpFunction.hasMyChess(chess, p,this.myChessSide) && HelpFunction.calDistance(locP, p) == 1){
							moveSet.add(p);
						}
					}
				}
				// 王衝王
				if(this.chess[0].getChessLoc().x == locX && HelpFunction.calObstacle(chess, c, this.chess[0].getChessLoc()) == 0){
					 moveSet.add(new Point(chess[0].getChessLoc().x,chess[0].getChessLoc().y));
				}
				break;
			// 紅仕
			case 2:
			case 3:
				if(!HelpFunction.hasMyChess(chess, new Point(3,9), this.myChessSide) && HelpFunction.calDistance(locP, new Point(3,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,9));
				if(!HelpFunction.hasMyChess(chess, new Point(3,7), this.myChessSide) && HelpFunction.calDistance(locP, new Point(3,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,7));
				if(!HelpFunction.hasMyChess(chess, new Point(4,8), this.myChessSide) && HelpFunction.calDistance(locP, new Point(4,8)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(4,8));
				if(!HelpFunction.hasMyChess(chess, new Point(5,9), this.myChessSide) && HelpFunction.calDistance(locP, new Point(5,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,9));
				if(!HelpFunction.hasMyChess(chess, new Point(5,7), this.myChessSide) && HelpFunction.calDistance(locP, new Point(5,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,7));
				break;
			// 黑士
			case 18:
			case 19:
				if(!HelpFunction.hasMyChess(chess, new Point(3,0), this.myChessSide) && HelpFunction.calDistance(locP, new Point(3,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,0));
				if(!HelpFunction.hasMyChess(chess, new Point(3,2), this.myChessSide) && HelpFunction.calDistance(locP, new Point(3,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,2));
				if(!HelpFunction.hasMyChess(chess, new Point(4,1), this.myChessSide) && HelpFunction.calDistance(locP, new Point(4,1)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(4,1));
				if(!HelpFunction.hasMyChess(chess, new Point(5,0), this.myChessSide) && HelpFunction.calDistance(locP, new Point(5,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,0));
				if(!HelpFunction.hasMyChess(chess, new Point(5,2), this.myChessSide) && HelpFunction.calDistance(locP, new Point(5,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,2));
				break;
			// 紅象
			case 4:
			case 5:
				if(!HelpFunction.hasMyChess(chess, new Point(0,7), this.myChessSide) && HelpFunction.calDistance(locP, new Point(0,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(0,7)) == 0) moveSet.add(new Point(0,7));
				if(!HelpFunction.hasMyChess(chess, new Point(2,5), this.myChessSide) && HelpFunction.calDistance(locP, new Point(2,5)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,5)) == 0) moveSet.add(new Point(2,5));
				if(!HelpFunction.hasMyChess(chess, new Point(2,9), this.myChessSide) && HelpFunction.calDistance(locP, new Point(2,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,9)) == 0) moveSet.add(new Point(2,9));
				if(!HelpFunction.hasMyChess(chess, new Point(4,7), this.myChessSide) && HelpFunction.calDistance(locP, new Point(4,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(4,7)) == 0) moveSet.add(new Point(4,7));
				if(!HelpFunction.hasMyChess(chess, new Point(6,5), this.myChessSide) && HelpFunction.calDistance(locP, new Point(6,5)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,5)) == 0) moveSet.add(new Point(6,5));
				if(!HelpFunction.hasMyChess(chess, new Point(6,9), this.myChessSide) && HelpFunction.calDistance(locP, new Point(6,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,9)) == 0) moveSet.add(new Point(6,9));
				if(!HelpFunction.hasMyChess(chess, new Point(8,7), this.myChessSide) && HelpFunction.calDistance(locP, new Point(8,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(8,7)) == 0) moveSet.add(new Point(8,7));
				break;
			// 黑象
			case 20:
			case 21:
				if(!HelpFunction.hasMyChess(chess, new Point(0,2), this.myChessSide) && HelpFunction.calDistance(locP, new Point(0,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(0,2)) == 0) moveSet.add(new Point(0,2));
				if(!HelpFunction.hasMyChess(chess, new Point(2,0), this.myChessSide) && HelpFunction.calDistance(locP, new Point(2,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,0)) == 0) moveSet.add(new Point(2,0));
				if(!HelpFunction.hasMyChess(chess, new Point(2,4), this.myChessSide) && HelpFunction.calDistance(locP, new Point(2,4)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,4)) == 0) moveSet.add(new Point(2,4));
				if(!HelpFunction.hasMyChess(chess, new Point(4,2), this.myChessSide) && HelpFunction.calDistance(locP, new Point(4,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(4,2)) == 0) moveSet.add(new Point(4,2));
				if(!HelpFunction.hasMyChess(chess, new Point(6,0), this.myChessSide) && HelpFunction.calDistance(locP, new Point(6,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,0)) == 0) moveSet.add(new Point(6,0));
				if(!HelpFunction.hasMyChess(chess, new Point(6,4), this.myChessSide) && HelpFunction.calDistance(locP, new Point(6,4)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,4)) == 0) moveSet.add(new Point(6,4));
				if(!HelpFunction.hasMyChess(chess, new Point(8,2), this.myChessSide) && HelpFunction.calDistance(locP, new Point(8,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(8,2)) == 0) moveSet.add(new Point(8,2));
				break;
			// 紅車、黑車
			case 6:
			case 7:
			case 22:
			case 23:
				// 搜尋上下左右，直到遇到第一顆棋子
				// 左
				for(int i=locX-1;i>=0;i--){
					if(HelpFunction.hasChess(chess, new Point(i,locY))){
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(i,locY));
							break;
						}else{
							break;
						}
					}else{
						moveSet.add(new Point(i,locY));
					}
				}
				// 右
				for(int i=locX+1;i<=8;i++){
					if(HelpFunction.hasChess(chess, new Point(i,locY))){
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(i,locY));
							break;
						}else{
							break;
						}
					}else{
						moveSet.add(new Point(i,locY));
					}
				}
				// 上
				for(int i=locY-1;i>=0;i--){
					if(HelpFunction.hasChess(chess, new Point(locX,i))){
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(locX,i));
							break;
						}else{
							break;
						}
					}else{
						moveSet.add(new Point(locX,i));
					}
				}
				// 下
				for(int i=locY+1;i<=9;i++){
					if(HelpFunction.hasChess(chess, new Point(locX,i))){
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(locX,i));
							break;
						}else{
							break;
						}
					}else{
						moveSet.add(new Point(locX,i));
					}
				}
				break;
			// 紅馬、黑馬
			case 8:
			case 9:
			case 24:
			case 25:
				// 往左, 沒有超出邊界＆沒有卡馬腳
				if(locX-2 >=0 && !HelpFunction.hasChess(chess, new Point(locX-1,locY))){
					// 左上, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locY-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-2,locY-1), myChessSide)) moveSet.add(new Point(locX-2,locY-1));
					// 左下, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locY+1 <=9 && !HelpFunction.hasMyChess(chess, new Point(locX-2,locY+1), myChessSide)) moveSet.add(new Point(locX-2,locY+1));
				}
				// 往右, 沒有超出邊界＆沒有卡馬腳
				if(locX+2 <=8 && !HelpFunction.hasChess(chess, new Point(locX+1,locY))){
					// 右上, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locY-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX+2,locY-1), myChessSide)) moveSet.add(new Point(locX+2,locY-1));
					// 右下, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locY+1 <=9 && !HelpFunction.hasMyChess(chess, new Point(locX+2,locY+1), myChessSide)) moveSet.add(new Point(locX+2,locY+1));
				}
				// 往上, 沒有超出邊界＆沒有卡馬腳
				if(locY-2 >=0 && !HelpFunction.hasChess(chess, new Point(locX,locY-1))){
					// 上左, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY-2), myChessSide)) moveSet.add(new Point(locX-1,locY-2));
					// 上右, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY-2), myChessSide)) moveSet.add(new Point(locX+1,locY-2));
				}
				// 往下, 沒有超出邊界＆沒有卡馬腳
				if(locY+2 <=9 && !HelpFunction.hasChess(chess, new Point(locX,locY+1))){
					// 下左, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY+2), myChessSide)) moveSet.add(new Point(locX-1,locY+2));
					// 上右, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY+2), myChessSide)) moveSet.add(new Point(locX+1,locY+2));
				}
				break;
			// 紅炮、黑炮
			case 10:
			case 11:
			case 26:
			case 27:
				// 搜尋上下左右，直到遇到第一顆棋子
				// 左
				boolean flag = false ;
				for(int i=locX-1;i>=0;i--){
					if(HelpFunction.hasChess(chess, new Point(i,locY)) && flag == false){
						flag = true;
						continue;
					}else if(HelpFunction.hasChess(chess, new Point(i,locY)) && flag == true){
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(i,locY));
							break;
						}else{
							break;
						}
					}else if(!HelpFunction.hasChess(chess, new Point(i,locY)) && flag == false){
						moveSet.add(new Point(i,locY));
					}
				}
				flag = false;
				// 右
				for(int i=locX+1;i<=8;i++){
					if(HelpFunction.hasChess(chess, new Point(i,locY)) && flag == false){
						flag = true;
						continue;
					}else if(HelpFunction.hasChess(chess, new Point(i,locY)) && flag == true){
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(i,locY));
							break;
						}else{
							break;
						}
					}else if(!HelpFunction.hasChess(chess, new Point(i,locY)) && flag == false){
						moveSet.add(new Point(i,locY));
					}
				}
				flag = false;
				// 上
				for(int i=locY-1;i>=0;i--){
					if(HelpFunction.hasChess(chess, new Point(locX,i)) && flag == false){
						flag = true;
						continue;
					}else if(HelpFunction.hasChess(chess, new Point(locX,i)) && flag == true){
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(locX,i));
							break;
						}else{
							break;
						}
					}else if(!HelpFunction.hasChess(chess, new Point(locX,i)) && flag == false){
						moveSet.add(new Point(locX,i));
					}
				}
				flag = false;
				
				// 下
				for(int i=locY+1;i<=9;i++){
					if(HelpFunction.hasChess(chess, new Point(locX,i)) && flag == false){
						flag = true;
						continue;
					}else if(HelpFunction.hasChess(chess, new Point(locX,i)) && flag == true){
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != this.myChessSide){
							moveSet.add(new Point(locX,i));
							break;
						}else{
							break;
						}
					}else if(!HelpFunction.hasChess(chess, new Point(locX,i)) && flag == false){
						moveSet.add(new Point(locX,i));
					}
				}
				flag = false;
				break;
			// 兵
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
				// 過河了，可以走左、右、前
				if(locY <= 4){
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY), this.myChessSide)){
						moveSet.add(new Point(locX-1,locY));
					}
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY), this.myChessSide)){
						moveSet.add(new Point(locX+1,locY));
					}
					if(locY-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX,locY-1), this.myChessSide)){
						moveSet.add(new Point(locX,locY-1));
					}
				// 沒有過河、只能往前
				}else{
					if(!HelpFunction.hasMyChess(chess, new Point(locX,locY-1), this.myChessSide)){
						moveSet.add(new Point(locX,locY-1));
					}
				}
				break;
			case 28:
			case 29:
			case 30:
			case 31:
			case 32:
				// 過河了，可以走左、右、前
				if(locY >= 5){
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY), this.myChessSide)){
						moveSet.add(new Point(locX-1,locY));
					}
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY), this.myChessSide)){
						moveSet.add(new Point(locX+1,locY));
					}
					if(locY+1 <=9 && !HelpFunction.hasMyChess(chess, new Point(locX,locY+1), this.myChessSide)){
						moveSet.add(new Point(locX,locY+1));
					}
				// 沒有過河、只能往前
				}else{
					if(!HelpFunction.hasMyChess(chess, new Point(locX,locY+1), this.myChessSide)){
						moveSet.add(new Point(locX,locY+1));
					}
				}
				break;
		}
		
		if(moveSet.isEmpty()){
			return null;
		}
		
		Random ran = new Random();
		int x = ran.nextInt(moveSet.size());
		return moveSet.get(x);
		
	}
	
}
