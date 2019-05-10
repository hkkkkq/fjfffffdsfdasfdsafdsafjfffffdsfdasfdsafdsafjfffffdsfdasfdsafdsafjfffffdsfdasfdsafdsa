package endpointList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;


public class Ojdbc_TEST {

	
	public static void printLog(PrintWriter pw, String logStr)
	{
		pw.println(logStr);
	}
	
	public static void closeLog(PrintWriter pw, BufferedWriter bw, FileWriter fw)
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
	
	public static void main(String[] args) 
	{
		InitialContext ctx = null;
		DataSource ds = null;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String dbUrl = "jdbc:oracle:thin:@10.217.136.71:1522:SBXESB";
//		String dbUrl = "jdbc:tibero:thin:@10.219.4.75:8640:GWEDB";
		String dbUsr = "aquser";
		String dbPwd = "Aquser1234!";
//		String dbPwd = "Prd$inf1";
		
//		String day = "01";
//		String jdbcName = "LogAdapter_DataSource";
		try
		{
//			ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup(jdbcName);
//			con = ds.getConnection();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(dbUrl, dbUsr, dbPwd);
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT INFL_SEQ_UNIQUEID_4.NEXTVAL FROM DUAL \n");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(query.toString());
			
			int count=0;
			while(rs.next())
			{
				System.out.println("===============================");
				count++;
				
				System.out.println("count="+count);
				
				String uniqueId = Integer.toString(rs.getInt(1));

				System.out.println(uniqueId);
				System.out.println("===============================");
			}
			
			if(rs!=null)  { rs.close();   rs = null;  }
			if(stmt!=null){ stmt.close(); stmt = null;}
			if(con!=null) { con.close();  con = null; }
			if(ctx!=null) { ctx.close();  ctx = null; }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(stmt!=null){ try {	stmt.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt = null;}
			if(con!=null) { try {	con.rollback(); con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
			if(ctx!=null) { try {	ctx.close();	} catch (Exception e1) {	e1.printStackTrace();} ctx = null;}
		}
		finally
		{
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(stmt!=null){ try {	stmt.close();	} catch (Exception e1) {	e1.printStackTrace();} stmt = null;}
			if(con!=null){ try { con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
			if(ctx!=null){ try {	ctx.close();	} catch (Exception e1) {	e1.printStackTrace();} ctx = null;}
		}
		
	}

}
