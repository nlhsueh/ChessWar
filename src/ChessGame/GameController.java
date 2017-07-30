package ChessGame;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.border.LineBorder;

import AI.HelpFunction;
import ObserverData.MyMove;
import ObserverData.DragData;


/**
 * GameController，負責玩家在遊戲時，對於移動棋子（View）與modal資料的控制
 */

public class GameController extends Observable implements MouseMotionListener, MouseListener{
	
	/* 基本參數 */
	private GameModel model ;
	private ChessMap chessMap ;
	/* 記錄滑鼠移動座標 */
	private int mouse_x; // save mouse point x on button
	private int mouse_y; // save mouse point y on button
	/* 記錄可移動方框 */
	private ArrayList<Chess> moveBorder = new ArrayList<Chess>();
	
	public GameController(GameModel model){
		this.model = model;
		this.chessMap = model.getChessMap() ;
		
		Chess[] chess = this.model.getAllChess();
		for(int i=0;i<32;i++){
			this.addObserver(chess[i]);
			chess[i].addMouseMotionListener(this);
			chess[i].addMouseListener(this);
		}
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
	
	/**
	 * 玩家移動棋子，若為當前玩家拖移自己的棋子，則顯示移動效果
	 * 並以Observer通知Chess做view update
	 * @param e 移動事件MouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		Chess c = (Chess) e.getComponent();
		if (c.getChessSide() == model.getTurn() && !model.isGameOver() && !model.getPauseStatus() && model.isGameStart() ) {
			
			ArrayList<Point> moveSet = this.getMoveSet(model.getAllChess(), c, model.getTurn());
			for(int i=0;i<moveSet.size();i++){
				Chess view = new Chess(moveSet.get(i));
				view.setBorder(new LineBorder(Color.GREEN,2));;
				model.changeChessViewPosition(view, moveSet.get(i));
				model.getChessPanel().add(view,  new Integer(402));
				moveBorder.add(view);
			}
			
			int x = c.getLocation().x + (e.getX() - mouse_x) ;
			int y = c.getLocation().y + (e.getY() - mouse_y) ;
			setChanged();
			notifyObservers(new DragData(c,x,y));
		}
	}
	
	/**
	 * 紀錄滑鼠移動座標
	 * @param e 移動事件MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mouse_x = e.getX();
		mouse_y = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	/**
	 * 玩家拖移棋子，釋放後判斷移動是否符合規則
	 * @param e 移動事件MouseEvent
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		/* 清除邊框 */
		for(int i=0;i<this.moveBorder.size();i++){
			model.getChessPanel().remove(moveBorder.get(i));
		}
		/* 如果滑鼠左鍵釋放，取得棋子 */
		Chess c = (Chess) e.getComponent();
		/* 如果選擇棋子的顏色為當前玩家 && 遊戲未結束 && 不是暫停狀態 && 遊戲已經開始 */
		if ( (c.getChessSide() == model.getTurn()) && (!model.isGameOver()) && (!model.getPauseStatus()) && (model.isGameStart()) ) {
			
			if (e.getButton() == MouseEvent.BUTTON1) {
				
				/* 取得移動後最接近座標位置 */
				Point p = this.chessMap.findNearChessLoc((Chess) e.getComponent());
				ChessRule chessRule = this.model.getChessRule();
				
				// 確認是否顛倒，若顛倒取得顛倒後的p !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				if(model.isInverse()){
					p = new Point(p.x,9-p.y);
				}
				
				/* 若該座標在棋盤內，且符合該棋子移動規則 */
				if ((p != null) && chessRule.isCouldMove(c, p)) {
					
//					選擇棋子、移動資訊
//					System.out.println("hasChess: " + hasChess(p));
//					System.out.println("isCouldMove: " + isCouldMove(c, p));
//					System.out.println("Obstacle: " + calObstacle(c, p));
					
					/* 合法移動，移除上一手移動軌跡 */
					model.removePreviousMoveBorder();
					
					/* 若該點有棋子 */
					if (chessRule.hasChess(p)) {
						/* 若該點棋子顏色與點選棋子不同，則吃棋 */
						if(chessRule.getChess(p).getChessSide() != c.getChessSide()){
							/* 進行吃棋動作 */
							int beEatedChessIndex = chessRule.getChess(p).getChessIndex();
							chessRule.eatChess(p);
							/* 記錄點選棋子原本的位置，並將此棋步儲存紀錄 */
							Point oldPos = c.getChessLoc();
							model.saveMove(c, p, "eat",beEatedChessIndex);
							/* 將點選的棋子移動到新的位置 */
							model.changeChessViewPosition(c,p);
							model.setChanged();
							model.notifyObservers(new MyMove(c,new Point(p)));
							/* 新增移動軌跡 */
							model.addPreviousMoveBorder(oldPos);
							model.addThisMoveBorder(c);
							/* 換手 */
							model.changeTurn();	
							
							// 如果對方是AI，則通知AI 
							if(model.getTurn() == model.getPlayer1Color() && model.getPlayer1Type() != 1 || model.getTurn() == model.getPlayer2Color() && this.model.getPlayer2Type() != 1){
								model.notifyAI(c.getChessIndex(), new Point(oldPos.x,oldPos.y), new Point(p.x,p.y));
							}
							
						/* 若該點棋子顏色與點選棋子相同，則將點選棋子歸位 */
						}else{
							c.setLocation(this.chessMap.getChessLoc(c.getChessLoc()));
						}
					/* 若該點無棋子，則直接移動 */
					}else{
						/* 記錄點選棋子原本的位置，並將此棋步儲存紀錄 */
						Point oldPos = c.getChessLoc();
						model.saveMove(c, p, "move");
						/* 將點選的棋子移動到新的位置 */
						model.changeChessViewPosition(c,p);
						model.setChanged();
						model.notifyObservers(new MyMove(c,new Point(p)));
						/* 新增移動軌跡 */
						model.addPreviousMoveBorder(oldPos);
						model.addThisMoveBorder(c);
						/* 換手 */
						model.changeTurn();
						
						// 如果對方是AI，則通知AI
						if(model.getTurn() == model.getPlayer1Color() && this.model.getPlayer1Type() != 1 || model.getTurn() == model.getPlayer2Color() && this.model.getPlayer2Type() != 1){
							model.notifyAI(c.getChessIndex(), new Point(oldPos.x,oldPos.y), new Point(p.x,p.y));
						}
					}
					
					/* 移動結束，判斷遊戲是否結束 */
					if (model.isGameOver()) {
						model.gameOver(); // System.out.println("GameOver!");
					}
				
				/* 若該座標不在棋盤內，或不符合該棋子移動規則 */
				} else {
					/* 直接歸位 */
//					c.setLocation(this.chessMap.getChessLoc(c.getChessLoc()));
					model.changeChessViewPosition(c,c.getChessLoc()); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				}

			}
		}
	}
	
}
