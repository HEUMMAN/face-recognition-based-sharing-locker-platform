package com.jongsul.fabinetgradle.AwsCollection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DeleteFacesRequest;
import com.amazonaws.services.rekognition.model.DeleteFacesResult;

import java.util.List;

public class DeleteFaceFromCollection {
    public static final String collectionId = "Collection";
    //컬랙션에서 지울 사진의 id
    public static final String faces[] = {"e64b05a5-29f2-446a-8d12-92fc50488ce9"
    ,"e431e60b-2da1-434a-9555-d7d3678a3519"
    ,"b18894b6-5b84-43b9-b6d3-4db0c0b63d1d"
    ,"64412b02-f159-4432-9186-412ee2f8e468"};

    public static void main(String[] args) throws Exception {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA4EPX72XC3ACFOWVU", "BnW0X9nSPsqJN07KXgjKreizEa4Q7BI4I9Qdrytd");
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.fromName("ap-northeast-2"))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        //AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();


        DeleteFacesRequest deleteFacesRequest = new DeleteFacesRequest()
                .withCollectionId(collectionId)
                .withFaceIds(faces);

        DeleteFacesResult deleteFacesResult = rekognitionClient.deleteFaces(deleteFacesRequest);


        List<String> faceRecords = deleteFacesResult.getDeletedFaces();
        System.out.println(Integer.toString(faceRecords.size()) + " face(s) deleted:");
        for (String face : faceRecords) {
            System.out.println("FaceID: " + face);
        }
    }
}
