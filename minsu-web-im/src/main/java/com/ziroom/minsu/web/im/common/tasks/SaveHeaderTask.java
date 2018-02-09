package com.ziroom.minsu.web.im.common.tasks;

import java.text.DecimalFormat;

import com.ziroom.minsu.services.common.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.web.im.common.header.Header;

public class SaveHeaderTask implements Runnable{

	private static Logger LOGGER = LoggerFactory.getLogger(SaveHeaderTask.class);
	
	private String uid;
	
	private Header header;
	
	private String serverIp;
	
	private LocationTypeEnum locationTypeEnum;
	
	//如果location_type是订单则值是订单编号；是IM聊天、收藏、分享、浏览则是房源编号
	private String serialNumber; 
	//出租方式 0：整租，1：合租 (serial_number为房源编号时不为空)
	private Integer rentWay;
	
    private CustomerLocationService customerLocationService;
    
    private RedisOperations redisOperations;
	
	
	public SaveHeaderTask(String uid, Header header, String serverIp, LocationTypeEnum locationTypeEnum,String serialNumber,Integer rentWay,
			CustomerLocationService customerLocationService, RedisOperations redisOperations) {
		super();
		this.uid = uid;
		this.header = header;
		this.serverIp = serverIp;
		this.locationTypeEnum = locationTypeEnum;
		this.serialNumber = serialNumber;
		this.rentWay = rentWay;
		this.customerLocationService = customerLocationService;
		this.redisOperations = redisOperations;
	}

	@Override
	public void run() {
		
		if (Check.NuNObj(header)){
            return;
        }
		
		CustomerLocationEntity customerLocationEntity = transParams2Entity(uid,header,serverIp,serialNumber,rentWay);
        if (Check.NuNObj(customerLocationEntity)){
            return;
        }
        customerLocationEntity.setLocationType(locationTypeEnum.getCode());
    	if (!Check.NuNObj(customerLocationEntity)) {	
    		//搜索，IM一个小时保存一次
			if(Check.NuNObj(locationTypeEnum.getCode()) || (!Check.NuNObj(locationTypeEnum.getCode()) && (locationTypeEnum.getCode() == LocationTypeEnum.IM.getCode() || locationTypeEnum.getCode() == LocationTypeEnum.SEARCH.getCode()) ) ){
				if (checkLocatByRedis(customerLocationEntity)){
					LogUtil.info(LOGGER,"location info uid:{},header:{}" , uid, JsonEntityTransform.Object2Json(header));
					return;
				}
			}
    		customerLocationService.saveUserLocation(JsonEntityTransform.Object2Json(customerLocationEntity));
    		LogUtil.info(LOGGER, "Im 保存位置信息，location={}", JsonEntityTransform.Object2Json(customerLocationEntity));
		}
		
	}
	
	
    /**
     * 参数转化
     * @param uid
     * @param header
     * @param serverIp
     * @return
     */
    private CustomerLocationEntity transParams2Entity(final String uid,final Header header,final String serverIp,final String serialNumber,final Integer rentWay){
    	if (Check.NuNObj(header)){
            return null;
        }
    	
    	CustomerLocationEntity customerLocationEntity = new CustomerLocationEntity();
        customerLocationEntity.setFid(UUIDGenerator.hexUUID());
        customerLocationEntity.setUid(uid);
        customerLocationEntity.setAppName(header.getAppName());
		customerLocationEntity.setChannelName(header.getChannelName());
		customerLocationEntity.setOsVersion(header.getOsVersion());
		customerLocationEntity.setImei(header.getImei());
        customerLocationEntity.setImsi(header.getImsi());
        customerLocationEntity.setSerialNumber(serialNumber);
        customerLocationEntity.setRentWay(rentWay);
        Long ip = null;
        String deviceIP = header.getDeviceIP();
        if (!Check.NuNStr(serverIp)){
            //当服务获取ip直接获取当前的服务ip
            deviceIP = serverIp;
        }
        if (!IpUtil.checkIp(deviceIP)){
            LogUtil.info(LOGGER,"当前的ip异常,location info uid:{},header:{},deviceIP:{}" , uid, JsonEntityTransform.Object2Json(header),deviceIP);
            return null;
        }
        ip = com.ziroom.minsu.services.common.utils.IpUtil.Ip2Long(deviceIP);
        customerLocationEntity.setDeviceIp(ip);
        customerLocationEntity.setDeviceNo(header.getDeviceId());
        String locationCoordinate = header.getLocationCoordinate();

        Double longitude = null;
        Double latitude = null;
        if (Check.NuNStr(locationCoordinate)
                || ",".equals(locationCoordinate)
                || "0,0".equals(locationCoordinate)
                || locationCoordinate.indexOf(",") < 0
                ){
            //当前位置
        }else {
            String[] coordinate = locationCoordinate.split(",");
            DecimalFormat df=new DecimalFormat("#.000000");
            df.format(ValueUtil.getdoubleValue(coordinate[0]));
            longitude = ValueUtil.getdoubleValue(df.format(ValueUtil.getdoubleValue(coordinate[0])));
            latitude = ValueUtil.getdoubleValue(df.format(ValueUtil.getdoubleValue(coordinate[1])));
            if (longitude < 0 || longitude > 180){
                longitude = null;
            }
            if (latitude < 0 || latitude > 90){
                latitude = null;
            }
        }
        customerLocationEntity.setLatitude(latitude);
        customerLocationEntity.setLongitude(longitude);
        customerLocationEntity.setPhoneModel(header.getPhoneModel());
        customerLocationEntity.setVersionCode(header.getVersionCode());
        customerLocationEntity.setCityCode(header.getLocationCityCode());
        customerLocationEntity.setCityName(header.getLocationCityName());
    	
    	return customerLocationEntity;
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
	
	
}
