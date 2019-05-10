
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSocketClient4345_2 {
    /**
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
    	
    	String ip = "211.189.16.13";
//    	String ip = "10.225.137.132";
//    	int port = 9025;
    	int port = 8201;
//    	int port = 9033; // Hongkong Roaming TEST

    	
//    	String sendData1 = "0015111112222233333\n";
    	String sendData1 = "1344N201709141322590000002001AO   N                                                                                                         SA12000    0000129585000000001               000000003K7003253                                                   SM-G930KSRSR                                                 20170913           SA12000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ";
//    	String sendData1 = "0015000112222233333"; // Hongkong Roaming TEST
    	

    	Socket socket = null;
    	OutputStreamWriter out = null;
    	InputStreamReader in = null;
    	BufferedReader br = null;
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
	            System.out.println("[Client] Send Data(" + DateyyyyMMddHHmmssSSS() + "): " + sendData1.length());
	            
	            // Read and display the response message sent by server application
	            String message = null;
	            StringBuffer sb = new StringBuffer("");
	            in = new InputStreamReader(socket.getInputStream());
	            br = new BufferedReader(in);
	            
	            int a = 0;
	            
	            while( (a = br.read()) >  -1) {
//	            	System.out.println((char)a);
	            	sb.append((char)a);
	            }
	            
	            message = sb.toString();
	            
//	            System.out.println(sb.toString());
	            System.out.println("[Client] Recv Data(" + DateyyyyMMddHHmmssSSS() + "): [" + message.length() + "]" + message);
	            
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
    public static String DateyyyyMMddHHmmssSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
    }
}