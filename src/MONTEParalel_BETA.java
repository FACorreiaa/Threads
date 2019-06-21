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
        for (long i = 1; i <= fim; i++) {
            x = Math.random();
            y = Math.random();
            if (x * x + y * y <= 1)
                nSuccess++;
        }
        return (4.0* nSuccess / fim);
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
        Future<Double> parte1 = es.submit(new MONTEParalel_BETA(1, 10000000));
        Future<Double> parte2 = es.submit(new MONTEParalel_BETA(10000001, 20000000));
        Future<Double> parte3 = es.submit(new MONTEParalel_BETA(20000001, 30000000));
        Future<Double> parte4 = es.submit(new MONTEParalel_BETA(30000001, 40000000));

        double π = 0;

        //1200000000
        try {
            es.shutdown();
            while(!es.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Waiting ...");
                System.out.println("Thread Name is :- " + Thread.currentThread().getName());

            }
            π = 4.0 * (parte1.get() + parte2.get() + parte3.get() + parte4.get()) / 40000000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        long tf = System.nanoTime();

        System.out.println("Valor calculado de π é " + π);
        long tcc = tf - ti;
        //System.out.println("Calculo com concorrencia demorou demorou (secs): "  + String.format("%.6f", (tcc)/1.0e9) );
        System.out.println("Calculo concorrencial (secs): "  + String.format("%.6f", (tcc)/1.0e9) );

        ti = System.currentTimeMillis();

        //separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma

        //1200000000
        //System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroPontos = scannerObj.nextInt();


        //junta os valores cálculados das 4 partes e multiplica por 4
        tf = System.currentTimeMillis();
        System.out.println("Valor calculado de PI é " + π);
        long tsc = tf - ti;
        double divisao = (double) tcc / (double) tsc;
        double ganho = (divisao) * 100;
        //System.out.println("Calculo sem concorrencia demorou demorou (secs): "  + String.format("%.6f", (tsc)/1.0e9) );
        System.out.println("Calculo nao concorrencial (secs): "  + String.format("%.6f", (tsc)/1.0e9) );
        System.out.println("ganho % – TCC/TSC * 100 = " + String.format("%.6f", (ganho)/1.0e9) + " %");
        System.out.println("Numero de processadores: " + Runtime.getRuntime().availableProcessors());


    }
}