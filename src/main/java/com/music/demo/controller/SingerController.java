package com.music.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.AlbuminfoDao;
import com.music.demo.dao.SingerDao;
import com.music.demo.dao.TagDao;
import com.music.demo.dao.TagLinkDao;
import com.music.demo.domain.Albuminfo;
import com.music.demo.domain.Singer;
import com.music.demo.domain.Songlistinfo;
import com.music.demo.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("Singer")
public class SingerController {
    @Autowired
    private SingerDao singerDao;
    @Autowired
    private AlbuminfoDao albuminfoDao;
    @Autowired
    private TagLinkDao tagLinkDao;

    @GetMapping("/getAllInfo")
    public JSONObject getAllInfo(Integer id){
        JSONObject jsonObject = new JSONObject();
        Singer res = singerDao.selectByPrimaryKey(id);
        List<Albuminfo> albumRes = albuminfoDao.getAlbum(res.getId());
        jsonObject.put("success",true);
        jsonObject.put("singerInfo",res);
        jsonObject.put("albumRes",albumRes);
        return  jsonObject;
    }

    private static final String audioFilePath = Constants.getUrl2();
    @RequestMapping(value = "/uploadAudio",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadImg(@RequestParam("file") MultipartFile upload){
        System.out.println(audioFilePath);
        JSONObject jsonObject = new JSONObject();
        File file = new File(audioFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取原始图片的扩展名
        String originalFileName = upload.getOriginalFilename();
        String newFileName = originalFileName;
        String newFilePath = audioFilePath+newFileName;
        String webFilePath = newFileName;
        jsonObject.put("filename",webFilePath);
        try {
            //将传来的文件写入新建的文件

            upload.transferTo(new File(newFilePath));
            System.out.println("上传图片成功进行上传文件测试");
            return jsonObject;
        }catch (Exception e ) {
            //处理异常
            return jsonObject;
        }
    }

    @GetMapping("/getSingerId")
    public JSONObject getSingerId(String name){
        JSONObject jsonObject = new JSONObject();
        Integer singerId = singerDao.getSingerId(name);
        if (singerId == null){
            jsonObject.put("success",false);
        } else {
            jsonObject.put("success",true);
            jsonObject.put("singerId",singerId);
        }

        return  jsonObject;
    }

    @PostMapping("/addSinger")
    public Map<String,Object> addSinger(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Singer singer = new Singer();
        String name = (String)map.get("name");
        String introduction = (String)map.get("introduction");
        String image =(String) map.get("image");
        singer.setName(name);
        singer.setIntroduction(introduction);
        singer.setImage(image);
        int res = singerDao.addSinger(singer);
        List<Integer> tags = (List<Integer>) map.get("tags");
        int i = singer.getId();

        for(Integer tagId : tags){
            tagLinkDao.addTagToSinger(i,tagId);
        }
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","添加成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","添加失败");
        }
        return jsonObject;

    }

    //通过标签获取列表
    @PostMapping("/getSingerByTag")
    public Map<String,Object> getListByTag(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer tagId = (Integer) map.get("tagId");
        Integer offset = (Integer) map.get("offset") ;
        List<Singer> singerList = singerDao.getSingerByTag(tagId,offset);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("singerList",singerList);
        return jsonObject;
    }

    @GetMapping("/getCollectSinger")
    public JSONObject getCollectSinger(Integer userId){
        JSONObject jsonObject = new JSONObject();
        List<Singer> singerList = singerDao.getCollectSinger(userId);
        jsonObject.put("success",true);
        jsonObject.put("singerList",singerList);
        return  jsonObject;
    }

    @PostMapping("/getAllSinger")
    public Map<String,Object> getAllSinger(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer size = (Integer) map.get("size");
        Integer offset = (Integer) map.get("offset") ;
        List<Singer> singerList = singerDao.getAllSinger(size,offset);
        for( Singer item : singerList){
            item.setTag_Id(tagLinkDao.getSingerTag(item.getId()));
        }
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("singerList",singerList);
        return jsonObject;
    }

    @PostMapping("/saveEdit")
    public Map<String,Object> saveEdit(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer id = (Integer) map.get("id");
        String name = (String) map.get("name") ;
        String image = (String) map.get("image");
        String introduction = (String ) map.get("introduction");
        Singer singer = new Singer();
        singer.setImage(image);
        singer.setId(id);
        singer.setIntroduction(introduction);
        singer.setName(name);
        List<Integer> tags = (List<Integer>) map.get("tag_Id");
        tagLinkDao.deleteTagFromSinger(id); //先删除 后插入
        for(Integer tagId : tags){
            tagLinkDao.addTagToSinger(id,tagId);
        }
        int res = singerDao.updateByPrimaryKeySelective(singer);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","保存成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","保存失败");
        }


        return jsonObject;
    }

    @PostMapping("/searchAllSinger")
    public Map<String,Object>   searchAllSinger(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String keyRes = (String) map.get("keyRes") ;
        List<Singer> singerList = singerDao.search(keyRes);
        jsonObject.put("success",true);
        jsonObject.put("singerList",singerList);
        return  jsonObject;
    }
}
