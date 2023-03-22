package org.ylab.melnikov.lesson3.passwordvalidator;

/**
 * @author Nikolay Melnikov
 */
public class WrongPasswordException extends IllegalArgumentException {
    public WrongPasswordException() {
    }

    public WrongPasswordException(String s) {
        super(s);
    }
}
