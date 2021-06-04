package com.jongsul.fabinetgradle.Service;

import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.CabinetHistory;
import com.jongsul.fabinetgradle.Domain.Member;
import com.jongsul.fabinetgradle.Repository.CabinetHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CabinetHistoryServiceImpl implements CabinetHistoryService {

    private final CabinetHistoryRepository cabinetHistoryRepository;


    @Override
    public String save(Cabinet cabinet) {
        return null;
    }

    @Override
    public List<Cabinet> getDate(Member member) {
        return null;
    }

    @Override
    public List<Cabinet> findAllbyName(Member member) {
        return null;
    }

    @Override
    public List<Cabinet> getOneCabinet(String userID) {
        return null;
    }

    @Override
    public List<CabinetHistory> getAllCabinet(String userID) {
        return cabinetHistoryRepository.findAllbyName(userID);
    }
}
