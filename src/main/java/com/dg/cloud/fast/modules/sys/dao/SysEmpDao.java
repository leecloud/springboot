package com.dg.cloud.fast.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.cloud.fast.modules.sys.entity.SysEmpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface SysEmpDao extends BaseMapper<SysEmpEntity> {

    void updateByIds(@Param("ids") Long[] ids);

}
