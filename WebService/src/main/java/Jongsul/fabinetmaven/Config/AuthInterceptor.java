package Jongsul.fabinetmaven.Config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String loginedMemberId = (String) request.getSession().getAttribute("loginMemberId");
        if (loginedMemberId == null) {
            System.out.println("로그인된 세션 없음");
            //로그인 후 사용하라는 alert 추가하자
            response.sendRedirect("/login");
            return false;
        }
        else{
            System.out.println("현재 사용자: "+loginedMemberId);
        }
        return true;
    }
}
