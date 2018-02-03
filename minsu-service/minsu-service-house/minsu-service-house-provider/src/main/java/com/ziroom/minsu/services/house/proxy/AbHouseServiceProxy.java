package com.ziroom.minsu.services.house.proxy;

import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.AbHouseRelateEntity;
import com.ziroom.minsu.entity.house.AbHouseStatusEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.airbnb.dto.AsyAbHouseDto;
import com.ziroom.minsu.services.house.airbnb.vo.AbHouseRelateVo;
import com.ziroom.minsu.services.house.api.inner.AbHouseService;
import com.ziroom.minsu.services.house.service.AbHouseServiceImpl;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.SyncHouseLockEnum;

/**
 * <p>airbnb房源日历处理</p>
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
@Component("house.abHouseServiceProxy")
public class AbHouseServiceProxy implements AbHouseService{

	private static final Logger LOGGER = LoggerFactory.getLogger(AbHouseServiceProxy.class);

	@Resource(name="house.abHouseServiceImpl")
	private AbHouseServiceImpl abHouseServiceImpl;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	@Override
	public String saveHouseCalendar(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER,"【saveHouseCalendar】参数={}",paramJson);
		if (Check.NuNStr(paramJson)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		AbHouseDto abHouseDto = JsonEntityTransform.json2Object(paramJson, AbHouseDto.class);
		if (Check.NuNStr(abHouseDto.getAbSn())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("ab房源编号为空");
			return dto.toJsonString();
		}
		if (Check.NuNCollection(abHouseDto.getAbStatusList())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("保存集合为空");
			return dto.toJsonString();
		}

		int count = abHouseServiceImpl.saveHouseCalendar(abHouseDto);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String saveHouseRelate(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER,"【saveHouseRelate】参数={}",paramJson);
		AbHouseRelateEntity abHouseRelateEntity = JsonEntityTransform.json2Object(paramJson, AbHouseRelateEntity.class);
		if (Check.NuNObj(abHouseRelateEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(abHouseRelateEntity.getHouseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源fid为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(abHouseRelateEntity.getRentWay())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租方式为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(abHouseRelateEntity.getCalendarUrl())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("url为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(abHouseRelateEntity.getAbSn())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("airbnb编号为空");
			return dto.toJsonString();
		}

		HouseBaseMsgEntity houseBaseMsgEntity = houseIssueServiceImpl.findHouseBaseMsgByFid(abHouseRelateEntity.getHouseFid());
		if (Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}
		if (abHouseRelateEntity.getRentWay() == RentWayEnum.ROOM.getCode()){
			if (Check.NuNStr(abHouseRelateEntity.getRoomFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间Fid为空");
				return dto.toJsonString();
			}
			HouseRoomMsgEntity houseRoomMsgByFid = houseIssueServiceImpl.findHouseRoomMsgByFid(abHouseRelateEntity.getRoomFid());
			if (Check.NuNObj(houseRoomMsgByFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间不存在");
				return dto.toJsonString();
			}

		}
		if (houseBaseMsgEntity.getRentWay() != abHouseRelateEntity.getRentWay()){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租类型不相符");
			return dto.toJsonString();
		}
		AbHouseRelateEntity abHouseByHouse = abHouseServiceImpl.findAbHouseByHouse(abHouseRelateEntity);
		if (!Check.NuNObj(abHouseByHouse)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("该房源信息已存在");
			return dto.toJsonString();
		}

		int count = abHouseServiceImpl.saveHouseRelate(abHouseRelateEntity);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String listHouseRelateVoByPage(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER,"【AbHouseServiceProxy.listHouseRelateVoByPage】参数={}",paramJson);
		AbHouseDto abHouseDto = JsonEntityTransform.json2Object(paramJson, AbHouseDto.class);
		if (Check.NuNObj(abHouseDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		PagingResult<AbHouseRelateVo> result = abHouseServiceImpl.listRelateVoByPage(abHouseDto);
		dto.putValue("total",result.getTotal());
		dto.putValue("list",result.getRows());
		return dto.toJsonString();
	}

	@Override
	public String findHouseRelateByFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER,"【AbHouseServiceProxy.findHouseRelateByFid】参数={}",paramJson);
		AbHouseDto abHouseDto = JsonEntityTransform.json2Object(paramJson, AbHouseDto.class);
		if (Check.NuNObj(abHouseDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		AbHouseRelateVo abHouseRelateEntity = abHouseServiceImpl.findRelateByHouseFid(abHouseDto);
		dto.putValue("obj",abHouseRelateEntity);
		return dto.toJsonString();
	}

	/**
	 * 根据fid修改关系记录
	 * @author zyl
	 * @created 2017年6月28日 下午7:22:58
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateHouseRelateByFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER,"【AbHouseServiceProxy.updateHouseRelateByFid】参数={}",paramJson);
		AbHouseRelateVo abHouseRelateVo = JsonEntityTransform.json2Object(paramJson, AbHouseRelateVo.class);
		if (Check.NuNObj(abHouseRelateVo)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}

		if (Check.NuNObj(abHouseRelateVo.getFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}

		int upNum = abHouseServiceImpl.updateHouseRelateByFid(abHouseRelateVo);
		dto.putValue("upNum", upNum);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.AbHouseService#asySaveHouseCalendar(java.lang.String)
	 */
	@Override
	public void asySaveHouseCalendar(String paramJson) {
		AsyAbHouseDto asyAbHouseDto = JsonEntityTransform.json2Object(paramJson, AsyAbHouseDto.class);
		if(Check.NuNObj(asyAbHouseDto)||Check.NuNStr(asyAbHouseDto.getAbSn())
				||Check.NuNCollection(asyAbHouseDto.getCalendarDataVos())){
			LogUtil.error(LOGGER, "【异步保存airbnb日历参数错误】参数={}",paramJson);

			return ;
		}


		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				int count = 0 ;//保存成功的条数
				List<AbHouseStatusEntity> saveList = new ArrayList<>();
				Long  t1 = System.currentTimeMillis();
				try {

					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR,0);
					calendar.set(Calendar.MINUTE,0);
					calendar.set(Calendar.SECOND,0);
					Date today = calendar.getTime();

					List<CalendarDataVo> calendarDataVos = asyAbHouseDto.getCalendarDataVos();
					//处理房源锁状态
					for (CalendarDataVo calendarDataVo : calendarDataVos){
						Date startDate = calendarDataVo.getStartDate();
						Date endDate = calendarDataVo.getEndDate();
						Integer summaryStatus = calendarDataVo.getSummaryStatus();
						List<Date> dates = DateSplitUtil.dateSplit(startDate, endDate);
						for (Date date : dates){
							if (date.before(today)){
								continue;
							}
							AbHouseStatusEntity abStatusEntity = new AbHouseStatusEntity();
							abStatusEntity.setUid(calendarDataVo.getUid());
							abStatusEntity.setSummary(calendarDataVo.getSummary());
							abStatusEntity.setSummaryStatus(calendarDataVo.getSummaryStatus());
							abStatusEntity.setAbSn(asyAbHouseDto.getAbSn());
							if (summaryStatus == SyncHouseLockEnum.ORDER_LOCK.getCode()){
								Map<String, Object> descriptionMap = calendarDataVo.getDescriptionMap();
								abStatusEntity.setEmail((String) descriptionMap.get("EMAIL"));
								abStatusEntity.setPhone((String) descriptionMap.get("PHONE"));
							}
							abStatusEntity.setLockTime(date);
							saveList.add(abStatusEntity);

						}
					}

					if(!Check.NuNCollection(saveList)){
						AbHouseDto abHouseDto = new AbHouseDto();
						abHouseDto.setAbStatusList(saveList);
						abHouseDto.setAbSn(asyAbHouseDto.getAbSn());
						count += abHouseServiceImpl.saveHouseCalendar(abHouseDto);
					}

				} catch (Exception e) {
					LogUtil.error(LOGGER, "【保存airbnb房态数据异常】不影响下一个房源处理e={}", e);
				}

				Long  t2 = System.currentTimeMillis();
				LogUtil.info(LOGGER, "【保存ab房源{}房态数据结束】原始条数sum={}，成功条数count={},用时t2-t1:{}ms",asyAbHouseDto.getAbSn(),saveList.size(), count,t2-t1);
			}
		});
		th.start();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.AbHouseService#findAbHouseByHouse(java.lang.String)
	 */
	@Override
	public String findAbHouseByHouse(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(paramJson)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER,"【AbHouseServiceProxy.findAbHouseByHouse】参数={}",paramJson);
		AbHouseRelateEntity abHouse = JsonEntityTransform.json2Object(paramJson, AbHouseRelateEntity.class);
		AbHouseRelateEntity resultAbHouse=abHouseServiceImpl.findAbHouseByHouse(abHouse);
		dto.putValue("obj",resultAbHouse);
		return dto.toJsonString();
	}

}
