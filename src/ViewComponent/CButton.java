package ViewComponent;

import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import CompetitionPattern.GameInfo;

@SuppressWarnings("serial")
public class CButton extends JButton{
	
	public static final Font MainFrame = new Font("標楷體", Font.PLAIN, 17);
	public static final Font SettingBottom = new Font("標楷體",Font.PLAIN,17);
	public static final Font SettingChangeNickname = new Font("標楷體",Font.PLAIN,17);
	public static final Font AddingBottom = new Font("標楷體",Font.PLAIN,17);
	public static final Font CompetitionBottom = new Font("標楷體",Font.PLAIN, 24);
	
	private GameInfo gameInfo = null;
	private int player = -1 ;
	
	public CButton(Font font,  AbstractAction abstractAction){
		super(abstractAction);
		this.setFont(font);
	}
	
	public CButton(int locX,int locY, int width, int height, Font font,  AbstractAction abstractAction){
		super(abstractAction);
		this.setLocation(locX, locY);
		this.setSize(width, height);
		this.setFont(font);
	}
	
	public void setGameInfo(GameInfo gameInfo){
		this.gameInfo = gameInfo;
	}
	
	public GameInfo getGameInfo(){
		return this.gameInfo;
	}
	
	public void setPlayer(int player){
		this.player = player;
	}
	
	public int getPlayer(){
		return this.player;
	}
	
}
