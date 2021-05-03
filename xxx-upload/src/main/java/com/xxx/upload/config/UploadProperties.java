package com.xxx.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "xxx.upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> allowImageTypes;
    private List<String> allowSongType;
}
