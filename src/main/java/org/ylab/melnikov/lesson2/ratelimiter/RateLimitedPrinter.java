package org.ylab.melnikov.lesson2.ratelimiter;

/**
 * @author Nikolay Melnikov
 */
public class RateLimitedPrinter implements Printer {
    private int interval;
    private long lastPrint;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        if (System.currentTimeMillis() - lastPrint > interval) {
            System.out.println(message);
            lastPrint = System.currentTimeMillis();
        }
    }


}
