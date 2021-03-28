package Fabinet.Fabinet.Controller;

import Fabinet.Fabinet.DTO.ImageDTO;
import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Domain.Image;
import Fabinet.Fabinet.Service.BoardService;
import Fabinet.Fabinet.Service.ImageService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final BoardService boardService;
    private final ImageService imageService;
    private HttpSession session;

    //게시판 페이지로
    @GetMapping("/toBoardList")
    public String toBoardList(Model model){
        log.info("게시판 페이지로 이동");
//        List<Board> boards = boardService.findBoards();
//        model.addAttribute("boards", boards);
        return "boardList";
    }

    //사물함 선택 페이지로
    @GetMapping("/toChooseCabinet")
    public String toCabinet(){
        log.info("사물함 페이지로 이동");
        return "cabinet";
    }

    @PostMapping("/imgUpload")
    public String upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException, SQLException {
        System.out.println(file);
        System.out.println(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());

        Image image = new Image();
        //HttpSession session = request.getSession();
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
        if(session != null) {
            session = request.getSession();
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
