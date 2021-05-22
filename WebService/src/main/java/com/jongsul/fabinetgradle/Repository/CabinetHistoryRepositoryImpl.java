package com.jongsul.fabinetgradle.Repository;

import com.jongsul.fabinetgradle.Domain.Cabinet;
import com.jongsul.fabinetgradle.Domain.CabinetHistory;
import com.jongsul.fabinetgradle.Domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CabinetHistoryRepositoryImpl implements CabinetHistoryRepository{

    private final EntityManager em;

    @Override
    public String save(CabinetHistory cabinet) {
        em.persist(cabinet);
        return cabinet.getName();
    }

    @Override
    public List<CabinetHistory> findAllbyName(Member member) {
        log.info("사용했던 사물함 전체 내역 불러오기");
        return em.createQuery("select c from CabinetHistory c where c.member=:member", CabinetHistory.class)
                .setParameter("member",member).getResultList();
    }

    @Override
    public List<String> getAllCabinet() {
        return em.createQuery("select c.name from Cabinet c").getResultList();
    }

    @Override
    public List<Cabinet> getDate(Member member) {
        return em.createQuery("select m from Cabinet m where m.member = :name", Cabinet.class)
                .setParameter("name",member).getResultList();
    }
}
