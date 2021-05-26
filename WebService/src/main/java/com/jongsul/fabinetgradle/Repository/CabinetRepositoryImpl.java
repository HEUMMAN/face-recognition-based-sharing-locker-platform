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
public class CabinetRepositoryImpl implements CabinetRepository{

    private final EntityManager em;

    @Override
    public String save(Cabinet cabinet) {
        em.persist(cabinet);
        return cabinet.getName();
    }

    @Override
    public List<Cabinet> findAllbyName(Member member) {
        log.info("사용중인 사물함 전체 불러오기");
        return em.createQuery("select c from Cabinet c where c.member=:member",Cabinet.class)
                .setParameter("member",member).getResultList();
    }

    @Override
    public List<String> getAllCabinet() {
        return em.createQuery("select c.name from Cabinet c").getResultList();
    }

    @Override
    public List<Cabinet> getOneCabinet(String userID) {
        log.info(userID+"가 사용중인 사물함 이름");
        return em.createQuery("select c from Cabinet c where c.member.loginId=:userID",Cabinet.class)
                .setParameter("userID",userID).getResultList();
    }

    @Override
    public List<Cabinet> getDate(Member member) {
        return em.createQuery("select m from Cabinet m where m.member = :name", Cabinet.class)
                .setParameter("name",member).getResultList();
    }
}
