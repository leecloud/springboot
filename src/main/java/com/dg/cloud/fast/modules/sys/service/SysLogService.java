package com.dg.cloud.fast.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
