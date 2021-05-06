package Jongsul.fabinetmaven.Repository;


import Jongsul.fabinetmaven.Domain.Cabinet;
import Jongsul.fabinetmaven.Domain.Member;

import java.util.List;

public interface CabinetRepository {

    public void save(Cabinet cabinet);

    List<Cabinet> getDate(Member member);

    public List<Cabinet> findAllbyName(Member member);
}
