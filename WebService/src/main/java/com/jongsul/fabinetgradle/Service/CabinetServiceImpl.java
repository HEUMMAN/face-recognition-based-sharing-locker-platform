package com.jongsul.fabinetgradle.Service;

import com.jongsul.fabinetgradle.Config.MemberInformation;
import com.jongsul.fabinetgradle.DTO.CabinetDTO;
import com.jongsul.fabinetgradle.Domain.Board;
import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.CabinetHistory;
import com.jongsul.fabinetgradle.Domain.Member;
import com.jongsul.fabinetgradle.Repository.CabinetHistoryRepository;
import com.jongsul.fabinetgradle.Repository.CabinetRepository;
import com.jongsul.fabinetgradle.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CabinetServiceImpl implements CabinetService{

    private final MemberInformation memberInformation;
    private final CabinetRepository cabinetRepository;
    private final CabinetHistoryRepository cabinetHistoryRepository;
    private final MemberRepository memberRepository;


    @Override
    public List<Cabinet> findAllByID(Member member){
        return cabinetRepository.findAllbyName(member);
    }

    @Override
    public List<String> getAllCabinet() {
        return cabinetRepository.getAllCabinet();
    }

    @Override
    public List<Cabinet> getCabinetName(String userID) {
        return cabinetRepository.getOneCabinet(userID);
    }

    @Override
    public String calculateBill(Board board) {
        return null;
    }

    @Override
    public long getBill(Member member) {
        /*List<Cabinet> usingCabinet = cabinetRepository.getDate(member); //로그인id(세션id)를 통해 해당 멤버 조회후 멤버로 사물함 사용내용 조회
        for (Cabinet a : usingCabinet) {
            System.out.println("현재 사용자가 점유하고있는 사물함: "+a.getName());
        }
        Cabinet tempOne = usingCabinet.get(0);  //한명이 여러개의 사물함 점유시 결제할 것들을 고를 수 있도록 구현해야함
        long calDate = -1;
        Date now = new Date();
        System.out.println("now: "+now);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

            //시작시간
            Date FirstDate = format.parse(tempOne.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            //끝시간(현재시간)
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Date SecondDate = format.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            System.out.println("시작시간: " + FirstDate);
            System.out.println("종료시간: "+SecondDate);

            calDate = SecondDate.getTime() - FirstDate.getTime();
            System.out.println(calDate);    //밀리초단위로 나온다
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return calDate;*/
        return 1L;
    }

    @Override
    @Transactional
    public String chooseCanibet(CabinetDTO cabinetDTO, HttpServletRequest request) {
        log.info("선택한 사물함 번호: "+cabinetDTO.getSelectOne());
        String[] temp = cabinetDTO.getSelectOne().split("-");
        Cabinet cabinet = Cabinet.builder()
                .building(temp[0])
                .floor(temp[1])
                .number(temp[2])
                .name(cabinetDTO.getSelectOne())
                .member(memberRepository.findOne(memberInformation.getUserName(request)))
                .startTime(new Date())
                .build();
        CabinetHistory cabinetHistory = CabinetHistory.builder()
                .building(temp[0])
                .floor(temp[1])
                .number(temp[2])
                .name(cabinetDTO.getSelectOne())
                .member(memberRepository.findOne(memberInformation.getUserName(request)))
                .startTime(new Date())
                .build();
        cabinetRepository.save(cabinet);
        cabinetHistoryRepository.save(cabinetHistory);
        return cabinet.getName();
    }
}
