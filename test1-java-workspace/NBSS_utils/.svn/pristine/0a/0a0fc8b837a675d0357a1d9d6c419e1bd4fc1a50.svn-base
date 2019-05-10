import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.management.ManagementFactory;
import java.util.Scanner;

//import com.sun.management.OperatingSystemMXBean;


/**
 * 쓰래드 갯수를 로그파일수 만큼 ( 약 20개 ) 하면 
 * CPU를 너무 많이 잡아먹는다 ( 90% 이상 )
 * 이에따라 쓰래드 갯수는 2개로만 제한 한다. ( 약 15 % 사용함  prd대내 기준 )
 * @author tmax
 *
 */
public class LogSearch2 
{
	private static Map<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
//	private static Map<String, String> concurrentHashMap = Collections.synchronizedMap(new HashMap<String, String>());
	
	
	public static void main(String[] args)
	{
		
		long cpuUsagePercent = getCpuUsagePercent();
		
		if(cpuUsagePercent > 70)
		{
			System.out.println("Can`t search becuase CPU usage : "+ cpuUsagePercent + "%");
			System.out.println("Try search later !!");
			
			System.exit(0);
		}
		else if( cpuUsagePercent > 50 )
		{
			Scanner scan = new Scanner(System.in);
			System.out.println("CPU usage : "+ cpuUsagePercent + "%");
			System.out.println("search contiue? (y/n)");
			
			String inStr = scan.next();
			
			if(!"Y".equals(inStr.toUpperCase()))
			{
				System.exit(0);
			}
		}
		
		System.out.println("Cpu(s) usage : "+ cpuUsagePercent + "%");
		System.out.println("\n=================================search start================================================================\n");
		
		final long startTime = System.currentTimeMillis();
		
		final String logFilePath = args[0];
		final String yyyymmdd    		 = args[1];
		final String keyWord     = args[2];
		
		File root = new File (logFilePath);
		
		final File[] logFiles = root.listFiles();
		
		concurrentHashMap.put("1", "1");
		concurrentHashMap.put("2", "2");
		
		Thread tc = new Thread()
		{
			public void run()
			{
				while(true)
				{
					try 
					{
						sleep(3000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
					boolean isEmpty = concurrentHashMap.isEmpty();
					
					if(isEmpty)
					{
						long endTime = System.currentTimeMillis();
						System.out.println("Total Search Time(ms) : "+ (endTime - startTime) );
						
						break;
					}
					else
					{
						//System.out.println("now Searching..... Map is empty=>"+isEmpty);
					}
						
				}
			}
		};
		
		tc.start();
		
		Thread t_euckr = new Thread()
		{
			
			public void run()
			{
				String tName = this.getName();
				concurrentHashMap.put(tName, tName);
			
				for(File logFile : logFiles)
				{
					String fileName = logFile.getName();
					if(fileName.indexOf(yyyymmdd) > 0)
					{
						try
			            {
			            	 InputStream is_euckr = new FileInputStream(logFile);
			                 InputStreamReader wisr_euckr = new InputStreamReader(is_euckr, "euc-kr");
			                 BufferedReader wibr_euckr = new BufferedReader(wisr_euckr);
			             
			                 String totLineRead_euckr="";
			                 String lineRead_euckr="";
			                 while((lineRead_euckr = wibr_euckr.readLine()) != null)
			                 {
			                	 if(lineRead_euckr.endsWith("#]"))
			                	 {
			                		 totLineRead_euckr = totLineRead_euckr + lineRead_euckr;
			                		 if(totLineRead_euckr.indexOf(keyWord) > 0)
				                	 {
				                		 System.out.println("[euc-kr]["+fileName+"]"+totLineRead_euckr);
				                		 
				                		 System.out.println("\n=============================================================================================================\n");
				                		
				                	 }
			                		 
			                		 totLineRead_euckr = "";
			                	 }
			                	 else
			                	 {
			                		 totLineRead_euckr = totLineRead_euckr + lineRead_euckr;
			                	 }
			                 }
			            }
			            catch (IOException e)
			            {
			            	concurrentHashMap.remove("1");
			            	concurrentHashMap.remove(tName);
			            	System.out.println("#### IO Exception ####");
			                 e.printStackTrace();
			            }
					} // end if
					
				} // end for
				
	            concurrentHashMap.remove("1");
	            concurrentHashMap.remove(tName);
			}
		};
		
		t_euckr.start();
		
		Thread t_utf8 = new Thread()
		{
			
			public void run()
			{
				String tName = this.getName();
				concurrentHashMap.put(tName, tName);
			
				for(File logFile : logFiles)
				{
					String fileName = logFile.getName();
					if(fileName.indexOf(yyyymmdd) >= 0)
					{
						try
			            {
			                 InputStream is_utf8 = new FileInputStream(logFile);
			                 InputStreamReader wisr_utf8 = new InputStreamReader(is_utf8, "utf-8");
			                 BufferedReader wibr_wisr_utf8 = new BufferedReader(wisr_utf8);
			             
			                 String totLineRead_utf8="";
			                 String lineRead_utf8="";
			                 while((lineRead_utf8 = wibr_wisr_utf8.readLine()) != null)
			                 {
			                	 if(lineRead_utf8.endsWith("#]"))
			                	 {
			                		 totLineRead_utf8 = totLineRead_utf8 + lineRead_utf8;
			                		 if(totLineRead_utf8.indexOf(keyWord) > 0)
				                	 {
				                		 System.out.println("[utf-8]["+fileName+"]"+totLineRead_utf8);
				                		 
				                		 System.out.println("\n=============================================================================================================\n");
				                		
				                	 }
			                		 
			                		 totLineRead_utf8 = "";
			                	 }
			                	 else
			                	 {
			                		 totLineRead_utf8 = totLineRead_utf8 + lineRead_utf8;
			                	 }
			                	 
			                 }
			            }
			            catch (IOException e)
			            {
			            	concurrentHashMap.remove("2");
			            	concurrentHashMap.remove(tName);
			            	System.out.println("#### IO Exception ####");
			                 e.printStackTrace();
			            }
					} // end if
					
				} // end for
				
	            concurrentHashMap.remove("2");
	            concurrentHashMap.remove(tName);
			}
		};
		
		t_utf8.start();
		
	}
	
	public static long getCpuUsagePercent()
	{
		double load = 0;
		try
		{
//			OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
//			
//			load = osBean.getSystemCpuLoad();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Math.round(load*100.0);
		
	}
}
