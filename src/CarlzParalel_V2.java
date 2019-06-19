package trabalho_pi;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class CalculatePi1 extends Thread
{
    double sum = 0;
    int numeroPontos = 0;
    int denominator = 1;

    CalculatePi1(int _numeroPontos)
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
public class CarlzParalel_V2 {


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
        CalculatePi1 calculatePi = new CalculatePi1(5);
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
