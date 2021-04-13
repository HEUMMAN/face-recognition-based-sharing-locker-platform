package Fabinet.Fabinet.Mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttEx{
    public static void main(String [] args) {

        MqttClient client;
        {
            try {
                client = new MqttClient(
                        "tcp://3.34.255.198:1883", //URI
                        MqttClient.generateClientId(), //ClientId
                        new MemoryPersistence()
                );
                System.out.println("0");
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        System.out.println("published topic :"+topic + " ,content: " + Arrays.toString(message.getPayload()));
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                    }
                });
                System.out.println("1");
                client.connect();
//                client.publish( "lsh/qq", "a".getBytes(UTF_8),0, false); // retained
//                try {
//                    Thread.sleep(3000); //3초 대기
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("isConnected?: "+client.isConnected());
                System.out.println("2");
                client.subscribe("heum/username", 0);
                System.out.println("3");
                //client.disconnect();
                System.out.println("4");
            } catch (MqttException e) {
                e.printStackTrace();
            }
            System.out.println("5");
        }
        System.out.println("6");
    }
}

/*
try {
        Thread.sleep(3000); //3초 대기
        } catch (InterruptedException e) {
        e.printStackTrace();
        }*/
