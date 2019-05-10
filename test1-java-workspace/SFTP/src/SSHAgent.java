import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/* javac -cp ./ganymed-ssh2-build210.jar SSHAgent.java
 * java -cp .:/home/infadm/sftp_test/ganymed-ssh2-build210.jar SSHAgent
 */
public class SSHAgent {

    private String hostname;
    private String username;
    private String password;
    private Connection connection;
    private int port;

    public SSHAgent( String hostname, String username, String password, int port )
    {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port     = port;
    }

    public boolean connect() throws Exception
    {
        try
        {
            // Connect to the server
            connection = new Connection( hostname, port );
            connection.connect();

            // Authenticate
            boolean result = connection.authenticateWithPassword( username, password );
            System.out.println( "Connection result: " + result );
            return result;
        }
        catch( Exception e )
        {
            throw new Exception( "An exception occurred while trying to connect to the host: " + hostname + ", Exception=" + e.getMessage(), e );
        }
    }

    public String executeCommand( String command ) throws Exception
    {
        try
        {
            // Open a session
            Session session = connection.openSession();

            // Execute the command
            session.execCommand( command );

            // Read the results
            StringBuilder sb = new StringBuilder();
            InputStream stdout = new StreamGobbler( session.getStdout() );
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            String line = br.readLine();
            while( line != null )
            {
                sb.append( line + "\n" );
                line = br.readLine();
            }

            // DEBUG: dump the exit code
            System.out.println( "ExitCode: " + session.getExitStatus() );

            // Close the session
            session.close();

            // Return the results to the caller
            return sb.toString();
        }
        catch( Exception e )
        {
            throw new Exception( "An exception occurred while executing the following command: " + command + ". Exception = " + e.getMessage(), e );
        }
    }

    public void logout() throws Exception
    {
        try
        {
            connection.close();
        }
        catch( Exception e )
        {
            throw new Exception( "An exception occurred while closing the SSH connection: " + e.getMessage(), e );
        }
    }

    public boolean isAuthenticationComplete()
    {
        return connection.isAuthenticationComplete();
    }

    public static void main( String[] args )
    {
        try
        {
        	String ip = "172.29.213.115";
    		int port = 22;
    		String id = "ktcorp";
    		String pw = "ktcorp12!";
    		
    		
            SSHAgent sshAgent = new SSHAgent( ip, id, pw, port );
            
            System.out.println("start connect");
            
            if( sshAgent.connect() )
            {
                String diskInfo = sshAgent.executeCommand( "df -k" );
                System.out.println( "Disk info: " + diskInfo );

                String processInfo = sshAgent.executeCommand( "top -b -n 1" );
                System.out.println( "Process Info: " + processInfo );

                // Logout
                sshAgent.logout();
            }
            
            System.out.println("end connect");
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}