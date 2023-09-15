package MultipleCatchBlock;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Demo3 {

	public static void main(String[] args) {
		
		try {
			
			int a = 10/0;
			
			// this can throw InputOutput Exception
			FileReader fileReader = new FileReader(new File("abc.txt"));
			
			//this can throw SQLException
			DriverManager.getConnection("url","username","pwd");
			
			
		}
		catch (IOException | SQLException | NullPointerException e) {
			
			System.out.println("Catch-1");
		} 
		
	}
}
