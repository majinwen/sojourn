package com.ziroom.minsu.portal.fd.center.common.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;

/**
 * <p>单点登录对接返回dto数据</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class LoginDataDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -1987782361893251101L;

	public static final Logger LOGGER = LoggerFactory.getLogger(LoginDataDto.class);

	/**
	 * 返回状态值 含义：http://wiki.ziroom.com/pages/viewpage.action?pageId=2917523#id-%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3-%E9%94%99%E8%AF%AF%E7%A0%81%E5%88%97%E8%A1%A8
	 */
	private String code;

	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 系统别名
	 */
	private String sys;

	/**
	 * 响应消息数据
	 */
	private Map<String, String> resp = new HashMap<String, String>();
	
	public LoginDataDto(){}

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




	public Map<String, String> getResp() {
		return resp;
	}

	public void setResp(Map<String, String> resp) {
		this.resp = resp;
	}

	/**
	 * 
	 * 向data中写入Entity
	 *
	 * @author zhangshaobin
	 * @created 2012-11-14 下午2:09:33
	 *
	 * @param key
	 * @param value
	 */
	public void putValue(final String key, final String value) {
		resp.put(key, value);
	}
	/**
	 * dto内部元素转换成Object
	 * @author jiangnian
	 * @param dto
	 * @param key
	 * @param type
	 * @return
	 */
	public  <T> T  parseResp(String key,TypeReference<T> type){
		ObjectMapper mapper = new ObjectMapper();
		try {
			if(Check.NuNObj(this)){
				return null;
			}
			Map<?, ?> map=this.getResp();
			if(Check.NuNMap(map)){
				return null;
			}
			Object obj=map.get(key);
			String json=JsonEntityTransform.Object2Json(obj);   
			return mapper.readValue(json, type);  
		} catch (JsonParseException e) {
			LOGGER.error("parseData error: JsonParseException", e);
			throw new BusinessException("dto内部元素转换成Object异常。", e);
		} catch (JsonMappingException e) {
			LOGGER.error("parseData error: JsonMappingException", e);
			throw new BusinessException("dto内部元素转换成Object异常。", e);
		} catch (IOException e) {
			LOGGER.error("parseData error: IOException", e);
			throw new BusinessException("dto内部元素转换成Object异常。", e);
		}
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}
	
}
