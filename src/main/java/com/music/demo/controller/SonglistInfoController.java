package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.SonglistinfoDao;
import com.music.demo.domain.Songlistinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//歌单列表
@RestController
@CrossOrigin
@RequestMapping("SongListInfo")
public class SonglistInfoController {
    @Autowired
    private SonglistinfoDao  songlistinfoDao;

    @PostMapping("/getCreateList")
    public Map<String,Object> getList(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int) map.get("userId");
        List<Songlistinfo> listinfo = songlistinfoDao.getCreateList(userId);
        jsonObject.put("listid",listinfo);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        return jsonObject;
    }

    @PostMapping("/createNewList")
    public Map<String,Object> createList(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int)map.get("userId");
        String SonglistName = (String) map.get("createListName");
        int flag = songlistinfoDao.createNewList(userId,SonglistName);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","创建成功");
        } else  {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }

    @GetMapping("/deleteSongList")
    public JSONObject deleteSongList(Integer songListId){
        JSONObject jsonObject = new JSONObject();
        int flag = songlistinfoDao.deleteByPrimaryKey(songListId);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","删除成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","删除失败");
        }
        return jsonObject;
    }

    @PostMapping("/getCollectList")
    public Map<String,Object> getCollectList(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int) map.get("userId");
        List<Songlistinfo> listinfo = songlistinfoDao.getCollectList(userId);
        jsonObject.put("listid",listinfo);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        return jsonObject;
    }

    @GetMapping("/getSongListInfo")
    public Map<String,Object> getSonglistInfo(Integer listId){
        JSONObject jsonObject = new JSONObject();
        Songlistinfo res = songlistinfoDao.selectByPrimaryKey(listId);
        List<Songlistinfo> songRes = songlistinfoDao.getSongList(listId);

        jsonObject.put("playListDeatils",res);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("songList",songRes);
        return jsonObject;
    }
}
