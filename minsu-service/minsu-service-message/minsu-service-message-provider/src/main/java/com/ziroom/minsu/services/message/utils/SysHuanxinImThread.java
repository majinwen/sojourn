/**
 * @FileName: SysHuanxinImThread.java
 * @Package com.ziroom.minsu.services.message.utils
 * 
 * @author yd
 * @created 2016年9月20日 上午9:48:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.message.entity.HuanxinImReVo;
import com.ziroom.minsu.services.message.service.HuanxinImRecordServiceImpl;
import com.ziroom.minsu.services.message.utils.base.HuanxinConfig;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;

/**
 * <p>
 * 
 * 定时任务同步环信IM消息  每天晚上12点，同步前一天的数据，即：同步前一天晚上到今天晚上12点的数据（例如：2016/09/09 24:00:00   到 2016/09/10 24:00:00）
 * 环信地址：http://docs.easemob.com/im/100serverintegration/30chatlog
 * 接口限流说明：同一个 APP 每分钟最多可调用10次，超过的部分会返回429或503错误。所以在调用程序中，如果碰到了这样的错误，需要稍微暂停一下并且重试。如果该限流控制不满足需求，请联系商务经理开放更高的权限。（一次最多返回1000条）
 * 算法：
 * 1. 获取环信token （保存当前redis中，失效时间6天，redis失效后去环信获取）
 * 2. 由于 msg_id 在环信返回中是唯一的，故为主键，并且入库时，以此值，校验重复，重复插入直接忽略
 * 3. 以当前时间往前推hours小时（25小时 这个时间做成可配置的，比定时任务时间长1个小时即可  例如：定时任务24小时，hours就是25，影响：理论上多取1个小时数据，好处：能保证数据不丢失）
 * 4. 对于接口调用次数限制问题处理：让接口去调用，出现429或503，让当前线程睡30s
 * 5. 接口循环去调用，直到接口获取完所以数据停止
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class SysHuanxinImThread implements Runnable{


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SysHuanxinImThread.class);
	/**
	 * 环信配置信息
	 */
	private HuanxinConfig huanxinConfig;

	/**
	 * redis 对象
	 */
	private RedisOperations redisOperations;

	/**
	 * 环信业务实现
	 */
	private HuanxinImRecordServiceImpl huanxinImRecordServiceImpl;

	/**
	 * 同步环信往前 推 hours 小时（单位：小时）
	 */
	private Integer hours;

	/**
	 * 异常后线程 睡的时间 单位(秒：s)
	 */
	private Integer sleepScends;

	public SysHuanxinImThread(){

	}
	/**
	 * 带参 构造
	 * @param huanxinConfig
	 */
	public SysHuanxinImThread(HuanxinConfig huanxinConfig,RedisOperations redisOperations,HuanxinImRecordServiceImpl huanxinImRecordServiceImpl
			,Integer hours,Integer sleepScends){

		this.huanxinConfig = huanxinConfig;
		this.redisOperations = redisOperations;
		this.huanxinImRecordServiceImpl = huanxinImRecordServiceImpl;
		this.hours = hours;
		this.sleepScends = sleepScends;
	}

	/**
	 * @return the hours
	 */
	public Integer getHours() {
		return hours;
	}
	/**
	 * @param hours the hours to set
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	/**
	 * @return the sleepScends
	 */
	public Integer getSleepScends() {
		return sleepScends;
	}
	/**
	 * @param sleepScends the sleepScends to set
	 */
	public void setSleepScends(Integer sleepScends) {
		this.sleepScends = sleepScends;
	}
	/**
	 * @return the huanxinImRecordServiceImpl
	 */
	public HuanxinImRecordServiceImpl getHuanxinImRecordServiceImpl() {
		return huanxinImRecordServiceImpl;
	}
	/**
	 * @param huanxinImRecordServiceImpl the huanxinImRecordServiceImpl to set
	 */
	public void setHuanxinImRecordServiceImpl(
			HuanxinImRecordServiceImpl huanxinImRecordServiceImpl) {
		this.huanxinImRecordServiceImpl = huanxinImRecordServiceImpl;
	}
	/**
	 * @return the redisOperations
	 */
	public RedisOperations getRedisOperations() {
		return redisOperations;
	}
	/**
	 * @param redisOperations the redisOperations to set
	 */
	public void setRedisOperations(RedisOperations redisOperations) {
		this.redisOperations = redisOperations;
	}
	/**
	 * @return the huanxinConfig
	 */
	public HuanxinConfig getHuanxinConfig() {
		return huanxinConfig;
	}

	/**
	 * @param huanxinConfig the huanxinConfig to set
	 */
	public void setHuanxinConfig(HuanxinConfig huanxinConfig) {
		this.huanxinConfig = huanxinConfig;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		if(!Check.NuNObj(this.getHours())){
			
			Long sysTime = System.currentTimeMillis()-this.getHours()*60*60*1000;
			LogUtil.info(logger, "同步环信聊天记录开始时间sysTime={},转化时间time={}", sysTime, DateUtil.dateFormat(new Date(sysTime),"yyyy-MM-dd HH:mm:ss"));
			try {
				//环信返回数据
				HuanxinImReVo huanxinImReVo = null;
				Long t1 = System.currentTimeMillis();
				if(Check.NuNObj(this.getSleepScends())){
					this.setSleepScends(10);
				}
				do {
					huanxinImReVo = saveHuanxinImToDb(huanxinImReVo,sysTime,this.getSleepScends());
					if(!Check.NuNObj(huanxinImReVo)){
						LogUtil.info(logger, "上一次同步数据公用信息huanxinImReVo={}", huanxinImReVo.toJsonStr());
					}
				} while (!Check.NuNObj(huanxinImReVo)&&!Check.NuNStr(huanxinImReVo.getCursor()));
				Long t2 = System.currentTimeMillis();
				try{
					SmsMessage smsMessage = new SmsMessage(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()),"定时任务同步环信IM聊天记录调用成功,用时"+(t2-t1)+"ms,请知晓");
					MessageUtils.sendSms(smsMessage,null);
				}catch (Exception e1){
					LogUtil.error(logger,"同步环信聊天记录定时任务---->发送短信异常");
				}

				LogUtil.info(logger, "本次同步环信用时t2-t1={},同步开始时间戳t1={},结束时间戳t2={}", t2-t1,t1,t2);
			} catch (Exception e) {
				LogUtil.error(logger, "同步环信聊天记录异常e={}", e);
			}
		}


	}


	/**
	 * 
	 * 同步环信 记录到数据库 ,并返回下一次分页标识cursor(此值为null，代表同步完本次所有数据)
	 *
	 * @author yd
	 * @created 2016年9月12日 上午10:39:02
	 *
	 * @return
	 */
	private HuanxinImReVo  saveHuanxinImToDb(HuanxinImReVo huanxinImReVo,Long sysTime,int sleepScends){


		String token = getHuanxinToken();
		HuanxinConfig huanxinConfig = this.getHuanxinConfig();
		if(!Check.NuNStr(token)){
			if(huanxinImReVo == null ) huanxinImReVo = new HuanxinImReVo();

			Map<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");
			headerMap.put("Authorization", "Bearer "+token);

			LogUtil.info(logger, "同步环信聊天记录开始时间sysTime={},转化时间time={}", sysTime, DateUtil.dateFormat(new Date(sysTime),"yyyy-MM-dd HH:mm:ss"));
			List<HuanxinImRecordEntity> listImRecord = null;
			String imRecordUrl;
			try {
				String huanxinParams = java.net.URLEncoder.encode("select * where timestamp>"+sysTime, "UTF-8");

				if(!Check.NuNStr(huanxinImReVo.getCursor())){
					huanxinParams +="&limit=1000&cursor="+huanxinImReVo.getCursor();
				}
				imRecordUrl = huanxinConfig.getHuanxinChatMessageUrl()+"?ql="+huanxinParams;
				LogUtil.info(logger, "同步环信记录开始ImRecordUrl={},headerMap={}", imRecordUrl,headerMap);
				String content = CloseableHttpsUtil.sendGet(imRecordUrl, headerMap);
				LogUtil.info(logger, "上一次同步环信记录公用信息huanxinImReVo={}",huanxinImReVo.toJsonStr());

				if(Check.NuNStr(content)){
					//当前返回内容为null说明请求异常 接口限流说明：同一个 APP 每分钟最多可调用10次，超过的部分会返回429或503错误。
					//所以在调用程序中，如果碰到了这样的错误，需要稍微暂停一下并且重试。如果该限流控制不满足需求，请联系商务经理开放更高的权限。（一次最多返回1000条） 
					//容错处理 ： 此时当前线程睡20s，在发起请求

					LogUtil.info(logger, "环信接口请求异常，当前线程沉睡开始....,当前时间戳t1={}", System.currentTimeMillis());
					Thread.sleep(sleepScends*1000);
					LogUtil.info(logger, "环信接口请求异常，当前线程沉睡结束....,当前时间戳t2={}，请求参数huanxinImReVo={}", System.currentTimeMillis(),huanxinImReVo.toJsonStr());
					return huanxinImReVo;
				}
				if(!Check.NuNStr(content)){
					Map<String, Object> huanxinImMap = new HashMap<String, Object>();
					huanxinImMap = (Map<String, Object>) JsonEntityTransform.json2Map(content);
					if(!Check.NuNMap(huanxinImMap)){
						Object entities = huanxinImMap.get("entities");
						if(!Check.NuNObj(entities)){
							JSONArray jsonArray = new JSONArray(JsonEntityTransform.Object2Json(entities));
							if(!Check.NuNObj(jsonArray)){

								//初始化下一次分页数据
								if(!Check.NuNObj(huanxinImMap.get("action"))){
									huanxinImReVo.setAction(String.valueOf(huanxinImMap.get("action")));
								}
								if(!Check.NuNObj(huanxinImMap.get("params"))){
									huanxinImReVo.setParams(String.valueOf(huanxinImMap.get("params")));
								}
								if(!Check.NuNObj(huanxinImMap.get("path"))){
									huanxinImReVo.setPath(String.valueOf(huanxinImMap.get("path")));
								}
								if(!Check.NuNObj(huanxinImMap.get("uri"))){
									huanxinImReVo.setUri(String.valueOf(huanxinImMap.get("uri")));
								}
								if(!Check.NuNObj(huanxinImMap.get("timestamp"))){
									huanxinImReVo.setTimestamp(Long.valueOf(huanxinImMap.get("timestamp").toString()));
								}
								if(!Check.NuNObj(huanxinImMap.get("duration"))){
									huanxinImReVo.setDuration(Integer.valueOf(huanxinImMap.get("duration").toString()));
								}
								if(!Check.NuNObj(huanxinImMap.get("count"))){
									huanxinImReVo.setCount(Integer.valueOf(huanxinImMap.get("count").toString()));

								}
								if(Check.NuNObj(huanxinImMap.get("cursor"))){
									LogUtil.info(logger, "环信同步结束，当前时间戳t={}", System.currentTimeMillis());
									huanxinImReVo.setCursor("");
									return huanxinImReVo;
								}
								huanxinImReVo.setCursor(String.valueOf(huanxinImMap.get("cursor")));

								listImRecord = new ArrayList<HuanxinImRecordEntity>();
								for(int i=0;i<jsonArray.length();i++){
									JSONObject obj=jsonArray.getJSONObject(i);
									HuanxinImRecordEntity huanxinIm = new HuanxinImRecordEntity();
									if(!obj.has("payload")&&Check.NuNObj(obj.getJSONObject("payload"))){
										continue;
									}
									//ext 和  bodies 解析  只保存有民宿标识的聊天记录
									JSONObject payload  = null;
									try {
										 payload = obj.getJSONObject("payload");
									} catch (Exception e) {
										LogUtil.error(logger, "当前环信异常数据obj={},原因不是json格式的数据", e);
										continue;
									}
									
									if(!payload.has("ext")){
										continue;
									}
									JSONObject ext = payload.getJSONObject("ext");

									//测试用
									//ext.put("ziroomFlag", "ZIROOM_MINSU_IM");
									//ext.put("domainFlag", "minsu_online");
									if(!Check.NuNObj(ext)&&ext.has("ziroomFlag")&&ext.has("domainFlag")){
										String ziroomFlag = ext.getString("ziroomFlag");
										String domainFlag = ext.getString("domainFlag");

										if(!Check.NuNObj(ziroomFlag)&&ziroomFlag.equals(huanxinConfig.getImMinsuFlag())&&domainFlag.equals(huanxinConfig.getDomainFlag())){
											huanxinIm.setZiroomFlag(ziroomFlag);
											String extStr = ext.toString();
											huanxinIm.setExt(extStr);
											if(extStr.length()>1000){
												huanxinIm.setExt(extStr.substring(0, 1000));
											}
											huanxinIm.setFid(UUIDGenerator.hexUUID());
											huanxinIm.setCreateDate(new Date());
											huanxinIm.setIsDel(IsDelEnum.NOT_DEL.getCode());
											huanxinIm.setIsRead(IsReadEnum.UNREAD.getCode());

											if(obj.has("uuid"))
												huanxinIm.setUuid(obj.getString("uuid"));
											if(obj.has("type"))
												huanxinIm.setInterfaceType(obj.getString("type"));
											if(obj.has("created")&&!Check.NuNObj(obj.getLong("created"))){
												huanxinIm.setCreated(new Date(obj.getLong("created")));
											}
											if(obj.has("modified")&&!Check.NuNObj(obj.getLong("modified"))){
												huanxinIm.setModified(new Date(obj.getLong("modified")));
											}
											if(obj.has("timestamp")&&!Check.NuNObj(obj.getLong("timestamp"))){
												huanxinIm.setTimestampSend(new Date(obj.getLong("timestamp")));
											}
											if(obj.has("from"))
												huanxinIm.setFromUid(obj.getString("from"));
											if(obj.has("msg_id"))
												huanxinIm.setMsgId(obj.getString("msg_id"));
											if(obj.has("to"))
												huanxinIm.setToUid(obj.getString("to"));
											if(obj.has("chat_type"))
												huanxinIm.setChatType(obj.getString("chat_type"));

											if(!payload.has("bodies")){
												continue;
											}

											JSONArray bodiesMap = payload.getJSONArray("bodies");
											if(Check.NuNObj(bodiesMap)){
												continue;
											}
											if(!Check.NuNObj(bodiesMap)){
												JSONObject bodiesObj=bodiesMap.getJSONObject(0);
												if(bodiesObj.has("msg"))
													huanxinIm.setMsg(bodiesObj.getString("msg"));
												if(bodiesObj.has("txt"))
													huanxinIm.setType(bodiesObj.getString("txt"));
												if(bodiesObj.has("url"))
													huanxinIm.setUrl(bodiesObj.getString("url"));
												if(bodiesObj.has("filename"))
													huanxinIm.setFilename(bodiesObj.getString("filename"));
												if(bodiesObj.has("secret"))
													huanxinIm.setSecret(bodiesObj.getString("secret"));
												if(bodiesObj.has("addr"))
													huanxinIm.setAddr(bodiesObj.getString("addr"));
												if(bodiesObj.has("length"))
													huanxinIm.setLength(bodiesObj.getInt("length"));
												if(bodiesObj.has("lat")&&!Check.NuNObj(bodiesObj.getDouble("lat"))){
													huanxinIm.setLat(Float.valueOf(bodiesObj.getDouble("lat")+""));
												}
												if(bodiesObj.has("lng")&&!Check.NuNObj(bodiesObj.getDouble("lng"))){
													huanxinIm.setLng(Float.valueOf(bodiesObj.getDouble("lng")+""));
												}	
											}
											listImRecord.add(huanxinIm);
										}

									}
								}
							}
						}
					}
					if(!Check.NuNCollection(listImRecord)&&!Check.NuNObj(this.getHuanxinImRecordServiceImpl())){
						this.getHuanxinImRecordServiceImpl().bachSaveHuanxinImRecord(listImRecord);
					}
					return huanxinImReVo;
				}
			} catch (UnsupportedEncodingException e) {
				huanxinImReVo = null;
				LogUtil.error(logger, "同步环信接口，查询参数转义异常e={}", e);
			} catch (InterruptedException e) {
				huanxinImReVo = null;
				LogUtil.error(logger, "同步环信接口，超时线程沉睡异常e={}", e);
			}catch (Exception e) {
				huanxinImReVo = null;
				LogUtil.error(logger, "同步环信接口，超时线程沉睡异常e={}", e);
			}
		}


		return null;
	}
	/**
	 * 
	 * 获取环信token
	 *
	 * @author yd
	 * @created 2016年9月10日 下午3:32:03
	 *
	 * @return
	 */
	private String getHuanxinToken(){

		String token = null;
		String huanxinTokenKey  = RedisKeyConst.getHuanxinTokenKey("huanxin_token");
		RedisOperations redisOperations = this.getRedisOperations();

		if(!Check.NuNObj(redisOperations)){
			try {
				token = this.redisOperations.get(huanxinTokenKey);
				LogUtil.info(logger, "根据环信 tokenKey={},获取token={}", huanxinTokenKey,token);
				if(!Check.NuNStr(token)){
					return token;
				}

			} catch (Exception e) {
				LogUtil.error(logger, "环信获取token，redis异常e={}", e);
			}
		}

		HuanxinConfig huanxinConfig = this.getHuanxinConfig();

		if(checkSysHuanxinConfig(huanxinConfig)){
			try {
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("grant_type", "client_credentials");
				paramsMap.put("client_id", huanxinConfig.getHuanxinClientId());
				paramsMap.put("client_secret", huanxinConfig.getHuanxinClientSecret());
				Long t1 = System.currentTimeMillis();
				String huanxinTokenRes = CloseableHttpsUtil.sendPost(huanxinConfig.getHuanxinTokenUrl(),JsonEntityTransform.Object2Json(paramsMap));
				Long t2 = System.currentTimeMillis();
				LogUtil.info(logger, "获取环信接口用户t2-t1={}ms,入参paramsMap={},请求url={},返回结果huanxinTokenRes={}", t2-t1,JsonEntityTransform.Object2Json(paramsMap),huanxinConfig.getHuanxinTokenUrl(),huanxinTokenRes);
				if(!Check.NuNStr(huanxinTokenRes)){
					LogUtil.info(logger, "环信token获取huanxinTokenRes={}", huanxinTokenRes);
					paramsMap.clear();
					paramsMap = (Map<String, String>) JsonEntityTransform.json2Map(huanxinTokenRes);
					if(!Check.NuNMap(paramsMap)){
						token = paramsMap.get("access_token");
						if(!Check.NuNStr(token)){
							this.redisOperations.setex(huanxinTokenKey, RedisKeyConst.HUANXIN_TOKEN_CACHE_TIME, token);
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(logger, "环信获取token接口异常e={},token={}", e,token);
			}
		}


		LogUtil.info(logger, "返回结果toekn={}", token);	
		return token;
	}



	/**
	 * 
	 * 校验当前环信配置文件
	 *
	 * @author yd
	 * @created 2016年9月20日 上午10:21:52
	 *
	 * @param huanxinConfig
	 * @return
	 */
	private boolean checkSysHuanxinConfig(HuanxinConfig huanxinConfig){

		if(Check.NuNObj(huanxinConfig)||Check.NuNStr(huanxinConfig.getHuanxinAppKey())
				||Check.NuNStr(huanxinConfig.getHuanxinChatMessageUrl())
				||Check.NuNStr(huanxinConfig.getHuanxinClientId())
				||Check.NuNStr(huanxinConfig.getHuanxinClientSecret())
				||Check.NuNStr(huanxinConfig.getHuanxinDomain())
				||Check.NuNStr(huanxinConfig.getHuanxinTokenUrl())
				||Check.NuNStr(huanxinConfig.getImMinsuFlag())){

			return false;
		}

		return true;
	}

}
