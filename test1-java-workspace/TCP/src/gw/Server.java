package gw;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 */
public class Server extends Thread
{
	private ServerSocket ss;
	private int port = 9002;
	private String returnStr = " 98912014102811111116001112006122200005BS000011000  101234123411117501111234567            0                         123456789012123456789012123456789012123456789012123456789012123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789020120917        20120917 12345678 12345678901234567890123456789012345678901234567890                                                                                                                                                                                                                                   2";
//	private int headerSize = 1000;
	private int headerSize = 1;
	
	private Server(){} 
	
	public Server(int port)
	{
		this.port = port;
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
	
	public void execute() throws Exception
	{
		if(ss == null) ss = new ServerSocket(port);
		
		System.out.println("Server Start[port:"+port+"]");
		
		while(true)
		{
			String receviedStr = "";
			try 
			{
				Socket s = ss.accept();
				InputStream in = s.getInputStream();
				byte[] getData = new byte[headerSize];
				
				
				in.read(getData, 0, headerSize);
				receviedStr = receviedStr + new String(getData);
				
				String bodyLen = "39";
				int bodyLenInt = Integer.parseInt(bodyLen);
				
				getData = new byte[bodyLenInt];
				
				in.read(getData, 0, bodyLenInt);
				
				receviedStr = receviedStr + new String(getData);
				
				System.out.println("[server]receviedStr[" + receviedStr+"]");
				
				OutputStream out = s.getOutputStream();
				out.write(returnStr.getBytes());
				out.flush();
				out.close();
				System.out.println("[server]send:"+returnStr);
				
				s.close();
				
				if("kill".equals(receviedStr))
				{
					System.out.println("[server] KILL !!!! ");
					System.exit(0);
				}
				
			}
			catch (Exception e) 
			{
				System.out.println("[server] exception catch receviedStr[" + receviedStr+"]");
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) 
	{
		new Server().start();
	}
}

