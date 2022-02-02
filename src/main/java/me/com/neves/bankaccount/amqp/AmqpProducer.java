package me.com.neves.bankaccount.amqp;

public interface AmqpProducer<T> {
    void producer(T t);
}
