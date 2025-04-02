package com.arav.play.kafka_consumer.producer;

import com.arav.play.kafka_consumer.avro.ShoppingOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@Slf4j
public class KafkaProducerService {



    @Autowired
    private KafkaTemplate<String, ShoppingOrder> kafkaTemplate;

    @Value("${spring.kafka.topic.shopping-order}")
    private String topic;

    public void sendShoppingOrder(ShoppingOrder order) {
        Supplier<String> s = () -> "order-sent";
        kafkaTemplate.send(topic, order.getOrderId().toString(), order)
                .completeAsync(null);


    }
}
