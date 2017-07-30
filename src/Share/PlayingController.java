package Share;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import FrameView.MainFrame;
import Replay.ReplayMainPanel;

public class PlayingController implements ActionListener{
	
	private PlayingModel playingModel ;
	
	private JPanel framePanel ;
	private CardLayout cardLayout ;
	
	public PlayingController(PlayingModel model){
		this.playingModel = model ;
	}
	
	public void setFramePanel(JPanel panel){
		this.framePanel = panel ;
	}
	
	public void setCardLayout(CardLayout cardLayout){
		this.cardLayout = cardLayout;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		switch(cmd){
			
		case "Play":{
			// 開始對弈
			this.playingModel.clickGameStart();
			break;
		} 
		case "WatchGame":{
			// 查看對戰過程
			/* 觀看模式 */
			new ReplayMainPanel(this.framePanel,this.playingModel.getGameRecord());
			this.cardLayout.show(this.framePanel,"WatchGame");
			break;
		}
		case "Pause":{
			// 暫停遊戲
			this.playingModel.clickPauseButton();
			break;
		}
		case "Inverse":{
			// 顛倒棋盤
			this.playingModel.clickInverseButton();
			break;
		}
		case "Exit":{
			// 回主選單
			String text = "是否確定要離開遊戲？\n提醒您，您將失去目前的遊戲記錄！";
			ImageIcon icon = new ImageIcon(new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/alert_a.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
			int opt = JOptionPane.showConfirmDialog(this.framePanel,text,"離開遊戲",
										JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,icon);
			if(opt == JOptionPane.YES_OPTION){
				this.playingModel.getGameModel().leaveGame();
				this.cardLayout.show(this.framePanel, "MainFrame");
			}
			break;
		}
		case "ResetChessBoard":{
			this.playingModel.clickResetChessBoardButton();
			break;
		}
		default:
			return;
		}
	}
	
}
