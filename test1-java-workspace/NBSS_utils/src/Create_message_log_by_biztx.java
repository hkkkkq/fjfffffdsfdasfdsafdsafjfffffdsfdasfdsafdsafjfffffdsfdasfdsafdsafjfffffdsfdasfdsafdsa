import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class Create_message_log_by_biztx 
{
	private static String yyyymmdd = ""; // 로그기준일자
//	private static String flag = "0"; // 0: all biztx, 1: 정의된 biztx 프로퍼티 참조
	
	// 거래ID별 로그정보를 key, value 형식으로 큐에 저장
	private static ConcurrentLinkedQueue<Map<String, List<String>>> queue=  new ConcurrentLinkedQueue<Map<String, List<String>>>();
	
//	private static String trgPath = "C:\\핸드폰백업\\메시지로그\\분리로그";
//	private static String trgPath = "/home/infadm/logSeperatorTest/mkFile";
//	private final static String trgPath = "/GW_REPO/applog_bak/infinilink/message_log_by_biztx";
	private final static String trgPath = "/applog/infinilink/message_log_by_biztx";
	private final static String srcPath = "/applog/infinilink/message_log";
//	private final static String srcPath = "/applog/infinilink/test_message_log";
	private static ConcurrentHashMap<String, BufferedWriter> bizTxWriterMap = new ConcurrentHashMap<String, BufferedWriter>(); //거래ID별 BufferedWriter 재사용
//	private static ConcurrentHashMap<String, String> logWriterThreadMap = new ConcurrentHashMap<String, String>();
	private static ConcurrentHashMap<String, String> guidMap = new ConcurrentHashMap<String, String>();
	
	//정상 -  패치후 거래 id 기준
	private final static int guidPos = 5;
	private final static int bizTxPos = 6;
	private final static int serverNmPos = 9;
	
	private final static int readCnt = 50000; // 3키로바이트  * 50000 건 => 150메가
	private final static int logWriterThreadCnt = 10;
	private static AtomicBoolean isReadEnd = new AtomicBoolean(false);

	private final static int limitReadQueueCnt = 10;
	
//	public static synchronized Map<String, List<String>> queuePoll()
//	{
//		return queue.poll();
//	
//	}
	
	public static void main(String[] args)
	{
		yyyymmdd = args[0];
//		String yyyymmdd = "20161019";
		
		System.out.println("*** param[0]:" + yyyymmdd + " ***");
		
//		flag = args[1];
		
		String startTime = getCurrentTime();
		System.out.println("*** main() START["+startTime+"] ***");
		
		ArrayList<Thread> logWriterThreads = new ArrayList<Thread>();
		
		// 10개의 LogWriter 생성
		for (int i=0; i<logWriterThreadCnt; i++) {
			
			logWriterThreads.add(new Thread(new LogWriter(i)));
			
		}
		
		// 10개의 LogWriter 실행
		for (int i=0; i<logWriterThreads.size(); i++) {
			
			logWriterThreads.get(i).start();	
		}
		
		LogReader();
		
		// 10개의 LogWriter join => 10개 모두 종료하고 나서 메인쓰레드 진행
		for (int i=0; i<logWriterThreads.size(); i++) {
			
			try {
				logWriterThreads.get(i).join();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		// LogWriter에서 거래ID별로 생성한 Write 인스턴스 해제
		Iterator<String> it = bizTxWriterMap.keySet().iterator();
		while(it.hasNext())
		{
			String key = it.next();
			Writer bw = bizTxWriterMap.get(key);

			if(bw!=null) { try { bw.flush(); bw.close();	} catch (IOException e) {e.printStackTrace();} }
		}
		
		System.out.println("*** main() Total guidCnt: "+ guidMap.keySet().size() + " ***");
		
		String endTime = getCurrentTime();
		System.out.println("*** main() END["+endTime+"] ***");	
		System.out.println("*** main() Total Running time : "+ getTimeDiferrence(startTime, endTime));
		
	}
	
	public static void LogReader() {
		
		String startTime = getCurrentTime();
		System.out.println("*** LogReader() START["+startTime+"] ***");
		
		
		// trgPath = "/GW_REPO/applog_bak/infinilink/message_log_by_biztx"
//		File root = new File(trgPath);
//		if(!root.exists() || !root.isDirectory())
//		{
//			root.mkdirs();
//		}
		
		File msgLogDir = new File(srcPath);
		File[] childDirs = msgLogDir.listFiles(); //서버명 Directories
		
		for(File childDir : childDirs)
		{	
			// 2017.07.20 특정 업무시스템만 추가
			if ("io_grp1".equals(childDir.getName()) || "io_grp1b".equals(childDir.getName()) || "eo_fnc1".equals(childDir.getName()) || "eo_fnc1b".equals(childDir.getName())) {
		
				File[] logFiles = childDir.listFiles(); //로그파일
				
				Arrays.sort(logFiles);
				
				for(File logFile : logFiles)
				{
					String logFileName = logFile.getName(); //로그파일
					
	//				if(logFileName.indexOf(yyyymmdd) > 0 && logFileName.equals("io_cdrm1_message_2016101909.log"))
					if(logFileName.indexOf(yyyymmdd) > 0 )
					{
						String srcPath = logFile.getParent(); //->/applog/infinilink/message_log/io_cdrm1
	//					String trgPathDir = trgPath + File.separator + srcPath.substring(srcPath.lastIndexOf("/")+1, srcPath.length()); //->io_cdrm1
						
	//					System.out.println("trgPathDir="+trgPathDir);  //->io_cdrm1
						
	//					System.out.println("srcPath="+srcPath); //->/applog/infinilink/message_log/io_cdrm1
	//					System.out.println("logFileName="+logFileName); //->io_cdrm1_message_2016101923.log
	//					System.out.println("*** LogReader(): " + srcPath + File.separator + logFileName + " ***");
						
						readFiles(srcPath, logFileName);
											
						//READ Capacity Limit
						while(true)
						{
							if(queue.size() < limitReadQueueCnt) //10
							{
								break;
							}
							else
							{
								System.out.println("*** queue.size(): " + queue.size() + " ***");
								System.out.println("*** LogReader() is waiting for 3 sec ***");
								try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();	}
								
							}
						}
					}
				}
			}
		}
		
		isReadEnd.set(true);
		
		String endTime = getCurrentTime();
		System.out.println("*** LogReader() END["+endTime+"] ***");	
		System.out.println("*** LogReader() Total Running time : "+ getTimeDiferrence(startTime, endTime));
		
	}
	
	
	// io_cdrm1
	// /applog/infinilink/message_log/io_cdrm1
	// io_cdrm1_message_2016101923.log
	public static void readFiles(String filePath, String fileName)
	{
		String startTime = getCurrentTime();
		System.out.println("*** readFiles() START ["+startTime+"] fileName=["+fileName+"]");
		
		File file = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		//1건에 최대 3천바이트 
		file = new File(filePath, fileName);
		
		int messageTotalCnt = 0;
		
		try
		{
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
			
			List<String> lst = new ArrayList<String>();
			
			String message = "";
			String line = "";

			int messageCnt = 0;
			
			while(( line = br.readLine()) != null)
			{
				
				message = message + line;
				
				if(line.startsWith("[#") && line.endsWith("#]"))
				{				
					messageTotalCnt++;
					messageCnt++;
					
					lst.add(message);
					
	    			String[] totLines = message.split("\\|");
	    			
	    			// guid 건수 확인을 위한 로직 추가
	    			String guid = totLines[guidPos];
	    			guidMap.put(guid, guid);
					
//					System.out.println("totLine="+totLine);
					
					line = "";
					message = "";
				}
				else
				{
					line = "";
					continue; // 메시지를 끝까지 완성시킨다.
				}
				
				if(messageCnt > readCnt) //50000
				{
					messageCnt = 0;
				   	addQueue(lst);
					lst.clear();
				}
			}
			
			addQueue(lst);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		String endTime = getCurrentTime();
		System.out.println("*** readFiles() END ["+endTime+"] fileName=["+fileName+"]" + "  messageTotalCnt="+messageTotalCnt);
		
	}
	
	private static void addQueue(List<String> lst)
	{
		if(lst!=null && lst.size()>0)
		{
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			
			for(String str : lst)
			{
				String[] splitStr = str.split("\\|");
				String bixTxId = splitStr[bizTxPos];
				
				List<String> currRowLst = map.get(bixTxId);
				if(currRowLst == null)
				{
					currRowLst = new ArrayList<String>();
				}
				
				currRowLst.add(str);
				map.put(bixTxId, currRowLst); // 거래ID를 키로 로그를 List에 담는다.
			}
			
			queue.add(map);
		}
	}
	
	private static void writeFiles(List<String> printLst)
	{
    	if(printLst!=null && printLst.size()>0)
    	{
    		//printLst에는 동일한 bixtxID의 로그만 존재
    		
    		String firstRow = printLst.get(0);
    		
    		try 
        	{    			
    			String[] firstRowSpit = firstRow.split("\\|");
    			   			
    			String serverName = firstRowSpit[serverNmPos];  // io_cdrm1_svr1
    			String bizTxId = firstRowSpit[bizTxPos];  
    			String bizSystemId = serverName.substring(0, serverName.lastIndexOf("_")); // io_cdrm1
    			
    			String[] arrBizTxId = bizTxId.split("\\.");
    			String ifId = arrBizTxId[arrBizTxId.length-1];
    			
    			if (ifId.startsWith("l_")) {
    				ifId = ifId.substring(2, ifId.length());
    			}
    			
//    			File dir1 = new File(trgPath);
//    			if(!dir1.exists() || !dir1.isDirectory())
//    			{
//    				dir1.mkdirs();
//    			}
//    			
//    			File rootDir = new File(trgPath, bizSystemId);
//    			if(!rootDir.exists() || !rootDir.isDirectory())
//    			{
//    				rootDir.mkdirs(); //->GW_REPO/applog_bak/infinilink/message_log_separator/io_cdrm1
//    			}
    			
    			// /applog/infinilink/message_log_by_biztx/io_cdrm1/20161026
    			// File trgDir = new File(trgPath + File.separator + bizSystemId + File.separator + yyyymmdd);
    			
    			// /applog/infinilink/message_log_by_biztx/io_cdrm1/20161026
    			File trgDir = new File(trgPath + File.separator + bizSystemId);
    			
    			if(!trgDir.exists() || !trgDir.isDirectory())
    			{
    				trgDir.mkdirs(); // /applog/infinilink/message_log_by_biztx/io_cdrm1/20161026
    			}
    			
    			BufferedWriter bw = bizTxWriterMap.get(bizTxId);
    			if(bw==null)
    			{
    				File file =  new File(trgDir, ifId + "_biztx_" + yyyymmdd + ".log");
    				FileOutputStream fos = new FileOutputStream(file, true);
        	    	OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        	    	bw = new BufferedWriter(osw);
        	    	
        	    	bizTxWriterMap.put(bizTxId, bw);
    			}
    			
				for(String row : printLst)
        		{
        			bw.write(row + System.lineSeparator());
        		}
				
				bw.flush();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        		System.out.println("*** firstRow: " + firstRow + " ***");
        	}
    	}
	}
	
	public static String getCurrentTime() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}
	
	public static long getTimeDiferrence(String startTime, String endTime) {
	    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	    Date date1 = new Date();
	    Date date2 = new Date();

	    try {
			date1 = formatter1.parse(startTime);
			date2 = formatter1.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 초로 리턴
	    return (date2.getTime()-date1.getTime())/1000;
	    
	}
	
	
	static class LogWriter implements Runnable {
		
		int threadNum;
		
		public LogWriter(int i) {
			
			threadNum = i;
			
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			String threadName = Thread.currentThread().getName();
//			logWriterThreadMap.put(threadName, threadName);
			
			String startTime = getCurrentTime();
			System.out.println("*** LogWriter[thread_" + threadNum + ":" + threadName + "] " + "START[" + startTime + "] ***");
			
			while(true)
			{
				Map<String, List<String>> map = queue.poll();
//				System.out.println("======queue is null sleep start=======");
				if(map==null)
				{
//					System.out.println("======queue is null sleep start=======");
					try	{Thread.sleep(1000);}catch (InterruptedException e){e.printStackTrace();}
//					System.out.println("======queue is null sleep end=======");
					
					if(isReadEnd.get() && queue.size() == 0)
					{
//						logWriterThreadMap.remove(threadName);
						
//						if(logWriterThreadMap.isEmpty())
//						{
//							Iterator<String> it = bizTxWriterMap.keySet().iterator();
//							while(it.hasNext())
//							{
//								String key = it.next();
//								Writer bw = bizTxWriterMap.get(key);
//	
//								if(bw!=null) { try { bw.flush(); bw.close();	} catch (IOException e) {e.printStackTrace();} }
//							}
//						}
																		
						break;
					}
				}
				else
				{
					//System.out.println("--queue is not null start--" + threadName);
					Iterator<String> it = map.keySet().iterator();
					
					//int totPrintLstSize = 0;
					
					while(it.hasNext())
					{
						String bixTxId = it.next(); // bixTxId
						List<String> printLst = map.get(bixTxId);
						
						//totPrintLstSize = totPrintLstSize + printLst.size();
						
						writeFiles(printLst);
					}
					//System.out.println("--queue is not null end--" + threadName);
				}
			}
			
			String endTime = getCurrentTime();
			System.out.println("*** LogWriter[thread_" + threadNum + ":" + threadName + "] " + "END[" + endTime + "] ***");
		}
	}
}
	

	