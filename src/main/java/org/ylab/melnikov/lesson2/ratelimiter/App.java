package org.ylab.melnikov.lesson2.ratelimiter;

/**
 * @author Nikolay Melnikov
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Printing with 1 sec interval");

        Printer printer = new RateLimitedPrinter(1000);
        for (int i = 0; i < 1_000_000_000; i++) {
            printer.print(String.valueOf(i));
        }
    }
}
