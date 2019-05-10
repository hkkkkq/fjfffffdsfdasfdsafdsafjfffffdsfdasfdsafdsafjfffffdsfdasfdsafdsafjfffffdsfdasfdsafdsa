import java.util.concurrent.atomic.AtomicReference;


public class AtomicReference_TEST {
	
	static AtomicReference<Integer> ar = new AtomicReference<Integer>(0);
	
	static int a = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for (int i=0; i<10; i++) {
				new Thread(new T()).start();

	}

	}
	
	static class T implements Runnable {
		

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			System.out.println(ar.getAndSet(ar.get()+1));
			
//			
//			System.out.println(a);
//			a++;

			
		}
		
	}

}
