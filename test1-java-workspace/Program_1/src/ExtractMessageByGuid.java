import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nbss.commonlib.decrypt.MessageDecryptForBMON;

import com.tmax.tibero.jdbc.ext.TbDataSource;

/*
 * ********************소스 수정 이력****************************************
 * DATE				Modifier			Description
 * *********************************************************************
 * 2017.07.03		전원호(82023121)		최초작성
 * 2017.11.20		전원호(82023121)		Message 복호화 로직 추가
 * *********************************************************************
 */

public class ExtractMessageByGuid {

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
		
		String messagetype = args[0];
		String searchText = args[1];
				
//		String messagetype = "7";
//		String searchText = "herasoo";
		

		if (nullChg(messagetype) == "") {
			System.out.println("######## param1(messagetype) info is null #######");
			System.exit(-1);
		}
		if (nullChg(searchText) == "") {
			System.out.println("######## param2(searchText) info is null #######");
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
		
		tds.setUser("mon_infinilink");
		
		if ("GWI".equals(env) || "GWE".equals(env)) {
			tds.setPassword("Prd$inf3");
		} else {
			tds.setPassword("infinilink");
		}
		
		Connection conn = tds.getConnection();
		
		// STEP1. 전체 건수를 구한다.
		StringBuffer sb1 = new StringBuffer();
		sb1.append("select count(guid) cnt from  infinilink.infl_extract_message_by_guid ");
		
		String sql1 = sb1.toString();
		
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);

		ResultSet rs1 = pstmt1.executeQuery();
		
		int count = 0;
		
		while (rs1.next()) {
			String cnt = rs1.getString("cnt");
			count = Integer.parseInt(cnt);
//			System.out.println("loop");
		}
		
		rs1.close();
		pstmt1.close();
		//conn.close();
		
		if (count > 200000) {
			System.out.println("[WARNING] guid count: " + count);
			System.out.println("[WARNING] infl_extract_message_by_guid.guid count is over 150,000. Try again with less count, or ask the administrator to change the limit!!!");
		} else {
			System.out.println("[INFO] guid count: " + count);
			System.out.println("=== param1(messagetype): " + messagetype + "(" + messagetypeName + ") ===");
			System.out.println("=== param2(searchText): " + searchText + " ===");
			System.out.println("=== result format ===");
			System.out.println("<number>");
			System.out.println("guid,global_no,state,[IN_REQ/OUT_REQ/OUT_RES/IN_RES]message\\n");
			System.out.println("=== start ===");
			
			// STEP2-1. GUID 기준 데이터를 추출한다. tx 및 trace 로그와 조인하기 위해 커스텀 로그의 reg_dt 함께 추출한다.
			StringBuffer sb2 = new StringBuffer();
			sb2.append("select a.guid, b.reg_dt ");
			sb2.append("from  infinilink.infl_extract_message_by_guid		a ");
			sb2.append("inner join infinilink.infl_custom_log_txn			b on a.guid = b.guid ");
			
			String sql2 = sb2.toString();
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			
			// STEP2-2. 쿼리 실행
			ResultSet rs2 = pstmt2.executeQuery();
			
			// STEP3-1. STEP2 결과 reg_dt를 가지고 -1, 0, 1 날짜(date) 정보 활용
			Calendar minus1Day = Calendar.getInstance();
			Calendar toDay = Calendar.getInstance();
			Calendar plus1Day = Calendar.getInstance();
			
			// STEP3-2. 최종 쿼리 작성, -1, 0, 1 날짜의 tx, trace 로그와 union all한다.
			StringBuffer sb3 = new StringBuffer();
			sb3.append("select guid, global_no, state, point, message, encrypt ");
			sb3.append("from ( ");
			
			sb3.append("select a.guid, c.global_no, a.state, b.point, b.message, b.sequence, b.encrypt ");
			sb3.append("from infinilink.il_transaction_log_%1$s a ");
			sb3.append("inner join infinilink.il_trace_log_%1$s b on a.guid = b.guid ");
			sb3.append("inner join infinilink.infl_custom_log_txn c on a.guid = c.guid ");
			sb3.append("where 1 = 1 ");
			sb3.append("and a.guid = ? ");
			
			if ( "1".equals(messagetype) ) {
				sb3.append("and b.point = 'AfterCalled' ");
			} else if ( "2".equals(messagetype) ) {
				sb3.append("and b.point = 'BeforeCall' ");
			} else if ( "3".equals(messagetype) ) {
				sb3.append("and b.point = 'AfterReplied' ");
			} else if ( "4".equals(messagetype) ){
				sb3.append("and b.point = 'BeforeReply' ");
			} else if ( "5".equals(messagetype) ){
				sb3.append("and b.point in ('AfterCalled','BeforeReply') ");
			} else if ( "6".equals(messagetype) ){
				sb3.append("and b.point in ('BeforeCall','AfterReplied') ");
			} else if ( "7".equals(messagetype) ){
				sb3.append("and b.point in ('AfterCalled','BeforeCall','AfterReplied','BeforeReply') ");
			} else {
				sb3.append("and b.point = 'AfterCalled' ");
			}
			
			sb3.append("and b.length > 0 ");
			
			sb3.append("union all ");
			
			sb3.append("select a.guid, c.global_no, a.state, b.point, b.message, b.sequence, b.encrypt ");
			sb3.append("from infinilink.il_transaction_log_%2$s a ");
			sb3.append("inner join infinilink.il_trace_log_%2$s b on a.guid = b.guid ");
			sb3.append("inner join infinilink.infl_custom_log_txn c on a.guid = c.guid ");
			sb3.append("where 1 = 1 ");
			sb3.append("and a.guid = ? ");
			
			if ( "1".equals(messagetype) ) {
				sb3.append("and b.point = 'AfterCalled' ");
			} else if ( "2".equals(messagetype) ) {
				sb3.append("and b.point = 'BeforeCall' ");
			} else if ( "3".equals(messagetype) ) {
				sb3.append("and b.point = 'AfterReplied' ");
			} else if ( "4".equals(messagetype) ){
				sb3.append("and b.point = 'BeforeReply' ");
			} else if ( "5".equals(messagetype) ){
				sb3.append("and b.point in ('AfterCalled','BeforeReply') ");
			} else if ( "6".equals(messagetype) ){
				sb3.append("and b.point in ('BeforeCall','AfterReplied') ");
			} else if ( "7".equals(messagetype) ){
				sb3.append("and b.point in ('AfterCalled','BeforeCall','AfterReplied','BeforeReply') ");
			} else {
				sb3.append("and b.point = 'AfterCalled' ");
			}
			
			sb3.append("and b.length > 0 ");
			
			sb3.append("union all ");
			
			sb3.append("select a.guid, c.global_no, a.state, b.point, b.message, b.sequence, b.encrypt ");
			sb3.append("from infinilink.il_transaction_log_%3$s a ");
			sb3.append("inner join infinilink.il_trace_log_%3$s b on a.guid = b.guid ");
			sb3.append("inner join infinilink.infl_custom_log_txn c on a.guid = c.guid ");
			sb3.append("where 1 = 1 ");
			sb3.append("and a.guid = ? ");
			
			if ( "1".equals(messagetype) ) {
				sb3.append("and b.point = 'AfterCalled' ");
			} else if ( "2".equals(messagetype) ) {
				sb3.append("and b.point = 'BeforeCall' ");
			} else if ( "3".equals(messagetype) ) {
				sb3.append("and b.point = 'AfterReplied' ");
			} else if ( "4".equals(messagetype) ){
				sb3.append("and b.point = 'BeforeReply' ");
			} else if ( "5".equals(messagetype) ){
				sb3.append("and b.point in ('AfterCalled','BeforeReply') ");
			} else if ( "6".equals(messagetype) ){
				sb3.append("and b.point in ('BeforeCall','AfterReplied') ");
			} else if ( "7".equals(messagetype) ){
				sb3.append("and b.point in ('AfterCalled','BeforeCall','AfterReplied','BeforeReply') ");
			} else {
				sb3.append("and b.point = 'AfterCalled' ");
			}
			
			sb3.append("and b.length > 0 ");
			
			sb3.append(") ");
			sb3.append("order by sequence ");
			
			String sql3 = sb3.toString();
			
			int rows = 0;
			
			// STEP3-3. STEP2 쿼리 결과를 가지고 STEP3 쿼리 실행
			while (rs2.next()) {
				
				rows++;
				
				String _guid = rs2.getString("guid");
				Timestamp reg_dt = rs2.getTimestamp("reg_dt");

				minus1Day.setTime(new Date(reg_dt.getTime()));
				toDay.setTime(new Date(reg_dt.getTime()));
				plus1Day.setTime(new Date(reg_dt.getTime()));
				
				minus1Day.add(Calendar.DATE, -1);
				plus1Day.add(Calendar.DATE, 1);
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd");
				
				String dd_minus1Day= sdf.format(minus1Day.getTime());
				String dd_toDay= sdf.format(toDay.getTime());
				String dd_plus1Day= sdf.format(plus1Day.getTime());
				
//				System.out.println(dd_minus1Day);
//				System.out.println(dd_toDay);
//				System.out.println(dd_plus1Day);
				
				sql3 = String.format(sql3, dd_minus1Day, dd_toDay, dd_plus1Day);
				
				PreparedStatement pstmt3 = conn.prepareStatement(sql3);
				
				pstmt3.setString(1, _guid);
				pstmt3.setString(2, _guid);
				pstmt3.setString(3, _guid);
				
				// STEP 3-4. STEP3 GUID 단위 쿼리 실행
				ResultSet rs3 = pstmt3.executeQuery();
				
				String preGuid = "";
				
				// STEP3-5. STEP3 쿼리 결과
				while (rs3.next()) {
					
					String guid = "";
					String global_no = "";
					String state = "";
					String point = "";
					String message = "";
					String encrypt = "";
					
					guid = rs3.getString("guid");
					global_no = rs3.getString("global_no");
					state = rs3.getString("state");
		
					if ( !preGuid.equals(guid)) {

						System.out.println("");
						System.out.println("<" + rows + ">");
						System.out.print(guid + "," + global_no + "," + state);
						
						preGuid = guid;
					}
								
					point = rs3.getString("point");
					Blob blob = rs3.getBlob("message");
					encrypt = rs3.getString("encrypt");
					
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
					//message = message.replaceAll(System.getProperty("line.separator"), "");
					message = message.replaceAll("\r", "");
					message = message.replaceAll("\n", "");
					
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
				
				rs3.close();
				pstmt3.close();
				System.out.println("");
				
			}
			
			rs2.close();
			pstmt2.close();
			
		}
		
		System.out.println("");
		System.out.println("=== finish ===");
			
	}
	
	
	public static String nullChg(String str) {
		return (str == null || str.isEmpty()) ? "" : str;
	}

	
}
