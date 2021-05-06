package Jongsul.fabinetmaven.Repository;



import Jongsul.fabinetmaven.Domain.Image;

import java.util.List;

public interface ImageRepository {

    public void save(Image image);
    public List<Image> findOne(String imageName);
//    public List<Member> findAll();
//    public List<Member> findByName(String name);
}
