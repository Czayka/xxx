package com.xxx.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.common.enums.ExceptionEnum;
import com.xxx.common.exception.GeneralException;
import com.xxx.common.vo.PageResult;
import com.xxx.item.pojo.Song;
import com.xxx.item.vo.SongVo;
import com.xxx.mapper.SongMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SongService {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public List<Song> getAllSongs() {
//        IPage<SongVo> songVoBySearch = songMapper.getSongVoBySearch(null, "");
        List<Song> songs = songMapper.selectList(null);
        if (CollectionUtils.isEmpty(songs)) {
            throw new GeneralException(ExceptionEnum.SONG_NOT_PRESENCE);
        }
        return songs;
    }

    @Transactional
    public void insertSong(Song song) {
        //  1.新增歌曲信息
        Timestamp time = new Timestamp(System.currentTimeMillis());
        song.setCreateTime(time);
        song.setLastUpdateTime(time);
        songMapper.insert(song);

        //  2.发送mq信息
        amqpTemplate.convertAndSend("item.song.insert",song);
    }

    @Transactional
    public void updateSong(Song song) {
        //  1.修改歌曲信息
        Timestamp time = new Timestamp(System.currentTimeMillis());
        song.setLastUpdateTime(time);
        songMapper.updateById(song);

        //  2.发送mq信息
        amqpTemplate.convertAndSend("item.song.update",song);
    }

    @Transactional
    public void deleteSong(Long id) {
        //  1.删除歌曲信息
        songMapper.deleteById(id);

        //  2.发送mq信息
        amqpTemplate.convertAndSend("item.song.delete",id);
    }


    /*
    public PageResult<Song> getSongsByPageSearch(String key, Integer page, Integer rows) {
        //  1.分页
        Page<Song> songPage = new Page<>(page, rows);
        //  2.过滤
        //  2.1 默认不返回创建时间和修改时间
        LambdaQueryWrapper<Song> songQueryWrapper = new LambdaQueryWrapper<Song>()
                .select(Song.class, tableFieldInfo ->
                        !tableFieldInfo.getColumn().equals("createTime")
                                && !tableFieldInfo.getColumn().equals("lastUpdateTime"));
        //  2.2 过滤查询条件
        if (!StringUtils.isBlank(key)) {
            songQueryWrapper.like(Song::getName, key);
        }

        //  3.查询
        Page<Song> selectPage = songMapper.selectPage(songPage, songQueryWrapper);

        if (CollectionUtils.isEmpty(selectPage.getRecords())) {
            throw new GeneralException(ExceptionEnum.SONG_NOT_PRESENCE);
        }
        return new PageResult<Song>(selectPage.getTotal(), selectPage.getPages(), selectPage.getRecords());
    }*/

    public PageResult<SongVo> getSongVoBySearch(String key, Integer page, Integer rows) {
        Page<SongVo> songVoPage = new Page<>(page, rows);
        IPage<SongVo> songVoBySearch = songMapper.getSongVoBySearch(songVoPage, key);
        return new PageResult<>(songVoBySearch.getTotal(),songVoBySearch.getPages(),songVoBySearch.getRecords());
    }
}
