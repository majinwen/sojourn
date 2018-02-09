package com.ziroom.minsu.troy.house.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.*;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.AbHouseRelateEntity;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.airbnb.vo.AbHouseRelateVo;
import com.ziroom.minsu.services.house.api.inner.AbHouseService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房源日历同步</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/houseSyncCalendar")
public class HouseSyncCalendarController {

    @Value("#{'${minsu.spider.url}'.trim()}")
    private String minsuSpiderUrl;

    @Resource(name = "house.abHouseService")
    private AbHouseService abHouseService;

	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseCalendarController.class);

    /**
     * 显示
     * @author jixd
     * @created 2017年04月17日 11:57:05
     * @param
     * @return
     */
    @RequestMapping("/houseSyncList")
    public String relateList(HttpServletRequest request){
    	
    	request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
    	
        return "house/houseCalendar/houseSyncList";
    }


    /**
     * 保存日历ulr同步关系
     * @author jixd
     * @created 2017年04月19日 21:22:08
     * @param
     * @return
     */
    @RequestMapping("/saveHouseRelate")
    @ResponseBody
    public DataTransferObject saveHouseRelate(AbHouseRelateEntity abHouseRelateEntity){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(abHouseRelateEntity.getRentWay())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("出租方式为空");
            return dto;
        }
        if (Check.NuNStr(abHouseRelateEntity.getHouseFid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房源FID为空");
            return dto;
        }
        if (abHouseRelateEntity.getRentWay() == RentWayEnum.ROOM.getCode()){
            if (Check.NuNStr(abHouseRelateEntity.getRoomFid())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("房间FID为空");
                return dto;
            }
        }
        if (Check.NuNStr(abHouseRelateEntity.getCalendarUrl())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("日历url为空");
            return dto;
        }

        if(!checkCalendarUrl(abHouseRelateEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("当前日历url无效，请输入到浏览器地址栏看看是否可以下载日历文件，如果可以下载请稍后再次添加！");
            return dto;
        }

        String calendarUrl = abHouseRelateEntity.getCalendarUrl();
        String abSn = calendarUrl.substring(calendarUrl.lastIndexOf("/") + 1, calendarUrl.indexOf(".ics"));
        abHouseRelateEntity.setAbSn(abSn);
        String s = abHouseService.saveHouseRelate(JsonEntityTransform.Object2Json(abHouseRelateEntity));
        dto = JsonEntityTransform.json2DataTransferObject(s);

        return dto;
    }

    /**
     * 数据结构
     * @author jixd
     * @created 2017年04月17日 14:13:33
     * @param
     * @return
     */
    @RequestMapping("/dataList")
    @ResponseBody
    public PageResult dataList(AbHouseDto abHouseDto){
        PageResult pageResult = new PageResult();
        
        Map<String,CustomerBaseMsgEntity> landlordUidMap = new HashMap<>();
		// 房东姓名或房东手机不为空,调用用户库查询房东uid
		if(!Check.NuNStr(abHouseDto.getLandlordName()) || !Check.NuNStr(abHouseDto.getLandlordPhone())){
			CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
			paramDto.setRealName(abHouseDto.getLandlordName());
			paramDto.setCustomerMobile(abHouseDto.getLandlordPhone());
			paramDto.setIsLandlord(HouseConstant.IS_TRUE);

			String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
			// 判断调用状态
			if(customerDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
				return new PageResult();
			}
			List<CustomerBaseMsgEntity> customerList = customerDto.parseData("listCustomerBaseMsg",
					new TypeReference<List<CustomerBaseMsgEntity>>() {});
			// 如果查询结果为空,直接返回数据
			if(Check.NuNCollection(customerList)){
				LogUtil.info(LOGGER, "返回客户信息为空,参数:{}", JsonEntityTransform.Object2Json(paramDto));
				return new PageResult();
			} 
			for (CustomerBaseMsgEntity customerBaseMsg : customerList) {
				landlordUidMap.put(customerBaseMsg.getUid(), customerBaseMsg);
			} 
			abHouseDto.setLandlordUidList(new ArrayList<>(landlordUidMap.keySet()));
		}
        
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(abHouseService.listHouseRelateVoByPage(JsonEntityTransform.Object2Json(abHouseDto)));
        if (dto.getCode() == DataTransferObject.SUCCESS){
            Long total = dto.parseData("total", new TypeReference<Long>() {});
            List<AbHouseRelateVo> list = dto.parseData("list", new TypeReference<List<AbHouseRelateVo>>() {});
            if (!Check.NuNCollection(list)) {
            	for (AbHouseRelateVo abHouseRelateVo : list) {
        			if (Check.NuNObj(landlordUidMap.get(abHouseRelateVo.getLandlordUid()))) {
        				String customerJson= customerInfoService.getCustomerInfoByUid(abHouseRelateVo.getLandlordUid());
        				try {
							CustomerBaseMsgEntity customerBaseMsg = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
							if (!Check.NuNObj(customerBaseMsg)) {
								landlordUidMap.put(customerBaseMsg.getUid(), customerBaseMsg);
							}
						} catch (SOAParseException e) {
							LogUtil.error(LOGGER, "查询用户信息失败，uid={},e={}", abHouseRelateVo.getLandlordUid(),e);
						}
        			}
        			abHouseRelateVo.setLandlordName(landlordUidMap.get(abHouseRelateVo.getLandlordUid()).getRealName());
        			abHouseRelateVo.setLandlordMobile(landlordUidMap.get(abHouseRelateVo.getLandlordUid()).getCustomerMobile());
        		}    			
			}
            
            pageResult.setTotal(total);
            pageResult.setRows(list);
        }

        return pageResult;
    }

    /**
     * 同步单个日历
     * @author jixd
     * @created 2017年04月17日 17:02:48
     * @param
     * @return
     */
    @RequestMapping("/syncCalendar")
    @ResponseBody
    public DataTransferObject syncCalendar(AbHouseDto abHouseDto){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(abHouseDto)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数错误");
            return dto;
        }
        abHouseDto.setStatus(YesOrNoEnum.NO.getCode());
        dto = JsonEntityTransform.json2DataTransferObject(abHouseService.findHouseRelateByFid(abHouseDto.toJsonStr()));
        if (dto.getCode() == DataTransferObject.ERROR){
            return dto;
        }
        AbHouseRelateVo abHouseRelateVo = dto.parseData("obj", new TypeReference<AbHouseRelateVo>() {});
        if (Check.NuNObj(abHouseRelateVo)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("关系不存在");
            return dto;
        }

        // 同步单个日历
        dto = syncSingleHouse(abHouseRelateVo);

        return dto;
    }

    /**
     * 
     * 同步单个日历
     * 
     * @author zhangyl2
     * @created 2017年10月20日 17:45
     * @param 
     * @return 
     */
    private DataTransferObject syncSingleHouse(AbHouseRelateVo abHouseRelateVo){
        //请求spider项目操作
        DataTransferObject dto = new DataTransferObject();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("houseFid", abHouseRelateVo.getHouseFid());
        paramMap.put("roomFid", abHouseRelateVo.getRoomFid());
        paramMap.put("rentWay", String.valueOf(abHouseRelateVo.getRentWay()));
        paramMap.put("abSn", abHouseRelateVo.getAbSn());
        paramMap.put("calendarUrl", abHouseRelateVo.getCalendarUrl());
        String resultJson = CloseableHttpUtil.sendFormPost(minsuSpiderUrl + "/calendar/syncSingleHouse", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(resultJson);
        boolean success = false;
        if (jsonObject.getInteger("status") == 200) {
            success = jsonObject.getBoolean("data");
        }
        if(!success){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("同步失败，系统会稍后重试");
        }
        return dto;
    }

    /**
     * 
     * 检查日历url是否有效
     * 
     * @author zhangyl2
     * @created 2017年10月21日 16:23
     * @param 
     * @return 
     */
    private boolean checkCalendarUrl(AbHouseRelateEntity abHouseRelateEntity) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("houseFid", abHouseRelateEntity.getHouseFid());
        paramMap.put("roomFid", abHouseRelateEntity.getRoomFid());
        paramMap.put("rentWay", String.valueOf(abHouseRelateEntity.getRentWay()));
        paramMap.put("calendarUrl", abHouseRelateEntity.getCalendarUrl());

        String resultJson = CloseableHttpUtil.sendFormPost(minsuSpiderUrl + "/calendar/checkCalendarUrlAvailable", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(resultJson);
        if (jsonObject.getInteger("status") == 200) {
            return jsonObject.getBoolean("data");
        } else {
            return false;
        }
    }

    /**
     * 
     * 更新房源同步关系
     *
     * @author zyl
     * @created 2017年6月29日 上午10:18:17
     *
     * @param abHouseRelateVo
     * @return
     */
    @RequestMapping("/updateHouseRelate")
    @ResponseBody
    public DataTransferObject updateHouseRelate(AbHouseRelateVo abHouseRelateVo){
    	DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(abHouseService.updateHouseRelateByFid(JsonEntityTransform.Object2Json(abHouseRelateVo)));
    	return dto;
    }

}
