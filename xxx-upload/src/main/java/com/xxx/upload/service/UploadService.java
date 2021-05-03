package com.xxx.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xxx.common.enums.ExceptionEnum;
import com.xxx.common.exception.GeneralException;
import com.xxx.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.spi.AudioFileReader;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties prop;

    public String uploadImage(MultipartFile file) {
        try{
            //  校验文件类型
            String fileType = file.getContentType();
            if(!prop.getAllowImageTypes().contains(fileType)){
                throw new GeneralException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //  校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null){
                throw new GeneralException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            //  上传到FDFS
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
            //  返回路径
            return prop.getBaseUrl() + storePath.getFullPath();
        }catch (IOException e){
            log.error("上传文件失败:", e);
            throw new GeneralException(ExceptionEnum.INVALID_FILE_TYPE);
        }
    }

    public String uploadSong(MultipartFile file) {
        try{
            //  校验文件类型
            String fileType = file.getContentType();
            log.info(fileType);
            if(!prop.getAllowSongType().contains(fileType)){
                throw new GeneralException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            log.info(file.getOriginalFilename());
            //  上传到FDFS
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
            //  返回路径
            log.info("上传成功");
            return prop.getBaseUrl() + storePath.getFullPath();
        }catch (IOException e){
            log.error("上传文件失败:", e);
            throw new GeneralException(ExceptionEnum.INVALID_FILE_TYPE);
        }
    }
}
