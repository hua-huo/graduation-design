package com.example.topic.controller;

import com.example.topic.MetaDbUtils.ResultMsg;
import com.example.topic.config.CurrentUser;
import com.example.topic.model.User;
import com.example.topic.service.UserService;

import com.example.topic.utils.StringUtils;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2019\5\13 0013.
 */
@RestController
public class LoginController {
    private  String orgid="900001";
    @Autowired
     private UserService userService;
    @RequestMapping("/frontend/Login/save")
    public ResultMsg login(@RequestBody User user) throws NoSuchAlgorithmException {
        String pasword = user.getPassword();
        user.setPassword(getMD5String(pasword));
        user.setOrgid(Integer.valueOf(orgid+userService.getMaxObjid()));
        userService.saveUser(user);
        ResultMsg msg = new ResultMsg();
        msg.setMessage("SUCCESS");
        return msg;
    }
    @RequestMapping("/frontend/Login/checkdname")
    public ResultMsg checkdName(@RequestBody User user) throws NoSuchAlgorithmException {
        String username = user.getUsername();
        User users =userService.getUser(username);
        ResultMsg msg = new ResultMsg();
        if(users == null ){
            msg.setMessage("SUCCESS");
        }else{
            msg.setMessage("ERROR");
        }

        return msg;
    }
    @RequestMapping("/frontend/Login/register")
    public ResultMsg register(@RequestBody User user ,HttpServletRequest request ,HttpServletResponse response) throws NoSuchAlgorithmException {
        String username = user.getUsername();
        User users =userService.getUser(username);
        ResultMsg msg = new ResultMsg();
        if(users != null ){
            String message= user.getPassword();
            if(users.getPassword().equals(getMD5String(message))){//密码验证通过
                HttpSession session = request.getSession();
                session.setAttribute(users.getUsername(), users);
                Cookie c = new Cookie("token",getMD5String(users.getUsername()));
                response.addCookie(c);
                Map map = new HashMap<>();
                map.put("username",users.getUsername());
                map.put("name", users.getName());
                msg.setData(map);
                msg.setMessage("SUCCESS");
            }else{
                msg.setMessage("ERROR");
            }
        }else{
            msg.setMessage("ERROR");
        }
        return msg;
    }
    @RequestMapping("/frontend/Invitation/userlimits")
    public ResultMsg userLimits(@RequestBody Map param, HttpServletRequest request){
        ResultMsg msg = new ResultMsg();
        msg.setMessage("SUCCESS");
        User user = CurrentUser.getUser();
        if(user !=null &&user.getAuthority()==1){
            msg.setData(true);
        }else{
            msg.setData(false);
        }
        return msg;
    }
    public String getMD5String(String str){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 计算md5函数
        md.update(str.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }
    @RequestMapping("/frontend/Invitation/modifierUser")
    public ResultMsg modifierUser(@RequestBody User u){
        ResultMsg msg = new ResultMsg();
        User user = CurrentUser.getUser();
        if(user != null){
            user.setName(u.getName());
            user.setGender(u.getGender());
            userService.saveUser(user);
            msg.setMessage("SUCCESS");
            msg.setData(user);
            return msg;
        }
        msg.setMessage("ERROR");
        return msg;
    }
    @RequestMapping("/frontend/Invitation/getUser")
    public ResultMsg getUser(){
        ResultMsg msg = new ResultMsg();
        User user = CurrentUser.getUser();
        msg.setMessage("SUCCESS");
        msg.setData(user);
        return msg;
    }
}
