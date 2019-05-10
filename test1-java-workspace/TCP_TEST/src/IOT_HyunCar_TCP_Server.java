
import java.io.BufferedReader;
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
public class IOT_HyunCar_TCP_Server extends Thread
{
	private ServerSocket ss;
	private int port = 14445;
	//private int port = 37127;
	private String returnStr = "okokokokok";
	private int headerSize = 30;
	
		
	private IOT_HyunCar_TCP_Server(){} 
	
	public IOT_HyunCar_TCP_Server(int port)
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
				
				BufferedReader br = new BufferedReader(new InputStreamReader(in));	
							
				receviedStr = br.readLine();
				
//				byte[] getData = new byte[headerSize];
//				
//				in.read(getData, 0, headerSize);
//				receviedStr = receviedStr + new String(getData);
				
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
		new IOT_HyunCar_TCP_Server().start();
	}
	
	public String DateyyyyMMddHHmmssSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
  }
}

