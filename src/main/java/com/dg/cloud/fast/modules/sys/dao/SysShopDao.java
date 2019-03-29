package com.dg.cloud.fast.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.cloud.fast.modules.sys.entity.SysShopEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品dao
 */
@Mapper
public interface SysShopDao extends BaseMapper<SysShopEntity> {

    List<SysShopEntity> queryNotButtonList();

}
