package com.xxx.item.vo;

import com.xxx.item.pojo.Song;
import lombok.Data;

import java.io.Serializable;

@Data
public class SongVo extends Song implements Serializable {

    private Long id;

    private Long albumId;

    private String name;

    private String songUrl;

    private String lyricsUrl;

    private String albumName;

    private String imageUrl;

    private String singerName;
}

