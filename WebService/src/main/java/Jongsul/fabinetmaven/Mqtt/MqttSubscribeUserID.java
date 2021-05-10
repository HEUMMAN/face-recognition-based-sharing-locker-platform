package Jongsul.fabinetmaven.Mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSubscribeUserID extends Thread{
    private static String userName = "de";

    public String MqttSub() {
            String findUserName = "";
            MqttClient client;
            try {
                client = new MqttClient(
                        "tcp://3.34.255.198:1883", //URI
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
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                        System.out.println("MQTT Publish Complete");
                    }
                });
                client.connect();
                client.subscribe("heum/username", 0);
                findUserName = userName;
                userName = "";
//                client.disconnect();
//                System.out.println("Subscriber disconnect");
            } catch (MqttException e) {
                e.printStackTrace();
            }
            System.out.println("findUserName: " + findUserName);
            return findUserName;
    }
}
