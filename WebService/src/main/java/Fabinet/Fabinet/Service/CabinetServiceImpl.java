package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Repository.BoardRepository;
import Fabinet.Fabinet.Repository.CabinetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CabinetServiceImpl implements CabinetService{

    private final CabinetRepository cabinetRepository;

    @Override
    public String calculateBill(Board board) {
        return null;
    }

    @Override
    public long getBill(Object loginMemberId) {
        String time = cabinetRepository.getDate(loginMemberId);
        long calDate = -1;
        Date now = new Date();
        System.out.println("now: "+now);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

            Date FirstDate = format.parse(time);
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Date SecondDate = format.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println("FirstDate: " + FirstDate);
            System.out.println("SecondDate: "+SecondDate);

            calDate = SecondDate.getTime() - FirstDate.getTime();
            System.out.println(calDate);    //밀리초단위로 나온다
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return calDate;
    }
}
