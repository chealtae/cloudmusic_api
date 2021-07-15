package com.music.demo.dao;

import com.music.demo.domain.Songinfo;
import com.music.demo.domain.Songlist;
import com.music.demo.domain.Songlistinfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SonglistinfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Songlistinfo record);

    int insertSelective(Songlistinfo record);

    Songlistinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Songlistinfo record);

    int updateByPrimaryKey(Songlistinfo record);

    List<Songlistinfo> getCreateList(Integer id);

    int createNewList(Integer id,String listName);

    List<Songlistinfo> getCollectList(Integer id);

    List<Songinfo> getSongList(Integer listId);

    List<Songinfo> getAlbumSongList(Integer listId);

    List<Songlistinfo> getRecommendList();

    int addCollect (Integer id);

    int reduceCollect(Integer id);

    List<Songlistinfo> search(String ResKey);

    int saveEdit(Integer id,String name,String introduction,String image);

    List<Songlistinfo> getListByTag(Integer tagId,Integer size,Integer offset);

    Integer getListCount(Integer tagId);

    int updateTotalPlay(Integer id);
}