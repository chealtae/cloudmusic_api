package com.music.demo.dao;

import com.music.demo.domain.Userinfo;
import org.springframework.stereotype.Component;

@Component
public interface UserinfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);

    Userinfo verifyPassword(String username,String password);

    int createUser(String username,String password);
}