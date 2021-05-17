package com.jongsul.fabinetgradle.AwsCollection;

import com.amazonaws.services.rekognition.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class AddCollection {
    public static final String collectionId = "Collection";

    //AWS Client 인증키를 새로운 class로 분리 후 github에 업로드 안함, 테스트 필요

    //MultipartFile -> byte[] 변환 후 바로 Collection에 올리기
    public void addFace(MultipartFile mfile) throws IOException {
        String userName = mfile.getName();
        byte[] imageBytes = mfile.getBytes();
        ByteBuffer buf = ByteBuffer.wrap(imageBytes);
        Image image = new Image().withBytes(buf);

        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                .withImage(image)
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(1)
                .withCollectionId(collectionId)
                .withExternalImageId(userName)
                .withDetectionAttributes("DEFAULT");

        //IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);
        IndexFacesResult indexFacesResult = AwsConfig.getAmazonRekognition().indexFaces(indexFacesRequest);

        System.out.println("Results for " + userName);
        System.out.println("Faces indexed:");
        List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
        for (FaceRecord faceRecord : faceRecords) {
            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
        }

        List<UnindexedFace> unindexedFaces = indexFacesResult.getUnindexedFaces();
        System.out.println("Faces not indexed:");
        for (UnindexedFace unindexedFace : unindexedFaces) {
            System.out.println("  Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
            System.out.println("  Reasons:");
            for (String reason : unindexedFace.getReasons()) {
                System.out.println("   " + reason);
            }
        }
    }
}
