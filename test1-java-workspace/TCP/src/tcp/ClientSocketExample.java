package tcp;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketExample {
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String ip = "10.217.232.12";
    	int port = 9004;
//    	String ip = "192.168.70.41";
//    	int port = 7777;
    	
//    	String sendData1 = "1637IFTCPTCP001      123456789012345678901234567890123456789012345678901234567890123456789011234567890                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     98912014102811111116001112006122200005BS000011000  101234123411117501111234567            0                         123456789012123456789012123456789012123456789012123456789012123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789020120917        20120917 12345678 12345678901234567890123456789012345678901234567890                                                                                                                                                                                                                                   1";
    	String sendData1 = "111156789012345678901234";
    	
    	Socket socket = null;
    	OutputStreamWriter out = null;
    	InputStreamReader in = null;
    	try {
            // Create a connection to the server socket on the server application
            socket = new Socket(ip, port);
            
//            boolean isWrite = false;
            boolean isWrite = true;
            boolean isSleep = false;
//            long sleepTime = 5000;
            long sleepTime = 500;
            
            if( isSleep ) {
	            try {
	            	System.out.println("[Client] Sleep Time::" + sleepTime + "(ms)");
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }

            if( isWrite ) {
	            // Send a message to the client application
	            out = new OutputStreamWriter(socket.getOutputStream());
	            out.write(sendData1);
	            out.flush();
	            System.out.println("[Client] Send Data::" + sendData1.length());
	            
	            // Read and display the response message sent by server application
	            String message = null;
	            in = new InputStreamReader(socket.getInputStream());
	            char[] cbuf = new char[24];
	            if( in.read(cbuf) > 0 ) {
	            	message = new String(cbuf);
	            }
	            
	            System.out.println("[Client] Recv Data::[" + message.length() + "]" + message);
	
            }
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
}