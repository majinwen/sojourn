package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.CouponOperateLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年10月23日 09:24
 * @since 1.0
 */
@Repository("cms.couponOperateLogDao")
public class CouponOperateLogDao {
    private String SQLID="cms.couponOperateLogDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 新增优惠券操作日志
     * @author yanb
     * @created 2017年10月23日 11:09:07
     * @param  * @param couponOperateLog
     * @return void
     */
    public void insertCouponOperateLog(CouponOperateLogEntity couponOperateLog) {
        mybatisDaoContext.save(SQLID + "insertCouponOperateLog", couponOperateLog);
    }



}
