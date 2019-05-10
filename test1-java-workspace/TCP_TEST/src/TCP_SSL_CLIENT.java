
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * complie - javac TCP_SSL_CLIENT.java
 * run     - java -Djavax.net.ssl.trustStore=/app/infinilink/ssl/sbx/client/truststore -Djavax.net.ssl.trustStorePassword=654321 TCP_SSL_CLIENT
 * debug option - -Djava.protocol.handler.pkgs=com.sun.net.ssl.internal.www.protocol -Djavax.net.debug=ssl
 * @author tmax
 *
 */
public class TCP_SSL_CLIENT {
    public static void main(String[] args) throws ClassNotFoundException {

        String ip = "10.217.136.62";
        int port = 14445;
        System.out.println("port="+port);
        
        int responseLength = 10;
        //total length = 100 ( header 50, body 50 )

        //commHeader
        String LENGTH = "1111111111";        //4

        StringBuffer sb = new StringBuffer();
                sb.append(LENGTH);

                String requestStr = sb.toString();

        SSLSocket socket = null;
        OutputStreamWriter out = null;
        InputStreamReader in = null;
        try {
        	
        	SSLSocketFactory sslfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        	socket = (SSLSocket)sslfactory.createSocket(ip, port);
        	
            // Create a connection to the server socket on the server application

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
                    out.write(requestStr);
                    out.flush();
                    System.out.println("[Client] Send Data::" + requestStr);

                    // Read and display the response message sent by server application
                    String message = null;
                    in = new InputStreamReader(socket.getInputStream());
                    char[] cbuf = new char[responseLength];
                    if( in.read(cbuf) > 0 ) {
                        message = new String(cbuf);
                    }

                    System.out.println("[Client] Recv Data::Len[" + message.length() + "]" +"  message=>"+ message);

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

