package Fabinet.Fabinet;

import Fabinet.Fabinet.Mqtt.MqttPublishUserID;
import Fabinet.Fabinet.Mqtt.MqttSubscribeUserID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FabinetApplication {

	public static void main(String[] args) {
		MqttSubThreadRun mqttSubThreadRun = new MqttSubThreadRun();
		MqttPubThreadRun mqttPubThreadRun = new MqttPubThreadRun();
		mqttPubThreadRun.run();
		mqttSubThreadRun.run();
		SpringApplication.run(FabinetApplication.class, args);
	}
}
