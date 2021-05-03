package com.xxx.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.common.enums.ExceptionEnum;
import com.xxx.common.exception.GeneralException;
import com.xxx.common.vo.PageResult;
import com.xxx.item.pojo.Album;
import com.xxx.item.pojo.Song;
import com.xxx.item.vo.AlbumVo;
import com.xxx.mapper.AlbumMapper;
import com.xxx.mapper.SongMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.rmi.runtime.Log;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public PageResult<Album> getAlbumsByPageSearch(String key, Integer page, Integer rows) {
        //  1.分页
        Page<Album> albumPage = new Page<>(page, rows);
        //  2.过滤
        LambdaQueryWrapper<Album> lambdaQueryWrapper = new LambdaQueryWrapper<Album>().select(
                Album.class, tableFieldInfo ->
                        !tableFieldInfo.getColumn().equals("createTime")
                                && !tableFieldInfo.getColumn().equals("lastUpdateTime")
        );
        if (!StringUtils.isBlank(key)) {
            lambdaQueryWrapper.like(Album::getAlbumName, key);
        }
        //  3.查询
        Page<Album> selectPage = albumMapper.selectPage(albumPage, lambdaQueryWrapper);

        if (CollectionUtils.isEmpty(selectPage.getRecords())) {
            throw new GeneralException(ExceptionEnum.ALBUM_NOT_PRESENCE);
        }
        return new PageResult<>(selectPage.getTotal(), selectPage.getPages(), selectPage.getRecords());
    }

    @Transactional
    public void insertAlbum(Album album) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        album.setCreateTime(time);
        album.setLastUpdateTime(time);
        albumMapper.insert(album);
    }

    @Transactional
    public void updateAlbum(Album album) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        album.setLastUpdateTime(time);
        albumMapper.updateById(album);

        //  4.发送mq信息
        amqpTemplate.convertAndSend("item.album.update", getAlbumDetailById(album.getId()).getSongs());
    }

    @Transactional
    public void deleteSong(Long id) {
        //  1.根据专辑id查询所有歌曲id
        List<Long> songIds = getAlbumDetailById(id).getSongs().stream()
                .map(Song::getId).collect(Collectors.toList());
        //  2.删除专辑歌曲表中的数据
        if (!CollectionUtils.isEmpty(songIds)) {
            songMapper.deleteBatchIds(songIds);
        }
        //  3.删除专辑
        albumMapper.deleteById(id);

        //  4.发送mq信息
        amqpTemplate.convertAndSend("item.album.delete", id);
    }

    public AlbumVo getAlbumDetailById(Long id) {
        //  1 根据专辑id查询出专辑
        Album album = getAlbumById(id);
        //  2.根据专辑id查询所有歌曲
        List<Song> songList = songMapper.selectList(new LambdaQueryWrapper<Song>()
                .select(Song::getId, Song::getName).eq(Song::getAlbumId, id));
        if (CollectionUtils.isEmpty(songList)) {
            throw new GeneralException((ExceptionEnum.ALBUM_SONG_NOT_PRESENCE));
        }

        return new AlbumVo(album, songList);
    }

    public Album getAlbumById(Long id) {
        Album album = albumMapper.selectById(id);
        if (album == null) {
            throw new GeneralException(ExceptionEnum.ALBUM_NOT_PRESENCE);
        }
        return album;
    }
}
