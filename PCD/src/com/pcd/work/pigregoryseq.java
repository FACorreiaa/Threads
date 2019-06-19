package com.pcd.work;

import java.util.Scanner;


public class pigregoryseq {

	private static final Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {
		
		double factor = 1.0;
		double sum = 0.0;
		
		System.out.print("Informe a quantidade de iterações a calcular: ");
		int n = SCANNER.nextInt(); 
		
		for (int k = 0; k<n; k++) {
			sum += factor/(2*k+1);
			factor = -factor;
		}
		
		double pi_approx = 4.0*sum; 
		
		System.out.println("Para a quantidade de " + n + " iterações, o valor aproximado de pi é: " + pi_approx);

	}
} 
