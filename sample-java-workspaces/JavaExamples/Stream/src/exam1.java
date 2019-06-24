import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class exam1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String[] strArray = {"a2","a1","b1","c1","c3","c2","e1","d2","a3"};
		
		List<String> list = Arrays.asList(strArray);
		
		Stream<String> stream = list.stream();
		
		//stream.forEach(a->System.out.println(a));
		//stream.forEach(System.out::println);
		//stream.filter(s -> s.startsWith("a")).forEach(System.out::println);
		
		stream.map(s -> s.toUpperCase()).sorted().forEach(System.out::println);

	}

}
