package com.jongsul.fabinetgradle.Mqtt;

public class MqttSubThreadRun extends Thread{
    public void run(){
        MqttSubscribeUserID mqttSubscribeUserID = new MqttSubscribeUserID();
        mqttSubscribeUserID.MqttSub();
    }
}
