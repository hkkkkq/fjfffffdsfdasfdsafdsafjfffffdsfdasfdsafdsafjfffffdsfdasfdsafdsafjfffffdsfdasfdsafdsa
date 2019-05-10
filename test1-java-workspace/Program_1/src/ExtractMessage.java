import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tmax.tibero.jdbc.ext.TbDataSource;
import nbss.commonlib.decrypt.MessageDecryptForBMON;

/*
 * ********************소스 수정 이력****************************************
 * DATE				Modifier			Description
 * *********************************************************************
 * 2015.12.14		전원호(82023121)		최초작성
 * 2017.11.20		전원호(82023121)		Message 복호화 로직 추가
 * *********************************************************************
 */

public class ExtractMessage {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		// env variables
		String env = "gwi".toUpperCase();
		
		String db_ip = "";
		String db_port = "";
		String sid = "";
		
		if ("GWI".equals(env)) {
			db_ip = "10.219.4.78";
			db_port = "8634";
			sid = "GWIDB";
		} else if ("GWE".equals(env)) {
			db_ip = "10.219.4.75";
			db_port = "8640";
			sid = "GWEDB";
		} else if ("SITGWI".equals(env)) {
			db_ip = "10.217.159.204";
			db_port = "8634";
			sid = "SIT2GWI";
		} else if ("SITGWE".equals(env)) {
			db_ip = "10.217.159.55";
			db_port = "8640";
			sid = "SITGWE";
		} else {
			db_ip = "10.217.136.60";
			db_port = "8629";
			sid = "SBX1GWE";
		}
	
		String date = args[0];
		String type = args[1].toUpperCase();
		String biztxId = args[2];
		String bizsystemId = args[3].toLowerCase();
		String messagetype = args[4];
		String searchText = args[5];
				

//		String date = "20170607";
//		String type = "ALL";
//		String biztxId = "llll";
//		String bizsystemId = "io_grp1";
//		String messagetype = "7";
//		String searchText = "";
		
		if (nullChg(date) == "" && date.length() != 8) {
			System.out.println("######## param1(date) info is null #######");
			System.exit(-1);
		}
		if (nullChg(type) == "") {
			System.out.println("######## param2(type) info is null #######");
			System.exit(-1);
		}
		if (nullChg(biztxId) == "") {
			System.out.println("######## param3(biztxId) info is null #######");
			System.exit(-1);
		}
		if (nullChg(bizsystemId) == "") {
			System.out.println("######## param4(bizsystemId) info is null #######");
			System.exit(-1);
		}
		if (nullChg(messagetype) == "") {
			System.out.println("######## param5(messagetype) info is null #######");
			System.exit(-1);
		}
		if (nullChg(searchText) == "") {
			System.out.println("######## param6(searchText) info is null #######");
			System.exit(-1);
		}
		
		String messagetypeName = "";
		
		if ( "1".equals(messagetype) ) {
			messagetypeName = "IN_REQ";
		} else if ( "2".equals(messagetype) ) {
			messagetypeName = "OUT_REQ";
		} else if ( "3".equals(messagetype) ) {
			messagetypeName = "OUT_RES";
		} else if ( "4".equals(messagetype) ) {
			messagetypeName = "IN_RES";
		} else if ( "5".equals(messagetype) ) {
			messagetypeName = "IN_REQ+RES";
		} else if ( "6".equals(messagetype) ) {
			messagetypeName = "OUT_REQ+RES";
		} else if ( "7".equals(messagetype) ) {
			messagetypeName = "IN/OUT_REQ+RES";
		} else {
			messagetypeName = "IN_REQ";
		}
		
		TbDataSource tds = new TbDataSource();
		tds.setLoginTimeout(3000);
							
		tds.setURL("jdbc:tibero:thin:@" + db_ip + ":" + db_port + ":" + sid);
		
		tds.setUser("infinilink");
		
		if ("GWI".equals(env) || "GWE".equals(env)) {
			tds.setPassword("Prd$inf1");
		} else {
			tds.setPassword("infinilink");
		}	
		
		Connection conn = tds.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("select a.guid, c.global_no, a.state, b.point, b.message, b.encrypt ");
		sb.append("from il_transaction_log_%1$s a ");
		sb.append("inner join il_trace_log_%1$s b on a.guid = b.guid ");
		sb.append("inner join infl_custom_log_txn c on a.guid = c.guid ");
		sb.append("where 1 = 1 ");
		sb.append("and a.biztxid = ? ");
				
		if ( "ALL".equals(type) ) {
			sb.append("and a.state in ('SUCCESS','ERROR') ");
		} else if ( "SUCCESS".equals(type) ) {
			sb.append("and a.state ='SUCCESS' ");
		} else if ( "ERROR".equals(type) ) {
			sb.append("and a.state ='ERROR' ");
		} else {
			sb.append("and a.state ='ERROR' ");
		}
	
//		sb.append("and a.starttime > trunc(systimestamp, 'DD') ");
		sb.append("and a.starttime > to_timestamp(?,'YYYY-MM-DD') ");
		sb.append("and a.startnodeid like ? ");
		sb.append("and a.pguid is null ");
			

		if ( "1".equals(messagetype) ) {
			sb.append("and b.point = 'AfterCalled' ");
		} else if ( "2".equals(messagetype) ) {
			sb.append("and b.point = 'BeforeCall' ");
		} else if ( "3".equals(messagetype) ) {
			sb.append("and b.point = 'AfterReplied' ");
		} else if ( "4".equals(messagetype) ){
			sb.append("and b.point = 'BeforeReply' ");
		} else if ( "5".equals(messagetype) ){
			sb.append("and b.point in ('AfterCalled','BeforeReply') ");
		} else if ( "6".equals(messagetype) ){
			sb.append("and b.point in ('BeforeCall','AfterReplied') ");
		} else if ( "7".equals(messagetype) ){
			sb.append("and b.point in ('AfterCalled','BeforeCall','AfterReplied','BeforeReply') ");
		} else {
			sb.append("and b.point = 'AfterCalled' ");
		}
		
		sb.append("and b.length > 0 ");
		sb.append("order by a.guid, c.global_no, b.sequence ");
		
		
		String sql = sb.toString();
		
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
//		String dd = sdf3.format(cal.getTime());
		
		String dd = date.substring(6);		
		String sql2 = String.format(sql, dd);
		
		PreparedStatement pstmt = conn.prepareStatement(sql2);
		
		pstmt.setString(1, biztxId);
		pstmt.setString(2, date);
		pstmt.setString(3, bizsystemId+"%");
		
		System.out.println("Query: " + sql2);
		System.out.println("=== start ===");
		System.out.println("");
		System.out.println("=== param1(date): " + date + " ===");
		System.out.println("=== param2(type): " + type + " ===");
		System.out.println("=== param3(biztxId): " + biztxId + " ===");
		System.out.println("=== param4(bizsytemID): " + bizsystemId.toUpperCase() + "(B) ===");
		System.out.println("=== param5(messagetype): " + messagetype + "(" + messagetypeName + ") ===");
		System.out.println("=== param6(searchText): " + searchText + " ===");
		System.out.println("");
		System.out.println("=== result format ===");
		System.out.println("<number>");
		System.out.println("guid,global_no,state,[IN_REQ/OUT_REQ/OUT_RES/IN_RES]message\\n");
		
		// Query 결과를 담는다.
		ResultSet rs = pstmt.executeQuery();			
		
		int rows = 0;
		
		String preGuid = "";
		
		while (rs.next()) {
		
			String guid = "";
			String global_no = "";
			String state = "";
			String point = "";
			String message = "";
			String encrypt = "";
			
			guid = rs.getString("guid");
			global_no = rs.getString("global_no");
			state = rs.getString("state");

			if ( !preGuid.equals(guid)) {
				rows++;
				System.out.println("");
				System.out.println("<" + rows + ">");
				System.out.print(guid + "," + global_no + "," + state);
				
				preGuid = guid;
			}
						
			point = rs.getString("point");
			Blob blob = rs.getBlob("message");
			encrypt = rs.getString("encrypt");
			
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
			
			//복호화 로직 추가
			if ("Y".equals(encrypt.toUpperCase())) {
				
				MessageDecryptForBMON messageDecrypt = MessageDecryptForBMON.getInstance();
				byte[] b_decrypt = messageDecrypt.decrypt_message(buf);
				message = new String(b_decrypt);
			} else {
				message = new String(buf);
			}
			
			
			//개행제거
			message = message.replaceAll(System.getProperty("line.separator"), "");
			
			String pointName = "";
			
			if ( "AfterCalled".equals(point) ) {
				pointName = "IN_REQ";
			} else if ( "BeforeCall".equals(point) ) {
				pointName = "OUT_REQ";
			} else if ( "AfterReplied".equals(point) ) {
				pointName = "OUT_RES";
			} else if ( "BeforeReply".equals(point) ) {
				pointName = "IN_RES";
			} else {
				pointName = "???";
			}
			
			if ( "all".equals(searchText) ) {
				System.out.print(",[" + pointName + "]" + message);	
			} else {
				if (message.contains(searchText)) {
					System.out.print(",[" + pointName + "]" + message);
				}
			}
			
		}
		
		System.out.println("");
		
		rs.close();
		pstmt.close();
		conn.close();
		
		System.out.println("=== finish ===");
			
	}
	
	
	public static String nullChg(String str) {
		return (str == null || str.isEmpty()) ? "" : str;
	}

	
}
