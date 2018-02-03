package com.ziroom.minsu.services.job.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.HouseBaseExtRequest;

/**
 * <p>获取房源昨天的收益</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/4.
 * @version 1.0
 * @since 1.0
 */
public class HouseReHouseAddrJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseReHouseAddrJob.class);
	
    private static final String ZERO_STRING = "0";
    
    /**
     * 每天凌晨一点执行一次
     * 0 4 * * * ?
     * @author afi
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext){

        LogUtil.info(LOGGER, "HouseReHouseAddrJob 开始执行.....");

        // 0 0 4 * * ?
        try {
            //获取昨天的收益
        	HouseManageService houseManageService = (HouseManageService) ApplicationContext.getContext().getBean("house.houseManageService");
        	HouseBaseExtRequest request = new HouseBaseExtRequest();
        	request.setBuildingNum("0");
			request.setUnitNum("0");
			request.setFloorNum("0");
			request.setHouseNum("0");
        	String jsonArray = houseManageService.findHouseBaseExtListByCondition(JsonEntityTransform.Object2Json(request));
        	DataTransferObject jsonDto = JsonEntityTransform.json2DataTransferObject(jsonArray);
        	if (jsonDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "houseManageService.findHouseBaseExtListByCondition错误,参数:{},结果:{}", 
						JsonEntityTransform.Object2Json(request), jsonArray);
				return;
			}
        	
        	int total = SOAResParseUtil.getIntFromDataByKey(jsonArray, "total");
        	List<HouseBaseExtEntity> rows1 = SOAResParseUtil.getListValueFromDataByKey(jsonArray, "rows", HouseBaseExtEntity.class);
        	List<String> list = new ArrayList<String>();
			this.assembleHouseAddr(rows1, list);
			
			int i = 1;
			long num = total % request.getLimit() == 0 ? total / request.getLimit() : total / request.getLimit() + 1;
			while (i < num) {
				i++;
				request.setPage(i);
				String resultJson = houseManageService.findHouseBaseExtListByCondition(JsonEntityTransform.Object2Json(request));
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if (dto.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "houseManageService.findHouseBaseExtListByCondition错误,参数:{},结果:{}", 
							JsonEntityTransform.Object2Json(request), resultJson);
					continue;
				}
				List<HouseBaseExtEntity> rows2 = SOAResParseUtil.getListValueFromDataByKey(jsonArray, "rows", HouseBaseExtEntity.class);
				this.assembleHouseAddr(rows2, list);
			}
        	
            LogUtil.info(LOGGER, "error:{},size={}", JsonEntityTransform.Object2Json(list), list.size());
        }catch (Exception e){
            LogUtil.error(LOGGER,"error:{}",e);
        }

    }
    
	/**
	 * 拼装房源地址
	 *
	 * @author liujun
	 * @created 2016年7月25日
	 *
	 * @throws SOAParseException 
	 * @param rows
	 * @param list 
	 */
	private void assembleHouseAddr(List<HouseBaseExtEntity> rows, List<String> list) throws SOAParseException {
		HouseIssueService houseIssueService = (HouseIssueService) ApplicationContext.getContext().getBean("house.houseIssueService");
		ConfCityService confCityService = (ConfCityService) ApplicationContext.getContext().getBean("basedata.confCityService");
		
		for (HouseBaseExtEntity houseBaseExt : rows) {
			String resultJson = houseIssueService.searchHousePhyMsgByHouseBaseFid(houseBaseExt.getHouseBaseFid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        	if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "houseIssueService.searchHousePhyMsgByHouseBaseFid错误,houseBaseFid:{},结果:{}", 
						houseBaseExt.getHouseBaseFid(), resultJson);
				list.add(houseBaseExt.getHouseBaseFid());
				continue;
			}
        	
        	HousePhyMsgEntity housePhyMsg = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HousePhyMsgEntity.class);
			if(Check.NuNObj(housePhyMsg)){
				LogUtil.error(LOGGER, "housePhyMsg is null or blank,houseBaseFid:{},结果:{}", houseBaseExt.getHouseBaseFid());
				list.add(houseBaseExt.getHouseBaseFid());
				continue;
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("cityCode", housePhyMsg.getCityCode());
			map.put("areaCode", housePhyMsg.getAreaCode());
			
			String jsonArray = confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(map.values()));
			map.put("communityName", housePhyMsg.getCommunityName());
			DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(jsonArray);
			if(cityDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "confCityService.getCityNameByCodeList调用失败,houseBaseFid:{},参数:{},结果:{}", 
						houseBaseExt.getHouseBaseFid(), JsonEntityTransform.Object2Json(map.values()), jsonArray);
				list.add(houseBaseExt.getHouseBaseFid());
				continue;
			}
			
			StringBuilder houseAddr = new StringBuilder(100);
			List<ConfCityEntity> cityList = SOAResParseUtil.getListValueFromDataByKey(jsonArray, "cityList", ConfCityEntity.class);
			for (ConfCityEntity confCityEntity : cityList) {
				if(map.get("cityCode").equals(confCityEntity.getCode()) && !Check.NuNStr(confCityEntity.getShowName())){
					houseAddr.append(confCityEntity.getShowName());
				}
				
				if(map.get("areaCode").equals(confCityEntity.getCode()) && !Check.NuNStr(confCityEntity.getShowName())){
					houseAddr.append(confCityEntity.getShowName());
				}
			}

			if(!Check.NuNStr(map.get("communityName"))){
				houseAddr.append(map.get("communityName")+" ");
			}
			
			if(!Check.NuNObj(houseBaseExt)){
				if(!Check.NuNStr(houseBaseExt.getBuildingNum()) && !ZERO_STRING.equals(houseBaseExt.getBuildingNum())){
					houseAddr.append(houseBaseExt.getBuildingNum() + "号楼");
				}
				if(!Check.NuNStr(houseBaseExt.getUnitNum()) && !ZERO_STRING.equals(houseBaseExt.getUnitNum())){
					houseAddr.append(houseBaseExt.getUnitNum() + "单元");
				}
				if(!Check.NuNStr(houseBaseExt.getFloorNum()) && !ZERO_STRING.equals(houseBaseExt.getFloorNum())){
					houseAddr.append(houseBaseExt.getFloorNum() + "层");
				}
				if(!Check.NuNStr(houseBaseExt.getHouseNum()) && !ZERO_STRING.equals(houseBaseExt.getHouseNum())){
					houseAddr.append(houseBaseExt.getHouseNum() + "号");
				}
			}
			
			if(Check.NuNStr(houseAddr.toString())){
				LogUtil.error(LOGGER, "houseAddr is empty,houseBaseFid={}", houseBaseExt.getHouseBaseFid());
				list.add(houseBaseExt.getHouseBaseFid());
				continue;
			}
			
			HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
			houseBaseMsg.setFid(houseBaseExt.getHouseBaseFid());
			houseBaseMsg.setHouseAddr(houseAddr.toString());
			LogUtil.info(LOGGER, "houseIssueService.updateHouseBaseMsg参数:{}", JsonEntityTransform.Object2Json(houseBaseMsg));
			String jsonString = houseIssueService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsg));
			DataTransferObject jsonDto = JsonEntityTransform.json2DataTransferObject(jsonString);
			if (jsonDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "houseIssueService.updateHouseBaseMsg错误,houseBaseFid={}", houseBaseExt.getHouseBaseFid());
				list.add(houseBaseExt.getHouseBaseFid());
				continue;
			}
		}
	}

}
