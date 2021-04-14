package Fabinet.Fabinet;

import Fabinet.Fabinet.Mqtt.MqttPublishUserID;

public class MqttPubThreadRun extends Thread{
    public void run(){
        MqttPublishUserID mqttPublishUserID = new MqttPublishUserID();
        mqttPublishUserID.MqttPub();
    }
}
