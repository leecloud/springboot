package com.dg.cloud.fast.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.cloud.fast.modules.sys.entity.SysShopEntity;

/**
 * 商品service
 */
public interface SysShopService extends IService<SysShopEntity> {

    void delete(Long[] ids);

}
