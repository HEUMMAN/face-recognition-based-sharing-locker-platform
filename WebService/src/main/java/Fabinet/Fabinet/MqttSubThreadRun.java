package Fabinet.Fabinet;

import Fabinet.Fabinet.Mqtt.MqttSubscribeUserID;

public class MqttSubThreadRun extends Thread{
    public void run(){
        MqttSubscribeUserID mqttSubscribeUserID = new MqttSubscribeUserID();
        mqttSubscribeUserID.MqttSub();
    }
}
