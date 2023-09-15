package TryWithoutResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Demo1 {

	public static void main(String[] args) {

		FileReader fileReader=null;
		
		try {
			
			fileReader =new FileReader(new File("abc.txt"));
			int read=fileReader.read();
			System.out.println("Read data : "+read);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			
			if(fileReader!=null)
			{
				try {
					fileReader.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}

}
