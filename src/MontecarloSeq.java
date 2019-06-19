import java.util.Scanner;

public class MontecarloSeq {
    long startTime = System.currentTimeMillis();
    public static double x = 0;
    public static double y = 0;
    public static int nSuccess = 0;

    public static double formMontecarloSeq(int numeroPontos) {
        for (int i = 1; i <= numeroPontos; i++) {
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
        System.out.println("Vamos calcular - Formula Professor");
        Thread[] listathreads = new Thread[2];
        long startTime = System.nanoTime();
        double result = formMontecarloSeq(numeroPontos);
        long stopTime = System.nanoTime();
        System.out.println("resultado: " + result);
        System.out.println("Time Duration: " + (stopTime - startTime)/0.001 + "s");
    }
}