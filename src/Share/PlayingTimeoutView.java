package Share;

import java.awt.Font;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ChessGame.PlayerInfo;
import FrameView.MainFrame;
import NormalPattern.PlayingPlayerView;
import ObserverData.ChangeTurnData;
import ObserverData.GameOverData;
import ObserverData.LeaveGameData;

public class PlayingTimeoutView implements Observer{
	
	private JPanel panel ;
	private PlayerInfo playerInfo ;
	
	private JLabel roundTimeLabel;
	private JLabel totalTimeLabel;
	
	private Timer timer;
	private TimeTask task;
	private int totalTime = 0;
	private int roundTime;
	private int defaultTimeout ;
	static final private DecimalFormat df = new DecimalFormat("##00") ;
	
	public PlayingTimeoutView(JPanel panel, PlayerInfo playerInfo,int timeout){
		this.panel = panel ;
		this.playerInfo = playerInfo ;
		this.defaultTimeout = timeout ;
		this.iniView();
	}
	
	private void iniView(){
		
		// Round time
		if(this.defaultTimeout == -1){
			// 無時間限制
			this.roundTimeLabel = new JLabel("無時間限制",SwingConstants.CENTER);
		}else{
			// 有時間限制
			this.roundTimeLabel = new JLabel("Round: "+String.format("%02d:%02d", defaultTimeout/60,defaultTimeout%60),SwingConstants.CENTER);
		}
		roundTimeLabel.setFont(new Font("Times New Roman", Font.BOLD, PlayingPlayerView.fontSizeContext));
		roundTimeLabel.setSize(167,40);
		roundTimeLabel.setLocation(0,280);
		
		ImageIcon icon_round = null;
		icon_round = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/timer_a.png"));
		icon_round = (new ImageIcon(icon_round.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		roundTimeLabel.setIcon(icon_round);
		
		// Total time
		this.totalTimeLabel = new JLabel("Total: "+String.format("%02d:%02d", 0, 0),SwingConstants.CENTER);
		totalTimeLabel.setFont(new Font("Times New Roman", Font.BOLD, PlayingPlayerView.fontSizeContext));
		totalTimeLabel.setSize(167,40);
		totalTimeLabel.setLocation(0,320);
		
		ImageIcon icon_total = null;
		icon_total = new ImageIcon(MainFrame.class.getClass().getResource("/img/icon/clock_a.png"));
		icon_total = (new ImageIcon(icon_total.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		totalTimeLabel.setIcon(icon_total);
		
		this.panel.add(roundTimeLabel);
		this.panel.add(totalTimeLabel);
	}
	
	public void setTimer(){
		this.timer = new Timer();
		this.task = new TimeTask(roundTimeLabel,totalTimeLabel,this.defaultTimeout,this.totalTime);
		timer.schedule(task,0,1000);
	}
	
	public void iniTimer(){
		this.totalTime = 0;
		// Round time
		if(this.defaultTimeout == -1){
			// 無時間限制
			this.roundTimeLabel = new JLabel("無時間限制",SwingConstants.CENTER);
		}else{
			// 有時間限制
			this.roundTimeLabel.setText("Round: "+df.format(this.defaultTimeout/60)+":"+df.format(this.defaultTimeout%60));
		}
		this.totalTimeLabel.setText("Total: "+df.format(0)+":"+df.format(0));
	}
	
	public void startTimer(){
		this.timer = new Timer();
		this.task = new TimeTask(roundTimeLabel,totalTimeLabel,this.defaultTimeout,this.totalTime);
		timer.schedule(task,0,1000);
	}
	
	public void stopTimer(){
		this.task.stop();
		this.totalTime = this.task.getTotalTime();
		this.timer.cancel();
	}
	
	public void pauseTimer(){
		this.timer.cancel();
		this.task.pause();
		this.roundTime = this.task.getRoundTime();
		this.totalTime = this.task.getTotalTime();
	}
	
	public void continueTimer(){
		this.timer = new Timer();
		this.task = new TimeTask(roundTimeLabel,totalTimeLabel,this.defaultTimeout,this.totalTime,this.roundTime);
		this.timer.schedule(task,0,1000);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ChangeTurnData){
			ChangeTurnData data = (ChangeTurnData) arg;
			if(data.getTurn() == this.playerInfo.getPlayerColor()){
				this.startTimer();
			}else{
				this.stopTimer();
			}
		}else if(arg instanceof GameOverData){
			// 停止計時
			if(this.task != null && this.timer != null)
				this.stopTimer();
		}else if(arg instanceof LeaveGameData){
			if(this.task != null && this.timer != null)
				this.stopTimer();
		}
	}
}