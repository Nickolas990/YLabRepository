package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Nikolay Melnikov
 */
@Component
public class DbProcessor {
    private final DataSource dataSource;

    @Autowired
    public DbProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void getCommand(String command) {
        System.out.println("Received command: " + command);
        List<String> data = List.of(command.split("&&&"));
        if ("DELETE".equals(data.get(0))) {
            Long id = Long.parseLong(data.get(1));
            startDeleteProtocol(id);
        } else if ("SAVE".equals(data.get(0))) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Person person = objectMapper.readValue(data.get(1), Person.class);
                startSaveProtocol(person);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startSaveProtocol(Person person) {
        String sql;
        if (containsId(person.getId())) {
            sql = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? WHERE person_id = ?";
        } else {
            sql = "INSERT INTO person (first_name, last_name, middle_name, person_id) VALUES (?,?,?,?)";
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getMiddleName());
            preparedStatement.setLong(4, person.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void startDeleteProtocol(Long id) {
        if (!containsId(id)) {
            System.err.println("Зафиксирована попытка удаления person_id = " + id + ". Данные для удаления не найдены");
            return;
        }

        String sql = "DELETE FROM person WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean containsId(Long id) {
        boolean result = false;
        String sql = "SELECT * FROM person WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
