package com.dg.cloud.fast.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.common.utils.Query;
import com.dg.cloud.fast.common.utils.R;
import com.dg.cloud.fast.modules.sys.dao.SysDepDao;
import com.dg.cloud.fast.modules.sys.entity.SysDepEntity;
import com.dg.cloud.fast.modules.sys.entity.SysUserEntity;
import com.dg.cloud.fast.modules.sys.service.SysDepService;
import com.dg.cloud.fast.modules.sys.service.SysUserService;
import com.dg.cloud.fast.modules.sys.vo.DepVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysDepService")
public class SysDepServiceImpl extends ServiceImpl<SysDepDao, SysDepEntity> implements SysDepService {

    @Autowired
    private SysUserService sysUserService;
    @Resource
    private SysDepDao sysDepDao;
    @Override
    public PageUtils page(Map<String, Object> params) {
        QueryWrapper<SysDepEntity> wrapper = new QueryWrapper<>();
        if (params.get("name") != null && !params.get("name").equals("")){
            wrapper.lambda().like(SysDepEntity::getName,params.get("name").toString());
        }
        wrapper.lambda().orderByDesc(SysDepEntity::getCreateTime);
        wrapper.lambda().eq(SysDepEntity::isDelFlag,false);
        IPage<SysDepEntity> page = this.page(
                new Query<SysDepEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    public List<DepVo> toDepVo(List<SysDepEntity> list){
        List<DepVo> voList = Lists.newArrayList();
        list.stream().forEach(d->{
            DepVo depVo = new DepVo();
            depVo.setCreateTime(d.getCreateTime());
            SysUserEntity userEntity = sysUserService.getById(d.getCreateUserId());
            if ( userEntity != null){
                depVo.setCreateUserName(userEntity.getUsername());
            }
            depVo.setUpdateTime(d.getUpdateTime());
            depVo.setId(d.getId());
            depVo.setName(d.getName());
            depVo.setDelFlag(d.isDelFlag());
            voList.add(depVo);
        });
        return voList;
    }

    public void  updateByIds(Long[] ids){
        sysDepDao.updateByIds(ids);
    }

    public R getAllDep(){
        QueryWrapper<SysDepEntity> wrapper = new QueryWrapper<>(new SysDepEntity());
        wrapper.lambda().eq(SysDepEntity::isDelFlag,false);
        return R.ok().put("data",baseMapper.selectList(wrapper));
    }



    public R getByUserId(long userId){
        QueryWrapper<SysDepEntity> wrapper = new QueryWrapper<>(new SysDepEntity());
        wrapper.lambda().eq(SysDepEntity::getCreateUserId,userId);
        return R.ok().put("data",baseMapper.selectList(wrapper));
    }

}
