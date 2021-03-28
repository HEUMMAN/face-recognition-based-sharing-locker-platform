package Fabinet.Fabinet.Controller;

import Fabinet.Fabinet.Service.CabinetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CabinetController {

    private final CabinetService cabinetService;

    //정산페이지 이전에 로그인 돼있는지 확인
    @GetMapping("/isPaymentLogined")
    public void isPaymentLogined(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("loginMemberId"));

        //로그인 안한 유저라면
        if(session.getAttribute("loginMemberId") == null){
            log.info("로그인 페이지로 이동");
            response.getWriter().write("-1");
        }
        else{
            log.info("정산페이지로 이동");
            response.getWriter().write("1");
        }
    }

    //정산페이지로
    @GetMapping("/toCheckBill")
    public String toPayment(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        log.info("정산확인페이지로 이동2");
        long passedTime = cabinetService.getBill(session.getAttribute("loginMemberId"));
        //넘어갈때 해당 회원이 지불해야할 돈을 여기서 계산한 후 보낸다
        // 서비스단에 요금 정산하는 함수 만들자 그거를 여기서도 호출하고 doPayment에서도 호출하자
        System.out.println("passedTime: "+passedTime);
        model.addAttribute("payMoney",passedTime);
        System.out.println("결제할 금액: "+passedTime*0.001541666);
        return "checkBill";
    }

    @PostMapping("/doPayment")
    public String doPayment(@RequestParam int money, HttpServletRequest request){
        HttpSession session = request.getSession();
        long passedTime = cabinetService.getBill(session.getAttribute("loginMemberId"));
        //3시간에 천원 => 1ms에 0.001541666원
        System.out.println("결제할 금액: "+passedTime*0.001541666);    //이거 에러뜬다
        log.info("결제 API 페이지 이동");
        return "payment";
    }
}
