package org.ylab.melnikov.lesson3.passwordvalidator;

/**
 * @author Nikolay Melnikov
 */
public class PasswordValidator {

    public static boolean passwordValidate(String login, String password, String confirmPassword) {
        boolean result = true;
        try {
            if (!login.matches("\\w+")) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            } else if (login.length() >= 20) {
                throw new WrongLoginException("Логин слишком длинный");
            }

            if (!password.matches("\\w+")) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            } else if (password.length() >= 20) {
                throw new WrongPasswordException("“Пароль слишком длинный");
            } else if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }

        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            result = false;
        }

        return result;
    }
}
