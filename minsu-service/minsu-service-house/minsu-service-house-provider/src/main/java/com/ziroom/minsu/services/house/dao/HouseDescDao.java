package com.ziroom.minsu.services.house.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.services.house.dto.HouseDescDto;

/**
 * 
 * <p>房源描述信息dao</p>
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
@Repository("house.houseDescDao")
public class HouseDescDao {

    private String SQLID="house.houseDescDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源描述信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param houseDesc
     * @return
     */
    public int insertHouseDesc(HouseDescEntity houseDesc) {
		return mybatisDaoContext.save(SQLID+"insertHouseDesc", houseDesc);
	}
    
    /**
     * 
     * 更新房源描述信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param houseDesc
     * @return
     */
    public int updateHouseDesc(HouseDescEntity houseDesc) {
    	return mybatisDaoContext.update(SQLID+"updateHouseDescByFid", houseDesc);
    }
    
    /**
     * 
     * 根据房源fid删除房源描述
     *
     * @author jixd
     * @created 2016年6月13日 下午6:16:33
     *
     * @param houseFid
     * @return
     */
    public int deleteHouseDescByHouseFid(String houseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseDescByHouseFid", map);
	}
    
	/**
	 * 根据房源逻辑id查询房源描述及房源基础扩展信息
	 *
	 * @author liujun
	 * @created 2016年5月5日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseDescDto findHouseDescDtoByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOneSlave(SQLID + "findHouseDescDtoByHouseBaseFid", HouseDescDto.class,
				houseBaseFid);
	}
	
	/**
	 * 
	 * 查询房源描述信息，通过房源fid
	 *
	 * @author bushujie
	 * @created 2016年5月26日 下午8:53:43
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseDescEntity findHouseDescByHouseBaseFid(String houseBaseFid){
		return mybatisDaoContext.findOne(SQLID+"findHouseDescByHouseBaseFid", HouseDescEntity.class, houseBaseFid);
	}
	
	/**
	 * 
	 *  更新房源描述根据房源fid
	 *
	 * @author bushujie
	 * @created 2016年5月26日 下午9:28:28
	 *
	 * @param houseDescEntity
	 */
	public int updateHouseDescByHouseBaseFid(HouseDescEntity houseDescEntity){
		return mybatisDaoContext.update(SQLID+"updateHouseDescByHouseBaseFid", houseDescEntity);
	}
}