package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.Mqtt.MqttSubscribeUserID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class HomeController {
    @RequestMapping("/")    //첫 화면
    public String home(){
        log.info("home controller");
//        log.info("Mqtt Subscriber 실행");
        return "index";
    }

    //게시판 페이지로
    @GetMapping("/board")
    public String BoardList(){
        log.info("게시판 페이지로 이동");
        return "board";
    }

    //게시글쓰기 페이지로 이동
    @GetMapping("/board/createBoard")
    public String toCreateBoardList() {
        log.info("게시글작성 페이지로 이동!");
        return "create-board";
    }

    //회원가입폼으로
    @GetMapping("/register")
    public String toCreateAccount(){
        log.info("회원가입 페이지로 이동");
        return "register";
    }

    //로그인화면으로
    @GetMapping("/login")
    public String toMain(){
        log.info("로그인 페이지로 이동(수정)");
        return "login";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("로그아웃");
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    //사물함 선택 페이지로
    @GetMapping("/cabinet")
    public String toCabinet(){
        log.info("사물함 페이지로 이동");
        return "cabinet";
    }

    //정산페이지로
    @GetMapping("/bill")
    public String toPayment() {
        log.info("정산 페이지로 이동");
        return "bill";
    }

    //결제 api 호출
    @GetMapping("/adjustment/pay")
    public String doPayment(HttpServletRequest request, Model model){
        log.info("결제 API 페이지 이동");
        return "payment";
    }

    //개인정보 페이지로
    @GetMapping("/mypage")
    public String privatePage(){
        log.info("개인 페이지로 이동");
        return "mypage";
    }
}
