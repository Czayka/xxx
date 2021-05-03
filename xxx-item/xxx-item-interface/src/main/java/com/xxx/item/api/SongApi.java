package com.xxx.item.api;

import com.xxx.item.pojo.Song;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/song")
public interface SongApi {
    @GetMapping("/list/song")
    List<Song> getAllSongs();
}
