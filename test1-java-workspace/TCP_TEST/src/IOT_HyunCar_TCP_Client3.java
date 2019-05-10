
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

public class IOT_HyunCar_TCP_Client3 {
    /**
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String ip = "10.217.136.62";
//    	String ip = "10.225.137.132";
//    	int port = 9025;
    	int port = 14445;
//    	int port = 9033; // Hongkong Roaming TEST

    	
//    	String sendData1 = "0015111112222233333\n";
//    	String sendData1 = "1111111111";
//    	String sendData1 = "0015000112222233333"; // Hongkong Roaming TEST
    	
   	
    	BigInteger bi = new BigInteger("16030100a30100009f03015797671183c04cc807f6f83d2725559839ef4ee6f158858d31679691525cfd1d000038c00ac0140035c005c00f00390038c009c013002fc004c00e00330032c008c012000ac003c00d00160013c007c0110005c002c00c000400ff0100003e000a0034003200170001000300130015000600070009000a0018000b000c0019000d000e000f001000110002001200040005001400080016000b000201001503010002020a", 16);
    	

    	Socket socket = null;
    	//OutputStreamWriter out = null;
    	OutputStream out = null;
    	InputStreamReader in = null;
    	
    	
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
	            //out = new OutputStreamWriter(socket.getOutputStream());
	            out = socket.getOutputStream();
	            
	            out.write(bi.toByteArray());
	               
	            System.out.print("[Client] Send Data: ");
	            for (byte b: bi.toByteArray()){
	            	System.out.print(b);
	            }
	            System.out.println("");
	        
	            out.flush();
	            System.out.println("[Client] Send Data Length: " + bi.toByteArray().length);
	            
	            // Read and display the response message sent by server application
	            String message = null;
	            in = new InputStreamReader(socket.getInputStream());
	            char[] cbuf = new char[10];
	            if( in.read(cbuf) > 0 ) {
	            	message = new String(cbuf);
	            }
	            
	            System.out.println("[Client] Recv Data::[" + message.length() + "]" + message);
	            
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
}