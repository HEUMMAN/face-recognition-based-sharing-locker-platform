package com.jongsul.fabinetgradle.Controller;

import com.jongsul.fabinetgradle.Config.MemberInformation;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final CabinetService cabinetService;
    private final MemberService memberService;
    private final MemberInformation memberInformation;

    @GetMapping("/list")
    public ResponseEntity<List<Cabinet>> showEntireBill(HttpServletRequest request){
        Member member = memberService.findOne(memberInformation.getUserName(request));
        List<Cabinet> cabinets = cabinetService.findAllByID(member);
        return ResponseEntity
                .ok(cabinets);
    }

    //사물함 사용 중지(삭제)
    @Transactional
    @PostMapping("/stopUsing/{id}")
    public ResponseEntity deleteCabinet(@PathVariable long id) {
        log.info("실행 중지처리할 캐비넷 아이디: "+id);
        cabinetService.deleteCabinetByID(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    //선택한 사물함 정보 받아서 DB반영
    @PostMapping("/chooseCabinet")
    public ResponseEntity chooseCabinet(@RequestBody CabinetDTO cabinetDTO,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        log.info(cabinetService.chooseCanibet(cabinetDTO,request)+"번 사물함 사용 시작");
        response.getWriter().write("available");
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/cabinets")
    public ResponseEntity<List<String>> getCurrentAvailableCabinet(){
        log.info("getAvailableCabinet 실행");
        return ResponseEntity
                .ok(cabinetService.getAvailableCabinet());
    }

    @GetMapping("/mqtt")
    public ResponseEntity<String> getCabinetByName(String userID) {
        log.info("getCabinetByName 실행");
        List<Cabinet> getCabinets = cabinetService.getCabinetName(userID);
        return ResponseEntity
                .ok(getCabinets.get(0).getName());
    }
}
