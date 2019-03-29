package com.dg.cloud.fast.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.common.utils.R;
import com.dg.cloud.fast.modules.sys.entity.SysDepEntity;
import com.dg.cloud.fast.modules.sys.service.SysDepService;
import com.dg.cloud.fast.modules.sys.service.impl.SysDepServiceImpl;
import com.dg.cloud.fast.modules.sys.vo.DepVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/dep")
public class SysDepController extends AbstractController {

    @Autowired
    private SysDepService sysDepService;
    @Autowired
    private SysDepServiceImpl sysDepServiceImpl;

    /**
     * 分页获取部门列表
     * @param params
     * @return
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String,Object> params){
        PageUtils page = sysDepService.page(params);
        List<DepVo> voList = sysDepServiceImpl.toDepVo((List<SysDepEntity>) page.getList());
        page.setList(voList);
        return R.ok().put("data",page);
    }

    /**
     * 删除部门del_flag = true
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        if (ids == null || ids.length <= 0){
            return R.error("请提供对应的id");
        }
        //sysDepServiceImpl.removeByIds(Arrays.asList(ids));
        sysDepServiceImpl.updateByIds(ids);
        return R.ok("删除成功");
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id")Long id){
        if (id == null || id.equals("")){
            return R.error("请提供id才能执行该操作");
        }
        SysDepEntity depEntity = sysDepServiceImpl.getById(id);
        if (depEntity == null){
            return R.error("数据有误");
        }
        return R.ok().put("data",depEntity);
    }

    /**
     * 更新
     * @param sysDepEntity
     * @return
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysDepEntity sysDepEntity){
        if (sysDepEntity.getId() == null || sysDepEntity.getId().equals("")){
            return R.error("请提供要操作的id编号");
        }
        sysDepEntity.setUpdateTime(LocalDateTime.now());
        sysDepServiceImpl.updateById(sysDepEntity);
        return R.ok("操作成功");
    }

    /**
     * 添加操作
     * @param sysDepEntity
     * @return
     */
    @RequestMapping("/save")
    public R save (@RequestBody SysDepEntity sysDepEntity){
        sysDepEntity.setCreateTime(LocalDateTime.now());
        sysDepEntity.setCreateUserId(getUserId());
        sysDepEntity.setDelFlag(false);
        sysDepServiceImpl.save(sysDepEntity);
        return R.ok("添加成功");
    }

}
