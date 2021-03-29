package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface CabinetRepository {

    public void save(Cabinet cabinet);

    public List<Board> findAll();

    List<Cabinet> getDate(Member member);
}
