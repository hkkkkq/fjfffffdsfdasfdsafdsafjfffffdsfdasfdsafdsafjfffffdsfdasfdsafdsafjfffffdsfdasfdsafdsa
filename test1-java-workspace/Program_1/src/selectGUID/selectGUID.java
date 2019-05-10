package selectGUID;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.tmax.tibero.jdbc.ext.TbDataSource;

/*
 * ********************소스 수정 이력****************************************
 * DATE				Modifier			Description
 * *********************************************************************
 * 2017.05.27		전원호(82023121)		최초작성
 * *********************************************************************
 */

public class selectGUID {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
		
		String dbEnv = args[0].toUpperCase(); // SBX
		String gwType = args[1].toUpperCase(); // I: internal, E: external
		String inputType = args[2].toUpperCase(); // 1: globalNo, 2: guid
		String value = args[3]; // gw00iocdrm1s22017022314314946300
		
//		System.out.println(dbEnv);
//		System.out.println(gwType);
//		System.out.println(inputType);
//		System.out.println(value);
		
//		String dbEnv = "SBX"; // SBX
//		String gwType = "I"; // I: internal, E: external
//		String inputType = "1"; // 1: globalNo, 2: guid
//		String value = "gw00iocdrm1s220170223143149463000"; // gw00iocdrm1s22017022314314946300
		
		if (args.length != 4) {
			System.out.println("######## params count is wrong #######");
			System.exit(-1);
		}
		
		if (nullChg(dbEnv) == "") {
			System.out.println("######## param1(dbEnv) info is null #######");
			System.exit(-1);
		}
		
		if (nullChg(gwType) == "") {
			System.out.println("######## param2(gwType) info is null #######");
			System.exit(-1);
		}
		
		if (nullChg(inputType) == "") {
			System.out.println("######## param3(inputType) info is null #######");
			System.exit(-1);
		}
		
		if (nullChg(value) == "") {
			System.out.println("######## param4(value) info is null #######");
			System.exit(-1);
		}
	
		TbDataSource tds = new TbDataSource();
		
		String db_ip = "";
		String db_port = "";
		String sid = "";
		String id = "";
		String pw = "";
		
		if (dbEnv.equalsIgnoreCase("SBX") && gwType.equalsIgnoreCase("I")) {
//			System.out.println("######## SBX #######");
			db_ip = "10.217.136.60";
			db_port = "8629";
			sid = "SBX1GWE";
			id = "infinilink";
			pw = "infinilink";
			
		} else if (dbEnv.equalsIgnoreCase("SIT") && gwType.equalsIgnoreCase("I")) {
			db_ip = "10.217.159.53";
			db_port = "8634";
			sid = "SITGWI";
			id = "infinilink";
			pw = "infinilink";
			
		} else if (dbEnv.equalsIgnoreCase("SIT") && gwType.equalsIgnoreCase("E")) {
			db_ip = "10.217.159.207";
			db_port = "8640";
			sid = "SIT2GWE";
			id = "infinilink";
			pw = "infinilink";
			
		} else if (dbEnv.equalsIgnoreCase("SIT2") && gwType.equalsIgnoreCase("I")) {
			db_ip = "10.217.159.204";
			db_port = "8634";
			sid = "SIT2GWI";
			id = "infinilink";
			pw = "infinilink";
			
		} else if (dbEnv.equalsIgnoreCase("SIT2") && gwType.equalsIgnoreCase("E")) {
			db_ip = "10.217.159.55";
			db_port = "8640";
			sid = "SITGWE";
			id = "infinilink";
			pw = "infinilink";
			
		} else if (dbEnv.equalsIgnoreCase("PRD") && gwType.equalsIgnoreCase("I")) {
			db_ip = "10.219.4.78";
			db_port = "8634";
			sid = "GWIDB";
			id = "infinilink";
			pw = "Prd$inf1";
			
		} else if (dbEnv.equalsIgnoreCase("PRD") && gwType.equalsIgnoreCase("E")) {
			db_ip = "10.219.4.75";
			db_port = "8640";
			sid = "GWEDB";
			id = "infinilink";
			pw = "Prd$inf1";
			
		} else {
			System.out.println("######## param1(dbEnv) info and param2(gwType) info are not defined in the target list #######");
			System.exit(-1);
		}
		
		
		
		tds.setURL("jdbc:tibero:thin:@" + db_ip + ":" + db_port + ":" + sid);
		
		// 접속 계정정보를 입력한다.
//		tds.setUser("infinilink");
//		tds.setPassword("infinilink");
		tds.setUser(id);
		tds.setPassword(pw);
		
		Connection conn = tds.getConnection();
		Statement stmt = conn.createStatement();
		
		// 1st SQL => IL_TRANSACTION_LOG_* 날짜 파악
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("substr(a.reg_dt,0,instr(a.reg_dt,'.')-1) reg_dt ");
		sb.append("from infl_custom_log_txn a ");
		sb.append("where 1=1 ");
		sb.append("and rownum = 1 ");

		// 1: globalNo, 2: guid
		if (inputType.equals("1")) {
			sb.append("and a.global_no = '" + value + "'");
		} else {
			sb.append("and a.guid = '" + value + "'");
		}
		
		final String sql1 = sb.toString();
//		System.out.println("Query1: " + sql1);
		
		ResultSet rs = stmt.executeQuery(sql1);
		
		String reg_dt = "";
		// 01~31
		String dd = "";
		
		while (rs.next()) {
			
			reg_dt = rs.getString("reg_dt");
//			System.out.println(reg_dt);
		}
		
		if (nullChg(reg_dt) == "") {
			dd = "01";		
		} else {
			dd = reg_dt.substring(8, 10);
		}
		
//		System.out.println(dd);
				
		// 2nd SQL => Main SQL
		sb = new StringBuffer();
		sb.append("select ");
		sb.append("a.guid ");
		sb.append(", a.global_no ");
		sb.append(", substr(a.in_ep_id,instr(a.in_ep_id,'.',-1)+1,length(a.in_ep_id)) in_ep_id ");
		sb.append(", substr(a.biztx_id,instr(a.biztx_id,'.',-1)+1,length(a.biztx_id)) biztx_id ");
		sb.append(", a.svr_instance_id ");
		sb.append(", substr(a.reg_dt,0,instr(a.reg_dt,'.')-1) reg_dt ");
		sb.append(", b.elapsedtime ");
		sb.append(", b.state ");
		sb.append(", b.errormessage ");
		sb.append("from infl_custom_log_txn a ");
		sb.append("left join il_transaction_log_%1$s b on a.guid = b.guid ");
		sb.append("where 1=1 ");
		
		// 1: globalNo, 2: guid
		if (inputType.equals("1")) {
			sb.append("and a.global_no = '" + value + "'");
		} else {
			sb.append("and a.guid = '" + value + "'");
		}
		
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd");
//		final String dd = sdf.format(cal.getTime());
		// 01~31
//		System.out.println(dd);
		
		final String sql2 = String.format(sb.toString(), dd);
		
//		System.out.println("Query2: " + sql2);
				
		// Query 결과를 담는다.
		rs = stmt.executeQuery(sql2);
//		System.out.println("######## SBX #######");
		
		String gwTypeName = "";
		
		if (gwType == "I") {
			gwTypeName = "Interal";
		} else {
			gwTypeName = "External";
		}
		
		System.out.println("[Env: " + dbEnv + " " + gwTypeName + "]");
		
		while (rs.next()) {
			
			int rows = 1;
			
			System.out.println("<ROW: " + rows + ">");
			System.out.println("=> GUID: " + rs.getString("guid"));
			System.out.println("=> GLOBAL_NO: " + rs.getString("global_no"));
			System.out.println("=> IN_EP_ID: " + rs.getString("in_ep_id"));
			System.out.println("=> BIZTX_ID: " + rs.getString("biztx_id"));
			System.out.println("=> SVR_INSTANCE_ID: " + rs.getString("svr_instance_id"));
			System.out.println("=> REG_DT: " + rs.getString("reg_dt"));
			System.out.println("=> ELAPSEDTIME: " + rs.getString("elapsedtime"));
			System.out.println("=> STATE: " + rs.getString("state"));
			System.out.println("=> ERRORMESSAGE: " + rs.getString("errormessage"));
			System.out.println("");
			rows++;
		}
	
		rs.close();
		stmt.close();
		conn.close();
			
	}
	
	
	public static String nullChg(String str) {
		return (str == null || str.isEmpty()) ? "" : str;
	}

	
}

