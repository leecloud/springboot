package com.dg.cloud.fast.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("tb_emp")
public class SysEmpEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long depId;
    private Long createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private boolean delFlag;
    @TableField(exist = false)
    private String depName;
    @TableField(exist = false)
    private String createUserName;
}
