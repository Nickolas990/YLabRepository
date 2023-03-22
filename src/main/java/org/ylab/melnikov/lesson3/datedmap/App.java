package org.ylab.melnikov.lesson3.datedmap;

/**
 * @author Nikolay Melnikov
 */
public class App {
    public static void main(String[] args) {
        DatedHashMap map = new DatedHashMap();

        map.put(" ", " ");
        map.put(" sdad", " asadad");
        System.out.println(map.getKeyLastInsertionDate(" "));
    }
}
