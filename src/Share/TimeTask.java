package Share;


import java.text.DecimalFormat;
import java.util.TimerTask;

import javax.swing.JLabel;

import ChessGame.GameModel;

public class TimeTask extends TimerTask{
	
	static private GameModel gameModel;
	
	private JLabel roundLabel ;
	private JLabel totalLabel ;
	private int defaultTimeout ;
	private int roundTime ;
	private int totalTime ;
	private DecimalFormat df_min ;
	private DecimalFormat df_sec ;
	private boolean noTimeout = false ;
	static private String colorRed = "#ff0000" ;
	static private String colorBlack = "#222222";
	static private String colorPause = "#34bf49" ;
	
	public TimeTask(JLabel roundLabel,JLabel totalLabel ,int defaultTimeout, int totalTime){
		this.roundLabel = roundLabel ;
		this.totalLabel = totalLabel ;
		this.defaultTimeout = defaultTimeout ;
		this.totalTime = totalTime;
		if(this.defaultTimeout == -1){
			this.noTimeout = true ;
		}
		this.roundTime = this.defaultTimeout ;
		
		// 顯示格式
		df_min = new DecimalFormat("##00");
		df_sec = new DecimalFormat("##00"); // ("##,##00.00")
//		df_sec.setRoundingMode(RoundingMode.HALF_UP); // 四捨五入
	}
	
	public TimeTask(JLabel roundLabel,JLabel totalLabel ,int defaultTimeout, int totalTime,int roundTime){
		this.roundLabel = roundLabel ;
		this.totalLabel = totalLabel ;
		this.defaultTimeout = defaultTimeout ;
		this.totalTime = totalTime;
		if(this.defaultTimeout == -1){
			this.noTimeout = true ;
		}
		this.roundTime = roundTime ;
		
		// 顯示格式
		df_min = new DecimalFormat("##00");
		df_sec = new DecimalFormat("##00"); // ("##,##00.00")
//		df_sec.setRoundingMode(RoundingMode.HALF_UP);  //四捨五入
	}
	
	public int getTotalTime(){
		return this.totalTime;
	}
	
	public int getRoundTime(){
		return this.roundTime;
	}
	
	public static void setGameModel(GameModel model){
		TimeTask.gameModel = model ;
	}
	
	public void stop(){
		if(this.noTimeout == true){
			totalLabel.setText("<html><font color='"+colorBlack+"'>Total: "+df_min.format(totalTime/60)+":"+df_sec.format(totalTime%60)+"</font></html>");
		}else{
			roundLabel.setText("<html><font color='"+colorBlack+"'>Round: "+df_min.format(roundTime/60)+":"+df_sec.format(roundTime%60)+"</font></html>");
			totalLabel.setText("<html><font color='"+colorBlack+"'>Total: "+df_min.format(totalTime/60)+":"+df_sec.format(totalTime%60)+"</font></html>");
		}
	}
	
	public void pause(){
		if(this.noTimeout == true){
			totalLabel.setText("<html><font color='"+colorPause+"'>Total: "+df_min.format(totalTime/60)+":"+df_sec.format(totalTime%60)+"</font></html>");
		}else{
			roundLabel.setText("<html><font color='"+colorPause+"'>Round: "+df_min.format(roundTime/60)+":"+df_sec.format(roundTime%60)+"</font></html>");
			totalLabel.setText("<html><font color='"+colorPause+"'>Total: "+df_min.format(totalTime/60)+":"+df_sec.format(totalTime%60)+"</font></html>");
		}
	}

	@Override
	public void run() {
		roundTime --;
		totalTime ++;
		// 無時間限制
		if(this.noTimeout == true){
			totalLabel.setText("<html><font color='"+colorRed+"'>Total: "+df_min.format(totalTime/60)+":"+df_sec.format(totalTime%60)+"</font></html>");
		}else{
		// 有時間限制
			// 超過時間限制 Lose
			if(this.roundTime < 0){
				// 待實作，判輸，遊戲結束
				TimeTask.gameModel.timeoutGameOver();
				roundLabel.setText("<html><font color='"+colorRed+"'>Round: "+"超過時間"+"</font></html>");
				totalLabel.setText("<html><font color='"+colorRed+"'>Total: "+df_min.format(totalTime/60)+":"+df_sec.format(totalTime%60)+"</font></html>");
			}else{
			// 無超過時間限制 繼續計時
				roundLabel.setText("<html><font color='"+colorRed+"'>Round: "+df_min.format(roundTime/60)+":"+df_sec.format(roundTime%60)+"</font></html>");
				totalLabel.setText("<html><font color='"+colorRed+"'>Total: "+df_min.format(totalTime/60)+":"+df_sec.format(totalTime%60)+"</font></html>");
			}
		}
	}

}
