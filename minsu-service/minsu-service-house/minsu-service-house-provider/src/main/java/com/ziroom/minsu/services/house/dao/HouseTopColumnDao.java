package com.ziroom.minsu.services.house.dao;


import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseTopColumnEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>TopColumn dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改时间	修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseTopColumnDao")
public class HouseTopColumnDao {


    private String SQLID="house.houseTopColumnDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    

	
    /**
     * 
     * 条件查询 top50 的房源条目
     *
     * @author yd
     * @created 2017年3月17日 下午3:28:19
     *
     * @param paramMap
     * @return
     */
    public List<HouseTopColumnEntity> findHouseTopColumnByHouseTopFid(String houseTopFid){
		return mybatisDaoContext.findAll(SQLID+"findHouseTopColumnByHouseTopFid", HouseTopColumnEntity.class, houseTopFid);
	}
    
    /**
     * 
     * 插入top房源条目
     *
     * @author bushujie
     * @created 2017年3月21日 下午4:27:27
     *
     * @param houseTopColumnEntity
     */
    public void insertHouseTopColumnEntity(HouseTopColumnEntity houseTopColumnEntity){
    	mybatisDaoContext.save(SQLID+"insertHouseTopColumn", houseTopColumnEntity);
    }
    
    /**
     * 
     * 更新top房源条目
     *
     * @author bushujie
     * @created 2017年3月22日 下午7:36:07
     *
     * @param houseTopColumnEntity
     */
    public void updateHouseTopColumn(HouseTopColumnEntity houseTopColumnEntity){
    	mybatisDaoContext.update(SQLID+"updateHouseTopColumn", houseTopColumnEntity);
    }
    
    /**
     * 
     * 条目类型查询唯一条目
     *
     * @author bushujie
     * @created 2017年3月29日 下午6:16:33
     *
     * @param paramMap
     * @return
     */
    public HouseTopColumnEntity findHouseTopColumnByType(Map<String, Object> paramMap){
    	return mybatisDaoContext.findOne(SQLID+"findHouseTopColumnByType", HouseTopColumnEntity.class, paramMap);
    }
}
