package com.ziroom.minsu.services.house.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.dto.CreatIndexRequest;
import com.ziroom.minsu.services.search.dto.HouseCurrentStatsDto;
import com.ziroom.minsu.services.search.vo.BedNumVo;
import com.ziroom.minsu.services.search.vo.CommunityInfo;
import com.ziroom.minsu.services.search.vo.ConfigVo;
import com.ziroom.minsu.services.search.vo.HouseDbInfoVo;
import com.ziroom.minsu.services.search.vo.HousePicVo;
import com.ziroom.minsu.services.search.vo.HousePriceVo;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;

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
@Repository("search.houseDbInfoDao")
public class HouseDbInfoDao {


    private String SQLID="search.houseDbInfoDao.";

    @Autowired
    @Qualifier("searchHouse.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前房源的审核通过时间
     * @param houseFid
     * @return
     */
    public Date getHousePassDate(String houseFid){
        Map<String,Object> par = new HashMap<>();
        par.put("houseFid",houseFid);
        return mybatisDaoContext.findOneSlave(SQLID + "getHousePassDate", Date.class, houseFid);
    }

    /**
     * 获取当前的审核通过时间
     * @param roomFid
     * @return
     */
    public Date getRoomPassDate(String roomFid){
        Map<String,Object> par = new HashMap<>();
        par.put("roomFid",roomFid);
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomPassDate",Date.class,roomFid);
    }



    /**
     * 获取房源的配置属性
     * @param houseFid
     * @return
     */
    public List<ConfigVo> getHouseConfig(String houseFid){
        return mybatisDaoContext.findAll(SQLID + "getHouseConfig", ConfigVo.class, houseFid);
    }


    /**
     * 获取当前的房源的价格信息
     * @author afi
     * @param houseFid
     * @return
     */
    public List<HousePriceVo>  getHousePrices(String houseFid){
        if(Check.NuNStr(houseFid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID +"getHousePrices",HousePriceVo.class,houseFid);
    }


    /**
     * 获取当前的房源的价格信息
     * @author afi
     * @param houseFid
     * @return
     */
    public List<HousePriceVo>  getHouseWeekPrices(String houseFid){
        if(Check.NuNStr(houseFid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID +"getHouseWeekPrices",HousePriceVo.class,houseFid);
    }


    /**
     * 获取当前的房间的week价格信息
     * @author afi
     * @param roomFid
     * @return
     */
    public List<HousePriceVo>  getRoomWeekPrices(String roomFid){
        if(Check.NuNStr(roomFid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID +"getRoomWeekPrices",HousePriceVo.class,roomFid);
    }

    /**
     * 获取当前的房间的价格信息
     * @author afi
     * @param roomFid
     * @return
     */
    public List<HousePriceVo>  getRoomPrices(String roomFid){
        if(Check.NuNStr(roomFid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID +"getRoomPrices",HousePriceVo.class,roomFid);
    }

    /**
     * 获取房间的图片信息
     * @param listIds
     * @return
     */
    public void testtest(List<String> listIds){

        Map<String,Object> par = new HashMap<>();
        par.put("listIds",listIds);
         mybatisDaoContext.findAll(SQLID + "testtest", HousePicVo.class, par);
    }


    /**
     * 获取房源的图片信息
     * @param houseFid
     * @return
     */
    public HousePicVo getHousePicInfo(String houseFid){
        return mybatisDaoContext.findOneSlave(SQLID + "getHousePicInfo", HousePicVo.class, houseFid);
    }

    /**
     * 获取房间的图片信息
     * @param roomFid
     * @return
     */
    public HousePicVo getRoomPicInfo(String roomFid){
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomPicInfo", HousePicVo.class, roomFid);
    }


    /**
     * 获取房间的图片信息
     * @param fid
     * @return
     */
    public HousePicVo getPicByFid(String fid){
        if (Check.NuNStr(fid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getPicByFid", HousePicVo.class, fid);
    }


    /**
     * 获取图片信息 强制不管状态
     * @param fid
     * @return
     */
    public HousePicVo getPicByFidForce(String fid){
        if (Check.NuNStr(fid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getPicByFidForce", HousePicVo.class, fid);
    }





    /**
     * 分页获取房源信息
     * @author afi
     * @param pageRequest
     * @return
     */
    public PagingResult<HouseDbInfoVo> getHouseDbInfoForPage(CreatIndexRequest pageRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(pageRequest.getLimit());
        pageBounds.setPage(pageRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getHouseDbInfoForPage", HouseDbInfoVo.class, pageRequest, pageBounds);
    }


    /**
     * 获取审核通过的房源物理信息
     * @param pageRequest
     * @return
     */
    public PagingResult<CommunityInfo>  getCommunityInfoForPage(PageRequest pageRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(pageRequest.getLimit());
        pageBounds.setPage(pageRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getCommunityInfo",CommunityInfo.class,pageRequest, pageBounds);
    }




    /**
     * 获取房源的信息
     * @author afi
     * @param houseFid
     * @return
     */
    public List<HouseDbInfoVo> getHouseDbInfoByHouseFid(String houseFid){
        if(Check.NuNStr(houseFid)){
            throw new BusinessException("houseFid is null on getHouseDbInfoByHouseFid");
        }
        return mybatisDaoContext.findAll(SQLID + "getHouseDbInfoByHouseFid", HouseDbInfoVo.class, houseFid);
    }


    /**
     * 获取houseFid下的所有的兄弟房间
     * @param houseFid
     * @return
     */
    public List<String> getRoomsByHouseFid(String houseFid){
        return mybatisDaoContext.findAll(SQLID + "getRoomsByHouseFid", String.class, houseFid);
    }



    /**
     * 获取houseFid下所有配置服务
     * @param houseFid
     * @return
     */
    public List<String> getHouseServices(String houseFid){
        return mybatisDaoContext.findAll(SQLID + "getHouseServices", String.class, houseFid);
    }

    /**
     * 获取roomFid下所有配置服务
     * @param roomFid
     * @return
     */
    public List<String> getRoomServices(String roomFid){
        return mybatisDaoContext.findAll(SQLID + "getRoomServices", String.class, roomFid);
    }



    public Integer getHouseStatusByHouseFid(String houseFid){
        return mybatisDaoContext.findOneSlave(SQLID + "getHouseStatusByHouseFid", Integer.class, houseFid);
    }

    /**
     * 获取当前的房源的浏览量
     * @param houseFid
     * @return
     */
    public Integer getHousePv(String houseFid){
        return mybatisDaoContext.findOneSlave(SQLID + "getHousePv", Integer.class, houseFid);
    }


    /**
     * 获取当前的房源的浏览量
     * @param roomFid
     * @return
     */
    public Integer getRoomPv(String roomFid){
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomPv", Integer.class, roomFid);
    }


    /**
     * 当前的订单总数
     * @author afi
     * @return
     */
    public Long countPvAll(){
        return mybatisDaoContext.countBySlave(SQLID + "countPvAll");
    }

    /**
     * 获取当前的房源的浏览量
     * @param housePv
     * @return
     */
    public Long countPvByPv(Integer housePv){
        if (Check.NuNObj(housePv)){
            return 0L;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("housePv",housePv);
        return mybatisDaoContext.countBySlave(SQLID + "countPvByPv",params);
    }


    /**
     * 获取当前的房源的
     * @author afi
     * @param houseFid
     * @return
     */
    public List<BedNumVo> getBedNumByHouseFid(String houseFid){
        return mybatisDaoContext.findAll(SQLID+"getBedNumByHouseFid", BedNumVo.class, houseFid);
    }

    /**
     * 获取当前的房源的
     * @author afi
     * @param roomFid
     * @return
     */
    public List<BedNumVo> getBedNumByRoomFid(String roomFid){
        return mybatisDaoContext.findAll(SQLID+"getBedNumByRoomFid", BedNumVo.class, roomFid);
    }
    
    /**
     * 查询城市下已经上线的房源类型
     * @param cityCode
     * @return
     */
    public List<Integer> getHouseTypeByCityCode(String cityCode){
    	if (Check.NuNStr(cityCode)) {
			return null;
		}
    	return mybatisDaoContext.findAll(SQLID+"getHouseTypeByCityCode", Integer.class, cityCode);
    }
    
    /**
     * 獲取當前房源、房間的熱度
     * TODO
     *
     * @author zl
     * @created 2016年12月2日 下午3:57:59
     *
     * @param dto
     * @return
     */
    public Integer getHouseCurrentHot(HouseCurrentStatsDto dto){
    	if (Check.NuNStr(dto.getHouseFid()) || Check.NuNObj(dto.getRentWay()) || Check.NuNObj(dto.getDays()) || Check.NuNObj(dto.getConsultNumWeight())|| Check.NuNObj(dto.getTradeNumWeight())) {
			return 0;
		} 
    	List<Integer> list= mybatisDaoContext.findAll(SQLID+"getHouseCurrentHot", Integer.class,dto);
    	if (!Check.NuNCollection(list)) {
			return list.get(0);
		}
		
    	return 0;
    }
    
    /**
     * 獲取當前已結統計的城市的熱度
     * TODO
     *
     * @author zl
     * @created 2016年12月2日 下午3:59:20
     *
     * @param dto
     * @return
     */
    public Map<String,Integer> getAllCityCurrentHot(HouseCurrentStatsDto dto){
    	if (Check.NuNObj(dto.getDays()) || Check.NuNObj(dto.getConsultNumWeight())|| Check.NuNObj(dto.getTradeNumWeight())) {
			return null;
		}
    	
    	List<Map> list = mybatisDaoContext.findAll(SQLID+"getAllCityCurrentHot", Map.class, dto);
    	if (!Check.NuNCollection(list)) {
			Map<String, Integer> result = new HashMap<>();
			for (Map map : list) {
				result.put(ValueUtil.getStrValue(map.get("cityCode")),ValueUtil.getintValue(map.get("hot")));
			}
			return result;
		}
    	
    	return null;
    }
    
    /**
     * 獲取靈活定價配置
     * 結果格式：ProductRulesEnum002001,1
     * TODO
     *
     * @author zl
     * @created 2016年12月5日 下午4:25:44
     *
     * @param houseBaseFid
     * @param roomFid
     * @return
     */
    public List<String> getFlexiblePriceConf(String houseBaseFid,String roomFid){
    	if (Check.NuNStr(houseBaseFid) && Check.NuNStr(roomFid)) {
			return null;
		}
    	Map<String, String> params = new HashMap<>();
    	params.put("houseBaseFid", houseBaseFid);
    	params.put("roomFid", roomFid);
    	params.put("dicCodeLike", ProductRulesEnum020.ProductRulesEnum020001.getParentValue());
    	
    	return mybatisDaoContext.findAll(SQLID+"getFlexiblePriceORLongTermLeaseDiscountConf", String.class, params);
    } 
    
    /**
     * 
     * 查询设置的今日特惠设置
     *
     * @author zl
     * @created 2017年5月10日 下午8:13:52
     *
     * @param houseBaseFid
     * @param roomFid
     * @param onlyToday
     * @return
     */
    public List<TonightDiscountEntity> getTonightDiscountList(String houseBaseFid,String roomFid,Boolean onlyToday ){
    	if (Check.NuNStr(houseBaseFid) && Check.NuNStr(roomFid)) {
			return null;
		}
    	Map<String, String> params = new HashMap<>();
    	params.put("houseBaseFid", houseBaseFid);
    	params.put("roomFid", roomFid);
    	if (onlyToday !=null && onlyToday) {
    		params.put("now",DateUtil.dateFormat(new Date()));
		}
    	
    	return mybatisDaoContext.findAll(SQLID+"getTonightDiscountList", TonightDiscountEntity.class, params);
    }
    
    
    
    
    
    /**
     * 獲取者長租折扣配置
     * 結果格式：ProductRulesEnum002001,1
     * TODO
     *
     * @author zl
     * @created 2016年12月5日 下午4:26:03
     *
     * @param houseBaseFid
     * @param roomFid
     * @return
     */
    public List<String> getLongTermLeaseDiscountConf(String houseBaseFid,String roomFid){
    	if (Check.NuNStr(houseBaseFid) && Check.NuNStr(roomFid)) {
			return null;
		}
    	Map<String, String> params = new HashMap<>();
    	params.put("houseBaseFid", houseBaseFid);
    	params.put("roomFid", roomFid);
    	params.put("dicCodeLike", ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
    	return mybatisDaoContext.findAll(SQLID+"getFlexiblePriceORLongTermLeaseDiscountConf", String.class, params);
    }
    
    
    /**
     * 
     * 获取房源标签
     *
     * @author zl
     * @created 2017年4月1日 上午9:31:39
     *
     * @param houseBaseFid
     * @param roomFid
     * @return
     */
    public List<String> getHouseTagFids (String houseBaseFid,String roomFid){
    	if (Check.NuNStr(houseBaseFid) && Check.NuNStr(roomFid)) {
			return null;
		}
    	Map<String, String> params = new HashMap<>();
    	params.put("houseBaseFid", houseBaseFid);
    	params.put("roomFid", roomFid); 
    	return mybatisDaoContext.findAll(SQLID+"getHouseTagFids", String.class, params);
    }
    
    /**
     * 
     * 获取房源或者房间fid
     *
     * @author zl
     * @created 2017年4月10日 上午9:34:04
     *
     * @param houseBaseFid
     * @param roomFid
     * @param rentWay
     * @return
     */
    public Integer getHouseOrRoomStatus(String houseBaseFid,String roomFid,int rentWay){
    	Map<String, Object> params = new HashMap<>();
    	params.put("houseBaseFid", houseBaseFid);
    	params.put("roomFid", roomFid); 
    	params.put("rentWay", rentWay); 
        return mybatisDaoContext.findOneSlave(SQLID + "getHouseOrRoomStatus", Integer.class, params);
    }
    
    /**
     * 
     * top50 文章标题
     *
     * @author zl
     * @created 2017年4月10日 上午11:37:09
     *
     * @param houseBaseFid
     * @param roomFid
     * @param rentWay
     * @return
     */
    public String getTop50ArticleTitle(String houseBaseFid,String roomFid,int rentWay){
    	Map<String, Object> params = new HashMap<>();
    	params.put("houseBaseFid", houseBaseFid);
    	params.put("roomFid", roomFid); 
    	params.put("rentWay", rentWay); 
        return mybatisDaoContext.findOneSlave(SQLID + "getTop50ArticleTitle", String.class, params);
    }
    
    /**
     * 
     * 查询房源的序号
     *
     * @author zl
     * @created 2017年4月10日 下午2:49:53
     *
     * @param landlordUid
     * @return
     */
    public Map<String,Integer> getHouseIndexbyLand(String landlordUid){
    	
    	List<Map> list = mybatisDaoContext.findAll(SQLID+"getHouseIndexbyLand", Map.class, landlordUid);
    	if (!Check.NuNCollection(list)) {
			Map<String, Integer> result = new HashMap<>();
			for (Map map : list) {
				result.put(ValueUtil.getStrValue(map.get("fid")),new Double(ValueUtil.getdoubleValue(map.get("idx"))).intValue());
			}
			return result;
		}
    	
    	return null;
    }
    
    /**
     * 
     * 查询房间序号
     *
     * @author zl
     * @created 2017年4月10日 下午3:24:14
     *
     * @param houseBaseFid
     * @return
     */
    public Map<String,Integer> getRoomIndexbyHouse(String houseBaseFid){
    	
    	List<Map> list = mybatisDaoContext.findAll(SQLID+"getRoomIndexbyHouse", Map.class, houseBaseFid);
    	if (!Check.NuNCollection(list)) {
			Map<String, Integer> result = new HashMap<>();
			for (Map map : list) {
				result.put(ValueUtil.getStrValue(map.get("fid")),new Double(ValueUtil.getdoubleValue(map.get("idx"))).intValue());
			}
			return result;
		}
    	
    	return null;
    }
    

    
}