package org.melnikov.ylab.lesson1.solution.stars;

import java.util.Scanner;

/**
 * @author Nikolay Melnikov
 */
public class Stars {
    public static void main(String[] args) {
        Stars star = new Stars();
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();
            star.print(n, m, template);
        }
    }
    public void print(int n, int m, String template) {
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                System.out.print(template);
            }
            System.out.println();
        }
    }
}
