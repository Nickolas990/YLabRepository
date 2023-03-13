package org.ylab.melnikov.lesson2.statsaccumulator;

/**
 * @author Nikolay Melnikov
 */
public class App {
    public static void main(String[] args) {
        StatsAccumulator statsAccumulator = new Accumulator();

        statsAccumulator.add(1);
        statsAccumulator.add(2);
        statsAccumulator.add(3);
        statsAccumulator.add(4);

        System.out.println("Count is: " + statsAccumulator.getCount());
        System.out.println("Max is: " + statsAccumulator.getMax());
        System.out.println("Min is:" + statsAccumulator.getMin());
        System.out.println("Average is: " + statsAccumulator.getAvg());
    }
}
