package org.melnikov.ylab.lesson1.solution.pell;

import java.util.Scanner;

/**
 * @author Nikolay Melnikov
 */
public class Pell {
    public static void main(String[] args) {
        Pell pell = new Pell();
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            System.out.println(pell.result(n));
        }
    }

    public int result(int n) {
        int pell1 = 0;
        int pell2 = 1;
        if (n < 3) pell2 = n;

        for (int i = 0; i < n - 1; i++) {
            int pellX = 2 * pell2 + pell1;
            pell1 = pell2;
            pell2 = pellX;
        }
        return pell2;
    }
}
