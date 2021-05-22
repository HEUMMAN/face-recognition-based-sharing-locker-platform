package com.jongsul.fabinetgradle.Config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Configuration
public class MemberInformation {

    private final String USER_SESSION_NAME = "loginMemberId";

    public String getUserName(HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionId = (String)session.getAttribute(USER_SESSION_NAME);
        return sessionId;
    }
}
