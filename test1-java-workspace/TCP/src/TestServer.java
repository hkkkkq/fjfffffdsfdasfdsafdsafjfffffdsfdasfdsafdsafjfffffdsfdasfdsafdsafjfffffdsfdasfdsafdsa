
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author kky
 *
 */
public class TestServer extends Thread
{
	private ServerSocket ss;
	private int port = 9001;
	private String returnStr = "0094120141023154900000001MRBL22222222222222222222233333333333333333333Z00222222222222222222222999999";
	private int headerSize = 5;
	
	private TestServer(){} 
	
	public TestServer(int port)
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
				
				String bodyLen = "5";
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
		new TestServer().start();
	}
}

