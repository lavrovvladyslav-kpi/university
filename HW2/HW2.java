package HW2;

import java.util.*;

public class HW2 {
	   public static void main(String[] args) {
		   	int count = 1_000_000; //усього точок
		   	int[] threadCounts = {1, 2, 4, 8, 16, 32, 64};
		   	
			System.out.println("Справжнє значення pi: " + Math.PI);
		   	
		   	for (int t_count : threadCounts) {
			   	try {
					run(t_count, count);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		   	}
	   }
	   
	   public static void run(int k, int count) throws InterruptedException {
													// виняток, якщо потік буде перервано під час join()
		   Thread[] threads = new Thread[k]; // Потоки
		   int[] results = new int[k]; // Результати потоків
		   int steps = count / k; // Cкільки точок у кожного потоку
		   // int remainder = count % k;

		   long start = System.nanoTime(); //Початок заміру
		   
		   for (int i = 0; i < k; i++) {
			   int index = i;
			   threads[i] = new Thread(() -> {
				   	int inside_count = 0;
				   	Random random = new Random();
				   	
				   	for (int j = 0; j < steps; j++) {
				   		double x = random.nextDouble();
				   		double y = random.nextDouble();
				   		if (Math.pow(x, 2) + Math.pow(y, 2) <= 1)
				   			inside_count++;
				   	}
				   	
				   	results[index] = inside_count;
			   });

			   threads[i].start(); // Запускаємо поточний потік на виконання
		   }

		   for (Thread t : threads) {
			   t.join(); // Очікуємо завершення всіх потоків
		   }
		   
		   long end = System.nanoTime(); //Кінець заміру
		   
		   int total_count = 0;
		   for (int r : results) {
			   total_count += r; // Очікуємо завершення всіх потоків
		   }
		   
		   double pi = 4.0 * total_count / count; // Потрібен хоча б один операнд double, щоб ділення виконувалося з плаваючою крапкою
		   double diff = Math.abs(pi - Math.PI); // Різниця по модулю
		   double millis = (end - start) / 1_000_000.0;
		   
		   System.out.printf(Locale.US, "Потоків: %2d; pi = %f; різниця зі справжнім значенням: %.8f; час = %.3f мс%n", k, pi, diff, millis);
	   }
}