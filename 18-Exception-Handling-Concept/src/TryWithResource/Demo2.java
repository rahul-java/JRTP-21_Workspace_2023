package TryWithResource;

import java.io.File;
import java.io.FileReader;

public class Demo2 {

	public static void main(String[] args) {

		try(FileReader fileReader=new FileReader(new File("abc.txt"))) {
			
			int read = fileReader.read();
			System.out.println(read);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
