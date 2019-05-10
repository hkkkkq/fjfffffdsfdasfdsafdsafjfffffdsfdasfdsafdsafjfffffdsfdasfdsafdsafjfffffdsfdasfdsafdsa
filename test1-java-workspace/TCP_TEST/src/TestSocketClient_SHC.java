
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSocketClient_SHC {
    /**
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String ip = "10.217.136.62";
//    	String ip = "10.225.137.132";
//    	int port = 9025;
    	int port = 50002;
//    	int port = 9033; // Hongkong Roaming TEST

    	
//    	String sendData1 = "0015111112222233333\n";
    	String sendData1 = "00394654504c4720203036303052452020202020202020303030303832393135303931313030312020202020202020202020202020202020202020";
//    	String sendData1 = "0015000112222233333"; // Hongkong Roaming TEST
    	
    	byte[] ba = hexToByteArray(sendData1);
    	

    	Socket socket = null;
    	OutputStream out = null;
    	InputStreamReader in = null;
    	try {
            // Create a connection to the server socket on the server application
            socket = new Socket(ip, port);
            
            //socket.setSoTimeout(3000);
            
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
	            out = socket.getOutputStream();
	            out.write(ba);            
	            out.flush();
	            System.out.println("[Client] Send Data::" + ba.length);
	            
	            // Read and display the response message sent by server application
//	            String message = null;
	            in = new InputStreamReader(socket.getInputStream());
//	            char[] cbuf = new char[80];
	            
	            int a = -1;
	            
	            while ((a = in.read()) > -1) {
	            	            	
	            	System.out.println("[" + intToHex(a,2) + "]");
           	            	
	            }
	            
//	            if( in.read(cbuf) > 0 ) {
//	            	message = new String(cbuf);
//	            }
	            
	            //System.out.println("[Client] Recv Data::[" + message.length() + "]" + message);
	            
	            
	            socket.close();
	
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
    
 // Hex-String to ByteArray
 	public static byte[] hexToByteArray(String s) {
 		
 		if (s == null || s.length() == 0) {
 			return null;
 		}
 		
 		byte[] ba = new byte[s.length()/2];
 		
 		for (int i=0; i<ba.length; i++) {
 			ba[i] = (byte)Integer.parseInt(s.substring(2*i, 2*i+2), 16);
 		}
 		
 		return ba;
 	}
 	
	public static String intToHex(int s, int n) {
		
	    String result = "";

	    result = Integer.toHexString(s).toUpperCase();
	    
	    return String.format("%" + n + "s", result).replace(" ", "0");
	}
}