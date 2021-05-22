package com.jongsul.fabinetgradle.Service;


import com.jongsul.fabinetgradle.DTO.RegisterDTO;
import com.jongsul.fabinetgradle.Domain.Member;

import java.util.List;

public interface MemberService {

    String join(RegisterDTO registerDTO);
    Member findOne(String loginId);
    List<Member> findMembers();
    String login(String id, String password);
    String isExistId(String id);
}
