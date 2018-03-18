package com.zra.common.security;

import java.lang.annotation.*;

/**
 * 指定字段不能为空
 *
 * @Author: wangxm113
 * @CreateDate: 2016-05-26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNullForZRY {
    String value();
}
