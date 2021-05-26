package com.jongsul.fabinetgradle.Mqtt;

import com.jongsul.fabinetgradle.Controller.CabinetController;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.annotation.PostConstruct;

public class MqttSubscribeUserID extends Thread{
    private static String userName = "default name";
    private static String MQTT_BROKER_URI = "tcp://3.34.255.198:1883";
    private static String SUBSCRIBE_TOPIC = "heum/username";
    private CabinetController cabinetController;
    public void MqttSub() {
        System.out.println("Mqtt 메서드 실행");
        String findUserName = "";
        MqttClient client;
        try {
            client = new MqttClient(
                    MQTT_BROKER_URI, //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence()
            );
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                    System.out.println("MQTT Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("published topic :" + topic + " ,content: " + message.toString());
                    userName = message.toString();
                    //이 메시지 인자에 유저사진명(ID)가 넘어오므로 이것을 사용해서 DB조회하자
                    //여기에 디비조회 넣자
                    //System.out.println("DB조회한 사물함 이름: "+cabinetController.getCabinetByName(userName));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                    System.out.println("MQTT Publish Complete");
                }
            });
            client.connect();
            client.subscribe(SUBSCRIBE_TOPIC, 0);
            findUserName = userName;
            userName = "";
//                client.disconnect();
//                System.out.println("Subscriber disconnect");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
