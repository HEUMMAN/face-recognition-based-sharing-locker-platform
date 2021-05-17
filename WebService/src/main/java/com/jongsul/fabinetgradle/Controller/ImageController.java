package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.AwsCollection.AddCollection;
import com.jongsul.fabinetgradle.Domain.Image;
import com.jongsul.fabinetgradle.Service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;
    private HttpSession session;

    @PostMapping("/imgUpload")
    public String upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException, SQLException {
        log.info("이미지 형식: "+file.getContentType());
        log.info("이미지 크기: "+file.getSize());
        log.info("이미지 이름: "+file.getOriginalFilename());

        log.info("Collection에 추가");
        AddCollection addCollection = new AddCollection();
        addCollection.addFace(file);

        Image image = new Image();
        session = request.getSession();
        String sessionId = (String)session.getAttribute("loginMemberId");
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
        return "redirect:/";
    }

    @RequestMapping("/getImage")
    @ResponseBody
    public byte[] downloadImg(HttpServletRequest request) throws IOException, SQLException {
        session =  request.getSession();
        if(session != null) {
            String sessionId = (String) session.getAttribute("loginMemberId");
            List<Image> findImage = imageService.findOne(sessionId);    //리스트로 가져오긴 했지만 기본적으로 회원당 1장의 사진만 올릴수 있도록 업데이트 쿼리로 바꾸자
            System.out.println("findImage: " + findImage);
            System.out.println("getID: " + findImage.get(0).getId());
            System.out.println("getImage: " + findImage.get(0).getImage());

            int blobLength = (int) findImage.get(0).getImage().length();
            byte[] byteImage = findImage.get(0).getImage().getBytes(1, blobLength);
            return byteImage;
        }
        else{
            return null;
        }
    }
}
