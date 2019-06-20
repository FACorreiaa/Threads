import java.util.Scanner;

public class MontecarloSeq {
    long startTime = System.currentTimeMillis();
    public static double x = 0;
    public static double y = 0;
    public static int nSuccess = 0;
    private final static int ITERATIONS = 10;


    public static double formMontecarloSeq(long numeroPontos) {
        for (long i = 1; i <= numeroPontos; i++) {
            x = Math.random();
            y = Math.random();
            if (x * x + y * y <= 1)
                nSuccess++;
        }
        return (4.0* nSuccess / numeroPontos);
    }

    public static void main(String[] args) {
        Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Informe a quantidade de iterações a calcular: ");
        int numeroPontos = scannerObj.nextInt();
        System.out.println("Nº Pontos : " + numeroPontos);  // Output user input


        System.out.println("Numero de processadores: " + Runtime.getRuntime().availableProcessors());
        //Thread[] listathreads = new Thread[2];
        long startTime = System.nanoTime();
        double result = formMontecarloSeq(numeroPontos);
        System.out.println("π: " + result);
        long stopTime = System.nanoTime();
        long totalTime = stopTime - startTime;
        System.out.println("Calculo sequencial demorou (secs): "  + String.format("%.6f", (totalTime)/1.0e9) );

        long start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; ++i) {
            formMontecarloSeq(numeroPontos);
        }
        long elapsed = System.currentTimeMillis() - start;
        long average = elapsed / ITERATIONS;
        System.out.println("Para " + ITERATIONS + "iteracoes" + "o tempo médio foi: " + String.format("%.6f", (average)/1.0e9) + "s");
    }
}