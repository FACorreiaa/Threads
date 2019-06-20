import java.util.Scanner;

public class CarlzSeq {
    private final static int ITERATIONS = 10;

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
        long startTime = System.nanoTime();
        double result = formCarlzSeq(numeroIteracoes);
        long stopTime = System.nanoTime();
        long totalTime = stopTime - startTime;
        System.out.println("π: " + result);
        System.out.println("Calculo Sequencial (secs): "  + String.format("%.6f", (totalTime)/1.0e9) );

        long start = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; ++i) {
            formCarlzSeq(numeroIteracoes);
        }
        long elapsed = System.currentTimeMillis() - start;
        long average = elapsed / ITERATIONS;
        System.out.println("Para " + ITERATIONS + " iteracoes" + "o tempo médio foi: " + String.format("%.6f", (average)/1.0e9) + "s");
    }
}
