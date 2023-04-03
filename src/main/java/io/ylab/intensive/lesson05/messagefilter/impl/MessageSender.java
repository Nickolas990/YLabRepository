package io.ylab.intensive.lesson05.messagefilter.impl;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.interfaces.Sender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class MessageSender implements Sender {

    private ConnectionFactory connectionFactory;

    @Value("${sender.exchange.name}")
    private String exchangeName;
    @Value("${sender.queue.name}")
    private String queueName;

    public MessageSender(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
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
