package com.ziroom.minsu.services.house.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.services.house.dto.HousePhyListDto;

/**
 * 
 * <p>房源物理信息dao</p>
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
@Repository("house.housePhyMsgDao")
public class HousePhyMsgDao {


    private String SQLID="house.housePhyMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 新增房源物理信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param housePhyMsg
     * @return
     */
    public void insertHousePhyMsg(HousePhyMsgEntity housePhyMsg) {
		mybatisDaoContext.save(SQLID+"insertHousePhyMsg", housePhyMsg);
	}
    
    /**
     * 
     * 更新房源物理信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param housePhyMsg
     * @return
     */
    public int updateHousePhyMsg(HousePhyMsgEntity housePhyMsg) {
    	return mybatisDaoContext.update(SQLID+"updateHousePhyMsgByFid", housePhyMsg);
    }

	/**
	 * 根据小区名称查询房源物理信息
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午12:13:37
	 *
	 * @param communityName
	 * @param cityCode
	 * @return 
	 */
	public HousePhyMsgEntity findHousePhyMsgByCommuNameAndCityCode(String communityName, String cityCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("communityName", communityName);
		map.put("cityCode", cityCode);
		return mybatisDaoContext.findOne(SQLID + "findHousePhyMsgByCommuNameAndCityCode", HousePhyMsgEntity.class, map);
	}
	
	/**
	 * 
	 * 条件查询楼盘信息列表
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午1:50:33
	 *
	 * @param housePhyMsgEntity
	 * @return
	 */
	public PagingResult<HousePhyMsgEntity> findHousePhyMsgListByCondition(HousePhyListDto housePhyListDto){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(housePhyListDto.getLimit());
		pageBounds.setPage(housePhyListDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findHousePhyMsgListByCondition", HousePhyMsgEntity.class, housePhyListDto,pageBounds);
	}

	/**
	 * 根据房源基础逻辑id查询房源物理信息
	 *
	 * @author liujun
	 * @created 2016年4月29日 下午7:04:27
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePhyMsgEntity findHousePhyMsgByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOneSlave(SQLID+"findHousePhyMsgByHouseBaseFid", HousePhyMsgEntity.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 特殊修改，小区名称可以为空
	 *
	 * @author bushujie
	 * @created 2017年7月17日 上午11:21:27
	 *
	 * @param housePhyMsg
	 * @return
	 */
    public int specialUpdateHousePhyMsgByFid(HousePhyMsgEntity housePhyMsg) {
    	return mybatisDaoContext.update(SQLID+"specialUpdateHousePhyMsgByFid", housePhyMsg);
    }
}