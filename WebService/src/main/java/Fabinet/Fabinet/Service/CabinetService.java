package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;

import java.util.List;

public interface CabinetService {

    public String calculateBill(Board board);

    long getBill(Member member);

    String chooseCanibet(Cabinet cabinet);

    public List<Cabinet> findAllByID(Member member);
}
