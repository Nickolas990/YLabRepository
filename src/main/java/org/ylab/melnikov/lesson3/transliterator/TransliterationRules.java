package org.ylab.melnikov.lesson3.transliterator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikolay Melnikov
 */
public class TransliterationRules {

    public static Map<String, String> initializeSimpleTransliterationRules() {
        Map<String, String> rules = new HashMap<>();
        rules.put("A", "А");
        rules.put("Б", "B");
        rules.put("В", "V");
        rules.put("Г", "G");
        rules.put("Д", "D");
        rules.put("Е", "E");
        rules.put("Ё", "E");
        rules.put("Ж", "ZH");
        rules.put("З", "Z");
        rules.put("И", "I");
        rules.put("Й", "I");
        rules.put("К", "K");
        rules.put("Л", "L");
        rules.put("М", "M");
        rules.put("Н", "N");
        rules.put("О", "O");
        rules.put("П", "P");
        rules.put("Р", "R");
        rules.put("С", "S");
        rules.put("Т", "T");
        rules.put("У", "U");
        rules.put("Ф", "F");
        rules.put("Х", "KH");
        rules.put("Ц", "TS");
        rules.put("Ч", "CH");
        rules.put("Ш", "SH");
        rules.put("Щ", "SHCH");
        rules.put("Ъ", "IE");
        rules.put("Ы", "Y");
        rules.put("Ь", "-");
        rules.put("Э", "E");
        rules.put("Ю", "IU");
        rules.put("Я", "IA");
        return rules;
    }
}
