import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
public class MontecarloPar {
    static AtomicInteger nAtomSuccess;
    int nThrows;
    double value;
    private final static int ITERATIONS = 10;
    public static double x = 0;
    public static double y = 0;
    public static int nSuccess = 0;
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

    public static double formMontecarloSeq(long numeroPontos) {
        for (long i = 1; i <= numeroPontos; i++) {
            x = Math.random();
            y = Math.random();
            if (x * x + y * y <= 1)
                nSuccess++;
        }
        return (4.0* nSuccess / numeroPontos);
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

        long startTime = System.currentTimeMillis();
        double value = PiVal.getPi(numetothreads);
        long stopTime = System.currentTimeMillis();
        System.out.println("π com concorrencia: " + value);
        long totalTime = stopTime - startTime;
        System.out.println("Calculo com concorrencia demorou demorou (secs): "  + String.format("%.6f", (totalTime)/1.0e9) );

        long startTimeSemConc = System.nanoTime();
        double result = formMontecarloSeq(numeroPontos);
        long stopTimeSemConc = System.nanoTime();
        System.out.println("π sem concorrencia: " + result);
        long totalTimeSemConc = stopTime - startTime;
        System.out.println("Calculo sequencial demorou (secs): "  + String.format("%.6f", (totalTimeSemConc)/1.0e9) );

        double divisao = (double) totalTime / (double) totalTimeSemConc;
        double ganho = (divisao) * 100;
        System.out.println("ganho % – TCC/TSC * 100 = " + ganho + " %");


        long start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; ++i) {
            PiVal.getPi(numetothreads);
        }
        long elapsed = System.currentTimeMillis() - start;
        long average = elapsed / ITERATIONS;
        System.out.println("Para " + ITERATIONS + "iteracoes" + "o tempo médio foi: " + String.format("%.6f", (average)/1.0e9) + "s");

    }
}