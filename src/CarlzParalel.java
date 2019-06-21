import java.util.Scanner;
import java.util.concurrent.*;

public class CarlzParalel implements Callable<Double> {

    private long inicio;
    private long fim;
    private final static int ITERATIONS = 25;
    public static long start = System.nanoTime();
    public static long stop = System.nanoTime();
    public static Future<Double> parte1;
    public static Future<Double> parte2;
    public static Future<Double> parte3;
    public static Future<Double> parte4;


    public CarlzParalel(long inicio, long fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Double call() throws Exception {
        double sum = 0.0;
        for (long i = inicio - 1; i < fim; i++) {
            double numerator = i % 2 == 0 ? 1 : -1;
            double denominator = i * 2 + 1;
            sum += numerator / denominator;
        }
        return sum;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //cria um pool de threads para realizar o cálculo
        Scanner scannerObj = new Scanner(System.in);
        System.out.println("Numero de processadores disponiveis: " + Runtime.getRuntime().availableProcessors());
        //System.out.println("Nº threads : " + listathreads);
        //ExecutorService es = Executors.newCachedThreadPool();
        System.out.print("Informe a número máx de threads:");
        int numetothreads = scannerObj.nextInt();
        System.out.println("Nº threads : " + numetothreads);
        //https://stackoverflow.com/questions/949355/executors-newcachedthreadpool-versus-executors-newfixedthreadpool
        ExecutorService es = Executors.newFixedThreadPool(numetothreads);
        long startTime = System.nanoTime();
        //separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma
        start = System.nanoTime();

        for (int i = 0; i < ITERATIONS; ++i) {
            parte1 = es.submit(new CarlzParalel(1, 500000000));
            parte2 = es.submit(new CarlzParalel(500000001, 1000000000));
            parte3 = es.submit(new CarlzParalel(1000000001, 1500000000));
            parte4 = es.submit(new CarlzParalel(1500000001, 2000000000));
        }



        double π = 0;

        //1200000000
        try {
            //System.out.println(Thread.currentThread().getName());
            es.shutdown();
            while(!es.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Waiting ...");
            }

                π = 4.0 * (parte1.get() + parte2.get() + parte3.get() + parte4.get());


        } catch (Exception e) {
            e.printStackTrace();

        }
        stop = System.nanoTime();


        System.out.println("Valor calculado de π é " + π);
        long stopTime = System.nanoTime();
        long totalTime = stopTime - startTime;
        double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;
        System.out.println("Calculo paralelo demorou (secs): " + elapsedTimeInSecond );


        long ttTime = stop - start;
        double ttTimeSeconds = ((double)ttTime/ 1_000_000_000);
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio foi: " + ttTimeSeconds + "s");
    }
}