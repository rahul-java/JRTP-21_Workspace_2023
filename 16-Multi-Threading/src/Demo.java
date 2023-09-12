public class Demo implements Runnable {

	public void run(){

		System.out.println("Hi...");
	}
	
	public static void main(String[] args) {
		
		Demo demo = new Demo();
		Thread t1=new Thread(demo);
		t1.start();
		
		Thread t2=new Thread(demo);
		t2.start();
		
		//Lambda Expression
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {

				System.out.println("Hello");
			}
		};
		//runnable.run();
		
		Thread t3=new Thread(runnable);
		t3.start();
	}

}
