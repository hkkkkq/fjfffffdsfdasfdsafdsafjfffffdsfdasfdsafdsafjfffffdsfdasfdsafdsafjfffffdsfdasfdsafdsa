import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class FileEncodingTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File f = new File("test_messasge_log.log");
		FileOutputStream fis = null;
		OutputStreamWriter osw1 = null;
		OutputStreamWriter osw2 = null;
		PrintWriter pw1 = null;
		PrintWriter pw2 = null;
		
		try {
		
			fis = new FileOutputStream(f);
			osw1 = new OutputStreamWriter(fis,"UTF-8");
			osw2 = new OutputStreamWriter(fis,"EUC-KR");
			//BufferedWriter bw = new BufferedWriter(osw);
			pw1 = new PrintWriter(osw1,true);
			pw2 = new PrintWriter(osw2,true);
			
			pw1.println("안녕하세요전원호입니다");
//			pw1.println("안녕하세요짱짱짱입니다");
			pw2.println("안녕하세요정재훈입니다");
			
			pw1.close();
			osw1.close();
			pw2.close();
			osw2.close();
			fis.close();
				
		} catch (Exception e) {
			System.out.println("*** Exception ****");
			e.printStackTrace();
			
		} finally {
			if (pw1 != null)  { try{pw1.close(); pw1 = null;}   catch (Exception e){e.printStackTrace();}}
			if (osw1 != null) { try{osw1.close(); osw1 = null;} catch (Exception e){e.printStackTrace();}}
			if (pw2 != null)  { try{pw2.close(); pw2 = null;}   catch (Exception e){e.printStackTrace();}}
			if (osw2 != null) { try{osw2.close(); osw2 = null;} catch (Exception e){e.printStackTrace();}}
			if (fis != null) { try{fis.close(); fis = null;} catch (Exception e){e.printStackTrace();}}
		}
		
		
		
		
	}

}
