
import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/* javac -cp ./jsch-0.1.53.jar SftpTest.java
 * java -cp .:/home/infadm/sftp_test/jsch-0.1.53.jar SftpTest
 */
public class SftpTest2{

	public static void main(String[] args) throws SftpException {
		// TODO Auto-generated method stub
		JSch jsch = new JSch();
	
		String ip = "10.217.159.222";
		int port = 22;
		String id = "infadm";
		String pw = "Inf@3000";
		int timeoutInt = 999999999;
		
		int OutgoingSpeedLimit;
		
//		String ip = "172.29.213.115";
//		int port = 22;
//		String id = "ktcorp";
//		String pw = "ktcorp12!";
		
		try 
		{
//			jsch.addIdentity("/home/infadm/.ssh/known_hosts");
//			jsch.setKnownHosts("/home/infadm/.ssh/known_hosts");
			
			System.out.println("connect start");
//			jsch.setKnownHosts("/home/infadm/.ssh/known_hosts");
			
			Session session = jsch.getSession(id, ip, port);
			session.setPassword(pw);
			session.setDaemonThread(true);
		    Properties config = new Properties();
		    config.put("StrictHostKeyChecking", "no");
		    config.put("PreferredAuthentications", "publickey,password,keyboard-interactive");
		    session.setConfig(config);
		    session.connect(timeoutInt);
		    session.setTimeout(timeoutInt); 
		    
		    System.out.println("connect End");
		    
		    Channel channel = session.openChannel("sftp");
		    channel.connect();
		    
		    ChannelSftp channelSftp = (ChannelSftp)channel;
		    
		    System.out.println("TEST");
		    try {
				System.out.println("pwd: " + channelSftp.pwd());
				
				System.out.println("cd /home/infadm");
				channelSftp.cd("/home/infadm");
				
				String src = "test1?.log";

				System.out.println("try put");
				
				// ? 파일명 String으로 전달 가능
//				channelSftp.put(src, "bbbb");
				
				File f = new File("./test1?.log");
				FileInputStream fis = new FileInputStream(f);
				
				// ? 파일명 FileInputStream으로 전달 불가능
				channelSftp.put(fis, src, 0);
				
				channelSftp.disconnect();
			    channel.disconnect();
			    session.disconnect();
				
		    } 
		    catch (SftpException e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	    
		    
		    System.out.println("connect end");
		} 

		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
				
	}

}
