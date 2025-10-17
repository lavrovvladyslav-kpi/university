import java.util.*;
import java.util.stream.IntStream;

public class HW4 {
    // Метод для підрахунку кількості кроків за гіпотезою Коллатца
    public static int collatzSteps(long n) {
        int steps = 0;
        while (n != 1) {
            n = (n % 2 == 0) ? n / 2 : 3 * n + 1;
            steps++;
        }
        return steps;
    }

    public static void main(String[] args) {
        int maxNumber = 10_000_000; // Верхня межа чисел
        long start = System.nanoTime();

        // Паралельна обробка діапазону
        double averageSteps = IntStream.rangeClosed(1, maxNumber)
                .parallel() // Aвтоматичний розподіл по потоках
                .mapToLong(HW4::collatzSteps)
                .average()
                .orElse(0.0);

        long end = System.nanoTime();
        double millis = (end - start) / 1_000_000.0;

        System.out.printf(Locale.US,
                "Паралельний Stream; Середня кількість кроків: %.5f; Час = %.3f мс%n",
                averageSteps, millis);
    }
}
