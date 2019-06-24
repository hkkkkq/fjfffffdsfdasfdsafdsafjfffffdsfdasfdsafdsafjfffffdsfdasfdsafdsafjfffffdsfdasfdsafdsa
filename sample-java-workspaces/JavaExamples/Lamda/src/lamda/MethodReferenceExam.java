package lamda;

import java.time.LocalDateTime;
import java.util.function.IntBinaryOperator;

public class MethodReferenceExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		IntBinaryOperator operator;
		
		operator = (x, y) -> Calculator.staticMethod(x, y);
        System.out.println("결과1: " + operator.applyAsInt(1, 5));
 
        operator = Calculator::staticMethod;
        System.out.println("결과2: " + operator.applyAsInt(5, 1));
 
        Calculator calc = new Calculator();
        
        operator = (x, y) -> calc.instanceMethod(x, y);
        System.out.println("결과3: " + operator.applyAsInt(4, 8));
 
        operator = calc::instanceMethod;
        System.out.println("결과4: " + operator.applyAsInt(8, 4));
       

	}

}
