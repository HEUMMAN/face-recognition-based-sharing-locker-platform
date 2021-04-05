package Fabinet.Fabinet.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Cabinet {

    @Id
    @GeneratedValue
    private Long id;

    private String name;        //빌딩-층-번호 문자열 조합으로 구성(E-3-24면 E동 3층 24번사물함)
    private String building;
    private String floor;
    private String number;
    private LocalDateTime startTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
