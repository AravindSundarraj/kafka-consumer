package com.arav.play.kafka_consumer.config;


import com.arav.play.kafka_consumer.avro.ShoppingOrder;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
    public class KafkaConsumerConfig {

        @Value("${spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        @Value("${spring.kafka.properties.schema.registry.url}")
        private String schemaRegistryUrl;

        @Value("${spring.kafka.consumer.group-id}")
        private String groupId;

        @Bean
        public ConsumerFactory<String, ShoppingOrder> consumerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
            configProps.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
            configProps.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
            return new DefaultKafkaConsumerFactory<>(configProps);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, ShoppingOrder> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, ShoppingOrder> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());

            return factory;
        }
    }


