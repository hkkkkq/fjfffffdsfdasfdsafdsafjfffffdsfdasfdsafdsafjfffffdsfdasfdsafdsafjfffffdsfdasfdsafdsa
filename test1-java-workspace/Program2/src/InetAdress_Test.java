import java.net.InetAddress;
import java.net.UnknownHostException;


public class InetAdress_Test {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub

		InetAddress inetAddress = InetAddress.getLocalHost();
		
		System.out.println(inetAddress.getHostAddress());
		System.out.println(inetAddress.getHostName());
		
		
	}

}
