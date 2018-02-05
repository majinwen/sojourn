/**
 * @FileName: TenantHouseController.java
 * @Package com.ziroom.minsu.api.house.controller
 * @author bushujie
 * @created 2016年5月1日 下午4:55:41
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.EvaluateConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.common.util.ApiStringUtil;
import com.ziroom.minsu.api.common.valeenum.HouseServiceFacEnum;
import com.ziroom.minsu.api.house.dto.HouseEvaluate;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.api.house.valueenum.MsgInfoEnum;
import com.ziroom.minsu.api.house.valueenum.RulesEnum;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordReplyEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseTonightDiscountService;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.house.entity.HouseBedNumVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseDetailNewVo;
import com.ziroom.minsu.services.house.entity.HouseDetailTopVo;
import com.ziroom.minsu.services.house.entity.HousePriorityVo;
import com.ziroom.minsu.services.house.entity.HouseTonightPriceInfoVo;
import com.ziroom.minsu.services.house.entity.HouseTopInfoVo;
import com.ziroom.minsu.services.house.entity.MercureInfoVo;
import com.ziroom.minsu.services.house.entity.StatsHouseEvaVo;
import com.ziroom.minsu.services.house.entity.TenantEvalVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.services.house.entity.ToNightDiscount;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.LabelTipsEnum;
import com.ziroom.minsu.valenum.search.LabelTipsStyleEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesVo;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>客端房源相关接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author bushujie
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("tenantHouse")
public class TenantHouseController extends AbstractController {

    @Resource(name = "house.tenantHouseService")
    private TenantHouseService tenantHouseService;

    @Resource(name = "evaluate.evaluateOrderService")
    private EvaluateOrderService evaluateOrderService;

    @Resource(name = "api.messageSource")
    private MessageSource messageSource;

    @Resource(name = "api.paramCheckLogic")
    private ParamCheckLogic paramCheckLogic;

    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;

    @Value("#{'${list_small_pic}'.trim()}")
    private String list_small_pic;

    @Value("#{'${detail_big_pic}'.trim()}")
    private String detail_big_pic;

    @Value("#{'${HOUSE_SHARE_URL}'.trim()}")
    private String house_share_url;

    @Value("#{'${NO_LGIN_AUTH}'.trim()}")
    private String no_lgin_auth;

    @Value("#{'${ZIROOM_LOGO_PIC_URL}'.trim()}")
    private String ZIROOM_LOGO_PIC_URL;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "house.houseIssueService")
    private HouseIssueService houseIssueService;

    @Resource(name = "api.houseService")
    private HouseService houseService;

    @Resource(name = "order.houseCommonService")
    private HouseCommonService houseCommonService;

    @Resource(name = "house.houseTonightDiscountService")
    private HouseTonightDiscountService houseTonightDiscountService;

    @Value("#{'${minsu.static.resource.url}'.trim()}")
    private String STATIC_URL;

    @Resource(name = "basedata.staticResourceService")
    private StaticResourceService staticResourceService;

    @Resource(name = "basedata.zkSysService")
    private ZkSysService zkSysService;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TenantHouseController.class);

    /**
     * 房源列表详情
     *
     * @param request
     * @return
     * @author bushujie
     * @created 2016年5月1日 下午4:07:36
     */
    @RequestMapping("${UNLOGIN_AUTH}/houseListDetail")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> houseListDetail(HttpServletRequest request) {
        try {

            Long time = System.currentTimeMillis();

            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "参数：" + paramJson);


            String resultJson = tenantHouseService.houseListDetail(paramJson);

            //--记录调用时间-----
            Long time1 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseListDetail】-获取房源列表：{}", time1 - time);

            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            //判断服务是否有错误
            if (dto.getCode() == 1) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            TenantHouseDetailVo tenantHouseDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);
            //tenantHouseDetailVo.setDefaultPic(tenantHouseDetailVo.getDefaultPic()==null?"":picBaseAddrMona+tenantHouseDetailVo.getDefaultPic()+picSize+"."+tenantHouseDetailVo.getDefaultPic().split("\\.")[1]);
            tenantHouseDetailVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), list_small_pic));

            //--记录调用时间-----
            Long time2 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseListDetail】-解析返回结果时间：{}", time2 - time1);

            //填充房东信息
            setLanlordInfo(tenantHouseDetailVo);


            //查询评论数
            StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
            if (tenantHouseDetailVo.getRentWay() == 0) {
                statsHouseEvaRequest.setHouseFid(tenantHouseDetailVo.getFid());
            } else if (tenantHouseDetailVo.getRentWay() == 1) {
                statsHouseEvaRequest.setRoomFid(tenantHouseDetailVo.getFid());
            }
            statsHouseEvaRequest.setRentWay(tenantHouseDetailVo.getRentWay());
            LogUtil.debug(LOGGER, "查询评论数参数：" + JsonEntityTransform.Object2Json(statsHouseEvaRequest));
            String evaluateCountJson = evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));


            //--记录调用时间-----
            Long time3 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseListDetail】-获取评论时间：{}", time3 - time2);

            List<StatsHouseEvaEntity> evaluateStats = SOAResParseUtil.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
            if (!Check.NuNCollection(evaluateStats)) {
                LogUtil.debug(LOGGER, "查询评论数结果：" + JsonEntityTransform.Object2Json(evaluateStats));
                tenantHouseDetailVo.setEvaluateCount(evaluateStats.get(0).getEvaTotal());
            }

            //--记录调用时间-----
            Long time4 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseListDetail】-解析评论时间：{}", time4 - time3);

            //退订政策组装  预定订单页 对于新老版本 需要做兼容   UnregText1 严格退订： 老版本  展示0
            getTradeRulesNew(tenantHouseDetailVo);
            MsgInfoEnum.MSG_INFO.setMsgInfo(tenantHouseDetailVo);

            //--记录调用时间-----
            Long time5 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseListDetail】-退订政策时间：{}", time5 - time4);

            LogUtil.debug(LOGGER, "返回结果：" + JsonEntityTransform.Object2Json(tenantHouseDetailVo));

            //--记录调用时间-----
            Long time6 = System.currentTimeMillis();
            LogUtil.info(LOGGER, "【houseListDetail】-累计时间：{}", time6 - time);

            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(tenantHouseDetailVo), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * 房源详细信息
     *
     * @param request
     * @return
     * @author bushujie
     * @created 2016年5月1日 下午7:09:01
     */
    @RequestMapping("${UNLOGIN_AUTH}/houseDetail")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> houseDetail(HttpServletRequest request) {
        try {
            Long time0 = System.currentTimeMillis();
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);

            ValidateResult<HouseDetailDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
                    HouseDetailDto.class);
            if (!validateResult.isSuccess()) {
                LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
            }

            LogUtil.info(LOGGER, "房源详情查询开始:参数houseDetailDto={}", paramJson);

            HouseDetailDto houseDetailDto = JsonEntityTransform.json2Object(paramJson, HouseDetailDto.class);
            Long time1 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-参数转换时间：{}", time1 - time0);

            //查询房源详情
            String resultJson = tenantHouseService.houseDetail(paramJson);

            Long time2 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-获取房源详情：{}", time2 - time1);

            //设置房源浏览量
            tenantHouseService.statisticalPv(paramJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);

            Long time3 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-设置房源浏览量：{}", time3 - time2);


            //判断服务是否有错误
            if (dto.getCode() == 1) {
                LogUtil.info(LOGGER, "查询房源详情异常，error = {}", dto.toJsonString());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }

            TenantHouseDetailVo tenantHouseDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);
            if (Check.NuNObj(tenantHouseDetailVo)) {
                LogUtil.info(LOGGER, "房源不存在，paramJson = {}", paramJson);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
            }


            Long time4 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-将房源json转化成对象：{}", time4 - time3);
            //分享房源地址处理
            setHouseShareUrl(houseDetailDto, tenantHouseDetailVo);

            //默认图片处理
            tenantHouseDetailVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), detail_big_pic));

            Long time5 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-分享地理位置：{}", time5 - time4);

            //图片列表处理
            if (!Check.NuNCollection(tenantHouseDetailVo.getPicList())) {

                List<String> picList = new LinkedList<String>();

                String def = tenantHouseDetailVo.getDefaultPic();
                if (!Check.NuNObj(def)) {
                    picList.add(def);
                }
                for (String picUrl : tenantHouseDetailVo.getPicList()) {
                    //picList.add(processPic(picUrl));
                    if (!Check.NuNObj(def) && def.indexOf(picUrl) > -1) {//列表中已经存在
                        continue;
                    }
                    picList.add(PicUtil.getSpecialPic(picBaseAddrMona, picUrl, detail_big_pic));
                }


                tenantHouseDetailVo.setPicList(picList);
            }

            if (!Check.NuNCollection(tenantHouseDetailVo.getPicDisList())) {//处理新的图片列表
                List<MinsuEleEntity> picDisList = tenantHouseDetailVo.getPicDisList();
                MinsuEleEntity defPic = null;
                String def = tenantHouseDetailVo.getDefaultPic();
                for (int i = 0; i < picDisList.size(); i++) {//取默认图片
                    MinsuEleEntity picEntity = picDisList.get(i);
                    if (!Check.NuNObj(def) && !Check.NuNObj(picEntity) && def.indexOf(picEntity.getEleValue()) > -1) {
                        defPic = picEntity;
                        picDisList.remove(i);
                        break;
                    }
                }
                List<MinsuEleEntity> picDisListNew = new LinkedList<MinsuEleEntity>();
                if (!Check.NuNObj(defPic)) {
                    picDisListNew.add(defPic);
                }
                if (picDisList.size() > 0) {
                    picDisListNew.addAll(picDisList);
                }
                tenantHouseDetailVo.setPicDisList(picDisListNew);
            }


            //如果图片列表为空，添加默认图片
            if (Check.NuNCollection(tenantHouseDetailVo.getPicList())) {
                List<String> picList = tenantHouseDetailVo.getPicList();
                if (picList == null) {
                    picList = new LinkedList<String>();
                }
                picList.add(tenantHouseDetailVo.getDefaultPic());
                tenantHouseDetailVo.setPicList(picList);
            }
            if (!Check.NuNCollection(tenantHouseDetailVo.getPicDisList())) {
                for (MinsuEleEntity eleEntity : tenantHouseDetailVo.getPicDisList()) {
                    //picList.add(processPic(picUrl));
                    eleEntity.setEleValue(PicUtil.getSpecialPic(picBaseAddrMona, eleEntity.getEleValue(), detail_big_pic));
                }
            } else {
                MinsuEleEntity eleEntity = new MinsuEleEntity();
                eleEntity.setEleKey("卧室");
                eleEntity.setEleValue(tenantHouseDetailVo.getDefaultPic());
                tenantHouseDetailVo.getPicDisList().add(eleEntity);
            }

            Long time6 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-处理图片链接：{}", time6 - time5);

            //查询评论数和星级判断
            StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
            if (tenantHouseDetailVo.getRentWay() == 0) {
                statsHouseEvaRequest.setHouseFid(tenantHouseDetailVo.getFid());
            } else if (tenantHouseDetailVo.getRentWay() == 1) {
                statsHouseEvaRequest.setRoomFid(tenantHouseDetailVo.getFid());
            }
            statsHouseEvaRequest.setRentWay(tenantHouseDetailVo.getRentWay());
            LogUtil.debug(LOGGER, "查询评论数参数：" + JsonEntityTransform.Object2Json(statsHouseEvaRequest));
            String evaluateCountJson = evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));

            Long time7 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-获取评价时间：{}", time7 - time6);

            List<StatsHouseEvaEntity> evaluateStats = SOAResParseUtil.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
            if (!Check.NuNCollection(evaluateStats)) {
                LogUtil.debug(LOGGER, "查询评论数结果：" + JsonEntityTransform.Object2Json(evaluateStats));
                StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
                Float sumStar = (statsHouseEvaEntity.getHouseCleanAva() + statsHouseEvaEntity.getDesMatchAva() + statsHouseEvaEntity.getSafeDegreeAva() + statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity.getCostPerforAva()) / 5;
                BigDecimal bigDecimal = new BigDecimal(sumStar.toString());
                tenantHouseDetailVo.setGradeStar(bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).floatValue());
                tenantHouseDetailVo.setEvaluateCount(statsHouseEvaEntity.getEvaTotal());
            } else {
                //默认5星
                tenantHouseDetailVo.setGradeStar(new BigDecimal(5).floatValue());
                tenantHouseDetailVo.setEvaluateCount(0);
            }

            Long time8 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-解析图片时间：{}", time8 - time7);

            //房东信息获取
            String landlordJson = customerMsgManagerService.getCutomerVoFromDb(tenantHouseDetailVo.getLandlordUid());
            CustomerVo landlord = SOAResParseUtil.getValueFromDataByKey(landlordJson, "customerVo", CustomerVo.class);
            if (!Check.NuNObj(landlord)) {
                tenantHouseDetailVo.setLandlordIcon(landlord.getUserPicUrl());
                //默认显示昵称，如果没有显示真实姓名
                if (Check.NuNStr(landlord.getNickName())) {
                    tenantHouseDetailVo.setLandlordName(landlord.getRealName());
                } else {
                    tenantHouseDetailVo.setLandlordName(landlord.getNickName());
                }
                tenantHouseDetailVo.setLandlordMobile(landlord.getHostNumber() + "," + landlord.getZiroomPhone());
            }

            //--记录调用时间-----
            Long time10 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-获取房东信息：{}", time10 - time8);

            //获取评论信息
            EvaluateRequest evaluateRequest = new EvaluateRequest();
            if (tenantHouseDetailVo.getRentWay() == 0) {
                evaluateRequest.setHouseFid(tenantHouseDetailVo.getFid());
            } else if (tenantHouseDetailVo.getRentWay() == 1) {
                evaluateRequest.setRoomFid(tenantHouseDetailVo.getFid());
            }
            evaluateRequest.setRentWay(tenantHouseDetailVo.getRentWay());
            evaluateRequest.setLimit(1);
            String evaluatePageJson = evaluateOrderService.queryTenantEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest));
            List<TenantEvaluateVo> evaList = SOAResParseUtil.getListValueFromDataByKey(evaluatePageJson, "listTenantEvaluateVo", TenantEvaluateVo.class);
            if (!Check.NuNCollection(evaList)) {
                TenantEvalVo tenantEvalVo = new TenantEvalVo();
                //以后获取用户名
                String customerJson = customerMsgManagerService.getCutomerVo(evaList.get(0).getEvaUserUid());
                CustomerVo customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
                if (!Check.NuNObj(customer)) {
                    tenantEvalVo.setCustomerName(Check.NuNStr(customer.getNickName())
                            ? ApiStringUtil.hideRealName(customer.getRealName()) : customer.getNickName());
                }
                tenantEvalVo.setEvalContent(evaList.get(0).getContent());
                tenantEvalVo.setEvalDate(DateUtil.dateFormat(evaList.get(0).getCreateTime()));
                tenantEvalVo.setCustomerIcon(customer.getUserPicUrl());
                tenantHouseDetailVo.setTenantEvalVo(tenantEvalVo);
            }

            //--记录调用时间-----
            Long time11 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-获取评价信息：{}", time11 - time10);

            //组装床信息
            //String bedSizeJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
            //List<EnumVo> bedSizelist=SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
            String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
            List<EnumVo> bedTypelist = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
            if (!Check.NuNCollection(tenantHouseDetailVo.getBedList())) {
                Map<Integer, Integer> numMap = new HashMap<>();
                for (HouseBedNumVo bedVo : tenantHouseDetailVo.getBedList()) {

                    Integer bedType = bedVo.getBedType();
                    Integer oNum = bedVo.getBedNum();
                    if (numMap.containsKey(bedType)) {
                        Integer bedNum = numMap.get(bedType);
                        numMap.put(bedType, bedNum + oNum);
                    } else {
                        numMap.put(bedType, oNum);
                    }

                }
                //计算床数量
                List<HouseBedNumVo> houseBedVo = new ArrayList<>();
                for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
                    HouseBedNumVo vo = new HouseBedNumVo();
                    for (EnumVo enumVo : bedTypelist) {
                        if (entry.getKey().toString().equals(enumVo.getKey())) {
                            vo.setBedNum(entry.getValue());
                            vo.setBedTypeName(enumVo.getText());
                            vo.setBedType(entry.getKey());
                            houseBedVo.add(vo);
                            break;
                        }
                    }
                }
                tenantHouseDetailVo.setBedList(houseBedVo);
            }
            //--记录调用时间-----
            Long time12 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-组装床信息：{}", time12 - time11);

            //配套设施组装
            String facilityJson = cityTemplateService.getSelectSubDic(null, ProductRulesEnum.ProductRulesEnum002.getValue());
            List<EnumVo> facilitylist = SOAResParseUtil.getListValueFromDataByKey(facilityJson, "subDic", EnumVo.class);
            if (!Check.NuNCollection(tenantHouseDetailVo.getFacilityList()) && !Check.NuNCollection(facilitylist)) {
                for (HouseConfVo vo : tenantHouseDetailVo.getFacilityList()) {
                    for (EnumVo enumVo : facilitylist) {
                        if (vo.getDicCode().equals(enumVo.getKey())) {
                            for (EnumVo valVo : enumVo.getSubEnumVals()) {
                                if (vo.getDicValue().equals(valVo.getKey())) {
                                    vo.setDicName(valVo.getText());
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }

            if (!Check.NuNCollection(tenantHouseDetailVo.getFacilityList())) {
                //删除配套设施沙发
                for (HouseConfVo vo : tenantHouseDetailVo.getFacilityList()) {
                    if (vo.getDicCode().equals("ProductRulesEnum002003") && vo.getDicValue().equals("1")) {
                        tenantHouseDetailVo.getFacilityList().remove(vo);
                        break;
                    }
                }
            }
            //--记录调用时间-----
            Long time13 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-配套设施信息：{}", time13 - time12);

            //服务
            String serveJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0015.getValue());
            List<EnumVo> servelist = SOAResParseUtil.getListValueFromDataByKey(serveJson, "selectEnum", EnumVo.class);
            if (!Check.NuNCollection(tenantHouseDetailVo.getServeList()) && !Check.NuNCollection(servelist)) {
                for (HouseConfVo vo : tenantHouseDetailVo.getServeList()) {
                    for (EnumVo enumVo : servelist) {
                        if (vo.getDicValue().equals(enumVo.getKey())) {
                            vo.setDicName(enumVo.getText());
                            break;
                        }
                    }
                }
            }

            //--记录调用时间-----
            Long time14 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-服务信息：{}", time14 - time13);


            //地址处理
            if (!Check.NuNStr(tenantHouseDetailVo.getHouseAddr())) {
                tenantHouseDetailVo.setHouseAddr(tenantHouseDetailVo.getHouseAddr().split(" ")[0]);
            }

            //退订政策组装
            getTradeRulesNew(tenantHouseDetailVo);

            //--记录调用时间-----
            Long time15 = System.currentTimeMillis();
            LogUtil.debug(LOGGER, "【houseDetail】-退订政策：{}", time15 - time14);


            LogUtil.debug(LOGGER, "返回结果：" + JsonEntityTransform.Object2Json(tenantHouseDetailVo));


            //房源的夹心价格设置
            setHousePriorityDate(tenantHouseDetailVo, null, houseDetailDto, request);

            //--记录调用时间-----
            Long time16 = System.currentTimeMillis();

            LogUtil.debug(LOGGER, "【houseDetail】-夹心价格：{}", time16 - time1);

            setTonigDiscount(tenantHouseDetailVo, houseDetailDto, null, request);

            Long time17 = System.currentTimeMillis();

            LogUtil.debug(LOGGER, "【houseDetail】-总时间：{}", time17 - time1);
            try {
                Header header = getHeader(request);
                String uid = getUserId(request);
                houseService.saveLocation(uid, header, getIpAddress(request), LocationTypeEnum.HOUSE_DETAIL, houseDetailDto.getFid(), houseDetailDto.getRentWay());
            } catch (Exception e) {
                LogUtil.error(LOGGER, "查看房源详情保存用户信息异常，error = {}", e);
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(tenantHouseDetailVo), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "查看房源详情异常，error = {}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }


    /**
     * 房源详情
     * 说明： v1 版本
     * <p>
     * 具体业务：
     * 1. 查询房源详情信息
     * 2. 统计PV
     * 3. 分享房源地址处理
     * 4. 默认图片处理
     * 5. 查询评论数和星级判断
     * 6. 组装床信息
     * 7. 配套设施组装
     * 8. 地址处理
     * 9. 退订政策组装
     * 10.入住规则组装
     * 11.房源的夹心价格设置
     *
     * @param request
     * @return
     * @author yd
     * @created 2016年12月2日 上午10:21:20
     */
    @RequestMapping("${UNLOGIN_AUTH}/houseDetailV1")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> houseDetailV1(HttpServletRequest request) {

        String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        try {

            ValidateResult<HouseDetailDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
                    HouseDetailDto.class);
            if (!validateResult.isSuccess()) {
                LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
            }

            String version = "V1";
            LogUtil.info(LOGGER, "房源详情查询开始:参数houseDetailDto={}", paramJson);
            //查询房源详情
            String resultJson = tenantHouseService.houseDetail(paramJson);
            HouseDetailDto houseDetailDto = JsonEntityTransform.json2Object(paramJson, HouseDetailDto.class);

            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            //pv统计
            tenantHouseService.statisticalPv(paramJson);
            //判断服务是否有错误
            if (dto.getCode() == 1) {
                LogUtil.info(LOGGER, "查询房源详情失败errorMsg = {}", dto.getMsg());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            TenantHouseDetailVo tenantHouseDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);
            //LogUtil.info(LOGGER, "房源详情结果{}", resultJson);

            //分享房源地址处理
            setHouseShareUrl(houseDetailDto, tenantHouseDetailVo);

            //默认图片处理
            tenantHouseDetailVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), detail_big_pic));

            //地址处理
            tenantHouseDetailVo.setHouseAddr(tenantHouseDetailVo.getHouseAddr().split(" ")[0]);

            //处理新的图片列表
            setPicDisList(tenantHouseDetailVo);

            //查询评论数和星级判断
            setHouseEvaInfo(tenantHouseDetailVo);

            //房东信息获取
            setLanlordInfo(tenantHouseDetailVo);

            //组装床信息
            setHouseNameInfoList(tenantHouseDetailVo, version);

            //设置房源 美居信息
            setMercureInfoVo(tenantHouseDetailVo, version);

            //配套设施组装
            setHouseConfVo(tenantHouseDetailVo, version);

            //退订政策组装
            getTradeRulesNew(tenantHouseDetailVo);

            HouseDetailNewVo houseDetailNewVo = new HouseDetailNewVo();

            //入住规则组装
            RulesEnum.CHANGZU_DISCOUNT.setCheckRuleVo(tenantHouseDetailVo, houseDetailNewVo);

            //房源的夹心价格设置
            setHousePriorityDate(tenantHouseDetailVo, houseDetailNewVo, houseDetailDto, request);

            //设置房源标签
            setHouseLabTips(tenantHouseDetailVo, houseDetailNewVo);

            setTonigDiscount(tenantHouseDetailVo, houseDetailDto, houseDetailNewVo, request);

            //新接口返回vo
            BeanUtils.copyProperties(tenantHouseDetailVo, houseDetailNewVo);


            try {
                Header header = getHeader(request);
                String uid = getUserId(request);
                houseService.saveLocation(uid, header, getIpAddress(request), LocationTypeEnum.HOUSE_DETAIL, houseDetailDto.getFid(), houseDetailDto.getRentWay());
            } catch (Exception e) {
                LogUtil.error(LOGGER, "error = {}", e);
            }

            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseDetailNewVo), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "房源详情获取失败,paramJson={},error = {},", paramJson, e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * 房源详情
     * 说明： top50版本
     * <p>
     * 具体业务：
     * 1. 查询房源详情信息
     * 2. 统计PV
     * 3. 分享房源地址处理
     * 4. 默认图片处理
     * 5. 查询评论数和星级判断
     * 6. 组装床信息
     * 7. 配套设施组装
     * 8. 地址处理
     * 9. 退订政策组装
     * 10.入住规则组装
     * 11.房源的夹心价格设置
     * 12.设置top50房源属性
     *
     * @param request
     * @return
     * @author yd
     * @created 2016年12月2日 上午10:21:20
     */
    @RequestMapping("${UNLOGIN_AUTH}/houseDetailTOP50")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> houseDetailTOP50(HttpServletRequest request) {

        String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        String version = "TOP50";
        HouseDetailNewVo houseDetailNewVo = new HouseDetailNewVo();
        DataTransferObject dto = new DataTransferObject();

        HouseDetailTopVo houseDetailTopVo = new HouseDetailTopVo();
        try {
            setHouseDetailCommonInfo(dto, houseDetailNewVo, houseDetailTopVo, paramJson, version, request);
            if (dto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseDetailTopVo), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "房源详情获取失败,paramJson={},error = {},", paramJson, e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * top50 房源详情
     * 说明： 无需加密
     *
     * @param request
     * @return
     * @author yd
     * @created 2017年3月18日 下午2:52:25
     */
    @RequestMapping("${NO_LGIN_AUTH}/houseDetailTOP50V1")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> houseDetailTOP50V1(HttpServletRequest request, HouseDetailDto houseDetailDto) {
        request.setAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE, JsonEntityTransform.Object2Json(houseDetailDto));

        String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        String version = "TOP50_v1";
        HouseDetailNewVo houseDetailNewVo = new HouseDetailNewVo();
        DataTransferObject dto = new DataTransferObject();

        HouseDetailTopVo houseDetailTopVo = new HouseDetailTopVo();
        try {
            setHouseDetailCommonInfo(dto, houseDetailNewVo, houseDetailTopVo, paramJson, version, request);
            if (dto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(houseDetailTopVo), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "房源详情获取失败,paramJson={},error = {},", paramJson, e);
            return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * 房源详情公共信息查询
     *
     * @param dto
     * @param houseDetailNewVo
     * @param paramJson
     * @param version
     * @throws SOAParseException
     * @author yd
     * @created 2017年3月18日 下午12:00:50
     */
    private void setHouseDetailCommonInfo(DataTransferObject dto, HouseDetailNewVo houseDetailNewVo,
                                          HouseDetailTopVo houseDetailTopVo, String paramJson, String version, HttpServletRequest request) throws SOAParseException {

        ValidateResult<HouseDetailDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
                HouseDetailDto.class);
        if (!validateResult.isSuccess()) {
            LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(validateResult.getDto().getMsg());
            return;
        }

        LogUtil.info(LOGGER, "房源详情查询开始:参数houseDetailDto={}", paramJson);
        //查询房源详情
        String resultJson = tenantHouseService.houseDetail(paramJson);
        HouseDetailDto houseDetailDto = JsonEntityTransform.json2Object(paramJson, HouseDetailDto.class);

        dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        //pv统计
        tenantHouseService.statisticalPv(paramJson);
        //判断服务是否有错误
        if (dto.getCode() == 1) {
            LogUtil.error(LOGGER, "查询房源详情失败errorMsg = {}", dto.getMsg());
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(dto.getMsg());
            return;
        }
        TenantHouseDetailVo tenantHouseDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);

        //分享房源地址处理
        setTopHouseShareUrl(houseDetailDto, tenantHouseDetailVo);

        //默认图片处理
        tenantHouseDetailVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailVo.getDefaultPic(), detail_big_pic));

        //地址处理
        tenantHouseDetailVo.setHouseAddr(tenantHouseDetailVo.getHouseAddr().split(" ")[0]);

        //处理新的图片列表
        setPicDisList(tenantHouseDetailVo);

        //查询评论数和星级判断
        setHouseEvaInfo(tenantHouseDetailVo);

        //房东信息获取
        setLanlordInfo(tenantHouseDetailVo);

        //组装床信息
        setHouseNameInfoList(tenantHouseDetailVo, version);

        //设置房源 美居信息
        setMercureInfoVo(tenantHouseDetailVo, version);

        //配套设施组装
        setHouseConfVo(tenantHouseDetailVo, version);

        //退订政策组装
        getTradeRulesNew(tenantHouseDetailVo);

        //入住规则组装
        RulesEnum.CHANGZU_DISCOUNT.setCheckRuleVo(tenantHouseDetailVo, houseDetailNewVo);

        //房源的夹心价格设置
        setHousePriorityDate(tenantHouseDetailVo, houseDetailNewVo, houseDetailDto, request);

        //设置房源标签
        setHouseLabTips(tenantHouseDetailVo, houseDetailNewVo);

        //新街口返回vo
        BeanUtils.copyProperties(tenantHouseDetailVo, houseDetailNewVo);

        //设置top50 房源特有信息
        BeanUtils.copyProperties(houseDetailNewVo, houseDetailTopVo);

        setHouseTopInfoVo(houseDetailTopVo, tenantHouseDetailVo);

        try {
            Header header = getHeader(request);
            String uid = getUserId(request);
            houseService.saveLocation(uid, header, getIpAddress(request), LocationTypeEnum.HOUSE_DETAIL, houseDetailDto.getFid(), houseDetailDto.getRentWay());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error = {}", e);
        }
    }

    /**
     * 设置top50 房源特有数据
     *
     * @param houseDetailTopVo
     * @author yd
     * @created 2017年3月16日 下午8:17:01
     */
    public void setHouseTopInfoVo(HouseDetailTopVo houseDetailTopVo, TenantHouseDetailVo tenantHouseDetailVo) {

        //填充 top50 数据
        if (!Check.NuNObj(houseDetailTopVo)) {
            HouseTopInfoVo houseTopInfoVo = tenantHouseDetailVo.getHouseTopInfoVo();
            houseDetailTopVo.setHouseTopInfoVo(houseTopInfoVo);
            houseDetailTopVo.setCustomerIntroduce(tenantHouseDetailVo.getCustomerIntroduce());
            houseDetailTopVo.setDefaultPic(houseTopInfoVo.getTopHeadImg());
            /*houseTopInfoVo.setTopMiddlePic("http://10.16.34.44:8000/minsu/group1/M00/00/34/ChAiMFc6Lx2ALL41AABoXASCvmY306.jpg_Z_720_480.jpg");
            houseTopInfoVo.setTopShareTitle("TOP50测试分享标题");
			houseTopInfoVo.setTopTitle("该民宿入选年度发现TOP 50");
			houseTopInfoVo.setTopTitlePic("http://10.16.34.44:8000/minsu/group1/M00/02/51/ChAiMFithByAOESRAAXUD2pQnP828.jpeg_Z_120_120.jpg");
			
			List<HouseTopColumnVo> houseTopColumnVoList  = houseTopInfoVo.getHouseTopColumnVoList();
			HouseTopColumnVo  houseTopColumnVo = new HouseTopColumnVo();
			houseTopColumnVo.setColumnContent(ColumnTypeEnum.Column_Type_101.getName());
			houseTopColumnVo.setColumnSort(1);
			houseTopColumnVo.setColumnType(ColumnTypeEnum.Column_Type_101.getValue());
			houseTopColumnVo.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			HouseTopColumnVo  houseTopColumnVo0 = new HouseTopColumnVo();
			houseTopColumnVo0.setColumnContent(ColumnTypeEnum.Column_Type_102.getName());
			houseTopColumnVo0.setColumnSort(1);
			houseTopColumnVo0.setColumnType(ColumnTypeEnum.Column_Type_102.getValue());
			houseTopColumnVo0.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			HouseTopColumnVo  houseTopColumnVo1 = new HouseTopColumnVo();
			houseTopColumnVo1.setColumnContent(ColumnTypeEnum.Column_Type_103.getName());
			houseTopColumnVo1.setColumnSort(1);
			houseTopColumnVo1.setColumnType(ColumnTypeEnum.Column_Type_103.getValue());
			houseTopColumnVo1.setColumnStyle(ColumnStyleEnum.Column_Style_102.getValue());
			
			HouseTopColumnVo  houseTopColumnVo2 = new HouseTopColumnVo();
			houseTopColumnVo2.setColumnContent(ColumnTypeEnum.Column_Type_104.getName());
			houseTopColumnVo2.setColumnSort(1);
			houseTopColumnVo2.setColumnType(ColumnTypeEnum.Column_Type_104.getValue());
			houseTopColumnVo2.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			HouseTopColumnVo  houseTopColumnVo3 = new HouseTopColumnVo();
			houseTopColumnVo3.setColumnContent(ColumnTypeEnum.Column_Type_105.getName());
			houseTopColumnVo3.setColumnSort(1);
			houseTopColumnVo3.setColumnType(ColumnTypeEnum.Column_Type_105.getValue());
			houseTopColumnVo3.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			HouseTopColumnVo  houseTopColumnVo4 = new HouseTopColumnVo();
			houseTopColumnVo4.setColumnContent(ColumnTypeEnum.Column_Type_105.getName());
			houseTopColumnVo4.setColumnSort(1);
			houseTopColumnVo4.setColumnType(ColumnTypeEnum.Column_Type_105.getValue());
			houseTopColumnVo4.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			HouseTopColumnVo  houseTopColumnVo5 = new HouseTopColumnVo();
			houseTopColumnVo5.setColumnContent(ColumnTypeEnum.Column_Type_201.getName());
			houseTopColumnVo5.setColumnSort(1);
			houseTopColumnVo5.setColumnType(ColumnTypeEnum.Column_Type_201.getValue());
			houseTopColumnVo5.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			HouseTopColumnVo  houseTopColumnVo6 = new HouseTopColumnVo();
			houseTopColumnVo6.setColumnContent(ColumnTypeEnum.Column_Type_202.getName());
			houseTopColumnVo6.setColumnSort(1);
			houseTopColumnVo6.setColumnType(ColumnTypeEnum.Column_Type_202.getValue());
			houseTopColumnVo6.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			HouseTopColumnVo  houseTopColumnVo7 = new HouseTopColumnVo();
			houseTopColumnVo7.setColumnContent(ColumnTypeEnum.Column_Type_203.getName());
			houseTopColumnVo7.setColumnSort(1);
			houseTopColumnVo7.setColumnType(ColumnTypeEnum.Column_Type_203.getValue());
			houseTopColumnVo7.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			HouseTopColumnVo  houseTopColumnVo8 = new HouseTopColumnVo();
			houseTopColumnVo8.setColumnContent(ColumnTypeEnum.Column_Type_204.getName());
			houseTopColumnVo8.setColumnSort(1);
			houseTopColumnVo8.setColumnType(ColumnTypeEnum.Column_Type_204.getValue());
			houseTopColumnVo8.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			
			
			HouseTopColumnVo  houseTopColumnVo9 = new HouseTopColumnVo();
			houseTopColumnVo9.setColumnContent(ColumnTypeEnum.Column_Type_301.getName());
			houseTopColumnVo9.setColumnSort(1);
			houseTopColumnVo9.setColumnType(ColumnTypeEnum.Column_Type_301.getValue());
			houseTopColumnVo9.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			houseTopColumnVo9.setPicUrl("http://10.16.34.44:8000/minsu/group1/M00/00/34/ChAiMFc6Lx2ALL41AABoXASCvmY306.jpg_Z_720_480.jpg");
			
			
			
			HouseTopColumnVo  houseTopColumnVo10 = new HouseTopColumnVo();
			houseTopColumnVo10.setColumnContent(ColumnTypeEnum.Column_Type_401.getName());
			houseTopColumnVo10.setColumnSort(1);
			houseTopColumnVo10.setColumnType(ColumnTypeEnum.Column_Type_401.getValue());
			houseTopColumnVo10.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			houseTopColumnVo10.setPicUrl("http://10.16.34.44:8000/minsu/group1/M00/00/34/ChAiMFc6Lx2ALL41AABoXASCvmY306.jpg_Z_720_480.jpg");
			houseTopColumnVo10.setVideoUrl("http://10.16.34.44:8000/minsu/group1/M00/00/34/ChAiMFc6Lx2ALL41AABoXASCvmY306.jpg_Z_720_480.jpg");
			
			HouseTopColumnVo  houseTopColumnVo11 = new HouseTopColumnVo();

			houseTopColumnVo11.setColumnContent(ColumnTypeEnum.Column_Type_501.getName());
			houseTopColumnVo11.setColumnSort(1);
			houseTopColumnVo11.setColumnType(ColumnTypeEnum.Column_Type_501.getValue());
			houseTopColumnVo11.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			
			houseTopColumnVoList.add(houseTopColumnVo);
			houseTopColumnVoList.add(houseTopColumnVo0);
			houseTopColumnVoList.add(houseTopColumnVo1);
			houseTopColumnVoList.add(houseTopColumnVo2);
			houseTopColumnVoList.add(houseTopColumnVo3);
			houseTopColumnVoList.add(houseTopColumnVo4);
			houseTopColumnVoList.add(houseTopColumnVo5);
			houseTopColumnVoList.add(houseTopColumnVo6);
			houseTopColumnVoList.add(houseTopColumnVo7);
			houseTopColumnVoList.add(houseTopColumnVo8);
			houseTopColumnVoList.add(houseTopColumnVo9);
			houseTopColumnVoList.add(houseTopColumnVo10);
		
			
			houseTopColumnVoList.add(houseTopColumnVo11);*/

        }
    }

    /**
     * 设置房源标签
     *
     * @param tenantHouseDetailVo
     * @param houseDetailNewVo
     */
    private void setHouseLabTips(TenantHouseDetailVo tenantHouseDetailVo, HouseDetailNewVo houseDetailNewVo) {
        List<HouseConfVo> listHouseDiscount = tenantHouseDetailVo.getListHouseDiscount();
        if (Check.NuNCollection(listHouseDiscount)) {
            return;
        }
        List<LabelTipsEntity> list = houseDetailNewVo.getLabelTipsList();
        int index = list.size();
        for (HouseConfVo houseConfVo : listHouseDiscount) {
            String code = houseConfVo.getDicCode();
            String price = houseConfVo.getDicValue();
            Double discount = null;
            if (!Check.NuNStr(price)) {
                discount = BigDecimalUtil.div(ValueUtil.getintValue(price), 10, 1);
            }
            String discountStr = String.valueOf(discount);
            if (discountStr.length() > 3) {
                discountStr = discountStr.substring(0, 3);
            }
            if (LabelTipsEnum.IS_WEEK_DISCOUNT.getCode().equals(code) && Integer.parseInt(price) > 0) {
                LabelTipsEntity labelEntity = new LabelTipsEntity();
                labelEntity.setIndex(index);
                labelEntity.setName(String.format(LabelTipsEnum.IS_WEEK_DISCOUNT.getName(), discountStr));
                labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
                list.add(labelEntity);
                index++;
            }
            if (LabelTipsEnum.IS_MONTH_DISCOUNT.getCode().equals(code) && Integer.parseInt(price) > 0) {
                LabelTipsEntity labelEntity = new LabelTipsEntity();
                labelEntity.setIndex(index);
                labelEntity.setName(String.format(LabelTipsEnum.IS_MONTH_DISCOUNT.getName(), discountStr));
                labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
                list.add(labelEntity);
                index++;
            }
        }

        houseDetailNewVo.setLabelTipsList(list);
    }


    /**
     * 设置房源夹心价格
     *
     * @param tenantHouseDetailVo
     * @param houseDetailNewVo
     * @param request
     * @author yd
     * @created 2016年12月8日 上午10:29:14
     */
    private void setHousePriorityDate(TenantHouseDetailVo tenantHouseDetailVo, HouseDetailNewVo houseDetailNewVo, HouseDetailDto paramDto, HttpServletRequest request) {

        if (!Check.NuNObj(tenantHouseDetailVo) && !Check.NuNObj(paramDto)) {

            int rentWay = paramDto.getRentWay();
            String startTimeParam = paramDto.getStartTime();
            String endTimeParam = paramDto.getEndTime();
            try {
                HousePriorityDto housePriorityDt = new HousePriorityDto();
                if (rentWay == RentWayEnum.HOUSE.getCode()) {
                    housePriorityDt.setHouseBaseFid(paramDto.getFid());
                }
                if (rentWay == RentWayEnum.ROOM.getCode()) {
                    housePriorityDt.setHouseRoomFid(paramDto.getFid());
                }

                housePriorityDt.setRentWay(rentWay);
                housePriorityDt.setNowDate(new Date());

                Date tillDate = tenantHouseDetailVo.getTillDate();

                Date curDate = new Date();
                //是否填写日期
                boolean isW = true;
                if (Check.NuNStr(startTimeParam)) {
                    //没有设时间默认当天
                    startTimeParam = DateUtil.dateFormat(curDate);
                    paramDto.setStartTime(startTimeParam);
                    isW = false;
                }
                String todayStr = DateUtil.dateFormat(curDate, "yyyy-MM-dd");

                //查看搜索时间间隔 判断显示优惠规则
                int searchDayCount = 0;
                if (!Check.NuNStr(startTimeParam) && !Check.NuNStr(endTimeParam)) {
                    Date startTime = DateUtil.parseDate(startTimeParam, "yyyy-MM-dd");
                    Date endTime = DateUtil.parseDate(endTimeParam, "yyyy-MM-dd");
                    searchDayCount = DateSplitUtil.countDateSplit(startTime, endTime);
                } else if (!Check.NuNStr(startTimeParam) && Check.NuNStr(endTimeParam)) {
                    searchDayCount = 1;
                }

                Date priceDate = DateUtil.parseDate(paramDto.getStartTime(), "yyyy-MM-dd");

                //日期往前 推2天  搜索日期范围 只能是 从当前时间  到 截至时间  如果 不是 默认是当前时间
                Date endDate = DateSplitUtil.jumpDate(priceDate, ProductRulesEnum020.ProductRulesEnum020003.getDayNum() + 1);

                housePriorityDt.setStartDate(curDate);
                housePriorityDt.setEndDate(endDate);
                housePriorityDt.setTillDate(tillDate);

                DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseCommonService.findPriorityDate(JsonEntityTransform.Object2Json(housePriorityDt)));

                if (dto.getCode() == DataTransferObject.ERROR) {
                    LogUtil.error(LOGGER, "查看日历,夹心价格获取失败,参数paramDto={},msg={}", JsonEntityTransform.Object2Json(paramDto), dto.getMsg());
                    return;
                }

                Map<String, HousePriorityVo> housePriorityMap = dto.parseData("housePriorityMap", new TypeReference<Map<String, HousePriorityVo>>() {
                });
                if (!Check.NuNMap(housePriorityMap)) {
                    LogUtil.info(LOGGER, "获取夹心价格集合={}", JsonEntityTransform.Object2Json(housePriorityMap));
                    HousePriorityVo housePriorityVo = housePriorityMap.get(DateUtil.dateFormat(priceDate, "yyyy-MM-dd"));
                    LogUtil.info(LOGGER, "房源详情价格,房源fid={},rentWay={},当前价格price={}", paramDto.getFid(), rentWay, tenantHouseDetailVo.getHousePrice());
                    boolean isChangePrice = false;
                    if (!Check.NuNObj(housePriorityVo)) {

                        if (!Check.NuNObj(houseDetailNewVo)) {
                            List<LabelTipsEntity> labelTipsList = houseDetailNewVo.getLabelTipsList();
                            int index = labelTipsList.size();
//                            if (housePriorityVo.getPriorityCode().equals(ProductRulesEnum020.ProductRulesEnum020001.getValue())) {
//                                JYTJ  小于版本号100015，返回今日特惠的标签
//                                Header header = getHeader(request);
//                                if (header.getVersionCode() < 100015) {
//                                    //满足今日特惠条件
//                                    if (todayStr.equals(startTimeParam) && searchDayCount == 1) {
//                                        LogUtil.info(LOGGER, "满足今日特惠");
//                                        LabelTipsEntity labelEntity = new LabelTipsEntity();
//                                        labelEntity.setIndex(++index);
//                                        labelEntity.setName(LabelTipsEnum.IS_TODAY_DISCOUNT.getName());
//                                        labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
//                                        labelTipsList.add(labelEntity);
//                                        houseDetailNewVo.setOriginalPrice(tenantHouseDetailVo.getHousePrice());
//                                        isChangePrice = true;
//                                    }
//                                }
//                            }

                            if (isW && housePriorityVo.getPriorityCode().equals(ProductRulesEnum020.ProductRulesEnum020002.getValue())) {
                                if (searchDayCount == 1) {
                                    LogUtil.info(LOGGER, "满足灵活定价一天");
                                    LabelTipsEntity labelEntity = new LabelTipsEntity();
                                    labelEntity.setIndex(++index);
                                    labelEntity.setName(LabelTipsEnum.IS_JIAXIN_DISCOUNT1.getName());
                                    labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
                                    labelTipsList.add(labelEntity);
                                    houseDetailNewVo.setOriginalPrice(tenantHouseDetailVo.getHousePrice());
                                    isChangePrice = true;
                                }
                            }

                            if (isW && housePriorityVo.getPriorityCode().equals(ProductRulesEnum020.ProductRulesEnum020003.getValue())) {
                                if (searchDayCount == 2 || searchDayCount == 1) {
                                    LogUtil.info(LOGGER, "满足灵活定价两天");
                                    LabelTipsEntity labelEntity = new LabelTipsEntity();
                                    labelEntity.setIndex(++index);
                                    labelEntity.setName(LabelTipsEnum.IS_JIAXIN_DISCOUNT2.getName());
                                    labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
                                    labelTipsList.add(labelEntity);
                                    houseDetailNewVo.setOriginalPrice(tenantHouseDetailVo.getHousePrice());
                                    isChangePrice = true;
                                }
                            }
                            houseDetailNewVo.setLabelTipsList(labelTipsList);
                        }
                        //LogUtil.info(LOGGER,"返回结果result={}",JsonEntityTransform.Object2Json(houseDetailNewVo));
                        if (isChangePrice || Check.NuNStr(startTimeParam) || todayStr.equals(startTimeParam)) {
                            tenantHouseDetailVo.setHousePrice(DataFormat.getPriorityPrice(housePriorityVo.getPriorityDiscount(), tenantHouseDetailVo.getHousePrice()));
                        }
                        LogUtil.info(LOGGER, "房源详情价格,夹心后价格priorityPrice={}", tenantHouseDetailVo.getHousePrice());
                    }
                }

            } catch (Exception e) {
                LogUtil.error(LOGGER, "查看房源详情-夹心价格设置失败paramDto={},e={}", JsonEntityTransform.Object2Json(paramDto), e);
            }

        }
    }

    /**
     * 设置今夜特价
     *
     * @param
     * @param houseDetailNewVo
     * @return
     * @author wangwentao
     * @created 2017/5/11 17:43
     */
    private void setTonigDiscount(TenantHouseDetailVo tenantHouseDetailVo, HouseDetailDto houseDetailDto, HouseDetailNewVo houseDetailNewVo, HttpServletRequest request) {
        TonightDiscountEntity entity = new TonightDiscountEntity();
        try {
            //1.查询t_house_tonight_discount
            entity.setRentWay(houseDetailDto.getRentWay());
            if (houseDetailDto.getRentWay() == HouseConstant.HOUSE_RENTWAY_HOUSE) {
                entity.setHouseFid(houseDetailDto.getFid());
            }
            if (houseDetailDto.getRentWay() == HouseConstant.HOUSE_RENTWAY_ROOM) {
                entity.setRoomFid(houseDetailDto.getFid());
            }
            String paramJson = JsonTransform.Object2Json(entity);
            LogUtil.info(LOGGER, "查询今夜特价paramJson:{}", paramJson);
            String resultJson = houseCommonService.getEffectiveOfJYTJInfo(paramJson);
            LogUtil.info(LOGGER, "查询是否设置今夜特价结果：" + resultJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(LOGGER, "查询设置今夜特价错误，paramJosn:{}, dto:{}", paramJson, dto.toJsonString());
                return;
            }
            HouseTonightPriceInfoVo houseTonightPriceInfoVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "data", HouseTonightPriceInfoVo.class);
            if (Check.NuNObj(houseTonightPriceInfoVo)) {
                LogUtil.info(LOGGER, "查询设置今夜特价返回为null，paramJosn:{}", paramJson);
                return;
            }
            if (houseTonightPriceInfoVo.getCountdownBegin() <= 0 && !houseTonightPriceInfoVo.isEffective()) {
                //小于等于0 && !isEffective, 当期那时间在设置时间之后
                LogUtil.info(LOGGER, "今夜特价设置已失效, paramJson:{}", paramJson);
                return;
            }

            String lockJson = houseCommonService.isHousePayLockCurrentDay(paramJson);
            LogUtil.info(LOGGER, "查询房源锁结果：" + lockJson);
            DataTransferObject lockDto = JsonEntityTransform.json2DataTransferObject(lockJson);
            if (lockDto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(LOGGER, "获取房源日期锁信息失败， paramJson：{}", paramJson);
                return;
            }
            Boolean isLock = SOAResParseUtil.getValueFromDataByKey(lockJson, "data", Boolean.class);
            if (isLock) {
                LogUtil.info(LOGGER, "当天房源被锁，返回房源原价，lockJson:{}" + lockJson);
                return;
            }


            //4.计算今夜特价信息
            long remainTime = houseTonightPriceInfoVo.getCountdownBegin();
            long deadlineRemainTime = houseTonightPriceInfoVo.getCountdownEnd();
            tenantHouseDetailVo.setIsToNightDiscount(1);
            ToNightDiscount tonightDiscount = new ToNightDiscount();
            tonightDiscount.setOpenTime(getHourAndMin(houseTonightPriceInfoVo.getStartTime()));
            tonightDiscount.setDeadlineTime(getHourAndMin(houseTonightPriceInfoVo.getEndTime()));
            tonightDiscount.setRemainTime(remainTime);
            tonightDiscount.setDeadlineRemainTime(deadlineRemainTime);
            tonightDiscount.setTipsNname(LabelTipsEnum.IS_TODAY_DISCOUNT.getName());

            //设置开抢提示
            String startTimeTipsJson = staticResourceService.findStaticResListByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_STARTTIME_TIPS);
            List<StaticResourceVo> resList = SOAResParseUtil.getListValueFromDataByKey(startTimeTipsJson, "staticResList", StaticResourceVo.class);
            if (!Check.NuNCollection(resList)) {
                StaticResourceVo staticResourceVo = resList.get(resList.size() - 1);
                if (!Check.NuNObj(staticResourceVo)) {
                    tonightDiscount.setOpenTimeTips(staticResourceVo.getResContent());
                }
            }

            //设置进行中提示
            if (houseTonightPriceInfoVo.isEffective()) {
                //只有设置有效并且开始倒计时为0，才是活动进行中
                String goingTipsJson = staticResourceService.findStaticResListByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_GOING_TIPS);
                List<StaticResourceVo> goingResList = SOAResParseUtil.getListValueFromDataByKey(goingTipsJson, "staticResList", StaticResourceVo.class);
                if (!Check.NuNCollection(resList)) {
                    StaticResourceVo staticResourceVo = goingResList.get(goingResList.size() - 1);
                    if (!Check.NuNObj(staticResourceVo)) {
                        tonightDiscount.setGoingTips(staticResourceVo.getResContent());
                    }
                }
            }

            //设置倒计时结束提示
            String endTimeTipsJson = staticResourceService.findStaticResListByResCode(SearchConstant.StaticResourceCode.TODAY_DISCOUNT_ENDTIME_TIPS);
            List<StaticResourceVo> endResList = SOAResParseUtil.getListValueFromDataByKey(endTimeTipsJson, "staticResList", StaticResourceVo.class);
            if (!Check.NuNCollection(endResList)) {
                StaticResourceVo staticResourceVo = endResList.get(endResList.size() - 1);
                if (!Check.NuNObj(staticResourceVo)) {
                    tonightDiscount.setDeadlineTimeTips(staticResourceVo.getResContent());
                }
            }
            //设置活动价格
            Double discount = houseTonightPriceInfoVo.getDiscount();
            Integer housePrice = tenantHouseDetailVo.getHousePrice();
            Double todayPrice = BigDecimalUtil.mul(discount, housePrice.doubleValue());
            tonightDiscount.setTonightPrice(todayPrice.intValue());
            tonightDiscount.setTonightDiscount(discount);

            //兼容老版本，今夜特价生效时，设置当天价格为特价,并返回标签原价
            Header header = getHeader(request);
            LogUtil.info(LOGGER, "verisonCode:{}", header.getVersionCode());
            if (houseTonightPriceInfoVo.isEffective() && !Check.NuNObj(houseDetailNewVo)
                    && (Check.NuNObj(header.getVersionCode()) || header.getVersionCode() < 100015)) {
                LogUtil.info(LOGGER, "版本号是:{}", header.getVersionCode());
                tenantHouseDetailVo.setOriginalPrice(housePrice);
                tenantHouseDetailVo.setHousePrice(todayPrice.intValue());
                List<LabelTipsEntity> labelTipsList = houseDetailNewVo.getLabelTipsList();
                LabelTipsEntity labelEntity = new LabelTipsEntity();
                labelEntity.setName(LabelTipsEnum.IS_TODAY_DISCOUNT.getName());
                labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
                labelTipsList.add(0, labelEntity);
                houseDetailNewVo.setLabelTipsList(labelTipsList);
            }
            LogUtil.info(LOGGER, "房源今夜特价为:{}", tonightDiscount.getTonightPrice());
            tenantHouseDetailVo.setToNightDiscount(tonightDiscount);
        } catch (SOAParseException e) {
            LogUtil.error(LOGGER, "查询今夜特价异常house_fid:{}, room_fid:{}, rent_way:{}, e:{}", entity.getHouseFid(), entity.getRoomFid(), entity.getRentWay(), e);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "设置今夜特价异常house_fid:{}, room_fid:{}, rent_way:{}, e:{}", entity.getHouseFid(), entity.getRoomFid(), entity.getRentWay(), e);
        }
        LogUtil.info(LOGGER, "housePrice:{}, originalPrice:{}", tenantHouseDetailVo.getHousePrice(), tenantHouseDetailVo.getOriginalPrice());
    }

    /**
     * 获取日期中的 小时:分钟
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/5/15 17:08
     */
    private String getHourAndMin(Date date) {
        String dateStr = DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss");
        String result = dateStr.substring(dateStr.indexOf(" "), dateStr.lastIndexOf(":"));
        return result;

    }

    /**
     * 租房房源的配置信息
     * 1. 房源设置说明
     * 服务展示2个  设施  电器  卫浴 各展示一个
     *
     * @param tenantHouseDetailVo
     * @param version
     * @author yd
     * @created 2016年12月5日 下午4:27:57
     */
    private void setHouseConfVo(TenantHouseDetailVo tenantHouseDetailVo, String version) {

        try {
            List<HouseConfVo> serveList = tenantHouseDetailVo.getServeList();
            if (!Check.NuNCollection(serveList)) {
                //服务
                String serveJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0015.getValue());
                List<EnumVo> serveListAll = SOAResParseUtil.getListValueFromDataByKey(serveJson, "selectEnum", EnumVo.class);
                for (HouseConfVo vo : serveList) {

                    for (EnumVo enumVo : serveListAll) {
                        if (vo.getDicValue().equals(enumVo.getKey())) {
                            vo.setDicName(enumVo.getText());
                            break;
                        }
                    }
                }
            }

            List<HouseConfVo> facilityList = tenantHouseDetailVo.getFacilityList();

            //配套设施组装
            String facilityJson = cityTemplateService.getSelectSubDic(null, ProductRulesEnum.ProductRulesEnum002.getValue());
            List<EnumVo> facilitylistAll = SOAResParseUtil.getListValueFromDataByKey(facilityJson, "subDic", EnumVo.class);
            for (HouseConfVo vo : facilityList) {
                for (EnumVo enumVo : facilitylistAll) {
                    if (vo.getDicCode().equals(enumVo.getKey())) {
                        for (EnumVo valVo : enumVo.getSubEnumVals()) {
                            if (vo.getDicValue().equals(valVo.getKey())) {
                                vo.setDicName(valVo.getText());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            //删除配套设施沙发
            for (HouseConfVo vo : tenantHouseDetailVo.getFacilityList()) {
                if (vo.getDicCode().equals("ProductRulesEnum002003") && vo.getDicValue().equals("1")) {
                    tenantHouseDetailVo.getFacilityList().remove(vo);
                    break;
                }
            }

            HouseServiceFacEnum.House_Bathroom.sortHouseServiceFac(tenantHouseDetailVo, this.STATIC_URL);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "房源详情,版本号v={}——设置房源配置信息,e={}", version, e);
        }

    }

    /**
     * 设置房源 描述信息以及周边情况信息
     *
     * @param tenantHouseDetailVo
     * @param version
     * @author yd
     * @created 2016年12月2日 下午5:11:52
     */
    private void setMercureInfoVo(TenantHouseDetailVo tenantHouseDetailVo, String version) {

        MercureInfoVo mercureInfoVo = new MercureInfoVo();
        mercureInfoVo.setHouseAroundTitle(ApiMessageConst.HOUSE_DETAIL_AROUND_TITLE);
        mercureInfoVo.setMercureDesTitle(ApiMessageConst.HOUSE_DETAIL_MERCURE_DES_TITLE);
        mercureInfoVo.setMercureTitle(ApiMessageConst.HOUSE_DETAIL_MERCURE_TITLE);
        if (!Check.NuNObj(tenantHouseDetailVo)) {
            mercureInfoVo.setHouseDesc(tenantHouseDetailVo.getHouseDesc());
            mercureInfoVo.setHouseAroundDesc(tenantHouseDetailVo.getHouseAroundDesc());
            StringBuffer mercureDesHouseInfo = new StringBuffer();
            mercureDesHouseInfo.append("居住面积:" + tenantHouseDetailVo.getHouseArea() + "平米\n");

            Integer rentWay = tenantHouseDetailVo.getRentWay();
            if (!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()) {
                mercureDesHouseInfo.append(tenantHouseDetailVo.getIsTogetherLandlord() == 0 ? "不与房东同住\n" : "与房东同住\n");
            }
            mercureInfoVo.setMercureDesHouseInfo(mercureDesHouseInfo.toString());

        }
        tenantHouseDetailVo.setMercureInfoVo(mercureInfoVo);
    }

    /**
     * 设置 房源名称 下 ，房源相关信息
     *
     * @param tenantHouseDetailVo
     * @param version
     * @author yd
     * @created 2016年12月2日 下午3:03:28
     */
    private void setHouseNameInfoList(TenantHouseDetailVo tenantHouseDetailVo, String version) {

        try {
            //处理出租方式名称
            LinkedList<String> houseNameInfoList = new LinkedList<String>();
            int rentWay = tenantHouseDetailVo.getRentWay();
            RentWayEnum re = RentWayEnum.getRentWayByCode(rentWay);
            /**
             * 增加共享客厅校验
             */
            if (re.getCode() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(tenantHouseDetailVo.getRoomType()) && tenantHouseDetailVo.getRoomType() == RoomTypeEnum.HALL_TYPE.getCode()) {
                re = RentWayEnum.HALL;
            }
            houseNameInfoList.add(re == null ? "" : re.getAppRentWayName());

            if (!Check.NuNObj(tenantHouseDetailVo.getCheckInLimit())) {
                int checkInLimit = tenantHouseDetailVo.getCheckInLimit();
                houseNameInfoList.add(checkInLimit == 0 ? "不限人数" : "可住" + tenantHouseDetailVo.getCheckInLimit() + "人");
            }


            if (!Check.NuNObj(tenantHouseDetailVo.getRoomNum())) {
                houseNameInfoList.add(tenantHouseDetailVo.getRoomNum() + "居室");
            }

            String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
            List<EnumVo> bedTypelist = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);

            List<HouseBedNumVo> listBedVo = tenantHouseDetailVo.getBedList();
            if (!Check.NuNCollection(listBedVo)) {
                int bedTotalCount = 0;
                Map<Integer, Integer> numMap = new HashMap<>();
                for (HouseBedNumVo bedVo : tenantHouseDetailVo.getBedList()) {

                    Integer bedType = bedVo.getBedType();
                    Integer oNum = bedVo.getBedNum();
                    if (numMap.containsKey(bedType)) {
                        Integer bedNum = numMap.get(bedType);
                        numMap.put(bedType, bedNum + oNum);
                    } else {
                        numMap.put(bedType, oNum);
                    }

                }
                //计算床数量
                List<HouseBedNumVo> houseBedVo = new ArrayList<>();
                for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
                    HouseBedNumVo vo = new HouseBedNumVo();
                    for (EnumVo enumVo : bedTypelist) {
                        if (entry.getKey().toString().equals(enumVo.getKey())) {
                            vo.setBedNum(entry.getValue());
                            vo.setBedTypeName(enumVo.getText());
                            vo.setBedType(entry.getKey());
                            houseBedVo.add(vo);
                            bedTotalCount = bedTotalCount + entry.getValue();
                            break;
                        }
                    }
                }
                tenantHouseDetailVo.setBedList(houseBedVo);

                if (bedTotalCount > 0) {
                    houseNameInfoList.add(bedTotalCount + "张床");
                }
            }

            if (rentWay == RentWayEnum.HOUSE.getCode()
                    && !Check.NuNObj(tenantHouseDetailVo.getToiletNum())) {
                houseNameInfoList.add(tenantHouseDetailVo.getToiletNum() + "卫");
            }

            int istoilet = tenantHouseDetailVo.getIstoilet() == null ? 0 : tenantHouseDetailVo.getIstoilet();
            if (rentWay == RentWayEnum.ROOM.getCode()) {
                houseNameInfoList.add(istoilet == 0 ? "共卫" : "独卫");
            }
            tenantHouseDetailVo.setHouseNameInfoList(houseNameInfoList);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "房源详情,版本号v={}——设置房源名称下信息,e={}", version, e);
        }

    }

    /**
     * 房东信息获取
     *
     * @param tenantHouseDetailVo
     * @author yd
     * @created 2016年12月2日 上午11:01:42
     */
    private void setLanlordInfo(TenantHouseDetailVo tenantHouseDetailVo) {

        try {
            String landlordJson = customerMsgManagerService.getCutomerVoFromDb(tenantHouseDetailVo.getLandlordUid());
            CustomerVo landlord = SOAResParseUtil.getValueFromDataByKey(landlordJson, "customerVo", CustomerVo.class);
            if (!Check.NuNObj(landlord)) {
                tenantHouseDetailVo.setLandlordIcon(landlord.getUserPicUrl());
                //默认显示昵称，如果没有显示真实姓名
                if (Check.NuNStr(landlord.getNickName())) {
                    tenantHouseDetailVo.setLandlordName(landlord.getRealName());
                } else {
                    tenantHouseDetailVo.setLandlordName(landlord.getNickName());
                }
                tenantHouseDetailVo.setLandlordMobile(landlord.getHostNumber() + "," + landlord.getZiroomPhone());

                tenantHouseDetailVo.setCustomerIntroduce(landlord.getCustomerIntroduce());
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "房源详情V1——房东信息获取失败,e={}", e);
        }

    }

    /**
     * 房源详情——评论相关信息 获取
     *
     * @param tenantHouseDetailVo
     * @author yd
     * @created 2016年12月2日 上午10:53:40
     */
    private void setHouseEvaInfo(TenantHouseDetailVo tenantHouseDetailVo) {

        //查询评论数和星级判断
        StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
        try {
            if (tenantHouseDetailVo.getRentWay() == 0) {
                statsHouseEvaRequest.setHouseFid(tenantHouseDetailVo.getFid());
            } else if (tenantHouseDetailVo.getRentWay() == 1) {
                statsHouseEvaRequest.setRoomFid(tenantHouseDetailVo.getFid());
            }
            statsHouseEvaRequest.setRentWay(tenantHouseDetailVo.getRentWay());
            String evaluateCountJson = evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));

            List<StatsHouseEvaEntity> evaluateStats = SOAResParseUtil.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
            if (!Check.NuNCollection(evaluateStats)) {
                LogUtil.debug(LOGGER, "查询评论数结果：" + JsonEntityTransform.Object2Json(evaluateStats));
                StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
                Float sumStar = (statsHouseEvaEntity.getHouseCleanAva() + statsHouseEvaEntity.getDesMatchAva() + statsHouseEvaEntity.getSafeDegreeAva() + statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity.getCostPerforAva()) / 5;
                BigDecimal bigDecimal = new BigDecimal(sumStar.toString());
                tenantHouseDetailVo.setGradeStar(bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).floatValue());
                tenantHouseDetailVo.setEvaluateCount(statsHouseEvaEntity.getEvaTotal());

            } else {
                //默认5星
                tenantHouseDetailVo.setGradeStar(new BigDecimal(5).floatValue());
                tenantHouseDetailVo.setEvaluateCount(0);
            }

            //获取当前房源最新评论
            EvaluateRequest evaluateRequest = new EvaluateRequest();
            if (tenantHouseDetailVo.getRentWay() == 0) {
                evaluateRequest.setHouseFid(tenantHouseDetailVo.getFid());
            } else if (tenantHouseDetailVo.getRentWay() == 1) {
                evaluateRequest.setRoomFid(tenantHouseDetailVo.getFid());
            }
            evaluateRequest.setRentWay(tenantHouseDetailVo.getRentWay());
            evaluateRequest.setLimit(1);
            String evaluatePageJson = evaluateOrderService.queryTenantEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest));
            List<TenantEvaluateVo> evaList = SOAResParseUtil.getListValueFromDataByKey(evaluatePageJson, "listTenantEvaluateVo", TenantEvaluateVo.class);
            if (!Check.NuNCollection(evaList)) {
                TenantEvaluateVo tenantEvaluateVo = evaList.get(0);
                TenantEvalVo tenantEvalVo = new TenantEvalVo();
              //以后获取用户名
                String customerJson = customerMsgManagerService.getCutomerVo(tenantEvaluateVo.getEvaUserUid());
                CustomerVo customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
                if (!Check.NuNObj(customer)) {
                    tenantEvalVo.setCustomerName(Check.NuNStr(customer.getNickName()) ? EvaluateConst.TENANT_DEFAULT_NAME : customer.getNickName());
                }
                tenantEvalVo.setEvalContent(tenantEvaluateVo.getContent());
//			    tenantEvalVo.setEvalDate(DateUtil.dateFormat(evaList.get(0).getCreateTime())); 
                tenantEvalVo.setEvalDate(DateUtil.dateFormat(tenantEvaluateVo.getCreateTime(), "yyyy年MM月"));
                tenantEvalVo.setCustomerIcon(customer.getUserPicUrl());
                tenantHouseDetailVo.setTenantEvalVo(tenantEvalVo);

                DataTransferObject replyDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.findEvaReplyByOrderSn(tenantEvaluateVo.getOrderSn()));
                if (replyDto.getCode() == DataTransferObject.SUCCESS) {
                    LandlordReplyEntity landlordReplyEntity = replyDto.parseData("landlordReply", new TypeReference<LandlordReplyEntity>() {
                    });
                    if (!Check.NuNObj(landlordReplyEntity)) {
                        tenantEvalVo.setLandlordEvalContent(landlordReplyEntity.getContent());
                    }
                }

            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "房源详情V1——评论信息获取失败,e={}", e);
        }

    }


    /**
     * 设置房源详情页 图片集合
     * 说明: 此处处理 把默认图片 放在集合的第一位
     *
     * @param tenantHouseDetailVo
     * @author yd
     * @created 2016年12月2日 上午10:45:30
     */
    private void setPicDisList(TenantHouseDetailVo tenantHouseDetailVo) {

        if (Check.NuNObj(tenantHouseDetailVo)) return;
        //处理新的图片列表
        if (!Check.NuNCollection(tenantHouseDetailVo.getPicDisList())) {//处理新的图片列表
            List<MinsuEleEntity> picDisList = tenantHouseDetailVo.getPicDisList();
            MinsuEleEntity defPic = null;
            String def = tenantHouseDetailVo.getDefaultPic();
            for (int i = 0; i < picDisList.size(); i++) {//取默认图片
                MinsuEleEntity picEntity = picDisList.get(i);
                if (!Check.NuNObj(def) && !Check.NuNObj(picEntity) && def.indexOf(picEntity.getEleValue()) > -1) {
                    defPic = picEntity;
                    picDisList.remove(i);
                    break;
                }
            }
            List<MinsuEleEntity> picDisListNew = new LinkedList<MinsuEleEntity>();
            if (!Check.NuNObj(defPic)) {
                picDisListNew.add(defPic);
            }
            if (picDisList.size() > 0) {
                picDisListNew.addAll(picDisList);
            }
            tenantHouseDetailVo.setPicDisList(picDisListNew);
        }

        //如果图片列表为空，添加默认图片
        if (!Check.NuNCollection(tenantHouseDetailVo.getPicDisList())) {
            for (MinsuEleEntity eleEntity : tenantHouseDetailVo.getPicDisList()) {
                eleEntity.setEleValue(PicUtil.getSpecialPic(picBaseAddrMona, eleEntity.getEleValue(), detail_big_pic));
            }
        } else {
            MinsuEleEntity eleEntity = new MinsuEleEntity();
            eleEntity.setEleKey("卧室");
            eleEntity.setEleValue(tenantHouseDetailVo.getDefaultPic());
            tenantHouseDetailVo.getPicDisList().add(eleEntity);
        }

    }

    /**
     * 分享房源地址处理
     *
     * @param houseDetailDto
     * @param tenantHouseDetailVo
     * @author yd
     * @created 2016年12月2日 上午10:37:42
     */
    private void setHouseShareUrl(HouseDetailDto houseDetailDto, TenantHouseDetailVo tenantHouseDetailVo) {

        String houseShareUrl = new StringBuilder(house_share_url).append("tenantHouse/").append(no_lgin_auth)
                .append("/houseDetail?fid=").append(houseDetailDto.getFid()).append("&rentWay=")
                .append(houseDetailDto.getRentWay()).toString();
        tenantHouseDetailVo.setHouseShareUrl(houseShareUrl);
    }

    /**
     * 分享房源地址处理
     *
     * @param houseDetailDto
     * @param tenantHouseDetailVo
     * @author yd
     * @created 2016年12月2日 上午10:37:42
     */
    private void setTopHouseShareUrl(HouseDetailDto houseDetailDto, TenantHouseDetailVo tenantHouseDetailVo) {

        String houseShareUrl = new StringBuilder(house_share_url).append("houseTop/").append(no_lgin_auth)
                .append("/houseDetail?fid=").append(houseDetailDto.getFid()).append("&rentWay=")
                .append(houseDetailDto.getRentWay()).toString();
        tenantHouseDetailVo.setHouseShareUrl(houseShareUrl);
    }

    /**
     * 跳转退订政策页面
     *
     * @param tenantHouseDetailVo
     * @return
     * @author liujun
     * @created 2016年5月28日
     */
    public void getTradeRulesNew(TenantHouseDetailVo tenantHouseDetailVo) {
        TradeRulesVo tradeRulesVo = houseService.getTradeRulesCommon(tenantHouseDetailVo.getCheckOutRulesCode());
        if (!Check.NuNObj(tradeRulesVo)) {

            tenantHouseDetailVo.setUnregText1(tradeRulesVo.getUnregText1());
            tenantHouseDetailVo.setUnregText2(tradeRulesVo.getCheckInOnNameM());
            tenantHouseDetailVo.setUnregText3(tradeRulesVo.getCheckOutEarlyNameM());
            String string = tradeRulesVo.getCheckInOnNameM() + "\r\n" + tradeRulesVo.getCheckInOnNameM() + "\r\n" + tradeRulesVo.getCheckOutEarlyNameM()
                    + "\r\n" + tradeRulesVo.getCommonShowName();
            if (!Check.NuNStr(tradeRulesVo.getExplain())) {
                string = string + "\r\n" + tradeRulesVo.getExplain();
            }
            tenantHouseDetailVo.setTradeRulesShowName(string);
        }
    }

    /**
     * 旧版接口
     * 房源评价列表
     *
     * @return
     * @author bushujie
     * @created 2016年5月3日 上午10:05:26
     */
    @RequestMapping("${UNLOGIN_AUTH}/houseEvaluate")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> houseEvaluate(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "参数：" + paramJson);
            ValidateResult<HouseEvaluate> validateResult = paramCheckLogic.checkParamValidate(paramJson,
                    HouseEvaluate.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
            }
            HouseEvaluate houseEvaluate = validateResult.getResultObj();

            DataTransferObject dto = getStatsHouseEva(houseEvaluate);

            if (dto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }
            //定义返回参数
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("statsHouseEva", dto.getData().get("statsHouseEva"));


            //房源评价列表
            EvaluateRequest evaluateRequest = new EvaluateRequest();
            if (houseEvaluate.getRentWay() == 0) {
                evaluateRequest.setHouseFid(houseEvaluate.getFid());
            } else if (houseEvaluate.getRentWay() == 1) {
                evaluateRequest.setRoomFid(houseEvaluate.getFid());
            }
            evaluateRequest.setRentWay(houseEvaluate.getRentWay());
            evaluateRequest.setLimit(houseEvaluate.getLimit());
            evaluateRequest.setPage(houseEvaluate.getPage());
            String evaluatePageJson = evaluateOrderService.queryTenantEvaluateByPage(JsonEntityTransform.Object2Json(evaluateRequest));
            dto = JsonEntityTransform.json2DataTransferObject(evaluatePageJson);
            //判断服务是否有错误
            if (dto.getCode() == 1) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }

            int total = SOAResParseUtil.getIntFromDataByKey(evaluatePageJson, "total");
            returnMap.put("total", total);

            List<TenantEvaluateVo> evaList = SOAResParseUtil.getListValueFromDataByKey(evaluatePageJson, "listTenantEvaluateVo", TenantEvaluateVo.class);
            List<TenantEvalVo> tenantEvalVos = new ArrayList<TenantEvalVo>();
            if (!Check.NuNCollection(evaList)) {
                for (TenantEvaluateVo vo : evaList) {
                    TenantEvalVo tenantEvalVo = new TenantEvalVo();
                    //以后获取用户名
                    String customerJson = customerMsgManagerService.getCutomerVo(vo.getEvaUserUid());
                    CustomerVo customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
                    if (!Check.NuNObj(customer)) {
                        tenantEvalVo.setCustomerName(Check.NuNStr(customer.getNickName())
                                ? EvaluateConst.TENANT_DEFAULT_NAME : customer.getNickName());
                        tenantEvalVo.setCustomerIcon(customer.getUserPicUrl());
                    }
                    tenantEvalVo.setEvalContent(vo.getContent());
//					tenantEvalVo.setEvalDate(DateUtil.dateFormat(vo.getCreateTime()));
                    tenantEvalVo.setEvalDate(DateUtil.dateFormat(vo.getCreateTime(), "yyyy年MM月"));

                    //查询订单关联房东对房客的评价
                    EvaluateRequest evaRequest = new EvaluateRequest();
                    evaRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
                    evaRequest.setOrderSn(vo.getOrderSn());
                    String landlordEvalJson = evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaRequest));
                    DataTransferObject landlordEvalDto = JsonEntityTransform.json2DataTransferObject(landlordEvalJson);
                    Map<String, Object> landlordEvalMap = landlordEvalDto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
                    });

                    List<EvaluateOrderEntity> listOrderEvaluateOrderEntities = null;
                    if (!Check.NuNMap(landlordEvalMap) && landlordEvalMap.get("listOrderEvaluateOrder") != null) {
                        listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(landlordEvalMap.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
                        if (!Check.NuNCollection(listOrderEvaluateOrderEntities)) {
                            for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
//								if(evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()&&evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
//									if(!Check.NuNMap(landlordEvalMap)&&!Check.NuNObj( landlordEvalMap.get("landlordEvaluate"))){
//										LandlordEvaluateEntity landlordEvaluateEntity=JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(landlordEvalMap.get("landlordEvaluate")),LandlordEvaluateEntity.class);
//										tenantEvalVo.setLandlordEvalContent(landlordEvaluateEntity.getContent());
//										break;
//									}
//								}

                                if (evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN_REPLY.getCode()) {
                                    if (!Check.NuNMap(landlordEvalMap) && !Check.NuNObj(landlordEvalMap.get("landlordReplyEntity"))) {
                                        LandlordReplyEntity landlordEvaluateEntity = JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(landlordEvalMap.get("landlordReplyEntity")), LandlordReplyEntity.class);
                                        tenantEvalVo.setLandlordEvalContent(landlordEvaluateEntity.getContent());
                                        break;
                                    }
                                }

                            }

                        }
                    }

                    tenantEvalVos.add(tenantEvalVo);
                }
            }
            returnMap.put("evaList", tenantEvalVos);

            LogUtil.info(LOGGER, "房源评价查询结果={}", JsonEntityTransform.Object2Json(returnMap));

            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(returnMap), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }


    /**
     * 获取房源的评价统计分数  （房源评价通用方法 抽取出来）
     *
     * @param
     * @return
     * @author jixd
     * @created 2016年11月11日 15:22:30
     */
    public DataTransferObject getStatsHouseEva(HouseEvaluate houseEvaluate) {
        DataTransferObject resultDto = new DataTransferObject();
        try {
            //评价综合信息查询
            StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
            if (houseEvaluate.getRentWay() == 0) {
                statsHouseEvaRequest.setHouseFid(houseEvaluate.getFid());
            } else if (houseEvaluate.getRentWay() == 1) {
                statsHouseEvaRequest.setRoomFid(houseEvaluate.getFid());
            }
            statsHouseEvaRequest.setRentWay(houseEvaluate.getRentWay());
            String evaluateCountJson = evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(evaluateCountJson);
            //判断服务是否有错误
            if (dto.getCode() == 1) {
                return dto;
            }
            List<StatsHouseEvaEntity> evaluateStats = SOAResParseUtil.getListValueFromDataByKey(evaluateCountJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
            StatsHouseEvaVo statsHouseEvaVo = new StatsHouseEvaVo();
            if (!Check.NuNCollection(evaluateStats)) {
                StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
                Float sumStar = (statsHouseEvaEntity.getHouseCleanAva() + statsHouseEvaEntity.getDesMatchAva() + statsHouseEvaEntity.getSafeDegreeAva() + statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity.getCostPerforAva());
                statsHouseEvaVo.setTotalAvgGrade(ValueUtil.getEvaluteSoreDefault(ValueUtil.getStrValue(sumStar / 5)).floatValue());
                statsHouseEvaVo.setRealTotalAvgGrade(ValueUtil.getRealEvaluteSore(ValueUtil.getStrValue(sumStar / 5)).floatValue());
                statsHouseEvaVo.setHouseCleanAva(statsHouseEvaEntity.getHouseCleanAva());
                statsHouseEvaVo.setDesMatchAva(statsHouseEvaEntity.getDesMatchAva());
                statsHouseEvaVo.setSafeDegreeAva(statsHouseEvaEntity.getSafeDegreeAva());
                statsHouseEvaVo.setTrafPosAva(statsHouseEvaEntity.getTrafPosAva());
                statsHouseEvaVo.setCostPerforAva(statsHouseEvaEntity.getCostPerforAva());
            }
            dto.putValue("statsHouseEva", statsHouseEvaVo);
            return dto;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询房源统计异常e={}", e);
            resultDto.setErrCode(DataTransferObject.ERROR);
            resultDto.setMsg("服务错误");
            return resultDto;
        }
    }

    public static void main(String[] args) {
        Float sumStar = (4.9F + 5F + 5F + 5F + 5F) / 5;
        System.out.println(sumStar);
        BigDecimal bigDecimal = new BigDecimal(sumStar.toString());
        System.out.println(bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).floatValue());
        ;
    }
}
