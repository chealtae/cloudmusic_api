package com.music.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.SonglistDao;
import com.music.demo.domain.Songlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("SongList")
public class SonglistController {
    @Autowired
    private SonglistDao songlistDao;

    //添加到歌单
    @PostMapping("/addToList")
    public Map<String,Object> addToList (@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int id = (int) map.get("id");
        int songId = (int) map.get("songId");
        int res = songlistDao.addToList(id,songId);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","添加成功");
        } else{
            jsonObject.put("success",false);
            jsonObject.put("message","添加失败");
        }
        return  jsonObject;
    }

    @PostMapping("/addToAlbum")
    public Map<String,Object> addToAlbum (@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int id = (int) map.get("id");//id代表专辑编号
        int songId = (int) map.get("songId");
        int res = songlistDao.addToAlbum(id,songId);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","添加成功");
        } else{
            jsonObject.put("success",false);
            jsonObject.put("message","添加失败");
        }
        return  jsonObject;
    }

    @PostMapping("/removeFromList")
    public Map<String,Object> removeFromList (@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int id = (int) map.get("id");
        int songId = (int) map.get("songId");
        int res = songlistDao.removeFromList(id,songId);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","移出成功");
        } else{
            jsonObject.put("success",false);
            jsonObject.put("message","移出失败");
        }
        return  jsonObject;
    }
}
