import java.util.Scanner;
import java.util.concurrent.*;

public class CarlzParalel implements Callable<Double> {

    private int inicio;
    private int fim;

    public CarlzParalel(int inicio, int fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Double call() throws Exception {
        double sum = 0.0;
        double fator;
        for (int i = inicio; i <= fim; i++) {
            if (i % 2 == 0) {
                fator = Math.pow(1.0, i + 1);
            } else {
                fator = Math.pow(1.0, i - 1);
            }
            sum += fator / (2.0 * (double) i - 1.0);
        }

        return sum;
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //cria um pool de threads para realizar o cálculo
        Scanner scannerObj = new Scanner(System.in);
        long ti = System.currentTimeMillis();
        System.out.print("Informe a número máx de threads:");
        int numetothreads = scannerObj.nextInt();
        Thread[] listathreads = new Thread[numetothreads];

        System.out.println("Nº threads : " + listathreads);
        ExecutorService es = Executors.newCachedThreadPool();

        //separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma
        Future<Double> parte1 = es.submit(new CarlzParalel(1, 100000000));
        Future<Double> parte2 = es.submit(new CarlzParalel(100000001, 200000000));
        Future<Double> parte3 = es.submit(new CarlzParalel(200000001, 300000000));
        Future<Double> parte4 = es.submit(new CarlzParalel(400000001, 500000000));

            /*
            Future<Double> parte1 =  es.submit(new CarlzParalel(1,100000000));
            Future<Double> parte2 = es.submit(new CarlzParalel(100000001,200000000));
            Future<Double> parte3 = es.submit(new CarlzParalel(200000001,300000000));
            Future<Double> parte4 = es.submit(new CarlzParalel(300000001,400000000));*/

        //junta os valores cálculados das 4 partes e multiplica por 4
        double pi = 4.0 * (parte1.get() + parte2.get() + parte3.get() + parte4.get());

        es.shutdown();

        System.out.println("Valor calculado de PI é " + pi);
        long tf = System.currentTimeMillis();
        long tcc = tf-ti;
        System.out.println("Tempo gasto com concorrência " + tcc);

        ti = System.currentTimeMillis();

//separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma

        try {
            Double parteA = (new CarlzParalel(1, 100000000)).call();
            Double parteB = (new CarlzParalel(100000001, 200000000)).call();
            Double parteC = (new CarlzParalel(200000001, 300000000)).call();
            Double parteD = (new CarlzParalel(400000001, 500000000)).call();
            pi = 4.0 * (parteA + parteB + parteC + parteD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //junta os valores cálculados das 4 partes e multiplica por 4

        System.out.println("Valor calculado de PI é " + pi);
        tf = System.currentTimeMillis();
        long tsc = tf - ti;
        double divisao = (double) tcc / (double) tsc;
        double ganho = (divisao) * 100;
        System.out.println("Tempo gasto sem concorrência " + tsc);
        System.out.println("ganho % – TCC/TSC * 100 = " + ganho + " %");
        System.out.println("Numero de processadores: " + Runtime.getRuntime().availableProcessors());


    }
}