
package com.ziroom.minsu.services.common.utils;

/**
 * Created by lyy on 16/4/14.
 */
public interface ResponseCallback<T>{
    T onResponse(int resultCode, String resultJson);
}
