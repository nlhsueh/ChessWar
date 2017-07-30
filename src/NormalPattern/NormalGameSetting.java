package NormalPattern;

import java.io.Serializable;
//import java.util.Random;
import Share.GameSetting;

/**
 * 用來儲存遊戲設定的類別
 */

public class NormalGameSetting extends GameSetting implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/* 拷貝建構子 */
	public NormalGameSetting(GameSetting gameSetting){
		this.player1 = gameSetting.player1 ;
		this.player2 = gameSetting.player2 ;
		this.timeout = gameSetting.timeout ;
		this.externalAIName1 = gameSetting.externalAIName1;
		this.externalAIName2 = gameSetting.externalAIName2;
		this.nickname1 = gameSetting.nickname1;
		this.nickname2 = gameSetting.nickname2;
	}
	
	public NormalGameSetting(){
		
	}
	
	/**
	 * 檢查遊戲設定是否完成設定，並回傳設定狀態
	 * @return String
	 */
	@Override
	public String getSettingStatus(){
		if(this.player1 != 0 && this.player2 != 0 && this.timeout != 0 && this.nickname1 != null && this.nickname2 != null){
			return "okay" ;
		}else{
		String text = "";
			if(this.player1 == 0){
				text = text+"您尚未設定Player1類型！\n";
			}
			if(this.player2 == 0){
				text = text+"您尚未設定Player2類型！\n";
			}
			if(this.timeout == 0){
				text = text+"您尚未選擇Timeout設定！\n";
			}
			if(this.nickname1 == null){
				text = text+"您尚未設定Player1的暱稱！\n";
			}
			if(this.nickname2 == null){
				text = text+"您尚未設定Player2的暱稱！\n";
			}
			return text ;
		}
	}
	@Override
	public GameSetting getReverseSetting(){
		NormalGameSetting reverse = new NormalGameSetting();
		reverse.player1 = this.player2;
		reverse.player2 = this.player1;
		reverse.timeout = this.timeout;
		reverse.externalAIName1 = this.externalAIName2;
		reverse.externalAIName2 = this.externalAIName1;
		reverse.nickname1 = this.nickname2;
		reverse.nickname2 = this.nickname1;
		
		return reverse;
	}
	
//	/**
//	 * 設置隨機先手
//	 * @return String
//	 */
//	public void setRandomFirstPlayer(){
//		Random ran = new Random();
//		this.redPlayer = ran.nextInt(2)+1;
//	}
}
