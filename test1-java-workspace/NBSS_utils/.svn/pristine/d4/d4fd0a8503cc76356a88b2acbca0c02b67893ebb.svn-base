import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class LogSearch_MultiThread 
{
	private static Map<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
	
	
	//sh logSearch.sh "/applog/infinilink/test_message_log/test" "20161019" "0ADB01B0DD74C06E023E7BCF03BE1C"
	public static void main(String[] args)
	{
//		final String logFilePath = "/applog/infinilink/test_message_log/test";
//		String yyyymmdd    = "20161019";
//		final String keyWord     = "0ADB01B0DD74C06E023E7BCF03BE5E1C";
//		
		final String logFilePath = args[0];
		String yyyymmdd    = args[1];
		final String keyWord     = args[2];
		
		File root = new File (logFilePath);
		
		File[] logFiles = root.listFiles();
		
		concurrentHashMap.put("1", "1");
		
		for(File logFile : logFiles)
		{
			final String fileName = logFile.getName();
			System.out.println("fileName =>"+ fileName );
			
			if(fileName.indexOf(yyyymmdd) > 0)
			{
				Thread t = new Thread()
				{
					public void run()
					{
						String tName = this.getName();
						concurrentHashMap.put(tName, tName);
						
						//grep -aPzo '(?s)\[#\|(?:(?!\[#).)*?KeyWord(?:(?!#\]).)*?\|#\]' *.log
						
						List<String> cmd1 = new ArrayList<String>();
			            cmd1.add("/bin/sh");
			            cmd1.add("-c");
			            String cmdStr = "grep -aPzo '(?s)\\[#\\|(?:(?!\\[#).)*?"+ keyWord +"(?:(?!#\\]).)*?\\|#\\]' "+ fileName;
			            cmd1.add(cmdStr);
			            
			            ProcessBuilder pb = null;
			            Process p = null;
			           
			            try
			            {
			                 pb = new ProcessBuilder();
			                 pb.redirectErrorStream(true);
			                
			                 pb.directory(new File(logFilePath));
			                 pb.command(cmd1);
			                
			                 p = pb.start();
			                
			                 InputStreamReader wisr = new InputStreamReader(p.getInputStream());
			                 BufferedReader wibr = new BufferedReader(wisr);
			             
			                 String lineRead;
			                 while((lineRead = wibr.readLine()) != null)
			                 {
			                    System.out.println("### lineRead="+lineRead);
			                 }
			              
			                 try
			                 {
			                      p.waitFor();
			                 }
			                 catch(InterruptedException e)
			                 {
			                	 concurrentHashMap.remove("1");
			                	 concurrentHashMap.remove(tName);
			                	 System.out.println("#### Interrupted Exception ####");
			                      e.printStackTrace();
			                 }
			                 
			                 p.destroy();
			            }
			            catch (IOException e)
			            {
			            	concurrentHashMap.remove("1");
			            	concurrentHashMap.remove(tName);
			            	System.out.println("#### IO Exception ####");
			                 e.printStackTrace();
			            }
			            
			            concurrentHashMap.remove("1");
			            concurrentHashMap.remove(tName);
					}
				};
				
				t.start();
			}
		}
		
		
		Thread tc = new Thread()
		{
			public void run()
			{
				while(true)
				{
					try 
					{
						sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
					boolean isEmpty = concurrentHashMap.isEmpty();
					
					if(isEmpty)
					{
						break;
					}
					else
					{
						System.out.println("now Searching..... Map is empty=>"+isEmpty);
					}
						
				}
			}
		};
		
		tc.start();
	}
}
