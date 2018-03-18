package com.zra.common.error;

import com.zra.common.enums.ErrorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

/**
 * Created by cuigh6 on 2016/4/21. 应用请求结果
 */
public abstract class AppResult {

	@ApiModelProperty(value = "错误码")
	private String error_code;
	@ApiModelProperty(value = "错误信息")
	private String error_message;

	@ApiModelProperty(value = "请求状态 值为success 和 fail")
	private String status;

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static AppResult toSuccess(Object data) {
		return new AppResultFormat(data, ErrorEnum.MSG_SUCCESS, "success");
	}

	public static AppResult toSuccess(Object data, ErrorEnum errorEnum) {
		return new AppResultFormat(data, errorEnum, "success");
	}

	public static AppResult toFail(Object data) {
		if (data instanceof ResultException) {
			ResultException re = (ResultException) data;
			return new AppResultFormat(null, String.valueOf(re.getCode()), re.getMessage(), "fail");
		}
		return AppResult.toFail(null, ErrorEnum.MSG_FAIL);
	}

	public static AppResult toFailList(Object data) {
		if (data instanceof ResultException) {
			ResultException re = (ResultException) data;
			return new AppResultFormat(new ArrayList<Object>(), String.valueOf(re.getCode()), re.getMessage(), "fail");
		}
		return AppResult.toFail(new ArrayList<Object>(), ErrorEnum.MSG_FAIL);
	}

	public static AppResult toFail(Object data, ErrorEnum errorEnum) {
		return new AppResultFormat(data, errorEnum, "fail");
	}

}
