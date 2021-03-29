package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.Domain.Member;

import java.util.List;

public interface MemberService {

    public String join(Member member);
    public Member findOne(String loginId);
    public List<Member> findMembers();
    public String login(String id, String password);
    public String isExistId(String id);
}
