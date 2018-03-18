package com.zra.pay.utils;

import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.NetUtil;
import com.zra.common.utils.PropUtils;
import com.zra.pay.entity.ResultOfPaymentPlatform;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: wangxm113
 * CreateDate: 2017/2/20.
 */
public class CalculateYQWYJ {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CalculateYQWYJ.class);

    /**
     * 判断是否需要计算逾期违约金
     *
     * @Author: wangxm113
     * @CreateDate: 2017-02-20
     */
    public static boolean ifCalYQWYJ(Date planGatherDate) {
        if (planGatherDate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            now = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            LOGGER.error("判断是否需要计算逾期违约金出错！", e);
        }
        // 如果计划收款日期早于当前时间，则计算逾期违约金
        return planGatherDate.before(now);
    }

    /**
     * 调用zrams计算逾期违约金
     *
     * @Author: wangxm113
     * @CreateDate: 2017-02-20
     */
    public static String calYQWYJByMS(String billFid) {
        String result = "0";
        //计算逾期违约金
        String path = PropUtils.getString("zrams.cal.yqwyj.url");
        LOGGER.info("[调用zrams计算逾期违约金]应收账单fid:" + billFid);
        Map<String, String> map = new HashedMap();
        map.put("finReceiBillFid", billFid);
        Map map1 = new HashMap();
        try {
            InputStream inputStream = NetUtil.sendPostRequest(path, map);
            result = NetUtil.getTextContent(inputStream, "utf-8");
            LOGGER.info("[调用zrams计算逾期违约金]应收账单fid:" + billFid + "; 计算结果:" + result);
            map1 = new ObjectMapper().readValue(result, Map.class);
        } catch (Exception e) {
            LOGGER.error("[调用zrams计算逾期违约金]应收账单fid:" + billFid + ";异常信息:", e);
            throw new ResultException(ErrorEnum.MSG_FAIL);
        }
        return map1.get("data") == null ? "0" : map1.get("data").toString();
    }
}
