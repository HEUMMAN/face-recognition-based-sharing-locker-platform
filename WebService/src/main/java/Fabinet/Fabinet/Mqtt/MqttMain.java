package Fabinet.Fabinet.Mqtt;

import java.util.HashMap;
import java.util.function.Consumer;

public class MqttMain {
    public static void main(String [] args){

        //메시지(해시맵)를 받는 콜백 행위
        final Consumer<HashMap<Object, Object>> pdk = (arg)->{
            arg.forEach((key, value)->{
                System.out.println( String.format("메시지 도착 : 키 -> %s, 값 -> %s", key, value) );
            });
        };

        //해당 함수를 생성자로 넣어줌
        MyMqttClient client = new MyMqttClient(pdk);

        client.init("heum", "접속비번", "tcp://3.34.255.198:1883", "고유아이디")
                .subscribe(new String[]{"구독할주제1","구독할주제2"});


        //콜백행위1, 서버와의 연결이 끊기면 동작
        client.initConnectionLost( (arg)->{
            arg.forEach((key, value)->{
                System.out.println( String.format("커넥션 끊김~! 키 -> %s, 값 -> %s", key, value) );
            });
        });


        //콜백행위2, 메시지를 전송한 이후 동작
        client.initDeliveryComplete((arg)-> {
            arg.forEach((key, value)->{
                System.out.println( String.format("메시지 전달 완료~! 키 -> %s, 값 -> %s", key, value) );
            });
        });


        new Thread( ()->{
            try {
                Thread.sleep(9000);
                //client.subscribe(주제넣자)
                client.sender("new_topic", "Hello world! 한글한글!");  //이런식으로 보낸다.
                client.close();  //종료는 이렇게
            } catch (Exception e) {
                e.printStackTrace();
            }
        } ).start();
    }
}
