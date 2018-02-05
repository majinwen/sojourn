package com.ziroom.minsu.api.outer.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.util.HttpsUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/4/28
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/customerou")
public class CustomerController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    /**
     * 自如客key
     */
    private static final String ZIRUKE = "ziroom";

    /**
     * 民宿房东key
     */
    private static final String MINSU_LANDLORD = "minsu_landlord";

    /**
     * 客户类型：
     * 0：无
     * 1：自如客
     * 2：民宿房东
     */
    private static final String CUSTOMER_TYPE = "customer_type";

    @Value("#{'${account_profile_url}'.trim()}")
    private String accountProfileUrl;

    @Value("#{'${account_profile_auth_code}'.trim()}")
    private String authCode;

    @Value("#{'${account_profile_auth_secret_key}'.trim()}")
    private String authSecretKey;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    /**
     * 获取用户昵称和头像地址接口
     *
     * @param HttpServletRequest
     * @return ResponseEntity<ResponseSecurityDto>
     * @author wangwentao
     * @created 2017/4/28 12:01
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/getCustomerInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getCustomerInfo(HttpServletRequest request) {

        Object uid = request.getParameter("uid");
        LogUtil.info(logger, "参数:uid={}", uid);
        //参数校验
        if (Check.NuNObj(uid)) {
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("uid is null"), HttpStatus.OK);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(CUSTOMER_TYPE, "0");
        try {
            //1调用外部接口 查询自如客
            resultMap = queryAccountProfile((String) uid, resultMap);
            //2调用民宿服务: 2.1 外部接口可能调用超时/失败，2.2 判断是否房东
            resultMap = queryMinfuService((String) uid, resultMap);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(logger, "获取用户昵称和头像地址异常:", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("query fail"), HttpStatus.OK);
        }
    }


    /**
     * 从第三方接口获取自如客信息
     *
     * @param String Map<String, Object>
     * @return Map<String, Object>
     * @author wangwentao
     * @created 2017/4/28 11:16
     */
    private Map<String, Object> queryAccountProfile(String uid, Map<String, Object> resultMap) {
        try {
            String url = accountProfileUrl + "?auth_code=" + authCode + "&auth_secret_key=" + authSecretKey + "&uid=" + uid;
            LogUtil.info(logger, "调用第三方[查询个人信息接口]url={}", url);
            String httpResult = HttpsUtil.getRequest(url);
            LogUtil.info(logger, "调用第三方[查询个人信息接口]result={}", httpResult);
            if (!Check.NuNStr(httpResult)) {
                JSONObject httpJson = JSON.parseObject(httpResult);
                if (null != httpJson && httpJson.containsKey("status") && httpJson.containsKey("error_code")) {
                    if ("success".equals(httpJson.getString("status")) && "0".equals(httpJson.getString("error_code"))) {
                        String data = httpJson.getString("data");
                        if (!Check.NuNStr(data)) {
                            JSONObject dataJson = JSON.parseObject(data);
                            Map<String, Object> zirukeMap = new HashMap<>();
                            zirukeMap.put("nickName", dataJson.getString("nick_name"));
                            zirukeMap.put("headImg", dataJson.get("head_img"));
                            resultMap.put(ZIRUKE, zirukeMap);
                            resultMap.put(CUSTOMER_TYPE, "1");
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("调用第三方[查询个人信息接口]异常:", e);
        }
        return resultMap;
    }

    /**
     * 调用民宿服务
     *
     * @param String Map<String, Object>
     * @return Map<String, Object>
     * @author wangwentao
     * @created 2017/4/28 12:10
     */
    private Map<String, Object> queryMinfuService(String uid, Map<String, Object> resultMap) {
        try {
            LogUtil.debug(logger, "调用民宿服务[查询个人信息接口]参数：" + uid);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVo(uid));
            LogUtil.debug(logger, "调用民宿服务[查询个人信息接口]结果：" + dto.toJsonString());
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.info(logger, "调用民宿服务[查询个人信息接口]结果：" + dto.toJsonString());
                throw new BusinessException("调用民宿服务异常");
            }
            CustomerVo customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
            });
            if (!Check.NuNObj(customerVo)) {
                if (!resultMap.containsKey(ZIRUKE)) {
                    //如果之前第三方没有得到自如客信息，那么就赋值查询民宿服务的结果
                    Map<String, Object> zirukeMap = new HashMap<>();
                    zirukeMap.put("nickName", customerVo.getNickName());
                    zirukeMap.put("headImg", customerVo.getUserPicUrl());
                    resultMap.put(ZIRUKE, zirukeMap);
                    resultMap.put(CUSTOMER_TYPE, "1");
                }
                int isLandLord = customerVo.getIsLandlord();
                if (1 == isLandLord) {//是房东
                    Map<String, Object> landLordMap = new HashMap<>();
                    landLordMap.put("nickName", customerVo.getNickName());
                    landLordMap.put("headImg", customerVo.getUserPicUrl());
                    resultMap.put(MINSU_LANDLORD, landLordMap);
                    resultMap.put(CUSTOMER_TYPE, "2");
                } else {
                    Map<String, Object> landLordMap = new HashMap<>();
                    resultMap.put(MINSU_LANDLORD, landLordMap);
                }
            }
        } catch (BusinessException e) {
            throw new BusinessException("调用民宿服务[查询个人信息接口]异常:", e);
        }
        return resultMap;
    }


}
