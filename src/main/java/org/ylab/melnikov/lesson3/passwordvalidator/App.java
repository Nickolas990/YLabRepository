package org.ylab.melnikov.lesson3.passwordvalidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author Nikolay Melnikov
 */
public class App {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String login = reader.readLine();
                String password = reader.readLine();
                String confirm = reader.readLine();

                System.out.println(PasswordValidator.passwordValidate(login, password, confirm));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
