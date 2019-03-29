package com.dg.cloud.fast.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.common.utils.Query;
import com.dg.cloud.fast.modules.sys.dao.SysLogDao;
import com.dg.cloud.fast.modules.sys.entity.SysLogEntity;
import com.dg.cloud.fast.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
                .lambda().orderByDesc(SysLogEntity::getCreateDate)
        );

        return new PageUtils(page);
    }
}
