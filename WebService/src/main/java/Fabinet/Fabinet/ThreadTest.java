package Fabinet.Fabinet;

import org.springframework.boot.SpringApplication;

public class ThreadTest {
    public static void main(String[] args) {
        MqttSubThreadRun mqttSubThreadRun = new MqttSubThreadRun();
        MqttPubThreadRun mqttPubThreadRun = new MqttPubThreadRun();
        mqttPubThreadRun.run();
        mqttSubThreadRun.run();
    }
}
