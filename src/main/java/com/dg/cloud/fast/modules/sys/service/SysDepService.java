package com.dg.cloud.fast.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.modules.sys.entity.SysDepEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface SysDepService extends IService<SysDepEntity> {

    PageUtils page(@RequestParam Map<String,Object> params);

}
