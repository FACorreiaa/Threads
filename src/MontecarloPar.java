import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
public class MontecarloPar {
    static AtomicInteger nAtomSuccess;
    int nThrows;
    double value;
    private final static int ITERATIONS = 25;
    public static double x = 0;
    public static double y = 0;
    public static int nSuccess = 0;
    private  static long startTime = 0;
    private  static long stopTime = 0;
    static class MonteCarlo implements Runnable {
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
    public double getPi(int nProcessors) throws InterruptedException {
        //int nProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
        Thread.sleep(3000);

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
    //1200000000

    public static void  main(String[] args) throws InterruptedException{
        Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroPontos = scannerObj.nextInt();
        System.out.println("Nº Pontos : " + numeroPontos);  // Output user input
        System.out.print("Informe a número máx de threads:");
        int numetothreads = scannerObj.nextInt();
        System.out.println("Nº threads : " + numetothreads);
        //ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numetothreads);
        MontecarloPar PiVal = new MontecarloPar(numeroPontos);
        startTime = System.nanoTime();
        double value = PiVal.getPi(numetothreads);
        stopTime = System.nanoTime();
        System.out.println("π com concorrencia: " + value);
        long totalTime = stopTime - startTime;
        double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;
        System.out.println("Calculo sequencial demorou (secs): " + elapsedTimeInSecond );


        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; ++i) {
            MontecarloPar pipi = new MontecarloPar(numeroPontos);
            value = pipi.getPi(numetothreads);
        }
        long stop = System.nanoTime();
        long ttTime = stop - start;
        double ttTimeSeconds = ((double)ttTime/1_000_000_000) / ITERATIONS;
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio foi: " + ttTimeSeconds + "s");

    }
}