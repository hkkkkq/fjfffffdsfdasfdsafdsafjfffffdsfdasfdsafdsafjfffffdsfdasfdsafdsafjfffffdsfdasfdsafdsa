import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.jdom2.Document;
import org.jdom2.Element;

public class EP_Connect_Check 
{
	private static final XMLUtils xu = XMLUtils.getInstance();
	
	private static String epTcpConnectType  = "/*[local-name()='endpointInfo']/*[local-name()='tcpEndpointInfo']/*[local-name()='connectType']";
	private static String epTcpIp  			= "/*[local-name()='endpointInfo']/*[local-name()='tcpEndpointInfo']/*[local-name()='address']";
	private static String epTcpPort  		= "/*[local-name()='endpointInfo']/*[local-name()='tcpEndpointInfo']/*[local-name()='port']";
	
	private static String epFtpIp  			= "/*[local-name()='endpointInfo']/*[local-name()='ftpEndpointInfo']/*[local-name()='address']";
	private static String epFtpPort  		= "/*[local-name()='endpointInfo']/*[local-name()='ftpEndpointInfo']/*[local-name()='port']";
	
	private static String epWsIp  			= "/*[local-name()='endpointInfo']/*[local-name()='webServiceEndpointInfo']/*[local-name()='hostVariable']";
	private static String epWsPort  		= "/*[local-name()='endpointInfo']/*[local-name()='webServiceEndpointInfo']/*[local-name()='portVariable']";
	
	private static String epHttpSplit  		= "/*[local-name()='endpointInfo']/*[local-name()='httpEndpointInfo']/*[local-name()='split']";
	private static String epHttpUrl  		= "/*[local-name()='endpointInfo']/*[local-name()='httpEndpointInfo']/*[local-name()='url']";
	
	private static String epHttpIp  		= "/*[local-name()='endpointInfo']/*[local-name()='httpEndpointInfo']/*[local-name()='host']";
	private static String epHttpPort  		= "/*[local-name()='endpointInfo']/*[local-name()='httpEndpointInfo']/*[local-name()='port']";
	
	private static String bizSysVar_IsEnc   = "/*[local-name()='bizsysVariables']/*[local-name()='encrypted']";
	
	private static String elementGetValue(Element e)
	{
		String value = "not found";
		
		if(e != null)
		{
			value = e.getValue();
		}
		
		return value;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("************ Start EP Connection Info ************");
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Statement stmt2 = null;
		ResultSet rs2 = null;
		
		Statement stmt3 = null;
		ResultSet rs3 = null;
		
		String dbIp   = args[0];
		String dbPort = args[1];
		String dbSid  = args[2];
		String dbId   = args[3];
		String dbPw   = args[4];
		
//		String dbIp   = "10.217.159.55";
//		String dbPort = "8640";
//		String dbSid  = "SITGWE";
//		String dbId   = "infinilink";
//		String dbPw   = "infinilink";
		
		String dbUrl = "jdbc:tibero:thin:@"+dbIp+":"+dbPort+":"+dbSid;
		
		
		String dbUsr = dbId;
		String dbPwd = dbPw;
		
		Map<String, Document> varialeLstMap = new HashMap<String, Document>();
		
		try
		{
			Class.forName("com.tmax.tibero.jdbc.TbDriver");
			con = DriverManager.getConnection(dbUrl, dbUsr, dbPwd);
			
			String bizsystemVarQuery = "SELECT BIZSYSTEM_ID, CONTENT  FROM IL_RESOURCE WHERE RESOURCE_TYPE = 'BIZSYSTEM_VARIABLE'";
			stmt2 = con.createStatement();
			rs2 = stmt2.executeQuery(bizsystemVarQuery);
			
			
			while(rs2.next())
			{
				String BIZSYSTEM_ID = rs2.getString(1);
				String HEXA_Content  = rs2.getString(2);
				
				byte[] b = DatatypeConverter.parseHexBinary(HEXA_Content);
				
				Document doc = xu.makeDocument(b);
				varialeLstMap.put(BIZSYSTEM_ID, doc);
			}
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT                                                    	  \n");
			query.append("         A.PROTOCOL  	        AS PROTOCOL                   	  \n");
			query.append("       , A.ADAPTER_ID        	AS ADAPTER_ID                	  \n");
			query.append("       , A.BIZSYSTEM_ID		AS BIZSYSTEM_ID   	       	      \n");
			query.append("  	 , A.SYS_ID				AS SYS_ID                  	      \n");
			query.append("  	 , B.ID					AS ID			           	      \n");
			query.append("  	 , A.DIRECTION			AS DIRECTION  		       	      \n");
			query.append("  	 , A.RUNNING_STATE		AS RUNNING_STATE 	       	      \n");
			query.append("  	 , A.BIZTX_ID			AS BIZTX_ID	               	      \n");
			query.append("  	 , A.CONTEXT_PATH		AS CONTEXT_PATH         	      \n");
			query.append("  	 , A.ROUTING	        AS ROUTING  	       	          \n");
			query.append("  	 , B.CONTENT			AS CONTENT 			  	          \n");
			query.append("  FROM IL_ADAPTER A,   							  	          \n");
			query.append("       IL_RESOURCE B   							   			  \n");
			query.append("  WHERE 1=1   											      \n");
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
				//String ADAPTER_ID  	 = rs.getString(indx++);
				String BIZSYSTEM_ID  = rs.getString(indx++);
				//String SYS_ID  		 = rs.getString(indx++);
				String ID			 = rs.getString(indx++);
				String DIRECTION 	 = rs.getString(indx++);
				//String RUNNING_STATE = rs.getString(indx++);
				//String BIZTX_ID 	 = rs.getString(indx++);
				//String CONTEXT_PATH  = rs.getString(indx++);
				//String ROUTING 		 = rs.getString(indx++);
				String HEXA_Content  = rs.getString(indx++);
				
				byte[] b = DatatypeConverter.parseHexBinary(HEXA_Content);
				//String content = new String(b, "UTF-8");
				
				//find maxConnection Count
				Document doc = xu.makeDocument(b);
				
				if("TCP".equals(PROTOCOL))
				{
					String connectType	= elementGetValue(xu.getElement(doc, epTcpConnectType));
					
					if("Client".equals(connectType))
					{
						String ip			= elementGetValue(xu.getElement(doc, epTcpIp));
						String port			= elementGetValue(xu.getElement(doc, epTcpPort));
						
						ip = getVariableValue(ip, BIZSYSTEM_ID, varialeLstMap);
						port= getVariableValue(port, BIZSYSTEM_ID, varialeLstMap);
						
						String isConnect = callNc(ip, port);
						
						System.out.println("===== TCP EP [ "+ID+" ] PRINT =====");
						System.out.println("BIZSYSTEM_ID   : [ "+BIZSYSTEM_ID+" ]");
						System.out.println("EP_ID          : [ "+ID+" ]");
						System.out.println("DIRECTION      : [ "+DIRECTION+" ]");
						System.out.println("CONNECT_TYPE   : [ "+connectType+" ]");
						System.out.println("IP ADDRESS     : [ "+ip+" ]");
						System.out.println("PORT           : [ "+port+" ]");
						System.out.println("IS CONNECT     : [ "+isConnect+" ]\n");
					}
					
				}
				else if("WEB_SERVICE".equals(PROTOCOL) && "OUTBOUND".equals(DIRECTION))
				{
					String ip = elementGetValue(xu.getElement(doc, epWsIp));
					String port = elementGetValue(xu.getElement(doc, epWsPort));
					
					ip 	 = getVariableValue(ip, BIZSYSTEM_ID, varialeLstMap);
					port = getVariableValue(port, BIZSYSTEM_ID, varialeLstMap);
					
					String isConnect = callNc(ip, port);
					
					System.out.println("===== WS EP [ "+ID+" ] PRINT =====");
					System.out.println("BIZSYSTEM_ID   : [ "+BIZSYSTEM_ID+" ]");
					System.out.println("EP_ID          : [ "+ID+" ]");
					System.out.println("DIRECTION      : [ "+DIRECTION+" ]");
					System.out.println("IP ADDRESS     : [ "+ip+" ]");
					System.out.println("PORT           : [ "+port+" ]");
					System.out.println("IS CONNECT     : [ "+isConnect+" ]\n");
					
				}
				else if("HTTP".equals(PROTOCOL) && "OUTBOUND".equals(DIRECTION))
				{
					String split = elementGetValue(xu.getElement(doc, epHttpSplit));
					String url   = elementGetValue(xu.getElement(doc, epHttpUrl));
					
					String ip   = "not found";
					String port = "not found";
					
					if("false".equals(split))
					{
						String[] splitArr = url.split("/");
						if(splitArr !=null && splitArr.length > 2)
						{
							String ipPort = splitArr[2].replaceAll("sys:", "sys^");
							String[] split2Arr = ipPort.split(":");
							
							if(split2Arr.length > 1)
							{
								ip = split2Arr[0].replaceAll("sys\\^", "sys:");
								port = split2Arr[1].replaceAll("sys\\^", "sys:");
							}
							else
							{
								ip = split2Arr[0].replaceAll("sys\\^", "sys:");
								port = "80";
							}
						}
					}
					else
					{
						ip   = elementGetValue(xu.getElement(doc, epHttpIp));
						port = elementGetValue(xu.getElement(doc, epHttpPort));
					}
					
					ip   = getVariableValue(ip, BIZSYSTEM_ID, varialeLstMap);
					port = getVariableValue(port, BIZSYSTEM_ID, varialeLstMap);
					
					String isConnect = callNc(ip, port);
					
					System.out.println("===== HTTP EP [ "+ID+" ] PRINT =====");
					System.out.println("BIZSYSTEM_ID   : [ "+BIZSYSTEM_ID+" ]");
					System.out.println("EP_ID          : [ "+ID+" ]");
					System.out.println("DIRECTION      : [ "+DIRECTION+" ]");
					System.out.println("SPLIT          : [ "+split+" ]");
					System.out.println("URL            : [ "+url+" ]");
					System.out.println("IP ADDRESS     : [ "+ip+" ]");
					System.out.println("PORT           : [ "+port+" ]");
					System.out.println("IS CONNECT     : [ "+isConnect+" ]\n");
					
					
				}
				else if("FTP".equals(PROTOCOL) && "OUTBOUND".equals(DIRECTION))
				{
					String ip 	= elementGetValue(xu.getElement(doc, epFtpIp));
					String port = elementGetValue(xu.getElement(doc, epFtpPort));
					
					ip 		= getVariableValue(ip, BIZSYSTEM_ID, varialeLstMap);
					port    = getVariableValue(port, BIZSYSTEM_ID, varialeLstMap);
					
					String isConnect = callNc(ip, port);
					
					System.out.println("===== FTP EP [ "+ID+" ] PRINT =====");
					System.out.println("BIZSYSTEM_ID   : [ "+BIZSYSTEM_ID+" ]");
					System.out.println("EP_ID          : [ "+ID+" ]");
					System.out.println("DIRECTION      : [ "+DIRECTION+" ]");
					System.out.println("IP ADDRESS     : [ "+ip+" ]");
					System.out.println("PORT           : [ "+port+" ]");
					System.out.println("IS CONNECT     : [ "+isConnect+" ]\n");
				}
			}
			
			
			System.out.println("************ End EP Connection Info ************\n\n");
			
			
			System.out.println("************ Start DBCP Info ************");
			String dbcpQuery = "SELECT EXPORT_NAME, IP_ADDR, PORT FROM INFL_DBCP_INFO ORDER BY EXPORT_NAME ASC, IP_ADDR ASC, PORT ASC";
			stmt3 = con.createStatement();
			rs3 = stmt3.executeQuery(dbcpQuery);
			
			while(rs3.next())
			{
				String exportName = rs3.getString(1);
				String ip         = rs3.getString(2);
				String port       = rs3.getString(3);
				
				String isConnect = callNc(ip, port);
				
				System.out.println("===== DBCP [ "+exportName+" ] PRINT =====");
				System.out.println("IP ADDRESS     : [ "+ip+" ]");
				System.out.println("PORT           : [ "+port+" ]");
				System.out.println("IS CONNECT     : [ "+isConnect+" ]\n");
			}
			
			System.out.println("************ End DBCP Info ************");
			
			
			if(rs!=null)  { rs.close();   rs = null;  }
			if(stmt!=null){ stmt.close(); stmt = null;}
			if(rs2!=null)  { rs2.close();   rs2 = null;  }
			if(stmt2!=null){ stmt2.close(); stmt2 = null;}
			if(rs3!=null)  { rs3.close();   rs3 = null;  }
			if(stmt3!=null){ stmt3.close(); stmt3 = null;}
			if(con!=null) { con.close();  con = null; }
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(stmt!=null){ try {	stmt.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt = null;}
			if(rs2!=null)  { try {	rs2.close();	} catch (Exception e1) {	e1.printStackTrace();} rs2 = null;}
			if(stmt2!=null){ try {	stmt2.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt2 = null;}
			if(rs3!=null)  { try {	rs3.close();	} catch (Exception e1) {	e1.printStackTrace();} rs3 = null;}
			if(stmt3!=null){ try {	stmt3.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt3 = null;}
			if(con!=null) { try {	con.rollback(); con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
		}
		finally
		{
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(stmt!=null){ try {	stmt.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt = null;}
			if(rs2!=null)  { try {	rs2.close();	} catch (Exception e1) {	e1.printStackTrace();} rs2 = null;}
			if(stmt2!=null){ try {	stmt2.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt2 = null;}
			if(rs3!=null)  { try {	rs3.close();	} catch (Exception e1) {	e1.printStackTrace();} rs3 = null;}
			if(stmt3!=null){ try {	stmt3.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt3 = null;}
			if(con!=null){ try { con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
		}
	}
	
	public static String getVariableValue(String variable, String BIZSYSTEM_ID, Map<String, Document> varialeLstMap) throws Exception
	{
		boolean isFind = true;
		
		if(variable.startsWith("${sys:"))
		{
			isFind = false;
			
			Document doc = varialeLstMap.get(BIZSYSTEM_ID);
			
			String isEncrypt = elementGetValue(xu.getElement(doc, bizSysVar_IsEnc));
			
			Element root = doc.getRootElement();
			List<Element> elementLst = root.getChildren();
			
			for(Element e : elementLst)
			{
				if("entry".equals(e.getName()))
				{
					List<Element> entry = e.getChildren();
					Element key = entry.get(0);
					
					String keyVal = key.getValue();
					if(keyVal.equals(variable))
					{
						Element value = entry.get(1);					
						variable = value.getValue();
						
						if("true".equals(isEncrypt))
						{
							variable = KkyCrypto.decrypt(value.getValue());
						}
						
						isFind = true;
						break;
					}
				}
			}
		}
		
		if(isFind)
		{
			return variable;
		}
		else
		{
			return "not found ["+variable+"]";
		}
	}
	
	private static String callNc(String ip, String port)
    {
		String returnStr = "";
		if(ip.startsWith("not found") || port.startsWith("not found"))
		{
			returnStr = "Connect Failed";
		}
		else
		{
			StringBuffer sb = new StringBuffer();
	        
	        List<String> cmd1 = new ArrayList<String>();
	        cmd1.add("/bin/sh");
	        cmd1.add("-c");
	        
	        String cmdStr = "nc -z "+ip+" "+port;
	        
	        cmd1.add(cmdStr);
	       
	        ProcessBuilder pb = null;
	        Process p = null;
	       
	        try
	        {
	             pb = new ProcessBuilder();
	             pb.redirectErrorStream(true);
	            
	             pb.directory(new File("/app"));
	             pb.command(cmd1);
	            
	             p = pb.start();
	            
	             InputStreamReader wisr = new InputStreamReader(p.getInputStream());
	             BufferedReader wibr = new BufferedReader(wisr);
	         
	             String lineRead;
	             while((lineRead = wibr.readLine()) != null)
	             {
	                sb.append(lineRead);
	             }
	          
	             try
	             {
	                  p.waitFor();
	             }
	             catch(InterruptedException e)
	             {
	                  e.printStackTrace();
	             }
	             
	             p.destroy();
	        }
	        catch (IOException e)
	        {
	             e.printStackTrace();
	        }
	        
	        returnStr = sb.toString().trim();
		}
		
		if("".equals(returnStr))
		{
			returnStr = "Connection Failed";
		}
		
		return returnStr;
    }
}
