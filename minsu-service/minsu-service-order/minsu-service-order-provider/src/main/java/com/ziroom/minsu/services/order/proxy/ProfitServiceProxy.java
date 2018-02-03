package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.entity.HouseRoomVo;
import com.ziroom.minsu.services.order.api.inner.ProfitService;
import com.ziroom.minsu.services.order.dto.OrderProfitRequest;
import com.ziroom.minsu.services.order.service.OrderLoadlordServiceImpl;
import com.ziroom.minsu.services.order.service.OrderMoneyServiceImpl;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收益
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie 
 * @since 1.0
 * @version 1.0
 */
@Component("order.profitServiceProxy")
public class ProfitServiceProxy implements ProfitService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfitServiceProxy.class);

	@Resource(name = "order.orderMoneyServiceImpl")
	private OrderMoneyServiceImpl orderMoneyServiceImpl;
	@Resource(name = "order.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;
	
	@Resource(name = "order.orderLoadlordServiceImpl")
	private OrderLoadlordServiceImpl orderLoadlordServiceImpl;
	
	@Autowired
	private RedisOperations redisOperations;
	
	/**
	 * 获取 一个房源(房间) 一个月的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
	@Override
	public String getHouseMonthProfit(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderProfitRequest request = JsonEntityTransform.json2Object(param, OrderProfitRequest.class);
			
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("getHouseMonthProfit 参数为空");
				 return dto.toJsonString();
			}
			
			if(Check.NuNStr(request.getHouseFid())){
				LogUtil.info(LOGGER,"getHouseMonthProfit houseFid为空.");
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("getHouseMonthProfit houseFid为空.");
				return dto.toJsonString();
			}
	    	
	    	if(request.getMonth() == 0){
	    		LogUtil.info(LOGGER,"getHouseMonthProfit 参数month为0.");
	    		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	    		dto.setMsg("getHouseMonthProfit 参数month为0.");
				return dto.toJsonString();
	    	}
	    	if(request.getRentWay() == RentWayEnum.ROOM.getCode() && Check.NuNStr(request.getRoomFid())){
				LogUtil.info(LOGGER, "getHouseMonthProfit 合租房子,roomFid:{}",request.getRoomFid());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("getHouseMonthProfit 合租房子,roomFid 为空");
				return dto.toJsonString();
			}
	    	if(request.getType() != 1 && request.getType() != 2){
	    		LogUtil.info(LOGGER, "getHouseMonthProfit 请求类型type错误,type:{}",request.getType());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("getHouseMonthProfit 请求类型type错误");
				return dto.toJsonString();
	    	}
	    	
			
	    	Long realMoney = 0l;
	    	if(request.getType() == 1){
	    		realMoney = Long.valueOf(this.getMonthRealProfit(request));
	    	}else if(request.getType() == 2){
	    		realMoney = Long.valueOf(this.getMonthPredictProfit(request)); 
	    	}
			
			dto.putValue("money", realMoney);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	/**
	 * 获取 一个用户  所有房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
    @Override
	public String getUserAllHouseMonthProfit(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderProfitRequest request = JsonEntityTransform.json2Object(param, OrderProfitRequest.class);
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("参数为空");
				 return dto.toJsonString();
			}
			if(Check.NuNStr(request.getUid())){
	    		LogUtil.info(LOGGER,"getUserAllHouseMonthProfit uid为空.");
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("uid为空.");
				return dto.toJsonString();
	    	}
	    	if(request.getMonth() == 0){
	    		LogUtil.info(LOGGER,"getUserAllHouseMonthProfit 参数month为0.");
	    		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	    		dto.setMsg("参数month为0.");
				return dto.toJsonString();
	    	}
	    	Map<String, Object> resMap = countProfit(request);
	    	if(Check.NuNMap(resMap)){
	    		LogUtil.info(LOGGER,"getUserAllHouseMonthProfit 计算收益错误.");
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("计算收益错误.");
				return dto.toJsonString();
	    	}
	    	
	    	dto.putValue("houseRealMoney", resMap.get("realMoney"));
			dto.putValue("housePredictMoney", resMap.get("predictMoney"));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	/**
	 * 获取 一个用户  所有房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
    @Override
	public String getUserHouseList(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			OrderProfitRequest request = JsonEntityTransform.json2Object(param, OrderProfitRequest.class);
			if(Check.NuNObj(request)){
				 LogUtil.info(LOGGER, "参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				 dto.setMsg("参数为空");
				 return dto.toJsonString();
			}
			
			if(Check.NuNStr(request.getUid())){
	    		LogUtil.info(LOGGER,"getUserAllHouseMonthProfit uid为空.");
	            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("uid为空.");
				return dto.toJsonString();
	    	}
	    	
	    	if(request.getMonth() == 0){
	    		LogUtil.info(LOGGER,"getUserAllHouseMonthProfit 参数month为0.");
	    		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	    		dto.setMsg("参数month为0.");
				return dto.toJsonString();
	    	}
	    	//获取房源列表
	    	List<HouseRoomVo> houseList = getHouseRoomList(request.getUid(),request.getLimit(),request.getPage());
	    	
	    	if(Check.NuNCollection(houseList)){
	    		dto.putValue("list", new ArrayList<HouseRoomVo>());
	    		dto.putValue("total", 0);
	    		return dto.toJsonString();
	    	}
	    	//计算收益
	    	List<HouseRoomVo> newHouseList = getHouseRoomProfitList(houseList,request);//计算收益给新的list
	    	dto.putValue("list", newHouseList);
	    	dto.putValue("total", houseList.size());
	    	
	    }catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	
	
	/**
	 * 获取 一个用户  所有房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 * @throws SOAParseException 
	 */
	private List<HouseRoomVo> getHouseRoomList(String uid,int limit,int page) throws SOAParseException{
		HouseBaseListDto requestDto = new HouseBaseListDto();
    	requestDto.setLandlordUid(uid);
    	requestDto.setLimit(limit);
    	requestDto.setPage(page);
    	//先用分页的
    	String resultJson = houseManageService.searchLandlordHouseList(JsonEntityTransform.Object2Json(requestDto));
		List<HouseRoomVo> houseList = operHouseRoomList(resultJson,uid);
    	return houseList;
	}
	
	/**
	 * 获取 一个用户  所有房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 * @throws SOAParseException 
	 */
	private List<HouseRoomVo> operHouseRoomList(String resultJson,String uid) throws SOAParseException{
		List<HouseRoomVo> houseList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomVo.class);
	    //循环查询合租房源的房间
	    List<HouseRoomVo> list=new ArrayList<HouseRoomVo>();
	    for(HouseRoomVo vo:houseList){
			
	    	if(vo.getRentWay()==RentWayEnum.HOUSE.getCode()){
	    		if(Check.NuNStrStrict(vo.getName())){
	    			vo.setName("待完善房源");
	    		}
	    		list.add(vo);
	    	} else if(vo.getRentWay()==RentWayEnum.ROOM.getCode()) {
	    		if(vo.getStatus()==null||HouseStatusEnum.DFB.getCode()==vo.getStatus()){
	        		vo.setName("待完善房源");
	        	}
	    		HouseBaseListDto dto=new HouseBaseListDto();
	    		dto.setHouseBaseFid(vo.getHouseBaseFid());
	    		dto.setLandlordUid(uid);
	    		//查询房源包含房间列表
	    		String roomJson = houseManageService.searchHouseRoomList(JsonEntityTransform.Object2Json(dto));
	    		LogUtil.info(LOGGER,"房源fid:"+dto.getHouseBaseFid()+",查询房间列表返回结果："+roomJson);
	    		List<HouseRoomVo> roomList=SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomVo.class);
	    		if(!Check.NuNCollection(roomList)){
	    			list.addAll(roomList);
	    		} else {
	    			if(Check.NuNObj(vo.getHouseStatus())){
	        			vo.setHouseStatus(HouseStatusEnum.DFB.getCode());
	        			vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowName());
	        			vo.setStatus(HouseStatusEnum.DFB.getCode());
	    			} else {
	        			vo.setStatusName(HouseStatusEnum.getHouseStatusByCode(vo.getHouseStatus()).getShowName());
	        			vo.setStatus(vo.getHouseStatus());
					}
	    			list.add(vo);
	    		}
			}
	    }
	    return list;
	}
	

	
	/**
	 * 计算  一个用户  所有房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param list
	 * @return 
	 */
	private List<HouseRoomVo> getHouseRoomProfitList(List<HouseRoomVo> list,OrderProfitRequest request){
		List<HouseRoomVo> houseList = new ArrayList<HouseRoomVo>();
		for(HouseRoomVo roomVo : list ){
    		request.setHouseFid(roomVo.getHouseBaseFid());
    		request.setRentWay(roomVo.getRentWay());
    		if(roomVo.getRentWay() == RentWayEnum.ROOM.getCode()){
    			request.setRoomFid(roomVo.getHouseRoomFid());
    		}else{
    			request.setRoomFid(null);
    		}
    		roomVo.setActualMoney(Integer.valueOf(this.getMonthRealProfit(request)));
    		roomVo.setPredictMoney(Integer.valueOf(this.getMonthPredictProfit(request)));
    		roomVo.setOrderNum(Integer.valueOf(String.valueOf(orderLoadlordServiceImpl.countOrderList(request))));
    		houseList.add(roomVo);
    	}
		return houseList;
	}
	
	/**
	 * 计算 一个用户  所有房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 * @throws SOAParseException 
	 */
	private Map<String, Object> countProfit(OrderProfitRequest request) throws SOAParseException{
		Map<String, Object> resMap = new HashMap<String,Object>(2);
		long houseRealMoney = 0L;
		long housePredictMoney = 0L;
		
		//从缓存获取数据  开始
		//uid_real_month 房源总收益   uid_predict_month 房源总收益
		String real_month_key = RedisKeyConst.getProfitKey(request.getUid(),"real",request.getMonth());
		String predict_month_key = RedisKeyConst.getProfitKey(request.getUid(),"predict",request.getMonth());
		
		String real_month_money= null;
		String predict_month_money= null;
		try {
			real_month_money=redisOperations.get(real_month_key);
			predict_month_money=redisOperations.get(predict_month_key);
			 LogUtil.info(LOGGER, "real_month_money:{}",real_month_money);
			 LogUtil.info(LOGGER, "predict_month_money:{}",predict_month_money);
			
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		if(!Check.NuNStr(real_month_money) && !Check.NuNStr(predict_month_money)){
			houseRealMoney = Long.valueOf(real_month_money);
			housePredictMoney = Long.valueOf(predict_month_money);
			resMap.put("realMoney", houseRealMoney);
			resMap.put("predictMoney", housePredictMoney);
			return resMap;
		}
		
		//统计房东共有多少房源
		Long count = this.countHouseRoomNum(request.getUid());
		
    	final int limit = 100;
    	final int pageAll = ValueUtil.getPage(count.intValue(), limit);
		for(int page = 1; page <= pageAll; page++) {
			List<HouseRoomVo> houseList = getHouseRoomList(request.getUid(),limit,page);
    		for(HouseRoomVo roomVo : houseList ){
	    		request.setHouseFid(roomVo.getHouseBaseFid());
	    		request.setRentWay(roomVo.getRentWay());
	    		if(roomVo.getRentWay() == RentWayEnum.ROOM.getCode()){
	    			request.setRoomFid(roomVo.getHouseRoomFid());
	    		}else{
	    			request.setRoomFid(null);
	    		}
	    		houseRealMoney += Integer.valueOf(this.getMonthRealProfit(request));
	    		housePredictMoney += Integer.valueOf(this.getMonthPredictProfit(request)); 
	    	}
    	}
		
		//将计算好的数据 放入缓存
		try {
			redisOperations.setex(real_month_key.toString(), RedisKeyConst.PRIFIT_CACHE_TIME, String.valueOf(houseRealMoney));
			redisOperations.setex(predict_month_key.toString(), RedisKeyConst.PRIFIT_CACHE_TIME, String.valueOf(housePredictMoney));
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
        
		//设置返回结果
		resMap.put("realMoney", houseRealMoney);
		resMap.put("predictMoney", housePredictMoney);
		return resMap;
	}
	
	/**
	 * 计算 一个用户  一个房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param uid
	 * @return 
	 */
	private long countHouseRoomNum(String uid){
		String resultJson = houseManageService.countHouseRoomNum(uid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		Long count = 0L;
		if(dto.getCode() == 0){
			count = dto.parseData("houseRoomNum", new TypeReference<Long>() {});
		}
		return count;
	}
	
	
	/**
	 * 计算 一个用户  一个房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
	private String getMonthRealProfit(OrderProfitRequest request){
		String key = "";
        
		if(request.getRentWay() == RentWayEnum.HOUSE.getCode()){
			key = RedisKeyConst.getProfitKey(request.getHouseFid(),request.getMonth(),"real");
		}else if(request.getRentWay() == RentWayEnum.ROOM.getCode()){
			key = RedisKeyConst.getProfitKey(request.getHouseFid(),request.getMonth(),request.getRoomFid(),"real");
		}
        LogUtil.info(LOGGER, "【收益】获取收益的key：{}", key);
		String money= null;
		
		try {
			money=redisOperations.get(key);
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		if(!Check.NuNStr(money)){
            LogUtil.info(LOGGER, "【收益】获取缓存收益的value：{}", money);
			return money;
		}else {
            LogUtil.info(LOGGER, "【收益】获取缓存收益的value：{}", money);
        }
		
		money = String.valueOf(orderMoneyServiceImpl.monthRealProfit(request));

        LogUtil.info(LOGGER, "【收益】数据库查询数据value：{}", money);
		//将计算好的数据 放入缓存
		try {
			redisOperations.setex(key.toString(), RedisKeyConst.PRIFIT_CACHE_TIME, money);
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		return money;
	}
	
	/**
	 * 计算 一个用户  一个房源   一个月  的实际收益和预计收益
	 * @author liyingjie
	 * @param param
	 * @return 
	 */
	private String getMonthPredictProfit(OrderProfitRequest request){
		String key = "";
        
		if(request.getRentWay() == RentWayEnum.HOUSE.getCode()){
			key = RedisKeyConst.getProfitKey(request.getHouseFid(),request.getMonth(),"predict");
		}else if(request.getRentWay() == RentWayEnum.ROOM.getCode()){
			key = RedisKeyConst.getProfitKey(request.getHouseFid(),request.getMonth(),request.getRoomFid(),"predict");
		}
        LogUtil.info(LOGGER, "【收益】获取收益的key：{}", key);
		String money= null;
		try {
			money=redisOperations.get(key);
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		if(!Check.NuNStr(money)){
            LogUtil.info(LOGGER, "【收益】获取缓存收益的value：{}", money);
			return money;
		}else {
            LogUtil.info(LOGGER, "【收益】获取缓存收益的value：{}", money);
        }
		money = String.valueOf(orderMoneyServiceImpl.monthPredictProfit(request));

        LogUtil.info(LOGGER, "【收益】获取数据库的收益的value：{}", money);
        //将计算好的数据 放入缓存
		try {
			redisOperations.setex(key.toString(), RedisKeyConst.PRIFIT_CACHE_TIME, money);
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		return money;
	}
	
}
