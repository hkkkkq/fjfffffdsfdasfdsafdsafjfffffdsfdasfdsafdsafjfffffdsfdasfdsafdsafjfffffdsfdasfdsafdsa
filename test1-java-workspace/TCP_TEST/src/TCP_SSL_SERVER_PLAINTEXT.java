
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kky
 *
 */
public class TCP_SSL_SERVER_PLAINTEXT extends Thread
{
	private ServerSocket ss;
	private int port = 14444;
	//private int port = 37127;
	private String returnStr = "2222222222";
	private int headerSize = 10;
	
		
	private TCP_SSL_SERVER_PLAINTEXT(){} 
	
	public TCP_SSL_SERVER_PLAINTEXT(int port)
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
				InputStream in = s.getInputStream();
				byte[] getData = new byte[headerSize];
				
				
				in.read(getData, 0, headerSize);
				receviedStr = receviedStr + new String(getData);
				
//				int i = 0;
//				
//	            if ( (i = in.read(getData)) > 0 ) {
//	            	System.out.println(i);
//	            	receviedStr = new String(getData);
//	            	
//	            }
				
//				int i = 0;
//				
//				while ( true ) {
//					
//					
//					if ( (i = in.read()) > -1 ) {
//						System.out.println(i);
//					}
//					
//					if (i == -1) {
//						System.out.println(i);
//						break;
//					}
//						
//				}
//	            

//				String bodyLen = "5";
//				int bodyLenInt = Integer.parseInt(bodyLen);
//				
//				getData = new byte[bodyLenInt];
//				
//				in.read(getData, 0, bodyLenInt);
				
//				receviedStr = receviedStr + new String(getData);
				
				System.out.println("Receive Time: " + DateyyyyMMddHHmmssSSS());
				System.out.println("[server]receviedStr[" + receviedStr+"]");
				
				//Thread.sleep(15000);
				
				OutputStream out = s.getOutputStream();
				out.write(returnStr.getBytes());
				out.flush();
				out.close();
				System.out.println("Send Time: " + DateyyyyMMddHHmmssSSS());
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
		new TCP_SSL_SERVER_PLAINTEXT().start();
	}
	
	public String DateyyyyMMddHHmmssSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
  }
}

