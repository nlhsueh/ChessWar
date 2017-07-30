package NormalPattern;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ChessGame.PlayerInfo;
import FrameView.MainFrame;
import Share.InterfacePlayeringPlayerInfoView;

public class PlayingPlayerInfoView implements InterfacePlayeringPlayerInfoView{

	private JPanel panel ;
	private PlayerInfo playerInfo ;
	private JLabel playerChessColorLabel = new JLabel();
	private JLabel playerNicknameLabel = new JLabel();
	private JLabel playerTypeLabel = new JLabel();
	
	public PlayingPlayerInfoView(JPanel panel, PlayerInfo playerInfo){
		this.panel = panel ;
		this.playerInfo = playerInfo ;
		this.iniView();
	}
	
	public void iniView(){
		ImageIcon icon = null;
		if(this.playerInfo.getColorWord().equals("紅子")){
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/7.png"));
		}else if(this.playerInfo.getColorWord().equals("黑子")){
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/14.png"));
		}
		icon = (new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		this.playerChessColorLabel.setIcon(icon);
		this.playerChessColorLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>"+this.playerInfo.getColorWord()+"</font></html>");
		this.playerChessColorLabel.setFont(new Font("Times New Roman", Font.BOLD, PlayingPlayerView.fontSizeContext));
		this.playerChessColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.playerChessColorLabel.setSize(167,50);
		this.playerChessColorLabel.setLocation(0,90);
		
		// player Type
		ImageIcon icon_type = null;
		int playerType = this.playerInfo.getPlayerType();
		if(playerType == 1){
			icon_type = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/user_a.png"));
			this.playerTypeLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>Human Being</font></html>");
		}else if(playerType == 2){
			icon_type = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/computer_a.png"));
			this.playerTypeLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>Build-in AI</font></html>");
		}else if(playerType == 3){
			icon_type = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/computer_a.png"));
			this.playerTypeLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>"+this.playerInfo.getExternalname()+"</font></html>");
		}
		icon_type = (new ImageIcon(icon_type.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		this.playerTypeLabel.setIcon(icon_type);
		this.playerTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.playerTypeLabel.setFont(new Font("Times New Roman", Font.BOLD, PlayingPlayerView.fontSizeContext));
		this.playerTypeLabel.setSize(167,50);
		this.playerTypeLabel.setLocation(0,150);
		
		this.playerNicknameLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>"+this.playerInfo.getPlayerNickname()+"</font></html>");
		this.playerNicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.playerNicknameLabel.setFont(new Font("Times New Roman", Font.BOLD, PlayingPlayerView.fontSizeContext));
		this.playerNicknameLabel.setSize(167,50);
		this.playerNicknameLabel.setLocation(0,200);
		
		ImageIcon icon_nickname = null;
		icon_nickname = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/star_a.png"));
		icon_nickname = (new ImageIcon(icon_nickname.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		playerNicknameLabel.setIcon(icon_nickname);
		
		this.panel.add(playerChessColorLabel);
		this.panel.add(playerTypeLabel);
		this.panel.add(playerNicknameLabel);
	}
	
	public void changePlayerUpdate(){
		ImageIcon icon = null;
		if(this.playerInfo.getColorWord().equals("紅子")){
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/7.png"));
		}else if(this.playerInfo.getColorWord().equals("黑子")){
			icon = new ImageIcon(MainFrame.class.getClass().getResource("/img/chess/14.png"));
		}
		icon = (new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		this.playerChessColorLabel.setIcon(icon);
		this.playerChessColorLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>"+this.playerInfo.getColorWord()+"</font></html>");
		
		this.playerNicknameLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>"+this.playerInfo.getPlayerNickname()+"</font></html>");
		
		ImageIcon icon_type = null;
		int playerType = this.playerInfo.getPlayerType();
		if(playerType == 1){
			icon_type = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/user_a.png"));
			this.playerTypeLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>Human Being</font></html>");
		}else if(playerType == 2){
			icon_type = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/computer_a.png"));
			this.playerTypeLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>Build-in AI</font></html>");
		}else if(playerType == 3){
			icon_type = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/computer_a.png"));
			this.playerTypeLabel.setText("<html><font color='"+this.playerInfo.getColorChess()+"'>"+this.playerInfo.getExternalname()+"</font></html>");
		}
		icon_type = (new ImageIcon(icon_type.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
	}

}