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
    public static final String faces[] = {"06e1c661-eb36-46e6-bc16-ce8d50d095f6"
    ,"8e019913-84a5-451a-96c7-0b0ca9d0be31"
    ,"d5d31ab2-fe33-4295-af8f-943fe4619f92"
    ,"dd73532f-ab09-4c57-b785-0372899f13c0"};

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
