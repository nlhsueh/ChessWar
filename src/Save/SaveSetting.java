package Save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DevelopPattern.DevelopGameSetting;
import NormalPattern.NormalGameSetting;
import Share.ExternalAIData;
import Share.GameSetting;

/**
 * 負責「讀取」與「儲存」GameSetting資訊
 */

public class SaveSetting{
	
	/**
	 * 對「對弈模式」遊戲設定進行儲存
	 */
	public static void savePlayGameSetting(GameSetting gameSetting){
		try {
            FileOutputStream fs = new FileOutputStream("PlayGameSetting.sav");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(gameSetting);
            os.close();
            fs.close();
            System.out.println("Save PlayGameSetting Suc.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Save PlayGameSetting Fail.");
        }
        finally {
//        	System.out.println("Save Setting Finally.");
        }
	}
	
	/**
	 * 對「開發模式」遊戲設定進行儲存
	 */
	public static void saveDevelopGameSetting(GameSetting gameSetting){
		try {
            FileOutputStream fs = new FileOutputStream("DevelopGameSetting.sav");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(gameSetting);
            os.close();
            fs.close();
            System.out.println("Save DevelopGameSetting Suc.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Save DevelopGameSetting Fail.");
        }
        finally {
//        	System.out.println("Save Setting Finally.");
        }
	}
	
	/**
	 * 對外掛AI資訊進行儲存
	 */
	public static void saveExternalAI(ExternalAIData data){
		try {
            FileOutputStream fs = new FileOutputStream("ExternalAIConfig.sav");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(data);
            os.close();
            fs.close();
            System.out.println("Save ExternalAIConfig Suc.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Save ExternalAIConfig Fail.");
        }
        finally {
//        	System.out.println("Save Setting Finally.");
        }
		
	}
	
	/**
	 * 檢查同層目錄是否存在PlayGameSetting.sav，若存在則從GameSetting.sav讀取遊戲設定並回傳，若否則new 空的GameSetting回傳
	 * @return PlayGameSetting
	 */
	public static NormalGameSetting loadPlayGameSetting(){
		NormalGameSetting gameSetting = null ;
		try {
			File save = new File("PlayGameSetting.sav");
			if(save.exists()){
				FileInputStream fs = new FileInputStream("PlayGameSetting.sav");
	            ObjectInputStream is = new ObjectInputStream(fs);
	            gameSetting = (NormalGameSetting) is.readObject();
	            is.close();
	            fs.close();
	            System.out.println("Load PlayGameSetting Suc.");
			}else{
				System.out.println("Load PlayGameSetting Not Found Save.");
			}
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Load PlayGameSetting Fail.");
        }
        finally {
//        	System.out.println("Load Setting Finally.");
        }
		if(gameSetting != null){
//			// 檢查外部存檔類別讀取狀況
			
			return gameSetting;
		}else 
    		return new NormalGameSetting();
	}
	
	/**
	 * 檢查同層目錄是否存在GameSetting.sav，若存在則從GameSetting.sav讀取遊戲設定並回傳，若否則new 空的GameSetting回傳
	 * @return DevelopGameSetting
	 */
	public static DevelopGameSetting loadDevelopGameSetting(){
		DevelopGameSetting gameSetting = null ;
		try {
			File save = new File("DevelopGameSetting.sav");
			if(save.exists()){
				FileInputStream fs = new FileInputStream("DevelopGameSetting.sav");
	            ObjectInputStream is = new ObjectInputStream(fs);
	            gameSetting = (DevelopGameSetting) is.readObject();
	            is.close();
	            fs.close();
	            System.out.println("Load DevelopGameSetting Suc.");
			}else{
				System.out.println("Load DevelopGameSetting Not Found Save.");
			}
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Load DevelopGameSetting Fail.");
        }
        finally {
//        	System.out.println("Load Setting Finally.");
        }
		if(gameSetting != null){
//			// 檢查外部存檔類別讀取狀況
			
			return gameSetting;
		}else 
    		return new DevelopGameSetting();
	}
	
	/**
	 * 檢查同層目錄是否存在ExternalAIConfig.sav，若存在則從ExternalAIConfig.sav讀取外掛AI資訊並回傳，若否則new 空的ExternalADataI回傳
	 * @return ExternalAIData
	 */
	public static ExternalAIData loadExternalAI(){
		ExternalAIData externalAIData = null ;
		try {
			File save = new File("ExternalAIConfig.sav");
			if(save.exists()){
				FileInputStream fs = new FileInputStream("ExternalAIConfig.sav");
	            ObjectInputStream is = new ObjectInputStream(fs);
	            externalAIData = (ExternalAIData) is.readObject();
	            is.close();
	            fs.close();
	            System.out.println("Load ExternalAIConfig Suc.");
			}else{
				System.out.println("Load ExternalAIConfig Not Found Save.");
			}
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Load ExternalAIConfig Fail.");
        }
        finally {
//        	System.out.println("Load Setting Finally.");
        }
		if(externalAIData != null){
			return externalAIData;
		}else 
    		return new ExternalAIData();
	}
}
