import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
class PiMonteCarlo {
    AtomicInteger nAtomSuccess;
    int nThrows;
    double value;

    class MonteCarlo implements Runnable {
        @Override
        public void run() {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1)
                nAtomSuccess.incrementAndGet();
        }
    }
    public PiMonteCarlo(int i) {
        this.nAtomSuccess = new AtomicInteger(0);
        this.nThrows = i;
        this.value = 0;
    }
    public double getPi(int nProcessors) {
        //int nProcessors = Runtime.getRuntime().availableProcessors();
        //ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
        ExecutorService executor = Executors.newFixedThreadPool(nProcessors);
        for (int i = 1; i <= nThrows; i++) {
            Runnable worker = new MonteCarlo();
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        value = 4.0 * nAtomSuccess.get() / nThrows;
        return value;
    }
}
public class Assignment102 {
    public static void main(String[] args) {
        Scanner scannerObj = new Scanner(System.in);
        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroIteracoes = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroIteracoes);
        System.out.print("Informe a número máx de threads:");
        int numetothreads = scannerObj.nextInt();

        PiMonteCarlo PiVal = new PiMonteCarlo(numeroIteracoes);
        long startTime = System.currentTimeMillis();
        double value = PiVal.getPi(numetothreads);
        long stopTime = System.currentTimeMillis();
        System.out.println("Approx value:" + value);
        System.out.println("Difference to exact value of pi: " + (value - Math.PI));
        System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
    }
}