package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

    private final ConnectionFactory connectionFactory;
    private final DataSource dataSource;
    public static final String EXCHANGE_NAME = "exec1";
    public static final String QUEUE_NAME = "queue";

    public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        String message = String.format("DELETE&&&%d", personId);

        Dialog dialog = new Dialog(connectionFactory, EXCHANGE_NAME, QUEUE_NAME);
        dialog.sendMessage(message);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person person = new Person(personId, firstName, lastName, middleName);
        Dialog dialog = new Dialog(connectionFactory, EXCHANGE_NAME, QUEUE_NAME);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(person);
            String message = "SAVE&&&" + json;
            dialog.sendMessage(message);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person person = null;
        String sql = "SELECT * FROM person WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, personId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("person_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String middleName = rs.getString("middle_name");
                person = new Person(id, firstName, lastName, middleName);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("person_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String middleName = rs.getString("middle_name");
                result.add(new Person(id, firstName, lastName, middleName));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
        return result;
    }
}
