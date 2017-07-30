package ViewComponent;

import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class CRadioButton extends JRadioButton{
	
	public static final Font SettingRadioButton = new Font("標楷體",Font.PLAIN,18);
	
	public CRadioButton(Font font,  AbstractAction abstractAction){
		super(abstractAction);
		this.setFont(font);
	}
	
	public void setAttr(int locX,int locY, int width, int height){
		this.setLocation(locX, locY);
		this.setSize(width,height);
	}
	
}
