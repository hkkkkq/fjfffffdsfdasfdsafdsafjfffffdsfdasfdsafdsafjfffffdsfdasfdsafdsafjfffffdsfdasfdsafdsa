
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class IOT_HyunCar_TCP_Client2 {
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
    	String sendData1 = "1111111111";
//    	String sendData1 = "0015000112222233333"; // Hongkong Roaming TEST
    	
//    	StringBuffer sb = new StringBuffer();
//    	
//    	sb.append("POST /crm/services/svc6000ProviderWS HTTP/1.1\n");
//    	sb.append("Content-Type: text/xml; charset=utf-8\n");
//    	sb.append("User-Agent: Java1.7.0_71\n");
//    	sb.append("Host: 203.229.169.148:46013\n");
//    	sb.append("Accept: text/html, image/gif, image/jpeg, */*; q=.2\n");
//    	sb.append("Connection: Keep-Alive\n");
//    	sb.append("Content-Length: 752\n");
//    	sb.append("\n");
//    	sb.append("\n");
//    	sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//    	sb.append("<soapenv:Envelope\n");
//    	sb.append("    xmlnsoapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
//    	sb.append("    <soap:Header\n");
//    	sb.append("        xmlnsoap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
//    	sb.append("    </soap:Header>\n");
//    	sb.append("    <soapenv:Body>\n");
//    	sb.append("        <svc:UseQntRetvRequest\n");
//    	sb.append("            xmlns:tms=\"http://xmlns.hyundai.com/data/TMSCommon\"\n");
//    	sb.append("            xmlns:com=\"http://www.kt.com/m2m/commonHeader\"\n");
//    	sb.append("            xmlnsvc=\"http://www.kt.com/m2m/domain/svc6000Provider\">\n");
//    	sb.append("            <commonHeader>\n");
//    	sb.append("                <com:CMPN_CD>HMCM2M</com:CMPN_CD>\n");
//    	sb.append("                <com:MSG_ID>20160726150807659</com:MSG_ID>\n");
//    	sb.append("                <com:MSG_TYPE>6000</com:MSG_TYPE>\n");
//    	sb.append("                <com:USER_ID>ws1234</com:USER_ID>\n");
//    	sb.append("                <com:CH_TYPE>W</com:CH_TYPE>\n");
//    	sb.append("            </commonHeader>\n");
//    	sb.append("            <svcUseQntRetvRequest>\n");
//    	sb.append("                <SVC_CONT_NO>980004508</SVC_CONT_NO>\n");
//    	sb.append("            </svcUseQntRetvRequest>\n");
//    	sb.append("        </svc:UseQntRetvRequest>\n");
//    	sb.append("    </soapenv:Body>\n");
//    	sb.append("</soapenv:Envelope>\n");
//    	
//    	sendData1 = sb.toString();

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
	            
	            out.write(sendData1.getBytes());
	            
	            System.out.print("[Client] Send Data: ");
	            for (byte b: sendData1.getBytes()) {
	            	System.out.print(b);
	            }
	            System.out.println("");
	        
	            out.flush();
	            System.out.println("[Client] Send Data Length: " + sendData1.length());
	            
	            // Read and display the response message sent by server application
	            String message = null;
	            in = new InputStreamReader(socket.getInputStream());
	            char[] cbuf = new char[10];
	            if( in.read(cbuf) > 0 ) {
	            	message = new String(cbuf);
	            }
	            
	            System.out.println("[Client] Recv Data::[" + message.length() + "]" + message);
	            
	            socket.close();
	
            } else {
            	System.out.println("[Client] Send Data");
            	System.out.println(sendData1);
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