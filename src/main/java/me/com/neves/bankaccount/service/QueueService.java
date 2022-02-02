package me.com.neves.bankaccount.service;

import me.com.neves.bankaccount.amqp.AmqpProducer;
import me.com.neves.bankaccount.model.TransactionMessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
// ------------------------------ FIELDS ------------------------------

    @Autowired
    private AmqpProducer<TransactionMessageQueue> amqp;

// -------------------------- OTHER METHODS --------------------------

    public void sendToConsumer(TransactionMessageQueue message) {
        amqp.producer(message);
    }
}
