package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.MonoKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mono-kafka")
public class MonoKafkaResource {

    private final Logger log = LoggerFactory.getLogger(MonoKafkaResource.class);

    private MonoKafkaProducer kafkaProducer;

    public MonoKafkaResource(MonoKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.send(message);
    }
}
