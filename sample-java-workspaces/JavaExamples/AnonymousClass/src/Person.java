public class Person {

	void  whoAmI() {
		System.out.println("나는 Person이다.");
	}
}

class Doctor extends Person {
	
	String name = "herasoo";
	
	@Override
	void whoAmI() {
		System.out.println("I'm Doctor");
	}
}