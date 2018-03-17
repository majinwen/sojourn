package com.ziroom.minsu.activity.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>优惠前相关的操作</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/9.
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/mobile")
@Controller
public class MobileController {
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(MobileController.class);

    @Resource(name = "basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @Autowired
    private RedisOperations redisOperations;
    
    



    /**
     * 发送验证码
     * @param response
     * @param mobile
     * @param mobileCode
     * @param request
     * @throws IOException
     */
    @RequestMapping("getMobileCode")
    public @ResponseBody
    void getMobileCode(HttpServletResponse response, String mobile, String mobileCode, HttpServletRequest request) throws IOException{
        DataTransferObject dto = new DataTransferObject();
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
        if(Check.NuNStr(mobile)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("电话为空");
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return;
        }
        //获取验证码
        String code = randomUtil.getNumrOrChar(6, "num");
        if (Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取验证码异常");
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return;
        }
        LogUtil.info(LOGGER, "通过手机号获取验证码 mobile:{}，vcode:{}",mobile,code);

        String key = RedisKeyConst.getMobileCodeKey(mobile,mobileCode);
        try {
            this.redisOperations.setex(key, RedisKeyConst.MOBILE_CODE_CACHE_TIME, code);
            LogUtil.info(LOGGER, "通过手机号获取验证码 mobile:{}，code:{}",mobile,code);
            SmsRequest smsRequest = new SmsRequest();
            smsRequest.setMobile(mobile);
            smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.MOBILE_CODE.getCode()));
            Map<String, String> paMap = new HashMap<String, String>();
            paMap.put("{1}", code);
            paMap.put("{2}", String.valueOf(RedisKeyConst.MOBILE_CODE_CACHE_TIME/60));
            smsRequest.setParamsMap(paMap);
            DataTransferObject dtoSend = JsonEntityTransform.json2DataTransferObject( this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
            if(dtoSend.getCode() != DataTransferObject.SUCCESS){
                LogUtil.error(LOGGER," 发送短信失败 mobile:{}，vcode:{},json:{}",mobile,code,dtoSend.toJsonString());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("发送验证码异常");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER," mobile:{}，vcode:{},e:{}",mobile,code,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取验证码异常");
        }
        //返回验证码
        response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    }



}
