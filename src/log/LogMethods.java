package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class LogMethods {

	
	
	
	public boolean printLog(String action){
		
		boolean worked = false;
		
		//fileCheck
		if(new File("tmp/test/serverLog.txt").isFile()){
			//file exists! ok!
			//System.out.println("fileExists!"+ new File("serverLog.txt").getAbsolutePath());
		}else{
			//create file!
			File dir = new File("tmp/test");
			
			dir.mkdirs();
			File tmp = new File(dir, "serverLog.txt");
			try {
				//System.out.println("fileExists!"+ tmp.getAbsolutePath());
				tmp.createNewFile();
				Thread.sleep(20);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		String text = timeStamp() +":  "+ action+"\n";
		
		try {
		    Files.write(Paths.get("tmp/test/serverLog.txt"), text.getBytes(), StandardOpenOption.APPEND);

		    
		    worked = true;
		}catch (IOException e) {
		    e.printStackTrace();
		}
		
		return worked;
	}
	
	public String timeStamp(){
		return LocalDateTime.now().toString();
	}
	
	
	
	
}
