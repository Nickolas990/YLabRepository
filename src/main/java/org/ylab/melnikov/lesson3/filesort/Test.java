package org.ylab.melnikov.lesson3.filesort;

import java.io.File;
import java.io.IOException;


/**
 * @author Nikolay Melnikov
 */
public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 10000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true
    }
}

