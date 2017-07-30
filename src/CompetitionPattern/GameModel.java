package CompetitionPattern;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import AI.AbstractPlayer;
import AI.communicateObserver;
import ChessGame.Chess;
import ChessGame.ChessBoard;
import ChessGame.ChessMap;
import ChessGame.ChessSide;
import FrameView.MainFrame;
import NormalPattern.NormalGameSetting;
import ObserverData.ChangeTurnData;
import ObserverData.GameOverData;
import ObserverData.MyMove;
import ObserverData.OpponentMove;
import Save.RecordGame;

public class GameModel extends Observable implements Observer{
	
	private GameInfo gameInfo;
	private AIInfo player1 ;
	private AIInfo player2 ;
	
	private ChessSide turn = ChessSide.RED; // 1 = red , 0 = false
	
	private Chess[] chess = new Chess[32];
	private ChessMap chessMap ;
	private ChessSide player1Color = ChessSide.RED;
	private ChessSide player2Color = ChessSide.BLACK;
	
	private RecordGame recordGame ;
	private ChessRule chessRule ;

	/* AI參數 */
	private AbstractPlayer player1AI ;
	private AbstractPlayer player2AI ;
	private communicateObserver obs1 ;
	private communicateObserver obs2 ;
	
	public GameModel(GameInfo gameInfo, int timeout){
		this.gameInfo = gameInfo;
		this.player1 = gameInfo.getPlayer1();
		this.player2 = gameInfo.getPlayer2();
		this.player1AI = player1.AIClass;
		this.player2AI = player2.AIClass;
		
		ChessBoard chessBoard = new ChessBoard();
		this.setChess(chessBoard.getAllChess());
		this.chessMap = chessBoard.getChessMap();
		
		this.setAI();
		this.chessRule = new ChessRule(this.getAllChess(), this);
		
		NormalGameSetting gameSetting = new NormalGameSetting() ;
		gameSetting.player1 = 3;
		gameSetting.player2 = 3;
		gameSetting.timeout = timeout;
		gameSetting.externalAIName1 = this.player1.getAIName();
		gameSetting.externalAIName2 = this.player2.getAIName();
		gameSetting.nickname1 = this.player1.getTeamName();
		gameSetting.nickname2 = this.player2.getTeamName();
		this.recordGame = new RecordGame(new NormalGameSetting(gameSetting));
		
		this.turn = ChessSide.RED;
	}
	
	public void gameStart(){
		/* notifyAIFirstMove */
		setChanged();
		notifyObservers(new OpponentMove(this.copyAllChess(),this.turn,-1, new Point(-1,-1), new Point(-1, -1)));
	}
	
	public void notifyAI(int index,Point oldPos, Point newPos){
		setChanged();
		notifyObservers(new OpponentMove(this.copyAllChess(),this.turn,index,oldPos,newPos));
	}
	
	private void setAI(){
		
		Class<?> c = this.player1AI.getClass();
		Class<?>[] params = new Class[2];
		params[0] = Chess[].class;
        params[1] = ChessSide.class;
        Constructor<?> constructor;
		try {
			constructor = c.getConstructor(params);
			this.player1AI = (AbstractPlayer) constructor.newInstance(this.copyAllChess(), player1Color);
			this.obs1 = new communicateObserver(this.player1AI, player1Color);
			this.addObserver(obs1);
			obs1.addObserver(this);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Class<?> c2 = this.player1AI.getClass();
        Constructor<?> constructor2;
		try {
			constructor2 = c2.getConstructor(params);
			this.player2AI = (AbstractPlayer) constructor2.newInstance(this.copyAllChess(), player2Color);
			this.obs2 = new communicateObserver(this.player2AI, player2Color);
			this.addObserver(obs2);
			obs2.addObserver(this);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getWhoWin(){
		if ( chess[0].isAlive() == false ){
			return "Black" ;
		}else if( chess[16].isAlive() == false ){
			return "Red" ;
		}else{
			return "Error";
		}
	}
	
	/**
	 * 當遊戲結束，設定遊戲參數，並產生對應訊息
	 */
	public void gameOver() {
		
		if(this.getWhoWin().equals("Black")){
			this.gameInfo.setGameOver(2);
		}else{
			this.gameInfo.setGameOver(1);
		}
		
		this.deleteObserver(obs1);
		this.deleteObserver(obs2);
		this.player1AI = null;
		this.player2AI = null;
		for(int i=0;i<32;i++){
			this.deleteObserver(chess[i]);
		}
		
		setChanged();
		notifyObservers(new GameOverData(this.gameInfo,this.recordGame));
	}
	
	public Chess[] copyAllChess(){
		Chess[] chess = new Chess[32]; 
		System.arraycopy(this.chess, 0, chess, 0, 32);
		return chess;
	}
	
	public Chess[] getAllChess(){
		return this.chess;
	}
	
	public Chess getChess(int i){
		return this.chess[i];
	}
	
	public void setChess(Chess[] chess){
		for(int i=0;i<32;i++){
			this.chess[i] = chess[i];
			this.addObserver(this.chess[i]);
		}
	}
	
	/**
	 * 取得當前玩家，true為紅棋、false為黑棋
	 */
	public ChessSide getTurn(){
		return this.turn ;
	}
	
	public boolean isGameOver() {
		if ((chess[0].isAlive() == false) || (chess[16].isAlive() == false)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 更換當前玩家
	 */
	public void changeTurn(){
		if (this.turn == ChessSide.RED) {
			this.turn = ChessSide.BLACK;
		} else {
			this.turn = ChessSide.RED;
		}
		// 計時器更換、記錄上一手
		setChanged();
		notifyObservers(new ChangeTurnData(this.turn));
	}
	
	/**
	 * AI違規或timeout，將將帥致死
	 */
	public void letGeneralDieForBreak(ChessSide side){
		if(side == ChessSide.RED){
			this.setChanged();
			this.notifyObservers(new Integer(1));
		}else{
			this.setChanged();
			this.notifyObservers(new Integer(17));
		}
	}
	
	/**
	 * 當move與break時Save move
	 */
	public void saveMove(Chess c, Point p, String status){
		this.recordGame.addData(c, p, status);
		
		setChanged();
		notifyObservers(this.recordGame.getLastestData());
	}
	
	/**
	 * 當eat時Save move
	 */
	public void saveMove(Chess c, Point p, String status, int beEatedChessIndex){
		this.recordGame.addData(c, p, "eat",beEatedChessIndex);
		
		setChanged();
		notifyObservers(this.recordGame.getLastestData());
	}
	
	/**
	 * 當timeout時Save move
	 */
	public void saveMove(ChessSide side, String status){
		this.recordGame.addData(side, status);
		
		setChanged();
		notifyObservers(this.recordGame.getLastestData());
	}
	
	/**
	 * 如果棋子沒有死亡，檢查是否顛倒後，依照傳入之p更改其view位置
	 * @param c 欲修改位置之棋子
	 * @param p 欲移動到之位置
	 */
	public void changeChessViewPosition(Chess c,Point p){
		/* 顛倒狀態做視覺顛倒 */
		c.setLocation(this.chessMap.getChessLoc(p));
	}

	/**
	 * AI藉由Observer傳送移動資訊給Controller進行判斷
	 * @param arg 若是從AI傳來的檔案，則進行判斷，若合法則移動並通知Modal，若不合法則發出違規訊息，結束遊戲
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		/* 如果有收到AI的移動資訊 */
		if(arg instanceof MyMove){
			MyMove data = (MyMove) arg;
			
			/* 取得該AI選擇的棋子與移動的點座標 */
			Chess c = this.getChess(data.getChess().getChessIndex()-1);
			Point p = data.getTargetLoc();
			
			/* 如果選擇棋子的顏色為當前玩家 && 遊戲未結束 && 遊戲已經開始 */
			if (c.getChessSide() == this.getTurn() && !this.isGameOver() ) {
				/* 若該座標在棋盤內，且符合該棋子移動規則 */
				if ((p != null) && this.chessRule.isCouldMove(c, p)) {
					
					/* 若該點有棋子 */
					if (this.chessRule.hasChess(p)) {
						/* 若該點棋子顏色與點選棋子不同，則吃棋 */
						if(this.chessRule.getChess(p).getChessSide() != c.getChessSide()){
							/* 進行吃棋動作 */
							int beEatedChessIndex = this.chessRule.getChess(p).getChessIndex();
							this.chessRule.eatChess(p);
							/* 記錄點選棋子原本的位置，並將此棋步儲存紀錄 */
							Point oldPos = c.getChessLoc();
							this.saveMove(c, p, "eat",beEatedChessIndex);
							/* 將點選的棋子移動到新的位置 */
							changeChessViewPosition(c,p);
							this.setChanged();
							this.notifyObservers(new MyMove(c,new Point(p)));
							/* 換手 */
							this.changeTurn();	
							/* 移動結束，判斷遊戲是否結束 */
							if (this.isGameOver()) {
								this.gameOver(); // System.out.println("GameOver!");
							}
							this.notifyAI(c.getChessIndex(), new Point(oldPos.x,oldPos.y), new Point(p.x,p.y));
						/* 吃相同顏色棋子---AI違規 */
						}else{
							// 顏色相同，違反規則 跳出視窗 結束遊戲！
	//							System.out.println("犯規！吃相同顏色棋子");
	//							System.out.println(c.name+" : from "+c.loc.x+","+c.loc.y+" to "+p.x+","+p.y);
							this.saveMove(c, p, "break"); // 儲存此步
							this.changeTurn();
							String breaker ;
							if(c.getChessSide() == player1Color){
								breaker = "Player1" ;
							}else{
								breaker = "Player2" ;
							}
							/* 將犯規者將帥設為死亡 */
							this.letGeneralDieForBreak(c.getChessSide());
							JOptionPane.showMessageDialog(new JPanel(), breaker+" AI違規！吃相同顏色棋子！\n選擇棋子："+c.getName()+"\nfrom "+c.getChessLoc().x+","+c.getChessLoc().y+" to "+p.x+","+p.y+" (棋盤最左上角為0,0)"
									, "犯規", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
							this.gameOver();
						}
					/* 若該點無棋子，則直接移動 */
					}else{
						/* 記錄點選棋子原本的位置，並將此棋步儲存紀錄 */
						Point oldPos = c.getChessLoc();
						this.saveMove(c, p, "move");
						/* 將點選的棋子移動到新的位置 */
						changeChessViewPosition(c,p);
						this.setChanged();
						this.notifyObservers(new MyMove(c,new Point(p)));
						/* 換手 */
						this.changeTurn();
						/* 移動結束，判斷遊戲是否結束 */
						if (this.isGameOver()) {
							this.gameOver(); // System.out.println("GameOver!");
						}
						
						// 如果對方是AI，則通知AI
						this.notifyAI(c.getChessIndex(), new Point(oldPos.x,oldPos.y), new Point(p.x,p.y));
					}
					
				/* 若該座標不在棋盤內，或不符合該棋子移動規則---AI違規 */
				} else {
	//					System.out.println("犯規！不符合移動規則");
	//					System.out.println(c.name+" : from "+c.loc.x+","+c.loc.y+" to "+p.x+","+p.y);
					// 移動不符該子規則，違反規則 跳出視窗 結束遊戲！
					this.saveMove(c, p, "break"); // 儲存此步
					this.changeTurn();
					String breaker ;
					if(c.getChessSide() == player1Color){
						breaker = "Player1" ;
					}else{
						breaker = "Player2" ;
					}
					/* 將犯規者將帥設為死亡 */
					this.letGeneralDieForBreak(c.getChessSide());
					JOptionPane.showMessageDialog(new JPanel(), breaker+" AI違規！不符合移動規則！\n選擇棋子："+c.getChessName()+"\nfrom "+c.getChessLoc().x+","+c.getChessLoc().y+" to "+p.x+","+p.y+" (棋盤最左上角為0,0)"
							, "犯規", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
					this.gameOver();	
				}
			}
		}
	}
	
	public void setChanged(){
		super.setChanged();
	}
	public void clearChanged(){
		super.setChanged();
	}
	
	
}
