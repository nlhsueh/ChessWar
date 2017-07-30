package NormalPattern;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ChessGame.ChessSide;
import FrameView.MainFrame;
import Share.AbstractSettingView;
import Share.ExternalAIData;
import Share.GameSetting;
import ViewComponent.CButton;
import ViewComponent.CLabel;
import ViewComponent.CPanel;
import ViewComponent.CRadioButton;
import ViewComponent.ChangePage;
import ViewComponent.DrawBackground;

/**
 * 對弈模式的遊戲設定
 */
@SuppressWarnings("serial")
public class SettingView extends AbstractSettingView{
	
	private SettingController ctr ;
	private ArrayList<ButtonGroup> clearBtnList = new ArrayList<ButtonGroup>();
	private CLabel redAIName ;
	private CLabel blackAIName ;
	private CLabel redNickname ;
	private CLabel blackNickname ;
	
	public SettingView (GameSetting gameSetting,ExternalAIData externalAIData){
		ctr = new SettingController(gameSetting, externalAIData);
		
		/* add button set panel & get delegated observable object */
		this.setButtons();
		/* add background image */
		this.add(new DrawBackground("playSetting"), new Integer(0));
		
		/* Player Part */
		CPanel playerCP = new CPanel(100,40,724,360,null);
		playerCP.setBorder(BorderFactory.createTitledBorder(BorderFactory.   
                createLineBorder(Color.BLACK, 2), "玩家設定", TitledBorder.CENTER,   
                TitledBorder.TOP, new Font("標楷體", Font.BOLD, 24), Color.black));
		
		/* Player Setting Panel */
		this.setPlayerSetting(playerCP);
		this.setNickName(playerCP,gameSetting.nickname1,gameSetting.nickname2);
		this.add(playerCP, new Integer(1));
		/* Timeout Setting Panel */
		CPanel timeoutCP = new CPanel(100,450,724,150,null);
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
		CPanel cp = new CPanel(138,660,648,60,new GridLayout(1,4,20,0));
		
		/* Add Button to Panel */
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("開始遊戲") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.clickGameStart();
		    }
		}));
		cp.add(new CButton(CButton.SettingBottom,new AbstractAction("外掛設定") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToLoadAIPage();
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
				
				redAIName.setText("");
				blackAIName.setText("");
				redNickname.setText("");
				blackNickname.setText("");
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
		
		/* AI Name */
		CPanel cp = new CPanel(0,180,724,30,null);
		
		this.redAIName = new CLabel("<html><font color='"+PlayingPlayerView.colorTitle+"'></font></html>",SwingConstants.CENTER);
		redAIName.setAttr(0, 0, 362, 30, CLabel.SettingAIName);
		redAIName.setVerticalAlignment(SwingConstants.TOP);
		
		this.blackAIName = new CLabel("<html><font color='"+PlayingPlayerView.colorTitle+"'></font></html>",SwingConstants.CENTER);
		blackAIName.setAttr(362, 0, 362, 30, CLabel.SettingAIName);
		blackAIName.setVerticalAlignment(SwingConstants.TOP);
		
		cp.add(redAIName);
		cp.add(blackAIName);
		playerCP.add(cp);
		
		/* title */
		CLabel subjectTitle = new CLabel("<html>遊戲依照象棋比賽規定由<font color='red'>紅子</font>先走，由下方<font color='"+PlayingPlayerView.colorNormal+"'>左邊玩家</font>為<font color='red'>執紅子</font>先走</html>",SwingConstants.CENTER);
		subjectTitle.setAttr(0, 40, 724, 30, CLabel.SettingTitleBold);
		playerCP.add(subjectTitle);
		
		/* red chess part */
		CPanel cpRed = new CPanel(0,90,362,75,null);
		
		CLabel redTitle = new CLabel("<html>請選擇<font color='red'>紅子</font>類型<html>",SwingConstants.CENTER);
		redTitle.setAttr(0, 0, 362, 30, CLabel.SettingTitleBold);
		
		CRadioButton buttonRedTypeHuman = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("玩家") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToHuman(ChessSide.RED);
		    	redAIName.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'></font></html>");
		    }
		});
		CRadioButton buttonRedTypeInternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("內建AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToInternalAI(ChessSide.RED);
		    	redAIName.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>Build-In AI</font></html>");
		    }
		});
		CRadioButton buttonRedTypeExternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("外掛AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToExternalAI(ChessSide.RED, redAIName,buttonRedTypeHuman,buttonRedTypeInternalAI);
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
		
		this.ctr.initType(ChessSide.RED,buttonRedTypeHuman,buttonRedTypeInternalAI,buttonRedTypeExternalAI,redAIName);
		
		cpRed.add(redTitle);
		cpRed.add(buttonRedTypeHuman);
		cpRed.add(buttonRedTypeInternalAI);
		cpRed.add(buttonRedTypeExternalAI);
		
		/* black chess part */
		CPanel cpBlack = new CPanel(362,90,362,75,null);
		
		CLabel blackTitle = new CLabel("<html>請選擇<font color='red'>黑子</font>類型<html>",SwingConstants.CENTER);
		blackTitle.setAttr(0, 0, 362, 30, CLabel.SettingTitleBold);
		
		CRadioButton buttonBlackTypeHuman = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("玩家") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToHuman(ChessSide.BLACK);
		    	blackAIName.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'></font></html>");
		    }
		});
		CRadioButton buttonBlackTypeInternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("內建AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToInternalAI(ChessSide.BLACK);
		    	blackAIName.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>Build-In AI</font></html>");
		    }
		});
		CRadioButton buttonBlackTypeExternalAI = new CRadioButton(CRadioButton.SettingRadioButton,new AbstractAction("外掛AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.changeTypeToExternalAI(ChessSide.BLACK, blackAIName, buttonBlackTypeHuman, buttonBlackTypeInternalAI);
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
		
		this.ctr.initType(ChessSide.BLACK,buttonBlackTypeHuman,buttonBlackTypeInternalAI,buttonBlackTypeExternalAI,blackAIName);
		
		cpBlack.add(blackTitle);
		cpBlack.add(buttonBlackTypeHuman);
		cpBlack.add(buttonBlackTypeInternalAI);
		cpBlack.add(buttonBlackTypeExternalAI);
		
		/* main-sub panel add */
		playerCP.add(cpRed);
		playerCP.add(cpBlack);
	}
	
	/**
	 * Player Panel之Nickname部分設定
	 */
	private void setNickName(CPanel playerCP, String redNicknameString , String blackNicknameString){
		this.redNickname = new CLabel("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+redNicknameString+"</font></html>",SwingConstants.CENTER);
		redNickname.setAttr(0, 230, 362, 30, CLabel.SettingAIName);
		playerCP.add(redNickname);
		this.blackNickname = new CLabel("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+blackNicknameString+"</font></html>",SwingConstants.CENTER);
		blackNickname.setAttr(362, 230, 362, 30, CLabel.SettingAIName);
		playerCP.add(blackNickname);
		
		playerCP.add(new CButton(120,280,130,45,CButton.SettingChangeNickname,new AbstractAction("修改暱稱") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.clickChangeNinkname(redNickname, ChessSide.RED);
		    }
		}));
		
		playerCP.add(new CButton(480,280,130,45,CButton.SettingChangeNickname,new AbstractAction("修改暱稱") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.clickChangeNinkname(blackNickname, ChessSide.BLACK);
		    }
		}));
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


/**
 * 對弈模式設定暱稱時的彈出視窗(未優化)
 */
class SettingEnterNicknameDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private GameSetting gameSetting;
	private JLabel labelNickname ;
	private JTextField input;
	private ChessSide side ;
	
	public SettingEnterNicknameDialog(GameSetting gameSetting, JLabel labelNickname, ChessSide side){
		this.gameSetting = gameSetting;
		this.labelNickname = labelNickname;
		this.side = side;
		
		this.setFrameView();
		this.setPanelView();
		
		this.setVisible(true);
	}
	
	/**
	 * 選擇AI視窗Frame與Panel參數設定
	 * @return
	 */
	private void setFrameView(){
		/* 主選單Frame設定 */
		this.setSize(400, 200);
		this.setResizable(false);
		if(this.side == ChessSide.RED){
			this.setTitle("紅子暱稱修改");
		}else if(this.side == ChessSide.BLACK){
			this.setTitle("黑子暱稱修改");
		}
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setResizable(false);
	}
	
	/**
	 * 設定Panel
	 * @return
	 */
	private void setPanelView(){
		JPanel jp = new JPanel(null);
		jp.setSize(400,200);
		
		JLabel text = new JLabel("請輸入暱稱:");
		text.setFont(new Font(null, Font.BOLD, 26));
		text.setSize(400,40);
		text.setLocation(0, 30);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		jp.add(text);
		
		this.input  = new JTextField(20);
		input.setSize(360, 30);
		input.setLocation(20, 80);
		/* 限制最大輸入字元為8 */
		input.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				JTextField field = (JTextField) e.getSource();
				String s = field.getText(); if(s.length() >= 8) e.consume();
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		input.setFont(new Font(null, Font.BOLD, 20));
		if(this.side == ChessSide.RED && this.gameSetting.nickname1 != null){
			input.setText(this.gameSetting.nickname1);
		}else if(this.side == ChessSide.BLACK && this.gameSetting.nickname2 != null){
			input.setText(this.gameSetting.nickname2);
		}
		jp.add(input);
		
		JButton btnClose = new JButton("OK");
		btnClose.setFont(new Font(null, Font.PLAIN, 17));
		btnClose.setHorizontalAlignment(SwingConstants.CENTER);
		btnClose.setOpaque(true);
		btnClose.setActionCommand("close");
		btnClose.addActionListener(this);
		btnClose.setSize(80,40);
		btnClose.setLocation(160, 130);
		jp.add(btnClose);
		
		this.add(jp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		
		switch(cmd){
	
		// 關閉視窗
		case "close":{
			if(this.input.getText().equals("")){
				JOptionPane.showMessageDialog(new JPanel(), "暱稱不可為空！", "提示", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				break;
			}
			
			this.labelNickname.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+input.getText()+"</font></html>");
			if(this.side == ChessSide.RED){
				this.gameSetting.nickname1 = input.getText() ;
			}else if(this.side == ChessSide.BLACK){
				this.gameSetting.nickname2 = input.getText() ;
			}
			this.dispose();
			break;
		}
		default:
			
			return;
		}
		
	}
	
}

/**
 * 對弈模式選擇外部AI時的彈出視窗(未優化)
 */
class SettingSelectAIDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLayeredPane framePanel = new JLayeredPane();
	private JList<String> AIList ;
	private GameSetting gameSetting;
	private ExternalAIData externalAIData;
	private JLabel AILabel ;
	private ChessSide side;
	private CRadioButton btnHuman ;
	private CRadioButton btnAIInternal;
	
	public SettingSelectAIDialog(GameSetting gameSetting,ExternalAIData externalAIData, CLabel AILabel,ChessSide side,CRadioButton btnHuman, CRadioButton btnAIInternal){
		this.gameSetting = gameSetting;
		this.externalAIData = externalAIData;
		this.AILabel = AILabel;
		this.side = side;
		this.btnHuman = btnHuman ;
		this.btnAIInternal = btnAIInternal ;
		
		this.setFrameView();
		this.setSelectView();
		
		this.setVisible(true);
	}
	
	/**
	 * 選擇AI視窗Frame與Panel參數設定
	 * @return
	 */
	private void setFrameView(){
		/* 主選單Frame設定 */
		this.setSize(500, 300);
		this.setResizable(false);
		this.setTitle("選擇AI");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setResizable(false);
		this.add(this.framePanel);
		// Background Image Set
		this.setBackground();
	}
	
	private void setBackground(){
		JPanel jp = new JPanel();
		jp.setLocation(0, 0);
		jp.setSize(500,300);
		jp.setOpaque(false);
		// Background Image Set
		ImageIcon img = new ImageIcon(MainFrame.class.getClass().getResource("/img/settingBackground.jpg"));
		img = (new ImageIcon(img.getImage().getScaledInstance(924, 760, Image.SCALE_SMOOTH)));
		jp.setLayout(new GridLayout(1, 0, 0, 0));
		jp.add(new JLabel(img));
		this.framePanel.add(jp, new Integer(0));
	}
	
	private void setSelectView(){
		JPanel jp = new JPanel(null);
		jp.setLocation(0, 0);
		jp.setSize(500, 300);
		jp.setOpaque(false);
		jp.setBorder(null);
		
		JLabel topLb = new JLabel("<html><font color='"+PlayingPlayerView.colorBlack+"'>請選擇您欲使用的AI</font></html>",SwingConstants.CENTER);
		topLb.setFont(new Font("標楷體", Font.BOLD, 26));
		topLb.setSize(500, 40);
		topLb.setLocation(0, 10);
		
		String[] AIName = (String[]) this.externalAIData.getAIMainNameSet().toArray(new String[0]);
		this.AIList = new JList<String>(AIName);
		this.AIList.setOpaque(false);
		this.AIList.setVisibleRowCount(6);
		this.AIList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.AIList.setCellRenderer(new TransparentListCellRenderer()); /* let background transparent */
		this.AIList.setFont(new Font(null, Font.BOLD, 18));

		JScrollPane jsp = new JScrollPane(this.AIList);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		jsp.setLocation(50, 60);
		jsp.setSize(400, 150);
		jsp.setBorder(new LineBorder(Color.BLACK));
		
		JButton btnCheck = new JButton("確定");
		btnCheck.setFont(new Font(null, Font.PLAIN, 18));
		btnCheck.setSize(120,40);
		btnCheck.setLocation(120,220);
		btnCheck.setOpaque(false);
		btnCheck.setActionCommand("Check");
		btnCheck.addActionListener(this);
		
		JButton btnCancel = new JButton("取消");
		btnCancel.setFont(new Font(null, Font.PLAIN, 18));
		btnCancel.setSize(120,40);
		btnCancel.setLocation(280,220);
		btnCancel.setOpaque(false);
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);
		
		jp.add(topLb);
		jp.add(jsp);
		jp.add(btnCheck);
		jp.add(btnCancel);
		
		this.framePanel.add(jp, new Integer(1));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		switch(cmd){
			
			case "Check":
				if(this.AIList.getSelectedValue() == null){
					JOptionPane.showMessageDialog(new JPanel(), "您還沒有選擇要使用的外掛！"
							, "提示", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
					return;
				}
				this.AILabel.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+this.AIList.getSelectedValue()+"</font></html>");
				if(this.side == ChessSide.RED){
					this.gameSetting.player1 = 3 ;
					this.gameSetting.externalAIName1 = this.AIList.getSelectedValue();
				}else if(this.side == ChessSide.BLACK){
					this.gameSetting.player2 = 3 ;
					this.gameSetting.externalAIName2 = this.AIList.getSelectedValue();
				}
				this.dispose();
				break;
			case "Cancel":
				if(this.side == ChessSide.RED){
					if(this.gameSetting.player1 == 1){
						this.btnHuman.doClick();
					}else if(this.gameSetting.player1 == 2){
						this.btnAIInternal.doClick();
					}
				}else if(this.side == ChessSide.BLACK){
					if(this.gameSetting.player2 == 1){
						this.btnHuman.doClick();
					}else if(this.gameSetting.player2 == 2){
						this.btnAIInternal.doClick();
					}
				}
				this.dispose();
				break;
				
			default:
				break;
			
		}
	}

}