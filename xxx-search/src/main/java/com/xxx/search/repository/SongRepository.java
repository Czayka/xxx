package com.xxx.search.repository;

import com.xxx.common.vo.PageResult;
import com.xxx.search.pojo.SongSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SongRepository extends ElasticsearchRepository<SongSearch,Long> {

    void deleteAllByAlbumIdEquals(Long id);

    List<SongSearch> getSongSearchByAlbumIdEquals(Long id);
}
