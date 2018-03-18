package com.ziroom.minsu.report.house.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.house.dto.HouseEfficDto;
import com.ziroom.minsu.report.house.dto.HouseViscosityDto;
import com.ziroom.minsu.report.house.vo.HouseEfficVo;
import com.ziroom.minsu.report.house.vo.HouseViscosityVo;
/**
 * 
 * <p>房源粘性报表——dao层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */
@Repository("report.houseViscosityDao")
public class HouseViscosityDao {


	private String SQLID = "report.houseViscosityDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 查询所有符合的（整租）Hfid
     *
     * @author ls
     * @created 2017年5月8日 上午10:08:59
     *
     * @param paramDto
     * @return
     */
    public PagingResult<HouseViscosityVo> getEntireRentList(HouseViscosityDto paramDto) {
		PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(paramDto.getLimit());
        pageBounds.setPage(paramDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"getEntireRentList", HouseViscosityVo.class, paramDto.toMap(), pageBounds);
	}
    
    /**
     * 
     * 查询所有符合的（分租）Rfid
     *
     * @author ls
     * @created 2017年5月8日 上午10:08:59
     *
     * @param paramDto
     * @return
     */
    public PagingResult<HouseViscosityVo> getJoinRentList(HouseViscosityDto paramDto) {
		PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(paramDto.getLimit());
        pageBounds.setPage(paramDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"getJoinRentList", HouseViscosityVo.class, paramDto.toMap(), pageBounds);
	}
    
  
    /**
	 *  根据大区Code获取省份Code
	 */
    public List<String> getPCodeListByRCode(String regionCode) {
    	return mybatisDaoContext.findAll(SQLID+"getPCodeListByRCode", String.class, regionCode);
	}
    
    /**
	 *  省份code==》大区名称
	 */
    public HouseViscosityVo getFromCityRegionRel(String provinceCode) {
		return mybatisDaoContext.findOne(SQLID+"getFromCityRegionRel", HouseViscosityVo.class, provinceCode);
	}
    
    /**
	 *  城市Code获取==》城市名称
	 */
    public HouseViscosityVo getFromConfCity(String cityCode) {
		return mybatisDaoContext.findOne(SQLID+"getFromConfCity", HouseViscosityVo.class, cityCode);
	}

    /**
	 *  查询累计浏览量——房源
	 */
	public HouseViscosityVo getCumulViewVoByHfid(String houseBaseFid) {
		return mybatisDaoContext.findOne(SQLID+"getCumulViewVoByHfid", HouseViscosityVo.class, houseBaseFid);
	}
	/**
	 *  查询房源累计浏览量——房间
	 */
	public HouseViscosityVo getCumulViewVoByRfid(String roomFid) {
		return mybatisDaoContext.findOne(SQLID+"getCumulViewVoByRfid", HouseViscosityVo.class, roomFid);
	}

	/**
	 *  累计咨询量
	 */
	public HouseViscosityVo getCumulAdviceVoByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulAdviceVoByHfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 *  累计申请量——整租   cumulApplyNum     所有订单    无论订单状态    无论下单类型 
	 */
	public HouseViscosityVo getCumulApplyVoByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulApplyVoByHfid", HouseViscosityVo.class, paramDto);
	}
	/**
	 *  累计申请量——分租   cumulApplyNum     所有订单    无论订单状态    无论下单类型
	 */
	public HouseViscosityVo getCumulApplyVoByRfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulApplyVoByRfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 *  累计接单量——整租
	 */
	public HouseViscosityVo getCumulGetOrderVoByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulGetOrderVoByHfid", HouseViscosityVo.class, paramDto);
	}
	/**
	 *  累计接单量——分租
	 */
	public HouseViscosityVo getCumulGetOrderVoByRfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulGetOrderVoByRfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 *  累计订单量——暂时用不到，  可用getReserveJYByHfid和getReserveJYByRfid获取额list.size()获取
	 */
	public HouseViscosityVo getCumulOrderVoByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulOrderVoByHfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 *  累计预定间夜量——整租 
	 */
	public List<HouseViscosityVo> getReserveJYByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findAll(SQLID+"getReserveJYByHfid", HouseViscosityVo.class, paramDto);
	}
	/**
	 *  累计预定间夜量 ——分租
	 */
	public List<HouseViscosityVo> getReserveJYByRfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findAll(SQLID+"getReserveJYByRfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 *  累计入住订单量——整租
	 */
	public HouseViscosityVo getCumulCheckInByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulCheckInByHfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 *  累计入住间夜量——分租
	 */
	public List<HouseViscosityVo> getCheckInListByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findAll(SQLID+"getCheckInListByHfid", HouseViscosityVo.class, paramDto);
	}
	
	/**
	 *  累计入住间夜量——暂时不用，可用getShieldJYListByHfid和getShieldJYListByRfid查询的list.size()获取
	 */
	public List<HouseViscosityVo> getCheckInListByRfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findAll(SQLID+"getCheckInListByRfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 * 累计屏蔽间夜量——整租
	 */
	public HouseViscosityVo getShieldJYByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getShieldJYByHfid", HouseViscosityVo.class, paramDto);
	}
	/**
	 * 累计屏蔽间夜量——整租
	 */
	public HouseViscosityVo getShieldJYByRfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getShieldJYByRfid", HouseViscosityVo.class, paramDto);
	}
	
	/**
	 * 累计收益——整租
	 */
	public HouseViscosityVo getCumulProfitByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulProfitByHfid", HouseViscosityVo.class, paramDto);
	}
	/**
	 * 累计收益——分租
	 */
	public HouseViscosityVo getCumulProfitByRfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulProfitByRfid", HouseViscosityVo.class, paramDto);
	}
	
	/**
	 *  累计收到评价量——整租
	 */
	public HouseViscosityVo getCumulGetEvalByHfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulGetEvalByHfid", HouseViscosityVo.class, paramDto);
	}
	/**
	 *  累计收到评价量——分租
	 */
	public HouseViscosityVo getCumulGetEvalByRfid(HouseViscosityDto paramDto) {
		return mybatisDaoContext.findOne(SQLID+"getCumulGetEvalByRfid", HouseViscosityVo.class, paramDto);
	}

	/**
	 * 房源粘性报表（整租）——一条sql解决，已经校验可用;
	 *
	 * @author ls
	 * @created 2017年5月5日 下午6:07:17
	 *
	 * @param paramDto
	 * @return
	 */
	public PagingResult<HouseViscosityVo> getAllVoByParam(
			HouseViscosityDto paramDto) {
		PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(paramDto.getLimit());
        pageBounds.setPage(paramDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"getAllVoByParam", HouseViscosityVo.class, paramDto, pageBounds);
	}

	
	

}
