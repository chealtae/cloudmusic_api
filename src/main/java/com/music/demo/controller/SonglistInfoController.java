package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.SonglistinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("SongListInfo")
public class SonglistInfoController {
    @Autowired
    private SonglistinfoDao  songlistinfoDao;

    @PostMapping("/getList")
    public Map<String,Object> getList(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String userId = (String) map.get("userId");
        return jsonObject;
    }
}
