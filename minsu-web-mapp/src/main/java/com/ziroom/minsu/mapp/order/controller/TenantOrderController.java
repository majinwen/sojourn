package com.ziroom.minsu.mapp.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;

/**
 * 订单相关
 * @author lishaochuan
 *
 */
@RequestMapping("/orderten")
@Controller
public class TenantOrderController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(TenantOrderController.class);
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name = "house.tenantHouseService")
	private TenantHouseService tenantHouseService;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
	private String USER_DEFAULT_PIC_URL;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	@Value("#{'${MAPP_URL}'.trim()}")
	private String MAPP_URL;
	
	
	/**
	 * 支付完后分享
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/orderShare")
	public String orderShare(HttpServletRequest request){
		try {
			String fid = request.getParameter("fid");
			String rentWay = request.getParameter("rentWay");
			String city = request.getParameter("c");
			LogUtil.info(LOGGER, "orderShare param, fid:{},rentWay:{},city:{}", fid, rentWay, city);
			if(Check.NuNStr(fid) || Check.NuNStr(rentWay)){
				return "error/error";
			}
			
			String cityJson = confCityService.getCityNameByCode(city);
			DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
			String cityName = (String) cityDto.getData().get("cityName");
			if(Check.NuNStr(cityName)){
				request.setAttribute("cityName", "旅行");
			}else{
				if(cityName.endsWith("市")){
					cityName = cityName.substring(0,cityName.length()-1);
				}
				request.setAttribute("cityName", cityName);
			}
			
			HouseDetailDto houseDetailDto = new HouseDetailDto();
			houseDetailDto.setFid(fid);
			houseDetailDto.setRentWay(ValueUtil.getintValue(rentWay));
			String houseDetail = tenantHouseService.houseDetail(JsonEntityTransform.Object2Json(houseDetailDto));
			TenantHouseDetailVo tenantHouseDetailVo = SOAResParseUtil.getValueFromDataByKey(houseDetail, "houseDetail", TenantHouseDetailVo.class);
			if(Check.NuNObj(tenantHouseDetailVo)){
				LogUtil.info(LOGGER, "查询房源为空，houseDetail:{}", houseDetail);
				return "error/error";
			}
			
			//默认图片处理
			tenantHouseDetailVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), detail_big_pic));

			//图片列表处理
			if(!Check.NuNCollection(tenantHouseDetailVo.getPicList())){
				List<String> picList=new ArrayList<String>();
				for(String picUrl:tenantHouseDetailVo.getPicList()){
					picList.add(PicUtil.getSpecialPic(picBaseAddrMona, picUrl, detail_big_pic));
				}
				tenantHouseDetailVo.setPicList(picList);
			}
			//如果图片列表为空，添加默认图片
			if(Check.NuNCollection(tenantHouseDetailVo.getPicList())){
				tenantHouseDetailVo.getPicList().add(tenantHouseDetailVo.getDefaultPic());
			}
			
			request.setAttribute("picList", tenantHouseDetailVo.getPicList());
			request.setAttribute("houseName", tenantHouseDetailVo.getHouseName());
			request.setAttribute("houseAroundDesc", tenantHouseDetailVo.getHouseAroundDesc());
			if(!Check.NuNStr(tenantHouseDetailVo.getDefaultPic())){
				request.setAttribute("defaultPic", tenantHouseDetailVo.getDefaultPic());
			}
			if(!Check.NuNStr(tenantHouseDetailVo.getHouseDesc())){
				request.setAttribute("houseDesc", tenantHouseDetailVo.getHouseDesc());
			}else{
				request.setAttribute("houseDesc", "自如民宿与你 开启一段美妙之旅");
			}
			
			//房东信息获取
			String landlordJson = customerMsgManagerService.getCutomerVo(tenantHouseDetailVo.getLandlordUid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(landlordJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.info(LOGGER, "查询房东信息为空，landlordJson:{}", landlordJson);
				return "error/error";
			}
			
			CustomerVo landlord = dto.parseData("customerVo", new TypeReference<CustomerVo>() {});
			if (!Check.NuNObj(landlord)) {
				request.setAttribute("headIcon", landlord.getUserPicUrl());
				// 默认显示昵称，如果没有显示真实姓名
				if (Check.NuNStr(landlord.getNickName())) {
					request.setAttribute("name", landlord.getRealName());
				} else {
					request.setAttribute("name", landlord.getNickName());
				}
			}
			
			
			//查询房东的个人介绍
			DataTransferObject introduceDto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.selectCustomerExtByUid(tenantHouseDetailVo.getLandlordUid()));
			if(introduceDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgExtEntity customerBaseMsgExt = introduceDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				if(!Check.NuNObj(customerBaseMsgExt)){
					if(customerBaseMsgExt.getCustomerIntroduce().length()>70){
						request.setAttribute("introduce", customerBaseMsgExt.getCustomerIntroduce().substring(0, 70) + "...");
					}else{
						request.setAttribute("introduce", customerBaseMsgExt.getCustomerIntroduce());
					}
				}
			}
			
			String houseUrl = MAPP_URL + "/tenantHouse/ee5f86/houseDetail?fid=" + fid + "&rentWay=" + rentWay + "&from=singlemessage&isappinstalled=1";
			request.setAttribute("houseUrl", houseUrl);
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "orderShare,error:{}",e);
		}
		return "order/orderShare";
	}
	
	
}
