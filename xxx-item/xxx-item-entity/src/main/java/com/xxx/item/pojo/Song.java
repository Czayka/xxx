package com.xxx.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "tb_songs")
@NoArgsConstructor
@AllArgsConstructor
public class Song implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long albumId;

    private String name;

    private String songUrl;

    private String lyricsUrl;

    @JsonIgnore
    @TableField(select = false)
    private Timestamp createTime;

    @JsonIgnore
    @TableField(select = false)
    private Timestamp lastUpdateTime;

}
