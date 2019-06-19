import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
public class MontecarloPar {
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
    public MontecarloPar(int numeroPontos) {
        this.nAtomSuccess = new AtomicInteger(0);
        this.nThrows = numeroPontos;
        this.value = 0;
    }
    public double getPi() {
        int nProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
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

    public static void main(String[] args) {
        Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroPontos = scannerObj.nextInt();
        System.out.println("Nº Pontos : " + numeroPontos);  // Output user input
        System.out.print("Informe a número máx de threads:");
        int numetothreads = scannerObj.nextInt();
        Thread[] listathreads = new Thread[numetothreads];

        System.out.println("Nº threads : " + listathreads);

        System.out.println("Numero de processadores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Vamos calcular - Formula Professor");
        long inicio = System.nanoTime();
        MontecarloPar PiVal = new MontecarloPar(numeroPontos);

        long startTime = System.currentTimeMillis();
        double value = PiVal.getPi();
        long stopTime = System.currentTimeMillis();
        System.out.println("Approx value:" + value);
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        long fim = System.nanoTime();
        System.out.println("Time Duration: " + (stopTime - startTime)*0.001 + "s");
    }
}