package com.ziroom.minsu.services.customer.test;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>基础测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:META-INF/spring/applicationContext-customer.xml"}
)
public class BaseTest {

    @Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
    private String CUSTOMER_DETAIL_URL;

    @Test
    public void demoTest() {
        String uid = "9e9d769e-e8d2-a81d-fe74-fb86903c4bd3"+"a";
        StringBuffer url = new StringBuffer();
        url.append(CUSTOMER_DETAIL_URL).append(uid);
        String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
        Map<String, String> yaoQingRenMsg = null;
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            resultMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
            yaoQingRenMsg = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(resultMap.get("data")));
        } catch (Exception e) {
        }
        Object code = resultMap.get("error_code");
        if (Check.NuNObj(code)) {

        }

        if (Check.NuNMap(yaoQingRenMsg) || Check.NuNStr(yaoQingRenMsg.get("mobile"))) {
            System.err.println("查询失败了" + yaoQingRenMsg);

        } else {
            System.err.println("查询成功!" + yaoQingRenMsg.toString());
            System.err.println("手机号码" + yaoQingRenMsg.get("mobile"));
        }


    }

}
