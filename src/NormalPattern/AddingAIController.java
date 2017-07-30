package NormalPattern;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import AI.AbstractPlayer;
import ChessGame.Chess;
import ChessGame.ChessSide;
import FrameView.MainFrame;
import Share.ExternalAIData;
import ViewComponent.CLabel;
import ViewComponent.CPanel;

public class AddingAIController {
	
	private ExternalAIData AIData;
	private ArrayList<File> loadedFiles = new ArrayList<File>(); /* 儲存選擇的檔案 */
	private File loadedMainFile ; /* 儲存主類別 */
	
	private CLabel selectedMainAIFileName ;
	private CPanel selectedSubAIFilesPanel;
	private CPanel LoadedAIListPanel;
	
	public AddingAIController(ExternalAIData AIData, CLabel selectedMainAIFileName, CPanel selectedSubAIFilesPanel, CPanel LoadedAIListPanel){
		this.AIData = AIData;
		this.selectedMainAIFileName = selectedMainAIFileName;
		this.selectedSubAIFilesPanel = selectedSubAIFilesPanel;
		this.LoadedAIListPanel = LoadedAIListPanel;
	}
	
	public ArrayList<File> getLoadedFiles(){
		return this.loadedFiles;
	}
	
	/* 選擇欲載入的副class檔案 */
	public void chooseSubFile(){
		/* 選擇檔案 */
		JFileChooser fc = new JFileChooser("." + File.separator); /* default path */
		fc.setDialogTitle("選擇子class檔案");
		/* 限制副檔名 */
		fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
		String type[] = {"class"}; /* file Filter */
		fc.setMultiSelectionEnabled(true); /* multi files. */
		fc.setFileFilter(new FileNameExtensionFilter("class FILE", type));
		
		int returnValue = fc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) //判斷是否選擇檔案 
		{ 
			/* success select */
			File[] files = fc.getSelectedFiles(); /* get Select File */
			this.checkSubClass(files); /* check class */
		} else{
			/* click cancel */
		}
	}
	
	/* 選擇欲載入的class檔案 */
	public void chooseMainFile(){
		/* 選擇檔案 */
		JFileChooser fc = new JFileChooser("." + File.separator); /* default path */
		fc.setDialogTitle("選擇主class檔案");
		/* 限制副檔名 */
		fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
		String type[] = {"class"}; /* file Filter */
		fc.setFileFilter(new FileNameExtensionFilter("class FILE", type));
		
		int returnValue = fc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) //判斷是否選擇檔案 
		{ 
			/* success select */
			File file = fc.getSelectedFile(); /* get Select File */
			this.checkMainClass(file); /* check class */
		} else{
			/* click cancel */
		}
	}
	
	public void checkMainClass(File file){
		
		/* 取得檔名 */
		int pos = file.getName().lastIndexOf(".");
		String s = file.getName().substring(0,pos);
		/* 取得路徑 */
		String path = "file:"+file.getPath();
		int p = path.lastIndexOf("/");
		path = path.substring(0,p)+"/";
		/* 判斷檔名（類別名稱）是否已經重複 */
		for(int i=0;i<this.AIData.getAIMainNameSet().size();i++){
			if(s.equals(this.AIData.getAIMainNameSet().get(i))){
				JOptionPane.showMessageDialog(new JPanel(), "與現有類別名稱重複！\n請確認後再試一次！"
						, "提示", JOptionPane.INFORMATION_MESSAGE, MainFrame.icon_alert);
				return ;
			}
		}
		
		/* 嘗試讀取類別 */
		try {
			URL url1 = new URL(path);
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1});
			urlClassLoader.loadClass(s);
			Class<?> c = Class.forName(s,false,urlClassLoader);
				/* 測試是否繼承AbstractAI與建構子是否無誤 */
				/* 建立建構子參數 */
				Class<?>[] params = new Class[2];
	            params[0] = Chess[].class;
	            params[1] = ChessSide.class;
	            Constructor<?> constructor = c.getConstructor(params);
	            @SuppressWarnings("unused")
				AbstractPlayer a = (AbstractPlayer) constructor.newInstance(new Chess[32], ChessSide.RED);
		}
		catch(ClassCastException | NoSuchMethodException e){
			JOptionPane.showMessageDialog(new JPanel(), "您所輸入的Class沒有繼承abstractPlayer類別！\n請確認後再試一次！"
				, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			return;
		}
		catch (InstantiationException e) {
			JOptionPane.showMessageDialog(new JPanel(), "初始化失敗！\n請修改後再試一次！\nError Code : InstantiationException"
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
			JOptionPane.showMessageDialog(new JPanel(), "無法找到與輸入相對應的AI類別！"
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
			return;
		}
		catch (NoClassDefFoundError e){
			JOptionPane.showMessageDialog(new JPanel(), "類別名稱必須與檔名相同，請重新確認後再試一次！"
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
		
		this.loadedMainFile = file;
		this.selectedMainAIFileName.setText("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+s+"</font></html>");
	}

	public void checkSubClass(File[] files){
		
		/* 取得路徑 */
		String path = "file:"+files[0].getPath();
		int p = path.lastIndexOf("/");
		path = path.substring(0,p)+"/";
		for(int i=0;i<files.length;i++){
			int pos = files[i].getName().lastIndexOf(".");
			String s = files[i].getName().substring(0,pos);
			/* 判斷檔名（類別名稱）是否已經重複 */
			for(int j=0;j<this.loadedFiles.size();j++){
				String allFileName = this.loadedFiles.get(j).getName();
	    		String realFileName = allFileName.substring(0,allFileName.lastIndexOf("."));
				if(s.equals(realFileName)){
					JOptionPane.showMessageDialog(new JPanel(), "與現有類別名稱重複！\n請確認後再試一次！"
							, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
					return ;
				}
			}
			
			try {
				URL url1 = new URL(path);
				URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1});
				urlClassLoader.loadClass(s);
				Class<?> c = Class.forName(s,false,urlClassLoader);
				c.newInstance();
				
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(new JPanel(), "無法找到與輸入相對應的AI類別！\nError code : ClassNotFoundException."
						, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				return;
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(new JPanel(), "檔案路徑格式錯誤！\nError code : MalformedURLException."
						, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
				e.printStackTrace();
			}
			catch (InstantiationException e) {
				JOptionPane.showMessageDialog(new JPanel(), "初始化失敗！\n請修改後再試一次！\nError Code : InstantiationException"
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
			
			GridLayout gl = (GridLayout)selectedSubAIFilesPanel.getLayout();
			if(selectedSubAIFilesPanel.getComponentCount() == 3){
				gl.setRows(0);
			}
			
			JLabel subFileLb = new JLabel("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+s+"</font></html>", SwingConstants.CENTER);
			subFileLb.setFont(new Font(null, Font.BOLD, 20));
			this.selectedSubAIFilesPanel.add(subFileLb);
			this.selectedSubAIFilesPanel.updateUI();
			
			this.loadedFiles.add(files[i]);
		}
		
	}
	
	public void checkInput(){
		
		int pos = this.loadedMainFile.getName().lastIndexOf(".");
		String s = this.loadedMainFile.getName().substring(0,pos);

		/* 資料夾路徑 */
		String path = "file:"+this.loadedMainFile.getPath();
		int p = path.lastIndexOf("/");
		path = path.substring(0,p)+"/";
		
		try {
			URL url1 = new URL(path);
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {url1});
			urlClassLoader.loadClass(s);
			Class<?> c = Class.forName(s,true,urlClassLoader);
			/* 測試是否繼承AbstractAI與建構子是否無誤 */
			/* 建立建構子參數 */
			Class<?>[] params = new Class[2];
            params[0] = Chess[].class;
            params[1] = ChessSide.class;
            Constructor<?> constructor = c.getConstructor(params);
            @SuppressWarnings("unused")
			AbstractPlayer a = (AbstractPlayer) constructor.newInstance(new Chess[32], ChessSide.RED);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : ClassNotFoundException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			return;
		} catch (NoSuchMethodException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : NoSuchMethodException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
			return;
		} catch (SecurityException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : SecurityException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
			return;
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : InstantiationException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
			return;
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : IllegalAccessException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
			return;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : IllegalArgumentException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
			return;
		} catch (InvocationTargetException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : InvocationTargetException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
			return;
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(new JPanel(), "Error code : MalformedURLException."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
			e.printStackTrace();
		}
		
		/* 載入成功，設立標籤、建立資料夾並搬移 */		
		GridLayout gl = (GridLayout) this.LoadedAIListPanel.getLayout();
		if(gl.getRows() != 0 && this.LoadedAIListPanel.getComponentCount() == 10){
			gl.setRows(0);
		}
		
		JLabel lb = new JLabel("<html><font color='"+PlayingPlayerView.colorTitle+"'>"+s+"</font></html>",SwingConstants.CENTER);
		lb.setOpaque(false);
		lb.setFont(new Font(null, Font.PLAIN, 24));
		this.LoadedAIListPanel.add(lb);
		
		/* 建立資料夾 */
		File dir_file = new File("./AI/"+s);
	    dir_file.mkdirs();
	    /* 複製檔案 */
	    try{
		    String mainFilePath = dir_file+"/"+this.loadedMainFile.getName();
	        File f11 = new File(this.loadedMainFile.getPath());
	        File f22 = new File(mainFilePath);
	        InputStream in1 = new FileInputStream(f11);
	        OutputStream out1 = new FileOutputStream(f22);
	        byte[] buf1 = new byte[1024];
	        int len1;
	        while ((len1 = in1.read(buf1)) > 0){
	          out1.write(buf1, 0, len1);
	        }
	        in1.close();
	        out1.close();
	    }
      catch(FileNotFoundException ex){
    	  JOptionPane.showMessageDialog(new JPanel(), ex.getMessage() + " in the specified directory."
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
    	  System.exit(0);
      }
      catch(IOException e){
    	  JOptionPane.showMessageDialog(new JPanel(), e.getMessage()
					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
    	  System.exit(0);
      }
	    for(int i=0;i<this.loadedFiles.size();i++){
	        try{
	        	String filePath = dir_file+"/"+this.loadedFiles.get(i).getName();
		        File f1 = new File(this.loadedFiles.get(i).getPath());
		        File f2 = new File(filePath);
		        InputStream in = new FileInputStream(f1);
		        
		        //For Append the file.
//		        OutputStream out = new FileOutputStream(f2,true);

		        //For Overwrite the file.
		        OutputStream out = new FileOutputStream(f2);

		        byte[] buf = new byte[1024];
		        int len;
		        while ((len = in.read(buf)) > 0){
		          out.write(buf, 0, len);
		        }
		        in.close();
		        out.close();
		      }
	        catch(FileNotFoundException ex){
	      	  JOptionPane.showMessageDialog(new JPanel(), ex.getMessage() + " in the specified directory."
	  					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
	      	  System.exit(0);
	        }
	        catch(IOException e){
	      	  JOptionPane.showMessageDialog(new JPanel(), e.getMessage()
	  					, "警告", JOptionPane.WARNING_MESSAGE, MainFrame.icon_error);
	      	  System.exit(0);
	        }
	    }
	    /* 儲存檔案資訊 */
	    /* 主檔案 */
	    this.AIData.getAIMainNameSet().add(s);
	    /* 副檔案 */
	    ArrayList<String> subNameArrayList ;
	    if(this.loadedFiles.size()>0){
	    	/* ArrayList初始化 */
	    	subNameArrayList = new ArrayList<String>();
	    	this.AIData.getAISubNameSet().add(subNameArrayList);
	    	/* 加入檔案 */ 
	    	for(int i=1;i<this.loadedFiles.size();i++){
	    		/* 取得無副檔名之檔名 */
	    		String allFileName = this.loadedFiles.get(i).getName();
	    		String realFileName = allFileName.substring(0,allFileName.lastIndexOf("."));
	    		/* 加入ArrayList */
	    		subNameArrayList.add(realFileName);
		    }
	    }
	    
	    /* 重置變數，並清除標籤，使得下次載入從預設值開始 */
	    this.clearInput();
	}
	
	public void clearInput(){
		this.selectedSubAIFilesPanel.removeAll();
		this.selectedSubAIFilesPanel.updateUI();
		this.selectedMainAIFileName.setText("");
		this.loadedFiles.removeAll(this.loadedFiles);
        this.loadedMainFile = null;
	}
	
	public void deleteAI(){
		new AddingDeleteAIDialog(this.AIData,this.LoadedAIListPanel);
	}
	
}
