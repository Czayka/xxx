package com.xxx.controller;

import com.xxx.common.vo.PageResult;
import com.xxx.item.pojo.Album;
import com.xxx.item.pojo.Song;
import com.xxx.item.vo.AlbumVo;
import com.xxx.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    /**
     * 根据搜索条件分页查询
     *
     * @param key
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/album")
    public ResponseEntity<PageResult<Album>> getAlbumsByPageSearch(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows) {
        return ResponseEntity.ok(albumService.getAlbumsByPageSearch(key, page, rows));
    }

    @PostMapping("/album")
    public ResponseEntity<Void> insertAlbum(@RequestBody Album album) {
        albumService.insertAlbum(album);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/album")
    public ResponseEntity<Void> updateAlbum(@RequestBody Album album) {
        albumService.updateAlbum(album);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/album/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable("id") Long id) {
        albumService.deleteSong(id);
        return ResponseEntity.ok().build();
    }


    /**
     * 根据id查询专辑
     * @param id
     * @return
     */
    @GetMapping("/album/{id}")
    public ResponseEntity<Album> getAlbumById(
            @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    /**
     * 根据id查询专辑中所有歌曲
     * @param id
     * @return
     */
    @GetMapping("/album/albumDetail/{id}")
    public ResponseEntity<AlbumVo> getAlbumDetailById(
            @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(albumService.getAlbumDetailById(id));
    }
}
