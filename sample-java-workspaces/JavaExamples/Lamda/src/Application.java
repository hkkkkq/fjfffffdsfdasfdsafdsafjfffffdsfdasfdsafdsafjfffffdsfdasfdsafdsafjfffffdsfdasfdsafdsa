import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Application {
	

	public static void main(String[] args) {

			
		// �͸� Ŭ����
		Compare max1 = new Compare() {
			
			@Override
			public int compareTo(int value1, int value2) {
				// TODO Auto-generated method stub
				return value1 > value2 ? value1 : value2;
			}
		};
		System.out.println(max1.compareTo(8, 5));
		
		
		// �Լ��� �������̽�
		Compare max2 = (x, y) -> x > y ? x: y;
		System.out.println(max2.compareTo(7, 4));
		
		
		// java.util.function ��Ű������ ������ FunctionalInterface ����	
		Predicate<String> isEmptyStr = s -> s.length() == 0;
		String s = "";

		if(isEmptyStr.test(s))
		  System.out.println("This is an empty String.");
		
		
		// ���ٽ��� �ϳ��� �޼��常 ȣ���ϴ� ���, �޼��� ������ ���� ���ٽ��� ������ �� �� �ִ�.
		// 	Ŭ������::�޼���� �Ǵ� ��������::�޼����
		
		// ����
		//Function<String, Integer> f = (String x) -> Integer.parseInt(x);
		//Function<Integer, int[]> j = x -> new int[x];
		

		// �޼��� ����
		Function<String, Integer> f = Integer::parseInt;
		Function<Integer, int[]> j = int[]::new;
		
		System.out.println(f.apply("10"));
		

	}

}
