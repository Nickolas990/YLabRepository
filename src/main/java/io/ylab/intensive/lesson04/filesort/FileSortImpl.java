package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Результат:
     * Тест batch processing против поочредного добавления данных
     * показал следующие тайминги:
     * batchLoadingDataToDB(data): 173 ms
     * simpleLoadingToDB(data): 35516 ms
     * <p>
     * Вывод: передача через executeBatch() ускоряет добавление данных более чем в 15 раз
     */


    @Override
    public File sort(File data) {
        // ТУТ ПИШЕМ РЕАЛИЗАЦИЮ
        File result = new File("result.txt");

        long start = System.currentTimeMillis();
        batchLoadingDataToDB(data);
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");


//        long start = System.currentTimeMillis();
//        simpleLoadingToDB(data);
//        long end = System.currentTimeMillis();
//        System.out.println(end - start + " ms");

        writeToFileSortedData(result);
        return result;
    }

    private void simpleLoadingToDB(File data) {
        String sql = "INSERT INTO numbers (val) values (?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(data));
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            while (reader.ready()) {
                statement.setLong(1, Long.parseLong(reader.readLine()));
                statement.executeUpdate();
            }
        } catch (IOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private int[] batchLoadingDataToDB(File data) {
        int[] queryResult = null;
        String sql = "INSERT INTO numbers (val) values (?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(data));
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            while (reader.ready()) {
                statement.clearParameters();
                statement.setLong(1, Long.parseLong(reader.readLine()));
                statement.addBatch();
            }
            try {
                queryResult = statement.executeBatch();
                connection.commit();
            } catch (BatchUpdateException e) {
                connection.rollback();
                System.out.println(e.getMessage());
            }
        } catch (IOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return queryResult;
    }

    private void writeToFileSortedData(File result) {
        String sql = "SELECT val FROM numbers ORDER BY val DESC";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(result));
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                writer.write(set.getString(1));
                writer.newLine();
                writer.flush();
            }
        } catch (IOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
