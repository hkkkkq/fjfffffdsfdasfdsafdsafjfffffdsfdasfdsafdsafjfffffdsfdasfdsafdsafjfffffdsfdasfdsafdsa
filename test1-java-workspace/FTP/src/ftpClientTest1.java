import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ftpClientTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FTPClient ftpClient = null;

		try {
		

			ftpClient = new FTPClient();
			
			
			//ftpClient.setControlKeepAliveTimeout(300);
			
//			FTPClientConfig fccf = new FTPClientConfig();
//			fccf.setServerLanguageCode("ko");
//			fccf.setDefaultDateFormatStr("M월 d일 HH:mm");
//			fccf.setRecentDateFormatStr("M월 d일 yyyy");
//			
//			System.out.println(fccf.getDefaultDateFormatStr());
			
//			ftpClient.setControlEncoding("utf-8");

//			String dateFormat = fccf.getDefaultDateFormatStr();
//			System.out.println(dateFormat);
			
//			ftpClient.configure(fccf);
			
			
			InetAddress ia = InetAddress.getByName("10.217.136.61");
			
			ftpClient.connect(ia);
			
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP connection failed");
				
			} else {

				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP connection succeeded");
				
//				String sys = ftpClient.getSystemType();
//				System.out.println(sys);
//				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP getSystemType");
			
				
				ftpClient.login("infadm", "Inf@3000");
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP login");
				
				//ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "Change File type");
				
				
				String pwd = ftpClient.printWorkingDirectory();
				System.out.println(pwd);
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP printWorkingDirectory");
				
//				ftpClient.changeWorkingDirectory("/Data/outer_ftp/1405");
				ftpClient.changeWorkingDirectory("/home/infadm/monitoring_chk_sh");
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "changeWorkingDirectory");
							
				ftpClient.enterLocalActiveMode();
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "enterLocalActiveMode");
								
				FTPFile[] ff1 = ftpClient.listFiles();
				System.out.println("listFiles().count: " + ff1.length);
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "listFiles");
				System.out.println("==============================================");
				
				for (FTPFile f : ff1) {
					//System.out.println(f.getName() + "(" + f.getSize() + ")");
					System.out.println(f.getRawListing());
				}
				
				
				System.out.println("==============================================");
				
//				FTPFile[] ff2 =ftpClient.mlistDir();
//				System.out.println("mlistDir().count: " + ff2.length);
//				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "mlistDir");
//				
//				for (FTPFile f : ff2) {
//					System.out.println(f.getName() + "(" + f.getSize() + ")");	
//				}
				
//				String[] ff3 =ftpClient.listNames();
//				System.out.println("listNames().count: " + ff3.length);
//				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "listNames");
//				
//				for (String f : ff3) {
//					System.out.println(f);	
//				}
				
				
				ftpClient.logout();
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "logout");
			}
		
		} catch(Exception e) {
			
			System.out.println("Error occurs");
			e.printStackTrace();
			
		} finally {
			
			if (ftpClient != null && ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
					System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "disconnect succeeded");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ftpClient.disconnect() Error occurs");
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	
	public static String getCurrentTime() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}

}
