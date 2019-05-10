import java.text.ParseException;

import javax.swing.text.MaskFormatter;


public class MASKFORMAT_TEST {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		
		MaskFormatter mf = new MaskFormatter("***-****");
		
		System.out.println(mf.getMask());

		mf.setValueContainsLiteralCharacters(false);
//		System.out.println(mf.stringToValue("a-b-c"));
		
		mf.setPlaceholderCharacter('_');
		System.out.println(mf.stringToValue("123-4567"));
		System.out.println(mf.valueToString("123456"));
		//System.out.println(mf.stringToValue("123"));
		
		
		System.out.println(Character.isLowerCase('L'));
		System.out.println(Character.isLowerCase('l'));
		
		
		
		
		

	}

}
