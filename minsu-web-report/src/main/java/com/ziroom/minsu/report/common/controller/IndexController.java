/*
 * Copyright (c) 2016. Copyright (c) 2016. ziroom.com.
 */
package com.ziroom.minsu.report.common.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.order.service.OrderService;
import com.ziroom.minsu.report.order.vo.OrderCountVo;
import com.ziroom.minsu.services.common.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.constant.Constant;
import com.ziroom.minsu.report.order.entity.CityOrderNumEntity;
import com.ziroom.minsu.report.basedata.entity.NameValueEntity;
import com.ziroom.minsu.report.common.util.UserUtil;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;


/**
 * 
 * <p>首页controller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {
	
    @Value("#{'${minsu.static.resource.url}'.trim()}")
    private String staticResourceUrl;
    
    @Resource(name = "report.confCityService")
    private ConfCityService confCityService;
    
    @Resource(name = "report.orderService")
    private OrderService orderService;
    
    
    @RequestMapping("index")
    public String index(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("staticResourceUrl", staticResourceUrl);
        //判断用户是否存在
        if(StringUtils.isBlank(UserUtil.getCurrentUserFid())){
        	return "/error/loginerror";
        }
		if (Check.NuNObj(UserUtil.getUpsUserMsg())
				|| Check.NuNObj(UserUtil.getUpsUserMsg().getCurrentuserEntity())) {
			return "/error/loginerror";
		}
		List<ResourceVo> resList=UserUtil.getUpsUserMsg().getResourceVoList();

		if(!Check.NuNCollection(resList)){
			request.setAttribute("currentuserResList", resList);
			return "/index/index";
		}
        return "/error/403";
    }
    
    /**
     * 
     * 城市经纬度坐标和订单数量
     *
     * @author bushujie
     * @created 2016年9月18日 下午3:46:45
     *
     * @return
     */
    @RequestMapping("cityMapCoordinates")
    @ResponseBody
    public  List<NameValueEntity> cityMapCoordinates(){
    	List<NameValueEntity> cityMapCoordinatesList=new ArrayList<NameValueEntity>();
    	//获取开通城市列表
    	List<ConfCityEntity> openCityList=confCityService.getOpenCity(null);

		Map<String,Integer>  cityMap = getOrderContMap(null,null);
    	for(ConfCityEntity city :openCityList){
    		List<Double> coordinatesList=new ArrayList<>();
    		coordinatesList.add(city.getLongitude());
    		coordinatesList.add(city.getLatitude());
			coordinatesList.add(ValueUtil.getdoubleValue(cityMap.get(city.getCode())));
			NameValueEntity nameValueEntity=new NameValueEntity();
    		nameValueEntity.setName(city.getShowName());
    		nameValueEntity.setValue(coordinatesList);
    		cityMapCoordinatesList.add(nameValueEntity);
    	}
    	return cityMapCoordinatesList;
    }

	/**
	 * 获取当前城市的数量
	 * @author afi
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    private Map<String,Integer> getOrderContMap(String startTime,String endTime){
		Map<String,Integer> orderMap = new HashMap<>();
		List<OrderCountVo> list = orderService.getOrderCityCountInfo(startTime,endTime);
		if (!Check.NuNCollection(list)){
			for (OrderCountVo vo : list) {
				orderMap.put(vo.getCityCode(),vo.getOrderNum());
			}
		}
		return orderMap;
	}


	/**
	 * 获取订单列表
	 * @author afi
	 * @return
	 */
	@RequestMapping("cityOrderNumList")
    @ResponseBody
    public List<CityOrderNumEntity> cityOrderNumList(){
    	List<CityOrderNumEntity> cityOrderNumList=new ArrayList<CityOrderNumEntity>();
    	//获取开通城市列表
    	List<ConfCityEntity> openCityList=confCityService.getOpenCity(null);
		//城市订单全部
		Map<String,Integer>  cityMapAll = getOrderContMap(null,null);
		//城市订单今天
		Date now = DateSplitUtil.getFirstSecondOfDay(new Date());
		Map<String,Integer>  cityMapToday = getOrderContMap(DateUtil.timestampFormat(now),null);

    	for(ConfCityEntity city :openCityList){
    		CityOrderNumEntity cityOrderNumEntity=new CityOrderNumEntity();
    		cityOrderNumEntity.setCityName(city.getShowName());
    		cityOrderNumEntity.setOrderTotal(ValueUtil.getlongValue(cityMapAll.get(city.getCode())));
    		cityOrderNumEntity.setOrderDayNum(ValueUtil.getlongValue(cityMapToday.get(city.getCode())));
    		cityOrderNumList.add(cityOrderNumEntity);
    	}
    	return cityOrderNumList;
    }
}
