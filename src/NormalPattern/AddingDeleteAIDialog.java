package NormalPattern;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import FrameView.MainFrame;
import Share.ExternalAIData;

public class AddingDeleteAIDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JLayeredPane framePanel = new JLayeredPane();
	private ExternalAIData externalAIData;
	private JPanel AIListPanel ;
	private JList<String> AIList ;

	public AddingDeleteAIDialog(ExternalAIData externalAIData, JPanel AIListPanel){
		this.externalAIData = externalAIData;
		this.AIListPanel = AIListPanel;
		
		this.setFrameView();
		this.setSelectView();
		
		this.setVisible(true);
	}
	
	/**
	 * 選擇AI視窗Frame與Panel參數設定
	 * @return
	 */
	private void setFrameView(){
		/* 主選單Frame設定 */
		this.setSize(500, 300);
		this.setResizable(false);
		this.setTitle("刪除AI");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setResizable(false);
		this.add(this.framePanel);
		// Background Image Set
		this.setBackground();
	}
	
	private void setBackground(){
		JPanel jp = new JPanel();
		jp.setLocation(0, 0);
		jp.setSize(500,300);
		jp.setOpaque(false);
		// Background Image Set
		ImageIcon img = new ImageIcon(MainFrame.class.getClass().getResource("/img/settingBackground.jpg"));
		img = (new ImageIcon(img.getImage().getScaledInstance(924, 760, Image.SCALE_SMOOTH)));
		jp.setLayout(new GridLayout(1, 0, 0, 0));
		jp.add(new JLabel(img));
		this.framePanel.add(jp, new Integer(0));
	}
	
	private void setSelectView(){
		JPanel jp = new JPanel(null);
		jp.setLocation(0, 0);
		jp.setSize(500, 300);
		jp.setOpaque(false);
		jp.setBorder(null);
		
		JLabel topLb = new JLabel("<html><font color='"+PlayingPlayerView.colorBlack+"'>請選擇您欲刪除的AI</font></html>",SwingConstants.CENTER);
		topLb.setFont(new Font("標楷體", Font.BOLD, 26));
		topLb.setSize(500, 40);
		topLb.setLocation(0, 10);
		
		String[] AIName = (String[]) this.externalAIData.getAIMainNameSet().toArray(new String[0]);
		this.AIList = new JList<String>(AIName);
		this.AIList.setOpaque(false);
		this.AIList.setVisibleRowCount(6);
		this.AIList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.AIList.setCellRenderer(new TransparentListCellRenderer()); /* let background transparent */
		this.AIList.setFont(new Font(null, Font.BOLD, 18));

		JScrollPane jsp = new JScrollPane(this.AIList);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		jsp.setLocation(50, 60);
		jsp.setSize(400, 150);
		jsp.setBorder(new LineBorder(Color.BLACK));
		
		JButton btnCheck = new JButton("確定");
		btnCheck.setFont(new Font(null, Font.PLAIN, 18));
		btnCheck.setSize(120,40);
		btnCheck.setLocation(120,220);
		btnCheck.setOpaque(false);
		btnCheck.setActionCommand("Check");
		btnCheck.addActionListener(this);
		
		JButton btnCancel = new JButton("取消");
		btnCancel.setFont(new Font(null, Font.PLAIN, 18));
		btnCancel.setSize(120,40);
		btnCancel.setLocation(280,220);
		btnCancel.setOpaque(false);
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);
		
		jp.add(topLb);
		jp.add(jsp);
		jp.add(btnCheck);
		jp.add(btnCancel);
		
		this.framePanel.add(jp, new Integer(1));
	}
	
	public boolean removeData(File dir){
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
	       for (int i=0;i<files.length;i++) { 
	            if (!removeData(files[i]))
	               return false;
	       }
		}
		
		return dir.delete();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		switch(cmd){
			
			case "Check":
				if(this.AIList.getSelectedValue() == null){
					JOptionPane.showMessageDialog(new JPanel(), "您還沒有選擇要刪除的外掛！"
							, "提示", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
					return;
				}
				/* 更新列表 */
				int index = this.AIList.getSelectedIndex();
				/* 在UI移除 */
				this.AIListPanel.remove(index);
				this.AIListPanel.updateUI();
				/* 在檔案路徑移除 */
				File dir = new File("./AI/"+this.externalAIData.getAIMainNameSet().get(index));
				this.removeData(dir);
				/* 在config移除 */
				this.externalAIData.getAIMainNameSet().remove(index);
				this.dispose();
				break;
			case "Cancel":
				this.dispose();
				break;
				
			default:
				break;
			
		}
	}
	
	
}

