package com.ziroom.minsu.mapp.house.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.BaseConstant;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.common.utils.CalendarDataUtil;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.valenum.order.LockTypeEnum;

/**
 * 
 * <p>房源日历</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/houseCalendar")
@Controller
public class HouseCalendarController {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseCalendarController.class);
	
	@Resource(name = "order.houseCommonService")
    private HouseCommonService houseCommonService;
	
	/**
	 * 
	 * 导出日历
	 *
	 * @author zhangyl
	 * @created 2017年6月29日 下午6:42:54
	 *
	 * @param response
	 * @param ciphertext
	 */
	@RequestMapping("/export/{ciphertext}")
	public void exportHouseCalendar(HttpServletResponse response, @PathVariable String ciphertext){
		if(Check.NuNStr(ciphertext)){
			return;
		}
		
		String decryptStr = "";
		try {
			decryptStr = CryptAES.AES_Decrypt_Hex(BaseConstant.CALENDAR_URL_PARAM_CIPHER, ciphertext);
		} catch (Exception e) {
			return;
		}
		
		if(Check.NuNStr(decryptStr)){
			return;
		}
		
		String[] params = decryptStr.split(BaseConstant.split);
		if(params.length != 3){
			return;
		}
		
		HouseLockRequest houseLockRequest = new HouseLockRequest();
		houseLockRequest.setFid(params[0]);
		houseLockRequest.setRoomFid(params[1]);
		houseLockRequest.setRentWay(Integer.parseInt(params[2]));
		
		String result = houseCommonService.getHouseLockDayList(JsonEntityTransform.Object2Json(houseLockRequest));
		LogUtil.info(LOGGER,"查询房源锁定记录结果result={}",result);
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
        List<CalendarDataVo> calendarDataVos = dto.parseData("data", new TypeReference<List<CalendarDataVo>>() {});
        
        for(CalendarDataVo calendarDataVo : calendarDataVos){
        	// 不是订单的日历设置摘要Not available
        	if(LockTypeEnum.ORDER.getCode() != calendarDataVo.getCalendarType()){
        		calendarDataVo.setSummary(CalendarDataUtil.CalendarDataConstant.PROPERTY_SUMMARY_STATUS);
        	}
        }
        
        // 输出ics文件
        try {
        	response.setContentType("application/octet-stream");
        	response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("calendar.ics", "UTF-8"));
			CalendarDataUtil.calendarFileOutput(calendarDataVos, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
