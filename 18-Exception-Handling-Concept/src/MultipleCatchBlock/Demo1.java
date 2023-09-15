package MultipleCatchBlock;

public class Demo1 {

	public static void main(String[] args) {
		
		try {
			
			int a = 10/0;
			
		} catch (Exception e) {

			System.out.println("Catch-1 :");
		} 
	/*	catch (ArithmeticException a) {
			
			System.out.println("Catch-2");
		}
		*/
		
		//valid code in demo1
	}
}
