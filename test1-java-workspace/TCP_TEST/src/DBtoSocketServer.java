
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kky
 *
 */
public class DBtoSocketServer extends Thread
{
	private ServerSocket ss;
	private int port = 9009;
	//private String returnStr = "0010oooookkkkk";
	private int headerSize = 610;
	
	private DBtoSocketServer(){} 
	
	public DBtoSocketServer(int port)
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
		System.out.println("Start Time: " + DateyyyyMMddHHmmssSSS());
		
		
		
		while(true)
		{
			String receviedStr = "";
			try 
			{
				Socket s = ss.accept();			
				
				//System.out.println(s.getInetAddress().getHostAddress());
				System.out.println("#" + s.getInetAddress().getHostName() + ":" + s.getPort());

				InputStream in = s.getInputStream();
				byte[] getData = new byte[headerSize];
				
				
				in.read(getData, 0, headerSize);
				receviedStr = receviedStr + new String(getData);
						
				System.out.println("[server]receviedStr[" + receviedStr+"]");			
				
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
		new DBtoSocketServer().start();
	}
	
	public String DateyyyyMMddHHmmssSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
  }
}

