package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.Domain.Board;
import com.jongsul.fabinetgradle.Exception.BoardNotFoundException;
import com.jongsul.fabinetgradle.Mqtt.MqttSubscribeUserID;
import com.jongsul.fabinetgradle.Mqtt4Spring.MqttConfig;
import com.jongsul.fabinetgradle.Service.BoardService;
import com.jongsul.fabinetgradle.Service.CabinetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final CabinetService cabinetService;
    private final BoardService boardService;

    @RequestMapping("/")    //첫 화면
    public String home(){
        log.info("home controller");
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

    //회원가입시 사진등록폼으로
    @GetMapping("/imageUpload")
    public String toimageUpload(){
        log.info("회원가입시 사진등록폼으로 이동");
        return "imageupload";
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

    //개인정보 페이지로
    @GetMapping("/mypage")
    public String privatePage(){
        log.info("개인 페이지로 이동");
        return "mypage";
    }

    //수동개방 페이지로 이동
    @GetMapping("/open")
    public String openPage(){
        log.info("수동 개방 페이지로 이동");
        return "open";
    }

    //결제 api 호출
    @GetMapping("/bill/pay/{id}")
    public String PaymentPage(@PathVariable("id") String id, Model model){
        log.info(id+"번에 대한 결제 API 페이지 이동");
        model.addAttribute("userInfo",cabinetService.getBill(id));
        return "payment";
    }

    //사물함 사용 종료 api 호출 페이지로
    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable("id") long id, Model model){
        log.info("삭제 페이지로 이동 들어온 번호: "+id);
        model.addAttribute("num",id);
        return "stopUsing";
    }

    //수동 개방
    @GetMapping("/bill/open/{name}")    //name값으로 사용자 ID가 들어옴
    public String doOpenMqtt(@PathVariable("name") String name){
        log.info(name+" 사물함 개방");    //name을 넘겨줘서 Mqtt메시지를 만들어서 전송하면됨 (A-1-1)이렇게
        MqttConfig sendMessage = new MqttConfig();
        sendMessage.selfOpenMqttSender(name);
        return "redirect:/";
    }

    //게시글 1개 불러오기
    @GetMapping("/board/{id}")
    public String showOneBoard(@PathVariable int id, Model model){
        log.info(id+"번 게시글 불러오기");
        Board board = boardService.findOne(id);
        //존재하지 않는 게시물 조회시 예외 상태코드 반환
        if (board == null) {
            throw new BoardNotFoundException(String.format("ID[%s] not found",id));
        }
        model.addAttribute("board",board);
        return "board-info";
    }
}
