package Jongsul.fabinetmaven.Repository;


import Jongsul.fabinetmaven.Domain.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public Member findOne(String id);
    public List<Member> findAll();
    public List<Member> findByName(String name);
}
