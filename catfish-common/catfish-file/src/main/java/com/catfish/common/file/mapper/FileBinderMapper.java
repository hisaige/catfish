package com.catfish.common.file.mapper;

import com.catfish.common.file.entity.FileBinder;
import com.hisaige.dbcore.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 12:33$
 */
public interface FileBinderMapper extends BaseMapper<FileBinder> {

    /**
     * 根据md5查询文件
     * @param md5 文件md5
     * @return FileBinder
     */
    @Select("select id, file_path as filePath, md5, systematic, create_time as createTime, update_time as updateTime from sys_file where md5 = #{md5}")
    FileBinder existMd5(String md5);

    /**
     * 根据文件路径删除
     * @param filePath
     * @return
     */
    @Delete("delete from sys_file where file_path = #{filePath} limit 1")
    int delByFilePath(String filePath);
}
