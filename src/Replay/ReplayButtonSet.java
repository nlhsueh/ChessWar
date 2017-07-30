package Replay;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ReplayButtonSet extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel framePanel ;
	private CardLayout cardLayout;
	private MotionController ctr = new MotionController();
	private int pattern ;
	
	public ReplayButtonSet(JPanel framePanel, int pattern){
		this.framePanel = framePanel;
		this.pattern = pattern;
		this.cardLayout = (CardLayout) this.framePanel.getLayout();
		
		this.setLocation(177, 650);
		this.setSize(569, 80);
		this.setOpaque(false);
		this.setLayout(new GridLayout(2, 5, 20, 0));
		
		JButton previous = new JButton("上一步");
		previous.setFont(new Font(null, Font.PLAIN, 17));
		previous.setActionCommand("Previous");
		previous.addActionListener(this);
		previous.setEnabled(false);

		JButton next = new JButton("下一步");
		next.setFont(new Font(null, Font.PLAIN, 17));
		next.setActionCommand("Next");
		next.addActionListener(this);
		
		JButton autoPlay = new JButton("自動播放");
		autoPlay.setFont(new Font(null, Font.PLAIN, 17));
		autoPlay.setActionCommand("AutoPlay");
		autoPlay.addActionListener(this);
		
		JButton reset = new JButton("重新開始");
		reset.setFont(new Font(null, Font.PLAIN, 17));
		reset.setActionCommand("Reset");
		reset.addActionListener(this);
		
		JButton inverse = new JButton("顛倒棋盤");
		inverse.setFont(new Font(null, Font.PLAIN, 17));
		inverse.setActionCommand("Inverse");
		inverse.addActionListener(this);
		
		JButton backGame = new JButton("結束觀看");
		backGame.setFont(new Font(null, Font.PLAIN, 17));
		backGame.setActionCommand("Exit");
		backGame.addActionListener(this);
		
		this.add(previous);
		this.add(next);
		this.add(autoPlay);
		this.add(reset);
		this.add(inverse);
		this.add(backGame);
		
		ctr.setNextBtn(next);
		ctr.setPreviousBtn(previous);
		ctr.setAutoBtn(autoPlay);
	}
	
	public MotionController getMotionController(){
		return this.ctr;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		switch(cmd){
		
		/* 頁面切換 */
		case "Next":{
			ctr.next();
			break;
		}
		case "Previous":{
			ctr.previous();
			break;
		}
		case "AutoPlay":{
			ctr.autoPlay();
			break;
		}
		case "PausePlay":{
			ctr.pausePlay();
			break;
		}
		case "Replay":{
			ctr.resetChess();
			ctr.autoPlay();
			break;
		}
		case "Inverse":{
			ctr.inverseChessBoard();
			break;
		}
		case "Reset":{
			ctr.resetChess();
			break;
		}
		//
		case "Exit":{
			// 切換到'主選單'頁面
			if(this.pattern == 1){
				cardLayout.show(this.framePanel,"Play");
			}else if(this.pattern == 2){
				cardLayout.show(this.framePanel,"CompetitionPlaying");
			}
			break;
		}

		default:
			return;
		}
	}
	
	public void setPattern(int pattern){
		this.pattern = pattern;
	}
	
}