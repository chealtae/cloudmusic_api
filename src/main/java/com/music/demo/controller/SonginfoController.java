package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.demo.dao.*;
import com.music.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("SongInfo")
public class SonginfoController {
    @Autowired
    private SonginfoDao songinfoDao;
    @Autowired
    private SingerDao singerDao;
    @Autowired
    private SonglistinfoDao songlistinfoDao;
    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private AlbuminfoDao albuminfoDao;
    @Autowired
    private SonglistDao songlistDao;
    @Autowired
    private OperationDao operationDao;


    @PostMapping("getSong")
    public JSONObject getSong(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer songId = (Integer) map.get("songId");
        Integer userId = (Integer) map.get("userId");
        Songinfo res = songinfoDao.selectByPrimaryKey(songId);
        if(userId != -1){
            res.setIsCollect(operationDao.judgeCollect(userId,0,songId));
        }
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("song",res);
        return  jsonObject;
    }

    @PostMapping("/search")
    public JSONObject search(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String ResKey =(String) map.get("ResKey");
        List<Songinfo> songRes = songinfoDao.search(ResKey);
        List<Singer> singerRes = singerDao.search(ResKey);
        List<Songlistinfo>  ListRes = songlistinfoDao.search(ResKey);
        List<Userinfo> userRes = userinfoDao.search(ResKey);
        List<Albuminfo> albumRes = albuminfoDao.search(ResKey);
        jsonObject.put("songRes",songRes);
        jsonObject.put("ListRes",ListRes);
        jsonObject.put("singerRes",singerRes);
        jsonObject.put("userRes",userRes);
        jsonObject.put("albumRes",albumRes);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        return  jsonObject;
    }

    @PostMapping("/addMusic")
    public JSONObject addMusic(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Songinfo songinfo = new Songinfo();

        String name =(String) map.get("name");
        String singer = (String) map.get("singer");
        String album = (String) map.get("album");
        String audio = (String) map.get("audio");
        String lyirc = (String) map.get("lyric");
        String image = (String) map.get("image");
        String introduction = (String) map.get("introduction");
        songinfo.setName(name);
        songinfo.setSinger(singer);
        songinfo.setAlbum(album);
        songinfo.setLyric(lyirc);
        songinfo.setImage(image);
        songinfo.setAudio(audio);
        songinfo.setIntroduction(introduction);
        int res = songinfoDao.addMusic(songinfo);


        //上传歌曲成功后要判断是否存在专辑信息，如果存在要把这首歌存放到专辑列表中。
        if(album != null){
            Integer albumId = albuminfoDao.getAlbumId(album).getId();
            if(albumId != null){
                int i = songinfo.getId();
                int flag = songlistDao.addToAlbum(albumId,i);
                //把歌曲的图片封面更新
                Albuminfo albuminfo = albuminfoDao.selectByPrimaryKey(albumId);
                Songinfo songinfo1 = new Songinfo();
                System.out.println(albuminfo.getImage());
                songinfo1.setImage(albuminfo.getImage());
                songinfo1.setId(i);
                songinfoDao.updateByPrimaryKeySelective(songinfo1);
            }
        }

        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","添加成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","添加失败");
        }

        return  jsonObject;
    }

    @GetMapping("getNewRank")
    public JSONObject getNewRank(){
        JSONObject jsonObject = new JSONObject();
        List<Songinfo> songList = songinfoDao.getNewRank();
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("NewList",songList);
        return  jsonObject;
    }

    @GetMapping("getHotRank")
    public JSONObject getHotRank(){
        JSONObject jsonObject = new JSONObject();
        List<Songinfo> songList = songinfoDao.getHotRank();
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("HotList",songList);
        return  jsonObject;
    }

    @PostMapping("/getAllSong")
    public JSONObject getAllSong(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer offset = (Integer) map.get("offset") ;
        Integer size = (Integer) map.get("size") ;
        List<Songinfo> songList = songinfoDao.getAllSong(offset,size);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("songList",songList);
        return  jsonObject;
    }

    @PostMapping("/saveEdit")
    public JSONObject saveEdit(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        Integer id = (Integer) map.get("id") ;
        String name = (String) map.get("name");
        String singer = (String) map.get("singer");
        String album = (String) map.get("album");
        String lyric = (String) map.get("lyric");
        String audio = (String) map.get("audio");
        //通过id判断专辑是否改变
        Songinfo songinfo = new Songinfo();
        Songinfo res = songinfoDao.selectByPrimaryKey(id);

        if(res.getAlbum() != album){
            Albuminfo albumRes = albuminfoDao.getAlbumId(album);
            //先判断是否未空
            if("".equals(album)){
            } else if(albumRes == null){
                jsonObject.put("success",false);
                jsonObject.put("message","该专辑未存在，请先添加专辑");
                return jsonObject;
            } else {
                Integer newAlbumId = albuminfoDao.getAlbumId(album).getId();
                int flag = songlistDao.addToAlbum(newAlbumId,id);
                //删除之前存在的专辑
            }
        }
        //这里应该还要判断歌手是否存在 ？？
        songinfo.setId(id);
        songinfo.setAlbum(album);
        songinfo.setLyric(lyric);
        songinfo.setName(name);
        songinfo.setSinger(singer);
        songinfo.setAudio(audio);
        int updateRes = songinfoDao.updateByPrimaryKeySelective(songinfo);
        if (updateRes == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","更新成功");
        } else {
            jsonObject.put("success",false);
            jsonObject.put("message","更新失败");
        }
        return  jsonObject;
    }

    @PostMapping("/searchAllSong")
    public JSONObject searchAllSong(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        String keyRes1 = (String) map.get("keyRes1") ;
        String keyRes2 = (String) map.get("keyRes2") ;
        String keyRes3 = (String) map.get("keyRes3");
        List<Songinfo> songList = songinfoDao.searchAllSong(keyRes1,keyRes2,keyRes3);
        jsonObject.put("success",true);
        jsonObject.put("message","查询成功");
        jsonObject.put("songList",songList);
        return  jsonObject;
    }
}
