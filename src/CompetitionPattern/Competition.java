package CompetitionPattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import FrameView.MainFrame;
import ObserverData.GameOverData;
import Share.AbstractSettingView;
import ViewComponent.CButton;
import ViewComponent.CPanel;
import ViewComponent.ChangePage;
import ViewComponent.DrawBackground;
import ViewComponent.Line;
import ViewComponent.LinesComponent;

@SuppressWarnings("serial")
public class Competition extends AbstractSettingView implements Observer{
	
	private ArrayList<GameInfo> scheduleGame;
	private LinesComponent lineComp = new LinesComponent();
	
	public static int playerButtonWidth = 120 ;
	public static int playerButtonHeight = 60 ;
	
	private int firstGameNum ;
	private int timeout;
	
	private CButton btnT1Win;
	private CButton btnT2Win;
	private CButton btnT3Win;
	private CButton btnT4Win;
	private CButton btnB1Win;
	private CButton btnB2Win;
	private CButton btnKWin;
	
	public Competition(ArrayList<GameInfo> scheduleGame, int timeout){
		this.scheduleGame = scheduleGame;
		this.firstGameNum = this.scheduleGame.size();
		
		MainFrame.framePanel.add(this,"CompetitionPlaying");
		/* add button set panel */
		this.setButtons();
		/* add background image */
		this.add(new DrawBackground("playSetting"), new Integer(0));
		/* playing View */
		this.showPlayer();
		/* start Game */
		this.startGame();
	}
	
	private void showPlayer(){
		CPanel cp = new CPanel(50,35,824,600,null);
		this.add(cp,new Integer(1));
		
		/* 晝線container */
		lineComp.setPreferredSize(new Dimension(824,600));
		lineComp.setSize(new Dimension(824,600));
		lineComp.setLocation(50, 35);
		this.add(this.lineComp,new Integer(3));
		
		/* 7 or 8 player */
		if(this.firstGameNum == 4){
			
			/* 後三場 */
			for(int i=4;i<7;i++){
				scheduleGame.add(new GameInfo(null,null,i));
			}
			scheduleGame.get(4).setAnotherGame(scheduleGame.get(5));
			scheduleGame.get(5).setAnotherGame(scheduleGame.get(4));
			scheduleGame.get(0).setNextGame(scheduleGame.get(4));
			scheduleGame.get(1).setNextGame(scheduleGame.get(4));
			scheduleGame.get(2).setNextGame(scheduleGame.get(5));
			scheduleGame.get(3).setNextGame(scheduleGame.get(5));
			scheduleGame.get(4).setNextGame(scheduleGame.get(6));
			scheduleGame.get(5).setNextGame(scheduleGame.get(6));
			
			CButton btn1 = this.scheduleGame.get(0).getPlayer1().getCButton();
			CButton btn2 = this.scheduleGame.get(0).getPlayer2().getCButton();
			CButton btn3 = this.scheduleGame.get(1).getPlayer1().getCButton();
			CButton btn4 = this.scheduleGame.get(1).getPlayer2().getCButton();
			CButton btn5 = this.scheduleGame.get(2).getPlayer1().getCButton();
			CButton btn6 = this.scheduleGame.get(2).getPlayer2().getCButton();
			CButton btn7 = this.scheduleGame.get(3).getPlayer1().getCButton();
			CButton btn8 = this.scheduleGame.get(3).getPlayer2().getCButton();

			btn1.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			btn2.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			btn3.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			btn4.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			btn5.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			btn6.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			btn7.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			btn8.setSize(Competition.playerButtonWidth, Competition.playerButtonHeight);
			
			btn1.setLocation(68, 40);
			btn2.setLocation(256, 40);
			btn3.setLocation(444, 40);
			btn4.setLocation(632, 40);
			btn5.setLocation(68, 520);
			btn6.setLocation(256, 520);
			btn7.setLocation(444, 520);
			btn8.setLocation(632, 520);
			
			btn1.setFont(CButton.CompetitionBottom);
			btn2.setFont(CButton.CompetitionBottom);
			btn3.setFont(CButton.CompetitionBottom);
			btn4.setFont(CButton.CompetitionBottom);
			btn5.setFont(CButton.CompetitionBottom);
			btn6.setFont(CButton.CompetitionBottom);
			btn7.setFont(CButton.CompetitionBottom);
			btn8.setFont(CButton.CompetitionBottom);
			
			this.btnT1Win = new CButton(162,140,Competition.playerButtonWidth, Competition.playerButtonHeight,CButton.CompetitionBottom,new AbstractAction("") {
			    public void actionPerformed(ActionEvent e) {
			    	watchGame(0);
			    }
			});
			btnT1Win.setEnabled(false);
			btnT1Win.setFont(CButton.CompetitionBottom);
			cp.add(btnT1Win);
			this.scheduleGame.get(0).setWinButton(btnT1Win);
			
			this.btnT2Win = new CButton(538,140,Competition.playerButtonWidth, Competition.playerButtonHeight,CButton.CompetitionBottom,new AbstractAction("") {
			    public void actionPerformed(ActionEvent e) {
			    	watchGame(1);
			    }
			});
			btnT2Win.setEnabled(false);
			btnT2Win.setFont(CButton.CompetitionBottom);
			cp.add(btnT2Win);
			this.scheduleGame.get(1).setWinButton(btnT2Win);
			
			this.btnT3Win = new CButton(162,420,Competition.playerButtonWidth, Competition.playerButtonHeight,CButton.CompetitionBottom,new AbstractAction("") {
			    public void actionPerformed(ActionEvent e) {
			    	watchGame(2);
			    }
			});
			btnT3Win.setEnabled(false);
			btnT3Win.setFont(CButton.CompetitionBottom);
			cp.add(btnT3Win);
			this.scheduleGame.get(2).setWinButton(btnT3Win);
			
			this.btnT4Win = new CButton(538,420,Competition.playerButtonWidth, Competition.playerButtonHeight,CButton.CompetitionBottom,new AbstractAction("") {
			    public void actionPerformed(ActionEvent e) {
			    	watchGame(3);
			    }
			});
			btnT4Win.setEnabled(false);
			btnT4Win.setFont(CButton.CompetitionBottom);
			cp.add(btnT4Win);
			this.scheduleGame.get(3).setWinButton(btnT4Win);
			
			this.btnB1Win = new CButton(352,240,Competition.playerButtonWidth, Competition.playerButtonHeight,CButton.CompetitionBottom,new AbstractAction("") {
			    public void actionPerformed(ActionEvent e) {
			    	watchGame(4);
			    }
			});
			btnB1Win.setEnabled(false);
			btnB1Win.setFont(CButton.CompetitionBottom);
			cp.add(btnB1Win);
			scheduleGame.get(4).setWinButton(btnB1Win);
			
			this.btnB2Win = new CButton(352,320,Competition.playerButtonWidth, Competition.playerButtonHeight,CButton.CompetitionBottom,new AbstractAction("") {
			    public void actionPerformed(ActionEvent e) {
			    	watchGame(5);
			    }
			});
			btnB2Win.setEnabled(false);
			btnB2Win.setFont(CButton.CompetitionBottom);
			cp.add(btnB2Win);
			scheduleGame.get(5).setWinButton(btnB2Win);
			
			this.btnKWin = new CButton(538,280,Competition.playerButtonWidth, Competition.playerButtonHeight,CButton.CompetitionBottom,new AbstractAction("") {
			    public void actionPerformed(ActionEvent e) {
			    	watchGame(6);
			    }
			});
			btnKWin.setEnabled(false);
			btnKWin.setFont(CButton.CompetitionBottom);
			cp.add(btnKWin);
			scheduleGame.get(6).setWinButton(btnKWin);
			
			/* 每個button中央出去的那條 */
			/* Game1 */
			lineComp.addLine(btn1.getLocation().x + (Competition.playerButtonWidth/2) , 100, btn1.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(0).addWinLine(1, btn1.getLocation().x + (Competition.playerButtonWidth/2), 100, btn1.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			lineComp.addLine(btn2.getLocation().x + (Competition.playerButtonWidth/2) , 100, btn2.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(0).addWinLine(2, btn2.getLocation().x + (Competition.playerButtonWidth/2), 100, btn2.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			/* Game2 */
			lineComp.addLine(btn3.getLocation().x + (Competition.playerButtonWidth/2) , 100, btn3.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(1).addWinLine(1, btn3.getLocation().x + (Competition.playerButtonWidth/2), 100, btn3.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			lineComp.addLine(btn4.getLocation().x + (Competition.playerButtonWidth/2) , 100, btn4.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(1).addWinLine(2, btn4.getLocation().x + (Competition.playerButtonWidth/2), 100, btn4.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			/* Game3 */
			lineComp.addLine(btn5.getLocation().x + (Competition.playerButtonWidth/2) , 520, btn5.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(2).addWinLine(1, btn5.getLocation().x + (Competition.playerButtonWidth/2), 520, btn5.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			lineComp.addLine(btn6.getLocation().x + (Competition.playerButtonWidth/2) , 520, btn6.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(2).addWinLine(2, btn6.getLocation().x + (Competition.playerButtonWidth/2), 520, btn6.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			/* Game4 */
			lineComp.addLine(btn7.getLocation().x + (Competition.playerButtonWidth/2) , 520, btn7.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(3).addWinLine(1, btn7.getLocation().x + (Competition.playerButtonWidth/2), 520, btn7.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			lineComp.addLine(btn8.getLocation().x + (Competition.playerButtonWidth/2) , 520, btn8.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(3).addWinLine(2, btn8.getLocation().x + (Competition.playerButtonWidth/2), 520, btn8.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			/* 第一次對決連線 */
			/* Game1 */
			lineComp.addLine(btn1.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(0).addWinLine(1, btn1.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			lineComp.addLine(btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btn2.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(0).addWinLine(2, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btn2.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			/* Game2 */
			lineComp.addLine(btn3.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(1).addWinLine(1, btn3.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			lineComp.addLine(btnT2Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btn4.getLocation().x + (Competition.playerButtonWidth/2), 100+15 ,Color.WHITE);
			this.scheduleGame.get(1).addWinLine(2, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btn4.getLocation().x + (Competition.playerButtonWidth/2), 100+15);
			/* Game3 */
			lineComp.addLine(btn5.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(2).addWinLine(1, btn5.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			lineComp.addLine(btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btn6.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(2).addWinLine(2, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btn6.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			/* Game4 */
			lineComp.addLine(btn7.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(3).addWinLine(1, btn7.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			lineComp.addLine(btnT4Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btn8.getLocation().x + (Competition.playerButtonWidth/2), 520-15 ,Color.WHITE);
			this.scheduleGame.get(3).addWinLine(2, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btn8.getLocation().x + (Competition.playerButtonWidth/2), 520-15);
			/* 第一次對決勝利，連往button */
			/* Game1 */
			lineComp.addLine(btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2), 140 ,Color.WHITE);
			this.scheduleGame.get(0).addWinLine(1, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2), 140);
			this.scheduleGame.get(0).addWinLine(2, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2), 140);
			/* Game2 */
			lineComp.addLine(btnT2Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 140 ,Color.WHITE);
			this.scheduleGame.get(1).addWinLine(1, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 140);
			this.scheduleGame.get(1).addWinLine(2, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2) , 100+15, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 140);
			/* Game3 */
			lineComp.addLine(btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2), 480 ,Color.WHITE);
			this.scheduleGame.get(2).addWinLine(1, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2), 480);
			this.scheduleGame.get(2).addWinLine(2, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2), 480);
			/* Game4 */
			lineComp.addLine(btnT4Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 480 ,Color.WHITE);
			this.scheduleGame.get(3).addWinLine(1, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 480);
			this.scheduleGame.get(3).addWinLine(2, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2) , 520-15, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 480);
			/* 第二次對決連線，中央出去 */
			lineComp.addLine(btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10 ,Color.WHITE);
			this.scheduleGame.get(4).addWinLine(1, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10);
			lineComp.addLine(btnT2Win.getLocation().x + (Competition.playerButtonWidth/2) , 200, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10 ,Color.WHITE);
			this.scheduleGame.get(4).addWinLine(2, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2) , 200, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10);
			lineComp.addLine(btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 420, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10 ,Color.WHITE);
			this.scheduleGame.get(5).addWinLine(1, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 420, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10 );
			lineComp.addLine(btnT4Win.getLocation().x + (Competition.playerButtonWidth/2) , 420, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10 ,Color.WHITE);
			this.scheduleGame.get(5).addWinLine(2, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2) , 420, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10 );
			/* 第二次對決連線 */
			lineComp.addLine(btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200+10, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10 ,Color.WHITE);
			this.scheduleGame.get(4).addWinLine(1, btnT1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200+10, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10);
			lineComp.addLine(btnB1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200+10, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10 ,Color.WHITE);
			this.scheduleGame.get(4).addWinLine(2, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200+10, btnT2Win.getLocation().x + (Competition.playerButtonWidth/2), 200+10);
			lineComp.addLine(btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 420-10, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10 ,Color.WHITE);
			this.scheduleGame.get(5).addWinLine(1, btnT3Win.getLocation().x + (Competition.playerButtonWidth/2) , 420-10, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10);
			lineComp.addLine(btnB2Win.getLocation().x + (Competition.playerButtonWidth/2) , 420-10, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10 ,Color.WHITE);
			this.scheduleGame.get(5).addWinLine(2, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2) , 420-10, btnT4Win.getLocation().x + (Competition.playerButtonWidth/2), 420-10);
			/* 第二次對決勝利，連往button */
			lineComp.addLine(btnB1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200+10, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2), 240 ,Color.WHITE);
			this.scheduleGame.get(4).addWinLine(1, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200+10, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2), 240);
			this.scheduleGame.get(4).addWinLine(2, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2) , 200+10, btnB1Win.getLocation().x + (Competition.playerButtonWidth/2), 240);
			lineComp.addLine(btnB2Win.getLocation().x + (Competition.playerButtonWidth/2) , 420-10, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2), 380 ,Color.WHITE);	
			this.scheduleGame.get(5).addWinLine(1, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2) , 420-10, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2), 380);
			this.scheduleGame.get(5).addWinLine(2, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2) , 420-10, btnB2Win.getLocation().x + (Competition.playerButtonWidth/2), 380);
			/* 第三次對決連線，中央出去 */
			lineComp.addLine(btnB1Win.getLocation().x + (Competition.playerButtonWidth) , 270, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 270 ,Color.WHITE);
			this.scheduleGame.get(6).addWinLine(1, btnB1Win.getLocation().x + (Competition.playerButtonWidth) , 270, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 270);
			lineComp.addLine(btnB1Win.getLocation().x + (Competition.playerButtonWidth) , 350, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 350 ,Color.WHITE);
			this.scheduleGame.get(6).addWinLine(2, btnB1Win.getLocation().x + (Competition.playerButtonWidth) , 350, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 350);
			/* 第三次對決連線 */
			lineComp.addLine(btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30 , 270, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 310 ,Color.WHITE);
			this.scheduleGame.get(6).addWinLine(1, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30 , 270, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 310);
			lineComp.addLine(btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30 , 310, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 350 ,Color.WHITE);
			this.scheduleGame.get(6).addWinLine(2, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30 , 310, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30, 350);
			/* 第三次對決連線，連往button */
			lineComp.addLine(btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30 , btnKWin.getLocation().y+30, btnKWin.getLocation().x , btnKWin.getLocation().y+30 ,Color.WHITE);
			this.scheduleGame.get(6).addWinLine(1, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30 , btnKWin.getLocation().y+30, btnKWin.getLocation().x , btnKWin.getLocation().y+30);
			this.scheduleGame.get(6).addWinLine(2, btnB1Win.getLocation().x + (Competition.playerButtonWidth)+30 , btnKWin.getLocation().y+30, btnKWin.getLocation().x , btnKWin.getLocation().y+30);
			
			cp.add(btn1);
			cp.add(btn2);
			cp.add(btn3);
			cp.add(btn4);
			cp.add(btn5);
			cp.add(btn6);
			cp.add(btn7);
			cp.add(btn8);
		}
	}
	
	/**
	 * 下方button設定
	 */
	protected void setButtons(){
		
		/* Button Set's Panel */
		CPanel cp = new CPanel(138,660,648,60,new GridLayout(1,1,0,0));
		
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("回主選單") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToMainPage();
		    }
		}));
		
		this.add(cp, new Integer(2));
	}
	
	private void startGame(){
		for(int i=0;i<4;i++){
			GameModel game = new GameModel(this.scheduleGame.get(i) ,timeout);
			game.addObserver(this);
			PlayGameThread thread = new PlayGameThread(game);
			thread.start();
		}
	}
	
	private void watchGame(int gameNo){
		/* 觀看模式 */
		ChangePage.changeToCompetitionWatchPage(this.scheduleGame.get(gameNo).getRecordGame());
	}
	
	private void drawWinLine(GameInfo gameInfo){
		ArrayList<Line> lines = null;
		if(gameInfo.getWinnerInt() == 1){
			lines = gameInfo.getWinLineP1();
		}else if(gameInfo.getWinnerInt() == 2){
			lines = gameInfo.getWinLineP2();
		}
		for(int i=0;i<lines.size();i++){
			Line l = lines.get(i);
			this.lineComp.addLine(l.x1, l.y1, l.x2, l.y2, l.color);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(arg instanceof GameOverData){
			GameOverData data = (GameOverData) arg;
			GameInfo gameInfo = data.gameInfo;
			/* 晝勝利線 */
			this.drawWinLine(gameInfo);
			/* 勝利Button設定 */
			if(gameInfo.getWinButton() == this.btnKWin){
				gameInfo.getWinButton().setText("<html><font color='red'>"+gameInfo.getWinner().getTeamName()+"</font></html>");
			}else{
				gameInfo.getWinButton().setText(gameInfo.getWinner().getTeamName());
			}
			
			gameInfo.getWinButton().setEnabled(true);
			gameInfo.setRecordGame(data.record);
			/* over */
			if(gameInfo.getNextGame() == null){
				return ;
			}
			/* 檢查配對遊戲是否已經結束 */
			if(!gameInfo.getAnotherGame().isGameOver() && gameInfo.getNextGame() != null){
				gameInfo.getNextGame().setPlayer1(gameInfo.getWinner());
			}else if(gameInfo.getAnotherGame().isGameOver() && gameInfo.getNextGame() != null){
				gameInfo.getNextGame().setPlayer2(gameInfo.getWinner());
				/* start New Game */
				GameModel game = new GameModel(gameInfo.getNextGame() ,timeout);
				game.addObserver(this);
				game.gameStart();
			}else{
				// Game Over
			}
			
		}
		
	}
	
}