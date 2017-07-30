package NormalPattern;

import javax.swing.JOptionPane;

import ChessGame.ChessSide;
import FrameView.MainFrame;
import Save.SaveSetting;
import Share.AbstractSettingController;
import Share.ExternalAIData;
import Share.GameSetting;
import Share.PlayingModel;
import ViewComponent.CLabel;
import ViewComponent.CRadioButton;
import ViewComponent.ChangePage;

public class SettingController extends AbstractSettingController{
	
	private GameSetting gameSetting;
	private ExternalAIData externalAIData;
	
	public SettingController(GameSetting gameSetting, ExternalAIData externalAIData){
		this.gameSetting = gameSetting ;
		this.externalAIData = externalAIData;
	}
	
	/**
	 * 選擇棋子類別之初始化設定
	 */
	@Override
	public void initType(ChessSide side,CRadioButton buttonTypeHuman,CRadioButton buttonTypeInternalAI,CRadioButton buttonTypeExternalAI, CLabel AIName){
		/* 如果有設定存檔，設立初始值為存檔內容 */
		
		if(side == ChessSide.RED){
			switch(this.gameSetting.player1){
				case 1:
					buttonTypeHuman.doClick();
					break;
				case 2:
					buttonTypeInternalAI.doClick();
					break;
				case 3:
					buttonTypeExternalAI.setSelected(true);
					AIName.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+this.gameSetting.externalAIName1+"</font></html>");
					this.gameSetting.player1 = 3 ;
					break;
				default:
					break;
			}
			
		}else{
			switch(this.gameSetting.player2){
				case 1:
					buttonTypeHuman.doClick();
					break;
				case 2:
					buttonTypeInternalAI.doClick();
					break;
				case 3:
					buttonTypeExternalAI.setSelected(true);
					AIName.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+this.gameSetting.externalAIName2+"</font></html>");
					this.gameSetting.player2 = 3 ;
					break;
				default:
					break;
			}
		}
	}
	
	
	/**
	 * 點擊「重新設定」按鈕
	 */
	@Override
	public void clickReset(){
		this.gameSetting.player1 = 0;
		this.gameSetting.player2 = 0;
		this.gameSetting.externalAIName1 = null;
		this.gameSetting.externalAIName2 = null;
		this.gameSetting.timeout = 0 ;
	}
	
	/**
	 * 點擊「修改暱稱」按鈕
	 */
	public void clickChangeNinkname(CLabel sideNickname, ChessSide side){
		new SettingEnterNicknameDialog(this.gameSetting, sideNickname, side);
	}
	
	/**
	 * 當點擊「開始遊戲」按鈕
	 */
	@Override
	public void clickGameStart(){
		/* 檢查是否皆設定完成 */
		String settingStatus = this.gameSetting.getSettingStatus() ;
		if(settingStatus.equals("okay") == true){
			/* 如果設定完成，儲存設定並切換到'對弈'頁面 */
			SaveSetting.savePlayGameSetting(this.gameSetting) ;
			/* 切換畫面到PlayPage，並使用存檔後的GameSetting之拷貝建構子去初始化 */
			new PlayingModel(MainFrame.framePanel,new NormalGameSetting(this.gameSetting));
			ChangePage.changeToPlayPage();
		}else{
			/* 未完成設定，跳出狀態視窗 */
			JOptionPane.showMessageDialog(null, settingStatus
					, "提示", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_error);
		}
	}
	
	/**
	 * 當修改「玩家類別」為「Human」
	 */
	@Override
	public void changeTypeToHuman(ChessSide side){
		
		if(side == ChessSide.RED){
			gameSetting.player1 = 1 ;
			gameSetting.externalAIName1 = null;
		}else{
			gameSetting.player2 = 1 ;
			gameSetting.externalAIName2 = null;
		}
		
	}
	
	/**
	 * 當修改「玩家類別」為「Internal AI」
	 */
	@Override
	public void changeTypeToInternalAI(ChessSide side){
		
		if(side == ChessSide.RED){
			gameSetting.player1 = 2 ;
			gameSetting.externalAIName1 = null;
		}else{
			gameSetting.player2 = 2 ;
			gameSetting.externalAIName2 = null;
		}
		
	}
	
	/**
	 * 當修改「玩家類別」為「External AI」
	 */
	@Override
	public void changeTypeToExternalAI(ChessSide side, CLabel AIName, CRadioButton btnHuman, CRadioButton btnInternalAI){
		
		if(side == ChessSide.RED){
			new SettingSelectAIDialog(this.gameSetting,this.externalAIData, AIName,ChessSide.RED,btnHuman,btnInternalAI);
		}else{
			new SettingSelectAIDialog(this.gameSetting,this.externalAIData, AIName,ChessSide.BLACK,btnHuman,btnInternalAI);
		}
		
	}
	
	/**
	 * 當修改「timeout」
	 */
	@Override
	public void changeTimeout(int timeout){
		
		this.gameSetting.timeout = timeout ;
		
	}

	@Override
	public void initType(ChessSide side, CRadioButton buttonTypeHuman, CRadioButton buttonTypeInternalAI,
			CRadioButton buttonTypeExternalAI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeTypeToExternalAI(ChessSide side) {
		// TODO Auto-generated method stub
		
	}
	
}
