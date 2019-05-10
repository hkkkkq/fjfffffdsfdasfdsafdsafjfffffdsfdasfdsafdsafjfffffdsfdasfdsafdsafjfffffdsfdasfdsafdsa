
public class GC_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		byte test[] = new byte[1024];
		
		test[0] = 20;
		test[1] = 34;
		
		System.out.println("test=>" + test);
		
		Runtime r = Runtime.getRuntime();
		
		System.out.println("totalMemory=>" + r.totalMemory());
		System.out.println("freeMemory=>" + r.freeMemory());
		System.out.println("totalMemory-freeMemory=>" + (r.totalMemory()-r.freeMemory()));
		
		test = null;
		
		System.out.println("totalMemory-freeMemory=>" + (r.totalMemory()-r.freeMemory()));
		
		r.gc();
		//System.gc();
		
		System.out.println("totalMemory-freeMemory=>" + (r.totalMemory()-r.freeMemory()));
		
		
		
		
		
		
		

	}

}
