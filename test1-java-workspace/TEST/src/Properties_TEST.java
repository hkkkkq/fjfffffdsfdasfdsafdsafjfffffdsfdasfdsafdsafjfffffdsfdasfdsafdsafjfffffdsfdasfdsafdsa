import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;


public class Properties_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Properties prop = new Properties();
		
		prop = readProperties("E:\\test\\test1.properties");
		
		Enumeration<?> e = prop.propertyNames();
		
		while (e.hasMoreElements()) {
			String key = (String)e.nextElement();
			String value = prop.getProperty(key);
			String[] values = value.split("\\,", 4);			
			
			System.out.println("#### key: " + key + " ####");
			
			for (String i:values) {
				System.out.println("#### value: " + i + " ####");
			}
			
		}

	}
	
	public static Properties readProperties(String filePath) {
		
		Properties props = new Properties();
		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			props.load(br);
		   
			br.close(); br=null;
			isr.close(); isr=null;
			fis.close(); fis=null;
								
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("######## System Variables Properties FileNotFoundException #######");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("######## System Variables Properties Exception #######");
			e.printStackTrace();
		}
		finally {
			if(br!=null)  { try {br.close(); br = null;} catch (IOException e) { e.printStackTrace();} }
			if(isr!=null) { try {isr.close(); isr = null;} catch (IOException e) { e.printStackTrace();}  }
			if(fis!=null) { try {fis.close(); fis = null;} catch (IOException e) { e.printStackTrace();}  }
		}
		
		return props;
	}
}
