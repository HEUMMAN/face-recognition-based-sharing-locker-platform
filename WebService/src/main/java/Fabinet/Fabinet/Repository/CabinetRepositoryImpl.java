package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;
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
    public void save(Cabinet cabinet) {
        em.persist(cabinet);
        System.out.println("persist 완료");
        System.out.println("cabinet.getId() = " + cabinet.getId());
        System.out.println("cabinet.getName() = " + cabinet.getName());
        System.out.println("cabinet.getMember().getLoginId() = " + cabinet.getMember().getLoginId());
    }

    @Override
    public List<Cabinet> findAllbyName(Member member) {
        log.info("사용중인 사물함 전체 불러오기");
        return em.createQuery("select c from Cabinet c where c.member=:member",Cabinet.class)
                .setParameter("member",member).getResultList();
    }

    @Override
    public List<Cabinet> getDate(Member member) {
        return em.createQuery("select m from Cabinet m where m.member = :name", Cabinet.class)
                .setParameter("name",member).getResultList();
    }
}
