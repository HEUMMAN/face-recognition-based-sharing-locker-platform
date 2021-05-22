package com.jongsul.fabinetgradle;

import com.jongsul.fabinetgradle.Mqtt.MqttSubscribeUserID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FabinetGradleApplication {

	public static void main(String[] args) {
		//mqtt를 스프링 실행시 최초 1회만 동작하도록 해서 subscriber를 실행시켜야함. publisher는 그 안에서 실행하도록   
//		MqttSubscribeUserID mqttSubscribeUserID = new MqttSubscribeUserID();
//		mqttSubscribeUserID.MqttSub();
		SpringApplication.run(FabinetGradleApplication.class, args);

	}

}
