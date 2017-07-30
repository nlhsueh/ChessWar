package Share;

import ChessGame.ChessSide;
import ViewComponent.CLabel;
import ViewComponent.CRadioButton;

public abstract class AbstractSettingController {
	
	protected GameSetting gameSetting;
	protected ExternalAIData externalAIData;
	
	/**
	 * 選擇棋子類別之初始化設定
	 */
	public abstract void initType(ChessSide side,CRadioButton buttonTypeHuman,CRadioButton buttonTypeInternalAI,CRadioButton buttonTypeExternalAI, CLabel AIName); // 對弈模式
	public abstract void initType(ChessSide side,CRadioButton buttonTypeHuman,CRadioButton buttonTypeInternalAI,CRadioButton buttonTypeExternalAI); // 開發模式
	/**
	 * 點擊「重新設定」按鈕
	 */
	public abstract void clickReset();
	/**
	 * 當點擊「開始遊戲」按鈕
	 */
	public abstract void clickGameStart();
	/**
	 * 當修改「timeout」
	 */
	public abstract void changeTimeout(int timeout);
	/**
	 * 當修改「玩家類別」為「Human」
	 */
	public abstract void changeTypeToHuman(ChessSide side);
	/**
	 * 當修改「玩家類別」為「Internal AI」
	 */
	public abstract void changeTypeToInternalAI(ChessSide side);
	/**
	 * 當修改「玩家類別」為「External AI」
	 */
	public abstract void changeTypeToExternalAI(ChessSide side, CLabel AIName, CRadioButton btnHuman, CRadioButton btnInternalAI); // 對弈模式
	public abstract void changeTypeToExternalAI(ChessSide side); // 開發模式
}
