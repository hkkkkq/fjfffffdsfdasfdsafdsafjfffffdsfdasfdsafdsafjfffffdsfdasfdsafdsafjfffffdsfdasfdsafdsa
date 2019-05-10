import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import com.tmax.tibero.jdbc.ext.TbDataSource;


public class DB_Conn {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String type = args[0];
		String fileName = "";
		
		if ("1".equals(type)) {
			fileName = "db_conn_list_1.properties";
		} else {
			fileName = "db_conn_list_2.properties";
		}
		
		System.out.println("##### " + fileName + " #####");
		System.out.println("##### DB Conn START [" + DateyyyyMMddHHmmssSSS() + "]#####");
		
		File f = new File("file",fileName);
		//File f = new File("./");
		//System.out.println(f.getAbsolutePath());
		//System.out.println(System.getProperties());
		
		
		
		FileInputStream fis = new FileInputStream(f);
		
		Properties prop = new Properties();
		prop.load(fis);
		
		fis.close();
		
		Enumeration<?> e = prop.propertyNames();
		
		while (e.hasMoreElements()) {
			
			String key = (String)e.nextElement();
			String value = prop.getProperty(key);
			String[] values = value.split(",");
			
			//System.out.println(key);
			//System.out.println(value);
			//System.out.println(values[0]);
			
			String ip = values[0];
			String port = values[1];
			String sid = values[2];
			String id = values[3];
			String pw = values[4];
			
			TbDataSource tds = new TbDataSource();
			
			try {
				tds.setLoginTimeout(3000);
				tds.setURL("jdbc:tibero:thin:@" + ip + ":" + port + ":" + sid);
				tds.setUser(id);
				tds.setPassword(pw);
				
				Connection conn = tds.getConnection();
								
				String sql = "select to_char(sysdate) from dual;";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					System.out.println(key + "=>" + rs.getString(1));
				}
				
				rs.close();
				pstmt.close();
				conn.close();

			}	
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}
	
		System.out.println("##### DB Conn END [" + DateyyyyMMddHHmmssSSS() + "]#####");

	}
	
	public static String DateyyyyMMddHHmmssSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}

}
