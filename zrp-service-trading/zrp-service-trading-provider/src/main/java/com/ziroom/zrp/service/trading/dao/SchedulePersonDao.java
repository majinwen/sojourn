package com.ziroom.zrp.service.trading.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;
/**
 * <p>排班从表DAO</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月2日
 * @since 1.0
 */
@Repository("trading.schedulePersonDao")
public class SchedulePersonDao {
	
	private String SQLID = "trading.schedulePersonDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * <p>通过ID查询排班人员信息</p>
     * @author xiangb
     * @created 2017年11月2日
     */
    public SchedulePersonEntity findSchedulePersonById(String id){
    	return this.mybatisDaoContext.findOne(SQLID+"findById", SchedulePersonEntity.class, id);
    }
    
    
    

}
