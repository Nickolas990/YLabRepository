package org.melnikov.ylab.lesson1.solution.multtable;

/**
 * @author Nikolay Melnikov
 */
public class MultTable {
    public static void main(String[] args) {
        printTable();
    }

    private static void printTable() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                System.out.printf("%d x %d = %d%n", i, j, i * j);
            }
        }
    }
}
