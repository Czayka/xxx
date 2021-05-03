package com.xxx.search.repository;


import com.xxx.item.pojo.Song;
import com.xxx.item.vo.SongVo;
import com.xxx.search.client.SongClient;
import com.xxx.search.pojo.SongSearch;
import com.xxx.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SongsRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SongClient songClient;

    @Autowired
    private SearchService searchService;

    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(SongSearch.class);
        elasticsearchTemplate.putMapping(SongSearch.class);
    }

    @Test
    public void loadData() {
        List<SongSearch> songSearches = songClient.getAllSongs()
                .stream().map(searchService::buildSongs)
                .collect(Collectors.toList());
        songRepository.saveAll(songSearches);
    }
}