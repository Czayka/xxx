package com.xxx.search.service;

import com.xxx.common.vo.PageResult;
import com.xxx.item.api.AlbumApi;
import com.xxx.item.api.SongApi;
import com.xxx.item.pojo.Album;
import com.xxx.item.pojo.Song;
import com.xxx.item.vo.AlbumVo;
import com.xxx.search.pojo.SearchRequest;
import com.xxx.search.pojo.SongSearch;
import com.xxx.search.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchService {

    @Autowired
    private SongRepository songsRepository;

    @Autowired
    private SongApi songApi;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private AlbumApi albumApi;

    public SongSearch buildSongs(Song song) {
        SongSearch songSearch = new SongSearch();
        //  1.歌曲信息
        songSearch.setId(song.getId());
        songSearch.setLyricsUrl(song.getLyricsUrl());

        String name = song.getName();
        songSearch.setName(name);

        songSearch.setSongUrl(song.getSongUrl());
        Long albumId = song.getAlbumId();
        songSearch.setAlbumId(albumId);

        //  2.专辑信息
        Album album = albumApi.getAlbumSongById(albumId);

        String singerName = album.getSingerName();
        songSearch.setSingerName(singerName);

        String albumName = album.getAlbumName();
        songSearch.setAlbumName(albumName);

        songSearch.setImageUrl(album.getImageUrl());
        songSearch.setAll(StringUtils.join(name, " ")
                + StringUtils.join(singerName, " ")
                + StringUtils.join(albumName, " "));

//        log.info(songSearch.getAll());

        return songSearch;
    }

    public PageResult<SongSearch> search(SearchRequest searchRequest) {
        //  1.创建queryBuilder
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //  2.分页
        queryBuilder.withPageable(PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize()));
        //  3.过滤
        String key = searchRequest.getKey();
        if (!StringUtils.isBlank(key)) {
            key = "*" + key + "*";
            /**
             * 根据type不同使用不同过滤方式
             * 1->根据歌名
             * 2->根据专辑名
             * 3->根据歌手
             * 其他->根据all字段
             */
            //wildcardQuery
            switch (searchRequest.getType()) {
                case 1:
                    queryBuilder.withQuery(QueryBuilders.wildcardQuery("name", key));
                    break;
                case 2:
                    queryBuilder.withQuery(QueryBuilders.wildcardQuery("albumName", key));
                    break;
                case 3:
                    queryBuilder.withQuery(QueryBuilders.wildcardQuery("singerName", key));
                    break;
                default:
                    queryBuilder.withQuery(QueryBuilders.wildcardQuery("all", key));
                    break;
            }
        }

        //  4.查询
        Page<SongSearch> searchPage = songsRepository.search(queryBuilder.build());


        return new PageResult<SongSearch>(searchPage.getTotalElements(), (long) searchPage.getTotalPages(), searchPage.getContent());
    }

    public void createOrUpdateIndex(Song song) {
        //  构建songSearch
        SongSearch songSearch = buildSongs(song);
        //  存入索引库
        songsRepository.save(songSearch);
    }

    public void deleteIndex(Long id) {
        songsRepository.deleteById(id);
    }

    public void updateIndexes(List<Song> songs) {
        List<SongSearch> collect = songs.stream().map(this::buildSongs).collect(Collectors.toList());
        songsRepository.saveAll(collect);
    }

    public void deleteIndexByAlbumId(Long id) {
        songsRepository.deleteAllByAlbumIdEquals(id);
    }

    public List<SongSearch> searchByAlbumId(Long id) {
        return songsRepository.getSongSearchByAlbumIdEquals(id);
    }
}
