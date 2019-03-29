package com.dg.cloud.fast.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.common.utils.Query;
import com.dg.cloud.fast.modules.sys.dao.SysEmpDao;
import com.dg.cloud.fast.modules.sys.entity.SysDepEntity;
import com.dg.cloud.fast.modules.sys.entity.SysEmpEntity;
import com.dg.cloud.fast.modules.sys.entity.SysUserEntity;
import com.dg.cloud.fast.modules.sys.service.SysDepService;
import com.dg.cloud.fast.modules.sys.service.SysEmpService;
import com.dg.cloud.fast.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysEmpService")
public class SysEmpServiceImpl extends ServiceImpl<SysEmpDao, SysEmpEntity> implements SysEmpService {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysDepService sysDepService;
    @Resource
    private SysEmpDao sysEmpDao;

    public PageUtils list(Map<String,Object> params){
        QueryWrapper<SysEmpEntity> wrapper = new QueryWrapper<>(new SysEmpEntity());
        if (params.get("name") != null && !params.get("name").equals("")){
            wrapper.lambda().like(SysEmpEntity::getName,params.get("name").toString());
        }
        wrapper.lambda().orderByDesc(SysEmpEntity::getCreateTime);
        wrapper.lambda().eq(SysEmpEntity::isDelFlag,false);
        IPage<SysEmpEntity> page = this.page(
                new Query<SysEmpEntity>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<SysEmpEntity> list = (List<SysEmpEntity>) pageUtils.getList();
        list.stream().forEach(s->{
            SysUserEntity userEntity = sysUserService.getById(s.getCreateUserId());
            if (userEntity != null){
                s.setCreateUserName(userEntity.getUsername());
            }
            SysDepEntity depEntity = sysDepService.getById(s.getDepId());
            if (depEntity != null){
                s.setDepName(depEntity.getName());
            }
        });
        pageUtils.setList(list);
        return pageUtils;
    }


    public void deleteByIds(Long[] ids){
        sysEmpDao.updateByIds(ids);
    }

}
