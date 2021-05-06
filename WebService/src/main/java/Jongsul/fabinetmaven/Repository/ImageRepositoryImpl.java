package Jongsul.fabinetmaven.Repository;

import Jongsul.fabinetmaven.Domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository{
    private final EntityManager em;

    public void save(Image image){
        em.persist(image);
    }

    @Override
    public List<Image> findOne(String imageName) {
        return em.createQuery("select m from Image m where m.name = :name", Image.class)
                .setParameter("name",imageName).getResultList();
    }

    /*public Member findOne(String id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m").getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name).getResultList();
    }*/
}
