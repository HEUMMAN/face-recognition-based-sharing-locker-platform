package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.DTO.BillDTO;
import com.jongsul.fabinetgradle.DTO.CabinetDTO;
import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.Member;
import com.jongsul.fabinetgradle.Service.CabinetService;
import com.jongsul.fabinetgradle.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final List<String> CABINET_LIST
            = Arrays.asList(new String[]{"A-1-1", "A-1-2", "B-1-1", "B-1-2"});

    //사물함 사용시간을 LocalDateTime에서 Date로 바꿨다. 사물함 추가하기 기능 구현하고 확인해보자
    @GetMapping("/list")
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
    public void chooseCabinet(@RequestBody CabinetDTO cabinetDTO,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        log.info(cabinetService.chooseCanibet(cabinetDTO,request)+"번 사물함 사용 시작");
        response.getWriter().write("available");
    }

    @GetMapping("/cabinets")
    public ResponseEntity<List<String>> getCurrentAvailableCabinet(){

        //A동, B동, C동만 하자
        //각 동에 1,2,3층있다
        //각 층에 1~10번 있다
        //프론트에서 사물함위치를 입력받을때 건물,층,번호로 분리해서 입력받지 말고 그냥 드롭다운 한개로만 받자(사물함예약 페이지의 남는여백에는 요금 정책 설명 텍스트)
        //db에 존재하는 사물함 사용 내역들을 List<Cabinet>으로 전부 가져오기
        //가져온 리스트에서 name속성을 추출(ex. B-2-9)
        //존재할수있는 모든 사물함위치들을 한곳에 미리 선언(A-1-1 ~ A-1-10, A-2-1 ~ A-2-1 이런식으로 쭉 따로 선언해두자)
        //선언해둔 집단에서 가져온 name속성을 뺀 결과(사용가능한 곳들)를 json으로 전송
        //사물함 위치를 입력받을때 건물,층,번호를 따로 받는것이 아닌 완성형태로 선택하는 드롭박스1개로 통일하자(A-1-1, A-1-2, A-1-3... 이런식으로 길게)

        List<String> usingCabinet = cabinetService.getAllCabinet();
        List<String> entireCabinet = new ArrayList<>();

        //deep copy
        for(String cabinetName : CABINET_LIST){
            entireCabinet.add(cabinetName);
        }
        //차집합
        entireCabinet.removeAll(usingCabinet);
        log.info("사용가능자리");
        for (String availableCabinet : entireCabinet) {
            log.info(availableCabinet);
        }
        return ResponseEntity.ok(entireCabinet);
    }
}
