package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CabinetRepositoryImpl implements CabinetRepository{

    private final EntityManager em;

    @Override
    public void save(Board board) {
        
    }

    @Override
    public List<Board> findAll() {
        return null;
    }

    @Override
    public String getDate(Object loginMemberId) {
        //로그인 세션으로 해당 사용자가 사용 시작한 캐비냇들 가져옴
        return "2021-03-05 09:00:00";
    }
}
