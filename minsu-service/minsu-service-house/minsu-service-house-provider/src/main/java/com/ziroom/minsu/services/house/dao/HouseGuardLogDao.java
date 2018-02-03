package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseGuardLogEntity;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;

/**
 * 
 * <p>房源维护管家关系日志dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseGuardLogDao")
public class HouseGuardLogDao {


    private String SQLID="house.houseGuardLogDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源维护管家关系日志记录
     *
     * @author liujun
     * @created 2016年7月5日
     *
     * @param houseDesc
     */
    public void insertHouseGuardLog(HouseGuardLogEntity houseGuardLog) {
		mybatisDaoContext.save(SQLID+"insertHouseGuardLog", houseGuardLog);
	}
	
	/**
	 * 
	 * 根据逻辑id查询房源维护管家关系日志记录
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	public HouseGuardLogEntity findHouseGuardLogByFid(String fid){
		return mybatisDaoContext.findOneSlave(SQLID+"findHouseGuardLogByFid", HouseGuardLogEntity.class, fid);
	}

	/**
	 * TODO
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseGuardDto
	 * @return
	 */
	public PagingResult<HouseGuardLogEntity> findHouseGuardLogForPage(HouseGuardDto houseGuardDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseGuardDto.getPage());
		pageBounds.setLimit(houseGuardDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findHouseGuardLogByHouseGuardFid", 
				HouseGuardLogEntity.class, houseGuardDto, pageBounds);
	}
}