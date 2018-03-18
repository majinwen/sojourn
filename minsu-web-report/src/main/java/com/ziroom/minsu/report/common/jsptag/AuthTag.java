/**
 * @FileName: AuthTag.java
 * @Package com.ziroom.minsu.troy.common.jsptag
 * 
 * @author bushujie
 * @created 2016年5月22日 下午4:44:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.common.jsptag;


import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.common.util.UserUtil;

/**
 * <p>自定义权限标签</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class AuthTag extends TagSupport{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3234634512908407214L;
	/**
	 * 权限url
	 */
	private String authUrl;
	
    @Override  
    public int doAfterBody() throws JspException {  
        return super.doAfterBody();  
    }  
  
    @Override  
    public int doEndTag() throws JspException {  
        return super.doEndTag();  
    }  
  
    @Override  
    public int doStartTag() throws JspException {  
        //当前用户权限菜单列表为空
        Set<String> resUrlSet =UserUtil.getUpsUserMsg().getResourceVoSet();
        if(Check.NuNCollection(resUrlSet)||!resUrlSet.contains(authUrl)){  
            return TagSupport.SKIP_BODY;  
        }  
        return TagSupport.EVAL_BODY_INCLUDE;  
    }
	/**
	 * @return the authUrl
	 */
	public String getAuthUrl() {
		return authUrl;
	}

	/**
	 * @param authUrl the authUrl to set
	 */
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
}
