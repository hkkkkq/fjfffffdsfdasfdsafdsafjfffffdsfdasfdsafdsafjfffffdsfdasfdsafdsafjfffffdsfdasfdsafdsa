package tcp;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocket_INN_9002 {
    private ServerSocket server;
    private int port = 9004;

    public ServerSocket_INN_9002() {
        try {
            server = new ServerSocket(port);
            System.out.println("[Server] Listen Port::" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocket_INN_9002 example = new ServerSocket_INN_9002();
        example.handleConnection();
    }

    public void handleConnection() {

        // The server do a loop here to accept all connection initiated by the
        // client application.
        while (true) {
            try {
                Socket socket = server.accept();
                new ConnectionHandler_INN_9002(socket);
                System.out.println("[Server] Accept::" + socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class ConnectionHandler_INN_9002 implements Runnable {
    private Socket socket;

    public ConnectionHandler_INN_9002(Socket socket) {
        this.socket = socket;

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
    	
    	InputStreamReader in = null;
    	PrintWriter out = null;
    	
        try {

        	in = new InputStreamReader(socket.getInputStream());
        	out = new PrintWriter(socket.getOutputStream(), true);
        	
        	while( true ) {
        		
        		// Read a message sent by client application
        		String message = null;
        		
        		char[] cbuf = new char[320]; //서버에서 31자리(dto길이) + ;(딜리미터) 보내줌 
        		if( in.read(cbuf) > 0 ) {
        			message = new String(cbuf);
        		}
        		System.out.println("[Server] Recv Data::[" + message.length() + "]" + message);
        		
        		
        		boolean isWrite = true;
	            boolean isSleep = true;
	            long sleepTime = 2000;
	            
	            if( isSleep ) {
		            try {
		            	System.out.println("[Server] Sleep Time::" + sleepTime + "(ms)");
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }
	            
	            if( isWrite ) {
		            // LENTH 30
		            String replyData = "11111111112222222222333333339;";
		            out.write(replyData);
		            out.flush();
		            System.out.println("[Server] Reply Data");
	            }
	            
	            System.out.println("[Server] To be continuted.....");
        	}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        	out.close();
        	
        	try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}