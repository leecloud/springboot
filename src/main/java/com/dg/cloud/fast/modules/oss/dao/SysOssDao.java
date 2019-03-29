package com.dg.cloud.fast.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.cloud.fast.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
