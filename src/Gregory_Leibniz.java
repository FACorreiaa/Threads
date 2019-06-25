import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.*;

public class Gregory_Leibniz implements Callable<Double> {

    private long inicio;
    private long fim;
    private final static int ITERATIONS = 10;
    public static long start = System.nanoTime();
    public static long stop = System.nanoTime();
    public static Future<Double> parte1;
    public static Future<Double> parte2;
    public static Future<Double> parte3;
    public static Future<Double> parte4;


    public Gregory_Leibniz(long inicio, long fim) {
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

        private  static long startTime = 0;
        private  static long stopTime = 0;

        public static double formCarlzSeq(long numeroIteracoes) {
            double factor = 1.0;
            double sum = 0.0;

            for (int k = 0; k < numeroIteracoes; k++) {
                sum += factor / (2 * k + 1);
                factor = -factor;
            }

            return (4.0 * sum);

        }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner scannerObj = new Scanner(System.in);

        System.out.print("Informe a quantidade de iterações a calcular: ");
        long numeroIteracoes = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroIteracoes);

        System.out.print("Informe o numero de threads: ");
        int numetothreads = scannerObj.nextInt();
        System.out.println("Nº threads : " + numetothreads);
        //https://stackoverflow.com/questions/949355/executors-newcachedthreadpool-versus-executors-newfixedthreadpool
        ExecutorService es = Executors.newFixedThreadPool(numetothreads);
        //separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma
        start = System.nanoTime();
        for (int i = 0; i <= ITERATIONS; ++i) {
            parte1 = es.submit(new Gregory_Leibniz(1, 500000000));
            parte2 = es.submit(new Gregory_Leibniz(500000001, 1000000000));
            parte3 = es.submit(new Gregory_Leibniz(1000000001, 1500000000));
            parte4 = es.submit(new Gregory_Leibniz(1500000001, 2000000000));
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


        long ttTime = (stop - start)/ITERATIONS;
        double ttTimeSeconds = ((double)ttTime/1_000_000_000);

        double pi = 0;
        long startseq = System.nanoTime();
        for (int i = 0; i <= ITERATIONS; ++i) {
            pi = formCarlzSeq(numeroIteracoes);
        }
        long stopseq = System.nanoTime();
        long ttimeseq = (stopseq - startseq)/ITERATIONS;
        double ttotalseq = ((double)ttimeseq/1_000_000_000);

        double speedUp = (ttotalseq/ttTimeSeconds);
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

        System.out.println("Value of pi sequencial is: " + pi);
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio sequencial foi: " + ttotalseq + "s");
        System.out.println("Valor calculado de π paralelo é " + π);
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio paralelo foi: " + ttTimeSeconds + "s");
        System.out.println("O startup foi: " + speedUp);
        System.out.println("O código percentual foi: " + percentagemCode);
        System.out.println("Numero de processadores disponiveis: " + Runtime.getRuntime().availableProcessors());







    }
}