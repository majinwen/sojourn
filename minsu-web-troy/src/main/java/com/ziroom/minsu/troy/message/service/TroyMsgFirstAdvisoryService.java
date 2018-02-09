package com.ziroom.minsu.troy.message.service;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.entity.HouseMsgVo;
import com.ziroom.minsu.services.message.api.inner.MsgAdvisoryFollowupService;
import com.ziroom.minsu.services.message.api.inner.MsgFirstAdvisoryService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseSearchOneRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.troy.message.vo.FirstAdvisoryFollowVO;
import com.ziroom.minsu.troy.message.vo.MsgAdvisoryFollowLogVO;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.MsgAdvisoryFollowEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwt
 * @version 1.0
 * @since 1.0
 */
@Service("message.TroyMsgFirstAdvisoryService")
public class TroyMsgFirstAdvisoryService {

    private static final Logger logger = LoggerFactory.getLogger(TroyMsgFirstAdvisoryService.class);

    @Value("#{'${pic_size}'.trim()}")
    private String picSize;

    @Resource(name = "message.msgFirstAdvisoryService")
    private MsgFirstAdvisoryService msgFirstAdvisoryService;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;

    @Resource(name = "house.troyHouseMgtService")
    private TroyHouseMgtService troyHouseMgtService;

    @Resource(name = "basedata.confCityService")
    private ConfCityService confCityService;

    @Resource(name = "message.msgAdvisoryFollowupService")
    private MsgAdvisoryFollowupService msgAdvisoryFollowupService;

    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;


    /**
     * 获取im跟进详情页面信息
     * 1.城市
     * 2.首日租金
     * 3.首次咨询信息
     * 4.对话内容
     * 5.跟进记录
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/6/9 13:20
     */
    public void getMsgFromFirstAdvisory(ModelAndView mv, FirstAdvisoryFollowVO followVO, String msgBaseFid) {
        String msgFirstJson = msgFirstAdvisoryService.queryByMsgBaseFid(msgBaseFid);
        LogUtil.info(logger, "获取首次咨询信息返回:{}", msgFirstJson);
        DataTransferObject msgBaseDto = JsonEntityTransform.json2DataTransferObject(msgFirstJson);
        if (msgBaseDto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(logger, "获取首次咨询信息失败：{}", msgFirstJson);
            return;
        }
        MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = null;
        try {
            msgFirstAdvisoryEntity = SOAResParseUtil.getValueFromDataByKey(msgFirstJson, "data", MsgFirstAdvisoryEntity.class);
        } catch (SOAParseException e) {
            LogUtil.error(logger, "获取首次咨询信息异常：{}", e);
        }
        if (Check.NuNObj(msgFirstAdvisoryEntity)) {
            LogUtil.error(logger, "获取首次咨询信息返回null");
            return;
        }

        String msgContent = msgFirstAdvisoryEntity.getMsgContentExt();
        JSONObject json = JSONObject.parseObject(msgContent);
        Date startDate = json.getDate("startDate");
        Date endDate = json.getDate("endDate");
        Integer personNum = json.getInteger("personNum");
        String houseName = json.getString("houseName");
        String houseFid = json.getString("fid");
        Integer rentWay = json.getInteger("rentWay");
        followVO.setStartDate(startDate);
        followVO.setEndDate(endDate);
        followVO.setPersonNum(personNum);
        followVO.setHouseName(houseName);
        followVO.setMsgAdvisoryFid(msgFirstAdvisoryEntity.getFid());
        followVO.setMsgBaseFid(msgFirstAdvisoryEntity.getMsgBaseFid());
        followVO.setHouseFid(houseFid);
        followVO.setRentWay(rentWay);

        Date createDate = msgFirstAdvisoryEntity.getCreateTime();
        followVO.setCreateDate(createDate);

        //是否跟进结束
        Integer followStatus = msgFirstAdvisoryEntity.getFollowStatus();
        if (MsgAdvisoryFollowEnum.FOLLOWED.getCode().equals(followStatus)) {
            followVO.setIsFollowEnd(1);
        }
        //设置房客信息
        setTenantInfo(followVO, msgFirstAdvisoryEntity);

        //设置房东信息
        setLandlordInfo(followVO, msgFirstAdvisoryEntity);

        //设置城市名称
        setCityName(followVO, msgFirstAdvisoryEntity);

        //设置日租金
        setDayPrice(followVO, msgFirstAdvisoryEntity, startDate, endDate);

        //设置跟进记录
        if (!Check.NuNObj(mv)) {
            setFollowLog(mv, msgFirstAdvisoryEntity.getFid());
        }
    }

    /**
     * 设置房客信息
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/6/9 13:22
     */
    private void setTenantInfo(FirstAdvisoryFollowVO followVO, MsgFirstAdvisoryEntity msgFirstAdvisoryEntity) {
        String tenantUid = msgFirstAdvisoryEntity.getFromUid();
        String tenantJson = customerInfoService.getCustomerInfoByUid(tenantUid);
        LogUtil.info(logger, "获取房客信息返回：{}", tenantJson);
        DataTransferObject tenantDto = JsonEntityTransform.json2DataTransferObject(tenantJson);
        if (tenantDto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(logger, "获取房客信息失败：{}", tenantJson);
            return;
        }
        try {
            CustomerBaseMsgEntity customerBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(tenantJson, "customerBase", CustomerBaseMsgEntity.class);
            if (!Check.NuNObj(customerBaseMsgEntity)) {
                String tenantName = customerBaseMsgEntity.getRealName();
                String tenantMobile = customerBaseMsgEntity.getCustomerMobile();
                followVO.setTenantName(tenantName);
                followVO.setTenantMobile(tenantMobile);
            }
        } catch (SOAParseException e) {
            LogUtil.error(logger, "获取房客信息异常：{}", e);
        }
    }

    /**
     * /设置房东信息
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/6/9 13:22
     */
    private void setLandlordInfo(FirstAdvisoryFollowVO followVO, MsgFirstAdvisoryEntity msgFirstAdvisoryEntity) {
        String landlordUid = msgFirstAdvisoryEntity.getToUid();
        String landlordJson = customerInfoService.getCustomerInfoByUid(landlordUid);
        LogUtil.info(logger, "获取房东信息返回：{}", landlordJson);
        DataTransferObject landlordDto = JsonEntityTransform.json2DataTransferObject(landlordJson);
        if (landlordDto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(logger, "获取房东信息失败：{}", landlordJson);
            return;
        }
        try {
            CustomerBaseMsgEntity landlordEntity = SOAResParseUtil.getValueFromDataByKey(landlordJson, "customerBase", CustomerBaseMsgEntity.class);
            if (!Check.NuNObj(landlordEntity)) {
                String landlordName = landlordEntity.getRealName();
                String landlordMobile = landlordEntity.getCustomerMobile();
                followVO.setLandlordName(landlordName);
                followVO.setLandlordMobile(landlordMobile);
            }
        } catch (SOAParseException e) {
            LogUtil.error(logger, "获取房东信息异常：{}", e);
        }

    }

    /**
     * 设置城市名称
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/6/9 13:22
     */
    private void setCityName(FirstAdvisoryFollowVO followVO, MsgFirstAdvisoryEntity msgFirstAdvisoryEntity) {
        String houseFid = msgFirstAdvisoryEntity.getHouseFid();
        Integer rentWay = msgFirstAdvisoryEntity.getRentWay();

        String houseDetailJson = null;
        if (rentWay == RentWayEnum.HOUSE.getCode()) {
            houseDetailJson = troyHouseMgtService.searchHouseDetailByFid(houseFid);
        } else {
            houseDetailJson = troyHouseMgtService.searchRoomDetailByFid(houseFid);
        }
        LogUtil.info(logger, "获取房源详情返回:{}", houseDetailJson.toString());
        DataTransferObject houseDetailDto = JsonEntityTransform.json2DataTransferObject(houseDetailJson);
        if (houseDetailDto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(logger, "获取房源详情失败");
            return;
        }
        HouseMsgVo houseMsgVo = null;
        try {
            houseMsgVo = SOAResParseUtil.getValueFromDataByKey(houseDetailJson, "obj", HouseMsgVo.class);
        } catch (SOAParseException e) {
            LogUtil.error(logger, "获取房源详情异常：{}", e);
        }

        if (Check.NuNObj(houseMsgVo)) {
            LogUtil.error(logger, "获取房源详情返回houseMsgVo为null");
            return;
        }
        String cityCode = "";
        try {
            cityCode = houseMsgVo.getHousePhyMsg().getCityCode();
        } catch (NullPointerException e) {
            LogUtil.error(logger, "获取城市编码失败：{}", e);
            return;
        }

        String cityNameJson = confCityService.getCityNameByCode(cityCode);
        LogUtil.info(logger, "获取城市名称返回:{}", cityNameJson.toString());
        DataTransferObject cityNameDto = JsonEntityTransform.json2DataTransferObject(cityNameJson);
        if (cityNameDto.getCode() != DataTransferObject.SUCCESS) {
            LogUtil.error(logger, "获取城市名称失败");
            return;
        }
        try {
            String cityName = (String) cityNameDto.getData().get("cityName");
            followVO.setCityName(cityName);
            followVO.setCityCode(cityCode);
        } catch (Exception e) {
            LogUtil.error(logger, "获取城市名称异常：{}", e);
            return;
        }
    }

    /**
     * 设置日租金
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/6/9 13:22
     */
    private void setDayPrice(FirstAdvisoryFollowVO followVO, MsgFirstAdvisoryEntity msgFirstAdvisoryEntity, Date startDate, Date endDate) {
        try {
            HouseSearchOneRequest requset = new HouseSearchOneRequest();
            requset.setFid(msgFirstAdvisoryEntity.getHouseFid());
            requset.setRentWay(msgFirstAdvisoryEntity.getRentWay());
            requset.setPicSize(picSize);
            requset.setStartTime(DateUtil.dateFormat(startDate, "yyyy-MM-dd"));
            requset.setEndTime(DateUtil.dateFormat(endDate, "yyyy-MM-dd"));
            String params = JsonEntityTransform.Object2Json(requset);

            LogUtil.info(logger, "获取首日租金参数，hlr:{}", params);
            String resultJson = searchService.getOneHouseInfo(params);
            LogUtil.info(logger, "获取首日租金返回，resultJson:{}", resultJson);
            HouseInfoEntity houseInfoEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseInfo", HouseInfoEntity.class);
            if (Check.NuNObj(houseInfoEntity)) {
                LogUtil.error(logger, "获取首日租金返回houseInfoEntity为null");
                return;
            }
            Integer price = houseInfoEntity.getPrice();
            if (Check.NuNObj(price)) {
                LogUtil.error(logger, "获取首日租金返回price为null");
                return;
            }
            LogUtil.info(logger, "获取日租金返回price为:{}", price);
            followVO.setPrice(price / 100);
        } catch (SOAParseException e) {
            LogUtil.error(logger, "获取首日租金异常，e:{}", e);
            return;
        } catch (Exception e) {
            LogUtil.error(logger, "设置首日租金异常，e:{}", e);
            return;
        }
    }


    /**
     * 设置跟进记录
     * @param
     * @return
     * @author wangwentao
     * @created 2017/6/9 13:28
     */
    private void setFollowLog(ModelAndView mv, String msgAdvidoryId) {
        String followListJson = msgAdvisoryFollowupService.getAllByFisrtAdvisoryFid(msgAdvidoryId);
        try {
            List<MsgAdvisoryFollowupEntity> followupList = SOAResParseUtil.getListValueFromDataByKey(followListJson, "list", MsgAdvisoryFollowupEntity.class);
            if (!Check.NuNCollection(followupList)) {
                int size = followupList.size();
                List<MsgAdvisoryFollowLogVO> followLogList = new ArrayList<>(size);
                for (int i = 0; i < followupList.size(); i++) {
                    MsgAdvisoryFollowLogVO msgAdvisoryFollowLogVo = new MsgAdvisoryFollowLogVO();
                    BeanUtils.copyProperties(followupList.get(i), msgAdvisoryFollowLogVo);
                    msgAdvisoryFollowLogVo.setIndex(size--);
                    followLogList.add(msgAdvisoryFollowLogVo);
                }
                mv.addObject("followLog", followLogList);
            } else {
                LogUtil.info(logger, "获取跟进记录 followLog is null");
            }
        } catch (SOAParseException e) {
            LogUtil.error(logger, "获取跟进记录异常：{}", e);
        }
    }

}
