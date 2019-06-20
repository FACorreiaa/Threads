
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class CalculatePi extends Thread
{
    double sum = 0;
    int numeroPontos = 0;
    CalculatePi(int _numeroPontos)
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
public class MonteCarlo_Multithread {


    public static void main(String[] args)
    {
        Scanner scannerObj = new Scanner(System.in);
        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroIteracoes = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroIteracoes);

        System.out.print("Informe a número máx de threads:");
        int numetothreads = scannerObj.nextInt();
        System.out.println("Nº threads : " + numetothreads);
        //https://howtodoinjava.com/java/multi-threading/java-fixed-size-thread-pool-executor-example/
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numetothreads);


        long inicio = System.nanoTime();
        CalculatePi calculatePi = new CalculatePi(numeroIteracoes);
        calculatePi.start();
        while(calculatePi.isAlive())
        {

        }
        //End
        double result = calculatePi.getPiValue();
        long fim = System.nanoTime();
        System.out.println("resultado: " + result);
        System.out.println("Calculo demorou (secs): "  + String.format("%.6f", (fim-inicio)/1.0e9) );
    }
}
