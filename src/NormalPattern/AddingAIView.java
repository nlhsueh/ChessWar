package NormalPattern;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import FrameView.MainFrame;
import Save.SaveSetting;
import Share.ExternalAIData;
import ViewComponent.CButton;
import ViewComponent.CLabel;
import ViewComponent.CPanel;
import ViewComponent.ChangePage;
import ViewComponent.DrawBackground;

@SuppressWarnings("serial")
public class AddingAIView extends JLayeredPane{
	
	private ExternalAIData AIData;
	
	private CLabel selectedMainAIFileName ;
	private CPanel selectedSubAIFilesPanel;
	private CPanel loadedAIListPanel;
	
	private AddingAIController ctr ;
	
	public AddingAIView(ExternalAIData AIData){
		this.AIData = AIData;
		/* add background panel, reuse Setting's */
		this.add(new DrawBackground("playSetting"), new Integer(0));
		/* 下方button列 */
		this.setButtons();
		/* 右下方選擇AI檔案的View */
		this.setSelectedFilesStatusView();
		/* 中間顯示AI列表的View */
		this.setAIListPanel();
		/* 設置右上方載入AI檔案的按鈕 */
		this.setAddingFilesButtons();
		/* 設置controller */
		this.ctr = new AddingAIController(AIData, selectedMainAIFileName, selectedSubAIFilesPanel, loadedAIListPanel);
	}
	
	/* 下方button列 */
	private void setButtons(){
		CPanel cp = new CPanel(218,660,488,60,new GridLayout(1,1,20,0));
		cp.setOpaque(false);
		
		cp.add(new CButton(CButton.AddingBottom,new AbstractAction("回遊戲設定") {
		    public void actionPerformed(ActionEvent e) {
		    	ChangePage.changeToPlaySettingPage();
				SaveSetting.saveExternalAI(AIData);
		    }
		}));
		this.add(cp, new Integer(1));
	}
	
	/* 右下方選擇AI檔案的View */
	private void setSelectedFilesStatusView(){
		CPanel cp = new CPanel(654,340,240,300,null);
		cp.setOpaque(false);
		cp.setBorder(BorderFactory.createTitledBorder(BorderFactory.   
                createLineBorder(Color.BLACK, 2), "目前選擇檔案", TitledBorder.CENTER,   
                TitledBorder.TOP, new Font("標楷體", Font.BOLD, 24), Color.black));
		
		CLabel selectMainFile = new CLabel("選擇主檔案",SwingConstants.CENTER);
		selectMainFile.setAttr(0, 30, 240, 40, CLabel.SelectingAITitleName);
		
		this.selectedMainAIFileName = new CLabel("",SwingConstants.CENTER);
		this.selectedMainAIFileName.setAttr(0,70,240,40,CLabel.SelectingAIFileName);
		
		CLabel selectSubFile = new CLabel("選擇子檔案",SwingConstants.CENTER);
		selectSubFile.setAttr(0,110,240,40, CLabel.SelectingAITitleName);
		
		/* 放置scrollPane的Panel */
		CPanel scrollCPanel = new CPanel(20,160,210,130, new BorderLayout(0,0));
		scrollCPanel.setOpaque(false);
		scrollCPanel.setBorder(null);
		
		/* 放在scrollPane裡的Panel */
		this.selectedSubAIFilesPanel = new CPanel(new GridLayout(3, 1, 0, 10));
		this.selectedSubAIFilesPanel.setOpaque(false);
		
		/* ScrollPane */
		JScrollPane scrollPane = new JScrollPane(this.selectedSubAIFilesPanel);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		
		scrollCPanel.add(scrollPane);
		
		cp.add(selectMainFile);
		cp.add(selectSubFile);
		cp.add(this.selectedMainAIFileName);
		cp.add(scrollCPanel);
		
		this.add(cp, new Integer(1));
	}
	
	/* 中間顯示AI列表的View */
	private void setAIListPanel(){
		CPanel cp = new CPanel(100, 40, 524, 600, new BorderLayout(0,0));
		cp.setOpaque(false);
		cp.setBorder(BorderFactory.createTitledBorder(BorderFactory.   
                createLineBorder(Color.BLACK, 2), "AI列表", TitledBorder.CENTER,   
                TitledBorder.TOP, new Font("標楷體", Font.BOLD, 24), Color.black));
		
		/* 放置已載入AI的CPanel */
		this.loadedAIListPanel = new CPanel(new GridLayout(10,1,0,10));
		this.loadedAIListPanel.setOpaque(false);
		/* 放置已載入AI的ScrollPanl */ 
		JScrollPane scrollPane = new JScrollPane(this.loadedAIListPanel);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		cp.add(scrollPane);
		
		/* 將存檔裡的AI載入到列表 */
		GridLayout gl = (GridLayout) this.loadedAIListPanel.getLayout();
		if(gl.getRows() != 0 && this.loadedAIListPanel.getComponentCount() == 10){
			gl.setRows(0);
		}
		
		for(int i=0;i<this.AIData.getAIMainNameSet().size();i++){
			JLabel lb = new JLabel("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+AIData.getAIMainNameSet().get(i)+"</font></html>",SwingConstants.CENTER);
			lb.setOpaque(false);
			lb.setFont(new Font(null, Font.PLAIN, 24));
			this.loadedAIListPanel.add(lb);
		}
		
		this.add(cp, new Integer(1));
	}
	
	/* 設置右上方載入AI檔案的按鈕 */
	private void setAddingFilesButtons(){
		CPanel cp = new CPanel(654,40,240,300,null);
		cp.setOpaque(false);
		
		cp.add(new CButton(10,30,220,40,CButton.AddingBottom,new AbstractAction("選擇子檔案") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.chooseSubFile();
		    }
		}));
		cp.add(new CButton(10,90,220,40,CButton.AddingBottom,new AbstractAction("選擇主檔案") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.chooseMainFile();
		    }
		}));
		cp.add(new CButton(15,150,100,40,CButton.AddingBottom,new AbstractAction("新增") {
		    public void actionPerformed(ActionEvent e) {
				if(ctr.getLoadedFiles() == null){
					JOptionPane.showMessageDialog(new JPanel(), "您還沒有加入任何外掛！"
							, "警告", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
					JOptionPane.showMessageDialog(new JPanel(), "您還沒有加入任何外掛!");
					return;
				}
				ctr.checkInput();
		    }
		}));
		cp.add(new CButton(125,150,100,40,CButton.AddingBottom,new AbstractAction("清除") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.clearInput();
		    }
		}));
		cp.add(new CButton(10,210,220,40,CButton.AddingBottom,new AbstractAction("刪除已載入AI") {
		    public void actionPerformed(ActionEvent e) {
		    	ctr.deleteAI();
		    }
		}));
		
		this.add(cp, new Integer(1));
	}
}