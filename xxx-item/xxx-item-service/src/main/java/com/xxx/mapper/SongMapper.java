package com.xxx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.item.pojo.Song;
import com.xxx.item.vo.SongVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SongMapper extends BaseMapper<Song> {

    IPage<SongVo> getSongVoBySearch(Page<SongVo> page, String key);
}
