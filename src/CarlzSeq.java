import java.util.Scanner;

public class CarlzSeq {
    private final static int ITERATIONS = 25;
    private  static long startTime = 0;
    private  static long stopTime = 0;

    public static double formCarlzSeq(int numeroIteracoes) {
        double factor = 1.0;
        double sum = 0.0;

        for (int k = 0; k < numeroIteracoes; k++) {
            sum += factor / (2 * k + 1);
            factor = -factor;
        }

        return (4.0 * sum);

    }

    public static void main(String[] args) {
        Scanner scannerObj = new Scanner(System.in);
        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroIteracoes = scannerObj.nextInt();
        System.out.println("Nº Iterações : " + numeroIteracoes);


        System.out.println("Gregory Numero de processadores: " + Runtime.getRuntime().availableProcessors());
        startTime = System.nanoTime();
        double result = formCarlzSeq(numeroIteracoes);
        stopTime = System.nanoTime();
        System.out.println("π: " + result);
        long totalTime = stopTime - startTime;
        double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;
        System.out.println("Calculo sequencial demorou (secs): " + elapsedTimeInSecond );

        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; ++i) {
            formCarlzSeq(numeroIteracoes);
        }
        long stop = System.nanoTime();
        long ttTime = stop - start;
        double ttTimeSeconds = ((double)ttTime/1_000_000_000) / ITERATIONS;
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio foi: " + ttTimeSeconds + "s");
    }
}
