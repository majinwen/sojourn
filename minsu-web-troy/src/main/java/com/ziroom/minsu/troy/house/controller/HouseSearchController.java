/**
 * @FileName: HouseSearchController.java
 * @Package com.ziroom.minsu.troy.house.controller
 * 
 * @author zl
 * @created 2017年5月25日 下午5:12:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.entity.CustomerSearchVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.HouseSearchOneRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.vo.HouseInfoListForTroyVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.constant.Constant;
import com.ziroom.minsu.troy.message.service.TroyMsgFirstAdvisoryService;
import com.ziroom.minsu.troy.message.vo.FirstAdvisoryFollowVO;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.SearchSourceTypeEnum;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */

@Controller
@RequestMapping("house/houseSearch")
public class HouseSearchController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseSearchController.class);
	
    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;
    
    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;
    
	@Value("#{'${pic_size}'.trim()}")
	private String defaultPicSize;
	
	@Value("#{'${pic_size}'.trim()}")
	private String defaultIconSize;
	
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
    
    @Resource(name="basedata.confCityService")
    private ConfCityService confCityService;
    
	@Value("#{'${search_city_list}'.trim()}")
	private String search_city_list;
	
	@Value("#{'${search_location_list}'.trim()}")
	private String search_location_list;
	
    @Value("#{'${HOUSE_SHARE_URL}'.trim()}")
    private String house_share_url;
    
    @Value("#{'${NO_LGIN_AUTH}'.trim()}")
    private String no_lgin_auth;
    
    @Value("#{'${search_ziroomstay_url}'.trim()}")
    private String search_ziroomstay_url;

	@Resource(name="message.TroyMsgFirstAdvisoryService")
	private TroyMsgFirstAdvisoryService troyMsgFirstAdvisoryService;

	@Resource(name="order.orderCommonService")
	private OrderCommonService orderCommonService;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;
	
	/**
	 * 
	 * 跳转客服找房页面
	 *
	 * @author zl
	 * @created 2017年5月25日 下午5:15:05
	 *
	 * @param request
	 */
	@RequestMapping("houseSearchList")
	public void houseSearchList(HttpServletRequest request,HouseInfoRequest houseInfo,String houseFid,Integer orderStatus,String orderSn) {
		 
		request.setAttribute("search_city_list", search_city_list);
		request.setAttribute("search_location_list", search_location_list);
		request.setAttribute("search_ziroomstay_url", search_ziroomstay_url);
		
		try {
			
			if (Check.NuNObj(houseInfo)) {
				houseInfo = new HouseInfoRequest();
			}
			
			try {
				
				if(!Check.NuNStr(houseInfo.getStartTime())){
					Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
					Date now =DateUtil.parseDate(DateUtil.dateFormat(new Date()), "yyyy-MM-dd");
					if(startTime.before(now)){
						houseInfo.setStartTime(DateUtil.dateFormat(new Date()));
					}
				}
				
				if(!Check.NuNStr(houseInfo.getStartTime()) &&  !Check.NuNStr(houseInfo.getEndTime())){
					Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
					Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getEndTime()), "yyyy-MM-dd");
					if(startTime.after(endTime)  || houseInfo.getStartTime().equals(houseInfo.getEndTime())){
						houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(startTime)));
					}
				}
			} catch (Exception e) {
				houseInfo.setStartTime(DateUtil.dateFormat(new Date()));
				houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(new Date())));
				LogUtil.info(LOGGER, "时间错误：startTime={}，endTime={}", houseInfo.getStartTime(),houseInfo.getEndTime());
			}

			String msgBaseFid = request.getParameter("msgBaseFid");

			if (!Check.NuNStr(msgBaseFid)) {
				FirstAdvisoryFollowVO followVO = new FirstAdvisoryFollowVO();

				troyMsgFirstAdvisoryService.getMsgFromFirstAdvisory(null, followVO, msgBaseFid);
				houseInfo.setPersonCount(followVO.getPersonNum());
				houseInfo.setPriceStart(followVO.getPrice());
				houseInfo.setPriceEnd(followVO.getPrice());
				houseInfo.setCityCode(followVO.getCityCode());
				houseInfo.setRentWay(followVO.getRentWay());
				houseFid = followVO.getHouseFid();

				try {
					Date now =DateUtil.parseDate(DateUtil.dateFormat(new Date()), "yyyy-MM-dd");
					if(!Check.NuNObj(followVO.getStartDate())){
						Date startTime = followVO.getStartDate();
						if (startTime.before(now)) {
							houseInfo.setStartTime(DateUtil.dateFormat(new Date()));
						} else {
							houseInfo.setStartTime(DateUtil.dateFormat(startTime));
						}
					}

					if(!Check.NuNObj(followVO.getStartDate()) &&  !Check.NuNObj(followVO.getEndDate())){
						Date startTime = followVO.getStartDate();
						Date endTime = followVO.getEndDate();
						if (startTime.after(endTime)) {
							houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(startTime)));
						} else {
							if (endTime.before(now)) {
								houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(now)));
							} else {
								houseInfo.setEndTime(DateUtil.dateFormat(endTime));
							}
						}
					}
				} catch (Exception e) {
					houseInfo.setStartTime(DateUtil.dateFormat(new Date()));
					houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(new Date())));
					LogUtil.info(LOGGER, "设置时间错误：startDate={}，endDate={}", followVO.getStartDate(),followVO.getEndDate());
				}
			}
			
			setOrderInfo( houseInfo, orderSn);
			
		} catch (Exception e) { 
			LogUtil.error(LOGGER, "客服找房初始化参数异常，param={},houseFid={},orderStatus={},e={}",JsonEntityTransform.Object2Json(houseInfo),houseFid ,orderStatus,e);
		}
		
		request.setAttribute("paramsInfo", houseInfo);
		request.setAttribute("houseFid", houseFid);
		request.setAttribute("orderStatus", orderStatus);

        // 床类型
        try {
            String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
            List<EnumVo> bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
            request.setAttribute("bedTypeList", bedTypeList);
        } catch (SOAParseException e) {
            LogUtil.error(LOGGER, "获取床类型异常 e={}", e);
        }

	}
	
	/**
	 * 
	 * 设置订单信息
	 *
	 * @author zl
	 * @created 2017年6月7日 下午8:57:01
	 *
	 * @param houseInfo
	 * @param orderSn
	 */
	private void setOrderInfo(HouseInfoRequest houseInfo,String orderSn){
		if (Check.NuNObjs(houseInfo,orderSn)) {
			return;
		}
		
		try {
			String orderJson =  orderCommonService.getOrderInfoByOrderSn(orderSn);
			DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
			if (orderDto.getCode()==DataTransferObject.SUCCESS) {
				OrderInfoVo orderInfoVo = SOAResParseUtil.getValueFromDataByKey(orderJson, "orderInfoVo", OrderInfoVo.class);
				if (!Check.NuNObj(orderInfoVo)) {
					 HouseSearchOneRequest requset = new HouseSearchOneRequest();
					 
					 if (!Check.NuNObj(orderInfoVo.getEndTime())) {
						 
						 if(orderInfoVo.getEndTime().before(new Date())){
							 return;
						 }						 
						 requset.setEndTime(DateUtil.dateFormat(orderInfoVo.getEndTime(), "yyyy-MM-dd"));
					 }
				 	if (!Check.NuNObj(orderInfoVo.getStartTime())) {
		            	requset.setStartTime(DateUtil.dateFormat(orderInfoVo.getStartTime(), "yyyy-MM-dd"));
					}
				 
				 	if(orderInfoVo.getRentWay()==RentWayEnum.HOUSE.getCode()){
				 		requset.setFid(orderInfoVo.getHouseFid());
				 	}else if(orderInfoVo.getRentWay()==RentWayEnum.ROOM.getCode()){
				 		requset.setFid(orderInfoVo.getRoomFid());
					}
		            requset.setRentWay(orderInfoVo.getRentWay());
		            requset.setPicSize(defaultPicSize);
		            requset.setSearchSourceTypeEnum(SearchSourceTypeEnum.troy_search_one);
		            
		            String params = JsonEntityTransform.Object2Json(requset); 
		            String resultJson = searchService.getOneHouseInfo(params); 
		            HouseInfoEntity houseInfoEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseInfo", HouseInfoEntity.class);
		            if (!Check.NuNObj(houseInfoEntity) && !Check.NuNObj(houseInfoEntity.getPrice())) {
		            	houseInfo.setPriceStart(houseInfoEntity.getPrice()/100);
		            	houseInfo.setPriceEnd(houseInfoEntity.getPrice()/100);
					} 
		            houseInfo.setCityCode(orderInfoVo.getCityCode());
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "设置订单信息异常，param={},orderSn={},e={}",JsonEntityTransform.Object2Json(houseInfo),orderSn ,e);
		}
		
	}
	
	
	/**
	 * 
	 * 搜索房源
	 *
	 * @author zl
	 * @created 2017年5月25日 下午6:22:55
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("houseQuery")
	@ResponseBody
	public PageResult houseQuery(HttpServletRequest request,HouseInfoRequest houseInfo) {
		 
		LogUtil.info(LOGGER, "troy 搜索房源参数:{}", JsonEntityTransform.Object2Json(houseInfo));
		long searchBegin = new Date().getTime();
		PageResult pageResult = new PageResult();
		
		try {
			if(!Check.NuNStr(houseInfo.getStartTime())){
				Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
                Date now =DateUtil.parseDate(DateUtil.dateFormat(new Date()), "yyyy-MM-dd");
                if(startTime.before(now)){
                    return pageResult;
                }
			}
	        
			if(!Check.NuNStr(houseInfo.getStartTime()) &&  !Check.NuNStr(houseInfo.getEndTime())){
	        	Date startTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getStartTime()), "yyyy-MM-dd");
	        	Date endTime = DateUtil.parseDate(ValueUtil.getTrimStrValue(houseInfo.getEndTime()), "yyyy-MM-dd");
	        	if(startTime.after(endTime)){
	        		return pageResult; 
	        	}
	        }
			
			if(!Check.NuNObj(houseInfo.getPriceStart())){
				houseInfo.setPriceStart(houseInfo.getPriceStart()*100);
			}
			if(!Check.NuNObj(houseInfo.getPriceEnd())){
				houseInfo.setPriceEnd(houseInfo.getPriceEnd()*100);
			}
			
			if(Check.NuNStr(houseInfo.getQ())){
	            houseInfo.setQ("*:*");
	        }
	        
	        houseInfo.setSearchSourceTypeEnum(SearchSourceTypeEnum.search_list);
	        houseInfo.setLimit(50);
			
	        //今日特惠
	        if(Check.NuNStr(houseInfo.getStartTime()) && Check.NuNStr(houseInfo.getEndTime()) 
	        		&& !Check.NuNStr(houseInfo.getJiaxinDiscount()) && houseInfo.getJiaxinDiscount().equalsIgnoreCase(ProductRulesEnum020.ProductRulesEnum020001.getValue())){
	        	houseInfo.setStartTime(DateUtil.dateFormat(new Date()));
	    		houseInfo.setEndTime(DateUtil.dateFormat(DateSplitUtil.getTomorrow(new Date())));
	        }
	       
	        
	        int size=30;	
	        int page=1;
	        List<HouseInfoListForTroyVo> resultList = new ArrayList<>();
	        List<HouseInfoEntity> list = new ArrayList<>();
	        
	        Integer priceStart = houseInfo.getPriceStart();
	        Integer priceEnd = houseInfo.getPriceEnd();
	        
	        houseInfo.setPriceStart(null);
	        houseInfo.setPriceEnd(null);
	        houseInfo.setSearchSourceTypeEnum(SearchSourceTypeEnum.troy_search_list);
	        Set<String> ids = new HashSet<>();
	        Set<String> uidSet = new HashSet<String>();
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        do {
	        	String par = JsonEntityTransform.Object2Json(houseInfo);
	        	LogUtil.info(LOGGER, "param={}", par);
	        	String jsonRst =searchService.getHouseListInfoAndSuggest(defaultPicSize, par,null);	  	       
	 	        DataTransferObject resDto = JsonEntityTransform.json2DataTransferObject(jsonRst);
	 	        if (resDto!=null && DataTransferObject.SUCCESS==resDto.getCode()) {
	 	        	list = SOAResParseUtil.getListValueFromDataByKey(jsonRst, "list", HouseInfoEntity.class);
					if (!Check.NuNCollection(list)) {
						for (HouseInfoEntity houseInfoEntity : list) {
							//将房东uid放入set集合中去重
							uidSet.add(houseInfoEntity.getLandlordUid());
						}
						 //查询uidSet集合中所有uid的60天内的接单率
						String landAcceptOrderJson = orderCommonService.getLandAcceptOrderRateIn60Days(JsonEntityTransform.Object2Json(uidSet));
						DataTransferObject landAcceptOrderDto = JsonEntityTransform.json2DataTransferObject(landAcceptOrderJson);
						if(landAcceptOrderDto.getCode()==DataTransferObject.SUCCESS){
							resultMap = SOAResParseUtil.getValueFromDataByKey(landAcceptOrderJson, "result", Map.class);
						}
						
						for (HouseInfoEntity houseInfoEntity : list) {
							if(resultList.size() > size-1){
								break;
							}
							if (ids.contains(houseInfoEntity.getFid())) {
								continue;
							}
							
							if(!Check.NuNObj(priceStart) && priceStart >houseInfoEntity.getPrice()){
								continue;
							}
							if(!Check.NuNObj(priceEnd) && priceEnd < houseInfoEntity.getPrice()){
								continue;
							}
							
							HouseInfoListForTroyVo vo = new HouseInfoListForTroyVo();
							BeanUtils.copyProperties(vo, houseInfoEntity);							
							try {
								String customerSearchVoJson = customerInfoService.getCustomerSearchVoByUid(houseInfoEntity.getLandlordUid());
								CustomerSearchVo customerSearchVo = SOAResParseUtil.getValueFromDataByKey(customerSearchVoJson, "customerSearchVo", CustomerSearchVo.class);
								if (!Check.NuNObj(customerSearchVo) && !Check.NuNObj(customerSearchVo.getCustomerBaseMsg()) ) {
									vo.setLandlordName(customerSearchVo.getCustomerBaseMsg().getRealName());
									vo.setLandlordMobile(customerSearchVo.getCustomerBaseMsg().getCustomerMobile());
									if(YesOrNoEnum.YES.getCode()==customerSearchVo.getIsAuditPassIn30Days()){
										vo.setIsAuditPassIn30DaysStr(YesOrNoEnum.YES.getName());
									}else{
										vo.setIsAuditPassIn30DaysStr(YesOrNoEnum.NO.getName());
									}
								}
							
							} catch (Exception e) {
								LogUtil.info(LOGGER, "查询房东信息失败，uid={}，e={}", e);
							}

							if(!Check.NuNObj(resultMap)){
								Object object = resultMap.get(houseInfoEntity.getLandlordUid());
								if(Check.NuNObj(object)){
									vo.setAcceptOrderRateIn60Days("无申请类型订单");
								}else{
									String acceptOrderRateIn60Days = String.valueOf(object);
									vo.setAcceptOrderRateIn60Days(acceptOrderRateIn60Days);
								}
							}
							
							vo.setPrice(vo.getPrice()/100);
							vo.setShareUrl(getHouseShareUrl(vo));
							resultList.add(vo);
							ids.add(vo.getFid());
						}
					}
	 	        }
	 	       page++;
	           houseInfo.setPage(page);
			}while (resultList.size() < size && page<15 && !Check.NuNCollection(list));
	        
	        pageResult.setRows(resultList);
			pageResult.setTotal(Long.valueOf(resultList.size()));
	        
		} catch (Exception e) {
			LogUtil.error(LOGGER, "troy 搜索房源异常,e={}", e);
		}
		LogUtil.info(LOGGER, "搜索结束，共耗时{}ms", (new Date().getTime()-searchBegin));
		
		return pageResult;
	}
	
	/**
	 * 
	 * 分享房地址
	 *
	 * @author zl
	 * @created 2017年6月7日 下午3:30:53
	 *
	 * @param houseInfoEntity
	 * @return
	 */
	private String getHouseShareUrl(HouseInfoEntity houseInfoEntity) {
		if(Check.NuNObj(houseInfoEntity)){
			return null;
		}
		if (!Check.NuNObj(houseInfoEntity.getIsTop50Online()) && houseInfoEntity.getIsTop50Online()==YesOrNoEnum.YES.getCode()) {
			return getTopHouseShareUrl(houseInfoEntity.getFid(), houseInfoEntity.getRentWay());
		}else{
			return getHouseShareUrl(houseInfoEntity.getFid(), houseInfoEntity.getRentWay());
		}
	}
	
	
	
	/**
	 * 
	 * top50分享房源地址
	 *
	 * @author zl
	 * @created 2017年6月7日 下午3:22:20
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
    private String getTopHouseShareUrl(String fid, Integer rentWay) {
    	
    	if (Check.NuNObjs(fid,rentWay)) {
			return null;
		}

        String houseShareUrl = new StringBuilder(house_share_url).append("houseTop/").append(no_lgin_auth)
                .append("/houseDetail?fid=").append(fid).append("&rentWay=")
                .append(rentWay).toString(); 
        return houseShareUrl;
    }
    
	 /**
	  * 
	  * 普通房源分享地址
	  *
	  * @author zl
	  * @created 2017年6月7日 下午3:24:24
	  *
	  * @param fid
	  * @param rentWay
	  * @return
	  */
    private String getHouseShareUrl(String fid, Integer rentWay) {
    	if (Check.NuNObjs(fid,rentWay)) {
			return null;
		}
        String houseShareUrl = new StringBuilder(house_share_url).append("tenantHouse/").append(no_lgin_auth)
                .append("/houseDetail?fid=").append(fid).append("&rentWay=")
                .append(rentWay).toString();
        return houseShareUrl;
    }


    /**
     * 查询是否有权限
     * @author wangwt
     * @created 2017年06月12日 09:51:52
     * @param request
     * @return
     */
	@RequestMapping(value = "getHouseDetailAuth", method = RequestMethod.POST)
	@ResponseBody
	public String hasHouseDetailAuth(HttpServletRequest request) {
		String url = request.getParameter("url");
		UpsUserVo user=UserUtil.getUpsUserMsg();
		Set<String> resUrlSet =user.getResourceVoSet();
		if(Check.NuNCollection(resUrlSet)||!resUrlSet.contains(url)){
			return "0";
		}
		return "1";
	}
}
