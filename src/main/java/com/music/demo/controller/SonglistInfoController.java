package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.music.demo.dao.OperationDao;
import com.music.demo.dao.SonglistinfoDao;
import com.music.demo.dao.TagLinkDao;
import com.music.demo.dao.UserinfoDao;
import com.music.demo.domain.Songinfo;
import com.music.demo.domain.Songlistinfo;
import com.music.demo.domain.TagLink;
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
    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private TagLinkDao tagLinkDao;
    @Autowired
    private OperationDao operationDao;

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

    @PostMapping("/getSongListInfo")
    public Map<String,Object> getSonglistInfo(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer listId = (Integer) map.get("listId");
        Integer userId = (Integer) map.get("userId");
        Songlistinfo res = songlistinfoDao.selectByPrimaryKey(listId);
        List<Songinfo> songRes = songlistinfoDao.getSongList(listId);
        //判断是否呗用户收藏
        if(userId != -1){
            for(Songinfo item:songRes){
                item.setIsCollect(operationDao.judgeCollect(userId,0,0));
            }
        }

        List<TagLink> tagRes = tagLinkDao.getListTag(listId);
        res.setUserinfo(userinfoDao.getBaseInfo(res.getUserid()));
        jsonObject.put("playListDeatils",res);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("songList",songRes);
        jsonObject.put("tagRes",tagRes);
        return jsonObject;
    }


    //首页推荐
    @GetMapping("/getRecommendList")
    public Map<String,Object> getRecommendList(){
        JSONObject jsonObject = new JSONObject();
        List<Songlistinfo> songRes = songlistinfoDao.getRecommendList();
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("songList",songRes);
        return jsonObject;
    }

    @PostMapping("/saveEdit")
    public Map<String ,Object> saveEdit(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        String image = (String) map.get("image");
        String introduction = (String) map.get("introduction");
        List<Integer> tags = (List<Integer>) map.get("tags");

        //先删除后添加标签
        tagLinkDao.deleteTagFromList(id);
        for(Integer tagId : tags){
            tagLinkDao.addTagToList(id,tagId);
        }

        int res = songlistinfoDao.saveEdit(id,name,introduction,image);
        if(res == 1){
            jsonObject.put("success",true);
        } else {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }

    //通过标签获取列表
    @PostMapping("/getListByTag")
    public Map<String,Object> getListByTag(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer tagId = (Integer) map.get("tagId");
        Integer size = (Integer) map.get("size") ;
        Integer offset = (Integer) map.get("offset") ;
        List<Songlistinfo> listRes = songlistinfoDao.getListByTag(tagId,size,offset);
        int count = songlistinfoDao.getListCount(tagId);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("songList",listRes);
        jsonObject.put("count",count);
        return jsonObject;
    }

    @GetMapping("/updateTotalPlay")
    public JSONObject updateTotalPlay (Integer listId){
        JSONObject jsonObject = new JSONObject();
        int res = songlistinfoDao.updateTotalPlay(listId);
        if(res == 1){
            jsonObject.put("message","增加成功");
            jsonObject.put("success",true);
        }
        return  jsonObject;
    }
}
