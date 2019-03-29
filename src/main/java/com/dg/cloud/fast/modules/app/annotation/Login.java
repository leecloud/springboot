package com.dg.cloud.fast.modules.app.annotation;

import java.lang.annotation.*;

/**
 * app登录效验
 * 加上此注解，用户必须登录才能访问，不加则相反
 *
 * @author Mark sunlightcs@gmail.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
