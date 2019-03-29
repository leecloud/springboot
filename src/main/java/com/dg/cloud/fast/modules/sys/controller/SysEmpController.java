package com.dg.cloud.fast.modules.sys.controller;

import com.dg.cloud.fast.common.utils.R;
import com.dg.cloud.fast.modules.sys.entity.SysEmpEntity;
import com.dg.cloud.fast.modules.sys.service.impl.SysDepServiceImpl;
import com.dg.cloud.fast.modules.sys.service.impl.SysEmpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/sys/emp")
public class SysEmpController extends AbstractController {

    @Autowired
    private SysEmpServiceImpl sysEmpServiceImpl;
    @Autowired
    private SysDepServiceImpl sysDepServiceImpl;

    /**
     * 分页数据
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String,Object> params){
        return R.ok().put("data",sysEmpServiceImpl.list(params));
    }

    /**
     * 删除（逻辑删除）
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        if (ids == null || ids.length <= 0){
            return R.error("请提供id才能操作");
        }
        sysEmpServiceImpl.deleteByIds(ids);
        return R.ok();
    }

    /**
     * 获取没有删除的部门
     * @return
     */
    @RequestMapping("/allDep")
    public R getAllDep(){
        return sysDepServiceImpl.getAllDep();
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        if (id == null || id.equals("")){
            return R.error("请提供id才能执行该操作");
        }
        return R.ok().put("data",sysEmpServiceImpl.getById(id));
    }

    /**
     * 添加
     * @param sysEmpEntity
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody SysEmpEntity sysEmpEntity){
        if (sysEmpEntity == null){
            return R.error("请求参数有问题");
        }
        if (sysEmpEntity.getDepId() == null || sysEmpEntity.getDepId().equals("")){
            return R.error("请提供部门id");
        }
        sysEmpEntity.setCreateUserId(getUserId());
        sysEmpEntity.setCreateTime(LocalDateTime.now());
        sysEmpServiceImpl.save(sysEmpEntity);
        return R.ok();
    }

    /**
     * 修改
     * @param sysEmpEntity
     * @return
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysEmpEntity sysEmpEntity){
        if (sysEmpEntity.getId() == null || sysEmpEntity.getId().equals("")){
            return R.error("请提供id才能执行操作");
        }
        if (sysEmpEntity.getDepId() == null || sysEmpEntity.getDepId().equals("")){
            return R.error("请提供depId");
        }
        sysEmpEntity.setDepId(sysEmpEntity.getDepId());
        sysEmpEntity.setUpdateTime(LocalDateTime.now());
        sysEmpServiceImpl.updateById(sysEmpEntity);
        return R.ok();
    }

}
