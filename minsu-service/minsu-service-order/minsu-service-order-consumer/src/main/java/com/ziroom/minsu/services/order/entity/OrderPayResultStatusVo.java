package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;


/**
 * <p>查询支付结果</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie  2016/4/8
 * @version 1.0
 * @since 1.0
 */
public class OrderPayResultStatusVo extends BaseEntity{
    
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1961981634122065811L;

	/** 处理状态   success或failure*/
	private String status;

    /** 返回内容  支付结果 0：未支付 3：支付中 6：未查到 8：支付失败 9：支付成功*/
    private String data;

    /** 返回状态码 正常返回：I100   E901:输入参数有误  ,E904:系统异常 ,E905:未知异常 */
    private String code;
    
    /** 内容描述 错误描述 */
    private String message;
    
    /** queryState */
    private String apiName;
    
    /** ext */
    private String ext;

    
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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
    
  
}
