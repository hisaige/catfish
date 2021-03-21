package com.catfish.common.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 12:25$
 */
public interface FileService{

    /**
     * 上传文件，文件以日期为模块存储
     * @param file
     * @return
     * @throws IOException
     */
    String uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException;

    /**
     * 上传文件，指定文件存储模块|路径
     * @param filePath
     * @param file
     * @return
     * @throws IOException
     */
    String uploadFile(String filePath, MultipartFile file) throws IOException, NoSuchAlgorithmException;

    /**
     * 删除文件
     * @param filePath 文件访问路径
     */
    int deleteFile(String filePath);
}
