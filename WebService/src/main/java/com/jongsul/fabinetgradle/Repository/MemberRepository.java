package com.jongsul.fabinetgradle.Repository;


import com.jongsul.fabinetgradle.Domain.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public Member findOne(String id);
    public List<Member> findAll();
    public List<Member> findByName(String name);
}
