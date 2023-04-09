package io.ylab.intensive.lesson05.messagefilter.interfaces;
@FunctionalInterface
public interface Filter {

    void checkAndChange(String message);

}
