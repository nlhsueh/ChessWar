package Replay;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import DevelopPattern.DevelopGameSetting;
import NormalPattern.NormalGameSetting;
import Share.AbstractPlayingPlayerView;
import Share.GameSetting;

public class ReplayGameInfoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private GameSetting gameSetting;
	
	// 記錄第幾手
	private JLabel roundLb ;
	private JLabel moveLb ;
	private JLabel totalNumberLb;
	
	public ReplayGameInfoView(GameSetting gameSetting, int totalRound){
		this.gameSetting = gameSetting;
		
		this.setLayout(null);
		this.setSize(167,565);
		this.setLocation(10, 75);
		this.setOpaque(false);
		
		this.setPlayerText();
		this.setGameText(totalRound);
	}
	
	public JLabel getRoundLb(){
		return this.roundLb;
	}
	
	public JLabel getMoveLb(){
		return this.moveLb;
	}
	
	private void setPlayerText(){
		
		/* 對弈模式 */
		if(gameSetting instanceof NormalGameSetting){
			this.setPlayerView_Normal();
		}else if(gameSetting instanceof DevelopGameSetting){
			this.setPlayerView_Develop();
		}
		
	}
	
	private void setPlayerView_Normal(){
		
		/* set Color Word */
		String p1ColorWord, p2ColorWord ;
		String p1ColorCode, p2ColorCode ;
		String colorNormal = AbstractPlayingPlayerView.colorNormal;
		p1ColorWord = "紅棋";
		p2ColorWord = "黑棋";
		p1ColorCode = AbstractPlayingPlayerView.colorRed;
		p2ColorCode = AbstractPlayingPlayerView.colorBlack;
		
		/* set Player Type */
		String p1Type = "",p2Type = "" ;
		if(this.gameSetting.player1 == 1){
			p1Type = "Human Being";
		}else if(this.gameSetting.player1 == 2){
			p1Type = "Build-in AI";
		}else if(this.gameSetting.player1 == 3){
			if(this.gameSetting.externalAIName1 != null){
				p1Type = this.gameSetting.externalAIName1;
			}else{
				JOptionPane.showMessageDialog(new JPanel(), "ReplayGameInfoView_Normal GameSetting External load Error.");
			}
		}else{
			JOptionPane.showMessageDialog(new JPanel(), "ReplayGameInfoView_Normal GameSetting p1Type load Error.");
		}
		if(this.gameSetting.player2 == 1){
			p2Type = "Human Being";
		}else if(this.gameSetting.player2 == 2){
			p2Type = "Build-in AI";
		}else if(this.gameSetting.player2 == 3){
			if(this.gameSetting.externalAIName2 != null){
				p2Type = this.gameSetting.externalAIName2;
			}else{
				JOptionPane.showMessageDialog(new JPanel(), "ReplayGameInfoView_Normal GameSetting External load Error.");
			}
		}else{
			JOptionPane.showMessageDialog(new JPanel(), "ReplayGameInfoView_Normal GameSetting p2Type load Error.");
		}
		
		JLabel player1Label = new JLabel();
		player1Label.setText("<html><font color='"+p1ColorCode+"'> Player1 "+p1ColorWord+"</font></html>");
		player1Label.setHorizontalAlignment(SwingConstants.CENTER);
		player1Label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player1Label.setSize(167,40);
		player1Label.setLocation(0,50);
		
		JLabel player1NicknameLabel = new JLabel();
		player1NicknameLabel.setText("<html><font color='"+AbstractPlayingPlayerView.colorTitle+"'>"+gameSetting.nickname1+"</font></html>");
		player1NicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player1NicknameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player1NicknameLabel.setSize(167,40);
		player1NicknameLabel.setLocation(0,90);
		
		JLabel player1TypeLabel = new JLabel();
		player1TypeLabel.setText("<html><font color='"+colorNormal+"'> "+p1Type+"</font></html>");
		player1TypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player1TypeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player1TypeLabel.setSize(167,40);
		player1TypeLabel.setLocation(0,130);
		
		JLabel player2Label = new JLabel();
		player2Label.setText("<html><font color='"+p2ColorCode+"'> Player2 "+p2ColorWord+"</font></html>");
		player2Label.setHorizontalAlignment(SwingConstants.CENTER);
		player2Label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player2Label.setSize(167,40);
		player2Label.setLocation(0,190);
		
		JLabel player2NicknameLabel = new JLabel();
		player2NicknameLabel.setText("<html><font color='"+AbstractPlayingPlayerView.colorTitle+"'>"+gameSetting.nickname2+"</font></html>");
		player2NicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player2NicknameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player2NicknameLabel.setSize(167,40);
		player2NicknameLabel.setLocation(0,230);
		
		JLabel player2TypeLabel = new JLabel();
		player2TypeLabel.setText("<html><font color='"+colorNormal+"'> "+p2Type+"</font></html>");
		player2TypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player2TypeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player2TypeLabel.setSize(167,40);
		player2TypeLabel.setLocation(0,270);
		
//		this.add(playerInfoLb);
		this.add(player1Label);
		this.add(player1NicknameLabel);
		this.add(player1TypeLabel);
		this.add(player2Label);
		this.add(player2NicknameLabel);
		this.add(player2TypeLabel);
	}
	
	private void setPlayerView_Develop(){
		/* set Color Word */
		String p1ColorWord, p2ColorWord ;
		String p1ColorCode, p2ColorCode ;
		String colorNormal = AbstractPlayingPlayerView.colorNormal;
		p1ColorWord = "紅棋";
		p2ColorWord = "黑棋";
		p1ColorCode = AbstractPlayingPlayerView.colorRed;
		p2ColorCode = AbstractPlayingPlayerView.colorBlack;
		
		/* set Player Type */
		String p1Type = "",p2Type = "" ;
		if(this.gameSetting.player1 == 1){
			p1Type = "Human Being";
		}else if(this.gameSetting.player1 == 2){
			p1Type = "Build-in AI";
		}else if(this.gameSetting.player1 == 3){
			p1Type = "開發撰寫AI";
		}else{
			JOptionPane.showMessageDialog(new JPanel(), "ReplayGameInfoView_Develop GameSetting p1Type load Error.");
		}
		if(this.gameSetting.player2 == 1){
			p2Type = "Human Being";
		}else if(this.gameSetting.player2 == 2){
			p2Type = "Build-in AI";
		}else if(this.gameSetting.player2 == 3){
			p2Type = "開發撰寫AI";
		}else{
			JOptionPane.showMessageDialog(new JPanel(), "ReplayGameInfoView_Develop GameSetting p2Type load Error.");
		}
		
		JLabel player1Label = new JLabel();
		player1Label.setText("<html><font color='"+p1ColorCode+"'> Player1 "+p1ColorWord+"</font></html>");
		player1Label.setHorizontalAlignment(SwingConstants.CENTER);
		player1Label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player1Label.setSize(167,40);
		player1Label.setLocation(0,50);
		
		JLabel player1TypeLabel = new JLabel();
		player1TypeLabel.setText("<html><font color='"+colorNormal+"'> "+p1Type+"</font></html>");
		player1TypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player1TypeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player1TypeLabel.setSize(167,40);
		player1TypeLabel.setLocation(0,130);
		
		JLabel player2Label = new JLabel();
		player2Label.setText("<html><font color='"+p2ColorCode+"'> Player2 "+p2ColorWord+"</font></html>");
		player2Label.setHorizontalAlignment(SwingConstants.CENTER);
		player2Label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player2Label.setSize(167,40);
		player2Label.setLocation(0,190);
		
		JLabel player2TypeLabel = new JLabel();
		player2TypeLabel.setText("<html><font color='"+colorNormal+"'> "+p2Type+"</font></html>");
		player2TypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player2TypeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		player2TypeLabel.setSize(167,40);
		player2TypeLabel.setLocation(0,270);
		
//		this.add(playerInfoLb);
		this.add(player1Label);
		this.add(player1TypeLabel);
		this.add(player2Label);
		this.add(player2TypeLabel);
	}
	
	private void setGameText(int totalRound){
		
		this.roundLb = new JLabel();
		this.roundLb.setText("<html>第 <font color='"+AbstractPlayingPlayerView.colorRed+"'>"+"0"+"</font> 手</html>");
		this.roundLb.setHorizontalAlignment(SwingConstants.CENTER);
		this.roundLb.setFont(new Font("Times New Roman", Font.BOLD, 20));
		this.roundLb.setSize(167,40);
		this.roundLb.setLocation(0,340);
		
		this.moveLb = new JLabel();
		this.moveLb.setText("<html>第 <font color='"+AbstractPlayingPlayerView.colorRed+"'>"+"1"+"</font> 手未下</html>");
		this.moveLb.setHorizontalAlignment(SwingConstants.CENTER);
		this.moveLb.setFont(new Font("Times New Roman", Font.BOLD, 20));
		this.moveLb.setSize(167,40);
		this.moveLb.setLocation(0,380);
		
		this.totalNumberLb = new JLabel();
		this.totalNumberLb.setText("<html>共 <font color='"+AbstractPlayingPlayerView.colorRed+"'>"+(totalRound-1)+"</font> 手</html>");
		this.totalNumberLb.setHorizontalAlignment(SwingConstants.CENTER);
		this.totalNumberLb.setFont(new Font("Times New Roman", Font.BOLD, 20));
		this.totalNumberLb.setSize(167,40);
		this.totalNumberLb.setLocation(0,420);
		
		this.add(this.roundLb);
		this.add(this.moveLb);
		this.add(this.totalNumberLb);
	}
	
}
