import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;



/**
 * compile - javac TCP_SSL_SERVER.java
 * run     - java -Djavax.net.ssl.keyStore=/app/infinilink/ssl/sbx/server/keystore -Djavax.net.ssl.keyStorePassword=123456 TCP_SSL_SERVER
 * @author tmax
 *
 */
public class TCP_SSL_SERVER extends Thread {

	private int headerSize = 10;
	private String returnStr = "2222222222";
	private int port = 41158;
	private TCP_SSL_SERVER(){}
	
	public static void main(String[] args)
	{
		new TCP_SSL_SERVER().start();
	}
	
	public void run()
	{
		try
		{
			execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void execute()
	{
		OutputStream out = null;
		SSLServerSocketFactory sslFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		SSLSocket socket = null;
		try 
		{
			SSLServerSocket sslServerSocket = (SSLServerSocket) sslFactory.createServerSocket(port);
			
			while (true)
			{
				String receviedStr = "";
				// Socket s = null;
				// OutputStream out = null;
				try {
					System.out.println("start server port="+port);
					
					socket = (SSLSocket)sslServerSocket.accept();
					InputStream in = socket.getInputStream();
					byte[] getData = new byte[headerSize];
					
					
					
					in.read(getData, 0, headerSize);
					receviedStr = receviedStr + new String(getData);

					System.out.println("[server]receviedStr[" + receviedStr+ "]");

					out = socket.getOutputStream();
					out.write(returnStr.getBytes());
					out.flush();
					// out.close();
					System.out.println("[server]send:" + returnStr);

					socket.close();

					if ("kill".equals(receviedStr)) {
						System.out.println("[server] KILL !!!! ");
						System.exit(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
					out.close();
					
					if (socket!= null)
						socket.close();
					System.out.println("[server] exception catch receviedStr[" + receviedStr + "]");

				}

			}
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		finally
		{
			try {
				if( out != null)
					out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if( socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

