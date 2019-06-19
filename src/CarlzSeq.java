import java.util.Scanner;

public class CarlzSeq {
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
        System.out.println("Vamos calcular - Formula Professor");
        long startTime = System.nanoTime();
        double result = formCarlzSeq(numeroIteracoes);
        long stopTime = System.nanoTime();
        System.out.println("resultado: " + result);
        System.out.println("Calculo demorou (secs): " + (stopTime - startTime)*0.001 + "s");
    }
}
