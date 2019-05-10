
public class RECEIVER {
	
	private static RECEIVER obj = null;
	
	public RECEIVER() {
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbb");
	}
	
	public static synchronized RECEIVER getInstance() {

		if (obj == null) {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			obj = new RECEIVER();
		}
		return obj;
	}
	
	public void setMnpConfigProperty(){
	
		System.out.println("ccccccccccccccccccccccccccc");
	}

}
