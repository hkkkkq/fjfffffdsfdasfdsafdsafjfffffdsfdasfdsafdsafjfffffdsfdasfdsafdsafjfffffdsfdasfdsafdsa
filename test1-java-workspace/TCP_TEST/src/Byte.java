
public class Byte {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
//		byte a;
//		byte b;
//		
//		a = 'a';
//		b = 123;
//		
//		System.out.println(a);
//		System.out.println(b);
		
		
//		String inputString = "GCRM0000        ";
//		System.out.println(inputString);
//		
//		byte[] inputBytes = inputString.getBytes();
//		System.out.println(new String(inputBytes));
//			
//		for (byte b:inputBytes) {
//			System.out.print(b);
//		}
//		System.out.println("");
//		
//		
//		String hexString = new java.math.BigInteger(inputBytes).toString(16);
//		System.out.println(hexString);
//		
//		System.out.println("");
//		
//		int r = 16;
//		byte[] r1 = intToByte(r);
//		
//		for (byte b:r1) {
//			System.out.println(b);
//		}
//
//		System.out.println("");
//		System.out.println(new String(r1));
//		
//		int r2 = byteToInt(r1);
//		System.out.println(r2);
//		
//		byte[] byte1 = new byte[] {0,0,0,16};
//		System.out.println(new String(byte1));
//		System.out.println(byteToInt(byte1));
//			
		String a = "1111111111";
		
		for (byte b : a.getBytes()) {
			System.out.print(b);
		}
		
	}
	
	public static byte[] intToByte(int n) {
		byte[] bytes = new byte[] {
			(byte)(n>>24)
			,(byte)(n>>16)
			,(byte)(n>>8)
			,(byte)(n)
		};
		return bytes;
	}
	
	public static int byteToInt(byte[] bytes) {
		int n = (bytes[0]<<24 & 0xffffffff) | (bytes[1]<<16 & 0xffffff) | (bytes[2]<<8 & 0xffff) | (bytes[3] & 0xff);
		return n;
	}

}
