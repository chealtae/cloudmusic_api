package com.music.demo.dao;

import com.music.demo.domain.Songlistinfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SonglistinfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Songlistinfo record);

    int insertSelective(Songlistinfo record);

    Songlistinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Songlistinfo record);

    int updateByPrimaryKey(Songlistinfo record);
}