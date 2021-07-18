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
    public static final String faces[] = {"6d141be4-2bdf-46d2-8deb-476877bef8e3"
    ,"6557b10b-1a94-44ae-b420-3b37e46eaa35"
    ,"fc67b4c0-0295-4f79-afba-7e411e27a072"};

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
