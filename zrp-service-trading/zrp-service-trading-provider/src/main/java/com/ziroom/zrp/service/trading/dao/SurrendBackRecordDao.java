package com.ziroom.zrp.service.trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.SurrendBackRecordEntity;

/**
 * <p>退租审核不通过原因</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月21日
 * @since 1.0
 */
@Repository("trading.surrendBackRecordDao")
public class SurrendBackRecordDao {
	
	private String SQLID = "trading.surrendBackRecordDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * <p>更新原因</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public int updateSurrendBackRecord(SurrendBackRecordEntity surrendBackRecordEntity){
    	return mybatisDaoContext.update(SQLID+"update",surrendBackRecordEntity);
    }
    /**
     * <p>保存原因</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public int saveSurrendBackRecord(SurrendBackRecordEntity surrendBackRecordEntity){
    	if(Check.NuNStr(surrendBackRecordEntity.getFid())){
    		surrendBackRecordEntity.setFid(UUID.randomUUID().toString());
    	}
    	return mybatisDaoContext.update(SQLID+"insert",surrendBackRecordEntity);
    }
    
    /**
     * <p>根据参数查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public List<SurrendBackRecordEntity> findSurrendBackRecordEntityByParam(String surrenderId,String type){
    	Map<String,String> map = new HashMap<>();
    	map.put("surrenderId", surrenderId);
    	map.put("type", type);
    	return mybatisDaoContext.findAll(SQLID+"findSurrendBackRecordEntityByParam",SurrendBackRecordEntity.class,map);
    }

}
