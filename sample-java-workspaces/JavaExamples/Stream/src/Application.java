import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<String> list = Arrays.asList("Apple","Banana","Grape");
		list.stream().forEach(a -> System.out.println(a));

		int sum = IntStream.of(1, 3, 5, 7, 9)
				  .peek(System.out::println)
				  .sum();
		System.out.println(sum);
		
		
		List<String> fruit = Arrays.asList("Apple","Banana","Grape");
		List<String> result = fruit.stream()
		.filter(a -> a.contains("B"))
		.map(String::toUpperCase)
		.collect(Collectors.toList());
		System.out.println(result);
		
		
		
	}

}
