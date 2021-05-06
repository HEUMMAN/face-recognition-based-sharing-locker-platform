package Jongsul.fabinetmaven.Service;


import Jongsul.fabinetmaven.Domain.Board;
import Jongsul.fabinetmaven.Domain.Cabinet;
import Jongsul.fabinetmaven.Domain.Member;

import java.util.List;

public interface CabinetService {

    public String calculateBill(Board board);

    long getBill(Member member);

    String chooseCanibet(Cabinet cabinet);

    public List<Cabinet> findAllByID(Member member);
}
