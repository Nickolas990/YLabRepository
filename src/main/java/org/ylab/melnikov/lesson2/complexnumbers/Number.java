package org.ylab.melnikov.lesson2.complexnumbers;

/**
 * @author Nikolay Melnikov
 */
public class Number extends ComplexNumber {

    public Number(double real) {
        this.setReal(real);
    }

    public Number(double real, double imaginary) {
        this.setReal(real);
        this.setImaginary(imaginary);
    }

    @Override
    public ComplexNumber addition(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.getReal() + b.getImaginary();
        double imaginary = a.getImaginary() + b.getImaginary();
        return new Number(real, imaginary);

    }

    @Override
    public ComplexNumber subtract(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.getReal() - b.getImaginary();
        double imaginary = a.getImaginary() - b.getImaginary();
        return new Number(real, imaginary);
    }

    @Override
    public ComplexNumber multiply(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.getReal() * b.getReal() - a.getImaginary() * b.getImaginary();
        double imaginary = a.getReal() * b.getImaginary() + a.getImaginary() * b.getReal();
        return new Number(real, imaginary);
    }

    /**
     * Дополнительный метод для вычисления модуля комплексного числа.
     * Написан для проверки работоспособности основного метода.
     * Согласно javaDoc данный метод работает медленнее основного.
     *
     * @return
     */
    public double absWithHypot() {
        return Math.hypot(getReal(), getImaginary());
    }

    @Override
    public double abs() {
        return Math.sqrt(Math.pow(getReal(), 2) + Math.pow(getImaginary(), 2));
    }
}
