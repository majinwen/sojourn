
package com.ziroom.minsu.api.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.HeaderParamName;
import com.ziroom.minsu.api.common.constant.LoginAuthConst;
import com.ziroom.minsu.api.common.dto.GatewayResponseDto;
import com.ziroom.minsu.api.common.dto.ResponseDto;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.services.customer.api.inner.CustomerBlackService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>黑名单拦截器</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @since 1.0
 */
public class CustBlackListInterceptor extends HandlerInterceptorAdapter {


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CustBlackListInterceptor.class);

    @Autowired
    private RedisOperations redisOperations;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;

    @Resource(name = "customer.customerBlackService")
    private CustomerBlackService customerBlackService;

    @Value("#{'${X-Forwarded-Ziroom}'.trim()}")
    private String X_Forwarded_Ziroom;
    /**
     * 解密类型工具:默认DES
     */
    private String encryptType = "DES";

    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResponseDto responseDto = new ResponseDto();

        try {
            Map<String, String> map = new HashMap<String, String>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);

            }
            String imei = null;
            String traceinfo = "";
            try {
                traceinfo = request.getHeader("traceInfo");
                JSONObject traceObject = JSON.parseObject(traceinfo);
                if (!Check.NuNObj(traceObject)) {
                    imei = traceObject.getString("imei");
                }
            } catch (Exception e) {
                LogUtil.info(logger, "traceInfo={}", traceinfo);
            }
            String uid = map.get(LoginAuthConst.UID);
            //判断是否在黑名单中
            if (isBlack(uid, imei)) {
                LogUtil.info(logger, "uid在黑名单中，不允许访问");
                getResponseMsg(response, request, "您的账号因违反自如民宿服务协议，已被禁用", responseDto, -1);
                return false;
            }

            return super.preHandle(request, response, handler);

        } catch (Exception e) {
            LogUtil.error(logger, "登录失败e={}", e);
            getResponseMsg(response, request, "黑名单校验异常", responseDto, 1);
        }
        return false;
    }


    /**
     * 用户是否在黑名单列表中
     *
     * @param uid
     * @return
     * @author jixd
     */
    private boolean isBlack(String uid, String imei) throws Exception {
        boolean is = false;
        if (!Check.NuNStr(imei)) {
            String resultJson = customerBlackService.findCustomerBlackByImei(imei);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(logger, "findCustomerBlackByImei return :{}", resultJson);
                throw new Exception("调用民宿黑名单服务异常");
            }
            CustomerBlackEntity customerBlackEntity = dto.parseData("obj", new TypeReference<CustomerBlackEntity>() {
            });
            if (!Check.NuNObj(customerBlackEntity)) {
                is = true;
                return is;
            }
        }

        if (Check.NuNStr(uid)) {
            is = false;
            return is;
        }
        String resultJson = customerBlackService.findCustomerBlackByUid(uid);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        if (dto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(logger, "findCustomerBlackByUid return :{}", resultJson);
            throw new Exception("调用民宿黑名单服务异常");
        }
        CustomerBlackEntity customerBlackEntity = dto.parseData("obj", new TypeReference<CustomerBlackEntity>() {
        });
        if (!Check.NuNObj(customerBlackEntity)) {
            is = true;
            return is;
        }
        return is;
    }


    /**
     * 重新登录公用返回
     *
     * @param response
     * @param msg
     * @param responseDto
     * @throws BusinessException
     * @throws IOException
     * @author yd
     * @created 2016年5月20日 上午2:06:32
     */
    private void getResponseMsg(HttpServletResponse response, HttpServletRequest request, String msg, ResponseDto responseDto, int status) throws BusinessException, IOException {

        if (Check.NuNObj(responseDto)) responseDto = new ResponseDto();
        IEncrypt encrypt2 = EncryptFactory.createEncryption(encryptType);
        response.setContentType("application/json; charset=utf-8");
        responseDto.setStatus(String.valueOf(status));
        responseDto.setMessage(msg);
        String forwarded = request.getHeader(HeaderParamName.X_Forwarded_Ziroom);
        if (X_Forwarded_Ziroom.equals(forwarded)) {
            //来自网关的请求 不加密返回
            response.getOutputStream().write(JsonEntityTransform.Object2Json(GatewayResponseDto.response2GatewayDto(responseDto)).getBytes());
        } else {
            response.getOutputStream().write(encrypt2.encrypt(JsonEntityTransform.Object2Json(responseDto)).getBytes());
        }
        response.flushBuffer();
    }


}
