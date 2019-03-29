package com.dg.cloud.fast.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.cloud.fast.modules.sys.entity.SysDepEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysDepDao extends BaseMapper<SysDepEntity> {

    void updateByIds(@Param("ids") Long[] ids);

}
