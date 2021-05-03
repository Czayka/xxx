package com.xxx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    INVALID_FILE_TYPE   (400,"文件类型错误"),
    SONG_NOT_PRESENCE(404,"歌曲不存在"),
    ALBUM_NOT_PRESENCE(404,"专辑不存在"),
    ALBUM_SONG_NOT_PRESENCE(404,"专辑不存在歌曲")
    ;
    private int code;
    private String msg;
}
