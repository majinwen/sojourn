/*
 * Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.portal.fd.center.common.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

/**
 * <p>Spring 3.x/4.x只支持1.3.x的velocity tools，要使用2.0必须自己扩展VelocityToolboxView类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @date 2016/3/23 15:23
 * @since 1.0
 */
public class VelocityToolbox2View extends VelocityToolboxView {
	
	public VelocityToolbox2View(){
		//staticUrl = SystemGlobals.getValue(HTTP_STATIC_URL);
		
	}

    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewToolContext velocityContext;
        velocityContext = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());
        // 增加服务器路径
        model.put("SERVER_HOST", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
        // 上下文
        model.put("SERVER_CONTEXT", request.getContextPath().equalsIgnoreCase("/") ? "" : request.getContextPath());
        //CDN的静态地址
       // model.put("HTTP_STATIC_URL", staticUrl);
        model.put("path", request.getContextPath().equalsIgnoreCase("/") ? "" : request.getContextPath());
        
        String basePath = null;
        if(model.get("SERVER_HOST")!=null&&model.get("SERVER_CONTEXT")!=null) basePath = model.get("SERVER_HOST").toString()+model.get("SERVER_CONTEXT").toString();
        model.put("basePath", basePath);
    
        velocityContext.putAll(model);

        if (this.getToolboxConfigLocation() != null) {
            ToolManager tm = new ToolManager();
            tm.setVelocityEngine(getVelocityEngine());
            tm.configure(getServletContext().getRealPath(getToolboxConfigLocation()));
            if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {
                velocityContext.addToolbox(tm.getToolboxFactory().createToolbox(Scope.REQUEST));
            }
            if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {
                velocityContext.addToolbox(tm.getToolboxFactory().createToolbox(Scope.APPLICATION));
            }
            if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {
                velocityContext.addToolbox(tm.getToolboxFactory().createToolbox(Scope.SESSION));
            }
        }
        return velocityContext;
    }

    
    
}
