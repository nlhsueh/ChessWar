package ChessGame;

import java.awt.Color;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import AI.AbstractPlayer;
import AI.BuildInAI;
import AI.communicateObserver;
import DevelopPattern.DevelopGameSetting;
import FrameView.MainFrame;
import NormalPattern.NormalGameSetting;
import ObserverData.ChangeTurnData;
import ObserverData.MyMove;
import ObserverData.GameOverData;
import ObserverData.LeaveGameData;
import ObserverData.OpponentMove;
import Save.RecordGame;
import Share.GameSetting;

public class GameModel extends Observable implements Observer{
	
	/* 基本參數 */
	private Chess[] chess = new Chess[32];
	private ChessSide turn = ChessSide.RED; // 1 = red , 0 = false
	private ChessMap chessMap ;
	private GameSetting gameSetting ;
	private RecordGame recordGame ;
	private ChessRule chessRule ;
	private boolean gameStart = false ;
	private boolean pauseStatus = false ;
	private enum ChessColorPosition {
		RedDownBlackUp,RedUpBlackDown
	} 
	private ChessColorPosition colorPosition = ChessColorPosition.RedDownBlackUp;
	
	private Chess previousMoveBorder = null;
	private Chess previousMoveChess  = null;
	private JLayeredPane chessPanel ;
	
	/* 紀錄P1.P2類型、顏色 */
	private int player1Type ;
	private int player2Type ;
	private ChessSide player1Color;
	private ChessSide player2Color;
	/* 預留AI參數 */
	private AbstractPlayer player1AI ;
	private AbstractPlayer player2AI ;
	private communicateObserver obs1 ;
	private communicateObserver obs2 ;
	
	public GameModel(GameSetting gameSetting, Chess[] chess, ChessMap chessMap, JLayeredPane chessPanel){
		
		this.setChess(chess);
		this.setChessMap(chessMap);
		this.chessPanel = chessPanel;
		
		this.player1Type = gameSetting.player1 ;
		this.player2Type = gameSetting.player2 ;
		this.player1Color = ChessSide.RED;
		this.player2Color = ChessSide.BLACK;
		
		if(gameSetting instanceof NormalGameSetting){
			this.gameSetting = new NormalGameSetting(gameSetting);
			this.checkAI_Normal();
		}else if(gameSetting instanceof DevelopGameSetting){
			this.gameSetting = new DevelopGameSetting(gameSetting);
			this.checkAI_Develop();
		}
		
	}
	
	public void changeSide(){
		
		this.gameSetting = this.gameSetting.getReverseSetting();
		this.player1Type = this.gameSetting.player1 ;
		this.player2Type = this.gameSetting.player2 ;
		this.player1Color = ChessSide.RED;
		this.player2Color = ChessSide.BLACK;
		this.previousMoveBorder = null;
		this.previousMoveChess = null;
		
	}
	
	public void gameStart(){
		if(this.player1Type == 1 || this.player2Type == 1){
			new GameController(this); /* 有Human Beings才需要Controller */
		}
		this.chessRule = new ChessRule(this.getAllChess(), this);
		
		if(gameSetting instanceof NormalGameSetting){
			this.recordGame = new RecordGame(new NormalGameSetting(this.gameSetting));
		}else if(gameSetting instanceof DevelopGameSetting){
			this.recordGame = new RecordGame(new DevelopGameSetting(this.gameSetting));
		}
		
		this.turn = ChessSide.RED;
		this.gameStart = true ;
	}
	
	public boolean isGameStart(){
		if(this.gameStart)
			return true;
		else
			return false;
	}
	
	public GameSetting getGameSetting(){
		return this.gameSetting;
	}
	
	public ChessRule getChessRule(){
		return this.chessRule;
	}
	
	public JLayeredPane getChessPanel(){
		return this.chessPanel;
	}
	
	public ChessColorPosition getChessColorPosition(){
		return this.colorPosition;
	}
	
	/**
	 * 對弈模式:判斷Player1與Player2是否為AI
	 * 若是AI則建立AI類別，並使AI類別與Game Modal相互觀察傳遞遊戲資訊
	 */
	public void checkAI_Normal(){
		if(this.player1Type == 2){
			this.player1AI = new BuildInAI(this.copyAllChess(),player1Color);
			this.obs1 = new communicateObserver(this.player1AI, player1Color);
			this.addObserver(obs1);
			obs1.addObserver(this);
		}else if(this.player1Type == 3){
			/* 取得外部AI類別之Class */
			try{
				String fileName = this.gameSetting.externalAIName1;
				/* 資料夾路徑 */
				String path = "file:./AI/"+fileName+"/";
				URL url1 = new URL(path);
				URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1});
				urlClassLoader.loadClass(fileName);
				Class<?> c = Class.forName(fileName,true,urlClassLoader);
				
				/* 建立建構子參數 */
				Class<?>[] params = new Class[2];
	            params[0] = Chess[].class;
	            params[1] = ChessSide.class;
	            Constructor<?> constructor = c.getConstructor(params);
				AbstractPlayer a = (AbstractPlayer) constructor.newInstance(this.copyAllChess(),player1Color);
				/* 設定類別參數與藉由委託實作Observer */
				this.player1AI = a ;
				this.obs1 = new communicateObserver(this.player1AI, player1Color);
				this.addObserver(obs1);
				obs1.addObserver(this);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			}
		}
		if(this.player2Type == 2){
			this.player2AI = new BuildInAI(this.copyAllChess(),player2Color);
			this.obs2 = new communicateObserver(this.player2AI, player2Color);
			this.addObserver(obs2);
			obs2.addObserver(this);
		}else if(this.player2Type == 3){
			try {
				/* 取得外部AI類別之Class */
				String fileName = this.gameSetting.externalAIName2;
				/* 資料夾路徑 */
				String path = "file:./AI/"+fileName+"/";
				
				URL url1 = new URL(path);
				URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1});
				urlClassLoader.loadClass(fileName);
				Class<?> c = Class.forName(fileName,true,urlClassLoader);
				
				/* 建立建構子參數 */
				Class<?>[] params = new Class[2];
	            params[0] = Chess[].class;
	            params[1] = ChessSide.class;
	            Constructor<?> constructor = c.getConstructor(params);
				AbstractPlayer a = (AbstractPlayer) constructor.newInstance(this.copyAllChess(),player2Color);
				/* 設定類別參數與藉由委託實作Observer */
				this.player2AI = a ;
				this.obs2 = new communicateObserver(this.player2AI, player2Color);
				this.addObserver(obs2);
				obs2.addObserver(this);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 開發模式:判斷Player1與Player2是否為AI
	 * 若是AI則建立AI類別，並使AI類別與Game Modal相互觀察傳遞遊戲資訊
	 */
	public void checkAI_Develop(){
		if(this.player1Type == 2){
			this.player1AI = new BuildInAI(this.copyAllChess(),player1Color);
			this.obs1 = new communicateObserver(this.player1AI, player1Color);
			this.addObserver(obs1);
			obs1.addObserver(this);
		}else if(this.player1Type == 3){
			
			try {
				/* 取得外部AI類別之Class */
				String fileName = "Develop.DevelopAI";
				/* 資料夾路徑 */
				String path = "file:./bin/";
				
				URL url1 = new URL(path);
				URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1});
				urlClassLoader.loadClass(fileName);
				Class<?> c = Class.forName(fileName,true,urlClassLoader);
				
				/* 建立建構子參數 */
				Class<?>[] params = new Class[2];
	            params[0] = Chess[].class;
	            params[1] = ChessSide.class;
	            Constructor<?> constructor = c.getConstructor(params);
				AbstractPlayer a = (AbstractPlayer) constructor.newInstance(this.copyAllChess(),player1Color);
				/* 設定類別參數與藉由委託實作Observer */
				this.player1AI = a ;
				this.obs1 = new communicateObserver(this.player1AI, player1Color);
				this.addObserver(obs1);
				obs1.addObserver(this);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			}
			
		}
		if(this.player2Type == 2){
			this.player2AI = new BuildInAI(this.copyAllChess(),player2Color);
			this.obs2 = new communicateObserver(this.player2AI, player2Color);
			this.addObserver(obs2);
			obs2.addObserver(this);
		}else if(this.player2Type == 3){
			
			try {
				/* 取得外部AI類別之Class */
				String fileName = "Develop.DevelopAI";
				/* 資料夾路徑 */
				String path = "file:./bin/";
				
				URL url1 = new URL(path);
				URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1});
				urlClassLoader.loadClass(fileName);
				Class<?> c = Class.forName(fileName,true,urlClassLoader);
				
				/* 建立建構子參數 */
				Class<?>[] params = new Class[2];
	            params[0] = Chess[].class;
	            params[1] = ChessSide.class;
	            Constructor<?> constructor = c.getConstructor(params);
				AbstractPlayer a = (AbstractPlayer) constructor.newInstance(this.copyAllChess(),player2Color);
				/* 設定類別參數與藉由委託實作Observer */
				this.player2AI = a ;
				this.obs2 = new communicateObserver(this.player2AI, player2Color);
				this.addObserver(obs2);
				obs2.addObserver(this);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(new JPanel(), "GameModel 錯誤代碼："+e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 顛倒棋盤<br>
	 * 只更改視覺上的View和ChessColorPosition變數
	 */
	public void inverseChessBoard(){
		/* 視覺顛倒 */
		for(int i=0;i<32;i++){
			if(chess[i].getChessLoc().x != -1){
				if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
					chess[i].setLocation(this.chessMap.getChessLoc(new Point(chess[i].getChessLoc().x,chess[i].getChessLoc().y)));
				}else if(this.colorPosition == ChessColorPosition.RedDownBlackUp){
					chess[i].setLocation(this.chessMap.getChessLoc(new Point(chess[i].getChessLoc().x,9-chess[i].getChessLoc().y)));
				}
			}
		}
		if(this.colorPosition == ChessColorPosition.RedDownBlackUp){
			this.colorPosition = ChessColorPosition.RedUpBlackDown;
		}else if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
			this.colorPosition = ChessColorPosition.RedDownBlackUp;
		}
	}
	
	/**
	 * 如果棋子沒有死亡，檢查是否顛倒後，依照傳入之p更改其view位置
	 * @param c 欲修改位置之棋子
	 * @param p 欲移動到之位置
	 */
	public void changeChessViewPosition(Chess c,Point p){
		/* 顛倒狀態做視覺顛倒 */
		if(c.getChessLoc().x != -1){
			if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
				c.setLocation(this.chessMap.getChessLoc(new Point(p.x,9-p.y)));
			}else if(this.colorPosition == ChessColorPosition.RedDownBlackUp){
				c.setLocation(this.chessMap.getChessLoc(p));
			}
		}
		/* 立即重晝 */
		c.paintImmediately(c.getVisibleRect());
		this.chessPanel.paintImmediately(this.chessPanel.getVisibleRect());
	}
	
	/**
	 * 回傳目前是否有顛倒棋盤
	 * @return boolean 目前是否顛倒棋盤(紅上黑下)
	 */
	public boolean isInverse(){
		if(this.colorPosition == ChessColorPosition.RedUpBlackDown){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 重置棋盤顛倒變數為預設
	 */
	public void resetInverse(){
		this.colorPosition = ChessColorPosition.RedDownBlackUp;
	}
	
	/**
	 * 判斷遊戲是否已經結束（藉由判斷雙方將、帥是否仍然存活）
	 * @return boolean
	 */
	public boolean isGameOver() {
		if ((chess[0].isAlive() == false) || (chess[16].isAlive() == false)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判斷遊戲勝利者是誰，並回傳勝利顏色字串
	 * @return String "Black" or "Red"
	 */
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
		
		int winner = -1 ; // 1=plaer1 , 2=player2
		String gameOverText = "" ;
		
		if(this.getWhoWin().equals("Black")){
			gameOverText = "遊戲結束！"+this.gameSetting.nickname2+" 勝利!";
			winner = 2 ;
		}else{
			gameOverText = "遊戲結束！"+this.gameSetting.nickname1+" 勝利!";
			winner = 1 ;
		}
		
		this.gameStart = false;
		this.deleteObserver(obs1);
		this.deleteObserver(obs2);
		this.player1AI = null;
		this.player2AI = null;
		
		for(int i=0;i<32;i++){
			this.deleteObserver(chess[i]);
		}
		
		setChanged();
		notifyObservers(new GameOverData(winner,false,this.recordGame));
		
		JOptionPane.showMessageDialog(new JPanel(), gameOverText, "遊戲結束", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
	}
	
	/**
	 * 當點擊離開遊戲
	 */
	public void leaveGame() {
		setChanged();
		notifyObservers(new LeaveGameData());
	}
	
	/**
	 * 當點擊遊戲暫停，修改pauseStatus
	 */
	public void changePauseStatus(){
		if(this.pauseStatus == true){
			this.pauseStatus = false;
		}else{
			this.pauseStatus = true;
		}
	}
	
	/**
	 * 取得pauseStatus
	 */
	public boolean getPauseStatus(){
		if(this.pauseStatus == true){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 取得Player1的color
	 */
	public ChessSide getPlayer1Color(){
		if(this.player1Color == ChessSide.RED){
			return ChessSide.RED;
		}else{
			return ChessSide.BLACK;
		}
	}
	
	/**
	 * 取得Player2的color
	 */
	public ChessSide getPlayer2Color(){
		if(this.player2Color == ChessSide.RED){
			return ChessSide.RED;
		}else{
			return ChessSide.BLACK;
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
	 * 取得當前玩家，true為紅棋、false為黑棋
	 */
	public ChessSide getTurn(){
		return this.turn ;
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
	
	public void timeoutGameOver(){
		String chessColor ;
		String player ;
		if(this.getTurn() == ChessSide.RED){
			chessColor = "紅棋" ;
			if(this.player1Color == ChessSide.RED){
				player = "紅棋" ;
			}else{
				player = "黑棋" ;
			}
		}else{
			chessColor = "黑棋" ;
			if(this.player1Color == ChessSide.BLACK){
				player = "紅棋" ;
			}else{
				player = "黑棋" ;
			}
		}
		this.saveMove(this.getTurn(), "timeout");
		this.letGeneralDieForBreak(this.getTurn());
		JOptionPane.showMessageDialog(new JPanel(), player+" 的 "+chessColor+" 超過思考時間！\n根據規則視為認輸，遊戲結束！", "遊戲結束", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
		this.gameOver();
	}
	
	public void notifyAI(int index,Point oldPos, Point newPos){
		// 如果有AI, 通知AI
		if(this.player1Type != 1 || this.player2Type != 1){
			setChanged();
			notifyObservers(new OpponentMove(this.copyAllChess(),this.turn,index,oldPos,newPos));
		}
	}
	
	public void notifyAIFirstMove(){
		// 如果第一步為AI, 通知AI
		if( (this.player1Type != 1) || (this.player2Type != 1) ){
			setChanged();
			notifyObservers(new OpponentMove(this.copyAllChess(),this.turn,-1, new Point(-1,-1), new Point(-1, -1)));
		}
	}
	
	public Chess[] copyAllChess(){
		Chess[] chess = new Chess[32]; 
		System.arraycopy(this.chess, 0, chess, 0, 32);
		return chess;
	}
	
	public void setChessMap(ChessMap map){
		this.chessMap = map ;
	}
	
	public void setChessPanel(JLayeredPane chessPanel){
		this.chessPanel = chessPanel;
	}
	
	public void setChess(Chess[] chess){
		for(int i=0;i<32;i++){
			this.chess[i] = chess[i];
			this.addObserver(this.chess[i]);
		}
	}
	
	public Chess[] getAllChess(){
		return this.chess;
	}
	
	public ChessMap getChessMap(){
		return this.chessMap;
	}
	
	public Chess getChess(int i){
		return this.chess[i];
	}
	
	public int getPlayer1Type(){
		return this.player1Type;
	}
	
	public int getPlayer2Type(){
		return this.player2Type;
	}
	
	
	/**
	 * 移動軌跡：移除上一手之border
	 */
	public void removePreviousMoveBorder(){
		
		/* 檢查是否為第一手，若非第一手，移除邊框 */
		if(this.previousMoveBorder != null && this.previousMoveChess != null){
			this.previousMoveChess.setBorder(null);
			this.chessPanel.remove(this.previousMoveBorder);
			/* 立即重晝 */
			previousMoveChess.paintImmediately(previousMoveChess.getVisibleRect());
			this.chessPanel.paintImmediately(this.chessPanel.getVisibleRect());
		}
		
	}
	
	/**
	 * 移動軌跡：原本位置新增虛線border，表示移動軌跡
	 */
	public void addPreviousMoveBorder(Point oldPos){
		
		this.previousMoveBorder = new Chess(oldPos);
		changeChessViewPosition(previousMoveBorder,oldPos);
		chessPanel.add(previousMoveBorder,  new Integer(401));
		
		/* 立即重晝 */
		previousMoveBorder.paintImmediately(previousMoveBorder.getVisibleRect());
		this.chessPanel.paintImmediately(this.chessPanel.getVisibleRect());
	}
	
	/**
	 * 移動軌跡：移動後位置新增實現border，表示移動軌跡
	 */
	public void addThisMoveBorder(Chess c){
		
		this.previousMoveChess = c ;
		c.setBorder(new LineBorder(Color.BLUE,2));
		
		/* 立即重晝 */
		previousMoveChess.paintImmediately(previousMoveChess.getVisibleRect());
		this.chessPanel.paintImmediately(this.chessPanel.getVisibleRect());
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
			
			/* 如果選擇棋子的顏色為當前玩家 && 遊戲未結束 && 不是暫停狀態 && 遊戲已經開始 */
			if (c.getChessSide() == this.getTurn() && !this.isGameOver() && !this.pauseStatus) {
				/* 若該座標在棋盤內，且符合該棋子移動規則 */
				if ((p != null) && this.chessRule.isCouldMove(c, p)) {
					
					/* 合法移動，移除上一手移動軌跡 */
					this.removePreviousMoveBorder();
					
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
							/* 新增移動軌跡 */
							this.addPreviousMoveBorder(oldPos);
							this.addThisMoveBorder(c);
							/* 換手 */
							this.changeTurn();	
							/* 移動結束，判斷遊戲是否結束 */
							if (this.isGameOver()) {
								this.gameOver(); // System.out.println("GameOver!");
							}
							// 如果對方是AI，則通知AI
							if(this.getTurn() == this.player1Color && this.player1Type != 1 || this.getTurn() == this.player2Color && this.player2Type != 1){
								this.notifyAI(c.getChessIndex(), new Point(oldPos.x,oldPos.y), new Point(p.x,p.y));
							}
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
						/* 新增移動軌跡 */
						this.addPreviousMoveBorder(oldPos);
						this.addThisMoveBorder(c);
						/* 換手 */
						this.changeTurn();
						/* 移動結束，判斷遊戲是否結束 */
						if (this.isGameOver()) {
							this.gameOver(); // System.out.println("GameOver!");
						}
						
						// 如果對方是AI，則通知AI
						if(this.getTurn() == player1Color && this.player1Type != 1 || this.getTurn() == player2Color && this.player2Type != 1){
							this.notifyAI(c.getChessIndex(), new Point(oldPos.x,oldPos.y), new Point(p.x,p.y));
						}
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
