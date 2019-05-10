import java.io.File;


public class FileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String srcPath = "E:\\test\\fileBackupTest\\src";
		
		System.out.println("srcPath => " + srcPath);
		
		File srcFile = new File(srcPath);
		
		File[] files = srcFile.listFiles();
		
		System.out.println("exists? => " + srcFile.exists());
		System.out.println("isDirectory? => " + srcFile.isDirectory());
		
		System.out.println(files);
		System.out.println(files.length);
		

	}

}
