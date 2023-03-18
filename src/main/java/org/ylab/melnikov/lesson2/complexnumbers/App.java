package org.ylab.melnikov.lesson2.complexnumbers;

/**
 * @author Nikolay Melnikov
 */
public class App {
    public static void main(String[] args) {
        System.out.println(new Number(1,2) + " //Constructor with 2 parameters");
        System.out.println(new Number(1) + " //Constructor with 1 parameter");

        ComplexNumber number25 = new Number(2, 5);
        ComplexNumber number2 = new Number(2);

        System.out.println(number25.addition(number2) + " //Addition of 2 numbers (Second number has 1 parameter)");
        System.out.println(number25.addition(new Number(2, 2)) + " //Addition of 2 numbers (Second number has 2 parameters)");
        System.out.println(number25.subtract(new Number(2, 2)) + " //Subtraction of 2 numbers");
        System.out.println(number25.multiply(new Number(2, 2)) + " //Multiplication of 2 numbers");
        System.out.println(number25.multiply(new Number(2, 2)).abs() + " //Absolute value of number");


    }
}
