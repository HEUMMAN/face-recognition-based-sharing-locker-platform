package Fabinet.Fabinet.Controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*로그인
* 회원가입
* */
@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final ImageService imageService;

    BasicAWSCredentials awsCreds = new BasicAWSCredentials("key", "key");
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName("ap-northeast-2"))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .disableChunkedEncoding()
            .build();

    @PostMapping("/doLogin")
    public void doLogin(@RequestParam String u_id, @RequestParam String u_pw, HttpServletRequest request , HttpServletResponse response, HttpSession session) throws IOException {
//        String id = request.getParameter("mid");
//        String password = request.getParameter("psw");
        System.out.println("Input id: "+u_id+", Input pw: "+u_pw);

        log.info("로그인 가능여부 판별");
        String result = memberService.login(u_id,u_pw);
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
            session.setAttribute("loginMemberId",u_id);
            response.getWriter().write("Success");
        }
    }

    //회원가입폼으로
    @GetMapping("/createAccount")
    public String toCreateAccount(){
        log.info("회원가입 페이지로 이동");
        return "register";
    }

    //로그인화면으로
    @GetMapping("/login")
    public String toMain(){
        log.info("로그인 페이지로 이동");
        return "index";
    }

    @PostMapping("/doRegister")
    public void doRegister(@RequestBody RegisterDTO registerVO, HttpServletResponse response) throws IOException {

        System.out.println("이름: "+registerVO.getU_name());
        System.out.println("로그인 아이디: "+registerVO.getU_id());
        System.out.println("로그인 비밀번호: "+registerVO.getU_pw());
        System.out.println("로그인 비밀번호 확인: "+registerVO.getU_pw2());
        System.out.println("전화번호: "+registerVO.getU_tel());
        System.out.println("이메일: "+registerVO.getU_email());
        System.out.println("이미지: "+registerVO.getU_img());
        //AJAX쪽 변수이름과 DTO의 변수이름이 같아야 받아짐
        String result = memberService.isExistId(registerVO.getU_id()); //입력받은 id가 이미 사용중인지 확인 위함
        //available이면 사용가능한 id
        //occupied면 이미 사용중인 id
        if(result.equals("occupied")){
            System.out.println("중복 if문");
            response.getWriter().write("occupied");
            return;
        }
        if(!registerVO.getU_pw().equals(registerVO.getU_pw2())){    //비밀번호 확인이 틀릴경우
            System.out.println("비밀번호확인이 틀림");
            response.getWriter().write("wrongCheck");
            return;
        }
        System.out.println("중복검사 통과");
        Member member = new Member();
        member.setLoginId(registerVO.getU_id());
        member.setLoginPassword(registerVO.getU_pw());
        member.setName(registerVO.getU_name());
        member.setEmail(registerVO.getU_email());
        member.setTel(registerVO.getU_tel());

//        //python에서 얼굴조회를 해야하기에 얼굴테이블을 따로 분리하여 만들었다.
//        Image image = new Image();
//        image.setImage(registerVO.getU_img());
//        imageService.join(image);
        
        String afterJoin = memberService.join(member);
        System.out.println(afterJoin+" 가입 완료");
        response.getWriter().write("available");
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("로그아웃");
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
