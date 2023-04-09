package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    // Тут пишем создание PersonApi, запуск и демонстрацию работы
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    PersonApi personApi = applicationContext.getBean(PersonApi.class);
    // пишем взаимодействие с PersonApi
    try {
      personApi.savePerson(3L, "John", "Dou", "Joe");
      personApi.savePerson(4L, "Jane", "Dou", "Jane");
      personApi.savePerson(5L, "Test", "Test", "Test");
      personApi.deletePerson(1L);
      System.out.println(personApi.findPerson(3L));
      List<Person> personList = personApi.findAll();
      System.out.println(personList);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
