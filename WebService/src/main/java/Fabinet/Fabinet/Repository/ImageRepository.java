package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Image;
import Fabinet.Fabinet.Domain.Member;

import java.util.List;

public interface ImageRepository {

    public void save(Image image);
    public List<Image> findOne(String imageName);
//    public List<Member> findAll();
//    public List<Member> findByName(String name);
}
