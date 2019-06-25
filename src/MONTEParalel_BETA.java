import java.util.Scanner;
import java.util.concurrent.*;

public class MONTEParalel_BETA implements Callable<Double> {

    private double x;
    private double y;
    private long inicio;
    private long fim;
    public static int nSuccess = 0;

    public MONTEParalel_BETA(long inicio, long fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Double call() throws Exception {
        for (long i = 0; i <= fim; i++) {
            x = Math.random();
            y = Math.random();
            if (x * x + y * y <= 1)
                nSuccess++;
        }
        return (4.0* nSuccess / (fim/4));
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
        long ti = System.nanoTime();



        //separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma
        Future<Double> parte1 = es.submit(new MONTEParalel_BETA(1, 500000000));
        Future<Double> parte2 = es.submit(new MONTEParalel_BETA(500000001, 1000000000));
        Future<Double> parte3 = es.submit(new MONTEParalel_BETA(1000000001, 1500000000));
        Future<Double> parte4 = es.submit(new MONTEParalel_BETA(1500000001, 2000000000));


        double π = 0;

        //1200000000
        try {
            es.shutdown();
            while(!es.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Waiting ...");
                System.out.println("Thread Name is :- " + Thread.currentThread().getName());

            }
            π = 4.0 * (parte1.get() + parte2.get() + parte3.get() + parte4.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long tf = System.nanoTime();

        long ttTime = (tf - ti);

        double ttTimeSeconds = ((double)ttTime/1_000_000_000);
        System.out.println("Value of pi is: " + π);
        System.out.println("ttTimeSeconds: " + ttTimeSeconds);

        //System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio foi: " + ttTimeSeconds + "s");
    }
}