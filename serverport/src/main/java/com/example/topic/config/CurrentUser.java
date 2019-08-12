package com.example.topic.config;

import com.example.topic.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019\5\21 0021.
 */
@Component
public class CurrentUser   {
    static String _loginSessionKey="login_session";

    public static User getUser(){//获取当前用户信息
        HttpServletRequest request =getHttpServletRequest();
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        Map<String,String> cookiemap= new HashMap<>();
        for(Cookie cookie : cookies){
            cookiemap.put(cookie.getName(),cookie.getValue());//把cookie存进map
        }
        String username = cookiemap.get("username");
        //这里的User是登陆时放入session的
        User user = (User) session.getAttribute(username);
        return user;
    }
    /**
     * 获取当前请求session
     * @return
     */
    public static HttpServletRequest getHttpServletRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();
        return request;
    }
    /**
     * 获取当前请求session
     * @return
     */
    public static HttpSession getHttpSession(){
        return getHttpServletRequest().getSession();
    }
}
