
import com.jcraft.jsch.*;

import java.util.Properties;

/* javac -cp ./jsch-0.1.53.jar SftpTest.java
 * java -cp .:/home/infadm/sftp_test/jsch-0.1.53.jar SftpTest
 */
public class SftpTest{

	public static void main(String[] args) throws SftpException {
		// TODO Auto-generated method stub
		JSch jsch = new JSch();
	
//		String ip = "10.217.159.97";
//		int port = 22;
//		String id = "infadm";
//		String pw = "Inf@3000";
		int timeoutInt = 999999999;
		
		int OutgoingSpeedLimit;
		
		String ip = "172.29.213.115";
		int port = 22;
		String id = "ktcorp";
		String pw = "ktcorp12!";
		
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    
		    	    
		    channelSftp.disconnect();
		    channel.disconnect();
		    session.disconnect();
		    
		    
		} 
		catch (JSchException e)
		{
			e.printStackTrace();
		}
				
	}

}
