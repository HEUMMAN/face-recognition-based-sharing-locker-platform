package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Board;

import java.time.LocalDateTime;
import java.util.List;

public interface CabinetRepository {

    public void save(Board board);

    public List<Board> findAll();

    String getDate(Object loginMemberId);
}
