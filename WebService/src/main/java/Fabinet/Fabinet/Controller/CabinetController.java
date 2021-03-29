package Fabinet.Fabinet.Controller;

import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;
import Fabinet.Fabinet.Service.CabinetService;
import Fabinet.Fabinet.Service.MemberService;
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
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CabinetController {

    private final CabinetService cabinetService;
    private final MemberService memberService;
    private long passedTime;

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
        Member member = memberService.findOne((String)session.getAttribute("loginMemberId"));
        passedTime = cabinetService.getBill(member);    //이거 전역변수로 했기때문에 초기화 주의해야함
        //넘어갈때 해당 회원이 지불해야할 돈을 여기서 계산한 후 보낸다
        // 서비스단에 요금 정산하는 함수 만들자 그거를 여기서도 호출하고 doPayment에서도 호출해야함
        System.out.println("사용한 시간: "+passedTime*0.001+"초");
        model.addAttribute("payMoney",passedTime*0.001541666);
        System.out.println("결제할 금액: "+passedTime*0.001541666);
        return "checkBill";
    }

    @PostMapping("/doPayment")
    public String doPayment(HttpServletRequest request, Model model){
        System.out.println("결제 API 호출");
        System.out.println("결제할 금액: "+(int)(passedTime*0.001541666));
        model.addAttribute("money",(int)(passedTime*0.001541666));
        log.info("결제 API 페이지 이동");
        return "payment";
    }

    //선택한 사물함 정보 받아서 DB반영
    @PostMapping("/chooseCabinet")
    public String chooseCabinet(@RequestParam(value="select1") String select1,
                                @RequestParam(value="select2") String select2,
                                @RequestParam(value="select3") String select3,HttpServletRequest request){
        System.out.println("select1: "+select1);
        System.out.println("select2: "+select2);
        System.out.println("select3: "+select3);

        HttpSession session = request.getSession();
        String sessionId = (String)session.getAttribute("loginMemberId");

        Cabinet cabinet = new Cabinet();
        cabinet.setName(select1+"-"+select2+"-"+select3);
        cabinet.setMember(memberService.findOne(sessionId));
        cabinet.setStartTime(LocalDateTime.now());
        cabinetService.chooseCanibet(cabinet);

        return "redirect:/";
    }
}
