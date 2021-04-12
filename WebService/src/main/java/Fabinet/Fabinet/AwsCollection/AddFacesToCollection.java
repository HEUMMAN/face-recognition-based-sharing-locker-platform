package Fabinet.Fabinet.AwsCollection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.QualityFilter;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.UnindexedFace;
import com.amazonaws.services.rekognition.model.transform.IndexFacesResultJsonUnmarshaller;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.JSONType;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;

public class AddFacesToCollection {
    public static final String collectionId = "Collection";
    public static final String bucket = "bucket";
    public static final String photo = "lshlsh.jpg";
    public static final byte[] Bytephoto = {112,114,116};

    public static void main(String[] args) throws Exception {

<<<<<<< HEAD
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA4EPX72XC3ACFOWVU", "BnW0X9nSPsqJN07KXgjKreizEa4Q7BI4I9Qdrytd");
//        private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
//                .withRegion(Regions.fromName("ap-northeast-2"))
//                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//                .disableChunkedEncoding()
//                .build();
=======
        AWSCredentials credentials = new BasicAWSCredentials("key", "key");
>>>>>>> 71c7d9b1864840d18b276f09551d4127d5dde78e

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.fromName("ap-northeast-2"))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();



        Image image = new Image()
                .withS3Object(new S3Object()
                        .withBucket(bucket)
                        .withName(photo));


//        String json = "{'Bytes' : Bytephoto}";
//        Base64.Encoder encoder = Base64.getEncoder();
//        byte[] encodedBytes = encoder.encode(Bytephoto);
//        ByteArrayInputStream bis = new ByteArrayInputStream(Bytephoto);
//        BufferedImage bImage2 = ImageIO.read(bis);
//
//        ByteBuffer buf = ByteBuffer.wrap(encodedBytes);
//        //Image image = new Image().withBytes(buf);
        JSONObject img = new JSONObject();
        img.put("Bytes", Bytephoto);

        JSONObject json = new JSONObject();
        json.put("CollectionId",  "Collection"); // key는 "name", value는 "송요시"
        json.put("DetectionAttributes", "DEFAULT");
        json.put("ExternalImageId", "lshlsh.jpg");
        json.put("Image", img);
        json.put("MaxFaces", 1);
        json.put("QualityFilter", "AUTO");

        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                //.withImage(photo)   //여기 바로 바이트배열로 넣자
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(1)
                .withCollectionId(collectionId)
                .withExternalImageId(photo)
                .withDetectionAttributes("DEFAULT");


        //var jsonn = "Bytes": Bytephoto;
//        IndexFacesResult indexFacesResult = rekognitionClient
//                .indexFaces(collectionId,"DEFAULT",photo,{"Bytes": Bytephoto},1,"AUTO");

        IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);

        System.out.println("Results for " + photo);
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
