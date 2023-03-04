package org.melnikov.ylab.lesson1.solution.guess;


import java.util.Random;
import java.util.Scanner;

/**
 * @author Nikolay Melnikov
 */
public class Guess {
    private int maxAttempts;

    public static void main(String[] args) {
        Guess guess = new Guess();
        guess.startGame();

    }

    public void startGame() {
        int number = new Random().nextInt(99) + 1;
        maxAttempts = 10;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.printf("Я загадал число от 1 до 100. У тебя %d попыток угадать.%n", maxAttempts);
            do {
                int guess = scanner.nextInt();
                if (guess < number) {
                    maxAttempts--;
                    System.out.printf("Мое число больше! Осталось %d попыток.%n", maxAttempts);
                } else if (guess > number) {
                    maxAttempts--;
                    System.out.printf("Мое число меньше! Осталось %d попыток.%n", maxAttempts);
                } else if (guess == number) {
                    maxAttempts--;
                    System.out.printf("Ты угадал с %d попыток!%n", 10 - maxAttempts);
                    break;
                } else if (maxAttempts == 0) {
                    System.out.printf("Ты не угадал. Мое число %d.", number);
                }
            } while (maxAttempts != 0);
        }
    }
}
