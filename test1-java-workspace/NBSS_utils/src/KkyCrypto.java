import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import javax.crypto.Cipher;
import com.tmax.infinilink.common.util.Base64;
import com.tmax.infinilink.common.util.security.Crypto;

public class KkyCrypto extends Crypto {

	private static Key getKey256()  throws Exception
	{
		if (key256 != null) {
			return key256;
		}
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(getInputStream(aes256KeyFileURL));
			key256 = (Key)in.readObject();
		}
		finally
		{
			if (in != null) {
				in.close();
			}
		}
		return key256;
	}
	
	public static String encrypt(String str) throws Exception
	{
		return encrypt(KeyType.AES256, str);
	}

	public static String encrypt(KeyType type, String source) throws Exception
	{
		if (type == null) {
			return encrypt(source);
		}
		if ((source == null) || (source.length() == 0)) {
			return "";
		}
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		Key  key = getKey256();
		cipher.init(1, key);
		String amalgam = source;
		byte[] inputBytes1 = amalgam.getBytes("UTF8");
		byte[] outputBytes1 = cipher.doFinal(inputBytes1);
		return new String(Base64.encode(outputBytes1));
	}
	
	public static String decrypt(String str) throws Exception
	{
		return decrypt(KeyType.AES256, str);
	}

	public static String decrypt(KeyType type, String source) throws Exception
	{
		if (type == null) {
			return decrypt(source);
		}
		if ((source == null) || (source.length() == 0)) {
			return "";
		}
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		Key  key = getKey256();
		cipher.init(2, key);

		byte[] inputBytes1 = Base64.decode(source);
		byte[] outputBytes2 = cipher.doFinal(inputBytes1);
		return new String(outputBytes2, "UTF8");
	}


	private static InputStream getInputStream(String name) throws Exception
	{
		return KkyCrypto.class.getResourceAsStream("/infinilink256.key");
	}

	public static void main(String[] args) 
	{
		try 
		{
			String str1 = "infadm";
			String encStr = KkyCrypto.encrypt(str1);
			System.out.println("encStr="+encStr);

			String str2 = "SAyr5TvIwviFPvb+2i8L3g==";
			String decStr = KkyCrypto.decrypt(str2);

			System.out.println("decStr="+decStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
