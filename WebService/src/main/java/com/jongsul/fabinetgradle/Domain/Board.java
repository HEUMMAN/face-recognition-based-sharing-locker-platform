package com.jongsul.fabinetgradle.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "LOGIN_ID")
    private Member member;

    @Builder
    public Board(String title, String content, String author, LocalDateTime date, Member member) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
        this.member = member;
    }
}
