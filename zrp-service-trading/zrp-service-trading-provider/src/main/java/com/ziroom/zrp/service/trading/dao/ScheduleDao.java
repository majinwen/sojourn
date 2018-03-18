package com.ziroom.zrp.service.trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.ScheduleEntity;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;
/**
 * <p>排班主表DAO</p>
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
@Repository("trading.scheduleDao")
public class ScheduleDao {
	
	private String SQLID = "trading.scheduleDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    /**
     * <p>保存排班实体</p>
     * @author xiangb
     * @created 2017年11月2日
     */
    public int saveSchedule(ScheduleEntity schedule){
    	return this.mybatisDaoContext.save(SQLID+"saveSchedule", schedule);
    }
    /**
     * <p>通过ID查询排班表信息</p>
     * @author xiangb
     * @created 2017年11月2日
     */
    public ScheduleEntity findScheduleById(String id){
    	return this.mybatisDaoContext.findOne(SQLID+"findById",ScheduleEntity.class, id);
    }
    
    
    /**
     * <p>通过项目ID查询当天值班的管家列表</p>
     * @author xiangb
     * @created 2017年11月2日
     */
    public List<SchedulePersonEntity> findSchedulePersonByProjectId(String projectId,String week){
    	Map<String, Object> map = new HashMap<>();
    	map.put("projectId", projectId);
    	map.put("week", week);
    	return this.mybatisDaoContext.findAll(SQLID+"findSchedulePersonByProjectId",SchedulePersonEntity.class, map);
    }

}
