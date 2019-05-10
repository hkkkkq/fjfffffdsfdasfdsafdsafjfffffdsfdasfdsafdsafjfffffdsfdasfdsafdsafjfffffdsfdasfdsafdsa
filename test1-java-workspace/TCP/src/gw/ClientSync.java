package gw;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 */
public class ClientSync {

	private String ip = "127.0.0.1";	
	private int port = 7010; //TCP_EX01
	private String requestStr = "00025TCP03111111111122222";
	private int bufferSize = 35; 
	
	public ClientSync(){}
	
	public ClientSync(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	public void isLive()
	{
		Socket socket = null;
		
		try
		{
			socket = new Socket(ip, port);
			
			System.out.println(socket);
			OutputStream out = socket.getOutputStream();
			DataInputStream in = new DataInputStream(socket.getInputStream());
			byte[] msg = requestStr.getBytes();
			
			while(true)
			{
				out.write(msg);
				out.flush();
				
				
				System.out.println("[client] send request: " + requestStr);
				
				int len = -1;
				
				byte[] getData = new byte[bufferSize];
				String receviedStr ="";
				
				len = in.read(getData, 0, bufferSize);
				
				receviedStr = receviedStr + new String(getData);
				
				System.out.println("[client] len="+len);
				System.out.println("[client] receviedStr:" + receviedStr);
				
				Thread.sleep(5000);
			}
			
			
//			socket.close();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("=== server is not start!! start local server====");
		}
		finally
		{ 
			if(socket != null)
			{
				try {	socket.close();	} catch (IOException e) {	e.printStackTrace();	}
			}
		}
	}
	
	public static void main(String[] args)
	{
		try 
		{
			new ClientSync().isLive();
			System.out.println("server isCalled");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

