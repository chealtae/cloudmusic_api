package com.music.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.SonglistinfoDao;
import com.music.demo.dao.UserinfoDao;
import com.music.demo.domain.Albuminfo;
import com.music.demo.domain.Songlistinfo;
import com.music.demo.domain.Userinfo;
import com.music.demo.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("User")
public class UserController {
    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private SonglistinfoDao songlistinfoDao;

    private static final String UserFilePath = Constants.getUrl();

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        System.out.println(map.get("username"));
        String username =  (String) map.get("username");
        String password =  (String) map.get("password");
        System.out.println(username);
        System.out.println(password);
        Userinfo flag = userinfoDao.verifyPassword(username,password);
        System.out.println(flag);
        if(flag != null){
            jsonObject.put("success",true);
            jsonObject.put("message","登录成功");
            jsonObject.put("userId",flag.getId());
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","账号或密码错误");
        }
        return jsonObject;
    }

    @PostMapping("/logon")
    public Map<String,Object> logon(@RequestBody HashMap map) {
        JSONObject jsonObject = new JSONObject();
        String username = (String) map.get("username");
        String password = (String) map.get("password");

        String nickName = Constants.namePrefix;
        int flag = userinfoDao.createUser(username,password,nickName);
        if(flag == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","注册成功");
        } else {
            jsonObject.put("succsee",false);
            jsonObject.put("message","注册失败");
        }
        return jsonObject;
    }

    @GetMapping("/getUserInfo")
    public  JSONObject getUserInfo(Integer userId){
        JSONObject jsonObject = new JSONObject();
        Userinfo userinfo = userinfoDao.getBaseInfo(userId);
        jsonObject.put("userInfo",userinfo);
        jsonObject.put("success",true);
        jsonObject.put("message","获取用户信息成功");
        return jsonObject;
    }

    @GetMapping("/getAllInfo")
    public JSONObject getAllInfo(Integer userId){
        JSONObject jsonObject = new JSONObject();
        Userinfo userinfo = userinfoDao.getAllInfo(userId);
        List<Songlistinfo> collectList = songlistinfoDao.getCreateList(userId);
        jsonObject.put("userInfo",userinfo);
        jsonObject.put("collectList",collectList);
        jsonObject.put("success",true);
        jsonObject.put("message","获取用户信息成功");
        return jsonObject;
    }

    @PostMapping("/saveEdit")
    public Map<String,Object> saveEdit(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int) map.get("userId");
        String nickname = (String)map.get("nickname");
        String introduction = (String) map.get("introduction");
        String region = (String) map.get("region");
        String image = (String) map.get("image");
        int sex = (int) map.get("sex");
        int flag = userinfoDao.saveEdit(userId,nickname,introduction,region,sex,image);
        if(flag ==  1){
            jsonObject.put("success",true);
            jsonObject.put("message","修改成功");
        } else  {
            jsonObject.put("success",false);
            jsonObject.put("message","修改失败");
        }

        return jsonObject;
    }

    //定义上传商铺文件的存放位置


    @RequestMapping(value = "/uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadImg(@RequestParam("file")MultipartFile upload){
        System.out.println(UserFilePath);
        JSONObject jsonObject = new JSONObject();
        File file = new File(UserFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取原始图片的扩展名
        String originalFileName = upload.getOriginalFilename();
        String newFileName = UUID.randomUUID()+originalFileName;
        String newFilePath = UserFilePath+newFileName;
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

    @PostMapping("/getAllUser")
    public JSONObject getAllUser(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int size = (int) map.get("size");
        int offset = (int ) map.get("offset");
        List<Userinfo> userList = userinfoDao.getAllUser(size,offset);
        jsonObject.put("userList",userList);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        return  jsonObject;
    }

    @PostMapping("/editLock")
    public JSONObject editLock(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int id = (int) map.get("id");
        int islocked = (int ) map.get("islocked");
        Userinfo userinfo = new Userinfo();
        userinfo.setId(id);
        userinfo.setIslocked(islocked);
        int res = userinfoDao.updateByPrimaryKeySelective(userinfo);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","修改成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","修改失败");
        }
        return  jsonObject;
    }

    @PostMapping("/searchAllUser")
    public JSONObject searchAllUser(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String keyRes = (String) map.get("keyRes");
        List<Userinfo> userList = userinfoDao.searchAllUser(keyRes);

            jsonObject.put("success",true);
            jsonObject.put("message","修改成功");
            jsonObject.put("userList",userList);

        return  jsonObject;
    }

    @PostMapping("/getFollow")
    public Map<String,Object> getFollow(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer id = (Integer) map.get("id");
        Integer offset = (Integer) map.get("offset");
        List<Userinfo> followList = userinfoDao.getFollow(id, offset);
        Userinfo baseInfo = userinfoDao.getBaseInfo(id);
        jsonObject.put("success",true);
        jsonObject.put("followList",followList);
        jsonObject.put("baseInfo",baseInfo);
        return jsonObject;
    }

    @PostMapping("/getFans")
    public Map<String,Object> getFans(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer id = (Integer) map.get("id");
        Integer offset = (Integer) map.get("offset");
        List<Userinfo> fansList = userinfoDao.getFans(id, offset);
        Userinfo baseInfo = userinfoDao.getBaseInfo(id);
        jsonObject.put("success",true);
        jsonObject.put("fansList",fansList);
        jsonObject.put("baseInfo",baseInfo);
        return jsonObject;
    }
}
