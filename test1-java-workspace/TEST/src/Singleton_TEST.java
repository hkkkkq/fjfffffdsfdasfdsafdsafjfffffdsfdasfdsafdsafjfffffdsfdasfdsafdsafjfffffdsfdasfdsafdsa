
public class Singleton_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Singleton singleton1 = Singleton.getInstance();
		Singleton singleton2 = Singleton.getInstance();
		
		System.out.println(singleton1.equals(singleton2));

	}

}

class Singleton {
	
	static Singleton singleton;
	
	static int count = 0;
	
	private Singleton() {
		
		count++;
		System.out.println(count);
		
	}
	
	public static Singleton getInstance() {
		
		if (singleton == null) {
			singleton = new Singleton();
		}
		
		return singleton;
		
		
	}
	
}
