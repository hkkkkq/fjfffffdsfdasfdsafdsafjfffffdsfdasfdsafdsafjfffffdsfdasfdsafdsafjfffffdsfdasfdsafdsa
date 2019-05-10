package endpointList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.DatatypeConverter;

import com.tmax.tibero.jdbc.ext.TbDataSource;

/*
 * ********************소스 수정 이력****************************************
 * DATE				Modifier			Description
 * *********************************************************************
 * 2015.12.14		전원호(82023121)		최초작성 
 * *********************************************************************
 */

public class Blob_Update1 {

	public static void main(String[] args) throws SQLException{
		// TODO Auto-generated method stub
	
		TbDataSource tds = new TbDataSource();
		
		// 접속 DB정보 입력
		String db_ip = "10.217.136.60";
		String db_port = "8629";
		String sid = "SBX1GWE";
		
		tds.setURL("jdbc:tibero:thin:@" + db_ip + ":" + db_port + ":" + sid);
		
		// 접속 계정정보를 입력한다.
		tds.setUser("infinilink");
		tds.setPassword("infinilink");
		
		Connection conn = tds.getConnection();
		//Statement stmt = conn.createStatement();
		
		
		StringBuffer query1 = new StringBuffer();
		query1.append("select content from il_resource ");
		query1.append("where 1=1 ");
		query1.append("and bizsystem_id = 'IB_ORDER1' ");
		query1.append("and sys_id = 'default-log-adapter.default-log-endpoint';");

		PreparedStatement pstmt = conn.prepareStatement(query1.toString());
		
		//System.out.println("Query: " + sb.toString());
				
		// Query 결과를 담는다.	
		ResultSet rs = pstmt.executeQuery();
		//ResultSet rs = stmt.executeQuery(query1.toString());			
		
		String updateData = "";
		
		int rows = 1;
		while (rs.next()) {
			
			String content;
			
			content = new String(rs.getBytes(1));
			
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("row num: " + rows);
			System.out.println("+++++++++++++++++++++++++");
			
			//System.out.println(rs.getString(1));
			//System.out.println(content);
			
			content = content.replaceAll("LogAdapter_DataSource999","LogAdapter_DataSource");
			
			System.out.println(content);
			
			updateData = DatatypeConverter.printHexBinary(content.getBytes());
			
			rows++;
		}
		
		System.out.println(updateData);
		
		
		//UPDATE
		conn.setAutoCommit(false);
		
		StringBuffer query2 = new StringBuffer();
		query2.append(" UPDATE IL_RESOURCE SET CONTENT='"+updateData+"' \n");
		query2.append(" WHERE bizsystem_id = 'IB_ORDER1' and sys_id = 'default-log-adapter.default-log-endpoint'; \n");

		int updateResult = pstmt.executeUpdate(query2.toString());
		
		conn.commit();
		
		System.out.println("updateResult="+updateResult);
		
		rs.close();
		pstmt.close();
		conn.close();
	}
}
