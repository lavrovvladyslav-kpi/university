public class HW1 {
	   public static void main(String[] args) {
	        Thread t1 = new Thread(() -> run(1, 10, "t1"));
	        Thread t2 = new Thread(() -> run(11, 15, "t2"));

	        t1.start();
	        t2.start();
	        
	        try {
	        	t1.join();
	        	t2.join();
	        } catch (InterruptedException e){
	        	e.printStackTrace();
	        }
	        
	        System.out.println("All threads are finished");
	    }

	    private static void run(int start, int end, String name) {
	        long product = 1;
	        for (int i = start; i < end; i++) 
	            product *= i;
            try {
                Thread.sleep(2000); // пауза 2000 мс
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
	        System.out.println(name + " is done. Result = " + product);
	    }
}