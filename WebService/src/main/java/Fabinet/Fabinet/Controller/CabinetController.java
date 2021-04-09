package Fabinet.Fabinet.Controller;

import Fabinet.Fabinet.DTO.BillDTO;
import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;
import Fabinet.Fabinet.Service.CabinetService;
import Fabinet.Fabinet.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("bill")
public class CabinetController {

    private BillDTO bill;
    private final double weight = 0.001541666;
    private final CabinetService cabinetService;
    private final MemberService memberService;
    private long passedTime;

    //사물함 예약 드롭다운 요소를 DB에서 가져온다
    //사용가능한 건물, 층, 번호를 계산해야함
    //DB의 cabinet테이블을 다 가져온다
    //'-'기준으로 사용중인 건물,층,번호를 알아낸다
    //(가능한 사물함을 모두 적어놓은 목록) - (cabinet테이블에 존재하는 사물함들) 의 값만 드롭다운에 출력(즉, 사용가능한 사물함칸들)
    //(가능한 사물함을 모두 적어놓은 목록)을 따로 클래스를 만들어서 미리 정의해놓자

    //사물함 사용시간을 LocalDateTime에서 Date로 바꿨다. 사물함 추가하기 기능 구현하고 확인해보자
    @GetMapping("list")
    public List<Cabinet> showEntireBill(HttpServletRequest request){
        HttpSession session = request.getSession();
        Member member = memberService.findOne((String) session.getAttribute("loginMemberId"));
        List<Cabinet> cabinets = cabinetService.findAllByID(member);    //멤버를 통해 해당 사용자가 사용중인 사물함 리스트 가져오기
        System.out.println("=====사용중인 사물함=====");
        for(Cabinet a : cabinets){
            System.out.println(a.getName());
        }

        return cabinets;
    }

    //요금 정산 361482526
    //ResponseEntity
    @GetMapping("/bill2")
    public BillDTO toPayment(Model model, HttpServletRequest request) {
        log.info("요금 정산");
        HttpSession session = request.getSession();
        System.out.println((String) session.getAttribute("loginMemberId"));
        Member member = memberService.findOne((String) session.getAttribute("loginMemberId"));
        passedTime = cabinetService.getBill(member);    //이거 전역변수로 했기때문에 초기화 주의해야함
        //넘어갈때 해당 회원이 지불해야할 돈을 여기서 계산한 후 보낸다
        // 서비스단에 요금 정산하는 함수 만들자 그거를 여기서도 호출하고 doPayment에서도 호출해야함
        System.out.println("사용한 시간: " + passedTime * 0.001 + "초");
        //model.addAttribute("payMoney", passedTime * weight);
        System.out.println("결제할 금액: " + passedTime * weight);

        bill = new BillDTO();
        bill.setMoney((int) (passedTime * weight));
        return bill;
    }

    @PostMapping("/pay")
    public void doPayment(HttpServletRequest request, Model model){
        System.out.println("결제 API 호출");
        System.out.println("결제할 금액: "+(int)(bill.getMoney()));
        model.addAttribute("money",(int)(bill.getMoney()));
        log.info("결제 API 페이지 이동");
        //return (int)(bill.getMoney());
    }

    //선택한 사물함 정보 받아서 DB반영
    @PostMapping("/chooseCabinet")
    public String chooseCabinet(@RequestParam(value="select1") String select1,
                                @RequestParam(value="select2") String select2,
                                @RequestParam(value="select3") String select3, HttpServletRequest request){
        System.out.println("select1: "+select1);
        System.out.println("select2: "+select2);
        System.out.println("select3: "+select3);

        HttpSession session = request.getSession();
        String sessionId = (String)session.getAttribute("loginMemberId");

        Cabinet cabinet = new Cabinet();
        cabinet.setName(select1+"-"+select2+"-"+select3);
        cabinet.setMember(memberService.findOne(sessionId));
        cabinet.setStartTime(new Date());
        cabinetService.chooseCanibet(cabinet);

        return "redirect:/";
    }
}
