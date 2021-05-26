package com.jongsul.fabinetgradle.Mqtt4Spring;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
public class Mqtt {

    @ServiceActivator(inputChannel = "mqttOrderOutboundChannel")
    public MessageHandler mqttOrderMessageHandler() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOrderOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "mqttOrderOutboundChannel")
    public interface MqttOrderGateway {
        void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
    }
}
*/