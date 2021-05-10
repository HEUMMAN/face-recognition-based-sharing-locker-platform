package Jongsul.fabinetmaven.Mqtt;

public class MqttPubThreadRun extends Thread{
    public void run(){
        MqttPublishUserID mqttPublishUserID = new MqttPublishUserID();
        mqttPublishUserID.MqttPub();
    }
}
