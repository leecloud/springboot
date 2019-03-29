package com.dg.cloud.fast.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.cloud.fast.modules.app.entity.UserEntity;
import com.dg.cloud.fast.modules.app.form.LoginForm;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}