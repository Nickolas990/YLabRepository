package org.ylab.melnikov.lesson3.filesort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Nikolay Melnikov
 */
public class BinaryFileBuffer {
    public static final int BUFFERSIZE = 2048;
    private BufferedReader bufferedReader;
    private File originalfile;
    private String cache;
    private boolean empty;

    public BinaryFileBuffer(File file) {
        setOriginalFile(file);
        setFbr(file, BUFFERSIZE);
        reload();
    }

    public boolean empty() {
        return empty;
    }

    private void reload() {
        try {
            empty = (this.cache = bufferedReader.readLine()) == null;
        } catch (EOFException oef) {
            empty = true;
            cache = null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public void close() throws IOException {
        bufferedReader.close();
    }


    public Long peek() {
        if (empty()) return null;
        return Long.parseLong(cache);
    }

    public Long pop(){
        Long answer = peek();
        reload();
        return answer;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setFbr(File file, int buffer) {
        try {
            this.bufferedReader = new BufferedReader(new FileReader(file), buffer);
        } catch (FileNotFoundException e) {
            System.out.println("Origin file not found");
            throw new IllegalArgumentException(e);
        }
    }

    public File getOriginalfile() {
        return originalfile;
    }

    public void setOriginalFile(File originalfile) {
        this.originalfile = originalfile;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void deleteOriginalFile() throws IOException {
        Files.delete(Path.of(originalfile.toURI()));
    }

    public void closeReader() throws IOException {
        bufferedReader.close();
    }
}
