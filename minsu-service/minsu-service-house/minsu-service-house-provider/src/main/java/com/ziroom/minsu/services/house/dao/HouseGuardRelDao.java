package com.ziroom.minsu.services.house.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.entity.HouseGuardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * <p>房源维护管家关系dao</p>
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
@Repository("house.houseGuardRelDao")
public class HouseGuardRelDao {


    private String SQLID="house.houseGuardRelDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源维护管家关系
     *
     * @author liujun
     * @created 2016年7月5日
     *
     * @param houseDesc
     */
    public void insertHouseGuardRel(HouseGuardRelEntity houseGuardRel) {
		mybatisDaoContext.save(SQLID+"insertHouseGuardRel", houseGuardRel);
	}
	
	/**
	 * 
	 * 根据逻辑id查询房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	public HouseGuardRelEntity findHouseGuardRelByFid(String fid){
		return mybatisDaoContext.findOneSlave(SQLID+"findHouseGuardRelByFid", HouseGuardRelEntity.class, fid);
	}
	
	/**
	 * 
	 * 根据房源逻辑id查询房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseGuardRelEntity findHouseGuardRelByHouseBaseFid(String houseBaseFid){
		return mybatisDaoContext.findOneSlave(SQLID+"findHouseGuardRelByHouseBaseFid", HouseGuardRelEntity.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 分页查询房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	public PagingResult<HouseGuardVo> findHouseGuardVoForPage(HouseGuardDto houseGuardDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseGuardDto.getPage());
		pageBounds.setLimit(houseGuardDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findHouseGuardVoForPage", 
				HouseGuardVo.class, houseGuardDto.toMap(), pageBounds);
	}
	
	/**
	 * 
	 * 根据逻辑id更新房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	public int updateHouseGuardRelByFid(HouseGuardRelEntity houseGuardRel){
		return mybatisDaoContext.update(SQLID+"updateHouseGuardRelByFid", houseGuardRel);
	}
	
	/**
	 * 
	 * 根据房源逻辑id更新房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param fid
	 * @return
	 */
	public int updateHouseGuardRelByHouseFid(HouseGuardRelEntity houseGuardRel){
		return mybatisDaoContext.update(SQLID+"updateHouseGuardRelByHouseFid", houseGuardRel);
	}

	/**
	 * 根据房源逻辑id查询房源管家关系详情
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseGuardVo findHouseGuardVoByHouseBaseFid(String houseBaseFid) {
		return mybatisDaoContext.findOneSlave(SQLID + "findHouseGuardVoByHouseBaseFid", HouseGuardVo.class, houseBaseFid);
	}
	
	/**
	 * 
	 * 条件查询 房源和维护管家  内联
	 *
	 * @author yd
	 * @created 2016年7月12日 下午7:51:39
	 *
	 * @param houseGuardDto
	 * @return
	 */
	public List<HouseGuardVo> findHouseGuardByCondition(HouseGuardDto houseGuardDto){
		return mybatisDaoContext.findAll(SQLID+"findHouseGuardByCondition", HouseGuardVo.class, houseGuardDto.toMap());
	}


	/**
	 * 根据管家姓名查询，房源维护管家关联表
	 * @author lisc
	 * @param houseGuardRel
	 * @return
     */
	public List<HouseGuardRelEntity> findHouseGuardRelByCondition(HouseGuardRelEntity houseGuardRel){
		if(Check.NuNObj(houseGuardRel)){
			throw new BusinessException("参数为空");
		}
		if(Check.NuNStr(houseGuardRel.getEmpGuardName()) && Check.NuNStr(houseGuardRel.getEmpPushName())){
			throw new BusinessException("地推管家、维护管家，必须有一个有值");
		}
		return mybatisDaoContext.findAll(SQLID+"findHouseGuardRelByCondition", HouseGuardRelEntity.class, houseGuardRel.toMap());
	}
	
	/**
	 * 
	 * 特殊权限查询房源管家关系列表
	 *
	 * @author bushujie
	 * @created 2016年10月27日 下午3:54:35
	 *
	 * @param houseGuardDto
	 * @return
	 */
	public PagingResult<HouseGuardVo> findSpecialHouseGuardVoForPage(HouseGuardDto houseGuardDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(houseGuardDto.getPage());
		pageBounds.setLimit(houseGuardDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"findSpecialHouseGuardVoForPage", 
				HouseGuardVo.class, houseGuardDto.toMap(), pageBounds);
	}
}