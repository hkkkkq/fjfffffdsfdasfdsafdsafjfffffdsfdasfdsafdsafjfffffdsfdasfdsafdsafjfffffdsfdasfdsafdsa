import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


public class ftpClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FTPClient ftpClient = null;

		try {
		
			ftpClient = new FTPClient();
			
			ftpClient.setControlEncoding("utf-8");
			ftpClient.setControlKeepAliveTimeout(300);
			
			InetAddress ia = InetAddress.getByName("192.168.34.82");
			
			ftpClient.connect(ia);
			
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP server refuse connection");
				
			} else {

				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP connection succeeded");
				
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "Change File type succeeded");
				
				ftpClient.login("ktf016", "ktf016123!");
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "FTP login succeeded");
				
				ftpClient.changeWorkingDirectory("/app/ktf/MOBI");

				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "changeWorkingDirectory succeeded");
				
				File getFile = new File("/home/infadm/ftpGetTest/SSKTL1_FKTFCINT_ID0001_T20161125120000.DAT");
				OutputStream os = new FileOutputStream(getFile);
				
				ftpClient.retrieveFile("SSKTL1_FKTFCINT_ID0001_T20161125120000.DAT",os);
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "retrieveFile succeeded");
				
				os.close();
				
				ftpClient.logout();
				System.out.println("[" + ftpClient.getReplyCode() + "]" + "[" + getCurrentTime() + "]" + "logout succeeded");
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
