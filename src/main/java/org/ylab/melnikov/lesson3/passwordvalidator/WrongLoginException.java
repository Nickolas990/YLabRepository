package org.ylab.melnikov.lesson3.passwordvalidator;

/**
 * @author Nikolay Melnikov
 */
public class WrongLoginException extends IllegalArgumentException {
    public WrongLoginException() {
    }

    public WrongLoginException(String s) {
        super(s);
    }
}
