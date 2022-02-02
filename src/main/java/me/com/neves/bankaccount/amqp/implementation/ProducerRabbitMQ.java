package me.com.neves.bankaccount.amqp.implementation;

import lombok.RequiredArgsConstructor;
import me.com.neves.bankaccount.amqp.AmqpProducer;
import me.com.neves.bankaccount.model.TransactionMessageQueue;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerRabbitMQ implements AmqpProducer<TransactionMessageQueue> {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.exchange.producer}")
    private String exchange;

    @Override
    public void producer(TransactionMessageQueue message) {
        try {
            rabbitTemplate.convertAndSend(exchange, queue, message);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }
}
