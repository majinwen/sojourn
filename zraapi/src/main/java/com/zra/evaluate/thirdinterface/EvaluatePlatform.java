package com.zra.evaluate.thirdinterface;

import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.NetUtil;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.ZraApiConst;
import com.zra.evaluate.entity.dto.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class EvaluatePlatform {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(EvaluatePlatform.class);

    /**
     * 获取评价问题信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    public static GetQuestionReturnDto getQuestion(GetQuestionDto dto) throws IOException {
        ObjectMapper om = new ObjectMapper();
        String order = om.writeValueAsString(dto);
        LOGGER.info("[获取评价问题信息]受评人:" + dto.getBeEvaluatorId() + "，获取评价问题信息参数:" + order);
        // 调用获取评价问题信息接口，获取返回结果
        String resultStr = null;
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("channelCode", dto.getChannelCode());
            param.put("beEvaluatorId", dto.getBeEvaluatorId());
            param.put("beEvaluatorType", dto.getBeEvaluatorType());
            param.put("questionType", dto.getQuestionType());
             InputStream is = NetUtil.sendPostRequest(PropUtils.getString(ZraApiConst.EVALUATE_GETQUESTION_URL), param);
            resultStr = NetUtil.getTextContent(is, ZraApiConst.ENCODING_UTF);
        } catch (Exception e) {
            LOGGER.error("[获取评价问题信息]出错！", e);
            throw new ResultException(ErrorEnum.MSG_EVALUATE_GETQUERUN_FAIL);
        }
        LOGGER.info("[获取评价问题信息]受评人:" + dto.getBeEvaluatorId() + "，获取评价问题信息结果:" + resultStr);

        // 判断返回字符为空失败
        if (resultStr == null || resultStr.length() <= 0) {
            throw new ResultException(ErrorEnum.MSG_EVALUATE_GETQUESTION_NULL);
        }

        // 封装评价下单返回结果
        GetQuestionReturnDto result = null;
        try {
            result = om.readValue(resultStr, GetQuestionReturnDto.class);
        } catch (Exception e) {
            EvaluateReturnDto failResult = om.readValue(resultStr, EvaluateReturnDto.class);
            LOGGER.error("[获取评价问题信息]受评人:" + dto.getBeEvaluatorId() + "，获取评价问题信息返回错误！异常信息:" + failResult.getMessage() + "!", e);
            throw new ResultException(ErrorEnum.MSG_EVALUATE_GETQUERETURN_FAIL);
        }
        return result;
    }

    /**
     * 评价
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    public static String evaluatePlatform(EvaluateDto dto) throws IOException {
        ObjectMapper om = new ObjectMapper();
        String order = om.writeValueAsString(dto.getEvaluateMsg());
        LOGGER.info("[调用评价系统]请评对象id:" + dto.getEvaluateMsg().getRequesterId() + "调用评价平台的参数:" + order);
        // 调用评价接口，获取返回结果
        String resultStr = null;
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("tokenId", dto.getTokenId());
            param.put("evaluateMsg", order);
            InputStream is = NetUtil.sendPostRequest(PropUtils.getString(ZraApiConst.EVALUATE_URL), param);
            resultStr = NetUtil.getTextContent(is, ZraApiConst.ENCODING_UTF);
        } catch (Exception e) {
            LOGGER.error("[调用评价系统]出错！", e);
            throw new ResultException(ErrorEnum.MSG_EVALUATE_RUN_FAIL);
        }
        LOGGER.info("[调用评价系统]请评对象id:" + dto.getEvaluateMsg().getRequesterId() + "调用评价平台的结果:" + resultStr);

        // 判断返回字符为空评价失败，更新评价直接返回
        if (resultStr == null || resultStr.length() <= 0) {
            throw new ResultException(ErrorEnum.MSG_EVALUATE_RETURN_NULL);
        }

        // 封装评价下单返回结果
        EvaluateReturnDto result = om.readValue(resultStr, EvaluateReturnDto.class);
        if ("success".equals(result.getStatus())) {
            return result.getMessage();// 支付平台返回的信息都在result中，目前，如果正确只返回给调用者支付订单号，错误抛异常
        } else {
            LOGGER.info("[调用评价系统]请评对象id:" + dto.getEvaluateMsg().getRequesterId() + "，调用评价平台返回错误！异常信息:" + result.getMessage() + "!");
            throw new ResultException(ErrorEnum.MSG_EVALUATE_RETURN_FAIL);
        }
    }

    /**
     * 获取评价历史详情
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    public static EvaluateHistoryReturnDto getEvaluateHistory(EvaluateHistoryDto dto) throws IOException {
        ObjectMapper om = new ObjectMapper();
        String order = om.writeValueAsString(dto);
        LOGGER.info("[获取评价历史详情]请评对象:" + dto.getRequesterId() + "，获取评价历史详情参数:" + order);
        // 调用获取评价问题信息接口，获取返回结果
        String resultStr = null;
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("requesterId", dto.getRequesterId());
            param.put("tokenId", dto.getTokenId());
            InputStream is = NetUtil.sendPostRequest(PropUtils.getString(ZraApiConst.EVALUATE_HISTORY_URL), param);
            resultStr = NetUtil.getTextContent(is, ZraApiConst.ENCODING_UTF);
        } catch (Exception e) {
            LOGGER.error("[获取评价历史详情]出错！", e);
            throw new ResultException(ErrorEnum.MSG_EVALUATE_HISRETURN_FAIL);
        }
        LOGGER.info("[获取评价历史详情]请评对象:" + dto.getRequesterId() + "，获取评价历史详情参数结果:" + resultStr);

        // 判断返回字符为空失败
        if (resultStr == null || resultStr.length() <= 0) {
            throw new ResultException(ErrorEnum.MSG_EVALUATE_HISTORY_NULL);
        }

        // 封装评价下单返回结果
        EvaluateHistoryReturnDto result = null;
        try {
            result = om.readValue(resultStr, EvaluateHistoryReturnDto.class);
        } catch (Exception e) {
            EvaluateReturnDto failResult = om.readValue(resultStr, EvaluateReturnDto.class);
            LOGGER.error("[获取评价历史详情]请评对象:" + dto.getRequesterId() + "，获取评价历史详情返回错误！异常信息:" + failResult.getMessage() + "!", e);
            throw new ResultException(ErrorEnum.MSG_EVALUATE_GETQUERETURN_FAIL);
        }
        return result;
    }

}
