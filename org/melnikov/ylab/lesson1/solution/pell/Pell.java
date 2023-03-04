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
        if (n < 3) return n;
        return 2 * result(n-1) + result(n-2);
    }
}
