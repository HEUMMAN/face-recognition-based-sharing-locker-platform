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
import java.util.*;

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
    private final List<String> CABINET_LIST
            = Arrays.asList(new String[]{"A-1-1", "A-1-2", "B-1-1", "B-1-2"});

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
    public List<String> getAvailableCabinet() {
        //A동, B동, C동만 하자
        //각 동에 1,2,3층있다
        //각 층에 1~10번 있다
        //프론트에서 사물함위치를 입력받을때 건물,층,번호로 분리해서 입력받지 말고 그냥 드롭다운 한개로만 받자(사물함예약 페이지의 남는여백에는 요금 정책 설명 텍스트)
        //db에 존재하는 사물함 사용 내역들을 List<Cabinet>으로 전부 가져오기
        //가져온 리스트에서 name속성을 추출(ex. B-2-9)
        //존재할수있는 모든 사물함위치들을 한곳에 미리 선언(A-1-1 ~ A-1-10, A-2-1 ~ A-2-1 이런식으로 쭉 따로 선언해두자)
        //선언해둔 집단에서 가져온 name속성을 뺀 결과(사용가능한 곳들)를 json으로 전송
        //사물함 위치를 입력받을때 건물,층,번호를 따로 받는것이 아닌 완성형태로 선택하는 드롭박스1개로 통일하자(A-1-1, A-1-2, A-1-3... 이런식으로 길게)
        List<String> availableCabinet = new ArrayList<>();
        List<String> usingCabinet = cabinetRepository.getAllCabinet();

        //deep copy
        for(String cabinetName : CABINET_LIST){
            availableCabinet.add(cabinetName);
        }
        //차집합
        availableCabinet.removeAll(usingCabinet);
        log.info("사용가능자리");
        for (String availableCabinetName : availableCabinet) {
            log.info(availableCabinetName);
        }
        return availableCabinet;
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
