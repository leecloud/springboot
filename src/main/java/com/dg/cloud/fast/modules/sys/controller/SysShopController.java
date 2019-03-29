package com.dg.cloud.fast.modules.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dg.cloud.fast.common.utils.PageUtils;
import com.dg.cloud.fast.common.utils.R;
import com.dg.cloud.fast.modules.sys.entity.SysShopEntity;
import com.dg.cloud.fast.modules.sys.entity.SysShopTypeEntity;
import com.dg.cloud.fast.modules.sys.service.SysShopService;
import com.dg.cloud.fast.modules.sys.service.SysShopTypeService;
import com.dg.cloud.fast.modules.sys.service.SysUserService;
import com.dg.cloud.fast.modules.sys.service.impl.SysShopServiceImpl;
import com.dg.cloud.fast.modules.sys.service.impl.SysShopTypeServiceImpl;
import com.dg.cloud.fast.modules.sys.vo.ShopVo;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 商品controller
 */
@RestController
@RequestMapping("/sys/shop")
public class SysShopController extends AbstractController {
    @Autowired
    private SysShopTypeService sysShopTypeService;
    @Autowired
    private SysShopTypeServiceImpl sysShopTypeServiceImpl;
    @Autowired
    private SysShopService sysShopService;
    @Autowired
    private SysShopServiceImpl sysShopServiceImpl;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取所有父节点
     * @return
     */
    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/getFirstZtree")
    public R getFirstZtree(){
        return R.ok().put("data",sysShopTypeServiceImpl.getFirstZtree());
    }

    /**
     * 获取所有的类型
     * @return
     */
    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/select")
    public R select(){
        return R.ok().put("typeList",sysShopTypeService.list(new QueryWrapper<>(new SysShopTypeEntity())));
    }

    /**
     * 查询返回给前端的数据
     * @return
     */
    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/selectTree")
    public R selectTree(){
        // 查所有数据
        List<Map<String, Object>> list = sysShopTypeServiceImpl.getFirstZtree();
        //System.out.println(JSONArray.toJSON(list));
        List<Map<String, Object>> parentList = Lists.newArrayList();
        for (Map<String, Object> map : list){
            //System.out.println(JSONArray.toJSON(map));
            if (map.get("pid").equals(0)){
                parentList.add(map);
            }
        }
        recursionChildren(parentList,list);
        return R.ok().put("data",parentList);
    }

    /**
     * 递归获取子节点数据
     */
    public static void recursionChildren(List<Map<String, Object>> parentList,List<Map<String, Object>> allList){
        //遍历根节点
        for (Map<String, Object> parentMap: parentList){
            // 定义集合装子类
            List<Map<String, Object>> childrenList = Lists.newArrayList();
            // 遍历所有数据
            for (Map<String, Object> allMap: allList){
                // 如果pid=id说明是它的子类则加入子类的集合中
                if (allMap.get("pid").equals(parentMap.get("id"))) {
                    childrenList.add(allMap);
                }
                // 如果子类集合不为空则说明还有下级
                if (!childrenList.isEmpty()){
                  parentMap.put("children",childrenList);
                  recursionChildren(childrenList,allList);
                }
            }
        }
    }

    /**
     * 根据类型id查询该类型对应的商品
     * @param typeId
     * @return TODO需要分页
     */
    @RequestMapping("/selectByTypeId/{typeId}")
    public R selectByTypeId(@PathVariable("typeId")Long typeId,@RequestParam Map<String, Object> params){
        PageUtils pageUtils = sysShopServiceImpl.queryPage(typeId, params);
        List<SysShopEntity> list = (List<SysShopEntity>) pageUtils.getList();
        pageUtils.setList(sysShopServiceImpl.toVo(list));
        return R.ok().put("data",pageUtils);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequiresPermissions("sys:menu:list")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        if (ids.length == 0 || ids == null){
            return R.error("请提供需要删除的id编号");
        }
        sysShopService.delete(ids);
        return R.ok();
    }

    /**
     * 分页查询所有商品
     * @param params
     * @return
     */
    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/page")
    public  R queryPage(@RequestParam Map<String,Object> params){
        PageUtils pageUtils = sysShopServiceImpl.queryPage2(params);
        List<SysShopEntity> list = (List<SysShopEntity>) pageUtils.getList();
        pageUtils.setList(sysShopServiceImpl.toVo(list));
        return R.ok().put("data",pageUtils);
    }

    /**
     * 添加商品
     * @param shopEntity
     * @return
     */
    @RequestMapping("/add")
     public R add(@RequestBody SysShopEntity shopEntity){
        shopEntity.setCreateTime(LocalDateTime.now());
        shopEntity.setCreateUserId(getUserId());
        shopEntity.setDelFlag(false);
        sysShopService.save(shopEntity);
        return R.ok("添加成功");
     }

    /**
     * 修改
     * @param shopEntity
     * @return
     */
     @RequestMapping("/update")
     public R update(@RequestBody SysShopEntity shopEntity){
        if (shopEntity.getId() == null || shopEntity.getId().equals("")){
            return R.error("请提供商品编号");
        }
        shopEntity.setUpdateTime(LocalDateTime.now());
        sysShopService.updateById(shopEntity);
        return R.ok("修改成功");
     }

    /**
     * 详情
     * @param id
     * @return
     */
     @RequestMapping("/info/{id}")
     public R info(@PathVariable("id")Long id){
         if (id == null || id.equals("")){
             return R.error("请提供商品编号");
         }
         SysShopEntity byId= sysShopService.getById(id);
         return R.ok().put("shop",byId);
     }

    /**
     * 获取树结构
     * @return
     */
     @RequestMapping("/list")
     public R list(){
         List<SysShopEntity> treeList = sysShopServiceImpl.treeList();
         return R.ok().put("treeList",treeList);
     }

    /**
     * 添加子节点
     * @param shopTypeEntity
     * @return
     */
    @RequestMapping("/addTree")
    public R addTree(@RequestBody SysShopTypeEntity shopTypeEntity){
        if (shopTypeEntity == null){
            return R.error("参数问题");
        }
        shopTypeEntity.setCreateUserId(getUserId());
        shopTypeEntity.setCreateTime(LocalDateTime.now());
        shopTypeEntity.setDelFlag(false);
        sysShopTypeService.save(shopTypeEntity);
        return R.ok();
     }

    /**
     * 删除节点
     *
     * @param id
     * @return
     */
     @RequestMapping("/deleteType/{id}")
     public R deleteType(@PathVariable("id")Long id){
        if (id == null || id.equals("")){
            return R.error("请提供节点id");
        }
        // 删除当前节点并且需要把pid=当前节点的数据删除
        sysShopTypeService.removeById(id);
        // 根据id去查询pid=id的数据
         List<SysShopTypeEntity> list = sysShopTypeServiceImpl.getTypeById(id);
         if (list.size() <= 0 || list == null){
             return R.ok();
         }else {
             List<Long> longs = Lists.newArrayList();
             list.stream().forEach(s->{
                 longs.add(s.getId());
             });
             sysShopTypeServiceImpl.removeByIds(longs);
         }
         return R.ok();
     }

    /**
     * 节点详情
     * @param id
     * @return
     */
     @RequestMapping("/selectTypeById/{id}")
     public R selectTypeById(@PathVariable("id")Long id){
         if (id == null || id.equals("")){
             return R.error("请提供id");
         }
         SysShopTypeEntity typeEntity = sysShopTypeService.getById(id);
         return R.ok().put("data",typeEntity);
     }

    /**
     * 修改节点
     * @param shopTypeEntity
     * @return
     */
     @RequestMapping("/updateTree")
     public R updateTree(@RequestBody SysShopTypeEntity shopTypeEntity){
         if (shopTypeEntity.getId() == null || shopTypeEntity.getId().equals("")){
             return R.error("请提供节点id");
         }
         sysShopTypeService.updateById(shopTypeEntity);
         return R.ok();
     }

    /**
     * 添加根节点
     * @param shopTypeEntity
     * @return
     */
     @RequestMapping("/addRoot")
     public R addRoot(@RequestBody SysShopTypeEntity shopTypeEntity){
         shopTypeEntity.setPid(0L);
         shopTypeEntity.setCreateTime(LocalDateTime.now());
         shopTypeEntity.setCreateUserId(getUserId());
         sysShopTypeServiceImpl.save(shopTypeEntity);
         return R.ok();
     }

}
