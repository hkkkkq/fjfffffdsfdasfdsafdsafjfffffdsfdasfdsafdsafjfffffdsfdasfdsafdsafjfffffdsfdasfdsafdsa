package tcp;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket_ARS_9999 {
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String ip = "192.168.56.101";
    	int port = 9999;
    	
//    	String sendData1 = "0030002011111122222222223333333333"; // 통신 해더 방식 ( 해더 + 바디가 30, 토탈 34 )
    	String sendData1 = "O2801037214384888888884444";     // 공통 해더 방식 해더길이포함유므 체크안함 ( 0010 1111111111 )
//    	String sendData1 = "55555666667777788888";     // 고정 길이 방식 ( 20 )
    	
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
//	            char[] cbuf = new char[83]; 
	            char[] cbuf = new char[132]; 
	            if( in.read(cbuf) > 0 ) {
	            	message = new String(cbuf);
	            }
	            
	            System.out.println("[Client] Recv Data::[" + message.length() + "]" +"["+ message + "]");
	
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