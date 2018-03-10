package io.github.jhipster.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Jhipster Websocket Sample Application.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final Rabbitmq rabbitmq = new Rabbitmq();
    public Rabbitmq getRabbitmq() {
        return rabbitmq;
    }
    public static class Rabbitmq {
        private String brokerUrl = "127.0.0.1";
        private String username = "guest";
        private String password = "guest";
        private int port = 61613;
        public String getBrokerUrl() {
            return brokerUrl;
        }
        public void setBrokerUrl(String brokerUrl) {
            this.brokerUrl = brokerUrl;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public int getPort() {
            return port;
        }
        public void setPort(int port) {
            this.port = port;
        }
    }
}
