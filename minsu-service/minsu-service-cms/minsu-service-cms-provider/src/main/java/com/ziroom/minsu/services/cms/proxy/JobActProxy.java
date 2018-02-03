package com.ziroom.minsu.services.cms.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.cms.api.inner.JobActService;
import com.ziroom.minsu.services.cms.service.ActCouponServiceImpl;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;

/**
 * <p>活动相关job</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月16日
 * @since 1.0
 * @version 1.0
 */
@Service("cms.jobActProxy")
public class JobActProxy implements JobActService {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JobActProxy.class);

	
	@Resource(name = "cms.actCouponServiceImpl")
	private ActCouponServiceImpl actCouponServiceImpl;
	
	/**
	 * 修改过期优惠券状态
	 * @author lishaochuan
	 * @create 2016年6月16日下午7:50:35
	 */
	@Override
	public void couponExpireStatus() {
		LogUtil.info(LOGGER, "【优惠券过期Job】");
		try {
            Thread task = new Thread(){
                @Override
                public void run() {
                    int limit = 150;
                    Long count = actCouponServiceImpl.getExpireCount();
                    LogUtil.info(LOGGER, "【优惠券过期Job】条数:{}", count);
                    if (Check.NuNObj(count) || count == 0) {
                        return;
                    }
                    int pageAll = ValueUtil.getPage(count.intValue(), limit);
                    for (int page = 1; page <= pageAll; page++) {
                        int num = actCouponServiceImpl.updateExpireList(limit);
                        LogUtil.info(LOGGER, "【优惠券过期Job】更新条数:{}", num);
                    }
                }
            };
            SendThreadPool.execute(task);
		}catch (Exception e){
            LogUtil.error(LOGGER, "【优惠券过期Job】e:{}", e);
        }
	}
	

	
}
