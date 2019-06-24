
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 자식 클래스를 생성하여 사용할 수 있다.
		Doctor doc = new Doctor();
		doc.whoAmI();		
		System.out.println(doc.name);
		
		// 만약 부모인  Person를 상속받아 처리해야 하는 클래스가 또 필요한 경우,
		// 매번 자식 클래스를 만드는 것은 낭비고 불필요한 클래스만 많아진다.
		// 상속받은 클래스가 재사용되면 모를까, 그냥 한번 쓰고 버려진다면 굳이 클래스 파일을 만들 필요는 없다.
		// 이럴 경우에 바로 익명 클래스를 사용하면 된다. 자식 클래스의 개념이다.
		// 물론 Infterface도 이와 같이 구현될 수 있다.
		Person p = new Person() {
			public String name = "ssemdol";
			
			@Override
			public void whoAmI() {
				System.out.println("I'm Engineer");
			}
			
			public void work() {
				System.out.println(name + " is working");
			}
			
		};
		p.whoAmI(); // p.name, p.work() 접근되지 않는다. 부모클래스로 인스턴스를 생성했기에 부모클래스의 필드나 메소드만 접근이 가능하다.
		

		
		
	}

}
