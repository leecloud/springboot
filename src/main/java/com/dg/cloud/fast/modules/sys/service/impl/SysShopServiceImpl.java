package com.dg.cloud.fast.modules.sys.service.impl;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.common.utils.Query;
import com.dg.cloud.fast.modules.sys.dao.SysShopDao;
import com.dg.cloud.fast.modules.sys.entity.SysShopEntity;
import com.dg.cloud.fast.modules.sys.entity.SysShopTypeEntity;
import com.dg.cloud.fast.modules.sys.entity.SysUserEntity;
import com.dg.cloud.fast.modules.sys.service.SysShopService;
import com.dg.cloud.fast.modules.sys.service.SysShopTypeService;
import com.dg.cloud.fast.modules.sys.service.SysUserService;
import com.dg.cloud.fast.modules.sys.vo.ShopVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品serviceImpl
 */
@Service("sysShopService")
public class SysShopServiceImpl extends ServiceImpl<SysShopDao,SysShopEntity> implements SysShopService {

    @Autowired
    private SysShopTypeService sysShopTypeService;
    @Autowired
    private SysUserService sysUserService;
    public PageUtils queryPage(Long id, Map<String,Object>params){
        QueryWrapper<SysShopEntity> wrapper = new QueryWrapper<>(new SysShopEntity());
        if (params.get("name") != null){
            wrapper.lambda().like(SysShopEntity::getName,params.get("name").toString());
        }
        if (id != null && !id.equals("")){
            wrapper.lambda().eq(SysShopEntity::getTypeId,id);
        }
        wrapper.lambda().orderByAsc(SysShopEntity::getCreateTime);
        IPage<SysShopEntity> page = this.page(
                new Query<SysShopEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void delete(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }

    public PageUtils queryPage2(Map<String,Object> params){
        QueryWrapper<SysShopEntity> wrapper = new QueryWrapper<>(new SysShopEntity());
        if (params.get("name") != null && !params.get("name").equals("")){
            wrapper.lambda().like(SysShopEntity::getName,params.get("name").toString());
        }
        IPage<SysShopEntity> page = this.page(
                new Query<SysShopEntity>().getPage(params),
               wrapper
        );
        return new PageUtils(page);
    }

    public List<SysShopEntity> treeList(){
        return baseMapper.queryNotButtonList();
    }

    // Vo视图封装
    public List<ShopVo> toVo(List<SysShopEntity> list){
        List<ShopVo> vos = Lists.newArrayList();
        list.stream().forEach(s->{
            ShopVo vo = new ShopVo();
            // 根据类型id获取类型对象
            SysShopTypeEntity byId = sysShopTypeService.getById(s.getTypeId());
            vo.setTypeName(byId.getName());
            vo.setId(s.getId());
            vo.setName(s.getName());
            vo.setPrice(s.getPrice());
            vo.setCreateTime(s.getCreateTime());
            vo.setUpdateTime(s.getUpdateTime());
            // 根据用户id 查询用户信息
            vo.setCreateUserName(sysUserService.getById(s.getCreateUserId()).getUsername());
            vo.setDelFlag(s.isDelFlag());
            vos.add(vo);
        });
        return vos;
    }
}
