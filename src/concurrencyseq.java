import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class concurrencyseq {
    private final static Object lock = new Object();

    public static double MonteCarlo(int numeroPontos) {
        double factor = 1.0;
        double sum = 0.0;
        for (int k = 0; k < numeroPontos; k++) {
            if(k%2 == 0)
            {
                factor = 1;
            }
            else
            {
                factor = -1;
            }
            sum += factor / (2 * k + 1);

        }
        return (double) 4.0 * sum;
    }

    public static double GregoryLeibniz(int numeroIteracoes)
    {
        double factor = 1.0;
        double sum = 0.0;
        for (int k = 0; k < numeroIteracoes; k++)
        {
            sum += factor / (2 * k + 1);
            factor = -factor;
        }
        return (double) 4.0 * sum;
    }

    static class MonteCarloPar extends Thread
    {
        double sum = 0;
        int numeroPontos = 0;
        MonteCarloPar(int _numeroPontos)
        {
            System.out.println("my thread created" + numeroPontos);
            numeroPontos = _numeroPontos;
        }

        @Override
        public void run()
        {
                System.out.println(LocalTime.now()  + " - thread: " + Thread.currentThread().getName());
                double factor = 1.0;
                for (int k = 0; k < numeroPontos; k++)
                {
                    if(k%2 == 0)
                    {
                        factor = 1;
                    }
                    else
                    {
                        factor = -1;
                    }
                        sum += factor / (2 * k + 1);
                }


        }

        public double getPiValue()
        {
            return (double) 4.0 * sum;
        }
    }

    static class GregoryLeibnizPar extends Thread
    {
        double sum = 0;
        int numeroPontos = 0;
        int denominator = 1;

        GregoryLeibnizPar(int _numeroPontos)
        {
            System.out.println("my thread created" + numeroPontos);
            numeroPontos = _numeroPontos;
        }
        @Override
        public void run()
        {
            double factor = 1.0;
            for (int k = 0; k < numeroPontos; k++)
            {
                if (k % 2 == 0) {
                    sum += (1 / denominator);
                } else {
                    sum -= (1 / denominator);
                }
                denominator = denominator + 2;
            }
        }

        public double getPiValue()
        {
            return (double) 4.0 * sum;
        }
    }

    public static void main(String[] args) {
        System.out.println("**************************");
        System.out.println("MonteCarlo SEQUENCIAL");
        System.out.println("**************************");
        Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroPontosMontePar = scannerObj.nextInt();
        System.out.println("Nº Pontos : " + numeroPontosMontePar);  // Output user input


        System.out.println("Numero de processadores: " + Runtime.getRuntime().availableProcessors() );
        System.out.println("Vamos calcular - Formula Professor");
        Thread[] listathreads = new Thread[2];
        long inicio = System.nanoTime();
        double result = MonteCarlo(numeroPontosMontePar);
        long fim = System.nanoTime();
        System.out.println("resultado: " + result);
        long tfm = System.currentTimeMillis();
        System.out.println("Calculo demorou (secs): "  + String.format("%.6f", (fim-inicio)/1.0e9) );

        System.out.println("**************************");
        System.out.println("MonteCarlo PARALEL");
        long ti = System.currentTimeMillis();

        System.out.println("**************************");

        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroIteracoes = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroIteracoes);

        System.out.print("Informe a número máx de threads:");
        int numetothreads = scannerObj.nextInt();
        System.out.println("Nº threads : " + numetothreads);
        //https://howtodoinjava.com/java/multi-threading/java-fixed-size-thread-pool-executor-example/
        //BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numetothreads);


        MonteCarloPar calculatePi = new MonteCarloPar(numeroIteracoes);
        calculatePi.start();
        while(calculatePi.isAlive())
        {

        }
        //End
        double resultParalel = calculatePi.getPiValue();
        System.out.println("resultado: " + resultParalel);
        long tf = System.currentTimeMillis();
        System.out.println("Calculo demorou (secs): "  + String.format("%.6f", (fim-inicio)/1.0e9) );
        long tcc = tf - ti;
        System.out.println("Tempo gasto com concorrência " + tcc);

        System.out.println("**************************");
        System.out.println("GregoryLeibniz SEQUENCIAL");
        System.out.println("**************************");

        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroIteracoesGreg = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroIteracoesGreg);

        System.out.println("Gregory Numero de processadores: " + Runtime.getRuntime().availableProcessors() );
        System.out.println("Vamos calcular - Formula Professor");
        double resultGreg = GregoryLeibniz(numeroIteracoesGreg);
        System.out.println("resultado: " + resultGreg);
        System.out.println("Calculo demorou (secs): "  + String.format("%.6f", (fim-inicio)/1.0e9) );

        System.out.println("**************************");
        System.out.println("GregoryLeibniz PARALEL");
        System.out.println("**************************");

        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroIteracoesGregPar = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroIteracoesGregPar);

        System.out.print("Informe a número máx de threads:");
        int numetothreadsGregPar = scannerObj.nextInt();
        System.out.println("Nº threads : " + numetothreadsGregPar);
        //https://howtodoinjava.com/java/multi-threading/java-fixed-size-thread-pool-executor-example/
        ThreadPoolExecutor executorGreg = (ThreadPoolExecutor) Executors.newFixedThreadPool(numetothreads);


        GregoryLeibnizPar gregoryLeibniz = new GregoryLeibnizPar(5);
        gregoryLeibniz.start();
        while(gregoryLeibniz.isAlive())
        {
        }
        //End
        double resultGergPar = gregoryLeibniz.getPiValue();
        System.out.println("resultado: " + resultGergPar);
        System.out.println("Calculo demorou (secs): "  + String.format("%.6f", (fim-inicio)/1.0e9) );
    }
}
