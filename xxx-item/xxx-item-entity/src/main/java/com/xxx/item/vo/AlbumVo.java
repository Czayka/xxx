package com.xxx.item.vo;

import com.xxx.item.pojo.Album;
import com.xxx.item.pojo.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumVo implements Serializable {

    private Long id;

    private String imageUrl;

    private String singerName;

    private String albumName;

    private List<Song> songs;

    public AlbumVo(Album album, List<Song> songs) {
        this.id = album.getId();
        this.imageUrl = album.getImageUrl();
        this.singerName = album.getSingerName();
        this.albumName = album.getAlbumName();
        this.songs = songs;
    }
}
