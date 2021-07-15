package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.CommentDao;
import com.music.demo.dao.OperationDao;
import com.music.demo.dao.ShareinfoDao;
import com.music.demo.dao.SonglistinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("Operation")
public class OperationController {
    @Autowired
    private OperationDao operationDao;
    @Autowired
    private ShareinfoDao shareinfoDao;
    @Autowired
    private SonglistinfoDao songlistinfoDao;
    @Autowired
    private CommentDao commentDao;

    @PostMapping("/cancelCollect")
    public Map<String, Object> cancelCollect(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int collectType = (int) map.get("collectType");
        int flag = operationDao.cancelCollect(userId,collectType,typeId);
        //同时要把歌单收藏次数减少
        int res = songlistinfoDao.reduceCollect(typeId);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","取消收藏成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","取消失败");
        }
        return jsonObject;
    }

    @PostMapping("/follow")
    public Map<String, Object> follow(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int followType = (int) map.get("followType");
        int flag = operationDao.follow(userId,followType,typeId);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","关注成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","关注失败");
        }
        return jsonObject;
    }

    @PostMapping("/collect")
    public Map<String, Object> collect(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int collectType = (int) map.get("collectType");
        int flag = operationDao.collect(userId,collectType,typeId);
        int add = songlistinfoDao.addCollect(typeId);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","收藏成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","收藏失败");
        }
        return jsonObject;
    }

    @PostMapping("/cancelFollow")
    public Map<String, Object> cancelFollow(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int followType = (int) map.get("followType");
        int flag = operationDao.cancelFollow(userId,followType,typeId);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","取消关注成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","取消关注失败");
        }
        return jsonObject;
    }

    @PostMapping("/judgeFollow")
    public Map<String, Object> judgeFollow(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int followType = (int) map.get("followType");
        int flag = operationDao.judgeFollow(userId,followType,typeId);
        if(flag != 0){
            jsonObject.put("success",true);
        } else {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }

    @GetMapping("getOtherInfo")
    public JSONObject getOtherInfo(Integer userId){
        JSONObject jsonObject = new JSONObject();
        int fansNumber = operationDao.getFansNumber(userId);
        int followNumber = operationDao.getFollowNumber(userId);
        int shareNumber = shareinfoDao.getShareNumber(userId);

        jsonObject.put("success",true);
        jsonObject.put("shareNumber",shareNumber);
        jsonObject.put("fansNumber",fansNumber);
        jsonObject.put("followNumber",followNumber);
        return jsonObject;
    }

    @PostMapping("/judgeCollect")
    public Map<String, Object> judgeCollect(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int collectType = (int) map.get("collectType");
        Boolean flag = operationDao.judgeCollect(userId,collectType,typeId);
        if(flag){
            jsonObject.put("success",true);
        } else {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }

    @PostMapping("/like")
    public Map<String, Object> like(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int collectType = (int) map.get("collectType"); //动态3 评论5
        if(collectType == 5){
            int res = commentDao.addLike(typeId);
        }
        Integer flag = operationDao.like(userId,collectType,typeId);
        if(flag == 1){
            jsonObject.put("success",true);
        } else {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }

    @PostMapping("/cancelLike")
    public Map<String, Object> cancelLike(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int collectType = (int) map.get("collectType"); //动态3 评论5
        if(collectType == 5){
            int res = commentDao.subLike(typeId);
        }
        Integer flag = operationDao.cancelLike(userId,collectType,typeId);
        if(flag == 1){
            jsonObject.put("success",true);
        } else {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }
}
