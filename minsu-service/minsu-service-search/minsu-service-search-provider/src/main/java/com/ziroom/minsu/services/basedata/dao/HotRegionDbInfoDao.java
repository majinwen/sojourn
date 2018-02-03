package com.ziroom.minsu.services.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.services.common.constant.BaseConstant;
import com.ziroom.minsu.services.search.vo.HotRegionSimpleVo;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.valenum.city.CityRulesEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

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
 * @author afi
 * @since 1.0
 * @version 1.0
 */
@Repository("search.hotRegionDbInfoDao")
public class HotRegionDbInfoDao {


    private String SQLID="search.hotRegionDbInfoDao.";

    @Autowired
    @Qualifier("searchBase.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前区域的热门信息
     * @author afi
     * @param cityCode
     * @return
     */
    public List<HotRegionVo> getHotRegionList(String cityCode){
        if(Check.NuNStr(cityCode)){
           return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getHotRegionList", HotRegionVo.class, cityCode);
    }

    /**
     * 获取当前有效的景点商圈类型下的有效的景点商圈
     * @author zl
     * @param cityCode
     * @return
     */
	public List<HotRegionVo> getHotRegionListByCityEnumStatus( String cityCode) {
		  
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dicCode", CityRulesEnum.CityRulesEnum002.getValue());
		paramMap.put("templateFid", BaseConstant.BASE_TEMPLATE); 
		paramMap.put("cityCode", cityCode); 
		return mybatisDaoContext.findAll(SQLID + "getHotRegionListByCityEnumStatus", HotRegionVo.class, paramMap);
	}


    /**
     * 获取当前区域的拼音统计信息
     * @author afi
     * @return
     */
    public List<HotRegionSimpleVo> getHotRegionSimpleList(){

        return mybatisDaoContext.findAll(SQLID + "getHotRegionSimpleList", HotRegionVo.class);
    }



    /**
     * 获取当前城市的地铁
     * @author afi
     * @param cityCode
     * @return
     */
    public List<HotRegionVo> getSubWayList(String cityCode){
        if(Check.NuNStr(cityCode)){
            throw new BusinessException("cityCode is null on getSubwaySimpleList");
        }
        return mybatisDaoContext.findAll(SQLID + "getSubWayList", HotRegionVo.class, cityCode);
    }


    /**
     * 获取当前地铁的拼音统计信息
     * @author afi
     * @return
     */
    public List<HotRegionSimpleVo> getSubwaySimpleList(){

        return mybatisDaoContext.findAll(SQLID + "getSubwaySimpleList", HotRegionVo.class);
    }



    /**
     * 获取城市的拼音统计信息
     * @author afi
     * @return
     */
    public List<HotRegionSimpleVo> getAreaSimpleList(){

        return mybatisDaoContext.findAll(SQLID + "getAreaSimpleList", HotRegionVo.class);
    }

    /**
     * 获取有效半径
     * @return
     */
    public Integer getKm(){

        return mybatisDaoContext.findOneSlave(SQLID + "getKm", Integer.class);
    }
    
    
    /**
     * 根据城市获取对应的区域 
     * @author lishaochuan
     * @create 2016年8月23日下午3:25:50
     * @param cityCode
     * @return
     */
    public List<HotRegionSimpleVo> getAreaByCity(String cityCode){
        return mybatisDaoContext.findAll(SQLID + "getAreaByCity", HotRegionSimpleVo.class, cityCode);
    }
    
    
    /**
     * 按照有效的枚举查询商圈、景点。。。
     * @param dicCode
     * @param templateFid
     * @param cityCode
     * @param regionType
     * @return
     */
	public List<HotRegionVo> getHotRegionListByEffectiveEnum( String dicCode,String templateFid,String cityCode,String regionType ) {
		if(Check.NuNStr(dicCode)){
            return null;
        }
        if (Check.NuNStr(templateFid)){
            templateFid = BaseConstant.BASE_TEMPLATE;
        }
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dicCode", dicCode);
		paramMap.put("templateFid", templateFid); 
		paramMap.put("itemStatus", YesOrNoEnum.YES.getCode());
		paramMap.put("cityCode", cityCode); 
		paramMap.put("regionType", regionType);
		return mybatisDaoContext.findAll(SQLID + "getHotRegionListByEnum", HotRegionVo.class, paramMap);
	}
	
	   
    /**
     * 按照有效的枚举查询有效的商圈、景点等类型下有效的景点商圈
     * @author zl
     * @param dicCode
     * @param templateFid
     * @param cityCode
     * @param regionType
     * @return
     */
	public List<HotRegionVo> getHotRegionListByEffectiveEnumStatus( String dicCode,String templateFid,String cityCode,String regionType ) {
		if(Check.NuNStr(dicCode)){
            return null;
        }
        if (Check.NuNStr(templateFid)){
            templateFid = BaseConstant.BASE_TEMPLATE;
        }
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dicCode", dicCode);
		paramMap.put("templateFid", templateFid); 
		paramMap.put("itemStatus", YesOrNoEnum.YES.getCode());
		paramMap.put("cityCode", cityCode); 
		paramMap.put("regionType", regionType);
		paramMap.put("regionStatus", YesOrNoEnum.YES.getCode());
		return mybatisDaoContext.findAll(SQLID + "getHotRegionListByEnum", HotRegionVo.class, paramMap);
	}

    
    
    
}