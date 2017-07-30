package DevelopPattern;

import java.io.Serializable;

import Share.GameSetting;

public class DevelopGameSetting extends GameSetting implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* 拷貝建構子 */
	public DevelopGameSetting(GameSetting gameSetting){
		this.player1 = gameSetting.player1 ;
		this.player2 = gameSetting.player2 ;
		this.timeout = gameSetting.timeout ;
	}
	
	public DevelopGameSetting(){
		
	}
	
	/**
	 * 檢查遊戲設定是否完成設定，並回傳設定狀態
	 * @return String
	 */
	public String getSettingStatus(){
		if(this.player1 != 0 && this.player2 != 0 && this.timeout != 0){
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
			return text ;
		}
	}
	
	public GameSetting getReverseSetting(){
		DevelopGameSetting reverse = new DevelopGameSetting();
		reverse.player1 = this.player2;
		reverse.player2 = this.player1;
		reverse.timeout = this.timeout;
		
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
