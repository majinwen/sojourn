/**
 * @FileName: TenantHouseService.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author bushujie
 * @created 2016年4月30日 下午4:59:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfTagService;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ConfTagVo;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.constant.CatConstant;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.*;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.logic.HouseCheckLogic;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.logic.ValidateResult;
import com.ziroom.minsu.services.house.service.*;
import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0012Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0022Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>房客端接口</p>
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
@Component("house.tenantHouseServiceProxy")
public class TenantHouseServiceProxy implements TenantHouseService{

	private static final Logger LOGGER = LoggerFactory.getLogger(TenantHouseServiceProxy.class);

	@Resource(name="house.messageSource")
	private MessageSource messageSource;

	@Resource(name="house.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Resource(name="house.houseCheckLogic")
	private HouseCheckLogic houseCheckLogic;

	@Resource(name="house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;

	@Resource(name="house.tenantHouseServiceImpl")
	private TenantHouseServiceImpl tenantHouseServiceImpl;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;
	
	@Resource(name="house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;
	
	@Autowired
	private RedisOperations redisOperations;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name = "house.houseTopServiceImpl")
	private HouseTopServiceImpl  houseTopServiceImpl;

	@Resource(name = "house.houseTagServiceImpl")
	private HouseTagServiceImpl  houseTagServiceImpl;

	@Resource(name="basedata.confTagService")
	private ConfTagService confTagService;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;

	@Value("#{'${list_small_pic}'.trim()}")
	private String list_small_pic;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	@Value("#{'${pic_size_1200_1200}'.trim()}")
	private String pic_size_1200_1200;
	
	@Value("#{'${pic_size_1200_1800}'.trim()}")
	private String pic_size_1200_1800;
	
	@Value("#{'${PLAY_VIDEO_URL}'.trim()}")
	private String PLAY_VIDEO_URL;
	@Override
	public String houseDetail(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseDetailDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseDetailDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		//查询房源操作
		try {
			HouseDetailDto houseDetailDto=validateResult.getResultObj();
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(houseDetailDto.getRentWay());
			if(Check.NuNObj(rentWayEnum)){
				LogUtil.error(LOGGER, "参数:{},出租方式错误:rentWayEnum={}",paramJson, rentWayEnum);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("出租方式错误");
				return dto.toJsonString();
			}

			HouseConfMsgEntity confMsgEntity = new HouseConfMsgEntity();
			//判断房子是否存在
			if(houseDetailDto.getRentWay()==0){
				if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, houseDetailDto.getFid())){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					return dto.toJsonString();
				}
				confMsgEntity.setHouseBaseFid(houseDetailDto.getFid());
			}else if(houseDetailDto.getRentWay()==1){
				if(!houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, houseDetailDto.getFid())){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
					return dto.toJsonString();
				}
				Object houseBaseFid = dto.getData().get("houseFid");
				confMsgEntity.setRoomFid(houseDetailDto.getFid());
				if(houseBaseFid != null){
					confMsgEntity.setHouseBaseFid(houseBaseFid.toString());
				}

			}
			TenantHouseDetailVo tenantHouseDetailVo=null;
			//判断出租方式
			if(houseDetailDto.getRentWay()==0){
				tenantHouseDetailVo=tenantHouseServiceImpl.getHouseDetail(houseDetailDto);
			}
			if(houseDetailDto.getRentWay()==1){
				/**yanb 调用方法修改 增加共享客厅逻辑*/
				tenantHouseDetailVo=tenantHouseServiceImpl.getHouseRoomDetail(houseDetailDto);
			}
			
			if (Check.NuNObj(tenantHouseDetailVo)) {
				LogUtil.info(LOGGER, "房源不存在，参数:{}",paramJson);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();				
			}
			
			//处理图片信息
			this.dealHouseDetailPic(tenantHouseDetailVo,houseDetailDto);
			//床单更换规则
			tenantHouseDetailVo.setSheetsReplaceRulesName(SheetReplaceEnum.getEnumMap().get(tenantHouseDetailVo.getSheetsReplaceRulesValue()));
			//下单类型
			tenantHouseDetailVo.setOrderTypeName(OrderTypeEnum.getEnumMap().get(tenantHouseDetailVo.getOrderType()));
			//房源押金规则值
			HouseConfMsgEntity depositCon = this.houseIssueServiceImpl.findHouseDepositConfByHouseFid(confMsgEntity.getHouseBaseFid(),confMsgEntity.getRoomFid(),houseDetailDto.getRentWay(),dto);
			tenantHouseDetailVo.setDepositRulesCode(depositCon.getDicCode());
			if (!Check.NuNStr(depositCon.getDicVal())) {				
				tenantHouseDetailVo.setDepositRulesName(DataFormat.formatHundredPriceInt(Integer.valueOf(depositCon.getDicVal()))+"元");
				tenantHouseDetailVo.setDepositRulesValue(DataFormat.formatHundredPriceInt(Integer.valueOf(depositCon.getDicVal())));
			}else{
				tenantHouseDetailVo.setDepositRulesName("0元");
				tenantHouseDetailVo.setDepositRulesValue("0");
			}

			//退订政策
			if(!Check.NuNObj(TradeRulesEnum005Enum.getEnumByValue(tenantHouseDetailVo.getCheckOutRulesCode())) ){
				tenantHouseDetailVo.setCheckOutRulesName(TradeRulesEnum005Enum.getEnumByValue(tenantHouseDetailVo.getCheckOutRulesCode()).getName());
			}
			//折扣规则
			ProductRulesEnum0012Enum[] ps=ProductRulesEnum0012Enum.values();
			for(ProductRulesEnum0012Enum p:ps){
				String codeValue=tenantHouseServiceImpl.getHouseDepositRulesValue(houseDetailDto.getFid(), houseDetailDto.getRentWay(), p.getValue());
				if(!Check.NuNStr(codeValue)){
					BigDecimal bigVal=new BigDecimal(codeValue);
					BigDecimal bigFloat=bigVal.multiply(new BigDecimal("100"));
					tenantHouseDetailVo.getDiscountMsg().add(p.getName()+bigFloat.floatValue()+"%");
				}
			}
			//查询房源或者房间的床位信息
			tenantHouseDetailVo.setBedList(tenantHouseServiceImpl.getBedNumByHouseFid(houseDetailDto.getFid(), houseDetailDto.getRentWay()));
			//查询房源配套设施信息
			tenantHouseDetailVo.setFacilityList(tenantHouseServiceImpl.findHouseConfListByCode(houseDetailDto.getFid(), houseDetailDto.getRentWay(), ProductRulesEnum.ProductRulesEnum002.getValue()));
			//服务
			tenantHouseDetailVo.setServeList(tenantHouseServiceImpl.findHouseConfListByCode(houseDetailDto.getFid(), houseDetailDto.getRentWay(), ProductRulesEnum.ProductRulesEnum0015.getValue()));
			//替换当前房源的特殊价格 
			this.dealHousePrice(tenantHouseDetailVo,houseDetailDto);
			//填充房源折扣 以及长租天数
			fillHouseDiscount(confMsgEntity, tenantHouseDetailVo);	
			
			//填充房屋守则(修改)
			fillHouseRules(tenantHouseDetailVo,confMsgEntity.getHouseBaseFid(),rentWayEnum.getCode(),confMsgEntity.getRoomFid());
			
			//填充TOP50房源特性
			fillHouseTopInfo(tenantHouseDetailVo,confMsgEntity.getHouseBaseFid());

			tenantHouseDetailVo.setHouseDesc(StringUtils.filterSpecialCharacter(tenantHouseDetailVo.getHouseDesc(), StringUtils.specialKey));
			
			dto.putValue("houseDetail", tenantHouseDetailVo);
			LogUtil.info(LOGGER,"返回的");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 填充房屋守则
	 *
	 * @author baiwei
	 * @created 2017年4月20日 下午9:10:38
	 *
	 * @param tenantHouseDetailVo
	 * @param houseBaseFid
	 */
	private void fillHouseRules(TenantHouseDetailVo tenantHouseDetailVo,String houseBaseFid,int rentWay,String roomFid){
		if(!Check.NuNObj(tenantHouseDetailVo) && !Check.NuNStr(houseBaseFid)){
			StringBuilder sb = new StringBuilder();
			//查询自定义守则
			String houseRules = tenantHouseDetailVo.getHouseRules();
			// 查询数据库中房屋可选守则列表
			List<HouseConfMsgEntity> listHouseRules = null;
			try {
				HouseConfParamsDto paramsDto = new HouseConfParamsDto();
				paramsDto.setDicCode(ProductRulesEnum.ProductRulesEnum0024.getValue());
				paramsDto.setHouseBaseFid(houseBaseFid);
				paramsDto.setRentWay(rentWay);
				if(rentWay==RentWayEnum.ROOM.getCode()){
					paramsDto.setRoomFid(roomFid);
				}
				listHouseRules = houseIssueServiceImpl.findHouseConfValidList( paramsDto);
				
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查询房屋可选守则异常e={}", e);
			}
			if(!Check.NuNCollection(listHouseRules)){
                DataTransferObject enum0024EnumDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0024.getValue()));
                if (enum0024EnumDto.getCode() == DataTransferObject.SUCCESS) {
                    List<EnumVo> selectEnum = enum0024EnumDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {
                    });
                    for (int i = 0; i < listHouseRules.size(); i++) {
                        for (int j = 0; j < selectEnum.size(); j++) {
                            if (listHouseRules.get(i).getDicVal().equals(selectEnum.get(j).getKey())) {
                                sb.append(selectEnum.get(j).getText());
                                sb.append("\n");
                            }
                        }
                    }
                }
			}
			if(!Check.NuNStr(houseRules)){
				sb.append(houseRules);
			}
			tenantHouseDetailVo.setHouseRules(sb.toString());
		}
	}
	
	/**
	 * 
	 * 填充TOP50房源特性
	 * 说明：
	 * 1. 查询top50 房源特性
	 * 2. 查询top50 标签
	 * 3. 条目处理
	 *  A.如果是图片，地址需要拼接
	 * 
	 *
	 * @author yd
	 * @created 2017年3月17日 下午8:49:49
	 *
	 * @param tenantHouseDetailVo
	 */
	private  void 	fillHouseTopInfo(TenantHouseDetailVo tenantHouseDetailVo,String houseBaseFid){

		if(Check.NuNObj(tenantHouseDetailVo)
				||Check.NuNStr(houseBaseFid)) return ;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("houseBaseFid",houseBaseFid);

		HouseTopInfoVo houseTopInfoVo	 = tenantHouseDetailVo.getHouseTopInfoVo();
		try {

			HouseTopVo  houseTopVo = this.houseTopServiceImpl.findHouseTopVoByHouse(paramMap);
			if(houseTopVo ==null){
				LogUtil.info(LOGGER, "【top50房源查询条目信息不存在】：查询参数params={}",JsonTransform.Object2Json(paramMap));
				return ;
			}
			BeanUtils.copyProperties(houseTopVo, houseTopInfoVo);

			//查询top50 房源标签
			paramMap.put("tagType", ProductRulesEnum0022Enum.TAG_TOP50_HOUSE.getValue());

			List<String> tagFids = this.houseTagServiceImpl.findHouseTagByParams(paramMap);

			if(!Check.NuNCollection(tagFids)){
				ConfTagRequest params  = new ConfTagRequest();
				params.setFids(tagFids);
				params.setIsValid(IsValidEnum.WEEK_OPEN.getCode());
				DataTransferObject dto = JsonTransform.json2DataTransferObject(this.confTagService.findByConfTagRequestList(JsonTransform.Object2Json(params)));
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "【填充TOP50房源特性-查询top50标签】msg={}", dto.getMsg());
					return ;
				}

				List<ConfTagVo> listConfTagVo = dto.parseData("list", new TypeReference<List<ConfTagVo>>() {
				});
				if(!Check.NuNCollection(listConfTagVo)){
					List<LabelTipsEntity> labelTipsTopList = houseTopInfoVo.getLabelTipsTopList();
					for (ConfTagVo confTagVo : listConfTagVo) {
						LabelTipsEntity labelTipsEntity = new LabelTipsEntity();
						labelTipsEntity.setName(confTagVo.getTagName());
						labelTipsEntity.setTipsType(String.valueOf(confTagVo.getTagType()));
						labelTipsTopList.add(labelTipsEntity);
					}
				}
			}

			//top50房源条目处理
			List<HouseTopColumnEntity> houseTopColumnList = houseTopVo.getHouseTopColumnList();
			if(!Check.NuNCollection(houseTopColumnList)){
				List<HouseTopColumnVo> houseTopColumnVoList  = houseTopInfoVo.getHouseTopColumnVoList();
				
				StringBuffer shareContent = new StringBuffer("");
				for (HouseTopColumnEntity houseTopColumnEntity : houseTopColumnList) {
					HouseTopColumnVo houseTopColumnVo = new HouseTopColumnVo();
					BeanUtils.copyProperties(houseTopColumnEntity, houseTopColumnVo);
					if(houseTopColumnEntity.getColumnType() == ColumnTypeEnum.Column_Type_301.getValue()
							||houseTopColumnEntity.getColumnType() == ColumnTypeEnum.Column_Type_401.getValue()
							||houseTopColumnEntity.getColumnType() == ColumnTypeEnum.Column_Type_302.getValue()){
						String 	picUrl = PicUtil.getFullPic(picBaseAddrMona, houseTopColumnEntity.getPicBaseUrl(), houseTopColumnEntity.getPicSuffix(),detail_big_pic);//1200*800图
						houseTopColumnVo.setPicUrl(picUrl);
						if(houseTopColumnEntity.getColumnType() == ColumnTypeEnum.Column_Type_401.getValue()){
							houseTopColumnVo.setVideoUrl(houseTopColumnVo.getColumnContent());
							houseTopColumnVo.setPlayVideoUrl(PLAY_VIDEO_URL+houseTopColumnVo.getColumnContent());
						}
						//头图处理
						if(houseTopColumnEntity.getColumnType() == ColumnTypeEnum.Column_Type_302.getValue()){
							picUrl = PicUtil.getFullPic(picBaseAddrMona, houseTopColumnEntity.getPicBaseUrl(), houseTopColumnEntity.getPicSuffix(),pic_size_1200_1800);
							houseTopInfoVo.setTopHeadImg(picUrl);
						}
					}
					if(houseTopColumnEntity.getColumnType() == ColumnTypeEnum.Column_Type_501.getValue()){
						houseTopColumnVo.setAudioUrl(houseTopColumnVo.getColumnContent());
					}
					if(houseTopColumnEntity.getColumnType() == ColumnTypeEnum.Column_Type_201.getValue()){
						shareContent.append(houseTopColumnVo.getColumnContent()+" ");
					}
					
					if(!Check.NuNObj(houseTopColumnEntity.getWidth()) && !Check.NuNObj(houseTopColumnEntity.getHight()) && houseTopColumnEntity.getWidth()>0 && houseTopColumnEntity.getHight()>0){
						houseTopColumnVo.setImageAspectratio(BigDecimalUtil.div(houseTopColumnEntity.getWidth(), houseTopColumnEntity.getHight(), 2));
					}
					
					houseTopColumnVoList.add(houseTopColumnVo);
				}
				
				houseTopInfoVo.setShareContent(StringUtils.filterSpecialCharacter(shareContent.toString(), StringUtils.specialKey));
			}
			if(!Check.NuNStr(houseTopInfoVo.getTopMiddlePic())){
				houseTopInfoVo.setTopMiddlePic(PicUtil.getFullPic(picBaseAddrMona,houseTopInfoVo.getTopMiddlePic(),detail_big_pic));
			}
			if(!Check.NuNStr(houseTopInfoVo.getTopTitlePic())){
				houseTopInfoVo.setTopTitlePic(PicUtil.getFullPic(picBaseAddrMona,houseTopInfoVo.getTopTitlePic(),detail_big_pic));
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【填充TOP50房源特性失败】paramMap={},e={}", JsonTransform.Object2Json(paramMap),e);
		}






	}

	/**
	 * 
	 * 填充房源折扣
	 *
	 * @author yd
	 * @created 2016年12月7日 下午8:08:57
	 *
	 * @param confMsgEntity
	 * @param tenantHouseDetailVo
	 */
	private void fillHouseDiscount(HouseConfMsgEntity confMsgEntity ,TenantHouseDetailVo tenantHouseDetailVo){

		if(!Check.NuNObj(confMsgEntity)
				&&!Check.NuNObj(tenantHouseDetailVo)){
			confMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			confMsgEntity.setDicCode(ProductRulesEnum.ProductRulesEnum0019.getValue());
			List<HouseConfMsgEntity>  list = houseIssueServiceImpl.findGapAndFlexPrice(confMsgEntity);
			if(!Check.NuNCollection(list)){
				List<HouseConfVo> listHouseDiscount = new LinkedList<HouseConfVo>();
				for (HouseConfMsgEntity houseConfMsgEntity : list) {
					HouseConfVo houseConfVo = new HouseConfVo();
					houseConfVo.setDicCode(houseConfMsgEntity.getDicCode());
					houseConfVo.setDicValue(houseConfMsgEntity.getDicVal());
					houseConfVo.setFid(houseConfMsgEntity.getFid());
					ProductRulesEnum0019 productRulesEnum0019 = ProductRulesEnum0019.getEnumByCode(houseConfVo.getDicCode());
					try {
						String value = cityTemplateService.getTextValue(null, houseConfMsgEntity.getDicCode());
						String text = SOAResParseUtil.getValueFromDataByKey(value, "textValue", String.class);
						houseConfVo.setDicDayNum(text);
						if(Check.NuNObj(text)&&!Check.NuNObj(productRulesEnum0019)){
							houseConfVo.setDicDayNum(String.valueOf(productRulesEnum0019.getDayNum()));
						}
					} catch (Exception e) {
						LogUtil.info(LOGGER, "获取优惠天数失败e={}", e);
						if(!Check.NuNObj(productRulesEnum0019)){
							houseConfVo.setDicDayNum(String.valueOf(productRulesEnum0019.getDayNum()));
						}

					}

					listHouseDiscount.add(houseConfVo);
				}
				tenantHouseDetailVo.setListHouseDiscount(listHouseDiscount);
			}
		}

		try {
			//长租天数 设置
			String value = cityTemplateService.getTextValue(null,TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
			String text = SOAResParseUtil.getValueFromDataByKey(value, "textValue", String.class);
			if(!Check.NuNStrStrict(text)){
				tenantHouseDetailVo.setLongTermDays(text);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "长租入住最小天数查询失败e={}", e);
		}

	}

	/**
	 * 
	 * 根据 房源 或房间 fid 查询房源或房间信息（暂不支持 床位）
	 *
	 * @author yd
	 * @created 2016年9月26日 上午11:39:43
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findHouseDetail(String paramJson) {

		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseDetailDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseDetailDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		//查询房源操作
		try {
			HouseDetailDto houseDetailDto=validateResult.getResultObj();
			//判断房子是否存在
			if(houseDetailDto.getRentWay()==0){
				if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, houseDetailDto.getFid())){
					LogUtil.info(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					return dto.toJsonString();
				}
			}else if(houseDetailDto.getRentWay()==1){
				if(!houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, houseDetailDto.getFid())){
					LogUtil.info(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
					return dto.toJsonString();
				}
			}
			TenantHouseDetailVo tenantHouseDetailVo=null;
			//判断出租方式
			if(houseDetailDto.getRentWay()==0){
				tenantHouseDetailVo=tenantHouseServiceImpl.getHouseDetail(houseDetailDto);
			}
			if(houseDetailDto.getRentWay()==1){
				tenantHouseDetailVo=tenantHouseServiceImpl.getHouseRoomDetail(houseDetailDto);
			}
			
			if (Check.NuNObj(tenantHouseDetailVo)) {
				LogUtil.info(LOGGER, "房源不存在，参数:{}",paramJson);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();				
			}
			
			//处理图片信息
			this.dealHouseDetailPic(tenantHouseDetailVo,houseDetailDto);
			//替换当前房源的特殊价格 added by afi  (根据需要打开)
			// this.dealHousePrice(tenantHouseDetailVo,houseDetailDto);
			dto.putValue("houseDetail", tenantHouseDetailVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	/**
	 * 
	 * 说明：
	 *  1. 开始时间设置  选择时间   如果未选择时间  默认当前时间
	 *  2. 夹心价格 查询当前日期 是否已经支付  如果是当天 查看是否满足灵活当天  如果是今天往后的日期  看是否满足 灵活一天  
	 *  3. 房源详情只能是 当前时间
	 *
	 * @author yd
	 * @created 2016年12月6日 上午9:18:07
	 *
	 * @param detail
	 * @param houseDetailDto
	 */
	private void dealHousePrice(TenantHouseDetailVo detail,HouseDetailDto houseDetailDto){
		if(Check.NuNObj(houseDetailDto) ){
			return;
		}
		if(Check.NuNStr(houseDetailDto.getStartTime())){
			//没有设时间默认当天
			houseDetailDto.setStartTime(DateUtil.dateFormat(new Date()));
		}
		Date priceDate = null;
		try {
			priceDate = DateUtil.parseDate(houseDetailDto.getStartTime(), "yyyy-MM-dd");
		}catch (Exception e){
			LogUtil.error(LOGGER, "e:", e);
		}
		if(Check.NuNObj(priceDate)){
			return;
		}
		HousePriceConfDto priceConfDto = new HousePriceConfDto();
		if(houseDetailDto.getRentWay() == RentWayEnum.ROOM.getCode()){
			priceConfDto.setRoomFid(houseDetailDto.getFid());
		}else{
			priceConfDto.setHouseBaseFid(houseDetailDto.getFid());
		}
		priceConfDto.setSetTime(priceDate);
		//获取当前的特殊价格
		HousePriceConfEntity priceInf = houseManageServiceImpl.findHousePriceConfByDate(priceConfDto);
		if(!Check.NuNObj(priceInf)){
			detail.setHousePrice(priceInf.getPriceVal());
		}else {//设置周末价格
			HousePriceWeekConfDto weekPriceConfDto =new HousePriceWeekConfDto();
			weekPriceConfDto.setDay(priceConfDto.getSetTime());
			weekPriceConfDto.setRentWay(houseDetailDto.getRentWay());
			if(houseDetailDto.getRentWay() == RentWayEnum.ROOM.getCode()){
				weekPriceConfDto.setHouseRoomFid(houseDetailDto.getFid());
			}else{
				weekPriceConfDto.setHouseBaseFid(houseDetailDto.getFid());
			} 
			HousePriceWeekConfEntity weekPrice = houseManageServiceImpl.findHousePriceWeekConfByDate(weekPriceConfDto);
			if (!Check.NuNObj(weekPrice)) {
				detail.setHousePrice(weekPrice.getPriceVal());
			}
		}
	}


	/**
	 * 拼接房源的各个类型的图片信息
	 * @author afi
	 * @param detail
	 * @param detailDto
	 */
	private void dealHouseDetailPic(TenantHouseDetailVo detail,HouseDetailDto detailDto){
		if(Check.NuNObjs(detail,detailDto)){
			return;
		}
		List<MinsuEleEntity> picList = tenantHouseServiceImpl.getHousePicList(detailDto.getFid(), detailDto.getRentWay(),detail.getHouseStatus());
		if(!Check.NuNCollection(picList)){
			List<String> pics = new ArrayList<>();
			for(MinsuEleEntity eleEntity:picList){
				pics.add(eleEntity.getEleValue());
			}
			detail.setPicList(pics);
			detail.setPicDisList(picList);
		}
	}

	@Override
	public String houseListDetail(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseDetailDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseDetailDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		//查询房源操作
		try {
			HouseDetailDto houseDetailDto=validateResult.getResultObj();
			//判断房子是否存在
			if(houseDetailDto.getRentWay()==0){
				if(!houseCheckLogic.checkHouseBaseNull(houseManageServiceImpl, dto, houseDetailDto.getFid())){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
					return dto.toJsonString();
				}
			}else if(houseDetailDto.getRentWay()==1){
				if(!houseCheckLogic.checkHouseRoomNull(houseManageServiceImpl, dto, houseDetailDto.getFid())){
					LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL));
					return dto.toJsonString();
				}
			}
			TenantHouseDetailVo tenantHouseDetailVo=null;
			
			//判断出租方式
			if(houseDetailDto.getRentWay()==0){
				tenantHouseDetailVo = tenantHouseServiceImpl.getHouseDetail(houseDetailDto);
			}
			if(houseDetailDto.getRentWay()==1){
				tenantHouseDetailVo =tenantHouseServiceImpl.getHouseRoomDetail(houseDetailDto);
			} 
			
			if (Check.NuNObj(tenantHouseDetailVo)) {
				LogUtil.info(LOGGER, "房源不存在，参数:{}",paramJson);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();				
			}

			dto.putValue("houseDetail", tenantHouseDetailVo);

			Transaction houseTran2 = Cat.newTransaction("TenantHouseServiceProxy", CatConstant.INIT_CREATE);
			try {
				//调用小的订单详情【提交订单】的埋点
				Cat.logMetricForCount("进入预定的数量");
				houseTran2.setStatus(Message.SUCCESS);
			} catch(Exception ex) {
				Cat.logError("进入预定的数量 打点异常", ex);
				houseTran2.setStatus(ex);
			} finally {
				houseTran2.complete();
			}


		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String statisticalPv(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		//1 参数校验
		ValidateResult<HouseDetailDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, HouseDetailDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, "参数:{},错误信息:{}",paramJson, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseStatisticsMsgEntity houseStatisticsMsgEntity=new HouseStatisticsMsgEntity();
		try {
			//参数组合
			HouseDetailDto houseDetailDto=validateResult.getResultObj();
			if(RentWayEnum.HOUSE.getCode()==houseDetailDto.getRentWay()){
				houseStatisticsMsgEntity.setRentWay(houseDetailDto.getRentWay());
				houseStatisticsMsgEntity.setHouseBaseFid(houseDetailDto.getFid());
			}else if(RentWayEnum.ROOM.getCode()==houseDetailDto.getRentWay()){
				houseStatisticsMsgEntity.setRentWay(houseDetailDto.getRentWay());
				houseStatisticsMsgEntity.setRoomFid(houseDetailDto.getFid());
				HouseRoomMsgEntity hsEntity=houseManageServiceImpl.getHouseRoomByFid(houseDetailDto.getFid());
				if(Check.NuNObj(hsEntity)){
					return  dto.toJsonString();
				}
				houseStatisticsMsgEntity.setHouseBaseFid(hsEntity.getHouseBaseFid());
			}
			//获取缓存key
			String key="";
			if(RentWayEnum.HOUSE.getCode()==houseStatisticsMsgEntity.getRentWay()){
				key=RedisKeyConst.getHouseKey(houseStatisticsMsgEntity.getHouseBaseFid(),houseStatisticsMsgEntity.getRentWay());
			} else if(RentWayEnum.ROOM.getCode()==houseStatisticsMsgEntity.getRentWay()) {
				key=RedisKeyConst.getHouseKey(houseStatisticsMsgEntity.getHouseBaseFid(),houseStatisticsMsgEntity.getRoomFid(),houseStatisticsMsgEntity.getRentWay());
			}
			//获取缓存值
			String housePv= null;
			try {
				housePv=redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			//判断缓存是否存在
			if(Check.NuNStr(housePv)){
				HouseStatisticsMsgEntity hs=tenantHouseServiceImpl.getHouseStatisticsMsgByParam(houseStatisticsMsgEntity);
				if(Check.NuNObj(hs)){
					try {
						redisOperations.setex(key, RedisKeyConst.HOUSE_STATISTICAL_CACHE_TIME, "1");
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
					houseStatisticsMsgEntity.setFid(UUIDGenerator.hexUUID());
					houseStatisticsMsgEntity.setHousePv(1);
					tenantHouseServiceImpl.insertHouseStatisticsMsg(houseStatisticsMsgEntity);
				} else {
					Integer housePvInt=hs.getHousePv();
					housePvInt++;
					houseStatisticsMsgEntity.setHousePv(housePvInt);
					try {
						redisOperations.setex(key, RedisKeyConst.HOUSE_STATISTICAL_CACHE_TIME, housePvInt.toString());
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}

				}
			} else {
				Integer housePvInt=Integer.valueOf(housePv);
				housePvInt++;
				if(housePvInt%50==0){
					houseStatisticsMsgEntity.setHousePv(housePvInt);
					tenantHouseServiceImpl.updateHouseStatisticsMsgPv(houseStatisticsMsgEntity);
				}
				try {
					redisOperations.setex(key, RedisKeyConst.HOUSE_STATISTICAL_CACHE_TIME, housePvInt.toString());
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误,e:{}",e);
				}

			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String findStatisticalPv(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();

		HouseStatisticsMsgEntity houseStatisticsMsgEntity=JsonEntityTransform.json2Object(paramJson, HouseStatisticsMsgEntity.class);
		//获取缓存key
		String key="";
		if(RentWayEnum.HOUSE.getCode()==houseStatisticsMsgEntity.getRentWay()){
			key=RedisKeyConst.getHouseKey(houseStatisticsMsgEntity.getHouseBaseFid(),houseStatisticsMsgEntity.getRentWay());
		} else if(RentWayEnum.ROOM.getCode()==houseStatisticsMsgEntity.getRentWay()) {
			key=RedisKeyConst.getHouseKey(houseStatisticsMsgEntity.getHouseBaseFid(),houseStatisticsMsgEntity.getRoomFid(),houseStatisticsMsgEntity.getRentWay());
		}
		//获取缓存值
		String housePv = null;
		try {
			housePv=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		if(!Check.NuNStr(housePv)){
			dto.putValue("housePv", Integer.valueOf(housePv));
		} else {
			dto.putValue("housePv", 0);
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 获取房源描述信息
	 *
	 * @param paramJson
	 * @return
	 * @author lishaochuan
	 * @create 2016/12/7 14:43
	 */
	@Override
	public String findHoseDesc(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseCheckDto request = JsonEntityTransform.json2Object(paramJson, HouseCheckDto.class);
			if(Check.NuNObj(request) || Check.NuNObj(request.getFid()) || Check.NuNObj(request.getRentWay())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}

			String houseBaseFid = "";
			String roomFid = "";
			if (RentWayEnum.HOUSE.getCode() == request.getRentWay()) {
				houseBaseFid = request.getFid();
			} else if (RentWayEnum.ROOM.getCode() == request.getRentWay()) {
				HouseRoomMsgEntity houseRoomMsg = houseIssueServiceImpl.findHouseRoomMsgByFid(request.getFid());
				houseBaseFid = houseRoomMsg.getHouseBaseFid();
				roomFid = request.getFid();
			} else {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}
			
			HouseDescEntity houseDesc = houseIssueServiceImpl.findhouseDescEntityByHouseFid(houseBaseFid);
			
			StringBuilder sb = new StringBuilder();
			//查询自定义守则
			String houseRulesZ = houseDesc.getHouseRules();
			if (RentWayEnum.ROOM.getCode() == request.getRentWay()) {
				HouseRoomExtEntity roomExt = houseIssueServiceImpl.getRoomExtByRoomFid(roomFid);
				if(!Check.NuNObj(roomExt)){
					houseRulesZ = roomExt.getRoomRules();
				}
				
			}
			//查询数据库中房屋可选守则列表
			List<HouseConfMsgEntity> listHouseRules = null;
			try {
				HouseConfParamsDto paramsDto = new HouseConfParamsDto();
				paramsDto.setDicCode(ProductRulesEnum.ProductRulesEnum0024.getValue());
				paramsDto.setHouseBaseFid(houseBaseFid);
				paramsDto.setRentWay(request.getRentWay());
				if (RentWayEnum.ROOM.getCode() == request.getRentWay()) {
					paramsDto.setRoomFid(roomFid);
				}
				
				listHouseRules = houseIssueServiceImpl.findHouseConfValidList( paramsDto);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查询房屋可选守则异常e={}", e);
			}
			if(!Check.NuNCollection(listHouseRules)){
                DataTransferObject enum0024EnumDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0024.getValue()));
                if (enum0024EnumDto.getCode() == DataTransferObject.SUCCESS) {
                    List<EnumVo> selectEnum = enum0024EnumDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {
                    });
                    for (int i = 0; i < listHouseRules.size(); i++) {
                        for (int j = 0; j < selectEnum.size(); j++) {
                            if (listHouseRules.get(i).getDicVal().equals(selectEnum.get(j).getKey())) {
                                sb.append(selectEnum.get(j).getText());
                                sb.append("\n");
                            }
                        }
                    }
                }
			}
			if(!Check.NuNStr(houseRulesZ)){
				sb.append(houseRulesZ);
			}

		    houseDesc.setHouseRules(sb.toString());
			
			dto.putValue("houseDesc", houseDesc);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
}
