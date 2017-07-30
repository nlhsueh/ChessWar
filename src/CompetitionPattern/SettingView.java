package CompetitionPattern;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import Share.AbstractSettingView;
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
	
	public static int playerButtonWidth = 120 ;
	public static int playerButtonHeight = 60 ;
	
	public SettingView (){
		ctr = new SettingController();
		/* add button set panel & get delegated observable object */
		this.setButtons();
		/* add background image */
		this.add(new DrawBackground("playSetting"), new Integer(0));
		
		/* select folder part */
		CPanel playerCP = new CPanel(100,40,724,400,null);
		playerCP.setBorder(BorderFactory.createTitledBorder(BorderFactory.   
                createLineBorder(Color.BLACK, 2), "AI載入設定", TitledBorder.CENTER,   
                TitledBorder.TOP, new Font("標楷體", Font.BOLD, 24), Color.black));
		this.setChooseFolderView(playerCP);
		this.add(playerCP, new Integer(1));
		/* Timeout Setting Panel */
		CPanel timeoutCP = new CPanel(100,470,724,150,null);
		timeoutCP.setBorder(BorderFactory.createTitledBorder(BorderFactory.   
                createLineBorder(Color.BLACK, 2), "時間設定", TitledBorder.CENTER,   
                TitledBorder.TOP, new Font("標楷體", Font.BOLD, 24), Color.black));
		this.setTimeout(timeoutCP);
		this.add(timeoutCP, new Integer(1));
	}
	
	/**
	 * 下方button設定
	 */
	protected void setButtons(){
		
		/* Button Set's Panel */
		CPanel cp = new CPanel(138,660,648,60,new GridLayout(1,2,20,0));
		
		/* Add Button to Panel */
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("開始對戰") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.clickGameStart();
		    }
		}));
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("回主選單") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToMainPage();
		    }
		}));
		
		this.add(cp, new Integer(1));
	}
	
	private void setChooseFolderView(CPanel cp){
		/* title */
		CLabel subjectTitle = new CLabel("載入AI列表",SwingConstants.CENTER);
		subjectTitle.setAttr(0, 40, 724, 30, CLabel.SettingTitleBold);
		
		/* select path button */
		cp.add(new CButton(442,35,122,40,CButton.SettingChangeNickname,new AbstractAction("選擇路徑") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.selectLoadingPath();
		    }
		}));
		
		/* change Opponent button */
		cp.add(new CButton(580,35,122,40,CButton.SettingChangeNickname,new AbstractAction("交換對手") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.clickChangePlayer();
		    }
		}));
		
		/* loaded AI List */
		JLayeredPane AIListPanel = new JLayeredPane();
		AIListPanel.setOpaque(false);
		ctr.setAIListPanel(AIListPanel); /* 傳送給controller */
		/* 放置已載入AI的ScrollPanl */ 
		JScrollPane scrollPane = new JScrollPane(AIListPanel);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		ctr.setScrollPane(scrollPane);
		/* put ScrollPane panel */
		CPanel cpScroll = new CPanel(50,75,624,310, new BorderLayout(0,0));
		cpScroll.setOpaque(false);
		cpScroll.add(scrollPane);
		
		cp.add(cpScroll);
		cp.add(subjectTitle);
	}
	
	/**
	 * Timeout Panel設定
	 */
	protected void setTimeout(CPanel timeoutCP){
		
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
		
		buttonTimeout10s.doClick();
	}
	
}
