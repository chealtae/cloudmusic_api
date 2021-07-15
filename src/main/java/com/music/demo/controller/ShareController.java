package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.OperationDao;
import com.music.demo.dao.ShareimageDao;
import com.music.demo.dao.ShareinfoDao;
import com.music.demo.domain.Shareimage;
import com.music.demo.domain.Shareinfo;
import com.music.demo.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("Share")
public class ShareController {
    @Autowired
    private ShareinfoDao shareinfoDao;
    @Autowired
    private ShareimageDao shareimageDao;
    @Autowired
    private OperationDao operationDao;

    @PostMapping("/addShare")
    Map<String,Object> addShare (@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int) map.get("userId");
        String content = (String) map.get("content");
        int musicId = (int) map.get("musicId");
        List<String> uploadList = (List<String>) map.get("uploadList");
        Shareinfo shareinfo = new Shareinfo();
        shareinfo.setMusicid(musicId);
        shareinfo.setUserid(userId);
        shareinfo.setContent(content);
        int res = shareinfoDao.addShare(shareinfo);
        int i = shareinfo.getId();
        System.out.println(i);
        for(String item:uploadList){
            String image = item;
            shareimageDao.addShareImg(i,image);
        }
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","发布成功");
        } else  {
            jsonObject.put("success",false);
            jsonObject.put("message","发布失败");
        }
        return jsonObject;
    }

    private static final String shareFilePath = Constants.getUrl();

    @RequestMapping(value = "/uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadImg(@RequestParam("file") MultipartFile upload){

        JSONObject jsonObject = new JSONObject();
        File file = new File(shareFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取原始图片的扩展名
        String originalFileName = upload.getOriginalFilename();
        String newFileName = UUID.randomUUID()+originalFileName;
        String newFilePath = shareFilePath+newFileName;
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

    @PostMapping("/getShare")
     public Map<String,Object> getShare (@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int) map.get("userId");
        int offset = (int) map.get("offset");

        List<Shareinfo> res = shareinfoDao.getShare(userId,offset);
        for(Shareinfo item:res){
//            System.out.println(item.getId());
            List<Shareimage> shareimage =  shareimageDao.getShareImage(item.getId());
//            System.out.println(shareimage);
            item.setShareimage(shareimage);
            item.setIsLike(operationDao.judgeLike(userId,3,item.getId()));
        }
        jsonObject.put("shareList",res);
        jsonObject.put("success",true);
        return jsonObject;
    }

    @GetMapping ("/deleteShare")
    public JSONObject deleteShare(Integer id){
        JSONObject jsonObject = new JSONObject();
        int res = shareinfoDao.deleteByPrimaryKey(id);
        if(res == 1){
            jsonObject.put("success",true);
        }
        return  jsonObject;
    }
}
