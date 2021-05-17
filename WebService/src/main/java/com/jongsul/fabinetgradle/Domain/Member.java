package com.jongsul.fabinetgradle.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
//@Builder
public class Member {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)   아마 @ID가 아니어서 자동생성이 안된듯
//    @Column(name = "MEMBER_ID")
//    private Long id;
    private String name;

    @Id
    @Column(name = "LOGIN_ID")
    private String loginId;
    private String loginPassword;
    private String tel;
    private String Email;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Cabinet> cabinets = new ArrayList<>();

    @Builder
    public Member(String name, String loginId, String loginPassword, String tel, String Email) {
        this.name = name;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.tel = tel;
        this.Email = Email;
    }

    @Override
    public String toString() {
        return "Name: "+name
                +"loginID: "+loginId
                +"loginPassword: "+loginPassword
                +"tel: "+tel
                +"Email: "+Email;
    }
}
