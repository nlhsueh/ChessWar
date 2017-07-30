package NormalPattern;

import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Share.AbstractPlayingView;
import Share.PlayingController;
import Share.PlayingModel;
import ViewComponent.DrawBackground;

public class PlayingView extends AbstractPlayingView{
	
	public PlayingView(PlayingModel model, JPanel framePanel){
		this.model = model ;
		this.framePanel = framePanel;
		this.framePanel.add(this.layeredPane,"Play");
		this.controller = new PlayingController(model) ;
		this.controller.setFramePanel(framePanel);
		this.controller.setCardLayout((CardLayout) this.framePanel.getLayout());
		this.setTitle();
		this.setChessBoard();
		layeredPane.add(new DrawBackground("playPage"), new Integer(0));
		this.setButtonSet();
		
		this.panelP1 = new PlayingPlayerView(this.model.getGameSetting(), 1);
		this.panelP2 = new PlayingPlayerView(this.model.getGameSetting(), 2);
		
		layeredPane.add(panelP1, new Integer(1));
		layeredPane.add(panelP2, new Integer(1));
	}
	
	/**
	 * 建立Title
	 * @return
	 */
	protected void setTitle(){
		JLabel title = new JLabel();
		
		title.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+"對弈模式"+"</font></html>");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Microsoft JhenHei", Font.PLAIN, 40));
		title.setSize(924,50);
		title.setLocation(0,10);
		
		layeredPane.add(title, new Integer(0));
	}
	
}
