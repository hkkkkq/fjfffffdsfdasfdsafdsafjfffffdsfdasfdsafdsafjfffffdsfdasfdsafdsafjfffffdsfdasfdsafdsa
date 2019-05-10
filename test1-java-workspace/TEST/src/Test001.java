import java.math.BigInteger;


public class Test001 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		try
//		{
//			System.out.println("SUCCESS");
//			String a = null;
//			a.length();
//			//System.exit(1);
//
//		}
//		catch (Exception e) 
//		{
//			System.out.println("ERROR");
//			e.printStackTrace();
//			//System.exit(1);
//		}
//
//		System.out.println("Finish");
		
		
		BigInteger bi = new BigInteger("16030100a30100009f03015797671183c04cc807f6f83d2725559839ef4ee6f158858d31679691525cfd1d000038c00ac0140035c005c00f00390038c009c013002fc004c00e00330032c008c012000ac003c00d00160013c007c0110005c002c00c000400ff0100003e000a0034003200170001000300130015000600070009000a0018000b000c0019000d000e000f001000110002001200040005001400080016000b000201001503010002020a", 16);
		
		//System.out.println(bi);
		
		byte[] b = bi.toByteArray();
		
		System.out.print("[Client] Send Data: ");
        for (byte c: b) {
        	System.out.print(c);
        }
        System.out.println("");
		
		
		
        String k = null;
        System.out.println(k+"^|"+"123");
	}

}
