package com.zra.common.error;


import com.zra.common.enums.ErrorEnum;

import java.util.HashMap;

/**
 * Created by cuigh6 on 2016/4/26.
 * 格式化请求结果类
 */
public class AppResultFormat extends AppResult {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AppResultFormat(Object data, String code, String message, String status) {
        if (data==null){
            data = new HashMap<>();
        }
        this.setData(data);
        super.setError_code(code);
        super.setError_message(message);
        super.setStatus(status);
    }
    
    public AppResultFormat(Object data, ErrorEnum errorEnum, String status) {
    	this(data,String.valueOf(errorEnum.getCode()),errorEnum.getMessage(),status);
    }
}
