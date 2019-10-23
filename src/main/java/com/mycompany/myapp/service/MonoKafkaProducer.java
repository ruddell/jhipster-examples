package com.mycompany.myapp.service;

import com.mycompany.myapp.config.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MonoKafkaProducer {

    private final Logger log = LoggerFactory.getLogger(MonoKafkaProducer.class);

    private static final String TOPIC = "topic_mono";

    private KafkaProperties kafkaProperties;

    private KafkaProducer<String, String> kafkaProducer;

    public MonoKafkaProducer(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public void init() {
        log.info("Kafka producer initializing...");
        kafkaProducer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    public void send(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
        try {
            kafkaProducer.send(record);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void shutdown() {
        log.info("Shutdown Kafka producer");
        kafkaProducer.close();
    }
}
