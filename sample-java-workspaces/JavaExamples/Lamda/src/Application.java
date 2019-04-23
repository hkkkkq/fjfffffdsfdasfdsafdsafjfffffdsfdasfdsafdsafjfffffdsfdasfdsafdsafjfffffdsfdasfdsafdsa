import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Application {
	

	public static void main(String[] args) {

			
		// 익명 클래스
		Compare max1 = new Compare() {
			
			@Override
			public int compareTo(int value1, int value2) {
				// TODO Auto-generated method stub
				return value1 > value2 ? value1 : value2;
			}
		};
		System.out.println(max1.compareTo(8, 5));
		
		
		// 함수형 인터페이스
		Compare max2 = (x, y) -> x > y ? x: y;
		System.out.println(max2.compareTo(7, 4));
		
		
		// java.util.function 패키지에는 유용한 FunctionalInterface 존재	
		Predicate<String> isEmptyStr = s -> s.length() == 0;
		String s = "";

		if(isEmptyStr.test(s))
		  System.out.println("This is an empty String.");
		
		
		// 람다식이 하나의 메서드만 호출하는 경우, 메서드 참조를 통해 람다식을 간략히 할 수 있다.
		// 	클래스명::메서드명 또는 참조변수::메서드명
		
		// 기존
		//Function<String, Integer> f = (String x) -> Integer.parseInt(x);
		//Function<Integer, int[]> j = x -> new int[x];
		

		// 메서드 참조
		Function<String, Integer> f = Integer::parseInt;
		Function<Integer, int[]> j = int[]::new;
		
		System.out.println(f.apply("10"));
		

	}

}
