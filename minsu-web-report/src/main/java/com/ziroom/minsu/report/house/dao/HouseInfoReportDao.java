package com.ziroom.minsu.report.house.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.house.dto.HouseInfoReportDto;
import com.ziroom.minsu.report.house.vo.HouseEfficVo;
import com.ziroom.minsu.report.house.vo.HouseInfoReportVo;
import com.ziroom.minsu.report.order.vo.HouseOrderInfoVo;

/**
 * 
 * <p>房子信息报表业务Dao</p>
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
@Repository("report.houseInfoReportDao")
public class HouseInfoReportDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseInfoReportDao.class);
	
	private String SQLID = "report.houseInfoReportDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
	 * 整租查询所有Hfid
	 */
	public PagingResult<HouseInfoReportVo> getEntireRentList(HouseInfoReportDto paramDto) {
		PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(paramDto.getLimit());
        pageBounds.setPage(paramDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"getEntireRentList", HouseInfoReportVo.class, paramDto.toMap(), pageBounds);
	}
	
	/**
	 * 分租查询所有roomfid
	 */
	public PagingResult<HouseInfoReportVo> getJoinRentList(HouseInfoReportDto paramDto) {
		PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(paramDto.getLimit());
        pageBounds.setPage(paramDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"getJoinRentList", HouseInfoReportVo.class, paramDto.toMap(), pageBounds);
	}
	
	/**
	 * 根据大区Code获取省份Code
	 */
    public List<String> getPCodeListByRCode(String regionCode) {
    	return mybatisDaoContext.findAll(SQLID+"getPCodeListByRCode", String.class, regionCode);
	}

    /**
	 * 方法目的：根据查询条件确定房源list之后，根据房源基础信息表中fid查找Vo中所有需要的字段
	 * 方法数量：此方法以后四个方法
	 * 本系列方法开始
	 */
	public HouseInfoReportVo getVoByFid(String houseBaseFid) {
		
		return mybatisDaoContext.findOne(SQLID+"getVoByFid", HouseInfoReportVo.class, houseBaseFid);
	}

	public HouseInfoReportVo getHextByHbmfid(String houseBaseFid) {
		
		return mybatisDaoContext.findOne(SQLID+"getHextByHbmfid", HouseInfoReportVo.class, houseBaseFid);
	}

	
	public HouseInfoReportVo getHbizMByHbmfid(String houseBaseFid) { 
		
		return mybatisDaoContext.findOne(SQLID+"getHbizMByHbmfid", HouseInfoReportVo.class, houseBaseFid);
	}
	
	public HouseInfoReportVo getHOLogByHbmfid(String houseBaseFid) {
		
		return mybatisDaoContext.findOne(SQLID+"getHOLogByHbmfid", HouseInfoReportVo.class, houseBaseFid);
	}

	public HouseInfoReportVo getHphyMByHbmfid(String houseBaseFid) {
		
		return mybatisDaoContext.findOne(SQLID+"getHphyMByHbmfid", HouseInfoReportVo.class, houseBaseFid);
	}
	
	/**
	 * 城市code==》城市名称
	 */
	public HouseInfoReportVo getFromConfCity(String cityCode) {
		return mybatisDaoContext.findOne(SQLID+"getFromConfCity", HouseInfoReportVo.class, cityCode);
	}
	
	/**
	 * 大区code==》大区名称
	 */
	public HouseInfoReportVo getFromCityRegionRel(String privinceCode) {
		return mybatisDaoContext.findOne(SQLID+"getFromCityRegionRel", HouseInfoReportVo.class, privinceCode);
	}
	/**
	 * 本系列方法结束
	 */

}
