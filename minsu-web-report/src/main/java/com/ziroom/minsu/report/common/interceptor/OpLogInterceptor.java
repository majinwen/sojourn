package com.ziroom.minsu.report.common.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.OpLogEntity;
import com.ziroom.minsu.report.common.abs.AbstractInterceptor;
import com.ziroom.minsu.report.common.constant.Constant;
import com.ziroom.minsu.report.common.util.UserUtil;
import com.ziroom.minsu.services.basedata.api.inner.OpLogService;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;

/**
 * <p>保存所有导出操作日志</p>
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

    
    private static String regListSuff = "/{0,2}[0-9a-zA-Z|-]*/[0-9a-zA-Z]*/{0,1}[0-9a-zA-Z]*(ExcelList|excelList|Excel|excel)$";

    public static void main(String[] args) {
        String aa = "/minsu-web-report/tenafdsfsafnt/fdsfsaExcelList";
        System.out.println(aa.matches(regListSuff));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UpsUserVo  user = UserUtil.getUpsUserMsg();
        if(Check.NuNObj(user)){
            //校验当期啊的用户是否存在，只有用户存在才惊醒log的记录
            return true;
        }
        String path = request.getRequestURI();
        
        if(Check.NuNStr(path)){
        	return true;
        }
        if(!path.matches(regListSuff)){
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
        this.saveLog(user,path);
        return super.preHandle(request, response, handler);
    }

    /**
     * 异步保存当前的信息
     * @param uid
     * @param realPath
     */
    private void saveLog(final UpsUserVo user,final String realPath){
        try{
            Thread task = new Thread(){
                @Override
                public void run() {
                    OpLogEntity opLog = new OpLogEntity();
                    opLog.setOpEmployeeId(user.getEmployeeEntity().getFid());
                    opLog.setOpEmployeeName(user.getEmployeeEntity().getEmpName());
                    opLog.setOpEmployeeCode(user.getEmployeeEntity().getEmpCode());
                    opLog.setOpAppNo(1);
                    opLog.setOpUrl(realPath);
                    LogUtil.info(LOGGER,"save info :{}", JsonEntityTransform.Object2Json(opLog));
                    opLogService.saveOpLogInfo(opLog);
                }
            };
            SendThreadPool.execute(task);
        }catch(Exception e){
            LogUtil.error(LOGGER,"the Exception on save the op log the par is userId:{}. url :{},e={}", user, realPath,e);
        }
    }
}
