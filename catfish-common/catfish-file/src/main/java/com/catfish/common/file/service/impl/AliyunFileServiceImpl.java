package com.catfish.common.file.service.impl;

import com.catfish.common.core.entity.CatfishConstants;
import com.catfish.common.file.entity.FileBinder;
import com.catfish.common.file.mapper.FileBinderMapper;
import com.catfish.common.file.service.FileService;
import com.hisaige.file.service.OssService;
import com.hisaige.file.service.impl.OssServiceImpl;
import com.hisaige.web.core.util.FileUtils;
import com.hisaige.web.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 12:25$
 */
public class AliyunFileServiceImpl implements FileService {

    @Autowired
    private FileBinderMapper fileBinderMapper;

    @Autowired
    private OssService ossService;

    @Override
    public String uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        String md5 = FileUtils.md5(file.getBytes());
        FileBinder dbFileBinder = fileBinderMapper.existMd5(md5);

        if(null != dbFileBinder) {
            return dbFileBinder.getFilePath();
        }

        String filePath = ossService.uploadFile(file);
        saveSysFile(filePath, md5);

        return filePath;
    }

    @Override
    public String uploadFile(String filePath, MultipartFile file) throws IOException, NoSuchAlgorithmException {

        String md5 = FileUtils.md5(file.getBytes());
        FileBinder dbFileBinder = fileBinderMapper.existMd5(md5);
        if(null != dbFileBinder) {
            return dbFileBinder.getFilePath();
        }
        String visitPath = ossService.uploadFile(filePath, file);
        saveSysFile(visitPath, md5);
        return visitPath;
    }

    @Override
    public int deleteFile(String filePath) {
        //文件路径开头必须为 CatfishConstants.ALIYUN_ENDPIONT
        if(filePath.startsWith(CatfishConstants.ALIYUN_ENDPIONT)){
            String url = filePath.substring(CatfishConstants.ALIYUN_ENDPIONT.length());
            ossService.deleteFile(url);
            return fileBinderMapper.delByFilePath(filePath);
        }
        return 0;
    }

    private void saveSysFile(String filePath, String md5) {
        if(!StringUtils.isEmpty(filePath)){
            FileBinder fileBinder = new FileBinder();
            fileBinder.setFilePath(filePath);
            Date now = new Date();
            fileBinder.setSystematic(false);
            fileBinder.setCreateTime(now);
            fileBinder.setUpdateTime(now);
            fileBinder.setMd5(md5);
            fileBinderMapper.insert(fileBinder);
        }
    }
}
