package com.xxx.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "song", type = "docs", shards = 1, replicas = 0)
public class SongSearch {

    @Id
    private Long id;

    private Long albumId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword, index = false)
    private String songUrl;

    @Field(type = FieldType.Keyword, index = false)
    private String lyricsUrl;

    @Field(type = FieldType.Text)
    private String albumName;

    @Field(type = FieldType.Keyword, index = false)
    private String imageUrl;

    @Field(type = FieldType.Text)
    private String singerName;

    @Field(type = FieldType.Text)
    private String all;
}
