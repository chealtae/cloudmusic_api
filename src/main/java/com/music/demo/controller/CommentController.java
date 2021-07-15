package com.music.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.music.demo.dao.CommentDao;
import com.music.demo.dao.OperationDao;
import com.music.demo.dao.UserinfoDao;
import com.music.demo.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("Comment")
public class CommentController {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private OperationDao operationDao;

    @PostMapping("/addComment")
    public Map<String,Object> addComment(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int)map.get("userId");
        int type = (int)map.get("type");
        int typeId = (int) map.get("typeId");
        String content =(String) map.get("content");
        int res = commentDao.insertComment(userId,type,typeId,content);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","评论成功");
        }else {
            jsonObject.put("success",false);
            jsonObject.put("message","评论失败");
        }
        return jsonObject;
    }

    @PostMapping("/replyComment")
    public Map<String,Object> replyComment(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int userId = (int)map.get("userId");
        int type = (int)map.get("type");
        int typeId = (int) map.get("typeId");
        int parentId = (int) map.get("parentId");
        String content =(String) map.get("content");
        int res = commentDao.replyComment(userId,type,typeId,parentId,content);
        if(res == 1){
            jsonObject.put("success",true);
            jsonObject.put("message","评论成功");
        }else {
            jsonObject.put("success",false);
            jsonObject.put("message","评论失败");
        }
        return jsonObject;
    }

    @GetMapping("/deleteComment")
    public Map<String,Object> deleteComment(Integer id) {
        JSONObject jsonObject = new JSONObject();
        int res = commentDao.deleteByPrimaryKey(id);
        if(res == 1) {
            jsonObject.put("success",true);
            jsonObject.put("message","删除成功");
        }else {
            jsonObject.put("success",false);
            jsonObject.put("message","删除失败");
        }
        return jsonObject;
    }

    @PostMapping("/getCommentNumber")
    public Map<String,Object> getCommentNumber(@RequestBody HashMap map) {
        JSONObject jsonObject = new JSONObject();
        int type = (int)map.get("type");
        int typeId = (int) map.get("typeId");
        int res = commentDao.getCommentNumber(type,typeId);
        if(res >= 0 ){
            jsonObject.put("commentNumber",res);
            jsonObject.put("success",true);
            jsonObject.put("message","查询成功");
        }else {
            jsonObject.put("success",false);
            jsonObject.put("message","查询失败");
        }
        return jsonObject;
    }



    @PostMapping("/getComment")
    public Map<String,Object> getComment(@RequestBody HashMap map){
        JSONObject jsonObject = new JSONObject();
        int size = (int) map.get("size");
        int offset =( (int) map.get("currentPage")-1)*size;
        int type = (int)map.get("type");
        int typeId = (int) map.get("typeId");
        int userId = (int) map.get("userId");
        if(userId == -1){
            jsonObject.put("success",false);
            jsonObject.put("message","查询失败");
            return jsonObject;
        }
        System.out.println(userId+' '+ typeId);
        List<Comment> res= commentDao.getComment(offset,size,type,typeId);
        for(Comment item :res){
            item.setIsLike(operationDao.judgeLike(userId,5,item.getId()));
            item.setUserinfo(userinfoDao.getBaseInfo(item.getUserId()));
            if(item.getParentId() != null){
                Comment temp = commentDao.getParentComment(item.getParentId());
                if(temp!=null){
                    temp.setUserinfo(userinfoDao.getBaseInfo(temp.getUserId()));
                }
                item.setComment(temp);
            }
        }
        if (res != null){
            jsonObject.put("comment",res);
            jsonObject.put("success",true);
            jsonObject.put("message","查询成功");
        } else{
            jsonObject.put("success",false);
            jsonObject.put("message","查询失败");
        }
        return jsonObject;
    }
}
