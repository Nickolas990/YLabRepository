package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Nikolay Melnikov
 */
public class Dialog {

    private ConnectionFactory connectionFactory;
    private String exchangeName;
    private String queueName;

    public Dialog(ConnectionFactory connectionFactory, String exchangeName, String queueName) {
        this.connectionFactory = connectionFactory;
        this.exchangeName = exchangeName;
        this.queueName = queueName;

    }

    public void sendMessage(String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "*");

            channel.basicPublish(exchangeName, "*", null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TimeoutException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
