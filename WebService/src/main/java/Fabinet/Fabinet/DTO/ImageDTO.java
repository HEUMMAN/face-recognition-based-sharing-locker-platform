package Fabinet.Fabinet.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
public class ImageDTO {

    private String name;
    private MultipartFile img;
}
