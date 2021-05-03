package com.xxx.search.controller;

import com.xxx.common.vo.PageResult;
import com.xxx.search.pojo.SearchRequest;
import com.xxx.search.pojo.SongSearch;
import com.xxx.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/page")
    public ResponseEntity<PageResult<SongSearch>> search(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(searchService.search(searchRequest));
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<List<SongSearch>> searchByAlbumId(@PathVariable Long id){
        return ResponseEntity.ok(searchService.searchByAlbumId(id));
    }
}
