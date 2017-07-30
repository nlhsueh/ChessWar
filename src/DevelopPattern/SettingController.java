package DevelopPattern;

import javax.swing.JOptionPane;

import ChessGame.ChessSide;
import FrameView.MainFrame;
import Save.SaveSetting;
import Share.AbstractSettingController;
import Share.GameSetting;
import Share.PlayingModel;
import ViewComponent.CLabel;
import ViewComponent.CRadioButton;
import ViewComponent.ChangePage;

public class SettingController extends AbstractSettingController{
	
	private GameSetting gameSetting;
	
	public SettingController(GameSetting gameSetting){
		this.gameSetting = gameSetting ;
	}
	
	/**
	 * 選擇棋子類別之初始化設定
	 */
	public void initType(ChessSide side,CRadioButton buttonTypeHuman,CRadioButton buttonTypeInternalAI,CRadioButton buttonTypeExternalAI){
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
					buttonTypeExternalAI.doClick();;
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
					buttonTypeExternalAI.doClick();;
					break;
				default:
					break;
			}
		}
	}
	
	/**
	 * 點擊「重新設定」按鈕
	 */
	public void clickReset(){
		this.gameSetting.player1 = 0;
		this.gameSetting.player2 = 0;
		this.gameSetting.timeout = 0 ;
	}
	
	/**
	 * 當點擊「開始遊戲」按鈕
	 */
	public void clickGameStart(){
		/* 檢查是否皆設定完成 */
		String settingStatus = this.gameSetting.getSettingStatus() ;
		if(settingStatus.equals("okay") == true){
			/* 如果設定完成，儲存設定並切換到'對弈'頁面 */
			SaveSetting.saveDevelopGameSetting(this.gameSetting) ;
			/* 切換畫面到PlayPage，並使用存檔後的GameSetting之拷貝建構子去初始化 */
			new PlayingModel(MainFrame.framePanel,new DevelopGameSetting(this.gameSetting));
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
	public void changeTypeToHuman(ChessSide side){
		
		if(side == ChessSide.RED){
			gameSetting.player1 = 1 ;
		}else{
			gameSetting.player2 = 1 ;
		}
		
	}
	
	/**
	 * 當修改「玩家類別」為「Internal AI」
	 */
	public void changeTypeToInternalAI(ChessSide side){
		
		if(side == ChessSide.RED){
			gameSetting.player1 = 2 ;
		}else{
			gameSetting.player2 = 2 ;
		}
		
	}
	
	/**
	 * 當修改「玩家類別」為「External AI」
	 */
	public void changeTypeToExternalAI(ChessSide side){
		
		if(side == ChessSide.RED){
			gameSetting.player1 = 3 ;
		}else{
			gameSetting.player2 = 3 ;
		}
		
	}
	
	/**
	 * 當修改「timeout」
	 */
	public void changeTimeout(int timeout){
		
		this.gameSetting.timeout = timeout ;
		
	}

	@Override
	public void initType(ChessSide side, CRadioButton buttonTypeHuman, CRadioButton buttonTypeInternalAI,
			CRadioButton buttonTypeExternalAI, CLabel AIName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeTypeToExternalAI(ChessSide side, CLabel AIName, CRadioButton btnHuman,
			CRadioButton btnInternalAI) {
		// TODO Auto-generated method stub
		
	}
	
}
