import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
			
		Semaphore s = new Semaphore(2);
		
		for(int i = 0; i < 10; i++) {
			
			new T(i, s).start();;
		}
	}
	
	public static class T extends Thread {
		
		int i;
		Semaphore s;
				
		public T(int i, Semaphore s) {
			
			this.i = i;
			this.s = s;		
		}
		
		public void run(){
			
			try {
				
				s.acquire();
				System.out.println("************** " + i);
				System.out.println("* " + s.availablePermits());
				this.sleep(1000);
				
				s.release();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
