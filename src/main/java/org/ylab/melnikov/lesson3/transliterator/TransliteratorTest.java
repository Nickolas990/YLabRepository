package org.ylab.melnikov.lesson3.transliterator;

import java.util.Map;

/**
 * @author Nikolay Melnikov
 */
public class TransliteratorTest {
    public static void main(String[] args) {

        Transliterator transliterator = string -> {
            StringBuilder sb = new StringBuilder();
            Map<String, String> rules = TransliterationRules.initializeSimpleTransliterationRules();
            for (int i = 1; i <= string.length(); i++) {
                if (rules.containsKey(string.substring(i-1,i))) {
                    sb.append(rules.get(string.substring(i-1,i)));
                } else {
                    sb.append(string.substring(i-1, i));
                }
            }
            return sb.toString();
        };

        System.out.println(transliterator.transliterate("ПРИВЕТ COWBOY!!!!!"));

    }
}
