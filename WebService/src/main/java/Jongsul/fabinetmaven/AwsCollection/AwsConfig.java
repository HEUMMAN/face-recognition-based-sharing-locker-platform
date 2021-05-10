package Jongsul.fabinetmaven.AwsCollection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsConfig {

    static BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA4EPX72XC3ACFOWVU", "BnW0X9nSPsqJN07KXgjKreizEa4Q7BI4I9Qdrytd");
    static AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
            .standard()
            .withRegion(Regions.fromName("ap-northeast-2"))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();


    public static AmazonRekognition getAmazonRekognition() {
        return rekognitionClient;
    }
}
/*
    //LoginController에 있던 인증키 여기에 백업
    BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAID7ORZRGXAVUBEXA", "AXgZBzrc/y4KzejD35GLZomcjYkm/dti40s642hE");
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName("ap-northeast-2"))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .disableChunkedEncoding()
            .build();

 */