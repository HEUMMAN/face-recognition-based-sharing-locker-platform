package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;

import java.util.List;

public interface CabinetRepository {

    public void save(Cabinet cabinet);

    List<Cabinet> getDate(Member member);

    public List<Cabinet> findAllbyName(Member member);
}
