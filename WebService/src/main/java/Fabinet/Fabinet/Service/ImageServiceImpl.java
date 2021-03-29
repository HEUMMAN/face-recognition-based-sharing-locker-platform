package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.Domain.Image;
import Fabinet.Fabinet.Domain.Member;
import Fabinet.Fabinet.Repository.ImageRepository;
import Fabinet.Fabinet.Repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.List;

@Service
@Transactional(readOnly = true) //트랜섹셔널해줘야함
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;

    @Transactional  //트랜섹셔널해줘야함
    public String join(Image image){
        imageRepository.save(image);
        return "complete";
    }

    @Override
    public List<Image> findOne(String imageName) {
        return imageRepository.findOne(imageName);
    }
}
