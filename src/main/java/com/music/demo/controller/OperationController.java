package com.music.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.OperationDao;
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

    @PostMapping("/cancelCollectList")
    public Map<String, Object> cancelCollectList(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId =(int) map.get("userId");
        int typeId = (int) map.get("typeId");
        int flag = operationDao.cancelCollectList(userId,typeId);
        //同时要把歌单收藏次数减少
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","取消收藏成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","取消失败");
        }
        return jsonObject;
    }

}
