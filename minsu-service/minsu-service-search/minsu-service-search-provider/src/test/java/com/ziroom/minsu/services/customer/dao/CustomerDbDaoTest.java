package com.ziroom.minsu.services.customer.dao;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.evaluate.dao.EvaluateDbDao;
import com.ziroom.minsu.services.search.vo.CustomerDbInfoVo;
import com.ziroom.minsu.services.search.vo.EvaluateDbInfoVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
public class CustomerDbDaoTest extends BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDbDaoTest.class);

    @Resource(name="search.customerDbDao")
    private CustomerDbDao customerDbDao;







    @Test
    public void getCustomerCollect(){

        try {
            List<String> evaluateDbInfoVo =customerDbDao.getCustomerCollect("5f4f193b-07fd-a708-85f8-22907004fd6d");
            System.out.println(JsonEntityTransform.Object2Json(evaluateDbInfoVo));
        }catch (Exception e){
            LogUtil.error(LOGGER,"e:{}",e);
        }
    }


    @Test
    public void TestgetEvaluateByHouse(){
        try {
            CustomerDbInfoVo evaluateDbInfoVo =customerDbDao.getCustomerInfo("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
            System.out.println(JsonEntityTransform.Object2Json(evaluateDbInfoVo));
        }catch (Exception e){
            LogUtil.error(LOGGER,"e:{}",e);
        }
    }

}
