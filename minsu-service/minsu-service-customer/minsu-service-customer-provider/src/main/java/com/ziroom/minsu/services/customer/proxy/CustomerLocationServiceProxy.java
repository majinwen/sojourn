package com.ziroom.minsu.services.customer.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.IpUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import com.ziroom.minsu.services.customer.service.CustomerLocationServiceImpl;
import com.ziroom.minsu.valenum.customer.SynStatusEnum;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/30.
 * @version 1.0
 * @since 1.0
 */
@Component("customer.customerLocationServiceProxy")
public class CustomerLocationServiceProxy implements CustomerLocationService {


	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMsgManagerServiceProxy.class);

	@Resource(name="customer.customerLocationServiceImpl")
	private CustomerLocationServiceImpl customerLocationService;


	@Autowired
	private RedisOperations redisOperations;

	@Value("#{'${BAIDU_API_IP_URL}'.trim()}")
	private String BAIDU_API_IP_URL;


	/**
	 * 定时任务补全当前的经纬度为空的数据信息
	 * @return
	 */
	public String taskFillUserLocation(){
		DataTransferObject dto = new DataTransferObject();
		try{
			Long count = customerLocationService.countIpLocation();
			int limit = 50;
			int pageAll = ValueUtil.getPage(count.intValue(),limit);
			for(int page=1 ; page <= pageAll ; page++){
				List<CustomerLocationEntity> list = customerLocationService.getIpLocation(limit);
				if (!Check.NuNCollection(list)){
					for (CustomerLocationEntity locationEntity : list) {
						this.fillUserLocation(locationEntity);
					}
				}
			}
			LogUtil.info(LOGGER, "填充当前的条数:{}",count);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作失败");
		}
		return dto.toJsonString();
	}

	/**
	 * 填充当前的位置信息
	 * @author afi
	 * @param locationEntity
	 */
	private void fillUserLocation(CustomerLocationEntity locationEntity){
		try{
			if (!checkUserLocation(locationEntity)){
				//异常的经纬度
				customerLocationService.updateStatusByFid(locationEntity.getFid(), SynStatusEnum.IP_ERROR.getCode());
				LogUtil.info(LOGGER, "异常的经纬度信息:fid:{}",locationEntity.getFid());
				return;
			}
			String Json = CloseableHttpUtil.sendGet(BAIDU_API_IP_URL+IpUtil.intToIP(locationEntity.getDeviceIp()),null);
			Map<String,Object>  map = transLocationMap(Json);
			if (Check.NuNObj(map) || ValueUtil.getdoubleValue(map.get("longitude")) >180 || ValueUtil.getdoubleValue(map.get("latitude")) >90){
				//异常的经纬度
				customerLocationService.updateStatusByFid(locationEntity.getFid(), SynStatusEnum.FAIL.getCode());
				LogUtil.info(LOGGER, "获取百度经纬度失败:fid:{}",locationEntity.getFid());
				return;
			}else {
				locationEntity.setCityName(ValueUtil.getStrValue(map.get("cityName")));
				locationEntity.setCityCode(ValueUtil.getStrValue(map.get("cityCode")));
				locationEntity.setLongitude(ValueUtil.getdoubleValue(map.get("longitude")));
				locationEntity.setLatitude(ValueUtil.getdoubleValue(map.get("latitude")));
				locationEntity.setSynStatus(SynStatusEnum.SUCCESS.getCode());
				//更新当前的位置信息
				customerLocationService.updateCustomerLocation(locationEntity);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}

	/**
	 * 校验经纬度
	 * @author afi
	 * @param locationEntity
	 * @return
	 */
	private boolean checkUserLocation(CustomerLocationEntity locationEntity){
		boolean falg = false;
		if (Check.NuNObj(locationEntity)){
			falg = true;
		}
		if (Check.NuNObj(locationEntity.getDeviceIp())){
			falg = true;
		}
		String ip = IpUtil.intToIP(locationEntity.getDeviceIp());
		if (IpUtil.checkIp(ip)){
			falg = true;
		}
		return falg;
	}

	/**
	 * 解析当前的定位
	 * @author afi
	 * @param json
	 * @return
	 */
	private static Map<String,Object> transLocationMap(String json){
		if (Check.NuNStr(json)){
			return null;
		}
		Map<String,Object> map =(Map<String,Object>)JsonEntityTransform.json2Map(json);
		if (!ValueUtil.getStrValue(map.get("status")).equals("0")){
			return null;
		}
		Map<String,Object> content = (Map<String,Object> )map.get("content");
		if (Check.NuNObj(content)){
			return null;
		}
		Map<String,Object> address_detail = (Map<String,Object> )content.get("address_detail");
		if (Check.NuNObj(address_detail)){
			return null;
		}
		Map<String,Object> rst = new HashMap<>();
		rst.put("cityName",address_detail.get("city"));
		rst.put("cityCode",address_detail.get("city_code"));

		Map<String,Object> point = (Map<String,Object> )content.get("point");
		if (!Check.NuNObj(point)){
			rst.put("longitude",point.get("x"));
			rst.put("latitude",point.get("y"));
		}
		return rst;
	}

	public static void main(String[] args) {
		String url = "http://api.map.baidu.com/location/ip?ak=sakCBjtP5nIXwobWibcIGma6xVGjgn4m&ip=120.134.33.9";
		String rst = CloseableHttpUtil.sendGet(url,null);
		if (Check.NuNStr(rst)){
			return;
		}
		Map<String,Object>  map = transLocationMap(rst);
		System.out.println(JsonEntityTransform.Object2Json(map));

	}


	/**
	 * 保存当前用户的微信信息
	 * 并且填充信息
	 * @param paramJson
	 * @return
	 */
	public String saveUserLocationAndFill(String paramJson){
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerLocationEntity customerLocationEntity = JsonEntityTransform.json2Object(paramJson, CustomerLocationEntity.class);
			if (Check.NuNObj(customerLocationEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("位置参数异常");
				return dto.toJsonString();
			}
			if(Check.NuNStr(customerLocationEntity.getDeviceNo()) && Check.NuNStr(customerLocationEntity.getUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return dto.toJsonString();
			}
			if (checkLocatByRedis(customerLocationEntity)){
				return dto.toJsonString();
			}else {
				CustomerLocationEntity has = customerLocationService.getCustomerLocationOneHas(customerLocationEntity.getUid(),customerLocationEntity.getDeviceNo());
				if (!Check.NuNObj(has)){
					//已经保存成功 将对象放入缓存中
					this.fill2cache(customerLocationEntity);
					return dto.toJsonString();
				}
				//
				String Json = CloseableHttpUtil.sendGet(BAIDU_API_IP_URL+IpUtil.intToIP(customerLocationEntity.getDeviceIp()),null);
				Map<String,Object>  map = transLocationMap(Json);
				if (Check.NuNObj(map) || ValueUtil.getdoubleValue(map.get("longitude")) >180 || ValueUtil.getdoubleValue(map.get("latitude")) >90){
					//异常的经纬度
					LogUtil.info(LOGGER, "获取百度经纬度失败:ip:{}",IpUtil.intToIP(customerLocationEntity.getDeviceIp()));
				}else {
					customerLocationEntity.setCityName(ValueUtil.getStrValue(map.get("cityName")));
					customerLocationEntity.setCityCode(ValueUtil.getStrValue(map.get("cityCode")));
					customerLocationEntity.setLongitude(ValueUtil.getdoubleValue(map.get("longitude")));
					customerLocationEntity.setLatitude(ValueUtil.getdoubleValue(map.get("latitude")));
					customerLocationEntity.setSynStatus(SynStatusEnum.SUCCESS.getCode());

					//更新当前的位置信息
					customerLocationService.saveCustomerLocation(customerLocationEntity);
				}
				//将对象放入缓存中
				this.fill2cache(customerLocationEntity);
			}

		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作失败");
		}
		return dto.toJsonString();
	}

	/**
	 * 保存当前用户的微信信息
	 * 并且填充信息
	 * @param paramJson
	 * @return
	 */
	public String saveUserLocationAndFillV1(String paramJson){
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerLocationEntity customerLocationEntity = JsonEntityTransform.json2Object(paramJson, CustomerLocationEntity.class);
			if (Check.NuNObj(customerLocationEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("位置参数异常");
				return dto.toJsonString();
			}
			if(Check.NuNStr(customerLocationEntity.getDeviceNo()) && Check.NuNStr(customerLocationEntity.getUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return dto.toJsonString();
			}
			//更新当前的位置信息
			customerLocationService.saveCustomerLocation(customerLocationEntity);
			//将对象放入缓存中
			this.fill2cache(customerLocationEntity);

		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作失败");
		}
		return dto.toJsonString();
	}

	/**
	 * 将当前的对象放入缓存
	 * @param customerLocationEntity
	 */
	private void fill2cache(CustomerLocationEntity customerLocationEntity){
		try {
			String key = getLocalKey(customerLocationEntity);
			redisOperations.setex(RedisKeyConst.getLocalKey(key).toString(), RedisKeyConst.LOCATION_TIME, JsonEntityTransform.Object2Json(customerLocationEntity));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}", e);
		}
	}


	/**
	 * 保存当前用户的
	 * @param paramJson
	 * @return
	 */
	public String saveUserLocation(String paramJson){
		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerLocationEntity customerLocationEntity = JsonEntityTransform.json2Object(paramJson, CustomerLocationEntity.class);
			if (Check.NuNObj(customerLocationEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("保存位置参数异常");
				return dto.toJsonString();
			}else if(Check.NuNStr(customerLocationEntity.getDeviceNo()) && Check.NuNStr(customerLocationEntity.getUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return dto.toJsonString();
			}
			if (checkLocatByRedis(customerLocationEntity)){
				return dto.toJsonString();
			}else {
				CustomerLocationEntity has = customerLocationService.getCustomerLocationOneHas(customerLocationEntity.getUid(),customerLocationEntity.getDeviceNo());
				if (Check.NuNObj(has)){
					//1小时之内未保存
					customerLocationService.saveCustomerLocation(customerLocationEntity);
					try {
						String key = getLocalKey(customerLocationEntity);
						redisOperations.setex(RedisKeyConst.getLocalKey(key).toString(), RedisKeyConst.LOCATION_TIME, JsonEntityTransform.Object2Json(customerLocationEntity));
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误,e:{}", e);
					}
				}
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作失败");
		}
		return dto.toJsonString();
	}


	/**
	 * 获取当前的key
	 * @param customerLocationEntity
	 * @return
	 */
	private String getLocalKey(CustomerLocationEntity customerLocationEntity){
		String  keyPre = "";
		//判断是否存在uid
		if(!Check.NuNStr(customerLocationEntity.getUid())){
			keyPre = customerLocationEntity.getUid();
		}else if(Check.NuNStr(customerLocationEntity.getDeviceNo())){
			keyPre = customerLocationEntity.getDeviceNo();
		}
		return keyPre;
	}
	/**
	 * 获取当前缓存中的信息
	 * @param customerLocationEntity
	 * @return
	 */
	private boolean checkLocatByRedis(CustomerLocationEntity customerLocationEntity){
		boolean has = false;
		String keyPre = getLocalKey(customerLocationEntity);
		if (Check.NuNStr(keyPre)){
			return has;
		}
		String key = RedisKeyConst.getLocalKey(keyPre);
		String rst = null;
		try {
			rst = redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}", e);
		}
		if (!Check.NuNStr(rst)){
			has = true;
		}
		return has;
	}


	@Override
	public String getCustomerLocation(String paramJson) {

		DataTransferObject dto = new DataTransferObject();
		try{
			CustomerLocationEntity customerLocationEntity = JsonEntityTransform.json2Object(paramJson, CustomerLocationEntity.class);
			if (Check.NuNObj(customerLocationEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return dto.toJsonString();
			}else if(Check.NuNStr(customerLocationEntity.getDeviceNo()) && Check.NuNStr(customerLocationEntity.getUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return dto.toJsonString();
			}

			customerLocationEntity = customerLocationService.getCustomerLocationOneHas(customerLocationEntity.getUid(),customerLocationEntity.getDeviceNo());

			dto.putValue("result", customerLocationEntity);  

		}catch (Exception e) {
			LogUtil.error(LOGGER, "查询位置信息错误,e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
			return dto.toJsonString();
		}

		return dto.toJsonString();
	}



}
