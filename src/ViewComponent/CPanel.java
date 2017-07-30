package ViewComponent;

import java.awt.LayoutManager;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CPanel extends JPanel{
	
	public CPanel(int locX,int locY, int width, int height, LayoutManager layout){
		this.setLocation(locX, locY);
		this.setSize(width, height);
		this.setLayout(layout);
		this.setOpaque(false);
	}
	
	public CPanel(LayoutManager layout){
		super(layout);
	}
	
}
