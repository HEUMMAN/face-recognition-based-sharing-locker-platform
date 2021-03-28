package Fabinet.Fabinet.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;
    private String name;    //컨트롤러에서 사진명을 유저id로 수정하여 올린다.
    @Lob
    private Blob image;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
//    @JoinColumn(name = "LOGIN_ID")
//    private Member member;
}
