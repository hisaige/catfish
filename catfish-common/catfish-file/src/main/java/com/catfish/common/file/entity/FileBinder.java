package com.catfish.common.file.entity;

import com.hisaige.dbcore.support.UUIDGenId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 12:27$
 */
@Table(name = "sys_file")
public class FileBinder {

    /**
     * id
     */
    @Id
    @KeySql(genId = UUIDGenId.class)
    private String id;

    /**
     * 文件存储路径
     */
    private String filePath;

    private String md5;

    private Boolean systematic;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Boolean getSystematic() {
        return systematic;
    }

    public void setSystematic(Boolean systematic) {
        this.systematic = systematic;
    }
}
