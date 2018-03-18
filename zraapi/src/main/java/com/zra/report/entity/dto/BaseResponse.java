package com.zra.report.entity.dto;

/**
 * 
 * @author huangy168@ziroom.com
 * @Date 2016年11月2日
 * @Time 下午7:17:32
 */
public class BaseResponse<T> {

    public static final int SUCCESS = 1,FAILED = 0;

    private int result = SUCCESS;

    private String message;
    
    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
