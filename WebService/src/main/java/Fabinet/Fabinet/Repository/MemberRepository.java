package Fabinet.Fabinet.Repository;

import Fabinet.Fabinet.Domain.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public Member findOne(String id);
    public List<Member> findAll();
    public List<Member> findByName(String name);
}
