package io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Nikolay Melnikov
 */
public class MessageReceiver {
    private ConnectionFactory connectionFactory;

    private DbProcessor dbProcessor;

    public MessageReceiver(ConnectionFactory connectionFactory, DbProcessor dbProcessor) {
        this.connectionFactory = connectionFactory;
        this.dbProcessor = dbProcessor;
    }

    public void receivingMessages() {

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
