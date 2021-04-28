package com.music.demo.controller;

import com.music.demo.dao.SonglistDao;
import com.music.demo.domain.Songlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("SongList")
public class SonglistController {
    @Autowired
    private SonglistDao songlistDao;

}
