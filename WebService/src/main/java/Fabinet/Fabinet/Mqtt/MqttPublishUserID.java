package Fabinet.Fabinet.Mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

public class MqttPublishUserID extends Thread{
    private static String userName = "de";

    public String MqttPub() {
            String findUserName = "";
            MqttClient client;
            int a = 0;
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
                client.publish( "lsh/qq", "a".getBytes(StandardCharsets.UTF_8),0, false); // retained
                try {
                    System.out.println(++a+"초");
                    Thread.sleep(3000); //3초 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                findUserName = userName;
                userName = "";
                //client.disconnect();
                System.out.println("Publisher disconnect");
            } catch (MqttException e) {
                e.printStackTrace();
            }
            return findUserName;
    }
}
