/**
 * @FileName: MsgAdvisotyFollowupController.java
 * @Package com.ziroom.minsu.troy.message.controller
 * 
 * @author loushuai
 * @created 2017年5月25日 上午11:52:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.follow.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.entity.HouseCityVo;
import com.ziroom.minsu.services.message.api.inner.MsgFirstAdvisoryService;
import com.ziroom.minsu.services.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryFollowVo;
import com.ziroom.minsu.services.order.api.inner.CustomerServeService;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.MsgAdvisoryFollowEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("messageFollowup")
public class MsgAdvisoryFollowupController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MsgAdvisoryFollowupController.class);
	
	@Resource(name="message.msgFirstAdvisoryService")
	private MsgFirstAdvisoryService msgFirstAdvisoryService;
	
	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;
    
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name="order.customerServeService")
	private CustomerServeService customerServeService;
	
	/**
	 * 
	 *  跳转订单跟进列表
	 *
	 * @author loushuai
	 * @created 2017年5月26日 上午10:47:19
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/listMsgAdvisoryFollow")
	public ModelAndView listMsgAdvisoryFollow(HttpServletRequest request) throws SOAParseException{
		String cityListJson = confCityService.getOpenCity();
		List<Map> cityList = SOAResParseUtil.getListValueFromDataByKey(cityListJson, "list", Map.class);
		ModelAndView mv = new ModelAndView("follow/msgAdvisoryFollowup");
		final Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
		cityList.sort(new Comparator<Map>() {
			@Override
			public int compare(Map o1, Map o2) {
				String city1 = (String) o1.get("name");
				String city2 = (String) o2.get("name");
				if (city1 == null || city2 == null) {
					return -1;
				}
				if (cmp.compare(city1, city2)>0){
					return 1;
				}else if (cmp.compare(city1, city2)<0){
					return -1;
				}
				return 0;
			}
		});
		mv.addObject("cityList", cityList);
		return mv;
	}
	
	/**
	 * 
	 * 获取所有需要跟进的首次咨询
	 *
	 * @author loushuai
	 * @created 2017年5月26日 上午10:45:41
	 *
	 * @param paramRequest
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/msgAdvisoryFollowList")
	@ResponseBody
    public String msgAdvisoryFollowList(@ModelAttribute("paramRequest")MsgAdvisoryFollowRequest paramRequest) throws SOAParseException {
		LogUtil.info(LOGGER, "页面请求参数msgAdvisoryFollowRequest：{}", paramRequest);
    	//根据房客手机号和名称获取房客uid，设置到request中，当做条件
		List<CustomerBaseMsgEntity> tenantCustomerList = this.getCustomerByTelAndName(paramRequest, YesOrNoEnum.NO.getCode());
    	if(!Check.NuNStr(paramRequest.getTenantName()) ||  !Check.NuNStr(paramRequest.getTenantTel())){
    		if(Check.NuNCollection(tenantCustomerList)){
    			return getResultJson(paramRequest, 0l, null).toString();
    		}
    	}
    	if(!Check.NuNCollection(tenantCustomerList) ){
    		List<String> tenantList = new ArrayList<>();
    		for (CustomerBaseMsgEntity customerBaseMsgEntity : tenantCustomerList) {
    			tenantList.add(customerBaseMsgEntity.getUid());
			}
			paramRequest.setTenantUids(tenantList);
		}
    	
    	//根据房东手机号和名称获取房东uid，设置到request中，当做条件
    	List<CustomerBaseMsgEntity> landcustomerList = this.getCustomerByTelAndName(paramRequest, YesOrNoEnum.YES.getCode());
    	if(!Check.NuNStr(paramRequest.getLandlordName()) ||  !Check.NuNStr(paramRequest.getLandlordTel())){
    		if(Check.NuNCollection(landcustomerList)){
    			return getResultJson(paramRequest, 0l, null).toString();
    		}
    	}
    	if(!Check.NuNCollection(landcustomerList)){
    		List<String> landLordList = new ArrayList<>();
    		for (CustomerBaseMsgEntity customerBaseMsgEntity : landcustomerList) {
    			landLordList.add(customerBaseMsgEntity.getUid());
			}
			paramRequest.setLandlordUids(landLordList);
		}
    	
    	//根据房源名称(模糊搜需) ， 国家， 城市三个维度获取整租房源  houseFidList
    	List<String> houseFidList = getFidListForIMFollow(paramRequest, 0);
    
    	//根据房间名称(模糊搜需) ， 国家，省份， 城市三个维度获取分租  roomFidList
    	List<String> roomFidList = getFidListForIMFollow(paramRequest, 1);
        if(!Check.NuNStr(paramRequest.getHouseName()) ||  !Check.NuNStr(paramRequest.getNationCode()) 
        		||  !Check.NuNStr(paramRequest.getCityCode()) ||  !Check.NuNStr(paramRequest.getCityCode())){
        	if(Check.NuNCollection(houseFidList) && Check.NuNCollection(roomFidList)){
        		return getResultJson(paramRequest, 0l, null).toString();
        	}
        }
    	List<String> allHfidList = getAllHfidList(houseFidList, roomFidList);
    	paramRequest.setHouseFidList(allHfidList);
    	//tenantUid，landlordUid，houseFidList，roomFidList==》当做查询条件 查询t_msg_first_advisory获取到所有需要跟踪的IM（首次咨询）
    	String allFollowListJson = msgFirstAdvisoryService.queryAllNeedFollowList(JsonEntityTransform.Object2Json(paramRequest));
    	DataTransferObject  allFollowListDto = JsonEntityTransform.json2DataTransferObject(allFollowListJson);
    	List<MsgAdvisoryFollowVo> imFollowVoList = null;
    	if(allFollowListDto.getCode()==DataTransferObject.SUCCESS){
    	imFollowVoList = SOAResParseUtil.getListValueFromDataByKey(allFollowListJson, "imFollowVoList", MsgAdvisoryFollowVo.class);
    		for (MsgAdvisoryFollowVo msgAdvisoryFollowVo : imFollowVoList) {
    			String fromUid = msgAdvisoryFollowVo.getTenantUid();
				//填充房客信息
				getCustomerMsgForVo(msgAdvisoryFollowVo, 0);

				//填充房东信息
				getCustomerMsgForVo(msgAdvisoryFollowVo, 1);

				//填充房源信息
				if(RentWayEnum.HOUSE.getCode()==msgAdvisoryFollowVo.getRentWay()){
					getHouseMsgForVo(msgAdvisoryFollowVo, 0);
				}else if(RentWayEnum.ROOM.getCode()==msgAdvisoryFollowVo.getRentWay()){
					getHouseMsgForVo(msgAdvisoryFollowVo, 1);
				}
				//跟进状态转换为跟进名称
				if(!Check.NuNObj(msgAdvisoryFollowVo.getFollowStatus())){
					MsgAdvisoryFollowEnum msgAdvisoryFollowEnum = MsgAdvisoryFollowEnum.getNameByCode(msgAdvisoryFollowVo.getFollowStatus());
					if(!Check.NuNObj(msgAdvisoryFollowEnum)){
						msgAdvisoryFollowVo.setFollowStatusName(msgAdvisoryFollowEnum.getName());
					}
				}

				/*************获取每天首次咨询是否有订单以及订单状态  @Author:lusp @Date:2017/8/11*************/
				MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = new MsgFirstAdvisoryEntity();
				msgFirstAdvisoryEntity.setHouseFid(msgAdvisoryFollowVo.getHouseFid());
				msgFirstAdvisoryEntity.setFromUid(fromUid);
				msgFirstAdvisoryEntity.setCreateTime(msgAdvisoryFollowVo.getCreateTime());

				String orderInfoJson = customerServeService.getAdvisoryOrderInfo(JsonEntityTransform.Object2Json(msgFirstAdvisoryEntity));
				DataTransferObject orderInfoDto = JsonEntityTransform.json2DataTransferObject(orderInfoJson);
				if(orderInfoDto.getCode() == DataTransferObject.SUCCESS){
					OrderEntity orderEntity = SOAResParseUtil.getValueFromDataByKey(orderInfoJson,"obj",OrderEntity.class);
					if(Check.NuNObj(orderEntity)){
						msgAdvisoryFollowVo.setIsOrder(0);
					}else{
						msgAdvisoryFollowVo.setOrderSn(orderEntity.getOrderSn());
						if(orderEntity.getPayStatus() == 0){
							msgAdvisoryFollowVo.setIsOrder(1);
						}else if(orderEntity.getPayStatus() == 1){
							msgAdvisoryFollowVo.setIsOrder(2);
						}
					}
				}else{
					LogUtil.error(LOGGER,"msgAdvisoryFollowList(),获取首次咨询是否产生订单信息失败，errorMsg:{}",orderInfoDto.getMsg());
					msgAdvisoryFollowVo.setIsOrder(0);
				}
				/*************获取每天首次咨询是否有订单以及订单状态  @Author:lusp @Date:2017/8/11*************/

			}
    	}
    	
    	Long size =  SOAResParseUtil.getLongFromDataByKey(allFollowListJson,"size");
		return getResultJson(paramRequest, size, imFollowVoList).toString();
	}

	/**
	 * 
	 * 获取结果json封住方法
	 *
	 * @author loushuai
	 * @created 2017年6月3日 下午5:07:20
	 *
	 * @param paramRequest
	 * @param size
	 * @param imFollowVoList
	 * @return
	 */
	public JSONObject getResultJson (MsgAdvisoryFollowRequest paramRequest,Long size, List<MsgAdvisoryFollowVo> imFollowVoList ) {
		JSONObject result = new JSONObject();
		Long totalpages = 0L;
		if(!Check.NuNObj(size)){
			if(size % paramRequest.getLimit() == 0){
				totalpages = size/paramRequest.getLimit();
			}else {
				totalpages = size/paramRequest.getLimit() + 1;
			}
		}
		result.put("totalpages", totalpages);
		result.put("currPage", paramRequest.getPage());
		result.put("totalCount", 0);
		result.put("dataList", imFollowVoList);
		return result;
	}
		
    /**
     * 
     * 根据客户名称和电话获取uid
     *
     * @author loushuai
     * @created 2017年5月25日 下午4:23:54
     *
     * @param paramRequest
     * @return
     */
    public List<CustomerBaseMsgEntity> getCustomerByTelAndName (MsgAdvisoryFollowRequest paramRequest, int isLandlord) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	if(YesOrNoEnum.NO.getCode()==isLandlord){//不是房东，
    		if(Check.NuNStr(paramRequest.getTenantName()) && Check.NuNStr(paramRequest.getTenantTel())){
    			return null;
        	}
        	if(!Check.NuNStr(paramRequest.getTenantName())){
        		paramMap.put("realName", paramRequest.getTenantName());
        	}
        	if(!Check.NuNStr(paramRequest.getTenantTel())){
        		paramMap.put("customerMobile", paramRequest.getTenantTel());
        	}
    	}else{
    		if(Check.NuNStr(paramRequest.getLandlordName()) && Check.NuNStr(paramRequest.getLandlordTel())){
    			return null;
        	}
    		if(!Check.NuNStr(paramRequest.getLandlordName())){
        		paramMap.put("realName", paramRequest.getLandlordName());
        	}
        	if(!Check.NuNStr(paramRequest.getLandlordTel())){
        		paramMap.put("customerMobile",paramRequest.getLandlordTel());
        	}
    	}
    	String customerBaseJson = customerInfoService.getByCustomNameAndTel(JsonEntityTransform.Object2Json(paramMap));
		DataTransferObject  customerBaseDto = JsonEntityTransform.json2DataTransferObject(customerBaseJson);
		if(customerBaseDto.getCode() == customerBaseDto.SUCCESS){
			List<CustomerBaseMsgEntity> cityList;
			try {
				cityList = SOAResParseUtil.getListValueFromDataByKey(customerBaseJson, "customerList", CustomerBaseMsgEntity.class);
				return cityList;
			} catch (SOAParseException e) {
				e.printStackTrace();
			}
		}
    	return null;
	}
    
    /**
     * 
     * 根据房源名称(或房间) ， 国家， 城市三个维度获取fidList
     *
     * @author loushuai
     * @created 2017年5月25日 下午6:46:34
     *
     * @param paramRequest
     * @param rentWay
     * @return
     */
    public List<String> getFidListForIMFollow(MsgAdvisoryFollowRequest paramRequest, int rentWay) {
    	if(Check.NuNStr(paramRequest.getHouseName()) &&  Check.NuNStr(paramRequest.getNationCode()) && Check.NuNStr(paramRequest.getCityCode())){
    		return null;
    	}
    	Map<String, Object> map = new HashMap<String, Object>(); 
		if(!Check.NuNStr(paramRequest.getHouseName())){
			map.put("houseName", paramRequest.getHouseName());
		}
		if(!Check.NuNStr(paramRequest.getNationCode())){
			map.put("nationCode", paramRequest.getNationCode());
		}
		if(!Check.NuNStr(paramRequest.getProvinceCode())){
			map.put("provinceCode", paramRequest.getProvinceCode());
		}
		if(!Check.NuNStr(paramRequest.getCityCode())){
			map.put("cityCode", paramRequest.getCityCode());
		}
		if(RentWayEnum.HOUSE.getCode()==rentWay){
			String hoseFidListJson = troyHouseMgtService.getHoseFidListForIMFollow(JsonEntityTransform.Object2Json(map));
			DataTransferObject hoseFidListDto = JsonEntityTransform.json2DataTransferObject(hoseFidListJson);
	    	if(hoseFidListDto.getCode()==DataTransferObject.SUCCESS){
	    		List<String> houseFidList = (List<String>) hoseFidListDto.getData().get("houseFidList");
	    		return houseFidList;
	    	}
		}else if(RentWayEnum.ROOM.getCode()==rentWay){
			String roomFidListJson = troyHouseMgtService.getRoomFidListForIMFollow(JsonEntityTransform.Object2Json(map));
			DataTransferObject roomFidListDto = JsonEntityTransform.json2DataTransferObject(roomFidListJson);
	    	if(roomFidListDto.getCode()==DataTransferObject.SUCCESS){
	    		List<String> roomFidList = (List<String>) roomFidListDto.getData().get("roomFidList");
	    		return roomFidList;
	    	}
		}
        return null;    	
	}
    
    /**
     * 
     * 根据CustomerUid获取用户信息，并填充到ImVo中
     *
     * @author loushuai
     * @throws SOAParseException 
     * @created 2017年5月26日 上午11:14:46
     *
     */
    public void  getCustomerMsgForVo(MsgAdvisoryFollowVo  msgAdvisoryFollowVo, int isLandlord) throws SOAParseException {
    	//根据tenantUid获取客户名称，电话.
    	String customerJson=null;
    	DataTransferObject customerDto =null;
    	if(isLandlord==0){
    		 customerJson = customerInfoService.getCustomerInfoByUid(msgAdvisoryFollowVo.getTenantUid());
    		 customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
    		
    	}else {
    		 customerJson = customerInfoService.getCustomerInfoByUid(msgAdvisoryFollowVo.getLandlordUid());
    		 customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
    	}
		
    	StringBuilder sBuilder=new StringBuilder();
		if(customerDto.getCode()==DataTransferObject.SUCCESS){
			CustomerBaseMsgEntity customerBaseMsgEntity = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
			if(!Check.NuNObj(customerBaseMsgEntity) && isLandlord==0){//是房客
				if(!Check.NuNStr(customerBaseMsgEntity.getRealName())){
					msgAdvisoryFollowVo.setTenantName(customerBaseMsgEntity.getRealName());
					sBuilder.append("用户姓名：").append(customerBaseMsgEntity.getRealName()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
					//msgAdvisoryFollowVo.setTenantUid(sBuilder.toString());  
				}else if(Check.NuNStr(customerBaseMsgEntity.getRealName()) && !Check.NuNStr(customerBaseMsgEntity.getNickName())){
					msgAdvisoryFollowVo.setTenantName(customerBaseMsgEntity.getNickName());
					sBuilder.append("用户昵称：").append(customerBaseMsgEntity.getNickName()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
				}else if(Check.NuNStr(customerBaseMsgEntity.getRealName()) && Check.NuNStr(customerBaseMsgEntity.getNickName()) && !Check.NuNStr(customerBaseMsgEntity.getCustomerMobile())){
					msgAdvisoryFollowVo.setTenantName(customerBaseMsgEntity.getCustomerMobile());
					sBuilder.append("用户电话：").append(customerBaseMsgEntity.getCustomerMobile()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
				}else if(Check.NuNStr(customerBaseMsgEntity.getRealName()) && Check.NuNStr(customerBaseMsgEntity.getNickName()) && Check.NuNStr(customerBaseMsgEntity.getCustomerMobile()) && !Check.NuNStr(customerBaseMsgEntity.getCustomerEmail())){
					msgAdvisoryFollowVo.setTenantName(customerBaseMsgEntity.getCustomerEmail());
					sBuilder.append("用户邮箱：").append(customerBaseMsgEntity.getCustomerEmail()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
				}else{
					sBuilder.append("用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
					msgAdvisoryFollowVo.setTenantName("无姓名");
				}
				if(!Check.NuNStr(customerBaseMsgEntity.getCustomerMobile())){
					msgAdvisoryFollowVo.setTenantTel(customerBaseMsgEntity.getCustomerMobile());
				}else{
					msgAdvisoryFollowVo.setTenantTel("-");
				}
				msgAdvisoryFollowVo.setTenantUid(sBuilder.toString()); 
			}else if(!Check.NuNObj(customerBaseMsgEntity) && isLandlord==1){
				if(!Check.NuNStr(customerBaseMsgEntity.getRealName())){
					msgAdvisoryFollowVo.setLandlordName(customerBaseMsgEntity.getRealName());
				}
				if(!Check.NuNStr(customerBaseMsgEntity.getCustomerMobile())){
					msgAdvisoryFollowVo.setLandlordTel(customerBaseMsgEntity.getCustomerMobile());
				}
			}
			
		}
	}
    
    /**
     * 
     * 根据houseFid获取房源（或房间）信息，并填充到ImVo中
     *
     * @author loushuai
     * @created 2017年5月26日 上午11:31:26
     *
     * @param msgAdvisoryFollowVo
     * @param rentWay
     */
    public void getHouseMsgForVo(MsgAdvisoryFollowVo  msgAdvisoryFollowVo, int rentWay){
    	String houseJson =null;
    	Map<String, Object> map = new HashMap<String, Object>(); 
    	if(rentWay==0){//整租
    		 map.put("houseFid", msgAdvisoryFollowVo.getHouseFid());
    		 houseJson = troyHouseMgtService.getHouseInfoForImFollow(JsonEntityTransform.Object2Json(map));
    	}else{
    		map.put("roomFid", msgAdvisoryFollowVo.getHouseFid());
    		houseJson = troyHouseMgtService.getRoomInfoForImFollow(JsonEntityTransform.Object2Json(map));
    	}
		 DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
		 if(houseDto.getCode()==DataTransferObject.SUCCESS){
			 HouseCityVo houseCityVo = houseDto.parseData("houseCityVo", new TypeReference<HouseCityVo>() {});
			 if(!Check.NuNObj(houseCityVo) && !Check.NuNStr(houseCityVo.getHouseName())){
				 msgAdvisoryFollowVo.setHouseName(houseCityVo.getHouseName());
			 }
			 if(!Check.NuNObj(houseCityVo) && !Check.NuNStr(houseCityVo.getCityCode())){
				 getCityNameByCode(houseCityVo, msgAdvisoryFollowVo);
			 }
		 }
    }
    
    /**
     * 
     * 根据code获取CityName
     *
     * @author loushuai
     * @created 2017年5月26日 下午12:54:16
     *
     */
    public void getCityNameByCode(HouseCityVo houseCityVo, MsgAdvisoryFollowVo  msgAdvisoryFollowVo) {
    	String cityNameJson = confCityService.getCityNameByCode(houseCityVo.getCityCode());
    	DataTransferObject cityNameDto = JsonEntityTransform.json2DataTransferObject(cityNameJson);
    	if(cityNameDto.getCode()==DataTransferObject.SUCCESS){
    		String cityName = cityNameDto.parseData("cityName", new TypeReference<String>() {});
    		msgAdvisoryFollowVo.setCityName(cityName);
    	}
	}
    
    /**
     * 
     * 所有fid（整租，分租）合成到一起
     *
     * @author loushuai
     * @created 2017年6月1日 下午4:26:09
     *
     * @param houseFidList
     * @param roomFidList
     * @return
     */
    public List<String> getAllHfidList(List<String> houseFidList, List<String> roomFidList) {
    	if(!Check.NuNCollection(houseFidList) && !Check.NuNCollection(roomFidList)){
    		List<String> allHfidList = new ArrayList<String>();
    		for (String housefid : houseFidList) {
    			allHfidList.add(housefid);
			}
    		for (String roomfid : roomFidList) {
    			allHfidList.add(roomfid);
			}
    		return allHfidList;
    	}
    	if(!Check.NuNCollection(houseFidList) && Check.NuNCollection(roomFidList)){
    		return houseFidList;
    	}
    	if(Check.NuNCollection(houseFidList) && !Check.NuNCollection(roomFidList)){
    		return roomFidList;
    	}
    	return null;
	}
}
