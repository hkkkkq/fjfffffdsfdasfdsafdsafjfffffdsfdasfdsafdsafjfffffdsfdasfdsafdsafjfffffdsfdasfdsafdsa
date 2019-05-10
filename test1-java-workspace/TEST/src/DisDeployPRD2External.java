
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;











//import com.tmax.infinilink.dis.client.ConfigurationManagementMBean;
//import com.tmax.infinilink.dis.client.DISOperator;
//import com.tmax.infinilink.dis.connector.ConfigurationFileManager;
//import com.tmax.infinilink.dis.connector.DisConnector;
//import com.tmax.infinilink.dis.msg.studio.DeploymentInfo;
//import com.tmax.infinilink.dis.msg.studio.DeploymentResult;
//import com.tmax.infinilink.logging.Logger;
//import com.tmaxsoft.schemas.infinilink.CommonInfoType;


/**
 * Deploy Request To DIS
 * @author tmax
 *
 */
public class DisDeployPRD2External {
	
//	private static final Logger logger = Logger.getLogger(DisDeployPRD2External.class.getName());
//	// public static String IAR_PATH = "/app/infinilink/jeus7/server/dis/contentRepository/ConfigurationTransfer/2015-05-27 13:45:10.421";
//	// public static String COMM_PATH = "/app/infinilink/jeus7/server/dis/contentRepository/ConfigurationTransfer/2015-05-27 13:45:10.421";
//	
//	public static String PROP_PATH_NAME = "/app/infinilink/properties/cm_deploy/cm_deploy.properties";
//	
//	public static String DEPLOY_LOG_PATH   = "/applog/infinilink/cm_deploy_log";
//	
//	public static void printLog(PrintWriter pw, String logStr, boolean isInfo)
//	{
//		if(isInfo)
//		{
//			logger.info(logStr);
//		}
//		else
//		{
//			logger.severe(logStr);
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		String now = sdf.format(new Date());
//		
//		pw.println("["+now+"]"+logStr);
//	}
//	
//	public static void closeLog(PrintWriter pw, BufferedWriter bw, FileWriter fw)
//	{
//		if(pw!=null)
//		{
//			pw.close(); pw=null;
//		}
//		if(bw!=null)
//		{
//			try{bw.close(); bw=null;} catch (IOException e) {e.printStackTrace();}
//		}
//		if(fw!=null)
//		{
//			try {fw.close(); fw=null;} catch (IOException e) {e.printStackTrace();}
//		}
//	}
//	
//    public static void main(String[] args)
//    {
//    	Thread logThread = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while(true)
//				{
//					System.out.println("Now Deploying...");
//					try {
//						Thread.sleep(10000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		});
//    	
//    	logThread.start();
//    	
//    	File logDir = new File(DEPLOY_LOG_PATH);
//    	
//    	if(logDir==null || !logDir.exists())
//    	{
//    		logDir.mkdir();
//    	}
//    	
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//    	String logFileName = sdf.format(new Date()) + ".log";
//    	File logFile = new File(DEPLOY_LOG_PATH, logFileName);
//    	if(logFile==null || !logFile.exists())
//    	{
//    		try
//    		{
//				logFile.createNewFile();
//			}
//    		catch (IOException e) 
//    		{
//    			e.printStackTrace();
//    			logger.severe("####CM Deploy Fail Make LogFile!!!!#####");
//    			logger.severe("####CM Deploy Fail e=>"+e.getMessage());
//			}
//    	}
//    	
//    	FileWriter fw = null;
//    	BufferedWriter bw = null;
//    	PrintWriter pw = null;
//    	try 
//    	{
//			fw = new FileWriter(logFile, true);
//			bw = new BufferedWriter(fw);
//			pw = new PrintWriter(bw, true);
//		}
//    	catch (IOException e1) 
//    	{
//    		logger.severe("####CM Deploy FileWriter Exception#####");
//    		logger.severe("####CM Deploy FileWriter Exception e1=>"+e1.getMessage());
//		}
//    	
//    	boolean isExistDeployError = false; // deploy Result Falg : if exist err -> true    else -> false
//    	
//    	try
//        {
//    		printLog(pw, "####CM Deploy START =======================================================", true);
//    		
//    		Properties prop = load_file();
//    		
//    		printLog(pw, "####CM path  args[0]=["+args[0]+"]", true);
//    		printLog(pw, "####CM sysid args[1]=["+args[1]+"]", true);
//    		
//    		String URL = prop.getProperty("cm_deploy.url");
//    		String sysID = args[1];
//    		String userID = prop.getProperty("cm_deploy.id");
//			String userPW = prop.getProperty("cm_deploy.pw");
//			
//			userPW = Base64Coder.decodeString(userPW);
//    		
//	    	DisConnector conn = new DisConnector();
//	        conn.setUrl(URL);
//	        conn.logIn(userID, userPW);
//        
//        	String IAR_PATH  = args[0];
//        	String COMM_PATH = args[0];
//        	String PROP_PATH = args[0];
//        	String LIB_PATH  = args[0];
//        	
////            ConfigurationManagementMBean mbean = conn.getConfigurationManagementMbean();
////            
////            DISOperator operator = conn.getDisOperator();
//            
//            ConfigurationFileManager manager = ConfigurationFileManager.getInstance();
//            
//            boolean isExistIAR = existIAR(IAR_PATH);
//            boolean isExistCOMM = existCOMM(COMM_PATH);
//            boolean isExistPROP = existPROP(PROP_PATH);
//            boolean isExistLIB = existLIB(LIB_PATH);
//            
//            printLog(pw, "####CM isExistIAR=>"+isExistIAR, true);
//            printLog(pw, "####CM isExistCOMM=>"+isExistCOMM, true);
//            printLog(pw, "####CM isExistPROP=>"+isExistPROP, true);
//            printLog(pw, "####CM isExistLIB=>"+isExistLIB, true);
//            
//            List<DeploymentResult> deployResult = new ArrayList<DeploymentResult>();
//            List<DeploymentResult> deployResult_Comm = new ArrayList<DeploymentResult>();
//            DeploymentResult deployResult_Prop = null;
//            
//            // deploy - SystemVariable  --> target : all bizSystem
//            if(isExistPROP)
//            {
//            	for(String bizSysId : conn.getBizSystemIdList() )
//            	{
//        			printLog(pw, "####CM PROP Deploy target bizSysId=>"+bizSysId, true);
//           		 
//            		Properties props = new Properties();
//                    String propFilePath = PROP_PATH + "/property.properties";
//                    File propFile = new File(propFilePath);
//                    props.load(new FileReader(propFile));
//                    //mbean.importBizsysVariableFromProperty(props, sysID);
//                    deployResult_Prop = conn.importBizsysVariableFromProperty(props, bizSysId);
//            	}
//            	
//            }
//            
//            
//            // deploy- commonLib --> target : dir-name(endsWith) matching bizSystem
//            List<DeploymentInfo> libInfo = manager.extractLibFromDir(IAR_PATH);
//            if(isExistLIB)
//            {
//            	for(String bizSysId : conn.getBizSystemIdList() )
//            	{
//            		
//            		if(sysID.equals(bizSysId))
//            		{
//            			printLog(pw, "####CM ComLib Deploy target bizSysId=>"+bizSysId, true);
//                		
////                		List<DeploymentInfo> libInfo = manager.extractLibFromDir(IAR_PATH);
//                		List<DeploymentInfo> deployLibInfo = new ArrayList<DeploymentInfo>();
//                        for(DeploymentInfo info : libInfo)
//                        {
//                        	String currFilePath = info.getFilePath(); // xxx.jar
//                        	
//                        	printLog(pw, "####CM currFilePath="+currFilePath, true);
//                        	
//                        	File currFile = new File(currFilePath);
//                        	if(currFile.exists() && currFile.isFile())
//                        	{
//                        		String parentDir = currFile.getParent();
//                        		printLog(pw, "####CM parentDir="+parentDir, true);
//                        		
//                        		if( parentDir.endsWith(sysID) || (parentDir+"B").endsWith(sysID) )
////                        		if( parentDir.endsWith(sysID) )
//                        		{
//                    				deployLibInfo.add(info);
//                        			printLog(pw, "####CM bizSysId equals parentDir endWith="+parentDir, true);
//                        		}
//                        	}
//                        	
//                        }
//                        
////                        libInfo = manager.prepareLibDeploymentList(libInfo, bizSysId);
////                        operator.deploySharedLibrary(libInfo);
//                        
//                        
//                        if(!deployLibInfo.isEmpty() && deployLibInfo.size() > 0)
//                        {
//                        	printLog(pw, "####CM lib deploy API Call bizSysId=["+bizSysId+"]", true);
//                        	manager.prepareLibDeploymentList(deployLibInfo, bizSysId);
//                        	deployResult =  conn.deploySharedLibrary(deployLibInfo);
//                        }
//                        else
//                        {
//                        	printLog(pw, "####CM deployLibInfo is Empty bizSysId=>" + bizSysId, true);
//                        }
//            			
//            		}
//            	}
//            }
//            
//            // deploy - Adapter, Endpoint
//            if(isExistCOMM)
//            {
//            	
//            	List<CommonInfoType> commInfo = manager.extractCommonInfoFromDir(COMM_PATH);
//                commInfo = manager.prepareCommonInfoDeploymentList(commInfo, sysID);
//                deployResult_Comm = conn.deployConfigurationInfo(commInfo);
//            }
//            
//            // deploy - IAR
//            if(isExistIAR)
//            {
//            	 List<DeploymentInfo> iarInfo = manager.extractIarInfoFromDir(IAR_PATH);
//                 iarInfo = manager.prepareIarDeploymentList(iarInfo, sysID, true);            
//                 deployResult = conn.deployIARResource(iarInfo);
//            }
//            
//         // Again deploy( for war ) - Adapter, Endpoint, 스케줄러, 잡, EP그룹 
//            if(isExistCOMM)
//            {
//            	List<CommonInfoType> commInfo = manager.extractCommonInfoFromDir(COMM_PATH);
//                commInfo = manager.prepareCommonInfoDeploymentList(commInfo, sysID);
//                deployResult_Comm = conn.deployConfigurationInfo(commInfo);
//            }
//            
//           
//            printLog(pw, "####CM deployResult=>" + deployResult, true);
//            
//            for(DeploymentResult dr : deployResult)
//        	{
//        		boolean errFlag = dr.getErrFlag();
//        		printLog(pw, "========CM root,middle,leaf Log start======", true );
//        		printLog(pw, "####CM errFlag=>" + errFlag , true);
//        		printLog(pw, "####CM dr.getSysId=>" + dr.getSysId() + "["+dr.getBizSystemid()+"]", true);
//        		printLog(pw, "####CM dr.getMassage=>" + dr.getMessage(), true );
//        		printLog(pw, "####CM dr.getFailureStage=>" + dr.getFailureStage(), true );
//        		if( errFlag )
//        		{
//        			isExistDeployError = true;
//        		}
//        	}
//            
//            for(DeploymentResult dr : deployResult_Comm)
//        	{
//        		boolean errFlag = dr.getErrFlag();
//        		String warResult = dr.getFailureStage();
//        		printLog(pw, "========CM EP,Adt,Job,Sch Log start======" , true);
//        		printLog(pw, "####CM errFlag=>" + errFlag , true);
//        		printLog(pw, "####CM dr.getSysId=>" + dr.getSysId() + "["+dr.getBizSystemid()+"]", true);
//        		printLog(pw, "####CM dr.getMassage=>" + dr.getMessage(), true );
//        		printLog(pw, "####CM dr.warDeployResult=>" + warResult , true);
//        		if( errFlag || warResult!=null )
//        		{
//        			isExistDeployError = true;
//        		}
//        	}
//            
//            if(deployResult_Prop != null)
//            {
//            	boolean errFlag = deployResult_Prop.getErrFlag();
//            	printLog(pw, "========CM root,middle,leaf Log start======" , true);
//            	printLog(pw, "####CM errFlag=>" + errFlag , true);
//            	printLog(pw, "####CM dr.getSysId=>" + deployResult_Prop.getSysId() + "["+deployResult_Prop.getBizSystemid()+"]", true);
//            	printLog(pw, "####CM dr.getMassage=>" + deployResult_Prop.getMessage(), true );
//            	printLog(pw, "####CM dr.getFailureStage=>" + deployResult_Prop.getFailureStage(), true );
//        		
//        		if( errFlag )
//        		{
//        			isExistDeployError = true;
//        		}
//            }
//            
//            printLog(pw, "####CM isExistDeployError =>" + isExistDeployError, true );
//            
//            printLog(pw, "####CM END =======================================================", true);
//            
//            if(isExistDeployError)
//            {
//            	 System.exit(-1);
//            }
//            else
//            {
//            	 System.exit(0);
//            }
//        }
//    	catch(Exception e)
//    	{
//    		printLog(pw, "####CM errFlag=>" + "true  catch Exception" , true);
//    		printLog(pw, "####CM Exception ############", true);
//    		printLog(pw, "####CM Exception Msg=["+e.getMessage()+"]############", true);
//    		e.printStackTrace();
//    		System.exit(-1);
//        }
//    	finally
//    	{
//    		closeLog(pw, bw, fw);
//    	}
//    }
//    
//    /**
//     * IAR Resource is Exist Checking Method
//     * @param IAR_PATH
//     * @return
//     */
//    public static boolean existIAR(String IAR_PATH)
//    {
//    	boolean returnBoolean = false;
//    	File file = new File(IAR_PATH);
//    	if(file!=null && file.exists() && file.isDirectory())
//    	{
//    		File[] childList = file.listFiles();
//    		for(File childFile : childList)
//    		{
//    			if(returnBoolean == true)
//    			{
//    				break;
//    			}
//    			else if(childFile!=null && childFile.exists() && childFile.isDirectory() )
//    			{
//    				File[] childChildList = childFile.listFiles();
//    				for(File childChildFile : childChildList)
//    				{
//    					if(childChildFile!=null && childChildFile.exists() && childChildFile.isFile())
//    					{
//    						String currentFileName = childChildFile.getName();
//    						if(currentFileName.endsWith(".iar"))
//    						{
//    							returnBoolean = true;
//    							break;
//    						}
//    					}
//    				}
//    			}
//    		}
//    	}
//    	
//    	return returnBoolean;
//    }
//    
//    /**
//     * COMM_RESOUCE is Exist Checking Method
//     * @param COMM_PATH
//     * @return
//     */
//    public static boolean existCOMM(String COMM_PATH)
//    {
//    	boolean returnBoolean = false;
//    	File file = new File(COMM_PATH); // root Directory
//    	if(file!=null && file.exists() && file.isDirectory())
//    	{
//    		File[] childList = file.listFiles();
//    		for(File childFile : childList)  // sub Directory Loop
//    		{
//    			if(returnBoolean == true)
//    			{
//    				break;
//    			}
//    			else if(childFile!=null && childFile.exists() && childFile.isDirectory() )
//    			{
//    				File[] childChildList = childFile.listFiles();  //
//    				for(File childChildFile : childChildList)   // sub sub Dir Loop
//    				{
//    					if(childChildFile!=null && childChildFile.exists() && childChildFile.isFile())
//    					{
//    						String currentFileName = childChildFile.getName();
//    						if(currentFileName.endsWith(".adt") || currentFileName.endsWith(".ep") || currentFileName.endsWith(".sjob") || currentFileName.endsWith(".epg") )
//    						{
//    							returnBoolean = true;
//    							break;
//    						}
//    					}
//    				}
//    			}
//    		}
//    	}
//    	
//    	return returnBoolean;
//    }
//    
//    /**
//     * SystemVariable Properties is Exist Checking Method
//     * @param PROP_PATH
//     * @return
//     */
//    public static boolean existPROP(String PROP_PATH)
//    {
//    	boolean returnBoolean = false;
//    	File file = new File(PROP_PATH); // root Directory
//    	if(file!=null && file.exists() && file.isDirectory())
//    	{
//    		File[] childList = file.listFiles();
//    		for(File childFile : childList)  // sub Files Loop
//    		{
//    			if(returnBoolean == true)
//    			{
//    				break;
//    			}
//    			else if(childFile!=null && childFile.exists() && childFile.isFile() )
//    			{
//					String currentFileName = childFile.getName();
//					if(currentFileName.endsWith(".properties") && !"ConfigurationTransfer.properties".equals(currentFileName))
//					{
//						returnBoolean = true;
//						break;
//					}
//    			}
//    		}
//    	}
//    	
//    	return returnBoolean;
//    }
//    
//    /**
//     * commonLib is Exist Checking Method
//     * @param LIB_PATH
//     * @return
//     */
//    public static boolean existLIB(String LIB_PATH)
//    {
//    	boolean returnBoolean = false;
//    	File file = new File(LIB_PATH); // root Directory
//    	if(file!=null && file.exists() && file.isDirectory())
//    	{
//    		File[] childList = file.listFiles();
//    		for(File childFile : childList)  // sub Directory Loop
//    		{
//    			if(returnBoolean == true)
//    			{
//    				break;
//    			}
//    			else if(childFile!=null && childFile.exists() && childFile.isDirectory() )
//    			{
//    				File[] childChildList = childFile.listFiles();  //
//    				for(File childChildFile : childChildList)   // sub sub Dir Loop
//    				{
//    					if(childChildFile!=null && childChildFile.exists() && childChildFile.isFile())
//    					{
//    						String currentFileName = childChildFile.getName();
//    						if(currentFileName.endsWith(".jar"))
//    						{
//    							returnBoolean = true;
//    							break;
//    						}
//    					}
//    				}
//    			}
//    		}
//    	}
//    	
//    	return returnBoolean;
//    }
//    
//    /**
//     * ProPerties Load
//     * @return
//     */
//    public static Properties load_file() 
//    {
//		Properties props = new Properties();
//		
//		FileInputStream fis = null;
//		InputStreamReader isr = null;
//		BufferedReader br = null;
//		
//		try
//		{
//			fis = new FileInputStream(PROP_PATH_NAME);
//			isr = new InputStreamReader(fis, "UTF-8");
//			br = new BufferedReader(isr);
//			props.load(br);
//			
//			br.close(); br=null;
//			isr.close(); isr=null;
//			fis.close(); fis=null;
//		} 
//		catch (FileNotFoundException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			if(br!=null)  { try {br.close(); br = null;		} catch (IOException e) {	e.printStackTrace();}  }
//			if(isr!=null) { try {isr.close();isr = null;	} catch (IOException e) {	e.printStackTrace();}  }
//			if(fis!=null) { try {fis.close();fis = null;	} catch (IOException e) {	e.printStackTrace();}  }
//		}
//			
//		return props;
//	}
    
}
