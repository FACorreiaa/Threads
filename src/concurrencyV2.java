import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class concurrencyV2 {
    private final static Object lock = new Object();
    private final static int TotalSamples = 100000;
    private static int total = 0;
    private static Integer[] Samples = null;
    private static ExecutorService es = null;
    private static final Scanner SCANNER = new Scanner(System.in);



    public static void main(String[] args) throws Exception {

        System.out.println("Carlz concorrencial");
        ExecutorService es = Executors.newCachedThreadPool();
        long ti = System.currentTimeMillis();

        System.out.print("Informe a quantidade inicial: ");
        int begin = SCANNER.nextInt(); //Número de iterações recebidas pelo utilizador
        System.out.print("Informe a quantidade inicial: ");
        int end = SCANNER.nextInt(); //Número de iterações recebidas pelo utilizador

        //separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma
        Future<Double> parte1 = es.submit(new CarlzParalel(begin, end));
        Future<Double> parte2 = es.submit(new CarlzParalel(begin, end));
        Future<Double> parte3 = es.submit(new CarlzParalel(begin, end));
        Future<Double> parte4 = es.submit(new CarlzParalel(begin, end));

        /*
        Future<Double> parte1 = es.submit(new CarlzParalel(1, 100000000));
        Future<Double> parte2 = es.submit(new CarlzParalel(100000001, 200000000));
        Future<Double> parte3 = es.submit(new CarlzParalel(200000001, 300000000));
        Future<Double> parte4 = es.submit(new CarlzParalel(300000001, 400000000));*/

        //junta os valores cálculados das 4 partes e multiplica por 4
        double pi = 4.0 * (parte1.get() + parte2.get() + parte3.get() + parte4.get());

        System.out.println("Valor calculado de PI é " + pi);
        long tf = System.currentTimeMillis();
        long tcc = tf - ti;
        System.out.println("Tempo gasto com concorrência " + tcc);

        ti = System.currentTimeMillis();

        //separa o cálculo em 4 partes definindo o valor de n inicial e final para cada uma
        Double parteA;
        Double parteB;
        Double parteC;
        Double parteD;

        try {
            parteA = (new CarlzParalel(1, 100000000)).call();
            parteB = (new CarlzParalel(100000001, 200000000)).call();
            parteC = (new CarlzParalel(200000001, 300000000)).call();
            parteD = (new CarlzParalel(400000001, 500000000)).call();
        } catch (Exception e) {
            throw new Exception("erro");
        }


        //junta os valores cálculados das 4 partes e multiplica por 4
        pi = 4.0 * (parteA + parteB + parteC + parteD);

        System.out.println("Valor calculado de PI é " + pi);
        tf = System.currentTimeMillis();
        long tsc = tf - ti;
        double divisao = (double) tcc / (double) tsc;
        double ganho = (divisao) * 100;
        System.out.println("Tempo gasto sem concorrência " + tsc);
        System.out.println("ganho % – TCC/TSC * 100 = " + ganho + " %");

        System.out.println("Carlz paralelo");
        double factor = 1.0;
        double sum = 0;

        System.out.print("Informe a quantidade de iterações a calcular: ");
        int n = SCANNER.nextInt(); //Número de iterações recebidas pelo utilizador

        for (int k = 0; k<n; k++) {
            if(k % 2 == 0) {
                factor = 1.0;
            } else {
                factor = -1.0;
            }
            sum += factor/(2*k+1);
        }

        double pi_approx = 4.0*sum; //Valor aproximado de pi
        System.out.println("Para a quantidade de " + n + " iterações, o valor aproximado de pi é: " + pi_approx);
    }

    //Carlz
    public static class CarlzParalel implements Callable<Double> {

        private int inicio;
        private int fim;
        double factor = 1.0;
        double sum = 0;
        int n = SCANNER.nextInt(); //Número de iterações recebidas pelo utilizador
        public CarlzParalel(int inicio, int fim) {
            this.inicio = inicio;
            this.fim = fim;
        }

        public Double call() throws Exception {
            double sum = 0.0;

            for (int k = 0; k<n; k++) {
                if(k % 2 == 0) {
                    factor = 1.0;
                } else {
                    factor = -1.0;
                }
                sum += factor/(2*k+1);
            }

            return sum;
        }
    }
}
