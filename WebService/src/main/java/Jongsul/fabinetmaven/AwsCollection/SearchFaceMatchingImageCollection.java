package Jongsul.fabinetmaven.AwsCollection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;

public class SearchFaceMatchingImageCollection {
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

        //이 사진과 일치하는 사진이 컬렉션에 있으면 유사도 나옴
        File f = new File("C:\\Users\\lsh97\\Desktop\\lshimg\\facepby.jpg");
        byte[] imageBytes = FileUtils.readFileToByteArray(f);
        ByteBuffer buf = ByteBuffer.wrap(imageBytes);
        Image image = new Image().withBytes(buf);

        // Search collection for faces similar to the largest face in the image.
        SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest()
                .withCollectionId(collectionId)
                .withImage(image)
                .withFaceMatchThreshold(70F)
                .withMaxFaces(2);

        SearchFacesByImageResult searchFacesByImageResult =
                rekognitionClient.searchFacesByImage(searchFacesByImageRequest);

        System.out.println("Faces matching largest face in image from " + photo);
        List< FaceMatch > faceImageMatches = searchFacesByImageResult.getFaceMatches();
        for (FaceMatch face: faceImageMatches) {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(face));
            System.out.println();
        }
    }
}
