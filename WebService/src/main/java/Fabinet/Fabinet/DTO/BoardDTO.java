package Fabinet.Fabinet.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BoardDTO {

    //AJAX측의 변수명과 같아야 받아진다
    private String title;
    private String content;
//    private String author;
//    private LocalDateTime date;
}
