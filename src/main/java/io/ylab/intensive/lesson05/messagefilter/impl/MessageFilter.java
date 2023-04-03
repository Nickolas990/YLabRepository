package io.ylab.intensive.lesson05.messagefilter.impl;

import io.ylab.intensive.lesson05.messagefilter.interfaces.Filter;
import io.ylab.intensive.lesson05.messagefilter.interfaces.Sender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.StringTokenizer;

@Component
public class MessageFilter implements Filter {

    private final DataSource dataSource;
    private final Sender sender;

    @Value("classpath:censored.txt")
    private Resource resource;

    @Value("${table.name}")
    private String tableName;

    public MessageFilter(DataSource dataSource, Sender sender) {
        this.dataSource = dataSource;
        this.sender = sender;
    }

    @PostConstruct
    private void initCensoredBase() {
        if (checkExists(tableName)) {
            truncateTable(tableName);
        } else {
            createTable(tableName);
        }
        fillTable(tableName);
    }

    @Override
    public void checkAndChange(String message) {
        String result = message;
        StringTokenizer tokenizer = new StringTokenizer(message, " \t\r\n,.");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (checkWord(token)) {
                String censoredToken = censoring(token);
                result = result.replace(token, censoredToken);
            }
        }
        sender.sendMessage(result);
    }

    private boolean checkWord(String token) {
        boolean result = false;
        String sql = "SELECT word FROM " + tableName + " WHERE LOWER(word) = LOWER(?) ";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            ResultSet rs = preparedStatement.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    private String censoring(String token) {
        StringBuilder sb = new StringBuilder(token);
        for (int i = 1; i < token.length() - 1 ; i++) {
            sb.replace(i, i+1, "*");
        }
        return sb.toString();
    }

    private boolean checkExists(String tableName) {
        boolean exists = false;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet rs = dbMetaData.getTables(null, null, tableName, new String[]{"TABLE"});
            exists = rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return exists;
    }

    private void createTable(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" +
                "word varchar(255) NOT NULL\n" +
                ")";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void truncateTable(String tableName) {
        String sql = "TRUNCATE TABLE " + tableName;
        try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    private void fillTable(String tableName) {
        String fillingSql = "INSERT INTO " + tableName + " (word) values (?)";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(fillingSql)) {
            connection.setAutoCommit(false);
            try (BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    preparedStatement.setString(1, line);
                    preparedStatement.addBatch();
                }
                try {
                    preparedStatement.executeBatch();
                    connection.commit();
                } catch (BatchUpdateException e) {
                    System.err.println(e.getMessage());
                    connection.rollback();
                }

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
