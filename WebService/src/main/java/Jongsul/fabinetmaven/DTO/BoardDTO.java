package Jongsul.fabinetmaven.DTO;

import Jongsul.fabinetmaven.Domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter
@Getter
public class BoardDTO {

    //AJAX측의 변수명과 같아야 받아진다
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime date;
    private Member member;

    @Override
    public String toString() {
        return "\ntitle: "+title
                +"\ncontent: "+content;
    }
}
