import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class STGClinet_Conn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String username = "infadm";
		String ip ="10.217.136.62";
		int port = 22;
		String password = "Inf@3000";
		
		Session session = null;
		Channel channel = null;
		ChannelExec channelExec = null;
		
		JSch jsch = new JSch();
		
		try {
			session = jsch.getSession(username, ip, port);
			session.setPassword(password);
			
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			
			session.setConfig(prop);
			
			//session.connect(5000);
			
			System.out.println("### Connecting to " + ip + "result => " + session.isConnected());
			
			channel = session.openChannel("exec");
			channelExec = (ChannelExec) channel;
			
			channelExec.setCommand("pwd");
			channelExec.connect();
			
			try {
				InputStream is = channelExec.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (channelExec != null) {
				channelExec.disconnect();
				channelExec = null;
			}
			
			if (channel != null) {
				channel.disconnect();
				channel = null;
			}
			
			if (session != null) {
				session.disconnect();
				session = null;
			}
		}
		

	}

}
