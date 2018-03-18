package com.zra.api.crm;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zra.common.utils.MessageUtil;
import com.zra.common.utils.PropUtils;

/**
 * 400api
 */
@Component
public class KeeperPhoneApi {

    private static Logger LOGGER = Logger.getLogger(KeeperPhoneApi.class);


    /**
     * 新增分机号
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public JSONObject addKeeperPhone(Map<String, Object> paramMap) {
        String url = PropUtils.getString("CRM_RESERVE_ADD_URL");
        //TODO 测试
        LOGGER.info("addKeeperPhone请求地址：" + url);
        String param = MessageUtil.getParameter(paramMap);
        LOGGER.info("addKeeperPhone请求参数为：" + param);
        JSONObject result = null;
        //接入cat监控远程调用用到的时间
        Transaction transaction = Cat.newTransaction("remoteRequest/crm", "addKeeperPhone");
        try {
            result = MessageUtil.postTransmessage(url, param);
            //设置成功状态
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            //设置失败状态
            transaction.setStatus(e);
            LOGGER.error("ddKeeperPhone请求异常：", e);
        } finally {
            //关闭Transaction
            transaction.complete();
        }
        LOGGER.info("addKeeperPhone返回结果：" + result);
        return result;
    }

    /**
     * 删除分机号
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public JSONObject delKeeperPhone(Map<String, Object> paramMap) {
        String url = PropUtils.getString("CRM_RESERVE_DEL_URL");
        //TODO 测试
        LOGGER.info("delKeeperPhone请求地址：" + url);
        String param = MessageUtil.getParameter(paramMap);
        LOGGER.info("delKeeperPhone请求参数为：" + param);
        JSONObject result = null;
        //接入cat监控远程调用用到的时间
        Transaction transaction = Cat.newTransaction("remoteRequest/crm", "delKeeperPhone");
        try {
            result = MessageUtil.postTransmessage(url, param);
            //设置成功状态
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            //设置失败状态
            transaction.setStatus(e);
            LOGGER.error("delKeeperPhone请求异常：", e);
        } finally {
            //关闭Transaction
            transaction.complete();
        }
        LOGGER.info("delKeeperPhone返回结果：" + result);
        return result;
    }


    /**
     * 检查管家在职状态
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public JSONObject checkKeeperStatus(Map<String, Object> paramMap) {
        String url = PropUtils.getString("EHR_GETUSERMSG_URL");
        LOGGER.info("checkKeeperStatus请求地址：" + url);
        String param = MessageUtil.getParameter(paramMap);
        LOGGER.info("checkKeeperStatus请求参数为：" + param);
        JSONObject result = null;
        //接入cat监控远程调用用到的时间
        Transaction transaction = Cat.newTransaction("remoteRequest/ehr", "checkKeeperStatus");
        try {
            result = MessageUtil.postTransmessage(url, param);
            //设置成功状态
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            //设置失败状态
            transaction.setStatus(e);
            LOGGER.error("checkKeeperStatus请求地址", e);
        } finally {
            //关闭Transaction
            transaction.complete();
        }
        LOGGER.info("checkKeeperStatus返回结果：" + result);
        return result;
    }


}
