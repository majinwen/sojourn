package com.ziroom.minsu.services.basedata.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.dto.ZrpPayRequest;
import com.ziroom.minsu.services.basedata.entity.ConfValueVo;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum002;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum003;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.CityTemplateEntity;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.entity.conf.TemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.basedata.entity.ConfigForceVo;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.TemplateEntityVo;
import com.ziroom.minsu.services.basedata.service.CityTemplateServiceImpl;
import com.ziroom.minsu.services.basedata.service.ConfDicServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0017Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;

import static com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum003.ContractTradingEnum003001;

/**
 * <p>城市模板管理</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/21.
 * @version 1.0
 * @since 1.0
 */
@Component("basedata.cityTemplateProxy")
public class CityTemplateProxy implements CityTemplateService{


	private static final Logger LOGGER = LoggerFactory.getLogger(OpLogProxy.class);



	@Resource(name = "basedata.cityTemplateServiceImpl")
	CityTemplateServiceImpl cityTemplateService;


	@Resource(name = "basedata.confDicServiceImpl")
	ConfDicServiceImpl confDicService;



	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;

	@Autowired
	private RedisOperations redisOperations;
	
	private static String ZERO_STRING = "0";




	/**
	 * 插入模板信息
	 * @param templateJosn
	 * @return
	 */
	public String insertTemplate(String templateJosn){
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNObj(templateJosn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			TemplateEntity templateEntity = JsonEntityTransform.json2Object(templateJosn,TemplateEntity.class);
			if (Check.NuNObj(templateEntity)){
				LogUtil.info(LOGGER,"dicItemJosn is error on insert");
				throw  new BusinessException("dicItemJosn is error on insert");
			}
			if(Check.NuNStr(templateEntity.getPfid())){
				//直接保存信息
				cityTemplateService.insertTemplate(templateEntity);
			}else{
				//复制原来的模板信息
				cityTemplateService.insertTemplateAndCopyIntoDicItem(templateEntity.getTemplateName(), templateEntity.getPfid(), templateEntity.getCreateFid());
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 插入字典值信息
	 * @param dicItemJosn
	 * @return
	 */
	public String insertDicItem(String dicItemJosn){
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNObj(dicItemJosn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			DicItemEntity dicItemEntity = JsonEntityTransform.json2Object(dicItemJosn,DicItemEntity.class);
			if (Check.NuNObj(dicItemEntity)){
				LogUtil.info(LOGGER,"dicItemJosn is error on insert");
				throw  new BusinessException("dicItemJosn is error on insert");
			}
			if(Check.NuNStr(dicItemEntity.getDicCode()) || Check.NuNStr(dicItemEntity.getTemplateFid())){
				//参数异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//直接插入一条字典值信息
			confDicService.insertDicItem(dicItemEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 插入字典信息
	 * @param confDicJosn
	 * @return
	 */
	public String insertConfDic(String confDicJosn){
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNObj(confDicJosn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			ConfDicEntity confDicEntity = JsonEntityTransform.json2Object(confDicJosn,ConfDicEntity.class);
			if (Check.NuNObj(confDicEntity)){
				LogUtil.info(LOGGER,"confDicJosn is error on insert");
				throw  new BusinessException("confDicJosn is error on insert");
			}
			if(!confDicService.checkCode(confDicEntity.getDicCode(),confDicEntity.getDicLevel())){
				//code异常
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.CODE_ERROR));
				return dto.toJsonString();
			}
			//直接插入一条字典信息
			confDicService.insertConfDic(confDicEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 更新字典值信息
	 * @param dicItemJosn
	 * @return
	 */
	public String updateDicItem(String dicItemJosn){
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNObj(dicItemJosn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//更新
			DicItemEntity dicItemEntity = JsonEntityTransform.json2Object(dicItemJosn,DicItemEntity.class);
			String dicCode =dicItemEntity.getDicCode();
			dicItemEntity.setDicCode(null);
			dicItemEntity.setTemplateFid(null);
			confDicService.updateDicItemByFid(dicItemEntity);
			
			try {
				if (!Check.NuNStr(dicCode)) {
					String key = RedisKeyConst.getConfigKey(null, dicCode);
					redisOperations.del(key);
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis delete key{},error:{}",dicCode, e);
			}
			
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 更新字典信息
	 * @param confDicJosn
	 * @return
	 */
	public String updateConfDicByFid(String confDicJosn){
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNObj(confDicJosn)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//更新
			ConfDicEntity confDicEntity = JsonEntityTransform.json2Object(confDicJosn,ConfDicEntity.class);

			if(confDicEntity != null && confDicEntity.getDicCode() !=null){
				if(!confDicService.checkCode(confDicEntity.getDicCode(),0)){
					//当前code不能修改
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.CODE_ERROR));
					return dto.toJsonString();
				}
			}
			confDicService.updateConfDicByFid(confDicEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 获取字典值列表
	 * @param code
	 * @param templateFid
	 * @return
	 */
	public String getDicItemListByCodeAndTemplate(String code,String templateFid){
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNStr(code)  || Check.NuNStr(templateFid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			dto.putValue("list", confDicService.getDicItemListByCodeAndTemplate(code, templateFid));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}


	/**
	 * 获取当前城市的模板信息
	 * @param cityCode
	 * @return
	 */
	public String getCityTemplateByCityCode(String cityCode){

		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNObj(cityCode)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			dto.putValue("info", cityTemplateService.getCityTemplateByCityCode(cityCode));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}


	/**
	 * 获取当前城市的模板信息
	 * @param paramJson
	 * @return
	 */
	public String insertCityTemplate(String paramJson){

		DataTransferObject dto = new DataTransferObject();
		try{
			CityTemplateEntity cityTemplateEntity = JsonEntityTransform.json2Object(paramJson, CityTemplateEntity.class);

			//非空校验
			if(Check.NuNObj(cityTemplateEntity)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNObj(cityTemplateEntity.getCityCode()) || Check.NuNObj(cityTemplateEntity.getTemplateFid()) ){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//直接保存信息
			cityTemplateService.insertCityTemplate(cityTemplateEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}


	/**
	 * 获取当前字典信息的列表
	 * @return
	 */
	public String getConfDicByFid(String fid){

		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNObj(fid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			dto.putValue("info", confDicService.getConfDicByFid(fid));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}

	/**
	 * 获取当前字典信息的列表
	 * @return
	 */
	public String getConfDicByPfid(String pfid){

		DataTransferObject dto = new DataTransferObject();
		try{
			dto.putValue("list",confDicService.getConfDicByPfid(pfid));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}

	/**
	 * 获取当前的模板列表
	 * @return
	 */
	public String  getTemplateListByPage(String paramJson){
		DataTransferObject dto = new DataTransferObject();
		PageRequest pageRequest = JsonEntityTransform.json2Object(paramJson, PageRequest.class);
		try{
			PagingResult<TemplateEntityVo> pagingResult = cityTemplateService.getTemplateListByPage(pageRequest);
			dto.putValue("list", pagingResult.getRows());
			dto.putValue("total", pagingResult.getTotal());
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 获取字典结构
	 * @return
	 */
	public String getDicTree(String line) {
		DataTransferObject dto = new DataTransferObject();

		try{
			dto.putValue("list", cityTemplateService.getDicTree(line));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.CityTemplateService#countItemNumList(java.lang.String, java.lang.String)
	 */
	@Override
	public String countItemNumList(String templateFid, String dicCode) {
		DataTransferObject dto = new DataTransferObject();
		try{
			long countRes = confDicService.countItemCodeNum(templateFid, dicCode);
			dto.putValue("countRes", JsonEntityTransform.Object2Json(countRes));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.CityTemplateService#getSelectEnum(java.lang.String, java.lang.String)
	 */
	@Override
	public String getSelectEnum(String cityCode, String dicCode) {
		DataTransferObject dto = new DataTransferObject();
		try{
			String key = RedisKeyConst.getConfigKey(cityCode, dicCode);
			String listJson= null;
			try {
				listJson=redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

			//判断缓存是否存在
			if(StringUtils.isBlank(listJson)){
				List<EnumVo> list=confDicService.selectEnumList(cityCode, dicCode);
				listJson=JsonEntityTransform.Object2Json(list);
				if(!Check.NuNCollection(list)){
					try {
						redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, listJson);
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}

				}
			}
			dto.putValue("selectEnum", JsonEntityTransform.json2ObjectList(listJson, EnumVo.class));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 查询有效的字典值
	 * @author lishaochuan
	 * @create 2016年5月31日下午8:51:53
	 * @param cityCode
	 * @param dicCode
	 * @return
	 */
	@Override
	public String getEffectiveSelectEnum(String cityCode, String dicCode) {
		DataTransferObject dto = new DataTransferObject();
		
		List<EnumVo>  list = getEnumVo(cityCode, dicCode,true);
		dto.putValue("selectEnum", list);
		return dto.toJsonString();
	}
	
	@Override
	public String getEffectiveSelectEnum(String cityCode, String dicCode,boolean fromCache) {
		DataTransferObject dto = new DataTransferObject();
		
		List<EnumVo>  list = getEnumVo(cityCode, dicCode,fromCache);
		dto.putValue("selectEnum", list);
		return dto.toJsonString();
	}

	
	/**
	 *  获取 当前陪配置属性
	 * @param cityCode
	 * @param dicCode
	 * @return
	 */
	private List<EnumVo>  getEnumVo(String cityCode, String dicCode,boolean fromCache){
		
		List<EnumVo> list  = null;
		try {
			String key = RedisKeyConst.getConfigKey("effective", cityCode, dicCode);
			String listJson = null;
			if(fromCache){				
				try {
					listJson=redisOperations.get(key);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误,e:{}",e);
				}
			}
			// 判断缓存是否存在
			if (StringUtils.isBlank(listJson)) {
				list = confDicService.selectEffectiveEnumList(cityCode, dicCode);
				if(!Check.NuNCollection(list)){
					listJson = JsonEntityTransform.Object2Json(list);
					if (!Check.NuNCollection(list)) {
						try {
							redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, listJson);
						} catch (Exception e) {
	                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
						}
						
					}
				}
			}
			list = JsonEntityTransform.json2ObjectList(listJson, EnumVo.class);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取配置项异常:{}，cityCode={}，dicCode={}", e,cityCode,dicCode);
		}
		return list;
		
	}
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.CityTemplateService#getTextValue(java.lang.String, java.lang.String)
	 */
	@Override
	public String getTextValue(String cityCode, String dicCode) {
		DataTransferObject dto = new DataTransferObject();
		try{
			StringBuffer key=new StringBuffer();
			key.append(RedisKeyConst.CONF_KEY_PREFIX);
			if(!Check.NuNStr(cityCode)){
				key.append(cityCode);
			}
			key.append(dicCode);
			String valueJson= null;
			try {
				valueJson=redisOperations.get(key.toString());
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			//判断缓存是否存在
			if(StringUtils.isBlank(valueJson)){
				List<String> list=confDicService.getTextValue(cityCode, dicCode);
				if(!Check.NuNCollection(list)){
					valueJson=list.get(0);
					try {
						redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, valueJson);
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
				}
			}
			dto.putValue("textValue", valueJson);
		}catch (Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 查询配置项唯一值
	 * @author afi
	 * @created 2016年4月21日
	 * @param cityCode
	 * @param dicCodes  多个用，隔开
	 * @return
	 */
	@Override
	public String getTextListByCodes(String cityCode,String dicCodes){
		DataTransferObject dto = new DataTransferObject();
		try{
			String key = RedisKeyConst.getConfigKey(cityCode, dicCodes);
			String valueJson =  null;
			try {
				 valueJson = redisOperations.get(key);
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			//判断缓存是否存在
			if(StringUtils.isBlank(valueJson)){
				List<MinsuEleEntity> confList = new ArrayList<>();
				//将字符转分割
				String[] strarray = dicCodes.split(BaseDataConstant.CODE_SPLIT);
				for (int i = 0; i < strarray.length; i++){
					String dicCode = strarray[i];
					MinsuEleEntity conf = confDicService.getTextValueByCode(cityCode, dicCode);
					confList.add(conf);
				}
				dto.putValue("confList", confList);
				if(!Check.NuNCollection(confList)){
					
					try {
						redisOperations.setex(key, RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
				}
			}else {
				return valueJson;
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}

	/**
	 * 获取 强制取消房东无责任时限、罚金交付时限、罚金天数
	 * @author lishaochuan
	 * @create 2016年5月3日
	 * @return
	 */
	@Override
	public String getConfigForceVo(){
		DataTransferObject dto = new DataTransferObject();
		try{
			List<String> codeList = new ArrayList<>();
			codeList.add(TradeRulesEnum.TradeRulesEnum0010.getValue());
			codeList.add(TradeRulesEnum.TradeRulesEnum0011.getValue());
			codeList.add(TradeRulesEnum.TradeRulesEnum0013.getValue());
			String confJson =  this.getTextListByCodes(null,ValueUtil.transList2Str(codeList));
			DataTransferObject cinfDto = JsonEntityTransform.json2DataTransferObject(confJson);
			List<MinsuEleEntity> confList = null;
			if(cinfDto.getCode() == DataTransferObject.SUCCESS){
				confList = cinfDto.parseData("confList", new TypeReference<List<MinsuEleEntity>>() {
				});
			}else {
				dto.setErrCode(cinfDto.getCode());
				dto.setMsg(cinfDto.getMsg());
				return dto.toJsonString();
			}

			if(Check.NuNCollection(confList) || confList.size() != codeList.size()){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("punish config is error");
				return dto.toJsonString();
			}
			String limitDayStr = "";
			String punishDayStr = "";
			String tillDayStr = "";
			for(MinsuEleEntity ele: confList){
				if(ele.getEleKey().equals(TradeRulesEnum.TradeRulesEnum0010.getValue())){
					limitDayStr = ele.getEleValue();
				}else if(ele.getEleKey().equals(TradeRulesEnum.TradeRulesEnum0011.getValue())){
					tillDayStr = ele.getEleValue();
				}else if(ele.getEleKey().equals(TradeRulesEnum.TradeRulesEnum0013.getValue())){
					punishDayStr = ele.getEleValue();
				}
			}
			if(Check.NuNStr(limitDayStr) || Check.NuNStr(punishDayStr)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("punish config is error");
				return dto.toJsonString();
			}
			int limitDay = ValueUtil.getintValue(limitDayStr);
			int punishDay = ValueUtil.getintValue(punishDayStr);
			int tillDay = ValueUtil.getintValue(tillDayStr);

			ConfigForceVo configForceVo = new ConfigForceVo();
			configForceVo.setLimitDay(limitDay);
			configForceVo.setPunishDay(punishDay);
			configForceVo.setTillDay(tillDay);

			dto.putValue("configForceVo", configForceVo);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}



	/**
	 * 查询配置项唯一值 前缀匹配
	 * @author afi
	 * @created 2016年4月21日
	 * @param cityCode
	 * @param dicCodes  多个用，隔开
	 * @return
	 */
	@Override
	public String getTextListByLikeCodes(String cityCode,String dicCodes){
		DataTransferObject dto = new DataTransferObject();
		try{
			List<MinsuEleEntity> confList = new ArrayList<>();
			//将字符转分割
			String[] strarray = dicCodes.split(BaseDataConstant.CODE_SPLIT);
			for (int i = 0; i < strarray.length; i++){
				String dicCode = strarray[i];
				List<MinsuEleEntity> list = confDicService.getListByLike(cityCode, dicCode);
				confList.addAll(list);
			}
			dto.putValue("confList", confList);
		}catch (Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.CityTemplateService#getSelectSubDic(java.lang.String)
	 */
	@Override
	public String getSelectSubDic(String cityCode,String dicCode) {
		DataTransferObject dto = new DataTransferObject();
		try{
			StringBuffer key=new StringBuffer();
			key.append(RedisKeyConst.CONF_KEY_PREFIX);
			if(!Check.NuNStr(cityCode)){
				key.append(cityCode);
			}
			key.append(dicCode);
			String listJson= null;
			try {
				 listJson=redisOperations.get(key.toString());
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			//判断缓存是否存在
			if(StringUtils.isBlank(listJson)){
				List<EnumVo> list=confDicService.getEnumDicList(dicCode,cityCode);
				listJson=JsonEntityTransform.Object2Json(list);
				if(!Check.NuNCollection(list)){
					try {
						redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, listJson);
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
					
				}
			}
			dto.putValue("subDic", JsonEntityTransform.json2ObjectList(listJson, EnumVo.class));
		}catch (Exception e){
			LogUtil.error(LOGGER,"e:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 获取房源照片 标准
	 *
	 * @author yd
	 * @created 2016年10月19日 下午7:33:16
	 *
	 * @return
	 */
	@Override
	public String getPicValidParams() {
		DataTransferObject dto = new DataTransferObject();
		Map<String, Integer> validMap = new HashMap<String, Integer>();
		Map<String, EnumVo> ruleMap = new HashMap<String, EnumVo>();
		
		//获取图片 当前标准
		List<EnumVo> minPixelVoList =  getEnumVo(null, ProductRulesEnum0017Enum.ProductRulesEnum0017001.getValue(),true);
		List<EnumVo> minDpiVoList = getEnumVo(null, ProductRulesEnum0017Enum.ProductRulesEnum0017002.getValue(),true);
		List<EnumVo> picScaleVoList = getEnumVo(null, ProductRulesEnum0017Enum.ProductRulesEnum0017003.getValue(),true);
		List<EnumVo> maxSizeVoList = getEnumVo(null, ProductRulesEnum0017Enum.ProductRulesEnum0017004.getValue(),true);
		
		if(Check.NuNCollection(minPixelVoList) || minPixelVoList.size() > 1){
			LogUtil.error(LOGGER, "[照片规则]最小像素属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]最小像素属性值错误");
			return dto.toJsonString();
		} 
		
		if (Check.NuNCollection(minDpiVoList) || minDpiVoList.size() > 1) {
			LogUtil.error(LOGGER, "[照片规则]最小分辨率属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]最小分辨率属性值错误");
			return dto.toJsonString();
		} 
		

		if(Check.NuNCollection(picScaleVoList) || picScaleVoList.size() > 1){
			LogUtil.error(LOGGER, "[照片规则]照片比例属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]照片比例属性值错误");
			return dto.toJsonString();
		}
		
		
		if(Check.NuNCollection(maxSizeVoList) || maxSizeVoList.size() > 1){
			LogUtil.error(LOGGER, "[照片规则]照片最大上传属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]照片最大上传属性值错误");
			return dto.toJsonString();
		} 
		
		//获取当前配置项 属性值
		String minPixelStr = minPixelVoList.get(0).getKey();
		String minDpiStr = minDpiVoList.get(0).getKey();
		String picScaleStr = picScaleVoList.get(0).getKey();
		String maxSizeStr = maxSizeVoList.get(0).getKey();
		
		if(Check.NuNStr(minPixelStr) || (!ZERO_STRING.equals(minPixelStr) && minPixelStr.indexOf("*") == -1)){
			LogUtil.error(LOGGER, "[照片规则]最小像素属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]最小像素属性值错误");
			return dto.toJsonString();
		}

		if(Check.NuNStr(minDpiStr)){
			LogUtil.error(LOGGER, "[照片规则]最小分辨率属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]最小分辨率属性值错误");
			return dto.toJsonString();
		}
		if(Check.NuNStr(picScaleStr) || (!ZERO_STRING.equals(picScaleStr) && picScaleStr.indexOf(":") == -1)){
			LogUtil.error(LOGGER, "[照片规则]照片比例属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]照片比例属性值错误");
			return dto.toJsonString();
		}
		
		if(Check.NuNStr(maxSizeStr)){
			LogUtil.error(LOGGER, "[照片规则]照片最大上传属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]照片最大上传属性值错误");
			return dto.toJsonString();
		}
		
		
		if(!ZERO_STRING.equals(minPixelStr)){// 属性值为0表示不限制
			String[] pixelArray = minPixelStr.split("\\*");
			Integer minPixel = Integer.parseInt(pixelArray[0]) * Integer.parseInt(pixelArray[1]);
			validMap.put("minPixel", minPixel);
			validMap.put("widthPixel",Integer.parseInt(pixelArray[0]));
			validMap.put("heightPixel",Integer.parseInt(pixelArray[1]));
			ruleMap.put("minPixel", minPixelVoList.get(0));
		}
		
		if(!ZERO_STRING.equals(minDpiStr)){// 属性值为0表示不限制
			Integer minDpi = Integer.valueOf(minDpiStr); 
			validMap.put("minDpi", minDpi);
			ruleMap.put("minDpi", minDpiVoList.get(0));
		}
		
		if(!ZERO_STRING.equals(picScaleStr)){// 属性值为0表示不限制
			String[] scaleArray = picScaleStr.split(":");
			Integer widthScale = Integer.valueOf(scaleArray[0]); 
			Integer heightScale = Integer.valueOf(scaleArray[1]); 
			validMap.put("widthScale", widthScale);
			validMap.put("heightScale", heightScale);
			ruleMap.put("picScale", picScaleVoList.get(0));
		}
		
		if(!ZERO_STRING.equals(maxSizeStr)){// 属性值为0表示不限制
			Integer maxSize = Integer.valueOf(maxSizeStr); 
			validMap.put("maxSize", maxSize);
			ruleMap.put("maxSize", maxSizeVoList.get(0));
		}
		
		dto.putValue("validMap", validMap);
		dto.putValue("ruleMap", ruleMap);
		return dto.toJsonString();
	}

	/**
	 * 更新配置项列表
	 */
	@Override
	public String updateDicItemList(String paramJson) {
		List<DicItemEntity> itemList = JsonEntityTransform.json2List(paramJson, DicItemEntity.class);
		DataTransferObject dto = new DataTransferObject();
		
		Set<String> codeSet = new HashSet<>();
		for (DicItemEntity dicItemEntity : itemList) {
			if (Check.NuNStr(dicItemEntity.getFid())) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			codeSet.add(dicItemEntity.getDicCode());
		}
		
		try {
			confDicService.updateDicItemList(itemList);
			//清除通用模板缓存
			this.clearRedisCache(codeSet);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updateDicItemList error:{}, paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String listZrpPayStyle(String paramJson) {
		LogUtil.info(LOGGER, "【listZrpPayStyle】参数={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		ZrpPayRequest zrpPayRequest = JsonEntityTransform.json2Object(paramJson, ZrpPayRequest.class);
		if (Check.NuNObj(zrpPayRequest.getRentType())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租类型为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(zrpPayRequest.getRentTime())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租时间为空");
			return dto.toJsonString();
		}
		Integer rentType = zrpPayRequest.getRentType();
		Integer rentTime = zrpPayRequest.getRentTime();
		ContractTradingEnum003 tradingEnum = ContractTradingEnum003.getByCode(rentType.intValue());
		if (Check.NuNObj(tradingEnum)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租类型错误");
			return dto.toJsonString();
		}

		List<ConfValueVo> list = new ArrayList<>();
		dto.putValue("list", list);
		String dicCode = tradingEnum.getValue();
		//日租
		if (ContractTradingEnum003.ContractTradingEnum003001.getCode() == rentType.intValue()) {
			ConfValueVo vo = new ConfValueVo();
			vo.setName(ContractTradingEnum002.ContractTradingEnum002005.getName());
			vo.setCode(ContractTradingEnum002.ContractTradingEnum002005.getCode());
            vo.setValue("1");
            list.add(vo);
			return dto.toJsonString();
		}
		//月租
		if (ContractTradingEnum003.ContractTradingEnum003002.getCode() == rentType.intValue()) {
			//如果是月租需要判断是否是三个月以上或以下
			List<String> timeLimitList = confDicService.getTextValueForCommon(ServiceLineEnum.ZRP.getTemplate(), ContractTradingEnum.ContractTradingEnum001.getValue());
			if (Check.NuNCollection(timeLimitList)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("月租时间限制错误");
				return dto.toJsonString();
			}
			String timeLimit = timeLimitList.get(0);
			int limit = Integer.parseInt(timeLimit);
            if (rentTime <= limit) {
                //规定期限下只能选择一次性付清
				ConfValueVo vo = new ConfValueVo();
				vo.setName(ContractTradingEnum002.ContractTradingEnum002005.getName());
				vo.setCode(ContractTradingEnum002.ContractTradingEnum002005.getCode());
                vo.setValue("1");
                list.add(vo);
				return dto.toJsonString();
			}
		}

		List<EnumVo> enumList = confDicService.listEnumDicForCommon(ServiceLineEnum.ZRP.getTemplate(), dicCode);
		for (EnumVo enumVo : enumList) {
			ConfValueVo vo = new ConfValueVo();
			vo.setName(enumVo.getText());
			vo.setCode(ContractTradingEnum002.getByValue(enumVo.getKey()).getCode());
			List<String> valueList = confDicService.getTextValueForCommon(ServiceLineEnum.ZRP.getTemplate(), enumVo.getKey());
			if (!Check.NuNCollection(valueList)) {
				vo.setValue(valueList.get(0));
			}
			list.add(vo);
		}
		LogUtil.info(LOGGER, "【listZrpPayStyle】返回结果={}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String getTextValueForCommon(String serviceLine, String dicCode) {

		DataTransferObject dto = new DataTransferObject();
		ServiceLineEnum serviceLineEnum = ServiceLineEnum.getEnumByCode(serviceLine);
		String templateFid = serviceLineEnum.getTemplate();
		try {
			StringBuffer key = new StringBuffer();
			key.append(RedisKeyConst.COMMON_KEY_CONF);
            key.append(":");
            key.append(serviceLine);
            key.append(":");
            key.append(dicCode);
			String valueJson = null;
			try {
				valueJson = redisOperations.get(key.toString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			//判断缓存是否存在
			if (StringUtils.isBlank(valueJson)) {
				List<String> list = confDicService.getTextValueForCommon(templateFid, dicCode);
				if (!Check.NuNCollection(list)) {
					valueJson = list.get(0);
					try {
						redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, valueJson);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误,e:{}", e);
					}
				}
			}
			dto.putValue("textValue", valueJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	@Override
	public String listTextValueForCommon(String serviceLine, String dicCode) {
		DataTransferObject dto = new DataTransferObject();
		ServiceLineEnum serviceLineEnum = ServiceLineEnum.getEnumByCode(serviceLine);
		String templateFid = serviceLineEnum.getTemplate();
		try {
			StringBuffer key = new StringBuffer();
			key.append(RedisKeyConst.COMMON_KEY_CONF);
            key.append(":");
            key.append(serviceLine);
            key.append(":");
            key.append(dicCode);
			String valueJson = null;
			try {
				valueJson = redisOperations.get(key.toString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			//判断缓存是否存在
			if (StringUtils.isBlank(valueJson)) {
				List<String> list = confDicService.getTextValueForCommon(templateFid, dicCode);
				if (!Check.NuNCollection(list)) {
					valueJson = JsonEntityTransform.Object2Json(list);
					try {
						redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, valueJson);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误,e:{}", e);
					}
				}
			}
			List list = JsonEntityTransform.json2Object(valueJson, List.class);
			dto.putValue("listValue", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}


	/**
	 * 清除通用模板缓存
	 *
	 * @author liujun
	 * @created 2017年1月11日
	 *
	 * @param codeSet
	 */
	private void clearRedisCache(final Set<String> codeSet) {
		try {
			for (String dicCode : codeSet) {
				if (!Check.NuNStr(dicCode)) {
					String key1 = this.generateRedisKey(null, dicCode);			
					redisOperations.del(key1);
					
					String key2 = RedisKeyConst.getConfigKey(null, dicCode);
					redisOperations.del(key2);
				}
			}
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
	}

	/**
	 * 生成redis key
	 *
	 * @author liujun
	 * @created 2017年1月11日
	 *
	 * @param cityCode
	 * @param dicCode
	 * @return 
	 */
	private String generateRedisKey(String cityCode, String dicCode) {
		StringBuilder key = new StringBuilder();
		key.append(RedisKeyConst.CONF_KEY_PREFIX);
		if (!Check.NuNStr(cityCode)) {
			key.append(cityCode);
		}
		key.append(dicCode);
		return key.toString();
	}
	
}

