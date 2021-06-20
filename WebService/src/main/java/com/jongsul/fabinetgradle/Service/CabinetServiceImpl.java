package com.jongsul.fabinetgradle.Service;

import com.jongsul.fabinetgradle.Config.MemberInformation;
import com.jongsul.fabinetgradle.DTO.CabinetDTO;
import com.jongsul.fabinetgradle.DTO.IamportApiDTO;
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
import java.util.Calendar;
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
    private final long THREE_HOURS_SEC = 10800;
    private final long ONE_HOUR_SEC = 3600;

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
    public List<Cabinet> getAllCabinetByName(String userID) {
        return cabinetRepository.getAllCabinet(userID);
    }

    @Override
    @Transactional
    public void deleteCabinetByID(long id) {
        log.info("deleteCabinetByID실행");
        Date now = new Date();
        Cabinet findCabinet = cabinetRepository.getOneCabinetById(id);
        CabinetHistory cabinetHistory = CabinetHistory.builder()
                .building(findCabinet.getBuilding())
                .floor(findCabinet.getFloor())
                .number(findCabinet.getNumber())
                .name(findCabinet.getName())
                .member(findCabinet.getMember())
                .startTime(findCabinet.getStartTime())
                .endTime(now)
                .build();
        cabinetRepository.delete(findCabinet);
        cabinetRepository.updateHistory(cabinetHistory);
    }

    @Override
    public String calculateBill(Board board) {
        return null;
    }

    //해당 사물함 사용 요금 계산
    @Override
    public IamportApiDTO getBill(String id) {
        Cabinet cabinet = cabinetRepository.getOneCabinetById(Long.parseLong(id));
        System.out.println("해당 사용자 시작시간: "+cabinet.getStartTime());

        Calendar getToday = Calendar.getInstance();
        getToday.setTime(new Date()); //금일 날짜
        Calendar cmpDate = Calendar.getInstance();
        cmpDate.setTime(cabinet.getStartTime()); //특정 일자

        long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
        System.out.println("초 차이: "+diffSec);
        long returnMoney = -1;
        if(diffSec < THREE_HOURS_SEC){
            returnMoney = 3000;
        }
        else if(diffSec >= THREE_HOURS_SEC){
            returnMoney = 3000 + ((diffSec - THREE_HOURS_SEC) / ONE_HOUR_SEC)*500;
        }
        //가격정책
        //최초 3시간동안은 3000원
        //이후 한시간마다 500원 추가
        IamportApiDTO iamportApiDTO = new IamportApiDTO();
        iamportApiDTO.setEmail(cabinet.getMember().getEmail());
        iamportApiDTO.setTel(cabinet.getMember().getTel());
        iamportApiDTO.setName(cabinet.getMember().getName());
        iamportApiDTO.setMoney(returnMoney);
        iamportApiDTO.setNum(Long.parseLong(id));
        log.info("지불할 금액: "+returnMoney);
        return iamportApiDTO;
    }

    @Override
    @Transactional
    public String chooseCanibet(CabinetDTO cabinetDTO, HttpServletRequest request) {
        log.info("선택한 사물함 번호: "+cabinetDTO.getSelectOne());
        Date now = new Date();
        String[] temp = cabinetDTO.getSelectOne().split("-");
        Cabinet cabinet = Cabinet.builder()
                .building(temp[0])
                .floor(temp[1])
                .number(temp[2])
                .name(cabinetDTO.getSelectOne())
                .member(memberRepository.findOne(memberInformation.getUserName(request)))
                .startTime(now)
                .build();
        cabinetRepository.save(cabinet);
        return cabinet.getName();
    }
}
