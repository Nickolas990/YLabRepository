package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Nikolay Melnikov
 */
@Component
public class MessageReceiver implements Receiver{
    private final ConnectionFactory connectionFactory;

    private final DbProcessor dbProcessor;

    @Autowired

    public MessageReceiver(ConnectionFactory connectionFactory, DbProcessor dbProcessor) {
        this.connectionFactory = connectionFactory;
        this.dbProcessor = dbProcessor;
    }

    public void receiveMessage() {

        String queueName = "queue";
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
                if (message != null) {
                    String received = new String(message.getBody());
                    dbProcessor.getCommand(received);
                }
            }
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
