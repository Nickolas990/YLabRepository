package org.ylab.melnikov.lesson3.transliterator;

import java.util.Map;

/**
 * @author Nikolay Melnikov
 */
public class TransliteratorTest {
    public static void main(String[] args) {

        Transliterator transliterator = string -> {
            Map<String, String> rules = TransliterationRules.initializeSimpleTransliterationRules();
            for (int i = 1; i < string.length(); i++) {
                if (rules.containsKey(string.substring(i-1,i))) {
                    string = string.replace(string.substring(i-1,i), rules.get(string.substring(i-1,i)));
                }
            }
            return string;
        };

        System.out.println(transliterator.transliterate("ПРИВЕТ COWBOY"));

    }
}
