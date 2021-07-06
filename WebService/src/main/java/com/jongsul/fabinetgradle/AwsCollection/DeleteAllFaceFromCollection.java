package com.jongsul.fabinetgradle.AwsCollection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class DeleteAllFaceFromCollection {
    public static final String collectionId = "Collection";
    public static void main(String[] args) throws Exception {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA4EPX72XC3ACFOWVU", "BnW0X9nSPsqJN07KXgjKreizEa4Q7BI4I9Qdrytd");
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.fromName("ap-northeast-2"))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        ListFacesResult listFacesResult = null;
        System.out.println("Faces in collection " + collectionId);

        String paginationToken = null;
        do {
            if (listFacesResult != null) {
                paginationToken = listFacesResult.getNextToken();
            }

            ListFacesRequest listFacesRequest = new ListFacesRequest()
                    .withCollectionId(collectionId)
                    .withMaxResults(1)
                    .withNextToken(paginationToken);

            listFacesResult = rekognitionClient.listFaces(listFacesRequest);
            List<Face> faces = listFacesResult.getFaces();

            for (Face face : faces) {
                System.out.println("얼굴정보");
                System.out.println("Name: "+face.getExternalImageId());
                System.out.println(face.getFaceId());
            }

            //delete
            //삭제할 얼굴 id가 담긴 String배열 생성
            List<String> faces2Delete = new ArrayList<>();
            for(Face face : faces){
                faces2Delete.add(face.getFaceId());
            }

            //삭제 요청
           DeleteFacesRequest deleteFacesRequest = new DeleteFacesRequest()
                    .withCollectionId(collectionId)
                    .withFaceIds(faces2Delete);

            DeleteFacesResult deleteFacesResult = rekognitionClient.deleteFaces(deleteFacesRequest);


            List<String> faceRecords = deleteFacesResult.getDeletedFaces();
            System.out.println(Integer.toString(faceRecords.size()) + " face(s) deleted:");
            for (String face : faceRecords) {
                System.out.println("FaceID: " + face);
            }

            System.out.println("==========================================================");
        } while (listFacesResult != null && listFacesResult.getNextToken() != null);
    }
}
