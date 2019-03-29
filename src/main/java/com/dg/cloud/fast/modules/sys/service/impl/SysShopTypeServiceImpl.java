package com.dg.cloud.fast.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.cloud.fast.modules.sys.dao.SysShopTypeDao;
import com.dg.cloud.fast.modules.sys.entity.SysShopTypeEntity;
import com.dg.cloud.fast.modules.sys.service.SysShopTypeService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("sysShopTypeService")
public class SysShopTypeServiceImpl extends ServiceImpl<SysShopTypeDao,SysShopTypeEntity> implements SysShopTypeService {

    @Resource
    private SysShopTypeDao sysShopTypeDao;
    public List<Map<String, Object>> getFirstZtree(){
        List<Map<String, Object>> list = baseMapper.selectMaps(null);
       return list;
    }

    public List<SysShopTypeEntity> getTypeById(Long id){
        QueryWrapper<SysShopTypeEntity> queryWrapper = new QueryWrapper<>(new SysShopTypeEntity());
        queryWrapper.lambda().eq(SysShopTypeEntity::getPid,id);
        return baseMapper.selectList(queryWrapper);
    }
}
