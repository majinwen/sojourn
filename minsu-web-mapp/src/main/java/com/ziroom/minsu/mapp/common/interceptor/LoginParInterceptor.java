package com.ziroom.minsu.mapp.common.interceptor;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.entity.AppRequest;
import com.ziroom.minsu.mapp.common.enumvalue.SourceTypeEnum;
import com.ziroom.minsu.mapp.common.util.StringUtil;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.valenum.version.VersionCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>需要登录信息，但是不要求必须登录的页面拦截器</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/7.
 * @version 1.0
 * @since 1.0
 */
public class LoginParInterceptor  extends LoginInterceptor {


    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //处理不需要登录的参数
        this.dealLoginPar(request);
        return true;
    }
    private void dealLoginPar(HttpServletRequest request){
        //获取当前的session
        HttpSession session = request.getSession();
        session.setAttribute("sourceOrg", 0);

        try {
            String sourceType = request.getParameter("sourceType");//请求来源
            String force = request.getParameter("force");//请求来源
            if(!Check.NuNStr(sourceType)){
                Integer  versionCode = null;//版本号
                String versionCodeStr = request.getParameter("versionCode");//请求版本号
                if(!Check.NuNStr(versionCodeStr)){
                    versionCode = Integer.valueOf(versionCodeStr);
                    LogUtil.info(logger, "当前app版本号versionCode={}", versionCode);
                }
                SourceTypeEnum sourceTypeEnum = SourceTypeEnum.getSourceTypeEnumByCode(Integer.valueOf(sourceType));
                if(!Check.NuNObj(sourceTypeEnum)){
                    AppRequest appRequest = new AppRequest();
                    appRequest.setSourceType(Integer.valueOf(sourceType));
                    //校验参数 不通过 直接到客户端 的登录页面
                    if(!checkParam(request,appRequest)){
                        session.removeAttribute(MappMessageConst.SESSION_USER_KEY);
                        return;
                    }
                    String mySign = StringUtil.getSign(new String[]{appRequest.getUid(),appRequest.getToken(),sourceType,appRequest.getTimestamp(),appRequest.getEchostr()});
                    if(!mySign.equals(appRequest.getSignature())){
                        session.removeAttribute(MappMessageConst.SESSION_USER_KEY);
                        return;
                    }
                    Object customerVoObj = session.getAttribute(MappMessageConst.SESSION_USER_KEY);
                    //存储信息请求来源
                    session.setAttribute("sourceType", sourceType);
                    session.setAttribute("versionCode", VersionCodeEnum.checkClean(versionCode));
                    session.setAttribute("versionCodeInit",versionCode);
                    if (VersionCodeEnum.checkOrg(versionCode)){
                        if (Check.NuNStr(force) || !"1".equals(force)){
                            //当前的版本信息验证通过
                            session.setAttribute("sourceOrg", sourceTypeEnum.getCode());
                        }
                    }
                    CustomerVo customerVo = null;
                    //session 为空  用户存在，保存session，用户不存在，保存用户信息，session只存uid
                    if (Check.NuNObj(customerVoObj)) {
                        customerVo = saveCusotmerVo(appRequest, request, customerVo);
                        session.setAttribute(MappMessageConst.SESSION_USER_KEY, customerVo);
                        if (!Check.NuNObj(customerVo)){
                            request.setAttribute(LoginInterceptor.USERID, customerVo.getUid());
                        }
                        return ;
                    }
                    customerVo =  (CustomerVo) customerVoObj;
                    //用户不一致 清空当前session 保存新的session
                    if(!customerVo.getUid().equals(appRequest.getUid())){
                        session.removeAttribute(MappMessageConst.SESSION_USER_KEY);
                        customerVo = saveCusotmerVo(appRequest, request, customerVo);
                        session.setAttribute(MappMessageConst.SESSION_USER_KEY, customerVo);
                        return ;
                    }
                    /*if (isBlack(customerVo.getUid())){
                        return;
                    }*/
                    request.setAttribute("customerVo", customerVo);
                    request.setAttribute(LoginInterceptor.USERID, customerVo.getUid());
                }
            }
        } catch (IOException e) {
            LogUtil.error(logger, "客户端登录异常e={}", e);
            return;
        }
    }
}
