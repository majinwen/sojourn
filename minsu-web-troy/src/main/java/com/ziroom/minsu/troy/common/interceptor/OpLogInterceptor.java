package com.ziroom.minsu.troy.common.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.OpLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.OpLogService;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.troy.common.abs.AbstractInterceptor;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.constant.Constant;

/**
 * <p>每一个操作记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/5/8.
 * @version 1.0
 * @since 1.0
 */
public class OpLogInterceptor extends  AbstractInterceptor {


    private static final Logger LOGGER = LoggerFactory.getLogger(OpLogInterceptor.class);



    @Resource(name="basedata.opLogApi")
    private OpLogService opLogService;


    private static String regListSuff = "/{0,2}[0-9a-zA-Z|-]*/[0-9a-zA-Z]*/{0,1}[0-9a-zA-Z]*(List|list|Page|page|detail|Detail|Info|info)$";

    private static String regListPre = "/{0,2}[0-9a-zA-Z|-]*/[0-9a-zA-Z]*/{0,1}(List|list|get|Get|query|Query|show|Show|find|Find)[0-9a-zA-Z]*";


    public static void main(String[] args) {
        String aa = "/minsu-web-troy/city/getNationInfo";
        System.out.println(aa.matches(regListSuff));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CurrentuserEntity  user = UserUtil.getFullCurrentUser();
        CurrentuserVo userVo= UserUtil.getFullCurrentUser();
        if(Check.NuNObj(user)){
            //校验当期啊的用户是否存在，只有用户存在才惊醒log的记录
            return true;
        }
        String path = request.getRequestURI();
        if(Check.NuNStr(path)){
            return true;
        }
        if("/".equals(path)){
            return true;
        }
        if(path.contains(".css")){
            return true;
        }
        if(path.contains(".js")){
            return true;
        }
        if(path.contains("/index")){
            return true;
        }
        if(path.endsWith(".jpg")){
            return true;
        }
        if(path.contains("noAuthority")){
            return true;
        }

        if("/".equals(path)){
            return true;
        }
        if (path.matches(regListSuff)){
            return true;
        }
        if (path.matches(regListPre)){
            return true;
        }
        // 获得请求中的参数
        Map par = request.getParameterMap();
        String queryString = JsonEntityTransform.Object2Json(par);
        if (Check.NuNStrStrict(queryString)) {
            queryString = "";
        }
        // 拼凑得到登陆之前的地址
        String realPath = path + "?" + queryString;
        if(realPath.length() > Constant.OP_LOG_LENG_MAX){
        	LogUtil.info(LOGGER,"the url is too long, the org url :{}",realPath);
            realPath = realPath.substring(0,Constant.OP_LOG_LENG_MAX);
        }
        this.saveLog(user,userVo,path);
        return super.preHandle(request, response, handler);
    }

    /**
     * 异步保存当前的信息
     * @param uid
     * @param realPath
     */
    private void saveLog(final CurrentuserEntity user,final CurrentuserVo userVo,final String realPath){
        try{
            Thread task = new Thread(){
                @Override
                public void run() {
                	OpLogEntity opLog = new OpLogEntity();
                    opLog.setOpEmployeeId(user.getFid());
                    opLog.setOpEmployeeName(userVo.getFullName());
                    opLog.setOpEmployeeCode(userVo.getEmpCode());
                    opLog.setOpAppNo(2);
                    opLog.setOpUrl(realPath);
                    LogUtil.info(LOGGER,"save info :{}", JsonEntityTransform.Object2Json(opLog));
                    opLogService.saveOpLogInfo(opLog);
                }
            };
            SendThreadPool.execute(task);
        }catch(Exception e){
            LogUtil.error(LOGGER,"the Exception on save the op log the par is userId:{}. url :{},e={}", user,userVo, realPath,e);
        }
    }
}
