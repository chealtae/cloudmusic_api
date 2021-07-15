package com.music.demo.dao;

import com.music.demo.domain.Userinfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserinfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);

    Userinfo verifyPassword(String username,String password);

    int createUser(String username,String password,String nickName);

    Userinfo getBaseInfo(Integer id);

    Userinfo getAllInfo(Integer id);

    int saveEdit(Integer id,String nickname,String introduction,String region,Integer sex,String image);

    List<Userinfo> search(String ResKye);

    List<Userinfo> getAllUser(Integer size,Integer offset);

    List<Userinfo> searchAllUser(String KeyRes);

    List<Userinfo> getFans(Integer id,Integer offset);

    List<Userinfo> getFollow(Integer id ,Integer offset);
}