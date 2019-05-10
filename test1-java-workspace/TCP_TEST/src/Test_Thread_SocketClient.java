
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kky
 *
 */
public class Test_Thread_SocketClient extends Thread
{
	
	String ip = "10.217.136.62";
	int port = 9041;
	String sendData1 = "12345";
	
		
	public Test_Thread_SocketClient(){} 
	

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
		Socket socket = null;
    	OutputStreamWriter out = null;

    	try {
            // Create a connection to the server socket on the server application
            socket = new Socket(ip, port);
            
            boolean isWrite = true;
           
            if( isWrite ) {
	            // Send a message to the client application
	            out = new OutputStreamWriter(socket.getOutputStream());
	            
	            out.write(sendData1);
	            out.flush();
	            System.out.println("Start Time: " + DateyyyyMMddHHmmssSSS());
	            System.out.println("[Client] Send Data: " + sendData1 + "(" + sendData1.length() + ")");
	                       
	            socket.close();
	
            }
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void main(String[] args) 
	{

		for (int i=0; i<5; i++) {
			new Test_Thread_SocketClient().start();
		}
		
	}
	
	public String DateyyyyMMddHHmmssSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
  }
}

