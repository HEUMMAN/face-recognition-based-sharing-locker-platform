package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;
import Fabinet.Fabinet.Repository.CabinetRepository;
import Fabinet.Fabinet.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements CabinetService{

    private final CabinetRepository cabinetRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Cabinet> findAllByID(Member member){
        return cabinetRepository.findAllbyName(member);
    }

    @Override
    public String calculateBill(Board board) {
        return null;
    }

    @Override
    public long getBill(Member member) {
        List<Cabinet> usingCabinet = cabinetRepository.getDate(member); //로그인id(세션id)를 통해 해당 멤버 조회후 멤버로 사물함 사용내용 조회
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
        return calDate;
    }

    @Override
    @Transactional
    public String chooseCanibet(Cabinet cabinet) {
        cabinetRepository.save(cabinet);
        return "OK";
    }
}
