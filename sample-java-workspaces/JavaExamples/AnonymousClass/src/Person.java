public class Person {

	void  whoAmI() {
		System.out.println("���� Person�̴�.");
	}
}

class Doctor extends Person {
	
	String name = "herasoo";
	
	@Override
	void whoAmI() {
		System.out.println("I'm Doctor");
	}
}