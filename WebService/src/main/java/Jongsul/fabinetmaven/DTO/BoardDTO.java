package Jongsul.fabinetmaven.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardDTO {

    //AJAX측의 변수명과 같아야 받아진다
    private String title;
    private String content;
//    private String author;
//    private LocalDateTime date;
}
