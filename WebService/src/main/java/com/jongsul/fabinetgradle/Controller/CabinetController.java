package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.Config.MemberInformation;
import com.jongsul.fabinetgradle.DTO.BillDTO;
import com.jongsul.fabinetgradle.DTO.CabinetDTO;
import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.Member;
import com.jongsul.fabinetgradle.Mqtt4Spring.MqttConfig;
import com.jongsul.fabinetgradle.Service.CabinetService;
import com.jongsul.fabinetgradle.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    private final MemberInformation memberInformation;
    private int money = -1;

    //사물함 사용시간을 LocalDateTime에서 Date로 바꿨다. 사물함 추가하기 기능 구현하고 확인해보자
    @GetMapping("/list")
    public List<Cabinet> showEntireBill(HttpServletRequest request){
        Member member = memberService.findOne(memberInformation.getUserName(request));
        List<Cabinet> cabinets = cabinetService.findAllByID(member);    //멤버를 통해 해당 사용자가 사용중인 사물함 리스트 가져오기
        System.out.println("=====사용중인 사물함=====");
        Date now = new Date();

        for(Cabinet a : cabinets){
            System.out.println("현재시각: "+ now.getTime());
            System.out.println("사용시각: "+ a.getStartTime().getTime());
            long diffDate = now.getTime() - a.getStartTime().getTime();
            System.out.println("diffDate: "+diffDate);
            money += diffDate;
            System.out.println(a.getName());
        }
        System.out.println("계산된값: "+Math.floor(money*0.00001541666/10)*10+1000);
        System.out.println("money: "+money);
        return cabinets;
    }

    //사물함 사용 중지(삭제)
    @Transactional
    @PostMapping("/stopUsing/{id}")
    public void deleteCabinet(@PathVariable long id) {
        System.out.println("여까진옴");
        System.out.println("실행 중지처리할 캐비넷 아이디: "+id);
        cabinetService.deleteCabinetByID(id);
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

    @GetMapping("/mqtt")
    public String getCabinetByName(String userID) {
        log.info("getCabinetByName 실행");
        List<Cabinet> getCabinets = cabinetService.getCabinetName(userID);
        return getCabinets.get(0).getName();
    }
}
