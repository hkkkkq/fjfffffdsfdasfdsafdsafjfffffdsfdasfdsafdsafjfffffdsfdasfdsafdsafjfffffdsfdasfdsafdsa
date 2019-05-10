
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSocketClient_SKT_1100 {
    /**
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String ip = "10.217.159.221";
//    	String ip = "10.225.137.132";
//    	int port = 9025;
    	int port = 9214;
//    	int port = 9033; // Hongkong Roaming TEST

    	
//    	String sendData1 = "0015111112222233333\n";
    	String sendData1 = "026620160720123015110020160829S015720160829185S200          20160829S015720160829185    LG-F120K                                                                        0012124352219050101245         20160829S01572016082918512301354                                       ";
//    	String sendData1 = "0015000112222233333"; // Hongkong Roaming TEST
    	

    	Socket socket = null;
    	OutputStreamWriter out = null;

    	try {
            // Create a connection to the server socket on the server application
            socket = new Socket(ip, port);
            
            boolean isWrite = true;
            //boolean isWrite = true;
//            boolean isSleep = false;
//            long sleepTime = 5000;
//            
//            if( isSleep ) {
//	            try {
//	            	System.out.println("[Client] Sleep Time::" + sleepTime + "(ms)");
//					Thread.sleep(sleepTime);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//            }
           

            if( isWrite ) {
	            // Send a message to the client application
	            out = new OutputStreamWriter(socket.getOutputStream());
	            
	            out.write(sendData1);
	            out.flush();
	            System.out.println("[Client] Send Data::" + sendData1.length());
	                        
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
}