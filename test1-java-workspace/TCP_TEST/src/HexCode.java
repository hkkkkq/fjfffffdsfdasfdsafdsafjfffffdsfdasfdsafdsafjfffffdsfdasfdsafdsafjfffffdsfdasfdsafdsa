
public class HexCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(getDectoHex("225"));
		System.out.println("\n");
		System.out.println(System.getProperty("line.seperator"));
		System.out.println(getHextoDec("E1"));
		
		System.out.println("\n");
		
		System.out.println("aaaaaaaaaaaaaaaa	bbbbbbbbbbb");
		System.out.println("aaaaaaaaaaaaaaaa\tbbbbbbbbbbb");
		
	}
	
	private static String getHextoDec(String hex) {
		
		long v = Long.parseLong(hex, 16);
		return String.valueOf(v);
	}
	
	private static String getDectoHex(String dec) {
		Long intDec = Long.parseLong(dec);
		return Long.toHexString(intDec).toUpperCase();
	}

}
