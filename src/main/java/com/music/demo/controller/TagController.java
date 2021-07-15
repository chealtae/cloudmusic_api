package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.TagDao;
import com.music.demo.domain.Tag;
import com.music.demo.domain.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("Tag")
public class TagController {
    @Autowired
    private TagDao tagDao;

    @GetMapping("/getListTag")
    public JSONObject getUserInfo(){
        JSONObject jsonObject = new JSONObject();
        List<Tag> tags = tagDao.getListTag();
        jsonObject.put("tags",tags);
        jsonObject.put("success",true);
        jsonObject.put("message","获取标签成功");
        return jsonObject;
    }

    @GetMapping("/getSingerTag")
    public JSONObject getSingerTag(){
        JSONObject jsonObject = new JSONObject();
        List<Tag> tags = tagDao.getSingerTag();
        jsonObject.put("tags",tags);
        jsonObject.put("success",true);
        jsonObject.put("message","获取标签成功");
        return jsonObject;
    }
}
