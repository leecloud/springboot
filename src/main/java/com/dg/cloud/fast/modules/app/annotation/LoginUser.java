package com.dg.cloud.fast.modules.app.annotation;

import java.lang.annotation.*;

/**
 * 登录用户信息
 *加上此注解就可以获取到用户的信息
 * @author Mark sunlightcs@gmail.com
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
