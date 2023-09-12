import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) throws Exception {
		
		Callable<Object> callable = new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				System.out.println("I am from thread.");
				return "Hi...";
			}
		};

		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(callable);
		executorService.submit(callable);
		
		//executorService.shutdown();
		
		executorService.awaitTermination(10, TimeUnit.SECONDS);
	}
}
