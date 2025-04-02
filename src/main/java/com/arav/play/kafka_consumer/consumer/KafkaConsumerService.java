package com.arav.play.kafka_consumer.consumer;

import com.arav.play.kafka_consumer.avro.ShoppingOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {



    @KafkaListener(topics = "${spring.kafka.topic.shopping-order}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeShoppingOrder(ShoppingOrder order) {
        log.info("📦 Order received: {}", order);
    }
}
