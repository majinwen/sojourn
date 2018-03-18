package com.zra.common.error;

import com.ziroom.platform.tesla.common.errors.ErrorInfo;
import com.ziroom.platform.tesla.server.errors.TeslaException;
import com.zra.common.enums.ErrorEnum;

/**
 * Created by cuigh6 on 2016/4/21. 结果错误处理
 */
public class ResultException extends TeslaException {
	private static final long serialVersionUID = -7975675663769500122L;

	private String message;// 错误信息
	private int code;// 错误码

	/**
	 * added by wangxm113
	 * 
	 * @param errorEnum
	 */
	public ResultException(ErrorEnum errorEnum) {
		this(errorEnum.getMessage(), errorEnum.getCode());
	}

	public ResultException(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public ResultException() {
		super();
	}

	@Override
	public int getCode() {
		return code;
	}
	
	@Override
	public String getMessage() {
		 return this.message;
	}

	@Override
	public ErrorInfo getErrorInfo() {
		return new ErrorInfo(String.valueOf(code), message);
	}

}
