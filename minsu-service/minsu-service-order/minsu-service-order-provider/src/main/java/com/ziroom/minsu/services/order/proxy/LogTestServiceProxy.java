package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.api.inner.LogTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/18.
 * @version 1.0
 * @since 1.0
 */
@Component("order.logTestServiceProxy")
public class LogTestServiceProxy implements LogTestService {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderUserServiceProxy.class);


    @Override
    public String test(String orderSn) {
        DataTransferObject dto = new DataTransferObject();

        LogUtil.info(LOGGER,"开始测试数据:orderSn{}",orderSn);

        if(Check.NuNStr(orderSn)){
            //这种情况log打印成error级别
            LogUtil.error(LOGGER, "当前参数为空", orderSn);
            //该uid用户的订单不存在
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        try {
            if(orderSn.equals("aaa")){
                //这种情况log打印成error级别
                LogUtil.error(LOGGER, "异常的订单数据:orderSn{}", orderSn);
                throw new BusinessException("异常的订单号，找的就是你");
            }

            if(orderSn.equals("bbb")){
                Integer aa = null;
                System.out.println("ssssssssssss");
                System.out.println(aa.toString());
            }
            LogUtil.info(LOGGER, "【结算】【orderSn;{}】 第1步:{}", orderSn,"111");
            LogUtil.info(LOGGER, "【结算】【orderSn;{}】 第2步:{}", orderSn,"222");
            LogUtil.info(LOGGER, "【结算】【orderSn;{}】 第3步:{}", orderSn,"333");
            LogUtil.info(LOGGER, "【结算】【orderSn;{}】 第4步:{}", orderSn,"444");
            LogUtil.info(LOGGER, "【结算】【orderSn;{}】 第5步:{}", orderSn, "555");
            dto.putValue("rst","ok");
        }catch (Exception e){
            LogUtil.error(LOGGER, "处理异常:orderSn{} e:{} ,", orderSn,e);
        }

        return dto.toJsonString();
    }
}
