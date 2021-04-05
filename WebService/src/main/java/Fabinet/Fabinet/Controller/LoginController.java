package Fabinet.Fabinet.Controller;

import Fabinet.Fabinet.Config.SecurityUtil;
import Fabinet.Fabinet.DTO.LoginDTO;
import Fabinet.Fabinet.Domain.Image;
import Fabinet.Fabinet.Domain.Member;
import Fabinet.Fabinet.Service.ImageService;
import Fabinet.Fabinet.Service.ImageServiceImpl;
import Fabinet.Fabinet.Service.MemberService;
import Fabinet.Fabinet.Service.MemberServiceImpl;
import Fabinet.Fabinet.DTO.RegisterDTO;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*로그인
* 회원가입
* */
@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final ImageService imageService;

    BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAID7ORZRGXAVUBEXA", "AXgZBzrc/y4KzejD35GLZomcjYkm/dti40s642hE");
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName("ap-northeast-2"))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .disableChunkedEncoding()
            .build();


    @PostMapping("/login")
    public void doLogin(@RequestBody LoginDTO loginDTO, HttpServletResponse response, HttpSession session) throws IOException {

        System.out.println("Input id: "+loginDTO.getUserID()+", Input pw: "+loginDTO.getUserPW());

        //SHA256
        SecurityUtil sha = new SecurityUtil();
        String encryptPassword = sha.encryptSHA256(loginDTO.getUserPW());

        log.info("로그인 가능여부 판별");
        String result = memberService.login(loginDTO.getUserID(),encryptPassword);
        if(result.equals("F-2")){
            log.info("로그인실패 - 아이디에대한 비번이 일치하지 않는다");
            response.getWriter().write("F-2");
        }
        else if(result.equals("F-1")){  //아예 없는 아이디
            log.info("로그인실패 - 없는 아이디");
            response.getWriter().write("F-1");
        }
        else{
            log.info("로그인 성공");
            session.setAttribute("loginMemberId",loginDTO.getUserID());
            response.getWriter().write("Success");
        }
    }

    @PostMapping("/register")
    public void doRegister(@RequestBody RegisterDTO registerDTO, HttpServletResponse response) throws IOException {

        System.out.println("이름: "+registerDTO.getUserName());
        System.out.println("로그인 아이디: "+registerDTO.getUserID());
        System.out.println("로그인 비밀번호: "+registerDTO.getUserPW());
        System.out.println("로그인 비밀번호 확인: "+registerDTO.getUserPW2());
        System.out.println("전화번호: "+registerDTO.getUserTel());
        System.out.println("이메일: "+registerDTO.getUserEmail());
        System.out.println("이미지: "+registerDTO.getU_img());

        //SHA256
        SecurityUtil sha = new SecurityUtil();
        String encryptPassword = sha.encryptSHA256(registerDTO.getUserPW());
        System.out.println("인코딩된 비밀번호: "+ encryptPassword);

        //AJAX쪽 변수이름과 DTO의 변수이름이 같아야 받아짐
        String result = memberService.isExistId(registerDTO.getUserID()); //입력받은 id가 이미 사용중인지 확인 위함
        //available이면 사용가능한 id
        //occupied면 이미 사용중인 id
        if(result.equals("occupied")){
            System.out.println("중복 if문");
            response.getWriter().write("occupied");
            return;
        }
        if(!registerDTO.getUserPW().equals(registerDTO.getUserPW2())){    //비밀번호 확인이 틀릴경우
            System.out.println("비밀번호확인이 틀림");
            response.getWriter().write("wrongCheck");
            return;
        }
        System.out.println("중복검사 통과");
        Member member = new Member();
        member.setLoginId(registerDTO.getUserID());
        member.setLoginPassword(encryptPassword);
        member.setName(registerDTO.getUserName());
        member.setEmail(registerDTO.getUserEmail());
        member.setTel(registerDTO.getUserTel());

//        //python에서 얼굴조회를 해야하기에 얼굴테이블을 따로 분리하여 만들었다.
//        Image image = new Image();
//        image.setImage(registerVO.getU_img());
//        imageService.join(image);
        
        String afterJoin = memberService.join(member);
        System.out.println(afterJoin+" 가입 완료");
        response.getWriter().write("available");
    }
}
