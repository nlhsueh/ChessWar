package Share;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ChessGame.PlayerInfo;
import NormalPattern.PlayingPlayerView;
import ObserverData.MoveRecordData;

public class PlayingLastMoveView implements Observer{
	
	private JPanel panel ;
	private PlayerInfo playerInfo;
	
	private JLabel lastMoveLabel;
	
	public PlayingLastMoveView(JPanel panel, PlayerInfo playerInfo){
		this.panel = panel;
		this.playerInfo = playerInfo ;
		this.iniView();
	}
	
	private void iniView(){
		this.lastMoveLabel = new JLabel("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"1"+"</font> 手未下</html>",SwingConstants.CENTER);
		this.lastMoveLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		this.lastMoveLabel.setSize(167,40);
		this.lastMoveLabel.setLocation(0,440);
		
		this.panel.add(this.lastMoveLabel);
	}
	
	public void resetLastMove(){
		this.lastMoveLabel.setText("<html>第 <font color='"+PlayingPlayerView.colorRed+"'>"+"1"+"</font> 手未下</html>");
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof MoveRecordData){
			MoveRecordData data = (MoveRecordData) arg;
			// 棋子移動後，更新最近一手在看板
			if(data.getSide() == this.playerInfo.getPlayerColor()){
				this.lastMoveLabel.setText("<html>"+data.getMoveWord()+"</html>");
			}
		}
	}
}