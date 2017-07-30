package ViewComponent;

import java.awt.Font;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CLabel extends JLabel{
	
	public static final Font SettingTitleBold = new Font("標楷體", Font.BOLD, 20);
	public static final Font SettingTitlePlain = new Font("標楷體", Font.PLAIN, 20);
	public static final Font SettingAIName = new Font("標楷體", Font.BOLD, 20);
	public static final Font SelectingAIFileName = new Font("標楷體", Font.BOLD, 20);
	public static final Font SelectingAITitleName = new Font("標楷體", Font.BOLD, 20);
	public static final Font CompetitionVS = new Font("標楷體", Font.BOLD, 20);
	
	public CLabel(String text, int horizontalAlignment){
		super(text,horizontalAlignment);
	}
	
	public void setAttr(int locX,int locY, int width, int height,Font font){
		this.setLocation(locX, locY);
		this.setSize(width,height);
		this.setFont(font);
	}

}
