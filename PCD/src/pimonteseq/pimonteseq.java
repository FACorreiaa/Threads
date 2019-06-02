package pimonteseq;

public class pimonteseq {
	public static void main(String[] args) {
		int nThrows = 100000;
		long startTime = System.currentTimeMillis();
		double x = 0, y = 0;
		int nSuccess = 0;
		for (int i = 1; i <= nThrows; i++) {
			x = Math.random();
			y = Math.random();
			if (x * x + y * y <= 1)
				nSuccess++;
		}
		double value = 4.0 * nSuccess / nThrows;
		long stopTime = System.currentTimeMillis();
		System.out.println("Approx value:" + value);
		System.out.println("Difference to exact value of pi: " + (value - Math.PI));
		System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
		System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
	}
} 
