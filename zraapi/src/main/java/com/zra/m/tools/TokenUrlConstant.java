package com.zra.m.tools;

import com.zra.common.utils.PropUtils;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public interface TokenUrlConstant {
    String TOKEN_SUCCESS_CODE = "20000";

    String TOKEN_URL_PRE = "TOKEN_URL_PRE";

    /**
     * Token验证
     *
     * @Author: wangxm113
     * @CreateDate: 2017-03-14
     */
    String CHECK_TOKEN_URL = "/auth/v1";
    /**
     * 根据Token获取用户信息
     *
     * @Author: wangxm113
     * @CreateDate: 2017-03-14
     */
    String GET_INFO_BY_TOKEN_URL = "/users/info/v1";
}
