import java.util.*;

public class HW3 {
	// Метод для підрахунку кількості кроків за гіпотезою Колатца
	public static int collatzSteps(long n) {
		int steps = 0;
		while (n != 1) {
			if (n % 2 == 0) {
				n /= 2; // Для парних n = n / 2
			} else {
				n = 3 * n + 1; // Для непарних n = 3n + 1
			}
			steps++;
		}
		return steps;
	}

	public static void main(String[] args) {
		int maxNumber = 10_000_000; // Верхня межа 
		int k = 8; // Кількість потоків вручну

		try {
			compute(k, maxNumber);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void compute(int k, int maxNumber) throws InterruptedException {
		Thread[] threads = new Thread[k]; // Масив потоків
		long[] results = new long[k];     // Масив результатів
    	int stepsPerThread = maxNumber / k; // Кількість чисел на один потік

    	for (int i = 0; i < k; i++) {
    		int index = i;
    		int from = i * stepsPerThread + 1;
    		int to = (i == k - 1) ? maxNumber : (i + 1) * stepsPerThread;

    		threads[i] = new Thread(() -> {
    			long localSum = 0;
    			for (int n = from; n <= to; n++) {
    				localSum += collatzSteps(n);
    			}
    			results[index] = localSum;
    		});

    		threads[i].start(); // Запускаємо поточний потік на виконання
    	}

    	for (Thread t : threads) {
    		t.join(); // Очікуємо завершення всіх потоків
    	}

    	long totalSteps = 0;
    	for (long r : results) {
    		totalSteps += r;
    	}

    	double averageSteps = (double) totalSteps / maxNumber; // Середня кількість кроків

    	System.out.printf(Locale.US,
    			"Потоків: %d; Середня кількість кроків: %.5f",
    			k, averageSteps);
	}	
}
