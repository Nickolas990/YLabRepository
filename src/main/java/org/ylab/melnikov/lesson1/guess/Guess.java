package org.ylab.melnikov.lesson1.guess;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Nikolay Melnikov
 */
public class Guess {


    public static void main(String[] args) {
        Guess guess = new Guess();
        guess.startGame();

    }

    public void startGame() {
        int number = new Random().nextInt(99) + 1;
        int maxAttempts = 10;
        int attempts = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.printf("Я загадал число от 1 до 100. У тебя %d попыток угадать.%n", maxAttempts);
            do {
                int guess = scanner.nextInt();
                if (guess == number) {
                    ++attempts;
                    System.out.printf("Ты угадал с %d попыток!%n", attempts);
                    break;
                }
                if (guess > number) {
                    ++attempts;
                    System.out.printf("Мое число меньше! Осталось %d попыток.%n", maxAttempts - attempts);
                }
                if (guess < number) {
                    ++attempts;
                    System.out.printf("Мое число больше! Осталось %d попыток.%n", maxAttempts - attempts);
                }
                if (attempts == 10) {
                    System.out.printf("Ты не угадал. Мое число %d.", number);
                }
            } while (maxAttempts != attempts);
        }
    }
}