package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.UserinfoDao;
import com.music.demo.domain.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("User")
public class UserController {
    @Autowired
    private UserinfoDao userinfoDao;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        System.out.println(map.get("username"));
        String username =  (String) map.get("username");
        String password =  (String) map.get("password");
        System.out.println(username);
        System.out.println(password);
        Userinfo flag = userinfoDao.verifyPassword(username,password);
        System.out.println(flag);
        if(flag != null){
            jsonObject.put("success",true);
            jsonObject.put("message","登录成功");
            jsonObject.put("userId",flag.getId());
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","账号或密码错误");
        }
        return jsonObject;
    }

    @PostMapping("/logon")
    public Map<String,Object> logon(@RequestBody HashMap map) {
        JSONObject jsonObject = new JSONObject();
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        int flag = userinfoDao.createUser(username,password);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","注册成功");
        } else {
            jsonObject.put("succsee",false);
            jsonObject.put("message","注册失败");
        }
        return jsonObject;
    }
}
