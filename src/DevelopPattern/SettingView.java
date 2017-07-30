package DevelopPattern;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import ChessGame.ChessSide;
import Share.AbstractSettingView;
import Share.GameSetting;
import ViewComponent.CButton;
import ViewComponent.CLabel;
import ViewComponent.CPanel;
import ViewComponent.CRadioButton;
import ViewComponent.ChangePage;
import ViewComponent.DrawBackground;

@SuppressWarnings("serial")
public class SettingView extends AbstractSettingView{
	
	private SettingController ctr ;
	private ArrayList<ButtonGroup> clearBtnList = new ArrayList<ButtonGroup>();
	
	private CRadioButton buttonRedTypeHuman;
	private CRadioButton buttonRedTypeInternalAI;
	private CRadioButton buttonRedTypeExternalAI;
	private CRadioButton buttonBlackTypeHuman;
	private CRadioButton buttonBlackTypeInternalAI;
	private CRadioButton buttonBlackTypeExternalAI;
	
	public SettingView (GameSetting gameSetting){
		ctr = new SettingController(gameSetting);
		
		/* add button set panel & get delegated observable object */
		this.setButtons();
		/* add background image */
		this.add(new DrawBackground("developSetting"), new Integer(0));
		
		/* Player Part */
		CPanel playerCP = new CPanel(100,40,724,300,null);
		playerCP.setBorder(BorderFactory.createTitledBorder(BorderFactory.   
                createLineBorder(Color.BLACK, 2), "玩家設定", TitledBorder.CENTER,   
                TitledBorder.TOP, new Font("標楷體", Font.BOLD, 24), Color.black));
		
		/* Player Setting Panel */
		this.setPlayerSetting(playerCP);
		this.add(playerCP, new Integer(1));
		/* Timeout Setting Panel */
		CPanel timeoutCP = new CPanel(100,380,724,150,null);
		timeoutCP.setBorder(BorderFactory.createTitledBorder(BorderFactory.   
                createLineBorder(Color.BLACK, 2), "時間設定", TitledBorder.CENTER,   
                TitledBorder.TOP, new Font("標楷體", Font.BOLD, 24), Color.black));
		this.setTimeout(timeoutCP, gameSetting.timeout);
		this.add(timeoutCP, new Integer(1));
	}
	
	/**
	 * 下方button設定
	 */
	protected void setButtons(){
		
		/* Button Set's Panel */
		CPanel cp = new CPanel(138,660,648,60,new GridLayout(1,3,20,0));
		
		/* Add Button to Panel */
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("開始遊戲") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.clickGameStart();
		    }
		}));
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("重新設定") {
		    public void actionPerformed(ActionEvent e) {
		    	Iterator<ButtonGroup> i = clearBtnList.iterator();
				while(i.hasNext()){
					ButtonGroup x = (ButtonGroup) i.next();
					x.clearSelection();
				}
				ctr.clickReset();
				
		    }
		}));
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("回主選單") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToMainPage();
		    }
		}));
		
		this.add(cp, new Integer(1));
	}
	
	/**
	 * Player Panel設定
	 */
	protected void setPlayerSetting(CPanel playerCP){
		
		/* title */
		CLabel subjectTitle = new CLabel("<html>遊戲依照象棋比賽規定由<font color='red'>紅子</font>先走，由下方<font color='"+PlayingPlayerView.colorNormal+"'>左邊設定</font>為<font color='red'>執紅子</font>先走</html>",SwingConstants.CENTER);
		subjectTitle.setAttr(0, 40, 724, 30, CLabel.SettingTitleBold);
		playerCP.add(subjectTitle);
		
		/* title2 */
		CLabel subjectTitle2 = new CLabel("<html>開發模式至少必須要有一方為<font color='"+PlayingPlayerView.colorNormal+"'>開發所使用之AI</font></html>",SwingConstants.CENTER);
		subjectTitle2.setAttr(0, 100, 724, 30, CLabel.SettingTitleBold);
		playerCP.add(subjectTitle2);
		
		/* red chess part */
		CPanel cpRed = new CPanel(0,160,362,75,null);
		
		CLabel redTitle = new CLabel("<html>請選擇<font color='red'>紅子</font>類型<html>",SwingConstants.CENTER);
		redTitle.setAttr(0, 0, 362, 30, CLabel.SettingTitleBold);
		
		// 外部AI優先加入
		this.buttonRedTypeExternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("開發AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToExternalAI(ChessSide.RED);
		    }
		});
		this.buttonBlackTypeExternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("開發AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToExternalAI(ChessSide.BLACK);
		    }
		});
		
		this.buttonRedTypeHuman = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("玩家") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToHuman(ChessSide.RED);
		    	buttonBlackTypeExternalAI.doClick();
		    }
		});
		this.buttonRedTypeInternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("內建AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToInternalAI(ChessSide.RED);
		    	buttonBlackTypeExternalAI.doClick();
		    }
		});
		buttonRedTypeHuman.setAttr(80, 40, 75, 35);
		buttonRedTypeInternalAI.setAttr(150, 40, 85, 35);
		buttonRedTypeExternalAI.setAttr(235, 40, 85, 35);
		
		ButtonGroup groupRed = new ButtonGroup();
		groupRed.add(buttonRedTypeHuman);
		groupRed.add(buttonRedTypeInternalAI);
		groupRed.add(buttonRedTypeExternalAI);
		clearBtnList.add(groupRed);
		
		cpRed.add(redTitle);
		cpRed.add(buttonRedTypeHuman);
		cpRed.add(buttonRedTypeInternalAI);
		cpRed.add(buttonRedTypeExternalAI);
		
		this.ctr.initType(ChessSide.RED,buttonRedTypeHuman,buttonRedTypeInternalAI,buttonRedTypeExternalAI);
		
		/* black chess part */
		CPanel cpBlack = new CPanel(362,160,362,75,null);
		
		CLabel blackTitle = new CLabel("<html>請選擇<font color='red'>黑子</font>類型<html>",SwingConstants.CENTER);
		blackTitle.setAttr(0, 0, 362, 30, CLabel.SettingTitleBold);
		
		this.buttonBlackTypeHuman = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("玩家") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToHuman(ChessSide.BLACK);
		    	buttonRedTypeExternalAI.doClick();
		    }
		});
		this.buttonBlackTypeInternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("內建AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToInternalAI(ChessSide.BLACK);
		    	buttonRedTypeExternalAI.doClick();
		    }
		});
		buttonBlackTypeHuman.setAttr(80, 40, 75, 35);
		buttonBlackTypeInternalAI.setAttr(150, 40, 85, 35);
		buttonBlackTypeExternalAI.setAttr(235, 40, 85, 35);
		
		ButtonGroup groupBlack = new ButtonGroup();
		groupBlack.add(buttonBlackTypeHuman);
		groupBlack.add(buttonBlackTypeInternalAI);
		groupBlack.add(buttonBlackTypeExternalAI);
		clearBtnList.add(groupBlack);
		
		this.ctr.initType(ChessSide.BLACK,buttonBlackTypeHuman,buttonBlackTypeInternalAI,buttonBlackTypeExternalAI);
		
		cpBlack.add(blackTitle);
		cpBlack.add(buttonBlackTypeHuman);
		cpBlack.add(buttonBlackTypeInternalAI);
		cpBlack.add(buttonBlackTypeExternalAI);
		
		/* main-sub panel add */
		playerCP.add(cpRed);
		playerCP.add(cpBlack);
	}
	
	/**
	 * Timeout Panel設定
	 */
	protected void setTimeout(CPanel timeoutCP, int timeout){
		
		
		CLabel subjectTitle = new CLabel("<html>請設定每手思考時間，若<font color='red'>超過</font>思考時間則直接<font color='red'>判輸</font></html>",SwingConstants.CENTER);
		subjectTitle.setAttr(0, 30, 724, 30, CLabel.SettingTitleBold);
		timeoutCP.add(subjectTitle);
		
		CRadioButton buttonTimeout10s = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("10秒") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTimeout(10);
		    }
		});
		buttonTimeout10s.setAttr(200, 85, 75, 30);
		timeoutCP.add(buttonTimeout10s);
		
		CRadioButton buttonTimeout5m = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("5分") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTimeout(300);
		    }
		});
		buttonTimeout5m.setAttr(315, 85, 75, 30);
		timeoutCP.add(buttonTimeout5m);
		
		CRadioButton buttonTimeoutNo = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("無") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTimeout(-1);
		    }
		});
		buttonTimeoutNo.setAttr(415, 85, 75, 30);
		timeoutCP.add(buttonTimeoutNo);
		
		ButtonGroup group = new ButtonGroup();
		group.add(buttonTimeout10s);
		group.add(buttonTimeout5m);
		group.add(buttonTimeoutNo);
		clearBtnList.add(group);
		
		/* init */
		switch(timeout){
			
			case 10:
				buttonTimeout10s.doClick();
				break;
			case 300:
				buttonTimeout5m.doClick();
				break;
			case -1:
				buttonTimeoutNo.doClick();
				break;
			
		}
		
	}
	
}
