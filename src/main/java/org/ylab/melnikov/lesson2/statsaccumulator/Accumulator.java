package org.ylab.melnikov.lesson2.statsaccumulator;

/**
 * @author Nikolay Melnikov
 */
public class Accumulator implements StatsAccumulator{

    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int count;
    private int sum;
    private double avg;



    @Override
    public void add(int value) {
        count++;
        sum += value;
        if (value < min) {
            min = value;
        } else if (value > max) {
            max = value;
        }
        avg = sum / (count * 1.0);
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        return avg;
    }
}
