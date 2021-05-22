package com.jongsul.fabinetgradle.Service;

import com.jongsul.fabinetgradle.Config.SecurityUtil;
import com.jongsul.fabinetgradle.DTO.RegisterDTO;
import com.jongsul.fabinetgradle.Domain.Member;
import com.jongsul.fabinetgradle.Repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepositoryImpl memberRepository;
    private final String salt = "haha";

    @Transactional
    public String join(RegisterDTO registerDTO){

        SecurityUtil sha = new SecurityUtil();
        String encryptPassword = sha.encryptSHA256(registerDTO.getUserPW()+salt);

        Member member = Member.builder()
                .name(registerDTO.getUserName())
                .loginId(registerDTO.getUserID())
                .loginPassword(encryptPassword)
                .Email(registerDTO.getUserEmail())
                .tel(registerDTO.getUserTel())
                .build();

        memberRepository.save(member);
        return member.getLoginId();
    }

    public Member findOne(String loginId) { //loginID는 PK의 id와 다름
        return memberRepository.findOne(loginId);
    }

    public List<Member> findMembers(){
        return  memberRepository.findAll();
    }

    public String login(String id, String password){
        Member findMember = memberRepository.findOne(id);
        if(findMember == null){
            //없는 아이디
            return "F-1";   //아예 없는 아이디
        }
        else{
            if(findMember.getLoginPassword().equals(password)){
                //로그인성공
                return "Success";
            }
            else{
                //비밀번호 틀림
                return "F-2";   //아이디에 대한 비번이 일치하지 않는다
            }
        }
    }

    public String isExistId(String id){
        Member findMember = memberRepository.findOne(id);
        System.out.println("findMember: "+findMember);
        if(findMember == null){
            return "available";
        }
        else{
            return "occupied";
        }
    }
}
