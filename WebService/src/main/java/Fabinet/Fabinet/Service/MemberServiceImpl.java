package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.Domain.Member;
import Fabinet.Fabinet.Repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //트랜섹셔널해줘야함
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepositoryImpl memberRepository;

    @Transactional  //트랜섹셔널해줘야함
    public String join(Member member){
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

    //올바른 회원정보로 로그인을 시도했는가?
    public String isExistId(String id){
        //이거 널로 안받아지는게 문제다
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
