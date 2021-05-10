package Jongsul.fabinetmaven.Mqtt;

public class MqttSubThreadRun extends Thread{
    public void run(){
        MqttSubscribeUserID mqttSubscribeUserID = new MqttSubscribeUserID();
        mqttSubscribeUserID.MqttSub();
    }
}
