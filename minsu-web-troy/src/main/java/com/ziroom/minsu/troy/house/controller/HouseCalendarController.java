package com.ziroom.minsu.troy.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.entity.order.HouseLockLogEntity;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.common.constant.BaseConstant;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.common.valenum.FullCalendarEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.IsValidEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.LockTypeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * <p>房源日历管理</p>
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
@RequestMapping("house/houseCalendar")
@Controller
public class HouseCalendarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseCalendarController.class);

    @Value("#{'${minsu.house.calendar.export.url}'.trim()}")
    private String houseCalendarExportUrl;
    
    @Resource(name="house.houseIssueService")
    private HouseIssueService houseIssueService;

    @Resource(name="house.houseManageService")
    private HouseManageService houseManageService;

    @Resource(name="order.orderUserService")
    private OrderUserService orderUserService;

    @Resource(name = "order.houseCommonService")
    private HouseCommonService houseCommonService;

    private static final String DATE_DAY_PATTERN = "yyyy-MM-dd";

    private static final String START = "start";

    private static final String END = "end";

    /**
     * 跳转到房源出租日历设置页面
     * @author jixd
     * @created 2017年03月15日 17:41:31
     * @param
     * @return
     */
    @RequestMapping("/toHouseCalendar")
    public String toHouseCalendar(String fid,String rentWay,Model model){
        int rentIntWay = Integer.parseInt(rentWay);
        String name = "";
        String houseFid = "";
        String roomFid = "";
        if (rentIntWay == RentWayEnum.HOUSE.getCode()){
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseMsgByFid(fid));
            HouseBaseMsgEntity houseBaseMsgEntity = dto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
            name = houseBaseMsgEntity.getHouseName();
            houseFid = houseBaseMsgEntity.getFid();
        }
        if (rentIntWay == RentWayEnum.ROOM.getCode()){
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseRoomMsgByFid(fid));
            HouseRoomMsgEntity houseRoomMsgEntity = dto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {});
            name = houseRoomMsgEntity.getRoomName();
            houseFid = houseRoomMsgEntity.getHouseBaseFid();
            roomFid = houseRoomMsgEntity.getFid();
        }

        model.addAttribute("houseFid",houseFid);
        model.addAttribute("roomFid",roomFid);
        model.addAttribute("name",name);
        model.addAttribute("rentWay",rentWay);

        return "house/houseCalendar/houseCalendar";
    }

    /**
     * 获取房源日历数据
     * @author jixd
     * @created 2017年03月15日 19:36:37
     * @param
     * @return
     */
    @RequestMapping("/getHouseCalendarData")
    @ResponseBody
    public List<HouseFullCalendarItemVo> getHouseCalendarData(HttpServletRequest request){
        try{
            String houseFid = request.getParameter("houseFid");
            String roomFid = request.getParameter("roomFid");
            int rentWay = Integer.parseInt(request.getParameter("rentWay"));
            String startTime = request.getParameter("start");
            String endTime = request.getParameter("end");
            Date startDate = DateUtil.parseDate(startTime,DATE_DAY_PATTERN);
            Date endDate = DateUtil.parseDate(endTime,DATE_DAY_PATTERN);



            LeaseCalendarDto sepcialPriceRequest = new LeaseCalendarDto();
            sepcialPriceRequest.setHouseBaseFid(houseFid);
            if(!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()){
                sepcialPriceRequest.setHouseRoomFid(roomFid);
            }
            sepcialPriceRequest.setStartDate(startDate);
            sepcialPriceRequest.setEndDate(endDate);
            sepcialPriceRequest.setRentWay(rentWay);
            sepcialPriceRequest.setIsValid(IsValidEnum.WEEK_OPEN.getCode());

            String specialPriceStr = houseManageService.leaseCalendar(JsonEntityTransform.Object2Json(sepcialPriceRequest));
            LogUtil.info(LOGGER,"查询特殊价格结果result={}",specialPriceStr);
            DataTransferObject sepecialDto = JsonEntityTransform.json2DataTransferObject(specialPriceStr);
            if (sepecialDto.getCode() == DataTransferObject.ERROR){
                return null;
            }
            LeaseCalendarVo leaseCalendarVo = sepecialDto.parseData("calendarData", new TypeReference<LeaseCalendarVo>() {});
            /** 查询出租日历特殊价格 **/
            HashMap<String,HouseFullCalendarItemVo>  calendarMap = setSpecialHousePrice(startDate,endDate,leaseCalendarVo);
            /** 查询出租日历特殊价格 **/

            //房源当天的基本价格，供后面计算今夜特价时使用
            Integer currentDayBasePrice = null;
            if(!Check.NuNObj(calendarMap.get(DateUtil.dateFormat(new Date(), DATE_DAY_PATTERN)))){
                currentDayBasePrice = calendarMap.get(DateUtil.dateFormat(new Date(), DATE_DAY_PATTERN)).getPrice();
            }

            //设置出租状态
            HouseLockRequest lockRequest = new HouseLockRequest();
            lockRequest.setFid(houseFid);
            if(!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()){
                lockRequest.setRoomFid(roomFid);
            }
            lockRequest.setRentWay(rentWay);
            lockRequest.setStarTime(startDate);
            lockRequest.setEndTime(endDate);
            String orderParams = JsonEntityTransform.Object2Json(lockRequest);

            //设置房源出租状态
            setHouseCalendarStatus(calendarMap, orderParams);
            
            //设置夹心价格
            setHousePriorityDate(calendarMap,lockRequest,leaseCalendarVo.getTillDate());
            
            //设置今夜特价
			setHouseTonightDiscount(calendarMap,lockRequest,currentDayBasePrice);

            //重新组装参数
            List<HouseFullCalendarItemVo> resultList = new ArrayList<>();
            for (String key : calendarMap.keySet()){
                HouseFullCalendarItemVo houseFullCalendarItemVo = calendarMap.get(key);
                houseFullCalendarItemVo.setTitle("¥"+String.valueOf(houseFullCalendarItemVo.getPrice()/100.0));
                resultList.add(houseFullCalendarItemVo);
            }
            return resultList;

        }catch (Exception e){
            LogUtil.error(LOGGER,"查询日历异常e={}",e);
        }
        return null;
    }
    
    private void setHouseCalendarStatus(HashMap<String, HouseFullCalendarItemVo> calendarMap, String orderParams) {
        String orderJson = orderUserService.getHouseLockInfo(orderParams);
        DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
        if (orderDto.getCode() == DataTransferObject.SUCCESS){
            List<HouseLockEntity> houseLockList = orderDto.parseData("houseLock", new TypeReference<List<HouseLockEntity>>() {});
            for (HouseLockEntity houseLockEntity : houseLockList) {
                HouseFullCalendarItemVo houseFullCalendarItemVo = calendarMap.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_DAY_PATTERN));
                if(!Check.NuNObj(houseFullCalendarItemVo)){
                    int lockType = houseLockEntity.getLockType();
                    if(lockType == LockTypeEnum.ORDER.getCode()){
                    	houseFullCalendarItemVo.setColor(FullCalendarEnum.ORDER_lOCK_COLOR.getValue());
                    }else if(lockType == LockTypeEnum.SYSTEM.getCode()){
                    	houseFullCalendarItemVo.setColor(FullCalendarEnum.SYS_LOCK_COLOR.getValue());
                    }else{
                    	houseFullCalendarItemVo.setColor(FullCalendarEnum.LAN_lOCK_COLOR.getValue());
                    }
                }
            }
        }
    }
    
    /**
	 * 设置今夜特价价格
	 * @author lusp
	 * @created 2017年5月10日 上午20:29:14
	 * @param monthMap
	 * @param requestDto
	 */
	private void setHouseTonightDiscount(
			HashMap<String,HouseFullCalendarItemVo> calendarMap,
			HouseLockRequest requestDto,Integer basePrice) {
		if(!Check.NuNMap(calendarMap)&&!Check.NuNObj(requestDto)){
			TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
			try {
				tonightDiscountEntity.setHouseFid(requestDto.getFid());
				if(!Check.NuNObj(requestDto.getRentWay()) && requestDto.getRentWay() == RentWayEnum.ROOM.getCode()){
					tonightDiscountEntity.setRoomFid(requestDto.getRoomFid());
				}
				tonightDiscountEntity.setRentWay(requestDto.getRentWay());
				String paramJson = JsonTransform.Object2Json(tonightDiscountEntity);
				String resultJson = houseCommonService.getEffectiveOfJYTJInfo(paramJson);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				//判断调用状态
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={},msg={}", paramJson,dto.getMsg());
					return;
				}
				HouseTonightPriceInfoVo houseTonightPriceInfoVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "data", HouseTonightPriceInfoVo.class);
				
				if(Check.NuNObj(houseTonightPriceInfoVo)){
					return;
				}
				if(houseTonightPriceInfoVo.isEffective()){
					double discount = houseTonightPriceInfoVo.getDiscount();
					if (Check.NuNObj(discount)) {
						return;
					}
					Date date = new Date();
					//当前系统时间大于今夜特价生效时间，今夜特价生效
					if(Check.NuNObj(basePrice)){
						return;
					}
					HouseFullCalendarItemVo houseFullCalendarItemVo = calendarMap.get(DateUtil.dateFormat(new Date(), DATE_DAY_PATTERN));
                    if(!Check.NuNObj(houseFullCalendarItemVo)){
                    	Double priceD = BigDecimalUtil.mul(discount, basePrice);
						int price = priceD.intValue()/100;
                        houseFullCalendarItemVo.setPrice(price*100);
                    }
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={}", requestDto);
			}
		}
	}
    
    /**
     * 设置房源的特殊价格
     *
     * @author jixd
     * @created 2017年03月15日 22:25:49
     * @param
     * @return
     */
    private HashMap<String,HouseFullCalendarItemVo> setSpecialHousePrice(Date startDate,Date endDate,LeaseCalendarVo leaseCalendarVo) {


        //填充基本价格
        HashMap<String, HouseFullCalendarItemVo> dayMap = new LinkedHashMap<>();
        for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(startDate, endDate) + 1; i < j; i++) {
            HouseFullCalendarItemVo vo = new HouseFullCalendarItemVo();
            Date addStart = DateUtil.getTime(startDate, i);
            String dateDay = DateUtil.dateFormat(addStart, DATE_DAY_PATTERN);
            vo.setStart(dateDay);
            //vo.setTitle("¥"+String.valueOf(leaseCalendarVo.getUsualPrice()/100.0));
            vo.setPrice(leaseCalendarVo.getUsualPrice());
            dayMap.put(dateDay,vo);
        }
        //填充特殊价格
        List<SpecialPriceVo> specialPriceList = leaseCalendarVo.getSpecialPriceList();
        for (SpecialPriceVo specialPriceVo : specialPriceList) {
            if(!Check.NuNMap(dayMap)){
                HouseFullCalendarItemVo vo = dayMap.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_DAY_PATTERN));
                if(!Check.NuNObj(vo)){
                   // vo.setTitle("¥"+String.valueOf(specialPriceVo.getSetPrice()/100.0));
                    vo.setPrice(specialPriceVo.getSetPrice());
                }
            }
        }

        return dayMap;
    }

    /**
     * 设置夹心价格
     * @author jixd
     * @created 2017年03月16日 23:12:42
     * @param
     * @return
     */
    private void setHousePriorityDate(HashMap<String,HouseFullCalendarItemVo> monthMap, HouseLockRequest lockRequest, Date tillDate ){
        if(!Check.NuNMap(monthMap)&&!Check.NuNObj(lockRequest)){
            try {
                Date curDate = new Date();
                if(Check.NuNObj(tillDate))tillDate = lockRequest.getEndTime();
                if(Check.NuNObj(lockRequest.getStarTime()))lockRequest.setStarTime(curDate);
                if(!(lockRequest.getStarTime().getTime()-curDate.getTime()>=0)){
                    lockRequest.setStarTime(curDate);
                }
                Date endDate = lockRequest.getEndTime();
                endDate =  DateSplitUtil.jumpDate(endDate, ProductRulesEnum020.ProductRulesEnum020003.getDayNum()+1);

                HousePriorityDto housePriorityDt = new HousePriorityDto();
                housePriorityDt.setHouseBaseFid(lockRequest.getFid());
                housePriorityDt.setRentWay(lockRequest.getRentWay());
                housePriorityDt.setHouseRoomFid(lockRequest.getRoomFid());
                housePriorityDt.setStartDate(curDate);
                housePriorityDt.setEndDate(endDate);
                housePriorityDt.setTillDate(tillDate);
                housePriorityDt.setNowDate(curDate);
                DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseCommonService.findPriorityDate(JsonEntityTransform.Object2Json(housePriorityDt)));

                if(dto.getCode() == DataTransferObject.ERROR){
                    LogUtil.error(LOGGER, "查看日历,夹心价格获取失败,参数paramDto={},msg={}", JsonEntityTransform.Object2Json(lockRequest),dto.getMsg());
                    return ;
                }
                Map<String,HousePriorityVo> housePriorityMap  = dto.parseData("housePriorityMap", new TypeReference<Map<String,HousePriorityVo> >() {});
                if(!Check.NuNMap(housePriorityMap)){
                    for (Map.Entry<String, HousePriorityVo>  entry : housePriorityMap.entrySet()) {
                        String key = entry.getKey();
                        HouseFullCalendarItemVo houseFullCalendarItemVo = monthMap.get(key);
                        if(!Check.NuNObj(houseFullCalendarItemVo)){
                            HousePriorityVo val = entry.getValue();
                            if(!Check.NuNObj(val) &&!Check.NuNStrStrict(val.getPriorityDiscount())){
                                houseFullCalendarItemVo.setPrice(DataFormat.getPriorityPrice(val.getPriorityDiscount(), houseFullCalendarItemVo.getPrice()));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.error(LOGGER, "查看日历-夹心价格设置失败paramDto={}", JsonEntityTransform.Object2Json(lockRequest));
            }

        }
    }

    /**
     *
     * @author jixd
     * @created 2017年03月16日 14:49:08
     * @param
     * @return
     */
    @RequestMapping("/lockHouse")
    @ResponseBody
    public DataTransferObject lockHouse(HttpServletRequest request){
        DataTransferObject dto = new DataTransferObject();
        try{
            String houseFid = request.getParameter("houseFid");
            String roomFid = request.getParameter("roomFid");
            int rentWay = Integer.parseInt(request.getParameter("rentWay"));
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            Integer lockType = Integer.parseInt(request.getParameter("lockType"));
            Date startDate =  DateUtil.parseDate(startDateStr,DATE_DAY_PATTERN);
            Date endDate =  DateUtil.parseDate(endDateStr,DATE_DAY_PATTERN);
            CurrentuserEntity currentUser = UserUtil.getCurrentUser();
            LogUtil.info(LOGGER,"【后台锁定房源开始】操作人账号={},操作人empId={},houseFid={},roomFid={},rentWay={},开始时间={},结束时间={}",
                    currentUser.getUserAccount(),currentUser.getEmployeeFid(),houseFid,roomFid,rentWay,startDateStr,endDateStr);
            LockHouseRequest lockHouseRequest = new LockHouseRequest();
            lockHouseRequest.setHouseFid(houseFid);
            if(!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()){
                lockHouseRequest.setRoomFid(roomFid);
            }
            lockHouseRequest.setRentWay(rentWay);
            lockHouseRequest.setLockType(LockTypeEnum.LANDLADY.getCode());
            List<Date> setDateList = new ArrayList<>();
            for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(startDate, endDate) + 1; i < j; i++) {
                Date tempDate = DateUtil.getTime(startDate, i);
                setDateList.add(tempDate);
            }
            lockHouseRequest.setLockDayList(setDateList);
            String result = "";

            if (lockType == 1){
                result = orderUserService.lockHouseForPC(JsonEntityTransform.Object2Json(lockHouseRequest));
            }else if (lockType == 0){
                result = orderUserService.unlockHouseForPC(JsonEntityTransform.Object2Json(lockHouseRequest));
            }

            //记录操作日志
            HouseLockLogEntity houseLockLogEntity = new HouseLockLogEntity();
            houseLockLogEntity.setHouseFid(houseFid);
            houseLockLogEntity.setRoomFid(roomFid);
            houseLockLogEntity.setRentWay(rentWay);
            houseLockLogEntity.setStartLockTime(startDate);
            houseLockLogEntity.setEndLockTime(endDate);
            houseLockLogEntity.setCreateFid(currentUser.getEmployeeFid());
            houseLockLogEntity.setCreateName(currentUser.getUserAccount());
            houseLockLogEntity.setLockType(lockType);
            houseCommonService.saveHouseLockLog(JsonEntityTransform.Object2Json(houseLockLogEntity));


            LogUtil.info(LOGGER,"房源锁定结果result={}",result);
            dto = JsonEntityTransform.json2DataTransferObject(result);
            return dto;
        }catch (Exception e){
            LogUtil.error(LOGGER,"【tory锁定房源异常error={}】",e);
        }
        return dto;
    }
    
	/**
	 * 
	 * 日历导出页面
	 *
	 * @author zyl
	 * @created 2017年6月26日 上午10:06:45
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/houseCalendarExportList")
	public void listHouseMsg(HttpServletRequest request) {
		// 只查询上架房源
		request.setAttribute("houseStatus", HouseStatusEnum.SJ.getCode());
	}

	/**
	 * 
	 * 生成日历URL
	 *
	 * @author zhangyl
	 * @created 2017年6月30日 下午1:31:46
	 *
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	@RequestMapping("/generateCalendarUrl")
	@ResponseBody
	public DataTransferObject generateCalendarUrl(String houseFid, String roomFid, Integer rentWay){
		HouseLockRequest houseLockRequest = new HouseLockRequest();
		houseLockRequest.setFid(houseFid);
		houseLockRequest.setRoomFid(roomFid);
		houseLockRequest.setRentWay(rentWay);
		
		String result = houseCommonService.getHouseLockDayList(JsonEntityTransform.Object2Json(houseLockRequest));
		LogUtil.info(LOGGER,"查询房源锁定记录结果result={}",result);
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
        List<CalendarDataVo> calendarDataVos = dto.parseData("data", new TypeReference<List<CalendarDataVo>>() {});
		
        String calendarUrl = "";
        if(!Check.NuNObj(calendarDataVos) && calendarDataVos.size() > 0){
        	String ciphertext = houseFid + BaseConstant.split + roomFid + BaseConstant.split + rentWay;
        	try {
        		String encryptStr = CryptAES.AES_Encrypt_Hex(BaseConstant.CALENDAR_URL_PARAM_CIPHER, ciphertext);
        		calendarUrl = houseCalendarExportUrl + encryptStr;
			} catch (Exception e) {
				calendarUrl = "";
			}
        }
        
        dto.putValue("calendarUrl", calendarUrl);
        
		return dto;
	}
	
}
