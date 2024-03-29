package io.ylab.intensive.lesson05.eventsourcing.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    // тут пишем создание и запуск приложения работы с БД
    Receiver messageReceiver = applicationContext.getBean(Receiver.class);
    messageReceiver.receiveMessage();
  }
}
