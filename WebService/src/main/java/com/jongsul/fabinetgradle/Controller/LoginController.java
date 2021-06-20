package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.Config.SecurityUtil;
import com.jongsul.fabinetgradle.DTO.LoginDTO;
import com.jongsul.fabinetgradle.DTO.RegisterDTO;
import com.jongsul.fabinetgradle.Service.ImageService;
import com.jongsul.fabinetgradle.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    private final String salt = "haha";

    @PostMapping("/login")
    public void doLogin(@RequestBody LoginDTO loginDTO, HttpServletResponse response, HttpSession session) throws IOException {

        log.info("Input id: "+loginDTO.getUserID()+", Input pw: "+loginDTO.getUserPW());

        //SHA256
        SecurityUtil sha = new SecurityUtil();
        String encryptPassword = sha.encryptSHA256(loginDTO.getUserPW()+salt);

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
    public void doRegister(@RequestBody RegisterDTO registerDTO, HttpServletResponse response, HttpSession session) throws IOException {

        log.info(registerDTO.toString());

        String result = memberService.isExistId(registerDTO.getUserID());
        if(!registerDTO.getUserPW().equals(registerDTO.getUserPW2())){    //비밀번호 확인이 틀릴경우
            log.info("비밀번호확인이 틀림");
            response.getWriter().write("wrongCheck");
            return;
        }
        if(result.equals("occupied")){
            log.info("이미 등록된 ID");
            response.getWriter().write("occupied");
            return;
        }
        else{
            log.info("중복검사 통과");
            log.info(memberService.join(registerDTO)+"가입 완료");
            session.setAttribute("loginMemberId",registerDTO.getUserID());
            response.getWriter().write("available");
        }
    }
}
