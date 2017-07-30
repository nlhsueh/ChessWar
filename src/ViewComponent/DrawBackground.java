package ViewComponent;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import FrameView.MainFrame;

public class DrawBackground extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public DrawBackground(String type){
		this.setSize(924,760);
		
		String path = null;
		switch(type){
			case "mainFrame":
				path = "/img/mainBackground.jpg";
				break;
			case "playSetting":
			case "developSetting":
				path = "/img/settingBackground.jpg";
				break;
			case "playPage":
				path = "/img/playBackground.jpg";
				break;
			case "replay":
				path = "/img/watchGameBackground.jpg";
				break;
		}
		// Background Image Set
		ImageIcon img = new ImageIcon(MainFrame.class.getClass().getResource(path));
		img = (new ImageIcon(img.getImage().getScaledInstance(924, 760, Image.SCALE_SMOOTH)));
		this.setLayout(new GridLayout(1, 0, 0, 0));
		this.add(new JLabel(img));
	}
	
}
