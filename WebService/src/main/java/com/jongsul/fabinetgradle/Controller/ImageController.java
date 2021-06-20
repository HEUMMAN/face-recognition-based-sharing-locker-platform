package com.jongsul.fabinetgradle.Controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.QualityFilter;
import com.jongsul.fabinetgradle.AwsCollection.AddCollection;
import com.jongsul.fabinetgradle.Config.MemberInformation;
import com.jongsul.fabinetgradle.Domain.Image;
import com.jongsul.fabinetgradle.Service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    public static final String collectionId = "Collection";
    private final MemberInformation memberInformation;
    private final ImageService imageService;
    private HttpSession session;

    BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIA4EPX72XC3ACFOWVU", "BnW0X9nSPsqJN07KXgjKreizEa4Q7BI4I9Qdrytd");
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
            .standard()
            .withRegion(Regions.fromName("ap-northeast-2"))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();

    @PostMapping("/imgUpload")
    public String upload(@RequestParam(name = "img") MultipartFile file, HttpServletRequest request) throws IOException, SQLException {

        //DB에 이미지 저장
        Image image = new Image();
        session = request.getSession();
        String sessionId = memberInformation.getUserName(request);
        log.info("사용자이름: "+sessionId);
        image.setName(sessionId);
        byte[] bytes;
        try {
            bytes = file.getBytes();
            try {
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                image.setImage(blob);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageService.join(image);

        //Collection에 추가
        //DB에서 현재 사용자의 사진을 가져와 바이트 배열형태로 변환
        String userID = memberInformation.getUserName(request);
        List<Image> findImage = imageService.findOne(userID);
        int blobLength = (int) findImage.get(0).getImage().length();
        byte[] byteImage = findImage.get(0).getImage().getBytes(1, blobLength);

        //바이트 배열형태의 사진을 컬랙션에 업로드
        ByteBuffer buf = ByteBuffer.wrap(byteImage);
        com.amazonaws.services.rekognition.model.Image image4Collection =
                new com.amazonaws.services.rekognition.model.Image().withBytes(buf);

        String photoName = userID;  //이 이름으로 업로르됨

        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                .withImage(image4Collection)
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(1)
                .withCollectionId(collectionId)
                .withExternalImageId(photoName)
                .withDetectionAttributes("DEFAULT");
        //컬랙션에 업로드
        IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);
        return "cabinet";
    }

    @RequestMapping("/getImage")
    @ResponseBody
    public byte[] downloadImg(HttpServletRequest request) throws IOException, SQLException {
        session =  request.getSession();
        if(session != null) {
            String sessionId = (String) session.getAttribute("loginMemberId");
            List<Image> findImage = imageService.findOne(sessionId);    //리스트로 가져오긴 했지만 기본적으로 회원당 1장의 사진만 올릴수 있도록 업데이트 쿼리로 바꾸자
            System.out.println("1: "+findImage.size());
            if(findImage.size() == 0){
                log.info("기본이미지 출력");
                List<Image> defaultImage = imageService.findOne("admin");
                int blobLength = (int) defaultImage.get(defaultImage.size()-1).getImage().length();
                byte[] byteImage = defaultImage.get(defaultImage.size()-1).getImage().getBytes(1, blobLength);
                return byteImage;
            }
            log.info("회원 이미지 출력");
            log.info("findImage: " + findImage);
            log.info("getID: " + findImage.get(0).getId());
            log.info("getImage: " + findImage.get(0).getImage());

            int blobLength = (int) findImage.get(findImage.size()-1).getImage().length();
            byte[] byteImage = findImage.get(findImage.size()-1).getImage().getBytes(1, blobLength);
            return byteImage;
        }
        else{
            return null;
        }
    }
}
