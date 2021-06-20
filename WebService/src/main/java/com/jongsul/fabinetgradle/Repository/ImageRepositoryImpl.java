package com.jongsul.fabinetgradle.Repository;

import com.jongsul.fabinetgradle.Domain.Image;
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
}
