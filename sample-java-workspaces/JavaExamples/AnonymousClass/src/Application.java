
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// �ڽ� Ŭ������ �����Ͽ� ����� �� �ִ�.
		Doctor doc = new Doctor();
		doc.whoAmI();		
		System.out.println(doc.name);
		
		// ���� �θ���  Person�� ��ӹ޾� ó���ؾ� �ϴ� Ŭ������ �� �ʿ��� ���,
		// �Ź� �ڽ� Ŭ������ ����� ���� ����� ���ʿ��� Ŭ������ ��������.
		// ��ӹ��� Ŭ������ ����Ǹ� �𸦱�, �׳� �ѹ� ���� �������ٸ� ���� Ŭ���� ������ ���� �ʿ�� ����.
		// �̷� ��쿡 �ٷ� �͸� Ŭ������ ����ϸ� �ȴ�. �ڽ� Ŭ������ �����̴�.
		// ���� Infterface�� �̿� ���� ������ �� �ִ�.
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
		p.whoAmI();
		// p.name, p.work() ���ٵ��� �ʴ´�. �θ�Ŭ������ �ν��Ͻ��� �����߱⿡ �θ�Ŭ������ �ʵ峪 �޼ҵ常 ������ �����ϴ�.
		

		
		
	}

}
