package com.catfish.common.file.controller;

import com.catfish.common.file.service.FileService;
import com.catfish.common.core.entity.domain.AjaxMessageRes;
import com.hisaige.web.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 11:56$
 */
@RestController
@RequestMapping("/aliyun/oss")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping({"/upload"})
    public AjaxMessageRes<String> uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String url = fileService.uploadFile(file);
        return new AjaxMessageRes<>(url);
    }

    @PostMapping({"/upload/{filePath}"})
    public AjaxMessageRes<String> uploadFile(@PathVariable("filePath") String filePath, MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String url = fileService.uploadFile(filePath, file);
        return new AjaxMessageRes<>(url);
    }

    @DeleteMapping("/delete")
    public AjaxMessageRes<String> delete(String url) {
        if(!StringUtils.isEmpty(url)){
            fileService.deleteFile(url);
        }
        return new AjaxMessageRes<>();
    }
}