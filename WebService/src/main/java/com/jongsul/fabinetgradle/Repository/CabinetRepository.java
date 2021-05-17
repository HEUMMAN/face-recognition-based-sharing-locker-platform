package com.jongsul.fabinetgradle.Repository;



import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.Member;

import java.util.List;

public interface CabinetRepository {

    public void save(Cabinet cabinet);

    List<Cabinet> getDate(Member member);

    public List<Cabinet> findAllbyName(Member member);
}
