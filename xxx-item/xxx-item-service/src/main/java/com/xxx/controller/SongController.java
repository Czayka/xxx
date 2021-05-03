package com.xxx.controller;

import com.xxx.common.vo.PageResult;
import com.xxx.item.pojo.Song;
import com.xxx.item.vo.SongVo;
import com.xxx.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("song")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 新增歌曲
     * @param song
     * @return
     */
    @PostMapping("/song")
    public ResponseEntity<Void> insertSong(@RequestBody Song song) {
        songService.insertSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改歌曲
     * @param song
     * @return
     */
    @PutMapping("/song")
    public ResponseEntity<Void> updateSong(@RequestBody Song song) {
        songService.updateSong(song);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除歌曲
     * @param id
     * @return
     */
    @DeleteMapping("/song/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable("id") Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 分页查找歌曲
     * @param key
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/song")
    public ResponseEntity<PageResult<SongVo>> getSongVoBySearch(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows) {
        return ResponseEntity.ok(songService.getSongVoBySearch(key, page, rows));
    }

    @GetMapping("/list/song")
    public ResponseEntity<List<Song>> getAllSongs(){
        return ResponseEntity.ok(songService.getAllSongs());
    }

}
