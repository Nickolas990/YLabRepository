package org.ylab.melnikov.lesson2.snilsvalidator;

import java.util.function.Predicate;

/**
 * @author Nikolay Melnikov
 */
public class SnilsValidator implements Validator {

    public boolean validate(String snils)  {
        var result = false;
        if (snils.matches("\\d{11}")) {
            var sum = 0;
            int control = -1;
            for (int i = 0; i < 9; i++) {
                sum += Character.digit(snils.charAt(i), 10) * (9 - i);
            }
            if (sum < 100) {
                control = sum;
            } else if (sum == 100) {
                control = 0;
            } else {
                int finalCheck = sum % 101;
                control = finalCheck == 100 ? 0 : finalCheck;
            }
            result = Integer.parseInt(snils.substring(9)) == control;
        }
        return result;
    }
}
