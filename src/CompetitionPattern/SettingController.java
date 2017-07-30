package CompetitionPattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import AI.AbstractPlayer;
import ChessGame.Chess;
import ChessGame.ChessSide;
import FrameView.MainFrame;
import NormalPattern.NormalGameSetting;
import ViewComponent.CButton;
import ViewComponent.CLabel;
import ViewComponent.ChangePage;
import ViewComponent.LinesComponent;

public class SettingController{

	private NormalGameSetting gameSetting;
	private JLayeredPane AIListPanel;
	private JScrollPane scrollPane ;
	private ArrayList<AIInfo> AIInfo = new ArrayList<AIInfo>();
	private ArrayList<GameInfo> scheduleGame = new ArrayList<GameInfo>();
	
	private int AINum = 0 ;
	private boolean changeStatus = false ;
	private boolean isLoadedAIFinished = false ;
	private CButton selectChangeBtn = null;
	private int selectChangeNum = 0 ;
	
	private LinesComponent lineComp = new LinesComponent();
	
	public SettingController(){
		this.gameSetting = new NormalGameSetting();
	}
	
	public void setAIListPanel(JLayeredPane AIListPanel){
		this.AIListPanel = AIListPanel ;
	}
	
	public void setScrollPane(JScrollPane scrollPane){
		this.scrollPane = scrollPane;
	}

	/**
	 * 當修改「timeout」
	 */
	public void changeTimeout(int timeout){
		this.gameSetting.timeout = timeout ;
	}
	
	/**
	 * 當選擇「載入AI的路徑」
	 */
	public void selectLoadingPath(){
		/* 選擇路徑 */
//		JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + File.separator); /* default path */
		JFileChooser fc = new JFileChooser("/Users/defsrisars/Study/Java/CompetitionAI");
		
		fc.setDialogTitle("選擇載入路徑");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /* folder only */
		fc.setAcceptAllFileFilterUsed(false); /* disable all files */
		
		int returnValue = fc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) //判斷是否選擇檔案 
		{ 
			/* success select */
			File file = fc.getCurrentDirectory(); /* get Select File */
			loadAIFromPath(file);
		} else{
			/* click cancel */
		}
		
	}
	
	@SuppressWarnings("serial")
	public void loadAIFromPath(File directory){
		/* 載入檔案 */
		ArrayList<File> files = new ArrayList<File>();
		importFiles(directory.getPath(),files);
		/* 載入class */
		for(int i=0;i<files.size();i++){
			File file = files.get(i);
			/* 取得 folder/filename(package/class name) */
			// folderName(package)
			int pos = file.getPath().lastIndexOf(".");
			String s = file.getPath().substring(0,pos);
			pos = s.lastIndexOf("/");
			s = s.substring(0,pos);
			pos = s.lastIndexOf("/");
			String folderName = s.substring(pos+1);
			// fileName(class)
			pos = file.getName().lastIndexOf(".");
			String fileName = file.getName().substring(0,pos);
			// load Class name
			String loadClassName = folderName+"."+fileName;
			
			/* 取得路徑 */
			String path = "file:"+directory+"/";
			
			/* 嘗試讀取類別 */
			try {
				URL url1 = new URL(path);
				URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1}, Thread.currentThread().getContextClassLoader());
				urlClassLoader.loadClass(loadClassName);
				Class<?> c = Class.forName(loadClassName,true,urlClassLoader);
				/* 測試是否繼承AbstractAI與建構子是否無誤 */
				if(AbstractPlayer.class.isAssignableFrom(c)){
					/* 建立建構子參數 */
					Class<?>[] params = new Class[2];
		            params[0] = Chess[].class;
		            params[1] = ChessSide.class;
		            Constructor<?> constructor = c.getConstructor(params);
					AbstractPlayer a = (AbstractPlayer) constructor.newInstance(new Chess[32], ChessSide.RED);	
		            /* 更新AI List */
					CButton btn = new CButton(CButton.SettingChangeNickname,new AbstractAction(folderName) {
					    public void actionPerformed(ActionEvent e) {
					    	clickPlayerButton((CButton) e.getSource());
					    }
					});
					btn.setOpaque(false);
					btn.setEnabled(false);
					/* data update */
					this.AIInfo.add(new AIInfo(folderName,fileName,this.AINum));
					this.AIInfo.get(this.AINum).setCButton(btn);
					this.AIInfo.get(this.AINum).AIClass = a ;
					this.AINum++;
				}else{
					c.newInstance();
				}
			}
			catch(ClassCastException | NoSuchMethodException e){
				JOptionPane.showMessageDialog(new JPanel(), "package "+folderName+" 的class沒有繼承abstractPlayer類別！\n請確認後再試一次！"
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				return;
			}
			catch (InstantiationException e) {
				JOptionPane.showMessageDialog(new JPanel(), "package "+folderName+" 的class初始化失敗！\n請修改後再試一次！\nError Code : InstantiationException"
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
				return;
			}
			catch (InvocationTargetException e) {
				JOptionPane.showMessageDialog(new JPanel(), "您沒有載入到主類別使用到的副類別！\n請先載入所有副類別後再試一次！"
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
				return;
			}
			catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(new JPanel(), "Error Code : IllegalAccessException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
				return;
			}
			catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(new JPanel(), "請勿Override建構子！\n請修改後再試一次！"
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
				return;
			}
			catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(new JPanel(), "package "+folderName+" 的class無法找到與輸入相對應的AI類別！"
						, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
				return;
			}
			catch (NoClassDefFoundError e){
				JOptionPane.showMessageDialog(new JPanel(), "package "+folderName+" 的class類別名稱必須與檔名相同，請重新確認後再試一次！"
						, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
				return;
			}
			catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(new JPanel(), e
						, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
				e.printStackTrace();
			}
			
		}
		
		if(this.AINum != 8){
			JOptionPane.showMessageDialog(new JPanel(), "載入AI必須為8個！請重新載入！"
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
		}else{
			this.isLoadedAIFinished = true;
			this.scheduleGame();
		}
		
	}
	
	public void importFiles(String directoryName, ArrayList<File> files) {
		File directory = new File(directoryName);
	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	        	String extension = "";
	        	int i = file.getPath().lastIndexOf('.');
	        	if (i > 0) {
	        	    extension = file.getPath().substring(i+1);
	        	}
	        	if(extension.equals("class")) files.add(file);
	        } else if (file.isDirectory()) {
	            importFiles(file.getAbsolutePath(), files);
	        }
	    }
	}
	
	public void clickChangePlayer(){
		if(this.isLoadedAIFinished == false) return ;
		
		if(this.changeStatus == true){
			this.changeStatus = false ;
			for(int i=0;i<this.AIInfo.size();i++){
				this.AIInfo.get(i).getCButton().setEnabled(false); ;
			}
		}else{
			this.changeStatus = true ;
			for(int i=0;i<this.AIInfo.size();i++){
				this.AIInfo.get(i).getCButton().setEnabled(true); ;
			}
		}
		this.selectChangeNum = 0 ;
		this.selectChangeBtn = null ;
	}
	
	public void clickPlayerButton(CButton btn){
		if(this.selectChangeNum == 0){
			btn.setEnabled(false);
			this.selectChangeBtn = btn ;
			this.selectChangeNum = 1 ;
		}else if(this.selectChangeNum == 1 && this.selectChangeBtn != btn){
			for(int i=0;i<this.AIInfo.size();i++){
				this.AIInfo.get(i).getCButton().setEnabled(false); ;
			}
			/* btnno & selectChangeNo  change place */
			// change Text
			CButton btn2 = this.selectChangeBtn;
			String s = btn2.getText();
			btn2.setText(btn.getText());
			btn.setText(s);
			// change GameInfo & AIInfo
			this.changeGameInfo(btn.getGameInfo(), btn.getPlayer(), btn2.getGameInfo(), btn2.getPlayer());
			
			// !! 交換 & 觀看總步數
			
			/* reset */
			this.selectChangeBtn = null ;
			this.selectChangeNum = 0 ;
			changeStatus = false ;
		}else{
			this.selectChangeBtn = null ;
			this.selectChangeNum = 0 ;
			btn.setEnabled(true);
		}
	}
	
	private void changeGameInfo(GameInfo a, int ap, GameInfo b, int bp){
		
		/* change GameInfo */
		AIInfo tmpA = null;
		AIInfo tmpB = null;
		// tmp = a
		if(ap == 1){
			tmpA = a.getPlayer1();
		}else if(ap == 2){
			tmpA = a.getPlayer2();
		}
		// a = b
		if(bp == 1){
			tmpB = (b.getPlayer1());
		}else if(bp == 2){
			tmpB = (b.getPlayer2());
		}
		if(ap == 1){
			a.setPlayer1(tmpB);
		}else if(ap == 2){
			a.setPlayer2(tmpB);
		}
		// b = tmp
		if(bp == 1){
			b.setPlayer1(tmpA);
		}else if(bp == 2){
			b.setPlayer2(tmpA);
		}
		
	}
	
	public void clickGameStart(){
		new Competition(this.scheduleGame,this.gameSetting.timeout);
		ChangePage.changeToCompetitionPlayingPage();
	}
	
	/* 設定第一次賽程 */
	private void scheduleGame(){
		
		int gameNo = 1 ;
		/* 初始4場配對 */
		for(int i=0;i<8;i+=2){
			scheduleGame.add(new GameInfo(this.AIInfo.get(i),this.AIInfo.get(i+1),gameNo++));
		}
		/* 4場遊戲配對 */
		for(int i=0;i<4;i+=2){
			scheduleGame.get(i).setAnotherGame(scheduleGame.get(i+1));
			scheduleGame.get(i+1).setAnotherGame(scheduleGame.get(i));
		}
		/* 將按鈕對到GameInfo */
		for(int i=0;i<8;i++){
			this.AIInfo.get(i).getCButton().setGameInfo(scheduleGame.get(i/2));
			if(i%2 == 0){
				this.AIInfo.get(i).getCButton().setPlayer(1);
			}else{
				this.AIInfo.get(i).getCButton().setPlayer(2);
			}
		}
		
		/* 晝賽程表在AIListPanel的lineComp */
		// 上方一半，下方一半，先晝第一層
		/* 遊戲配對 */
		for(int i=0;i<4;i+=2){
			scheduleGame.get(i).setAnotherGame(scheduleGame.get(i+1));
			scheduleGame.get(i+1).setAnotherGame(scheduleGame.get(i));
		}
		
		/* 晝賽程表在AIListPanel的lineComp */
		// 上方一半，下方一半，先晝第一層
		/* AIListPanel的大小 */
		this.AIListPanel.setPreferredSize(new Dimension((10+SettingView.playerButtonWidth)*(4),260));
		if(this.AIListPanel.getPreferredSize().getWidth() > 624){
			this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		/* 晝線container */
		lineComp.setPreferredSize(new Dimension((10+SettingView.playerButtonWidth)*(4),290));
		lineComp.setSize(new Dimension((10+SettingView.playerButtonWidth)*(4),290));
		lineComp.setLocation(0, 0);
		
		/* dx, dy */
		int dx = ( this.AIListPanel.getSize().width - SettingView.playerButtonWidth*4 - 10 * 3 ) / 2 ;
		int dy = 20 ;
		
		/* 放置buttons與晝線 */
		for(int i=0;i<8;i++){
			/* 取得button，並設定大小 */
			CButton btn = this.AIInfo.get(i).getCButton() ;
			btn.setSize(SettingView.playerButtonWidth, SettingView.playerButtonHeight);
			/* 排列button與晝線 */
			if(i>=4){
				/* 下排 */
				btn.setLocation((10+SettingView.playerButtonWidth)*(i-4)+dx, 210 +dy );
				int buttonCenterX = (10+SettingView.playerButtonWidth)*(i-4)+(SettingView.playerButtonWidth/2)+dx;
				int buttonTopY = (210) + dy;
				/* Button下方凸出去的第一條線 */
				lineComp.addLine( buttonCenterX , buttonTopY, buttonCenterX, buttonTopY-15,Color.WHITE);
				/* 連向兩button中央的水平線 */
				if(i%2 == 0){
					/* 向右 */
					int twoButtonCenterX = ( (10+SettingView.playerButtonWidth)*(i-4) + ((10+SettingView.playerButtonWidth)*((i-3))+SettingView.playerButtonWidth)) /2 ;
					lineComp.addLine( buttonCenterX , buttonTopY-15, twoButtonCenterX, buttonTopY-15,Color.WHITE);
				}else{
					/* 向左 */
					int thisButtonRight = ((10+SettingView.playerButtonWidth)*(i-3)+SettingView.playerButtonWidth);
					int previousButtonLeft = (10+SettingView.playerButtonWidth)*((i-6)) ;
					int twoButtonCenterX = ( thisButtonRight + previousButtonLeft) /2 ;
					lineComp.addLine( buttonCenterX , buttonTopY-15, twoButtonCenterX, buttonTopY-15,Color.WHITE);
				}
			}else{
				/* 上排 */
				btn.setLocation((10+SettingView.playerButtonWidth)*(i)+dx, 20 + dy);
				int buttonCenterX = (10+SettingView.playerButtonWidth)*(i)+(SettingView.playerButtonWidth/2)+dx;
				int buttonBottomY = (20)+(SettingView.playerButtonHeight) + dy;
				/* Button下方凸出去的第一條線 */
				lineComp.addLine( buttonCenterX , buttonBottomY-3, buttonCenterX, buttonBottomY+15,Color.WHITE);
				/* 連向兩button中央的水平線 */
				if(i%2 == 0){
					/* 向右 */
					int twoButtonCenterX = (((10+SettingView.playerButtonWidth)*(i)) + ((10+SettingView.playerButtonWidth)*(i+1)+SettingView.playerButtonWidth)) /2 ;
					lineComp.addLine( buttonCenterX , buttonBottomY+15, twoButtonCenterX, buttonBottomY+15,Color.WHITE);
				}else{
					/* 向左 */
					int twoButtonCenterX = (((10+SettingView.playerButtonWidth)*(i)+SettingView.playerButtonWidth) + (10+SettingView.playerButtonWidth)*(i-1)) /2 ;
					lineComp.addLine( buttonCenterX , buttonBottomY+15, twoButtonCenterX, buttonBottomY+15,Color.WHITE);
				}
			}
			AIListPanel.add(btn,0);
		}
		/* 中央字 */
		CLabel word = new CLabel("第一輪賽程表",SwingConstants.CENTER);
		word.setAttr(0, 125 +dy , (10+SettingView.playerButtonWidth)*(this.AINum/2+1), 30, CLabel.CompetitionVS);
		AIListPanel.add(word,2);
		
		AIListPanel.add(lineComp,1);
		AIListPanel.updateUI();
	}

}

