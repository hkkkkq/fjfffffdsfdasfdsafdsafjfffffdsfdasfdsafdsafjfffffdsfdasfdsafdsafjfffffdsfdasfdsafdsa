import java.lang.reflect.Array;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


public class String_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//ArrayUtils.isNotEmpty(array)
		
		String a = "";
		String b = null;
		
		System.out.println(StringUtils.isEmpty(a));
		
		//String[] c = {"","",""};
		String[] c = {};
		
		System.out.println(ArrayUtils.isEmpty(c));
		
		

	}

}
