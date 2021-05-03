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
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tb_album")
public class Album implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String albumName;

    private String imageUrl;

    private String singerName;

    @JsonIgnore
    @TableField(select = false)
    private Timestamp createTime;

    @JsonIgnore
    @TableField(select = false)
    private Timestamp lastUpdateTime;
}
