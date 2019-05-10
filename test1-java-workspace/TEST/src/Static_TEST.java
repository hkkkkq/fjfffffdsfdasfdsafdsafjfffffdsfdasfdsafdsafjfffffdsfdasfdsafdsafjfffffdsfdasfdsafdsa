
public class Static_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Counter c1 = new Counter();
		
		System.out.println(Counter.getCount());
		
		//Counter c2 = new Counter();
		
		System.out.println(Counter.getCount());;

	}
}

class Counter {
	
	static int count = 0;
	
	public Counter() {
		
		count++;
	}
	
	public static int getCount() {
		return count;
	}
	
}
