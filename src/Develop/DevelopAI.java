package Develop;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import AI.AbstractPlayer;
import AI.HelpFunction;
import ChessGame.Chess;
import ChessGame.ChessSide;
import ObserverData.MyMove;
import ObserverData.OpponentMove;

public class DevelopAI extends AbstractPlayer{
	
	/**
	 * 	索引值分別設定如下：
	 *  紅軍：帥 1 仕 2 仕 3 像 4 像 5 俥 6 俥 7 傌 8 傌 9 炮10 炮11 兵12~16
	 *  黑軍：將17 士18 士19 象20 象21 車22 車23 馬24 馬25 砲26 砲27 卒28~32
	 *  
	 *  分數設定
	 *  吃棋為正，被對方吃為負
	 *  將帥: 1000
	 *  車俥: 800
	 *  包炮: 900
	 *  馬傌: 700
	 *  士仕: 600
	 *  象像: 400
	 *  卒兵: 200
	 *  無 :  0
	 */
	
	private int[] pointArray = {
			-1,
			10000,600,600,400,400,800,800,700,700,900,900,200,200,200,200,200,
			10000,600,600,400,400,800,800,700,700,900,900,200,200,200,200,200,
	};
	
	/* 炮>傌>俥>像>仕>卒>將 */
	private int[] moveArray = {
		-1,
		0,20,20,30,30,40,40,50,50,60,60,10,10,10,10,10,	
		0,20,20,30,30,40,40,50,50,60,60,10,10,10,10,10,	
	};
	
	private static final int infMax = Integer.MAX_VALUE;
	
	private int MinMaxDepth = 2 ;

	public DevelopAI(Chess[] chess, ChessSide myChessSide) {
		super(chess, myChessSide);
	}

	@Override
	protected MyMove nextMove(OpponentMove opponentMove) {
		// 執行時間計算
		double time1 = System.currentTimeMillis();
		
		ArrayList<Chess> liveChess = this.getAllMyLiveChess(this.chess);
		int max = -1 ;
		Chess maxMoveChess = null;
		Point maxMovePoint = null;
		
		int beta  = infMax;
		/* 每一顆活著的棋子 */
		for(int i=0;i<liveChess.size();i++){
			/* 可走的每一步round1 */
			ArrayList<Point> moveSet = this.getMoveSet(chess, liveChess.get(i), this.myChessSide);
			/* 開始遍歷可走的每一步round1 */
			for(int j=0;j<moveSet.size();j++){
				Chess thisChess = liveChess.get(i);
				Point thisMove = moveSet.get(j);
				// 能勝利直接剪枝
				if(HelpFunction.isExistKing(chess, thisMove, this.myChessSide)){
					return new MyMove(thisChess, thisMove);
				}
				// 取得這手分數
				int thisMovePoint = this.calculatePoint(chess, thisChess, thisMove, this.myChessSide);
				int totalPoint = alphaBeta(chess, thisChess, thisMove, MinMaxDepth, max, beta, this.myChessSide, thisMovePoint);
				/* 記錄最佳手 */
				if(max < totalPoint){
					max = totalPoint;
					maxMoveChess = thisChess;
					maxMovePoint = thisMove;
				}
			}
			
		}
		double time2 = System.currentTimeMillis();
		System.out.println("time: "+((time2-time1)/1000)+" seconds");
		System.out.println("max: "+max);
		System.out.println("chess: "+maxMoveChess.getChessName()+"("+maxMoveChess.getChessLoc().x+","+maxMoveChess.getChessLoc().y+")");
		System.out.println("move: ("+maxMovePoint.x+","+maxMovePoint.y+")\n");
		return new MyMove(maxMoveChess,maxMovePoint);
		
//		return this.MinMax(2);
	}	
	
	/* 每個node相當於一個當前局面的走法，node的value為該走法的分數
	 * 故node包含 1. Chess[] chess 當前局面
	 *           2. Chess c 欲移動Chess
	 *           3. Point p 移動位置
	 * value = 這個走法經盤面函數取得的分數
	 * init  = 是否第一次進入
	 * pointSum 一系列走法最後的得分和
	 */
	private int alphaBeta(Chess[] chess, Chess c, Point p, int depth, int alpha, int beta, ChessSide strategy, int pointSum){
		if(depth == 0 || HelpFunction.isGameOver(chess)){
			return pointSum;
		}
		
		if(strategy == this.myChessSide){ // Max
			/* 取得child node */
			Chess[] chessRound = this.simulateMove(this.getAllCopyChess(chess), c, p); // 模擬這一手的移動
			ArrayList<Chess> chessLive = this.getAllMyLiveChess(chessRound); // 取得所有活著的棋子
			for(int i=0; i<chessLive.size(); i++){
				ArrayList<Point> moveSet = this.getMoveSet(chessRound, chessLive.get(i), this.myChessSide); // 取得可走的每一步
				for(int j=0; j<moveSet.size();j++){
					int thisMovePoint = this.calculatePoint(chessRound, chessLive.get(i), moveSet.get(j),this.myChessSide);
					alpha = Math.max(alpha, alphaBeta(chessRound, chessLive.get(i), moveSet.get(j), depth-1, alpha, beta, this.opponentSide, pointSum + thisMovePoint));
					if(beta <= alpha){
						return alpha; /* beta cut off */
					}
				}
			}
			return alpha ;
		}else{ // Minimum
			/* 取得child node */
			Chess[] chessRound = this.simulateMove(this.getAllCopyChess(chess), c, p); // 模擬這一手的移動
			ArrayList<Chess> chessLive = this.getAllOpponentLiveChess(chessRound); // 取得所有活著的棋子
			for(int i=0; i<chessLive.size(); i++){
				ArrayList<Point> moveSet = this.getMoveSet(chessRound, chessLive.get(i), this.opponentSide); // 取得可走的每一步
				for(int j=0; j<moveSet.size();j++){
					int thisMovePoint = this.calculatePoint(chessRound, chessLive.get(i), moveSet.get(j),this.opponentSide);
					beta = Math.min(beta, alphaBeta(chessRound, chessLive.get(i), moveSet.get(j), depth-1, alpha, beta, this.myChessSide, pointSum + thisMovePoint));
					if(beta <= alpha){
						return beta; /* alpha cut off */
					}
				}
			}
			return beta ;
		}
		
	}
	
	/* 盤面函數計算，吃棋分數計算 */
	/* 傳入移動後位置，回傳可得分數
	 * 吃棋為正，被對方吃為負
	 * 將帥: 1000
	 * 車俥: 100
	 * 包炮: 90
	 * 馬傌: 80
	 * 士仕: 60
	 * 象像: 40
	 * 卒兵: 20
	 * 無 :  0
	 */
	private int calculatePoint(Chess[] chess, Chess c, Point p, ChessSide side){
		
		if(HelpFunction.hasChess(chess, p)){
			/* 有棋子，回傳吃棋分數 */
			int controlPoint = this.calculateControlPoint(this.getAllCopyChess(chess), c, p, side);
			int eatPoint = this.pointArray[HelpFunction.getChess(chess, p).getChessIndex()];
			
			return controlPoint + eatPoint ;
		}else{
			/* 沒有棋子，單純移動，回傳移動分數 */
			int controlPoint = this.calculateControlPoint(this.getAllCopyChess(chess), c, p, side);
			int movePoint = this.moveArray[c.getChessIndex()];
			
			return controlPoint + movePoint ;
		}
		
	}
	
	/* 計算棋子移動後，可移動與可吃棋的加權分數 */
	private int calculateControlPoint(Chess[] chess, Chess c, Point p, ChessSide side){
		int point = 0;
		chess = this.simulateMove(chess, c, p);
		
		ArrayList<Point> moveSet = this.getMoveSet(chess, chess[c.getChessIndex()-1], side); // 取得可走的每一步
		for(int i=0; i<moveSet.size();i++){
			/* 可吃棋 */
			if(HelpFunction.hasChess(chess, moveSet.get(i)) && !HelpFunction.hasMyChess(chess, moveSet.get(i), side)){
				point += 20;
			}
			/* 可移動 */
			if(!HelpFunction.hasChess(chess, moveSet.get(i))){
				point+= 5 ;
			}
		}
		
		return point;
	}
	
	/* 模擬移動 */
	private Chess[] simulateMove(Chess[] nowAllChess, Chess moveChess, Point movePoint){
		/* 如果該點有棋子，則吃棋 */
		if(HelpFunction.hasChess(nowAllChess, movePoint)){
			nowAllChess[HelpFunction.getChess(nowAllChess, movePoint).getChessIndex()-1].setChessDie();
			nowAllChess[moveChess.getChessIndex()-1].setChessLoc(movePoint);
		}else{
		/* 該點無棋，則移動 */
			nowAllChess[moveChess.getChessIndex()-1].setChessLoc(movePoint);
		}
		
		return nowAllChess;
	}
	
	/* 取得我方所有存活棋子 */
	private ArrayList<Chess> getAllMyLiveChess(Chess[] chess){
		ArrayList<Chess> myChess = new ArrayList<Chess>();
		if(this.myChessSide == ChessSide.RED){
			for(int i=0;i<16;i++){
				if(chess[i].isAlive() == true){
					myChess.add(chess[i]);
				}
			}
		}else{
			for(int i=16;i<32;i++){
				if(chess[i].isAlive() == true){
					myChess.add(chess[i]);
				}
			}
		}
		/* 排序增加alpha-beta剪枝效率 */
		this.resort(myChess);
		
		return myChess;
	}
	
	/* 取得對方所有存活棋子 */
	private ArrayList<Chess> getAllOpponentLiveChess(Chess[] chess){
		ArrayList<Chess> myChess = new ArrayList<Chess>();
		if(this.myChessSide == ChessSide.RED){
			for(int i=16;i<32;i++){
				if(chess[i].isAlive() == true){
					myChess.add(chess[i]);
				}
			}
		}else{
			for(int i=0;i<16;i++){
				if(chess[i].isAlive() == true){
					myChess.add(chess[i]);
				}
			}
		}
		/* 排序增加alpha-beta剪枝效率 */
		this.resort(myChess);
		return myChess;
	}
	
	/* 重新排列以增加alpha-beta效率 */
	private ArrayList<Chess> resort(ArrayList<Chess> chess){
		
//		for(int i=0;i<chess.size();i++){
//			System.out.print(chess.get(i).getChessIndex()+" ");
//		}
//		System.out.println();
		
		for(int i=0, maxIndex;i<chess.size();i++){
			maxIndex = i ;
			for (int j = i + 1; j < chess.size(); ++j)
                if (this.moveArray[chess.get(j).getChessIndex()] > this.moveArray[chess.get(maxIndex).getChessIndex()])
                    maxIndex = j;
            if (maxIndex != i)
            	Collections.swap(chess, maxIndex, i);
		}
		
		return chess; 
	}
	
	// 選擇該棋子的可移動點，在此先採隨機取得
	private ArrayList<Point> getMoveSet(Chess[] chess, Chess c, ChessSide chessColor){
		ArrayList<Point> moveSet = new ArrayList<Point>();
		// 判斷該棋子的可移動範圍，並計算分數
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
						if(!HelpFunction.hasMyChess(chess, p,chessColor) && HelpFunction.calDistance(locP, p) == 1){
							moveSet.add(p);
						}
					}
				}
				// 王衝王
				if(chess[16].getChessLoc().x == locX && HelpFunction.calObstacle(chess, c, chess[16].getChessLoc()) == 0){
					 moveSet.add(new Point(chess[16].getChessLoc().x,chess[16].getChessLoc().y));
				}
				break;
			// 將
			case 17:
				// 九宮格
				for(int i=3;i<=5;i++){
					for(int j=0;j<=2;j++){
						Point p = new Point(i,j);
						if(!HelpFunction.hasMyChess(chess, p,chessColor) && HelpFunction.calDistance(locP, p) == 1){
							moveSet.add(p);
						}
					}
				}
				// 王衝王
				if(chess[0].getChessLoc().x == locX && HelpFunction.calObstacle(chess, c, chess[0].getChessLoc()) == 0){
					 moveSet.add(new Point(chess[0].getChessLoc().x,chess[0].getChessLoc().y));
				}
				break;
			// 紅仕
			case 2:
			case 3:
				if(!HelpFunction.hasMyChess(chess, new Point(3,9), chessColor) && HelpFunction.calDistance(locP, new Point(3,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,9));
				if(!HelpFunction.hasMyChess(chess, new Point(3,7), chessColor) && HelpFunction.calDistance(locP, new Point(3,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,7));
				if(!HelpFunction.hasMyChess(chess, new Point(4,8), chessColor) && HelpFunction.calDistance(locP, new Point(4,8)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(4,8));
				if(!HelpFunction.hasMyChess(chess, new Point(5,9), chessColor) && HelpFunction.calDistance(locP, new Point(5,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,9));
				if(!HelpFunction.hasMyChess(chess, new Point(5,7), chessColor) && HelpFunction.calDistance(locP, new Point(5,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,7));
				break;
			// 黑士
			case 18:
			case 19:
				if(!HelpFunction.hasMyChess(chess, new Point(3,0), chessColor) && HelpFunction.calDistance(locP, new Point(3,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,0));
				if(!HelpFunction.hasMyChess(chess, new Point(3,2), chessColor) && HelpFunction.calDistance(locP, new Point(3,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(3,2));
				if(!HelpFunction.hasMyChess(chess, new Point(4,1), chessColor) && HelpFunction.calDistance(locP, new Point(4,1)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(4,1));
				if(!HelpFunction.hasMyChess(chess, new Point(5,0), chessColor) && HelpFunction.calDistance(locP, new Point(5,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,0));
				if(!HelpFunction.hasMyChess(chess, new Point(5,2), chessColor) && HelpFunction.calDistance(locP, new Point(5,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(1, 1))) moveSet.add(new Point(5,2));
				break;
			// 紅象
			case 4:
			case 5:
				if(!HelpFunction.hasMyChess(chess, new Point(0,7), chessColor) && HelpFunction.calDistance(locP, new Point(0,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(0,7)) == 0) moveSet.add(new Point(0,7));
				if(!HelpFunction.hasMyChess(chess, new Point(2,5), chessColor) && HelpFunction.calDistance(locP, new Point(2,5)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,5)) == 0) moveSet.add(new Point(2,5));
				if(!HelpFunction.hasMyChess(chess, new Point(2,9), chessColor) && HelpFunction.calDistance(locP, new Point(2,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,9)) == 0) moveSet.add(new Point(2,9));
				if(!HelpFunction.hasMyChess(chess, new Point(4,7), chessColor) && HelpFunction.calDistance(locP, new Point(4,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(4,7)) == 0) moveSet.add(new Point(4,7));
				if(!HelpFunction.hasMyChess(chess, new Point(6,5), chessColor) && HelpFunction.calDistance(locP, new Point(6,5)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,5)) == 0) moveSet.add(new Point(6,5));
				if(!HelpFunction.hasMyChess(chess, new Point(6,9), chessColor) && HelpFunction.calDistance(locP, new Point(6,9)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,9)) == 0) moveSet.add(new Point(6,9));
				if(!HelpFunction.hasMyChess(chess, new Point(8,7), chessColor) && HelpFunction.calDistance(locP, new Point(8,7)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(8,7)) == 0) moveSet.add(new Point(8,7));
				break;
			// 黑象
			case 20:
			case 21:
				if(!HelpFunction.hasMyChess(chess, new Point(0,2), chessColor) && HelpFunction.calDistance(locP, new Point(0,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(0,2)) == 0) moveSet.add(new Point(0,2));
				if(!HelpFunction.hasMyChess(chess, new Point(2,0), chessColor) && HelpFunction.calDistance(locP, new Point(2,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,0)) == 0) moveSet.add(new Point(2,0));
				if(!HelpFunction.hasMyChess(chess, new Point(2,4), chessColor) && HelpFunction.calDistance(locP, new Point(2,4)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(2,4)) == 0) moveSet.add(new Point(2,4));
				if(!HelpFunction.hasMyChess(chess, new Point(4,2), chessColor) && HelpFunction.calDistance(locP, new Point(4,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(4,2)) == 0) moveSet.add(new Point(4,2));
				if(!HelpFunction.hasMyChess(chess, new Point(6,0), chessColor) && HelpFunction.calDistance(locP, new Point(6,0)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,0)) == 0) moveSet.add(new Point(6,0));
				if(!HelpFunction.hasMyChess(chess, new Point(6,4), chessColor) && HelpFunction.calDistance(locP, new Point(6,4)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(6,4)) == 0) moveSet.add(new Point(6,4));
				if(!HelpFunction.hasMyChess(chess, new Point(8,2), chessColor) && HelpFunction.calDistance(locP, new Point(8,2)) == HelpFunction.calDistance(new Point(0, 0), new Point(2, 2)) && HelpFunction.calObstacleForElephant(chess, c, new Point(8,2)) == 0) moveSet.add(new Point(8,2));
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
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != chessColor){
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
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != chessColor){
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
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != chessColor){
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
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != chessColor){
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
					if(locY-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-2,locY-1), chessColor)) moveSet.add(new Point(locX-2,locY-1));
					// 左下, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locY+1 <=9 && !HelpFunction.hasMyChess(chess, new Point(locX-2,locY+1), chessColor)) moveSet.add(new Point(locX-2,locY+1));
				}
				// 往右, 沒有超出邊界＆沒有卡馬腳
				if(locX+2 <=8 && !HelpFunction.hasChess(chess, new Point(locX+1,locY))){
					// 右上, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locY-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX+2,locY-1), chessColor)) moveSet.add(new Point(locX+2,locY-1));
					// 右下, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locY+1 <=9 && !HelpFunction.hasMyChess(chess, new Point(locX+2,locY+1), chessColor)) moveSet.add(new Point(locX+2,locY+1));
				}
				// 往上, 沒有超出邊界＆沒有卡馬腳
				if(locY-2 >=0 && !HelpFunction.hasChess(chess, new Point(locX,locY-1))){
					// 上左, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY-2), chessColor)) moveSet.add(new Point(locX-1,locY-2));
					// 上右, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY-2), chessColor)) moveSet.add(new Point(locX+1,locY-2));
				}
				// 往下, 沒有超出邊界＆沒有卡馬腳
				if(locY+2 <=9 && !HelpFunction.hasChess(chess, new Point(locX,locY+1))){
					// 下左, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY+2), chessColor)) moveSet.add(new Point(locX-1,locY+2));
					// 上右, 沒有超出邊界, 該點棋子不是我方的（可以吃）
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY+2), chessColor)) moveSet.add(new Point(locX+1,locY+2));
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
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != chessColor){
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
						if(HelpFunction.getChess(chess, new Point(i,locY)).getChessSide() != chessColor){
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
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != chessColor){
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
						if(HelpFunction.getChess(chess, new Point(locX,i)).getChessSide() != chessColor){
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
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY), chessColor)){
						moveSet.add(new Point(locX-1,locY));
					}
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY), chessColor)){
						moveSet.add(new Point(locX+1,locY));
					}
					if(locY-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX,locY-1), chessColor)){
						moveSet.add(new Point(locX,locY-1));
					}
				// 沒有過河、只能往前
				}else{
					if(!HelpFunction.hasMyChess(chess, new Point(locX,locY-1), chessColor)){
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
					if(locX-1 >=0 && !HelpFunction.hasMyChess(chess, new Point(locX-1,locY), chessColor)){
						moveSet.add(new Point(locX-1,locY));
					}
					if(locX+1 <=8 && !HelpFunction.hasMyChess(chess, new Point(locX+1,locY), chessColor)){
						moveSet.add(new Point(locX+1,locY));
					}
					if(locY+1 <=9 && !HelpFunction.hasMyChess(chess, new Point(locX,locY+1), chessColor)){
						moveSet.add(new Point(locX,locY+1));
					}
				// 沒有過河、只能往前
				}else{
					if(!HelpFunction.hasMyChess(chess, new Point(locX,locY+1), chessColor)){
						moveSet.add(new Point(locX,locY+1));
					}
				}
				break;
		}
		
		return moveSet;
	}
	
	/*
	 * 思考：
	 * 1. 抓取所有目前存活棋子
	 * 2. 迴圈：選擇棋子，開始遍歷深度(3)
	 * 3. 計算走每一步的分數
	 * 4. 記錄棋局，換手，繼續往下
	 */
	
	/* 
	 * min-max 演算法，回傳total計算分數
	 */
	private MyMove MinMax(int depth){
		
		ArrayList<Chess> liveChessRound1 = this.getAllMyLiveChess(this.chess);
		int max = -1 ;
		Chess maxMoveChess = null;
		Point maxMovePoint = null;
		
		int maxP1 = -1 ;
		int maxP2 = -1 ;
		int maxP3 = -1 ;
		
		// 1.彈性層數
		// 2.AI強化
		// 3.釋出部份可用method
		
		/* 每一顆活著的棋子 */
		for(int i=0;i<liveChessRound1.size();i++){
			/* 可走的每一步round1 */
			ArrayList<Point> moveSetRound1 = this.getMoveSet(chess, liveChessRound1.get(i), this.myChessSide);
			/* 開始遍歷可走的每一步round1 */
			for(int j=0;j<moveSetRound1.size();j++){
				/* 取得這一步棋的分數round1 */
				int pointRound1 = calculatePoint(chess, liveChessRound1.get(i),moveSetRound1.get(j),this.myChessSide);
				/* 如果可以直接吃將帥，則直接吃棋，結束計算 */
				if(pointRound1 == 10000){
					System.out.println(pointRound1);
					return new MyMove(liveChessRound1.get(i),moveSetRound1.get(j));
				}
				/* 模擬移動round1 */
				Chess[] chessRound2 = this.simulateMove(this.getAllCopyChess(this.chess), liveChessRound1.get(i), moveSetRound1.get(j));
				/* 第二層遍歷，取得活著的對方棋子 */
				ArrayList<Chess> liveChessRound2 = this.getAllOpponentLiveChess(chessRound2);
				/* 開始遍歷可走的每一步round2 */
				for(int k=0;k<liveChessRound2.size();k++){
					/* 可走的每一步round2 */
					ArrayList<Point> moveSetRound2 = this.getMoveSet(chessRound2, liveChessRound2.get(k), this.opponentSide);
					/* 開始遍歷可走的每一步round2 */
					for(int l=0;l<moveSetRound2.size();l++){
						/* 取得這一步棋的分數round2 */
						int pointRound2 = calculatePoint(chessRound2, liveChessRound2.get(k),moveSetRound2.get(l), this.opponentSide) * (-1);
						/* 模擬移動round2 */
						Chess[] chessRound3 = this.simulateMove(this.getAllCopyChess(chessRound2), liveChessRound2.get(k), moveSetRound2.get(l));
						/* 第三層遍歷，取得活著的我方棋子 */
						ArrayList<Chess> liveChessRound3 = this.getAllMyLiveChess(chessRound3);
						for(int s=0;s<liveChessRound3.size();s++){
							/* 可走的每一步round3 */
							ArrayList<Point> moveSetRound3 = this.getMoveSet(chessRound3, liveChessRound3.get(s), this.myChessSide);
							/* 開始遍歷可走的每一步round3 */
							for(int t=0;t<moveSetRound3.size();t++){
								/* 取得這一步棋的分數round3 */
								int pointRound3 = calculatePoint(chessRound3, liveChessRound3.get(s),moveSetRound3.get(t), this.myChessSide);
								
								// 收官
								if(max < pointRound1 + pointRound2 + pointRound3){
									max = pointRound1 + pointRound2 + pointRound3;
									maxMoveChess = liveChessRound1.get(i);
									maxMovePoint = moveSetRound1.get(j);
									
									maxP1 = pointRound1;
									maxP2 = pointRound2;
									maxP3 = pointRound3;
								}
								
							}
						}
					}
				}
			}
		}
		System.out.println(maxP1+"___"+maxP2+"___"+maxP3);
		
		return new MyMove(maxMoveChess,maxMovePoint);
	}	
	
	

}
