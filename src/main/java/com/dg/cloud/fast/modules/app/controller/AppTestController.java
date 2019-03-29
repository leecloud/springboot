package com.dg.cloud.fast.modules.app.controller;


import com.dg.cloud.fast.common.utils.R;
import com.dg.cloud.fast.modules.app.annotation.Login;
import com.dg.cloud.fast.modules.app.annotation.LoginUser;
import com.dg.cloud.fast.modules.app.entity.UserEntity;
import com.dg.cloud.fast.modules.sys.controller.AbstractController;
import com.dg.cloud.fast.modules.sys.entity.SysUserEntity;
import com.dg.cloud.fast.modules.sys.service.impl.SysDepServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP测试接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP测试接口")
public class AppTestController extends AbstractController {
    @Autowired
    private SysDepServiceImpl sysDepServiceImpl;

    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public R userInfo(@LoginUser UserEntity user){
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }

    @Login
    @RequestMapping("/getDepByUserId")
    @ApiOperation("根据用户id获取用户下面的部门")
    public R getDepByUserId(@LoginUser SysUserEntity sysUserEntity){
        return sysDepServiceImpl.getByUserId(sysUserEntity.getUserId());
    }

}
