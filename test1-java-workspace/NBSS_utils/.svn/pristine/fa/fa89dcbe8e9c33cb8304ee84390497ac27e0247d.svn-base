import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class BizTx_ORBL_TimeoutSearch
{
	/****** 로직 설명
	====================================================================================================
	0. 서버에서 이관관리 화면을 이용하여 모든 거래 파일 다운로드 후 UnZip 
	0. 그후 모든 파일 검사하여 Root 리스트와 Middle 리스트와 Leaf 리스트와 Outbound 리스트 세팅
	0. 세팅된 3개의 리스트를 가지고 아래 로직 처리
	0. Root 맵을 1번처럼 세팅
	0. Middle 맵을 2번처럼 세팅 
	0. Leaf 맵을 3번처럼 세팅
	0. Outbound 맵을 4번 처럼 세팅 

	1. 노트타입 [Root] Map 세팅 key 는 sysId value는 businessTimeout 으로 한다. 

	<ns0:nodeType>Root</ns0:nodeType>
	<ns0:sysId>nbss.bcc.ife.r_EDZN_TCP_IN_01_EP_001</ns0:sysId>
	<ns0:businessTimeout>15000</ns0:businessTimeout>

	2. 노트타입이 [middle] Map 세팅

	<ns0:nodeType>Middle</ns0:nodeType>
	<ns0:parentId>nbss.bcc.ife.r_NBCC_WS_IN_01_EP_002</ns0:parentId>
	<ns0:sysId>nbss.bcc.ife.r_NBCC_WS_IN_01_EP_002.r_NBCC_WS_IN_01_EP_002</ns0:sysId>

	   2-1. businessTimeout 이 존재할 경우 key는 sysId  value는 businessTimeout
	   2-2-1.  businessTimeout 이 존재하지 않을 경우 Root 맵에서 parentId 가지고 찾는다. 
	           parentId가 존재할 경우 get해서 key는 sysId  Value 는 businessTimeout로 Middle Map에 세팅
	         
	   2-2-2.  parentId가 존재하지 않을 경우 별도의 Map(unsearchedMap)에 Add 한다.
	   
	   2-3. for(0~10)  
	          { Map(unsearchedMap)에서 get하여 Middle Map 에서 찾는다. 
	            존재 할 경우 key는 sysId Value 는 찾은 timeOut 값으로 middleMap에 Put 한다. 
	            그 후  Map(unsearchedMap)에서 remove 한다. 
	           
	            if(맵 is empty ) break; 
	          }
	   2-4.  Map(unsearchedMap) 프린트 해보고 다 잘 찾았는지 확인
	   
	3. 노트타입이 [Leaf] Map 세팅

	<ns0:nodeType>Leaf</ns0:nodeType>
	<ns0:sysId>nbss.bcc.ife.r_EDZN_TCP_IN_01_EP_001.l_NIFE_EDZN_NBCC_I_1008_00</ns0:sysId>
	<ns0:parentId>nbss.bcc.ife.r_EDZN_TCP_IN_01_EP_001</ns0:parentId>


	   3-1. businessTimeout 이 존재할 경우 key는 sysId  value는 businessTimeout
	   3-2-1.  businessTimeout 이 존재하지 않을 경우 Middle 맵에서 parentId 가지고 찾는다. 
	            
	   3-2-2.  Middle 맵 존재하지 않을 경우 Root 맵에서 parentId 가지고 찾는다.
	   
	   3-3.  Middle, Root 맵에 둘다 존재 하지 않을 경우를 출력한다 ( 뭔가 잘못됨 )
	
	4-1. ep 종류별 timeout 정보 획득 (db)   
	   
	4-2. 아웃바운드 Map 세팅
	<ns0:requestTimeout>10000</ns0:requestTimeout>
	<ns0:package>nbss.bcc.ife.r_EDZN_TCP_IN_01_EP_001.l_NIFE_EDZN_NBCC_I_1008_00</ns0:package>
	<ns0:sysId>nbss.bcc.ife.r_EDZN_TCP_IN_01_EP_001.l_NIFE_EDZN_NBCC_I_1008_00:OBRL_NBCC_WS_OUT_01_EP_001.orule</ns0:sysId>

	   4-2-1 Leaf Map 에서  package를 key로 하여 Timeout을 찾는다. 
	       만일 찾은값보다 크거나 같으면 안된다. -> 별도 오류맵에 보관
	       
	       key는 package 로 Value는 List 안에 sysId#^#Timeout을 형태로 맵에 별도 보관 
	       
	5. Leaf 맵 기준으로 출력 후 key값을 기준으로 아웃바운드 Map에서 꺼내서 List안에서 #^# 로 스플릿하여 Timeout출력
	 ******/
	
	private static String nodeTypeXPath    = "/*[local-name()='bizTxInfo']/*[local-name()='nodeType']";
	private static String sysIdXPath 	   = "/*[local-name()='bizTxInfo']/*[local-name()='sysId']";
	private static String timeOutXPath     = "/*[local-name()='bizTxInfo']/*[local-name()='businessTimeout']";
	private static String parentIdXPath    = "/*[local-name()='bizTxInfo']/*[local-name()='parentId']";
	private static String obrlSysIdXPath   = "/*[local-name()='outboundRuleInfo']/*[local-name()='sysId']";
	private static String obrlTimeOutXPath = "/*[local-name()='outboundRuleInfo']/*[local-name()='requestTimeout']";
	private static String obrlPackageXPath = "/*[local-name()='outboundRuleInfo']/*[local-name()='package']";
	private static String obrlEpXPath 	   = "/*[local-name()='outboundRuleInfo']/*[local-name()='endpointId']";
	
	private static String epTcpTimeoutXPath  = "/*[local-name()='endpointInfo']/*[local-name()='tcpEndpointInfo']/*[local-name()='idleTimeout']";
	private static String epWSTimeoutXPath   = "/*[local-name()='endpointInfo']/*[local-name()='webServiceEndpointInfo']/*[local-name()='asyncTimeout']";
	private static String epHttpTimeoutXPath = "/*[local-name()='endpointInfo']/*[local-name()='httpEndpointInfo']/*[local-name()='soTimeout']";
	private static String epFtpTimeoutXPath  = "/*[local-name()='endpointInfo']/*[local-name()='ftpEndpointInfo']/*[local-name()='dataTimeout']";
	
	private static final XMLUtils xmlUtils = XMLUtils.getInstance();
	
	private static SAXBuilder builder = new SAXBuilder();
	
	public static void main(String[] args) 
	{
		
		
//		String dbUrl = "jdbc:tibero:thin:@10.217.159.215:8634:SIT2GWI"; //sit2 대내
		String dbUrl = "jdbc:tibero:thin:@10.217.159.65:8640:SITGWE"; //sit2 대외
		String dbUsr = "infinilink";
		String dbPwd = "infinilink";
		
		String bizSystemId    = "EO_NAC1";
		String srcFolderDir   = "/app/infinilink/jeus7/server/dis/contentRepository/ConfigurationTransfer/EO_NAC1";
		String exportFilePath = "/home/infadm/test/TimeOutSearch";
		String exportFileName = "bizTxTimeOut_" + bizSystemId;
		
		System.out.println("===Strat===["+bizSystemId+"]");
		
		//0. 모든 파일 언집 
		File file = new File(srcFolderDir);
		getChildFiles(file); 
		
		
		List<String[]> rootLst   = new ArrayList<String[]>();
		List<String[]> middleLst = new ArrayList<String[]>();
		List<String[]> leafLst   = new ArrayList<String[]>();
		List<String[]> obrlLst   = new ArrayList<String[]>();
		
		Map<String, String> rootMap = new HashMap<String, String>();
		Map<String, String> middleMap = new HashMap<String, String>();
		Map<String, String> leafMap = new HashMap<String, String>();
		Map<String, String> obrlMap = new HashMap<String, String>();
		Map<String, String> obrlMap_err = new HashMap<String, String>();
		Map<String, String> unSearchedMiddleMap = new HashMap<String, String>();
		
		Map<String, String[]> epInfoMap = new HashMap<String, String[]>();
		
		try
		{
			//0. 그후 모든 파일 검사하여 Root 리스트와 Middle 리스트와 Leaf 리스트와 Outbound 리스트 세팅
			setLst(rootLst, middleLst, leafLst, obrlLst, file);
			
			//1. 노트타입 [Root] Map 세팅 
			//key 는 sysId value는 businessTimeout 으로 한다. 
			for(String[] rootInfo : rootLst )
			{
				String sysId    = rootInfo[0];
				String timeOut  = rootInfo[1];
				
				rootMap.put(sysId, timeOut);
				
//				System.out.println("root sysId="+sysId + "   timeout="+timeOut);
			}
			
			//2. 노트타입이 [middle] Map 세팅
			for(String[] middleInfo : middleLst )
			{
				String sysId    = middleInfo[0];
				String timeOut  = middleInfo[1];
				String ParentId = middleInfo[2];
				
//				System.out.println("middleInfo sysId="+sysId + "   timeout="+timeOut + "   ParentId="+ParentId);
				
				//2-1. businessTimeout 이 존재할 경우 key는 sysId  value는 businessTimeout
				if(timeOut!=null)
				{
					middleMap.put(sysId, timeOut);
				}
				else
				{
					//2-2-1.  businessTimeout 이 존재하지 않을 경우 Root 맵에서 parentId 가지고 찾는다. 
					//        parentId가 root맵에 존재할 경우 get해서 key는 sysId  Value 는 businessTimeout로 Middle Map에 세팅
					String parentTimeOut = rootMap.get(ParentId);
					
					if(parentTimeOut!=null)
					{
						middleMap.put(sysId, parentTimeOut);
					}
					else
					{
						//2-2-2.  parentId가 존재하지 않을 경우 별도의 Map(unsearchedMap)에 Add 한다. ( root - mid1 - mid2 -leaf 일 경우 mid2는 parent가 rootMap에 존재하지 않음 )
						unSearchedMiddleMap.put(sysId, ParentId);
					}
				}
			}
			
			
			
			//2-3. for(0~10)  : unSearchMiddleMap 에서 middleMap으로 세팅  ( 최대 10 depth 까지 있다고 가정 )
			for(int i=0; i<10; i++)
			{
				//unSearchedMiddleMap에서 get하여 Middle Map 에서 찾는다. 
				Iterator<String> it = unSearchedMiddleMap.keySet().iterator();
				while(it.hasNext())
				{
					String sysId    = it.next();
					String parentId = unSearchedMiddleMap.get(sysId);
					
					// 존재 할 경우 key는 sysId Value 는 찾은 timeOut 값으로 middleMap에 Put 한다.
					// 그 후 unSearchedMiddleMap 에서 remove 한다. 
					String timeOut = middleMap.get(parentId);
					if(timeOut != null)
					{
						middleMap.put(sysId, timeOut);
						unSearchedMiddleMap.remove(sysId);
						
						i=0;  //맵에서 지우고 다시 처음부터
						break;
						
						
					}
				}
				
				//if(맵 is empty ) break; 
				if(unSearchedMiddleMap.isEmpty())
				{
					break;
				}
			}
			
			
			//2-4.  Map(unsearchedMap) 프린트 해보고 다 잘 찾았는지 확인
			if( !unSearchedMiddleMap.isEmpty() )
			{
				System.out.println("!!!!!! unSearchedMiddleMap is not Empty  !!!!");
				Iterator<String> it = unSearchedMiddleMap.keySet().iterator();
				while(it.hasNext())
				{
					String sysId    = it.next();
					String parentId = unSearchedMiddleMap.get(sysId);
					
					System.out.println("!!!!!!  unSearchedMiddleMap is not Empty sysId=["+sysId+"], parentId=["+parentId+"]  !!!!");
					System.exit(-1);
				}
			}
			
			
			//3. 노트타입이 [Leaf] Map 세팅
			for(String[] leafInfo : leafLst)
			{
				String sysId    = leafInfo[0];
				String timeOut  = leafInfo[1];
				String ParentId = leafInfo[2];
				
//				System.out.println("leafInfo sysId="+sysId + "   timeout="+timeOut + "   ParentId="+ParentId);
				
				//3-1. businessTimeout 이 존재할 경우 key는 sysId  value는 businessTimeout
				if(timeOut!=null)
				{
					leafMap.put(sysId, timeOut);
				}
				else
				{
					//3-2-1.  businessTimeout 이 존재하지 않을 경우 Middle 맵에서 parentId 가지고 찾는다.
					String parentTimeOut = middleMap.get(ParentId);
					if(parentTimeOut!=null)
					{
						leafMap.put(sysId, parentTimeOut);
					}
					else
					{
						//3-2-2.  Middle 맵 존재하지 않을 경우 Root 맵에서 parentId 가지고 찾는다.
						String rootTimeOut = rootMap.get(ParentId);
						if(rootTimeOut!=null)
						{
							leafMap.put(sysId, rootTimeOut);
						}
						else
						{
							//3-3.  Middle, Root 맵에 둘다 존재 하지 않을 경우를 출력한다 ( 뭔가 잘못됨 )
							System.out.println("[ERROR]!!!!!! leafInfo is not searched sysId=["+sysId+"] !!!!");
							System.exit(-1);
						}
					}
				}
			}
			
			//4-1. ep의 timeout 정보 획득 ( db ) 
			getEpInfo(bizSystemId, dbUrl, dbUsr, dbPwd, epInfoMap);
			
			
			//4-2. 아웃바운드 Map 세팅
			for(String[] obrlInfo : obrlLst)
			{
				String sysId    = obrlInfo[0];
				String timeOut  = obrlInfo[1];
				String _package = obrlInfo[2];
				String epId     = obrlInfo[3];
				
				String[] epInfo = epInfoMap.get(epId);  // epInfoMap.put(SYS_ID, new String[]{timeOut, BIZTX_ID});
				String epTimeOut = "not found";
				if(epInfo != null && epInfo.length > 1)
				{
					epTimeOut = epInfo[0].trim();
				}
				
				String temp = obrlMap.get(_package); //아웃바운드 엔드포인트는 여러개 있을 수 있음
				String putStr = "";
				if(temp==null || "".endsWith(temp.trim()))
				{
					putStr = sysId+"#"+timeOut+"#"+epId+"#"+epTimeOut;
				}
				else
				{
					putStr = temp+"#"+sysId+"#"+timeOut+"#"+epId+"#"+epTimeOut;
				}
				obrlMap.put(_package, putStr);
				
				
				//4-2-1. Leaf Map 에서  package를 key로 하여 Timeout을 찾는다. 
				//     만일 찾은값보다 크거나 같으면 안된다. -> 별도 오류맵에 보관
				String bizTxTimeOut = leafMap.get(_package);
				if(bizTxTimeOut==null) //미들 밑에 obrl 이 존재할 경우 미들맵에서 찾아야 함
				{
					bizTxTimeOut = middleMap.get(_package);
				}
				
				if(bizTxTimeOut==null) //루트 밑에 obrl 이 존재할 경우 루트맵에서 찾아야 함
				{
					bizTxTimeOut = rootMap.get(_package);
				}
				
				long bizTxTimeOut_l = Long.parseLong(bizTxTimeOut);
				long obrlTimeOut_l  = Long.parseLong(timeOut);
				
				if( obrlTimeOut_l > bizTxTimeOut_l)
				{
					obrlMap_err.put(_package, putStr);
					System.out.println("!!!! obrlInfo_err sysId=["+sysId+"] bizTxTimeOut_l=["+bizTxTimeOut_l+"] obrlTimeOut_l=["+obrlTimeOut_l+"]  !!!");
				}
				
				String[] putStrSplit = putStr.split("#");
				if(putStrSplit!=null)
				{
					int putStrSplitLength = putStrSplit.length;
					for(int i=3; i<putStrSplitLength; i=i+4)
					{
						String currentEpTimeOut = putStrSplit[i].trim();
						if("nothing".equals(currentEpTimeOut) || "not found".equals(currentEpTimeOut) || "".equals(currentEpTimeOut))
						{
							currentEpTimeOut = "0";
						}
						
						long currEpTimeOut_l = Long.parseLong(currentEpTimeOut);
						
						if( currEpTimeOut_l > bizTxTimeOut_l )
						{
							obrlMap_err.put(_package, putStr);
							System.out.println("!!!! obrlInfo_err sysId=["+sysId+"] bizTxTimeOut_l=["+bizTxTimeOut_l+"] obrlTimeOut_l=["+obrlTimeOut_l+"] putStr=["+putStr+"]  !!!");
						}
					}
				}
			}
			
			
			
		} 
		catch (JDOMException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		exportCsv(bizSystemId, leafMap, obrlMap, obrlMap_err, exportFilePath, exportFileName);
		
		System.out.println("===End===");
		
		
	}
	
	public static void getEpInfo(String bizSystemId, String dbUrl, String dbUsr, String dbPwd, Map<String, String[]> epInfoMap)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		XMLUtils xu = XMLUtils.getInstance();
		try
		{
			Class.forName("com.tmax.tibero.jdbc.TbDriver");
			con = DriverManager.getConnection(dbUrl, dbUsr, dbPwd);
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT                                                    	  \n");
			query.append("         A.PROTOCOL  	        AS PROTOCOL                   	  \n");
			query.append("       , A.ADAPTER_ID        	AS ADAPTER_ID                	  \n");
			query.append("       , A.BIZSYSTEM_ID		AS BIZSYSTEM_ID   	       	      \n");
			query.append("  	 , A.SYS_ID				AS SYS_ID                  	      \n");
			query.append("  	 , A.DIRECTION			AS DIRECTION  		       	      \n");
			query.append("  	 , A.RUNNING_STATE		AS RUNNING_STATE 	       	      \n");
			query.append("  	 , A.BIZTX_ID			AS BIZTX_ID	               	      \n");
			query.append("  	 , A.CONTEXT_PATH		AS CONTEXT_PATH         	      \n");
			query.append("  	 , A.ROUTING	        AS ROUTING  	       	          \n");
			query.append("  	 , B.CONTENT			AS CONTENT 			  	          \n");
			query.append("  FROM IL_ADAPTER A,   							  	          \n");
			query.append("       IL_RESOURCE B   							   			  \n");
			query.append("  WHERE 1=1   											      \n");
			query.append("  AND   A.BIZSYSTEM_ID  = '"+bizSystemId+"'   			      \n");
			query.append("  AND   A.RESOURCE_TYPE = 'ENDPOINT'   			        	  \n");
			query.append("  AND   B.RESOURCE_TYPE = 'ENDPOINT'   			    	      \n");
			query.append("  AND   A.SYS_ID = B.SYS_ID AND A.BIZSYSTEM_ID = B.BIZSYSTEM_ID \n");
			query.append("  ORDER BY BIZSYSTEM_ID ASC, PROTOCOL ASC, SYS_ID ASC           \n");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query.toString());
			
			while(rs.next())
			{
				int indx = 1;
				String PROTOCOL 	 = rs.getString(indx++);
				String ADAPTER_ID  	 = rs.getString(indx++);
				String BIZSYSTEM_ID  = rs.getString(indx++);
				String SYS_ID  		 = rs.getString(indx++);
				String DIRECTION 	 = rs.getString(indx++);
				String RUNNING_STATE = rs.getString(indx++);
				String BIZTX_ID 	 = rs.getString(indx++);
				String CONTEXT_PATH  = rs.getString(indx++);
				String ROUTING 		 = rs.getString(indx++);
				String HEXA_Content  = rs.getString(indx++);
				
				byte[] b = DatatypeConverter.parseHexBinary(HEXA_Content);
				//String content = new String(b, "UTF-8");
				
				//find maxConnection Count
				Document doc = xu.makeDocument(b);
				
				String timeOut = "";
				
				if("TCP".equals(PROTOCOL)) // in/out 동일
				{
					Element el_timeOut   = xmlUtils.getElement(doc, epTcpTimeoutXPath);
					timeOut = elementGetValue(el_timeOut);
				}
				else if("WEB_SERVICE".equals(PROTOCOL) && "INBOUND".equals(DIRECTION)) //outbound는 없음
				{
					Element el_timeOut   = xmlUtils.getElement(doc, epWSTimeoutXPath);
					
					timeOut = "3600000"; //않넣으면 디폴트 1시간
					if(el_timeOut !=null)
					{
						timeOut = elementGetValue(el_timeOut);
						if("".equals(timeOut.trim()))
						{
							timeOut = "3600000";
						}
					}
				}
				else if("WEB_SERVICE".equals(PROTOCOL) && "OUTBOUND".equals(DIRECTION)) //outbound는 없음
				{
					timeOut = "nothing"; 
				}
				else if("HTTP".equals(PROTOCOL) && "OUTBOUND".equals(DIRECTION)) // inbound는 없음
				{
					Element el_timeOut   = xmlUtils.getElement(doc, epHttpTimeoutXPath);
					timeOut = elementGetValue(el_timeOut);
				}
				else if("HTTP".equals(PROTOCOL) && "INBOUND".equals(DIRECTION)) // inbound는 jeus WebConnection
				{
					timeOut = "nothing"; 
				}
				else if("FTP".equals(PROTOCOL))
				{
					Element el_timeOut   = xmlUtils.getElement(doc, epFtpTimeoutXPath);
					timeOut = String.valueOf(Integer.parseInt(elementGetValue(el_timeOut)) * 1000 ); // 단위가 (s) 임
				}
				else
				{
					timeOut = "nothing"; 
				}
				
				epInfoMap.put(SYS_ID, new String[]{timeOut, BIZTX_ID});
			}
			
			if(rs!=null)  { rs.close();   rs = null;  }
			if(stmt!=null){ stmt.close(); stmt = null;}
			if(con!=null) { con.close();  con = null; }
			
			
			System.out.println("************ Db Select End ************");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(stmt!=null){ try {	stmt.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt = null;}
			if(con!=null) { try {	con.rollback(); con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
		}
		finally
		{
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(stmt!=null){ try {	stmt.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt = null;}
			if(con!=null){ try { con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
		}
	}
	
	public static void printLog(PrintWriter pw, String logStr)
	{
		pw.println(logStr);
	}
	
	public static void closeResouce(PrintWriter pw, BufferedWriter bw, FileWriter fw)
	{
		if(pw!=null)
		{
			pw.close(); pw=null;
		}
		if(bw!=null)
		{
			try{bw.close(); bw=null;} catch (IOException e) {e.printStackTrace();}
		}
		if(fw!=null)
		{
			try {fw.close(); fw=null;} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public static void unzip(InputStream is, File destDir, String charsetName) throws IOException
	{
		ZipArchiveInputStream zis;
		ZipArchiveEntry entry;
		String name;
		File target;
		int nWritten=0;
		BufferedOutputStream bos;
		byte[] buf = new byte[1024*8];
		zis = new ZipArchiveInputStream(is, charsetName, false);
		
		while (( entry = zis.getNextZipEntry()) !=null )
		{
			name = entry.getName();
			if(! ( name.endsWith(".biztx") || name.endsWith(".orule")  ) )
			{
				continue;
			}
			target = new File(destDir, name);
			if(entry.isDirectory())
			{
				target.mkdir();
			}
			else
			{
				target.createNewFile();
				bos = new BufferedOutputStream(new FileOutputStream(target));
				
				while(( nWritten = zis.read(buf)) >= 0)
				{
					bos.write(buf, 0, nWritten);
				}
				bos.close();
			}
		}
		zis.close();
	}
	
	public static File[] getChildFiles(File parent)
	{
		if(parent.isFile())
		{
			String fileName = parent.getName();
			if(fileName.endsWith(".iar"))
			{
				String filePath = parent.getParent();
				
//				System.out.println("fileName="+fileName);
//				System.out.println("filePath="+filePath);
				
				try
				{
						unzip(new FileInputStream(parent), new File(filePath), Charset.defaultCharset().name());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return null;
		}
		else
		{
			File[] files = parent.listFiles();
			for(File file : files)
			{
				getChildFiles(file);
			}
						
			return files;
		}
	}
	
	public static void setLst(List<String[]> rootLst, List<String[]> middleLst, List<String[]> leafLst, List<String[]> obrlLst, File file) throws JDOMException, IOException
	{
		File[] files = file.listFiles();
		{
			for(File currFile : files)
			{
				String fileName = currFile.getAbsolutePath();
				
				if(currFile.isFile())
				{
					if(fileName.endsWith(".biztx"))
					{
						Document doc = builder.build(currFile);
						
						Element el_NodeType = xmlUtils.getElement(doc, nodeTypeXPath);
						Element el_SysId    = xmlUtils.getElement(doc, sysIdXPath);
						Element el_TimeOut  = xmlUtils.getElement(doc, timeOutXPath);
						Element el_ParentId = xmlUtils.getElement(doc, parentIdXPath);

						String nodeType = elementGetValue(el_NodeType);
						String sysId    = elementGetValue(el_SysId);
						String timeOut  = el_TimeOut==null? null : elementGetValue(el_TimeOut);
						String parentId = el_ParentId==null?null : elementGetValue(el_ParentId);
						String[] bizTxInfo = new String[]{sysId,timeOut,parentId};
						if("Root".equals(nodeType))
						{
							rootLst.add(bizTxInfo);
						}
						else if("Middle".equals(nodeType))
						{
							middleLst.add(bizTxInfo);
						}
						else if("Leaf".equals(nodeType))
						{
							leafLst.add(bizTxInfo);
						}
						else
						{
							System.out.println("!!!!! BizTx NodeType not Found !!!! fileName=>" +fileName );
						}
						
					}
					else if(fileName.endsWith(".orule"))
					{
						
						Document doc = builder.build(currFile);
						Element el_SysId   = xmlUtils.getElement(doc, obrlSysIdXPath);
						Element el_TimeOut = xmlUtils.getElement(doc, obrlTimeOutXPath);
						Element el_Package = xmlUtils.getElement(doc, obrlPackageXPath);
						Element el_EpId    = xmlUtils.getElement(doc, obrlEpXPath);
						
						
						String sysId    = elementGetValue(el_SysId);
						String timeOut  = elementGetValue(el_TimeOut);
						String _package = elementGetValue(el_Package);
						String epId     = elementGetValue(el_EpId);
						
						obrlLst.add(new String[]{sysId, timeOut, _package, epId});
					}
				}
			}
			
			for(File currFile : files)
			{
				if(currFile.isDirectory())
				{
					setLst(rootLst, middleLst, leafLst, obrlLst, currFile);
				}
			}
		}
		
	}
	
	private static String elementGetValue(Element e)
	{
		String value = "null";
		
		if(e != null)
		{
			value = e.getValue();
		}
		
		return value;
	}
	
	
	private static void exportCsv(String bizSysId, Map<String, String> leafMap, Map<String, String> obrlMap, Map<String, String> obrlMap_err, String exportFilePath, String exportFileName)
	{
		File logDir = new File(exportFilePath);
		
		if(logDir==null || !logDir.exists())
    	{
    		logDir.mkdir();
    	}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String csvFileName = exportFileName + "_"+sdf.format(new Date()) + ".csv";
    	
    	File csvFile = new File(exportFilePath, csvFileName);
    	if(csvFile==null || !csvFile.exists())
    	{
    		try
    		{
    			csvFile.createNewFile();
			}
    		catch (IOException e) 
    		{
    			e.printStackTrace();
			}
    	}
    	else
    	{
    		csvFile.delete();
    		try
    		{
    			csvFile.createNewFile();
			}
    		catch (IOException e) 
    		{
    			e.printStackTrace();
    		}
    	}
    	
    	
    	FileOutputStream fos = null;
    	OutputStreamWriter osw = null;
    	Writer bw = null;
    	
    	try 
    	{
    		fos = new FileOutputStream(csvFile);
    		osw = new OutputStreamWriter(fos, "EUC-KR");
    		bw = new BufferedWriter(osw);
			
    		bw.write("TimeOutSearch Result" + System.lineSeparator());
    		bw.write(System.lineSeparator());
	    	String header = "업무시스템,거래 ID,거래 TimeOut,아웃바운드 Rule ID,Rule TimeOut, Ep Id, Ep TimeOut";
	    	bw.write(header  + System.lineSeparator() );
    	
	    	Iterator<String> it = leafMap.keySet().iterator();
	    	while(it.hasNext())
	    	{
	    		String sysId = it.next();
	    		String timeOut = leafMap.get(sysId);
	    		String obrlInfoData = obrlMap.get(sysId); // key : package   val : sysId+"#"+timeOut
	    		
	    		String obrlSysId = "";
	    		String obrlTimeOut = "";
	    		String ob_epId = "";
	    		String ob_epTimeOut = "";
	    		if(obrlInfoData!=null)
	    		{
	    			String[] obrlInfo = obrlInfoData.split("#");
	    			
	    			obrlSysId    = obrlInfo[0];
	    			obrlTimeOut  = obrlInfo[1];
	    			ob_epId      = obrlInfo[2];
	    			ob_epTimeOut = obrlInfo[3];
	    			
	    		}
	    		
	    		String printStr = bizSysId +","+ sysId +","+ timeOut +","+ obrlSysId +","+ obrlTimeOut +","+ ob_epId +","+ ob_epTimeOut;
	    		bw.write(printStr  + System.lineSeparator());
	    	}
	    	
	    	if(!obrlMap_err.isEmpty())
	    	{
	    		bw.write(System.lineSeparator());
	    		bw.write(System.lineSeparator());
	    		
	    		bw.write("OutBoundRule-TimeOut 이 거래 TimeOut 보다 크게 설정된 소스"+System.lineSeparator());
	    		bw.write("거래 ID,sysId+#+거래timeOut+#+epId+#+epTimeOut"+System.lineSeparator());
	    		
	    		Iterator<String> it_err = obrlMap_err.keySet().iterator();
	    		while(it_err.hasNext())
	    		{
	    			String errBizTxId = it_err.next();
	    			String errObrlInfoData = obrlMap_err.get(errBizTxId);
	    			
	    			bw.write(errBizTxId+","+errObrlInfoData+System.lineSeparator());
	    		}
	    	}
	    	
    	}
    	catch (IOException e) 
    	{
    		e.printStackTrace();
		}
		
    	finally
    	{
    		closeLog(fos, osw, bw);
    	}
	}
	
	private static void closeLog(FileOutputStream fos, OutputStreamWriter osw, Writer bw)
	{
		if(bw!=null)
		{
			try {bw.close(); bw=null;} catch (IOException e) {e.printStackTrace();}
		}
		if(osw!=null)
		{
			try{osw.close(); osw=null;} catch (IOException e) {e.printStackTrace();}
		}
		if(fos!=null)
		{
			try {fos.close(); fos=null;} catch (IOException e) {e.printStackTrace();}
		}
	}

}