/**
 * @FileName: MsgAdvisoryFollowupService.java
 * @Package com.ziroom.minsu.report.message.service
 * 
 * @author ls
 * @created 2017年5月31日 上午11:12:37
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.message.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.google.gson.JsonObject;
import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.valenum.MsgAdvisoryFollowEnum;
import com.ziroom.minsu.report.basedata.valenum.NationEnum;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dto.HouseInfoReportDto;
import com.ziroom.minsu.report.house.vo.HouseInfoReportVo;
import com.ziroom.minsu.report.message.dao.MsgAdvisoryFollowupDao;
import com.ziroom.minsu.report.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.report.message.vo.MsgAdvisoryFollowVo;


/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */

@Service("report.msgAdvisoryFollowupService")
public class MsgAdvisoryFollowupService implements ReportService <MsgAdvisoryFollowVo,MsgAdvisoryFollowRequest>{
	
	private final Logger LOGGER = LoggerFactory.getLogger(MsgAdvisoryFollowupService.class);
	
	@Resource(name="report.msgAdvisoryFollowupDao")
    private MsgAdvisoryFollowupDao msgAdvisoryFollowupDao;
	
	@Resource(name="report.confCityDao")
    private ConfCityDao confCityDao;
	
	
	/**
	 * 
	 * 导出IM列表
	 *
	 * @author ls
	 * @created 2017年5月31日 下午8:56:28
	 *
	 * @param paramRequest
	 * @return
	 */
	public PagingResult<MsgAdvisoryFollowVo> exportAllNeedFollowByPage(MsgAdvisoryFollowRequest paramRequest) {
		//houseFidList和roomFidList，当有一个为空的时候  不能使用union（都不为的时候走queryAllNeedFollowPage方法，使用整租分租union）
		LogUtil.info(LOGGER, "MsgAdvisoryFollowupService exportAllNeedFollowByPage param :{}", JsonEntityTransform.Object2Json(paramRequest));
		PagingResult<MsgAdvisoryFollowVo> imFollowVoByPage = msgAdvisoryFollowupDao.exportAllNeedFollowByPage(paramRequest);
		List<MsgAdvisoryFollowVo> allFollowlist= imFollowVoByPage.getRows();
		if(imFollowVoByPage.getTotal()<=0){
			return new PagingResult<MsgAdvisoryFollowVo>();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> cityMap = new HashMap<String, Object>();
		List<ConfCityEntity> openCityList = confCityDao.getOpenCity(paramMap);
		for (ConfCityEntity confCityEntity : openCityList) {
			cityMap.put(confCityEntity.getCode(), confCityEntity.getShowName());
		}
		//获取所有跟进人,填充到vo中
	    imFollowVoByPage.setRows(fillAllFollowPeople(allFollowlist,cityMap));
		return imFollowVoByPage;
	}
	
	/**
	 * 
	 * 获取所有跟进人,填充到vo中
	 *
	 * @author ls
	 * @created 2017年7月7日 下午4:53:58
	 *
	 * @param allFollowlist
	 * @return
	 */
	public List<MsgAdvisoryFollowVo> fillAllFollowPeople(List<MsgAdvisoryFollowVo> allFollowlist,Map<String, Object> cityMap){
		LogUtil.info(LOGGER, "MsgAdvisoryFollowupService fillAllFollowPeople param :{}", JsonEntityTransform.Object2Json(allFollowlist));
		for (MsgAdvisoryFollowVo msgAdvisoryFollowVo : allFollowlist) {
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//首次跟进时间精确到时分秒
			if(!Check.NuNObj(msgAdvisoryFollowVo.getFirstFollowTime())){
				msgAdvisoryFollowVo.setFirstFollowTimeStr(timeFormat.format(msgAdvisoryFollowVo.getFirstFollowTime()));
			}
			if(!Check.NuNObj(msgAdvisoryFollowVo.getCreateTime())){
				msgAdvisoryFollowVo.setCreateTimeStr(timeFormat.format(msgAdvisoryFollowVo.getCreateTime()));
			}
			//填充城市名称
			if(!Check.NuNStr(msgAdvisoryFollowVo.getCityCode())){
				msgAdvisoryFollowVo.setCityName((cityMap.get(msgAdvisoryFollowVo.getCityCode())).toString());
			}
			//填充房东回复时间
			if(!Check.NuNObj(msgAdvisoryFollowVo) && !Check.NuNObj(msgAdvisoryFollowVo.getLandLordFirstReplyTime()) 
					&& !Check.NuNObj(msgAdvisoryFollowVo.getReplayTimeHouse())){
				Calendar c =new GregorianCalendar();
				c.setTime(msgAdvisoryFollowVo.getLandLordFirstReplyTime());
				c.add(Calendar.MILLISECOND, msgAdvisoryFollowVo.getReplayTimeHouse().intValue());
				msgAdvisoryFollowVo.setLandLordFirstReplyTimeStr(timeFormat.format(c.getTime()));
			}
			//查询本条首次咨询所有的跟进人，并填充到vo中
			/*List<String> empNameList = msgAdvisoryFollowupDao.getAllEmpNameByFirstAdvFid(msgAdvisoryFollowVo.getMsgFirstAdvisoryFid());
			msgAdvisoryFollowVo.setEmpNameList(empNameList);*/
			
			//查询所有跟进人及跟进内容，填充到vo中
			/*List<MsgAdvisoryFollowupEntity> list = new ArrayList<MsgAdvisoryFollowupEntity>();
			List<Map<String, Object>> empList = new ArrayList<Map<String, Object>>();
			if(msgAdvisoryFollowVo.getFollowStatus().equals(20) ||  msgAdvisoryFollowVo.getFollowStatus().equals(30)){
				list = msgAdvisoryFollowupDao.getMsgFollowupByFirstAdvFid(msgAdvisoryFollowVo.getMsgFirstAdvisoryFid());
				//List<MsgAdvisoryFollowupEntity> list = msgAdvisoryFollowupDao.getMsgFollowupByFirstAdvFid(msgAdvisoryFollowVo.getMsgFirstAdvisoryFid());
				
				
				for (MsgAdvisoryFollowupEntity msgAdvisoryFollowupEntity : list) {
					String empName = !Check.NuNStr(msgAdvisoryFollowupEntity.getEmpName()) ? msgAdvisoryFollowupEntity.getEmpName() : null;
					String remark = !Check.NuNStr(msgAdvisoryFollowupEntity.getRemark()) ? msgAdvisoryFollowupEntity.getRemark() : null ;
					String createTime = !Check.NuNObj(msgAdvisoryFollowupEntity.getCreateTime()) ? timeFormat.format(msgAdvisoryFollowupEntity.getCreateTime()) : null ;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("跟进人", empName);
					map.put("跟进记录", remark);
					map.put("跟进时间", createTime);
					empList.add(map);
				}
				
			}
			*/
			//获取到房东首次回复的时间，填充到vo中
		/*	if(!Check.NuNStr(msgAdvisoryFollowVo.getMsgBaseFid()) && !Check.NuNStr(msgAdvisoryFollowVo.getHouseFid())){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("msgBaseFid", msgAdvisoryFollowVo.getMsgBaseFid());
				paramMap.put("houseFid", msgAdvisoryFollowVo.getHouseFid());
				MsgAdvisoryFollowVo landLordFirstReplyTimeVo = msgAdvisoryFollowupDao.getLandLordFirstReplyTime(paramMap);*/
				
			//}
			//msgAdvisoryFollowVo.setEmpList(empList);
		}
		return allFollowlist;
	}
	
	/**
	 * 
	 * 查询所有需要跟进的IM
	 *
	 * @author ls
	 * @created 2017年5月31日 下午8:56:04
	 *
	 * @param paramRequest
	 * @return
	 */
	public String queryAllNeedFollowList(MsgAdvisoryFollowRequest paramRequest) {
		LogUtil.info(LOGGER, "MsgAdvisoryFollowupService queryAllNeedFollowList param :{}", JsonEntityTransform.Object2Json(paramRequest));
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DataTransferObject dto = new DataTransferObject();
		PagingResult<MsgAdvisoryFollowVo> imFollowVoByPage = msgAdvisoryFollowupDao.queryAllNeedFollowPage(paramRequest);
		if(imFollowVoByPage.getTotal()<=0){
	        dto.putValue("imFollowVoList", new ArrayList<MsgAdvisoryFollowVo>());
	        dto.putValue("size", 0);
	        return dto.toJsonString();
	    }
		List<MsgAdvisoryFollowVo> rows = imFollowVoByPage.getRows();
	    List<String> tenantUidList = new ArrayList<>();
	    for (MsgAdvisoryFollowVo row : rows) {
	    	tenantUidList.add(row.getTenantUid());
	    }
	    paramRequest.setTenantUidList(tenantUidList);
		List<MsgAdvisoryFollowVo> imFollowVoList = msgAdvisoryFollowupDao.queryAllNeedFollowList(paramRequest);
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> cityMap = new HashMap<String, Object>();
		List<ConfCityEntity> openCityList = confCityDao.getOpenCity(paramMap);
		for (ConfCityEntity confCityEntity : openCityList) {
			cityMap.put(confCityEntity.getCode(), confCityEntity.getShowName());
		}
		//获取所有跟进人
		for (MsgAdvisoryFollowVo msgAdvisoryFollowVo : imFollowVoList) {
			//首次跟进时间精确到时分秒
			if(!Check.NuNObj(msgAdvisoryFollowVo.getFirstFollowTime())){
				msgAdvisoryFollowVo.setFirstFollowTimeStr(timeFormat.format(msgAdvisoryFollowVo.getFirstFollowTime()));
			}
			if(!Check.NuNObj(msgAdvisoryFollowVo.getCreateTime())){
				msgAdvisoryFollowVo.setCreateTimeStr(timeFormat.format(msgAdvisoryFollowVo.getCreateTime()));
			}
			//填充城市名称
			if(!Check.NuNStr(msgAdvisoryFollowVo.getCityCode())){
				msgAdvisoryFollowVo.setCityName((cityMap.get(msgAdvisoryFollowVo.getCityCode())).toString());
			}
			
			if(!Check.NuNObj(msgAdvisoryFollowVo.getFollowStatus())){
			   MsgAdvisoryFollowEnum msgAdvisoryFollowEnum = MsgAdvisoryFollowEnum.getNameByCode(msgAdvisoryFollowVo.getFollowStatus());
			   if(!Check.NuNObj(msgAdvisoryFollowEnum)){
				   msgAdvisoryFollowVo.setFollowStatusName(msgAdvisoryFollowEnum.getName());
			   }
			}
			msgAdvisoryFollowVo.getFollowStatus();
			/*List<String> empNameList = msgAdvisoryFollowupDao.getAllEmpNameByFirstAdvFid(msgAdvisoryFollowVo.getMsgFirstAdvisoryFid());
			msgAdvisoryFollowVo.setEmpNameList(empNameList);*/
			StringBuilder sBuilder = new StringBuilder();
			if(!Check.NuNStr(msgAdvisoryFollowVo.getTenantName())){
				sBuilder.append("用户姓名：").append(msgAdvisoryFollowVo.getTenantName()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
				msgAdvisoryFollowVo.setTenantUid(sBuilder.toString());
			}else if(Check.NuNStr(msgAdvisoryFollowVo.getTenantName()) && !Check.NuNStr(msgAdvisoryFollowVo.getNickName())){
				msgAdvisoryFollowVo.setTenantName(msgAdvisoryFollowVo.getNickName());
				sBuilder.append("用户昵称：").append(msgAdvisoryFollowVo.getNickName()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
			}else if(Check.NuNStr(msgAdvisoryFollowVo.getTenantName()) && Check.NuNStr(msgAdvisoryFollowVo.getNickName()) && !Check.NuNStr(msgAdvisoryFollowVo.getTenantTel())){
				msgAdvisoryFollowVo.setTenantName(msgAdvisoryFollowVo.getTenantTel());
				sBuilder.append("用户电话：").append(msgAdvisoryFollowVo.getTenantTel()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
			}else if(Check.NuNStr(msgAdvisoryFollowVo.getTenantName()) && Check.NuNStr(msgAdvisoryFollowVo.getNickName()) && Check.NuNStr(msgAdvisoryFollowVo.getTenantTel()) && !Check.NuNStr(msgAdvisoryFollowVo.getTenantEmail())){
				msgAdvisoryFollowVo.setTenantName(msgAdvisoryFollowVo.getTenantEmail());
				sBuilder.append("用户邮箱：").append(msgAdvisoryFollowVo.getTenantEmail()).append("，用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
			}else{
				sBuilder.append("用户uid：").append(msgAdvisoryFollowVo.getTenantUid());
				msgAdvisoryFollowVo.setTenantName("无姓名");
			}
			if(!Check.NuNStr(msgAdvisoryFollowVo.getTenantTel())){
				msgAdvisoryFollowVo.setTenantTel(msgAdvisoryFollowVo.getTenantTel());
			}else{
				msgAdvisoryFollowVo.setTenantTel("-");
			}
			msgAdvisoryFollowVo.setTenantUid(sBuilder.toString()); 
		}
		dto.putValue("imFollowVoList", imFollowVoList);
		dto.putValue("size", imFollowVoByPage.getTotal());
		LogUtil.info(LOGGER, "结果：{}",imFollowVoByPage.getTotal()); 
	    return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<MsgAdvisoryFollowVo> getPageInfo(
			MsgAdvisoryFollowRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(MsgAdvisoryFollowRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
