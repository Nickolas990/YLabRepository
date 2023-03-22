package org.ylab.melnikov.lesson3.filesort;

import java.io.*;

/**
 * @author Nikolay Melnikov
 */
public class BinaryFileBuffer {
    public static int BUFFERSIZE = 2048;
    public BufferedReader fbr;
    public File originalfile;
    private String cache;
    private boolean empty;

    public BinaryFileBuffer(File f) throws IOException {
        originalfile = f;
        fbr = new BufferedReader(new FileReader(f), BUFFERSIZE);
        reload();
    }

    public boolean empty() {
        return empty;
    }

    private void reload() throws IOException {
        try {
            if ((this.cache = fbr.readLine()) == null) {
                empty = true;
                cache = null;
            } else {
                empty = false;
            }
        } catch (EOFException oef) {
            empty = true;
            cache = null;
        }
    }

    public void close() throws IOException {
        fbr.close();
    }


    public Long peek() {
        if (empty()) return null;
        return Long.parseLong(cache);
    }

    public Long pop() throws IOException {
        Long answer = peek();
        reload();
        return answer;
    }
}
