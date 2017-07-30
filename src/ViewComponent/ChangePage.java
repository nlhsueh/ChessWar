package ViewComponent;

import java.awt.CardLayout;

import javax.swing.JPanel;

import FrameView.MainFrame;
import Replay.ReplayMainPanel;
import Save.RecordGame;

public class ChangePage {
	
	private static JPanel framePanel ;
	private static CardLayout cardLayout;
	
	public ChangePage(JPanel framePanel, CardLayout cardLayout){
		ChangePage.framePanel = framePanel;
		ChangePage.cardLayout = cardLayout;
	}
	
	public static void changeToPlaySettingPage(){
		cardLayout.show(framePanel, "PlaySetting");
	}
	
	public static void changeToMainPage(){
		cardLayout.show(framePanel, "MainFrame");
	}
	
	public static void changeToLoadAIPage(){
		cardLayout.show(framePanel, "LoadingAI");
	}
	
	public static void changeToDevelopSettingPage(){
		cardLayout.show(framePanel, "DevelopSetting");
	}
	
	public static void changeToPlayPage(){
		cardLayout.show(framePanel, "Play");
	}
	
	public static void changeToCompetitionPage(){
		framePanel.add(new CompetitionPattern.SettingView(), "CompetitionSetting");
		cardLayout.show(framePanel, "CompetitionSetting");
	}
	
	public static void changeToCompetitionPlayingPage(){
		cardLayout.show(framePanel, "CompetitionPlaying");
	}
	
	public static void changeToCompetitionWatchPage(RecordGame record){
		ReplayMainPanel r = new ReplayMainPanel(framePanel,record);
		r.setPattern(2);
		cardLayout.show(MainFrame.framePanel ,"WatchGame");
	}
	
}
