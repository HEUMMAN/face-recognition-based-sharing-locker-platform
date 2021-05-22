package com.jongsul.fabinetgradle.Repository;



import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.CabinetHistory;
import com.jongsul.fabinetgradle.Domain.Member;

import java.util.List;

public interface CabinetRepository {

    String save(Cabinet cabinet);
    List<Cabinet> getDate(Member member);
    List<Cabinet> findAllbyName(Member member);
    List<String> getAllCabinet();
}
