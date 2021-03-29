package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Domain.Cabinet;
import Fabinet.Fabinet.Domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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
    public List<Board> findAll() {
        return null;
    }

    @Override
    public List<Cabinet> getDate(Member member) {
        return em.createQuery("select m from Cabinet m where m.member = :name", Cabinet.class)
                .setParameter("name",member).getResultList();
    }
}
