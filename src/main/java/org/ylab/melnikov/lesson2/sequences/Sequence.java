package org.ylab.melnikov.lesson2.sequences;

/**
 * @author Nikolay Melnikov
 */
public class Sequence implements SequenceGenerator {
    @Override
    public void a(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(i * 2 + " ");
        }
        System.out.println();
    }

    @Override
    public void b(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print((i * 2 + 1) + " ");
        }
        System.out.println();
    }

    @Override
    public void c(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            System.out.print((int)Math.pow(i, 2) + " ");
        }
        System.out.println();
    }

    @Override
    public void d(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print((int)Math.pow(i, 3) + " ");
        }
        System.out.println();
    }

    @Override
    public void e(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print((int)Math.pow(-1, i+1) + " ");
        }
        System.out.println();
    }

    @Override
    public void f(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(((int)Math.pow(-1, i+1)*i) + " ");
        }
        System.out.println();
    }

    @Override
    public void g(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(((int)(Math.pow(-1, i+1) * Math.pow(i, 2))) + " ");
        }
        System.out.println();
    }

    @Override
    public void h(int n) {
        int[] odds = new int[n/2+1];
        int current = 0;
        for (int i = 0; i < odds.length; i++) {
            odds[i] = i + 1;
        }
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(odds[current] + " ");
                current++;
            } else {
                System.out.print(0 + " ");
            }
        }
        System.out.println();
    }

    @Override
    public void i(int n) {
        int x = 1;
        for (int i = 1; i <= n; i++) {
            x *= i;
            System.out.print(x + " ");
        }
        System.out.println();
    }

    @Override
    public void j(int n) {
        int[] fib = new int[n];
        fib[0] = 1;
        fib[1] = 1;
        for (int i = 2; i < n ; i++) {
            fib[i] = fib[i-1] + fib[i-2];
        }
        for (int i = 0; i < n ; i++) {
            System.out.print(fib[i] + " ");
        }
        System.out.println();
    }
}
