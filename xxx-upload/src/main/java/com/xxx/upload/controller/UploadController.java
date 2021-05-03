package com.xxx.upload.controller;

import com.xxx.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        log.info("图片上传开始");
        return ResponseEntity.ok(uploadService.uploadImage(file));
    }

    @PostMapping("/song")
    public ResponseEntity<String> uploadSong(@RequestParam("file")MultipartFile file){
        log.info("歌曲上传开始");
        return ResponseEntity.ok(uploadService.uploadSong(file));
    }
}
