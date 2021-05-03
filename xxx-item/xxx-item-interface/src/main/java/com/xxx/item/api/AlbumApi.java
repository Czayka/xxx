package com.xxx.item.api;

import com.xxx.item.pojo.Album;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/album")
public interface AlbumApi {
    @GetMapping("/album/{id}")
    Album getAlbumSongById(@PathVariable(value = "id") Long id);
}
