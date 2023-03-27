package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiApp {
  public static final String EXCHANGE_NAME = "exec1";
  public static final String QUEUE_NAME = "queue";

  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = initMQ();
    // Тут пишем создание PersonApi, запуск и демонстрацию работы

    PersonApi personApi = new PersonApiImpl(connectionFactory, DbUtil.buildDataSource());
    personApi.savePerson(3L, "John", "Dou", "Joe");
    personApi.savePerson(4L, "Jane", "Dou", "Jane");
    personApi.savePerson(5L, "Test", "Test", "Test");
    personApi.deletePerson(1L);
    System.out.println(personApi.findPerson(3L));
    List<Person> personList = personApi.findAll();
    System.out.println(personList);


  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
