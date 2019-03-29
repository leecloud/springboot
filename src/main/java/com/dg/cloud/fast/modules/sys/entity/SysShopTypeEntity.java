package com.dg.cloud.fast.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("tb_shop_type")
public class SysShopTypeEntity implements Serializable {
    private Long id;
    private String name;
    private Long pid;
    private Long createUserId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean delFlag;
}
