package org.ylab.melnikov.lesson2.complexnumbers;

/**
 * @author Nikolay Melnikov
 */
public abstract class ComplexNumber {
    private double real;
    private double imaginary;

    public abstract ComplexNumber addition(ComplexNumber b);

    public abstract ComplexNumber subtract(ComplexNumber b);

    public abstract ComplexNumber multiply(ComplexNumber b);

    public abstract double abs();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (imaginary == 0) sb.append(real);
            else if (real == 0) sb.append(imaginary).append("i");
                else if (imaginary < 0) sb.append(real).append(" - ").append(-imaginary).append("i");
                    else sb.append(real).append(" + ").append(imaginary).append("i");
        return sb.toString();
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
}
