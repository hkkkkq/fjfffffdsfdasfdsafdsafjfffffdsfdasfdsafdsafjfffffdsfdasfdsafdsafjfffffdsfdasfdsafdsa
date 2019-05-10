
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author kky
 *
 */
public class TestClient {

	//private String ip = "10.217.232.11";	
	//private int port = 8808; //
	
	private String ip = "localhost";	
	private int port = 8888; //
	
	String LENGTH = "0094";        //4
	String SENDER = "1";        //1
	String PORT_DATE = "20141023";   
	String PORT_TIME = "154900";    
	String SEQNO = "000001";       
	String WRK_JOB_CODE = "MRBL"; 
	String RESERVED1    = "222222222222222222222";    // RESERVED1 21
	String MSG_TRACE_NO = "33333333333333333333"; 
	String RECEIVE_YN   = "Z" ; 
	String RECEIVE_CODE = "00"; 
	String RESERVED2    = "222222222222222222222999999"; // RESERVED2 27
	
	private int bufferSize = 100;
	
	public TestClient(){}
	
	public TestClient(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	public boolean isLive()
	{
		Socket socket = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append(LENGTH);
		sb.append(SENDER);
		sb.append(PORT_DATE);
		sb.append(PORT_TIME);
		sb.append(SEQNO);
		sb.append(WRK_JOB_CODE);
		sb.append(RESERVED1);
		sb.append(MSG_TRACE_NO);
		sb.append(RECEIVE_YN);
		sb.append(RECEIVE_CODE);
		sb.append(RESERVED2);
		
		String requestStr = sb.toString();
		
		try
		{
			socket = new Socket(ip, port);
//			socket.setSoTimeout(500); 
			
			System.out.println(socket);
			OutputStream out = socket.getOutputStream();
			DataInputStream in = new DataInputStream(socket.getInputStream());
			byte[] msg = requestStr.getBytes();
			out.write(msg);
			out.flush();
			
			
			System.out.println("[client] send request: " + "(" + ip + " " + port + ")" + requestStr);
			
			int len = -1;
			
			byte[] getData = new byte[bufferSize];
			String receviedStr ="";
			
			len = in.read(getData, 0, bufferSize);
			
			receviedStr = receviedStr + new String(getData);
			
			System.out.println("[client] len="+len);
			System.out.println("[client] receviedStr:" + receviedStr);
			
			socket.close();
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("=== server is not start!! start local server====");
			return false;
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
			boolean isLive = new TestClient().isLive();
			System.out.println("servier isLive=>"+isLive);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

