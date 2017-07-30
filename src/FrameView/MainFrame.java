package FrameView;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import NormalPattern.AddingAIView;
import Save.SaveSetting;
import Share.ExternalAIData;
import Share.GameSetting;
import ViewComponent.CButton;
import ViewComponent.CPanel;
import ViewComponent.ChangePage;
import ViewComponent.DrawBackground;

@SuppressWarnings("serial")

/**
 * 創建主視窗View的JFrame
 */
public class MainFrame extends JFrame {

	public static JPanel framePanel = new JPanel();
	private CardLayout cardLayout = new CardLayout();
	private JLayeredPane layeredPane = new JLayeredPane();
	
	public static ImageIcon icon_alert = new ImageIcon(new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/alert_a.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	public static ImageIcon icon_error = new ImageIcon(new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/error_a.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	
	public MainFrame(){
		/* Frame & Panel View Attributes Setting */
		this.setViewAttr();
		/* setBackground Image */
		layeredPane.add(new DrawBackground("mainFrame"), new Integer(0));
		/* set Buttons */
		this.setButtons();
		/* create other pages */
		this.createOtherPage();
		
		this.setVisible(true);
	}
	
	/**
	 * 建立其它頁面
	 */
	private void createOtherPage(){
		GameSetting playGameSetting = SaveSetting.loadPlayGameSetting();
		GameSetting developGameSetting = SaveSetting.loadDevelopGameSetting();
		ExternalAIData externalAIData = SaveSetting.loadExternalAI();
		
		MainFrame.framePanel.add(new NormalPattern.SettingView(playGameSetting,externalAIData), "PlaySetting");
		MainFrame.framePanel.add(new DevelopPattern.SettingView(developGameSetting), "DevelopSetting");
		MainFrame.framePanel.add(new AddingAIView(externalAIData), "LoadingAI");
		new ChangePage(MainFrame.framePanel,this.cardLayout);
	}
	
	/**
	 * 主視窗Frame與Panel參數設定
	 */
	private void setViewAttr(){
		/* 主選單Frame設定 */
		this.setSize(924, 760);
		this.setResizable(false);
		this.setTitle("Chess Game");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(MainFrame.framePanel);
		/* 主選單Panel設定 */
		MainFrame.framePanel.setLayout(this.cardLayout);
		MainFrame.framePanel.add(layeredPane,"MainFrame");
	}
	/**
	 * 主選單按鈕設定
	 * @return
	 */
	private void setButtons(){
		
		/* Button Set's Panel */
		CPanel cp = new CPanel(0,375,924,40,new GridLayout(1, 5, 0, 0));
		
		/* Add Button to Panel */
		cp.add(new CButton(CButton.MainFrame,new AbstractAction("對弈模式") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToPlaySettingPage();
		    }
		}));
		cp.add(new CButton(CButton.MainFrame,new AbstractAction("開發模式") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToDevelopSettingPage();
		    }
		}));
		cp.add(new CButton(CButton.MainFrame,new AbstractAction("比賽模式") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToCompetitionPage();
		    }
		}));
		cp.add(new CButton(CButton.MainFrame,new AbstractAction("準備中") {
		    public void actionPerformed(ActionEvent e) {
		    	
		    }
		}));
		cp.add(new CButton(CButton.MainFrame,new AbstractAction("離開遊戲") {
		    public void actionPerformed(ActionEvent e) {
		    	System.exit(0);
		    }
		}));
				
		/* Add Button's Panel to layeredPane(1) */
		layeredPane.add(cp, new Integer(1));
	}
	
}

