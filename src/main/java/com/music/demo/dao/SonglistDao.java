package com.music.demo.dao;

import com.music.demo.domain.Songlist;

public interface SonglistDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Songlist record);

    int insertSelective(Songlist record);

    Songlist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Songlist record);

    int updateByPrimaryKey(Songlist record);
}