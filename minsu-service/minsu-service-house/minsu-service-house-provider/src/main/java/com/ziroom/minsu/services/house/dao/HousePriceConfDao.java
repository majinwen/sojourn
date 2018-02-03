package com.ziroom.minsu.services.house.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HousePriceConfEntity;
import com.ziroom.minsu.services.house.dto.HousePriceConfDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;

/**
 * 
 * <p>房源价格配置dao</p>
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
@Repository("house.housePriceConfDao")
public class HousePriceConfDao {


    private String SQLID="house.housePriceConfDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增价格配置信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param housePriceConf
     * @return
     */
    public void insertHousePriceConf(HousePriceConfEntity housePriceConf) {
		mybatisDaoContext.save(SQLID+"insertHousePriceConf", housePriceConf);
	}
    
    /**
     * 
     * 根据房间fid该房间逻辑删除价格配置
     *
     * @author jixd
     * @created 2016年6月13日 下午5:53:02
     *
     * @param houseFid
     * @return
     */
    public int deleteHousePriceConfByRoomFid(String roomFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", roomFid);
		return mybatisDaoContext.update(SQLID + "deleteHousePriceConfByRoomFid", map);
	}
    /**
     * 
     * 根据房源fid逻辑删除房源价格配置
     *
     * @author jixd
     * @created 2016年6月13日 下午5:53:32
     *
     * @param houseFid
     * @return
     */
    public int deleteHousePriceConfByHouseFid(String houseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseFid);
		return mybatisDaoContext.update(SQLID + "deleteHousePriceConfByHouseFid", map);
	}
    
    /**
     * 
     * 更新价格配置信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param housePriceConf
     * @return
     */
    public int updateHousePriceConf(HousePriceConfEntity housePriceConf) {
    	return mybatisDaoContext.update(SQLID+"updateHousePriceConfByFid", housePriceConf);
    }
    
    /**
     * 
     * 查询特殊价格日期列表
     *
     * @author bushujie
     * @created 2016年4月3日 下午11:35:35
     *
     * @param paramMap
     * @return
     */
    public List<SpecialPriceVo> findSpecialPriceList(LeaseCalendarDto leaseCalendarDto){
    	return mybatisDaoContext.findAll(SQLID+"findSpecialPrice", SpecialPriceVo.class, leaseCalendarDto);
    }
    
    /**
     * 
     * 查询日期特殊价格是否存在
     *
     * @author bushujie
     * @created 2016年4月5日 下午5:49:28
     *
     * @param housePriceConfDto
     * @return
     */
    public HousePriceConfEntity findHousePriceConfByDate(HousePriceConfDto housePriceConfDto){
    	return mybatisDaoContext.findOne(SQLID+"findHousePriceConfByDate", HousePriceConfEntity.class, housePriceConfDto);
    }
    
    /**
     * 
     * 根据fid删除特殊价格
     *
     * @author zl
     * @created 2016年9月24日
     *
     * @param fid
     * @return
     */
    public int deleteHousePriceConfByFid(String fid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", fid);
		return mybatisDaoContext.update(SQLID + "deleteHousePriceConfByFid", map);
	}
    
    /**
	 * 查询房东最后一次修改日历时间
	 * @author zl
	 * @param paramJson
	 * @return
	 */
    public Date getLastModifyCalendarDate(String landlordUid) {
    	if (Check.NuNStr( landlordUid)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("landlordUid", landlordUid);
		return mybatisDaoContext.findOne(SQLID + "getLastModifyCalendarDate",Date.class, landlordUid);
	}
    
}