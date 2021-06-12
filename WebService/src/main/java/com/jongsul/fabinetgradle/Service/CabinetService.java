package com.jongsul.fabinetgradle.Service;


import com.jongsul.fabinetgradle.DTO.CabinetDTO;
import com.jongsul.fabinetgradle.DTO.IamportApiDTO;
import com.jongsul.fabinetgradle.Domain.Board;
import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CabinetService {

    String calculateBill(Board board);
    IamportApiDTO getBill(String id);
    String chooseCanibet(CabinetDTO cabinetDTO, HttpServletRequest request);
    List<Cabinet> findAllByID(Member member);
    List<String> getAllCabinet();
    List<Cabinet> getCabinetName(String userID);
    List<Cabinet> getAllCabinetByName(String userID);
    void deleteCabinetByID(long id);
}
