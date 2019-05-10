import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Run_Shell {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String cmd = args[0];
		
		//case1
		Runtime runtime = Runtime.getRuntime();
		
		Process process = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		StringBuffer sb = new StringBuffer("");
		
		try {
			process = runtime.exec(cmd);
			
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			String line;
			
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				sb.append(line);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
				
				System.out.println(sb.toString());
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		

		//		//case2
//		ProcessBuilder pb = new ProcessBuilder();
//		pb.redirectErrorStream(true);
//
//		Process process = pb.start();
		


	}

}
