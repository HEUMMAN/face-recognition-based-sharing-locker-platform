package com.jongsul.fabinetgradle.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class CabinetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        //빌딩-층-번호 문자열 조합으로 구성(E-3-24면 E동 3층 24번사물함)
    private String building;
    private String floor;
    private String number;
    private Date startTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public CabinetHistory(String name, String building, String floor, String number, Date startTime, Member member) {
        this.name = name;
        this.building = building;
        this.floor = floor;
        this.number = number;
        this.startTime = startTime;
        this.member = member;
    }
}
