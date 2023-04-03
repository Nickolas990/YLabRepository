package io.ylab.intensive.lesson05.messagefilter.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.interfaces.Filter;
import io.ylab.intensive.lesson05.messagefilter.interfaces.Receiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageReceiver implements Receiver {

    @Value("${filter.queue.name}")
    private String QUEUE_NAME;
    private final ConnectionFactory connectionFactory;
    private final Filter filter;

    public MessageReceiver(ConnectionFactory connectionFactory, Filter filter) {
        this.connectionFactory = connectionFactory;
        this.filter = filter;
    }

    @Override
    public void receive() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_NAME, true);
                if (message != null) {
                    String received = new String(message.getBody());
                   filter.checkAndChange(received);
                }
            }
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
