package tcp;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CopyOfServerSocket_Port_7777 {
    private ServerSocket server;
    private int port = 9001;

    public CopyOfServerSocket_Port_7777() {
        try {
            server = new ServerSocket(port);
            System.out.println("[Server] Listen Port::" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CopyOfServerSocket_Port_7777 example = new CopyOfServerSocket_Port_7777();
        example.handleConnection();
    }

    public void handleConnection() {

        // The server do a loop here to accept all connection initiated by the
        // client application.
        while (true) {
            try {
                Socket socket = server.accept();
                new ConnectionHandler2(socket);
                System.out.println("[Server] Accept::" + socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class ConnectionHandler2 implements Runnable {
    private Socket socket;

    public ConnectionHandler2(Socket socket) {
        this.socket = socket;

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
    	
    	InputStreamReader in = null;
    	PrintWriter out = null;
    	
        try {

        	in = new InputStreamReader(socket.getInputStream());
        	socket.getInputStream();
        	
        	while( true ) {
        		
        		// Read a message sent by client application
        		String message = null;
        		
        		char[] cbuf = new char[5000];
        		while( in.read(cbuf) > 0 ) {
        			System.out.println(new String(cbuf));
        		}
        		
        	}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        	try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}