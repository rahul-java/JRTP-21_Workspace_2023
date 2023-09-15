package UserDefinedException;

public class Demo {
	
	public static void main(String[] args) throws NoDataFoundException {
		
		Demo demo = new Demo();
		demo.getData(101);
	}
	
	public void getData(Integer no) throws NoDataFoundException
	{
		if(no<=100)
		{
			System.out.println("Getting Data from DB is Valid");
		}
		else {
			throw new NoDataFoundException("Invalid Data ");
		}
	}

}
