package com.jongsul.fabinetgradle.Mqtt4Spring;

import com.jongsul.fabinetgradle.Service.CabinetService;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

import java.nio.charset.StandardCharsets;


@Configuration
@RequiredArgsConstructor
public class MqttConfig {
    private static final String MQTT_USERNAME = "username";
    private static final String MQTT_PASSWORD = "password";

    private static final String BROKER_URL = "tcp://3.34.255.198:1883";
    private static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId();
    private static final String TOPIC_FILTER = "heum/username";
    private static final String TOPIC_FILTER_TO_CABINET = "heum/username2";   //사물함으로 publish하는 topic

    private static String inputUserName = "";
    private static String outputCabinetName = "";

    @Autowired
    private CabinetService cabinetservice;

    private MqttConnectOptions connectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(MQTT_USERNAME);
        options.setPassword(MQTT_PASSWORD.toCharArray());
        return options;
    }

    @Bean
    public DefaultMqttPahoClientFactory  defaultMqttPahoClientFactory() {
        DefaultMqttPahoClientFactory  clientFactory = new DefaultMqttPahoClientFactory();
        clientFactory.setConnectionOptions(connectOptions());
        return clientFactory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inboundChannel() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(BROKER_URL, MQTT_CLIENT_ID, TOPIC_FILTER);
        adapter.setCompletionTimeout(50000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler inboundMessageHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            System.out.println("Topic:" + topic);
            System.out.println("Payload:" + message.getPayload());
            inputUserName = message.getPayload().toString();
            System.out.println("message.getPayload().toString의 값: "+message.getPayload().toString());
            String usingCabinetList = cabinetservice.getCabinetName(message.getPayload().toString()).get(0).getName();
            System.out.println(inputUserName+"가 사용중인 사물함 이름은"+usingCabinetList);
            outputCabinetName = usingCabinetList;
            SendMessage sendMessage = new SendMessage();
            sendMessage.sendToMqtt("NOT_USE_ARG");
        };
    }

    //publish

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(DefaultMqttPahoClientFactory clientFactory) {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(MQTT_CLIENT_ID, clientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(0);
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface OutboundGateway {
        void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic);
    }

    public class SendMessage implements OutboundGateway{
        @Override
        public void sendToMqtt(String topic) {
            MqttClient client;
            try {
                client = new MqttClient(
                        BROKER_URL, //URI
                        MqttClient.generateClientId(), //ClientId
                        new MemoryPersistence()
                );
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                        System.out.println("MQTT Connection Lost");
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message){
                        System.out.println("why messageArrived");
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                        System.out.println("MQTT Publish Complete");
                    }
                });
                client.connect();
                client.publish( TOPIC_FILTER_TO_CABINET, outputCabinetName.getBytes(StandardCharsets.UTF_8),0, false); // retained
                //client.disconnect();
                //System.out.println("Publisher disconnect");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}
