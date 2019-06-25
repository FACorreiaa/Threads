import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Monte_Carlo {
    static AtomicInteger nAtomSuccess;
    int nThrows;
    double value;
    private final static int ITERATIONS = 10;
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
    public Monte_Carlo(int numeroPontos) {
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
        return (4.0 * nSuccess / numeroPontos);
    }

    public static void  main(String[] args) throws InterruptedException{
        Scanner scannerObj = new Scanner(System.in);

        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroPontos = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroPontos);

        System.out.print("Informe a quantidade de threads a usar: ");
        int numetothreads = scannerObj.nextInt();
        System.out.println("Nº threads: "  + numetothreads);
        //ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numetothreads);
        /*MontecarloPar PiVal = new MontecarloPar(numeroPontos);
        startTime = System.nanoTime();
        double value = PiVal.getPi(numetothreads);
        stopTime = System.nanoTime();
        System.out.println("π com concorrencia: " + value);
        long totalTime = stopTime - startTime;
        double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;
        System.out.println("Calculo sequencial demorou (secs): " + elapsedTimeInSecond );*/

        double value = 0;
        long start = System.nanoTime();
        for (int i = 0; i <= ITERATIONS; ++i) {
            Monte_Carlo pipi = new  Monte_Carlo(numeroPontos);
            value = pipi.getPi(numetothreads);
        }
        long stop = System.nanoTime();
        long ttTime = (stop - start)/ITERATIONS;
        double ttTimeSeconds = ((double)ttTime/1_000_000_000);


        double valueseq = 0;
        long startseq = System.nanoTime();
        for (int i = 0; i < ITERATIONS; ++i) {
            valueseq = formMontecarloSeq(numeroPontos);
        }
        long stopseq = System.nanoTime();
        long ttTimeseq = (stopseq - startseq)/ITERATIONS;

        double ttTimeSecondseq = ((double)ttTimeseq/1_000_000_000);

        double speedUp = (ttTimeSecondseq/ttTimeSeconds);
        int numberOfCPUs;
        if(numetothreads==0) {
            numberOfCPUs = Runtime.getRuntime().availableProcessors();
        }
        else
        {
            numberOfCPUs = numetothreads;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        double percentagemCode = ((numberOfCPUs /speedUp)-1) / (numberOfCPUs-1);
        String angleFormated = df.format(percentagemCode);

        System.out.println("Value of pi paralelo is: " + value);
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio paralelo foi: " + ttTimeSeconds + "s");

        System.out.println("Value of pi seq is: " + valueseq/ITERATIONS);
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio sequencial foi: " + ttTimeSecondseq + "s");
        System.out.println("O startup foi: " + speedUp);
        System.out.println("O código percentual foi: " + percentagemCode);
        System.out.println("Numero de processadores disponiveis: " + Runtime.getRuntime().availableProcessors());

    }
}