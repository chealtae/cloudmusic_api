package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.music.demo.dao.AlbuminfoDao;
import com.music.demo.dao.SingerDao;
import com.music.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("Album")
public class AlbumInfoController {
    @Autowired
    private AlbuminfoDao albuminfoDao;
    @Autowired
    private SingerDao singerDao;


    @PostMapping("/addAlbum")
    public Map<String,Object> addAlbum(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String name = (String)map.get("name");
        String singer = (String)map.get("singer");
        String recordCompany = (String) map.get("recordCompany");
        String introduction = (String) map.get("introduction");
        String issueDate =(String) map.get("issueDate");
        String image =(String) map.get("image");
        Integer singerId = singerDao.getSingerId(singer);
        if(singerId == null){
            jsonObject.put("success",false);
            return jsonObject;
        } else {
            int res = albuminfoDao.addAlbum(singerId,name,image,issueDate,recordCompany,introduction);
            if(res == 1){
                jsonObject.put("success",true);
                jsonObject.put("message","添加成功");
            }else {
                jsonObject.put("success",false);
                jsonObject.put("message","添加失败");
            }
            return jsonObject;
        }

    }

    @GetMapping("/getSongListInfo")
    public Map<String,Object> getSonglistInfo(Integer listId){
        JSONObject jsonObject = new JSONObject();
        Albuminfo res = albuminfoDao.selectByPrimaryKey(listId);
        List<Songinfo> songRes = albuminfoDao.getSongList(listId);
        res.setSinger(singerDao.selectByPrimaryKey(res.getSingerid()));
        jsonObject.put("albumDeatils",res);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("songList",songRes);
        return jsonObject;
    }

    @PostMapping("/search")
    public JSONObject search(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String ResKey =(String) map.get("ResKey");
        List<Albuminfo> albumRes = albuminfoDao.search(ResKey);
        jsonObject.put("albumRes",albumRes);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        return  jsonObject;
    }

    @GetMapping("/getCollectAlbum")
    public JSONObject getCollectAlbum(Integer userId){
        JSONObject jsonObject = new JSONObject();
        List<Albuminfo> albumList = albuminfoDao.getCollectAlbum(userId);
        jsonObject.put("success",true);
        jsonObject.put("albumList",albumList);
        return  jsonObject;
    }

    @PostMapping("/getAllAlbum")
    public JSONObject getAllAlbum(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int size = (int) map.get("size");
        int offset = (int ) map.get("offset");
        List<Albuminfo> albumList = albuminfoDao.getAllAlbum(size,offset);
        jsonObject.put("albumList",albumList);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        return  jsonObject;
    }

    @PostMapping("/saveEdit")
    public JSONObject saveEdit(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        String singerName = (String) map.get("singerName");
        String issuedate = (String) map.get("issuedate");
        String recordcompany = (String) map.get("recordcompany");
        String introduction = (String) map.get("introduction");
        Integer singerId = singerDao.getSingerId(singerName);
        //判断修改后的歌手是否存在
        if(singerId == null){
            jsonObject.put("message","修改后歌手不存在，请先添加歌手");
            jsonObject.put("success",false);
        }
        Albuminfo albuminfo = new Albuminfo();
        albuminfo.setId(id);
        albuminfo.setName(name);
        albuminfo.setSingerid(singerId);
        albuminfo.setIssuedate(issuedate);
        albuminfo.setRecordcompany(recordcompany);
        albuminfo.setIntroduction(introduction);
        int res = albuminfoDao.updateByPrimaryKeySelective(albuminfo);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","查询成功");
        }

        return  jsonObject;
    }

    @PostMapping("/searchAllAlbum")
    public Map<String,Object>   searchAllSinger(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String keyRes1 = (String) map.get("keyRes1") ;
        String  keyRes2 = (String) map.get("keyRes2");
        List<Albuminfo> albumList = albuminfoDao.searchAllAlbum(keyRes1,keyRes2);
        jsonObject.put("success",true);
        jsonObject.put("albumList",albumList);
        return  jsonObject;
    }

    @GetMapping("addAlbumCollect")
    public JSONObject addAlbumCollect (Integer albumId){
        JSONObject jsonObject = new JSONObject();
        int res = albuminfoDao.addAlbumCollect(albumId);
        if(res == 1){
            jsonObject.put("success",true);
        }
        return  jsonObject;
    }

    @GetMapping("subAlbumCollect")
    public JSONObject subAlbumCollect (Integer albumId){
        JSONObject jsonObject = new JSONObject();
        int res = albuminfoDao.subAlbumCollect(albumId);
        if(res == 1){
            jsonObject.put("success",true);
        }
        return  jsonObject;
    }
}
