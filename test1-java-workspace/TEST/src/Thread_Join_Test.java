
public class Thread_Join_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("MAIN START!");
		
		Test t1 = new Test("1");
//		t1.setDaemon(true);
		System.out.println("aaa");
		
		Test t2 = new Test("2");
//		t2.setDaemon(true);
		System.out.println("bbb");
		
		Test t3 = new Test("3");
//		t3.setDaemon(true);
		System.out.println("ccc");
		
		t1.start();
		t2.start();
		t3.start();
		
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ddd");
		
		System.out.println("MAIN END!");
		
	}
	
	static class Test extends Thread {
		
		String a;
		
		public Test(String a){
			this.a = a;
		}
		
		public void run() {
			

			for (int i = 0; i < 10; i++) {
				try {
					
					System.out.println("["+a+"]"+Thread.currentThread().getName() + " implemented");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
