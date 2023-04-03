package io.ylab.intensive.lesson05.messagefilter.interfaces;
@FunctionalInterface
public interface Filter {

    String checkAndChange(String message);

}
