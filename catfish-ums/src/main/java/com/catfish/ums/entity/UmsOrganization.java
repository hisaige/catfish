package com.catfish.ums.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import javax.persistence.Table;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenyj
 * @since 2021-01-25
 */
@Data
@Table(name = "ums_organization")
public class UmsOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id，这里用数字当作id主要是为了方便ancestors字段
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orgName;

    private String orgCode;

    private Long orgPid;

    private String ancestors;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
