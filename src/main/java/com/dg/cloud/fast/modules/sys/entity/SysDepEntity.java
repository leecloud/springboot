package com.dg.cloud.fast.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("tb_dep")
public class SysDepEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long createUserId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean delFlag;
}
