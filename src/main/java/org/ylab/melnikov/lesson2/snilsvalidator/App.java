package org.ylab.melnikov.lesson2.snilsvalidator;

import java.util.function.Predicate;

/**
 * @author Nikolay Melnikov
 */
public class App {
    public static void main(String[] args) {

        Validator validator = new SnilsValidator()::validate;
        System.out.println("Validation 24432869174 is " + validator.validate("24432869174"));
        System.out.println("Validation 97252237626 is " + validator.validate("97252237626"));
        System.out.println("Validation 07872793932 is " + validator.validate("07872793932"));
        System.out.println("Validation 76151556200 is " + validator.validate("76151556200"));
        System.out.println("Validation 76151556200asda is " + validator.validate("76151556200asda"));
        System.out.println("Validation 761515500006200 is " + validator.validate("761515500006200"));
    }
}
