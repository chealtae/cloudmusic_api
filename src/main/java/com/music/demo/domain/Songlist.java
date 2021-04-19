package com.music.demo.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * songlist
 * @author 
 */
@Data
public class Songlist implements Serializable {
    private Integer id;

    private Date createtime;

    private Date modifytime;

    private Integer isdeleted;

    private Integer type;

    private Integer typedid;

    private Integer songid;

    private static final long serialVersionUID = 1L;
}