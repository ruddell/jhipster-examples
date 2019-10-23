package com.mycompany.myapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private Map<String, String> consumer;

    private Map<String, String> producer;

    public Map<String, Object> getConsumerProps() {
        return (Map) consumer;
    }

    public void setConsumer(Map<String, String> consumer) {
        this.consumer = consumer;
    }

    public Map<String, Object> getProducerProps() {
        return (Map) producer;
    }

    public void setProducer(Map<String, String> producer) {
        this.producer = producer;
    }
}
