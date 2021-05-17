package com.jongsul.fabinetgradle.AwsCollection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Face;
import com.amazonaws.services.rekognition.model.ListFacesRequest;
import com.amazonaws.services.rekognition.model.ListFacesResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class showFaceList {
    public static final String collectionId = "Collection";
    public static final String bucket = "fabinet";
    public static final String photo = "lsh.jpg";
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
//            for (Face face : faces) {
//                System.out.println("This: "+objectMapper.writerWithDefaultPrettyPrinter()
//                        .writeValueAsString(face));
//            }
            //컨트롤f로 Name 검색해서 이름만 확인하자
            for (Face face : faces) {
                System.out.println("Name: "+face.getExternalImageId());
            }
            System.out.println("==========================================================");
        } while (listFacesResult != null && listFacesResult.getNextToken() != null);
    }
}
