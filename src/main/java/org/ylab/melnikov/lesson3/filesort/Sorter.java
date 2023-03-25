package org.ylab.melnikov.lesson3.filesort;

import java.io.*;
import java.util.*;

/**
 * @author Nikolay Melnikov
 */
public class Sorter {

    public File sortFile(File dataFile) throws IOException {
        List<File> sortedChunks = sortInChunk(dataFile);
        Comparator<Long> comparator = Comparator.naturalOrder();
        return mergeSortedChunks(sortedChunks, comparator);
    }

    public static File mergeSortedChunks(List<File> files, final Comparator<Long> comparator) throws IOException {
        File outputfile = new File("output.txt");
        PriorityQueue<BinaryFileBuffer> priorityQueue = new PriorityQueue<>((i, j) -> comparator.compare(i.peek(), j.peek()));
        for (File currentFile : files) {
            BinaryFileBuffer fileBuffer = new BinaryFileBuffer(currentFile);
            priorityQueue.add(fileBuffer);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputfile))) {
            while (!priorityQueue.isEmpty()) {
                BinaryFileBuffer fileBuffer = priorityQueue.poll();
                Long element = fileBuffer.pop();
                bufferedWriter.write(element + "");
                bufferedWriter.newLine();

                if (fileBuffer.empty()) {
                    fileBuffer.closeReader();
                    fileBuffer.deleteOriginalFile();
                } else {
                    priorityQueue.add(fileBuffer);
                }
            }
        } finally {
            for (BinaryFileBuffer fileBuffer : priorityQueue) fileBuffer.close();
        }
        return outputfile;
    }


    public List<File> sortInChunk(File dataFile) throws IOException {
        List<File> files = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            long blockSize = estimatedSizeOfChunk(dataFile);
            List<Long> tempList = new ArrayList<>();
            String line = "";
            try {
                while (line != null) {
                    long currentBlockSize = 0;
                    while ((currentBlockSize < blockSize) && (line = reader.readLine()) != null) {
                        tempList.add(Long.parseLong(line));
                        currentBlockSize += line.length();
                    }
                    files.add(sortAndSave(tempList));
                    tempList.clear();
                }
            } catch (EOFException e) {
                if (!tempList.isEmpty()) {
                    files.add(sortAndSave(tempList));
                    tempList.clear();
                }
            }
        }
        return files;
    }

    public File sortAndSave(List<Long> tempData) throws IOException {
        Collections.sort(tempData);
        File newTempFile = File.createTempFile("sortInBatch", "flatFile");
        newTempFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newTempFile))) {
            for (Long element : tempData) {
                writer.write(element + "");
                writer.newLine();
            }
        }
        return newTempFile;
    }

    public long estimatedSizeOfChunk(File dataFile) {
        long fileSize = dataFile.length();
        final int maxTempFiles = 1024;
        long blockSize = fileSize / maxTempFiles;
        long freeMemory = Runtime.getRuntime().freeMemory();

        if (blockSize < freeMemory / 2) {
            blockSize = freeMemory / 2;
        } else if (blockSize >= freeMemory) {
            System.err.println("ВНИМАНИЕ! Возможно переполнение памяти!");
        }
        return blockSize;
    }

}

