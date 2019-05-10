package gw;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 */
public class ServerSync extends Thread
{
	private ServerSocket ss;
	private int port = 7020;
	private String returnStr = "00035TCP035555555555666666666667777";
	private int headerSize = 20;
	
	private ServerSync(){} 
	
	public ServerSync(int port)
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
		
		Socket s = ss.accept();
		InputStream in = s.getInputStream();
		OutputStream out = s.getOutputStream();
		
		while(true)
		{
			String receviedStr = "";
			try 
			{
				
				
				byte[] getData = new byte[headerSize];
				
				
				in.read(getData, 0, headerSize);
				receviedStr = receviedStr + new String(getData);
				
				String bodyLen = "5";
				int bodyLenInt = Integer.parseInt(bodyLen);
				
				getData = new byte[bodyLenInt];
				
				in.read(getData, 0, bodyLenInt);
				
				receviedStr = receviedStr + new String(getData);
				
				System.out.println("[server]receviedStr[" + receviedStr+"]");
				
				
				out.write(returnStr.getBytes());
				out.flush();
//				out.close();
				System.out.println("[server]send:"+returnStr);
				
//				s.close();
				
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
		new ServerSync().start();
	}
}

