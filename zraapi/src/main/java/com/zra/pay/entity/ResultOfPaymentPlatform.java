package com.zra.pay.entity;

/**
 * @Author: wangxm113
 * @CreateDate: 2016年5月5日
 */
public class ResultOfPaymentPlatform {
	private String status;// 处理状态-success或failure
	private String data;// 返回内容-支付订单号
	private String code;// 返回状态码-正常返回：I100 错误码见字典
	private String message;// 内容描述-错误描述
	private String apiName;// 接口名称 paySubmit
	private String ext;// 附加参数-暂时用来标识支付类型In,Out,Mix

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
}
