package com.music.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * songlistinfo
 * @author 
 */
@Data
public class Songlistinfo implements Serializable {
    private Integer id;

    private Date createtime;

    private Date modifytime;

    private Integer islocked;

    private Integer isdeleted;

    private Integer userid;

    private String name;

    private String image;

    private String introduction;

    private String totalplay;

    private String totalcollect;

    private String totalshare;

    private static final long serialVersionUID = 1L;
}