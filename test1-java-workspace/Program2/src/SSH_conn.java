import java.io.*;
import java.security.PublicKey;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;



public class SSH_conn {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		SSHClient client = new SSHClient();
		client.setConnectTimeout(3000);
		client.setTimeout(3000);
		client.addHostKeyVerifier(new HostKeyVerifier() {
			
			@Override
			public boolean verify(String hostname, int port, PublicKey key) {
				// TODO Auto-generated method stub
				return true;
			}
		});
				
		try {
			//client.connect("10.217.136.61");
			client.connect("10.217.136.61",22);
			//client.connect("10.217.136.61", 22, InetAddress.getByName("10.222.111.1"), 49999);
			
			client.authPassword("infadm", "Inf@3000");
			
			
		} catch (Exception e) {

			System.out.println("Excepion: " + e.getMessage());
		}
		
		if (client.isConnected()) {
			System.out.println("connection succeeded");
			
//			Command cmd = client.startSession().exec("");
//			BufferedReader br = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
			
		} else {
			System.out.println("connection failed");
		}
		
		client.disconnect();
		
	}

}
