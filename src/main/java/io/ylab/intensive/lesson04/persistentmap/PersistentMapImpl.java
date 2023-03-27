package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;
    private String currentName;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        currentName = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        boolean result = false;
        String sql = "SELECT key FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentName);
            preparedStatement.setString(2, key);

            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> keys = new ArrayList<>();
        String sql = "SELECT key FROM persistent_map WHERE map_name = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentName);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                keys.add(set.getString(1));
            }
        }
        return keys;
    }

    @Override
    public String get(String key) throws SQLException {
        String result;
        String sql = "SELECT value FROM persistent_map WHERE map_name = ? AND key = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentName);
            preparedStatement.setString(2, key);

            ResultSet set = preparedStatement.executeQuery();
            result = set.getString(1);
        }
        return result;
    }

    @Override
    public void remove(String key) throws SQLException {
        String sql = "DELETE FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentName);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        String sql;
        if (containsKey(key)) {
            sql = "UPDATE persistent_map SET value = ? WHERE key = ? AND map_name = ?";
        } else {
            sql = "INSERT INTO persistent_map (value, key, map_name) VALUES (?, ?, ?)";
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, currentName);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String sql = "DELETE FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentName);
            preparedStatement.executeUpdate();
        }
    }
}
