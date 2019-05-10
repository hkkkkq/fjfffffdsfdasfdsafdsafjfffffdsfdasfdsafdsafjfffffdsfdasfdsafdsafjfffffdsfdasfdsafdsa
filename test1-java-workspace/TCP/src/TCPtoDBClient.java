import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPtoDBClient {
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String ip = "127.0.0.1";
    	int port = 7777;
    	
    	String sendData1 = "1111ABCD1234";
    	
    	Socket socket = null;
    	OutputStreamWriter out = null;
    	InputStreamReader in = null;
    	try {
            // Create a connection to the server socket on the server application
            socket = new Socket(ip, port);
            
            boolean isWrite = true;
            boolean isSleep = false;
            long sleepTime = 4000;
            
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
	            char[] cbuf = new char[12];
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