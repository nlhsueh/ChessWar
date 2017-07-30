package Share;

import java.io.Serializable;

public abstract class GameSetting implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public int player1 = 0 ;   // 1 - human ,   2 - Internal AI , 3- External AI
	public int player2 = 0 ;   // 1 - human ,   2 - Internal AI , 3- External AI
	public int timeout = 0 ;   // unit: second , -1 = no limit
	public String externalAIName1 = null;
	public String externalAIName2 = null;
	public String nickname1 = "Player1";
	public String nickname2 = "Player2";
	
	public abstract String getSettingStatus();
	public abstract GameSetting getReverseSetting();
	
	
}
