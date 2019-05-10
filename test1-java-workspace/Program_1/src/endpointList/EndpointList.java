package endpointList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.tmax.tibero.jdbc.ext.TbDataSource;

/*
 * ********************소스 수정 이력****************************************
 * DATE				Modifier			Description
 * *********************************************************************
 * 2015.12.14		전원호(82023121)		최초작성
 * *********************************************************************
 */

public class EndpointList {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		String dbEnv = args[0].toUpperCase(); // DB Env
		
		if (nullChg(dbEnv) == "") {
			System.out.println("######## param1(DB SID) info is null #######");
			System.exit(-1);
		}
	
		TbDataSource tds = new TbDataSource();
		
		// DB 결과를 저장하기 위한 공간
		ArrayList<HashMap> arrayList = new ArrayList<HashMap>();
		
				
		EndpointList endpointList = new EndpointList();
		
		Properties prop1 = new Properties();
		
		// DB SID별 접속 정보 존재
		prop1 = endpointList.readProperties("./properties/dbinfo.properties");
				
		// 접속 DB정보 입력
//		String db_ip = "10.217.136.60";
//		String db_port = "8629";
//		String sid = "SBX1GWE";
		
		String dbInfo = prop1.getProperty(dbEnv);
		String[] arr_dbInfo = dbInfo.split("\\,", 5);
		
		String db_ip = arr_dbInfo[0];
		String db_port = arr_dbInfo[1];
		String sid = arr_dbInfo[2];
		
		tds.setURL("jdbc:tibero:thin:@" + db_ip + ":" + db_port + ":" + sid);
		
		// 접속 계정정보를 입력한다.
//		tds.setUser("infinilink");
//		tds.setPassword("infinilink");
		tds.setUser(arr_dbInfo[3]);
		tds.setPassword(arr_dbInfo[4]);
		
		Connection conn = tds.getConnection();
		Statement stmt = conn.createStatement();

		StringBuffer sb = new StringBuffer();
		sb.append("select adt.bizsystem_id, adt.protocol, adt.adapter_id, res.id, adt.running_state, adt.direction, adt.context_path, adt.biztx_id, adt.registered_date, adt.modified_date, res.content ");
		sb.append("from il_adapter as adt ");
		sb.append("inner join il_resource as res on adt.sys_id = res.sys_id and adt.bizsystem_id = res.bizsystem_id ");
		sb.append("where 1=1 ");
		sb.append("and adt.resource_type = 'ENDPOINT' ");
		sb.append("and adt.protocol not in ('SCHEDULER','LOG') ");
		sb.append("order by adt.bizsystem_id, adt.protocol, adt.direction, res.id ");
		
		//System.out.println("Query: " + sb.toString());
				
		// Query 결과를 담는다.
		ResultSet rs = stmt.executeQuery(sb.toString());			
		
		int rows = 1;
		while (rs.next()) {
			
			// DB 조회 결과 중 1row를 저장하기 위한 공간
			HashMap<String, String> map = new HashMap<String, String>();
			
			String bizsystem_id = "";
			String protocol = "";
			String adapter_id = "";
			String id = "";
			String running_state = "";
			String direction = "";
			String context_path = "";
			String biztx_id = "";
			String registered_date = "";
			String modified_date = "";
			String bootState = "";
			String encoding = "";
			String ip = "";
			String connectType = "";
			String handleConnection = "";
			String keepAlive = "";
			String port = "";
			String minConnection = "";
			String maxConnection = "";
			String url = "";
			
			bizsystem_id = rs.getString("bizsystem_id");
			protocol = rs.getString("protocol");
			adapter_id = rs.getString("adapter_id");
			id = rs.getString("id");
			running_state = rs.getString("running_state");
			direction = rs.getString("direction");
			context_path = rs.getString("context_path");
			biztx_id = rs.getString("biztx_id");
			registered_date = rs.getString("registered_date");
			modified_date = rs.getString("modified_date");	
			
			//System.out.println("rows: " + rows + ", " + "id: " + rs.getString("id"));
			//System.out.println("rows: " + rows + ", " + "content: " + rs.getBlob("content"));
			
				
			Blob blob = rs.getBlob("content");
			
			BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
			int size = (int)blob.length();
			byte[] buf = new byte[size];
			
			try {
				in.read(buf, 0, size);
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("######## BLOB Field Read Error #######");
				e.printStackTrace();
			} 
			
			String contentXML = new String(buf);
			
			//System.out.println("rows: " + rows + ", " + "content: " + contentXML);

			JSONObject jSONObject = (JSONObject) new XMLSerializer().read(contentXML);
			
			//System.out.println("rows: " + rows + ", " + "content: " + jSONObject);
			
			bootState = jSONObject.getString("ns0:bootState");
					
			if (jSONObject.containsKey("ns0:encoding")) {
				encoding = jSONObject.getString("ns0:encoding");
			}	
			
			if (jSONObject.containsKey("ns0:tcpEndpointInfo")) {
				
				if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").containsKey("ns0:connectType")) {
					
					connectType = jSONObject.getJSONObject("ns0:tcpEndpointInfo").getString("ns0:connectType");
				}
				if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").containsKey("ns0:connectOption")) {
					
					if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").getJSONObject("ns0:connectOption").containsKey("ns0:handleConnection")) {
						
						//System.out.println(jSONObject.getJSONObject("ns0:tcpEndpointInfo").getJSONObject("ns0:connectOption"));
						
						handleConnection = jSONObject.getJSONObject("ns0:tcpEndpointInfo").getJSONObject("ns0:connectOption").getString("ns0:handleConnection");
					}
					if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").getJSONObject("ns0:connectOption").containsKey("ns0:keepAlive")) {
						
						keepAlive = jSONObject.getJSONObject("ns0:tcpEndpointInfo").getJSONObject("ns0:connectOption").getString("ns0:keepAlive");
					}
				}		
				if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").containsKey("ns0:address")) {
					
					ip = jSONObject.getJSONObject("ns0:tcpEndpointInfo").getString("ns0:address");
				}
				if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").containsKey("ns0:port")) {
					
					port = jSONObject.getJSONObject("ns0:tcpEndpointInfo").getString("ns0:port");
				}
				if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").containsKey("ns0:minConnection")) {
					
					minConnection = jSONObject.getJSONObject("ns0:tcpEndpointInfo").getString("ns0:minConnection");
				}
				if (jSONObject.getJSONObject("ns0:tcpEndpointInfo").containsKey("ns0:maxConnection")) {
					
					maxConnection = jSONObject.getJSONObject("ns0:tcpEndpointInfo").getString("ns0:maxConnection");
				}
				
			}
			
			if (jSONObject.containsKey("ns0:ftpEndpointInfo")) {
				
				if (jSONObject.getJSONObject("ns0:ftpEndpointInfo").containsKey("ns0:address")) {
					
					ip = jSONObject.getJSONObject("ns0:ftpEndpointInfo").getString("ns0:address");
				}
				if (jSONObject.getJSONObject("ns0:ftpEndpointInfo").containsKey("ns0:port")) {
					
					port = jSONObject.getJSONObject("ns0:ftpEndpointInfo").getString("ns0:port");
				}
				
			}
			
			
			
			if (jSONObject.containsKey("ns0:httpEndpointInfo")) {
				
				if (jSONObject.getJSONObject("ns0:httpEndpointInfo").containsKey("ns0:url")) {
					
					url = jSONObject.getJSONObject("ns0:httpEndpointInfo").getString("ns0:url");
				}
				
			}
			
			
			
			if (jSONObject.containsKey("ns0:webServiceEndpointInfo")) {
				
				if (jSONObject.getJSONObject("ns0:webServiceEndpointInfo").containsKey("ns0:hostVariable")) {
					
					ip = jSONObject.getJSONObject("ns0:webServiceEndpointInfo").getString("ns0:hostVariable");
				}
				
				if (jSONObject.getJSONObject("ns0:webServiceEndpointInfo").containsKey("ns0:portVariable")) {
					
					port = jSONObject.getJSONObject("ns0:webServiceEndpointInfo").getString("ns0:portVariable");
				}
								
				if (jSONObject.getJSONObject("ns0:webServiceEndpointInfo").containsKey("ns0:url")) {
					
					url = jSONObject.getJSONObject("ns0:webServiceEndpointInfo").getString("ns0:url");
				}
			}
			
						
//			System.out.println("rows: " + rows + ", " + "bootState: " + bootState);
//			System.out.println("rows: " + rows + ", " + "ip: " + ip);
//			System.out.println("rows: " + rows + ", " + "port: " + port);
			
			// 1row 필드값들을 저장한다.
			map.put("bizsystem_id", nullChg(bizsystem_id));
			map.put("protocol", nullChg(protocol));
			map.put("adapter_id", nullChg(adapter_id));
			map.put("id", nullChg(id));
			map.put("running_state", nullChg(running_state));
			map.put("direction", nullChg(direction));
			map.put("context_path", nullChg(context_path));
			map.put("biztx_id", nullChg(biztx_id));
			map.put("registered_date", nullChg(registered_date));
			map.put("modified_date", nullChg(modified_date));
			map.put("bootState", nullChg(bootState));
			map.put("encoding", nullChg(encoding));
			map.put("connectType", nullChg(connectType));
			map.put("handleConnection", nullChg(connectType).isEmpty() ? "" : "true".equals(nullChg(handleConnection)) ? "Yes" : "No");
			map.put("keepAlive", nullChg(connectType).isEmpty() ? "" : "true".equals(nullChg(keepAlive)) ? "Yes" : "No");
			map.put("ip", nullChg(ip));
			map.put("port", nullChg(port));
			map.put("minConnection", nullChg(minConnection));
			map.put("maxConnection", nullChg(maxConnection));
			map.put("url", nullChg(url));
			
			// 1row을 최종 결과 SET에 추가한다.
			arrayList.add(map);
			
			map = null;
							
			rows++;
		}
		
		rs.close();
		stmt.close();
		conn.close();

//		int arrList_size = arrayList.size();
		
//		System.out.println("table rows: " + arrList_size);
//		
//		for (int i=0; i<arrList_size; i++) {
//			
//			System.out.println("rownum: " + (i+1) + ", id: " + arrayList.get(i).get("id") + ", port: " + arrayList.get(i).get("port"));
//		}
		
		// arrayList ip, port 필드에 존재하는 시스템 변수 ${sys:} 값을 프로퍼티 파일에 존재하는 내용으로 변환 여부
		// 시스템 변수 변환 여부
//		boolean boolSystemVariableChgYN = true;
		boolean boolSystemVariableChgYN = false;
		
		if (boolSystemVariableChgYN) {
			
			Properties prop2 = new Properties();
			
			// 시스템변수 프로퍼티 파일 위치를 입력 (모든 업무시스템의 시스템변수 내용이 존재해야 한다)
//			prop2 = endpointList.readProperties("F:\\test\\endpointList\\property.properties");
			prop2 = endpointList.readProperties("./properties/property.properties");
			
			for (HashMap<String,String> hashMap : arrayList) {
				
				String chgIp = hashMap.get("ip");
				String chgPort = hashMap.get("port");
				String chgUrl = hashMap.get("url");
				
				// ip필드 변경
				if (chgIp.startsWith("${sys:")) {
					chgIp = prop2.getProperty(chgIp, chgIp);
					hashMap.remove("ip");
					hashMap.put("ip", chgIp);
				}
				
				// port필드 변경
				if (chgPort.startsWith("${sys:")) {
					chgPort = prop2.getProperty(chgPort, chgPort);
					hashMap.remove("port");
					hashMap.put("port", chgPort);
				}
				
				// url필드 변경(ip, port 2개의 정보가 존재)
				if (chgUrl.contains("${sys:")) {
					
					int url_11 = chgUrl.indexOf("${sys:");
					int url_12 = chgUrl.indexOf("}");
					
					int url_21 = chgUrl.lastIndexOf("${sys:");
					int url_22 = chgUrl.lastIndexOf("}");
									
					String url_1= chgUrl.substring(url_11,url_12+1);
					String url_2= chgUrl.substring(url_21,url_22+1);
					
					String url_3 = prop2.getProperty(url_1, url_1);
					String url_4 = prop2.getProperty(url_2, url_2);
					
					System.out.println(url_1 + " => " + url_3);
					System.out.println(url_2 + " => " + url_4);
					
					chgUrl = chgUrl.replace(url_1, url_3).replace(url_2, url_4);
					
					hashMap.remove("url");
					hashMap.put("url", chgUrl);
				}
				
			}
		}
		
		
		// 파일저장 위치와 파일명을 지정한다.
		File file = new File("./out","endpointList_" + dbEnv + "_" + getDate() + ".log");
//		String filename = file.getAbsolutePath();
//		System.out.println("filename: " + filename);
		
		try {
			// false는 이어쓰기가 아닌 덮어쓰기
			FileWriter fw =  new FileWriter(file, false);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw), true);
			
			// 필드명을 먼저 쓰기
			pw.println("bizsystem_id,protocol,adapter_id,id,running_state,direction,context_path,biztx_id,registered_date,modified_date,bootState,encoding,connectType,handleConnection,keepAlive,ip,port,minConnection,maxConnection,url");
			
			for (HashMap<String,String> hashMap : arrayList) {
				
				StringBuffer txt = new StringBuffer();
				txt.append(hashMap.get("bizsystem_id"));
				txt.append(",");
				txt.append(hashMap.get("protocol"));
				txt.append(",");
				txt.append(hashMap.get("adapter_id"));
				txt.append(",");
				txt.append(hashMap.get("id"));
				txt.append(",");
				txt.append(hashMap.get("running_state"));
				txt.append(",");
				txt.append(hashMap.get("direction"));
				txt.append(",");
				txt.append(hashMap.get("context_path"));
				txt.append(",");
				txt.append(hashMap.get("biztx_id"));
				txt.append(",");
				txt.append(hashMap.get("registered_date"));
				txt.append(",");
				txt.append(hashMap.get("modified_date"));
				txt.append(",");
				txt.append(hashMap.get("bootState"));
				txt.append(",");
				txt.append(hashMap.get("encoding"));
				txt.append(",");
				txt.append(hashMap.get("connectType"));
				txt.append(",");
				txt.append(hashMap.get("handleConnection"));
				txt.append(",");
				txt.append(hashMap.get("keepAlive"));
				txt.append(",");
				txt.append(hashMap.get("ip"));
				txt.append(",");
				txt.append(hashMap.get("port"));
				txt.append(",");
				txt.append(hashMap.get("minConnection"));
				txt.append(",");
				txt.append(hashMap.get("maxConnection"));
				txt.append(",");
				txt.append(hashMap.get("url"));
				
				// 1row씩 파일쓰기
				pw.println(txt.toString());
			
			}
			
			pw.close();
			fw.close();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("######## File write Error #######");
			e1.printStackTrace();
		}
		
		
		System.out.println("######## The file was created #######"); 
			
	}
	
	
	public static String nullChg(String str) {
		return (str == null || str.isEmpty()) ? "" : str;
	}
	
	public Properties readProperties(String filePath) {
		
		Properties props = new Properties();
		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			props.load(br);
		   
			br.close(); br=null;
			isr.close(); isr=null;
			fis.close(); fis=null;
								
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("######## System Variables Properties FileNotFoundException #######");
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("######## System Variables Properties Exception #######");
			e.printStackTrace();
		}
		finally {
			if(br!=null)  { try {br.close(); br = null;} catch (IOException e) { e.printStackTrace();} }
			if(isr!=null) { try {isr.close(); isr = null;} catch (IOException e) { e.printStackTrace();}  }
			if(fis!=null) { try {fis.close(); fis = null;} catch (IOException e) { e.printStackTrace();}  }
		}
		
		return props;
	}
	
	public static String getDate() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}

	
}

