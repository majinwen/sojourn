package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.houses.entity.RoomRentInfoEntity;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;
import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import com.ziroom.zrp.service.houses.entity.RoomContractSmartVo;
import com.ziroom.zrp.service.houses.service.RoomInfoExtServiceImpl;
import com.ziroom.zrp.service.houses.service.RoomInfoServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

/**
 * <p>房间业务组装层</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月13日 17:34
 * @version 1.0
 * @since 1.0
 */
@Component("houses.roomServiceProxy")
public class RoomServiceProxy implements RoomService{
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceProxy.class);

	@Resource(name = "houses.roomInfoServiceImpl")
	private RoomInfoServiceImpl roomInfoServiceImpl;

	@Resource(name="houses.roomInfoExtServiceImpl")
	private RoomInfoExtServiceImpl roomInfoExtServiceImpl;

	@Override
	public String getRoomListByRoomIds(String roomIds) {
		LogUtil.info(LOGGER,"【getRoomListByRoomIds】参数={}",roomIds);

		DataTransferObject dto = new DataTransferObject();
		try {

			if (Check.NuNStr(roomIds)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}

			List<RoomInfoEntity> roomInfoEntityList = this.roomInfoServiceImpl.getRoomListByRoomIds(roomIds);
			dto.putValue("roomInfoList", roomInfoEntityList );
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER,"getRoomListByRoomIds:" + roomIds, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("异常");
			return dto.toJsonString();
		}

	}

	@Override
	public String getRoomByFid(String fid) {
		LogUtil.info(LOGGER,"【getRoomByFid】参数={}",fid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		RoomInfoEntity roomInfoEntity = this.roomInfoServiceImpl.getRoomInfoByFid(fid);
		dto.putValue("roomInfo", roomInfoEntity);
		return dto.toJsonString();
	}

	@Override
	public String saveRoomRentInfo(String paramJson) {
		LogUtil.info(LOGGER,"【saveRoomRentInfo】入参:{}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			RoomRentInfoEntity roomRentInfoEntity = JsonEntityTransform.json2Entity(paramJson,RoomRentInfoEntity.class);
			if(Check.NuNObj(roomRentInfoEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("实体不存在");
				return dto.toJsonString();
			}
			if(Check.NuNStr(roomRentInfoEntity.getFroomid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间id为空");
				return dto.toJsonString();
			}
			roomInfoServiceImpl.saveRoomRentInfo(roomRentInfoEntity);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "【saveRoomRentInfo】 e:{},paramJson={}", e, paramJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto.toJsonString();
	}

	@Override
	public String updateRoomInfoAndSaveRentInfo(String paramJson) {
		LogUtil.info(LOGGER,"【updateRoomInfoAndSaveRentInfo】入参:{}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			RoomRentInfoEntity roomRentInfoEntity = JsonEntityTransform.json2Entity(paramJson,RoomRentInfoEntity.class);
			if(Check.NuNObj(roomRentInfoEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("实体不存在");
				return dto.toJsonString();
			}
			if(Check.NuNStr(roomRentInfoEntity.getFroomid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间id为空");
				return dto.toJsonString();
			}
			roomInfoServiceImpl.updateRoomInfoAndSaveRentInfo(roomRentInfoEntity);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "【updateRoomInfoAndSaveRentInfo】 e:{},paramJson={}", e, paramJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto.toJsonString();
	}

	@Override
	public String updateRoomInfoAndDeleteRentInfo(String roomId) {
		LogUtil.info(LOGGER,"【updateRoomInfoAndDeleteRentInfo】入参roomId:{}",roomId);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStrStrict(roomId)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间id为空");
				return dto.toJsonString();
			}
			RoomRentInfoEntity roomRentInfoEntity = new RoomRentInfoEntity();
			roomRentInfoEntity.setFroomid(roomId);
			roomInfoServiceImpl.updateRoomInfoAndDeleteRentInfo(roomRentInfoEntity);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "【updateRoomInfoAndDeleteRentInfo】 e:{},roomId={}", e, roomId);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 * <p>查询合同相关的房间信息，房型信息，项目信息</p>.
	 * <p>连接查询troominfo，thousetype，tproject</p>
	 * @author xiangb
	 * @created 2017年9月24日
	 * @param
	 * @return
	 */
	@Override
	public String getRentRoomInfoByRoomId(String roomId) {
		LogUtil.info(LOGGER, "【根据房间ID查询合同信息】入参：", roomId);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(roomId)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空！");
			return dto.toJsonString();
		}
		RentRoomInfoDto rentRoom = roomInfoServiceImpl.getRentRoomInfoByRoomId(roomId);
		dto.putValue("rentRoom", rentRoom);
		return dto.toJsonString();
	}

	@Override
	public String findRoomListForPage(String paramJson) {
		LogUtil.info(LOGGER,"【findRoomListForPage】paramJson={}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			AddHouseGroupDto addHouseGroupDto = JsonEntityTransform.json2Object(paramJson,AddHouseGroupDto.class);
			PagingResult<AddHouseGroupVo> pagingResult = roomInfoServiceImpl.findRoomListForPage(addHouseGroupDto);
			dto.putValue("list",pagingResult.getRows());
			dto.putValue("total",pagingResult.getTotal());
		}catch (Exception e){
			LogUtil.error(LOGGER, "【findRoomListForPage】 error:{},param={}", e, paramJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	@Override
	public String updateRoomInfo(String paramJson) {
		LogUtil.info(LOGGER,"【updateRoomInfo】paramJson={}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			RoomInfoEntity roomInfoEntity = JsonEntityTransform.json2Entity(paramJson, RoomInfoEntity.class);
			int affect = this.roomInfoServiceImpl.updateRoomInfoByFid(roomInfoEntity);
			LogUtil.info(LOGGER, "【updateRoomInfo】影响行数:{}", affect);
			dto.putValue("affect", affect);
		}catch (Exception e){
			LogUtil.error(LOGGER, "【updateRoomInfo】 error:{},param={}", e, paramJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 * 批量释放房间
	 *
	 * @Author: wangxm113
	 * @Date: 2017年10月24日 15时17分59秒
	 */
    @Override
    public String updateRoomInfoForRelease(String paramStr) {
        LogUtil.info(LOGGER, "【updateRoomInfoForRelease】paramJson={}", paramStr);
        DataTransferObject dto = new DataTransferObject();
        try {
            List<String> list = JsonEntityTransform.json2ObjectList(paramStr, String.class);
            roomInfoServiceImpl.updateRoomInfoForRelease(list);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【updateRoomInfoForRelease】error:{},param={}", e, paramStr);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }
    
    
	/**
	 * 批量回滚释放的房间
	 *
	 * @Author: tianxf9
	 * @Date: 2017年10月24日 15时17分59秒
	 */
    @Override
    public String updateRoomStateRent(String paramStr) {
        LogUtil.info(LOGGER, "【updateRoomStateRent】paramJson={}", paramStr);
        DataTransferObject dto = new DataTransferObject();
        try {
            List<String> list = JsonEntityTransform.json2ObjectList(paramStr, String.class);
            roomInfoServiceImpl.updateRoomStateRental(list);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【updateRoomStateRent】error:{},param={}", e, paramStr);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }


	/**
	 * 将房间状态由签约进行中改为已出租
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String updateRoomStateFromSigningToRental(String paramStr) {
		LogUtil.info(LOGGER,"【updateRoomStateFromSigningToRental】{}", paramStr);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStrStrict(paramStr)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}

		List<String> roomIdList = Arrays.asList(paramStr.split(","));
		if (roomIdList.isEmpty()) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间列表为空");
			return dto.toJsonString();
		}
		boolean flag = false;
		try {
			flag = this.roomInfoServiceImpl.updateRoomStateFromSigningToRental(roomIdList);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"{}" + paramStr, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间状态变化，请重新签约");
			return  dto.toJsonString();
		}

		if (flag) {
			dto.setErrCode(DataTransferObject.SUCCESS);
			dto.setMsg("修改房间状态成功!");
			return  dto.toJsonString();
		} else {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("修改房间状态失败!");
			return  dto.toJsonString();
		}

	}


	@Override
	public String findRoomContractSmartByPage(String paramStr) {

		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStrStrict(paramStr)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		RoomStmartDto roomStmartDto = JsonEntityTransform.json2Object(paramStr,RoomStmartDto.class);
		PagingResult<RoomContractSmartVo> pagingResult = this.roomInfoServiceImpl.findRoomContractSmartByPage(roomStmartDto);
		List<RoomContractSmartVo> list = pagingResult.getRows();
		dto.putValue("list",list);
		dto.putValue("total",pagingResult.getTotal());

		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.zrp.service.houses.api.RoomService#getRoomInfoExtByRoomId(java.lang.String)
	 */
	@Override
	public String getRoomInfoExtByRoomId(String roomId) {
		LogUtil.info(LOGGER, "【getRoomInfoExtByRoomId】roomId={}", roomId);
        DataTransferObject dto = new DataTransferObject();
        try {
            RoomInfoExtEntity roomExt=roomInfoExtServiceImpl.getRoomInfoExtEntityByRoomFid(roomId);
            dto.putValue("roomExt", roomExt);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getRoomInfoExtByRoomId】error:{},param={}", e, roomId);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
	}
	/**
	 * 查询房间信息 通过父房间标识
	 * @param roomId 房间标识
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月7日
	 */
	@Override
	public String getRoomInfoByParentId(String roomId) {
		LogUtil.info(LOGGER, "【getRoomInfoByParentId】roomId={}", roomId);
		DataTransferObject dto = new DataTransferObject();
		try {
			List<RoomInfoEntity> roomList=roomInfoServiceImpl.getRoomInfoByParentId(roomId);
			dto.putValue("roomList", roomList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getRoomInfoByParentId】param={},error:{}", roomId, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 * 查询绑定水表的所有房间
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月28日
	 */
	@Override
	public String getAllRoomOfBindingWaterMeter() {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<RoomInfoEntity> roomList=roomInfoExtServiceImpl.getAllRoomOfBindingWaterMeter();
			dto.putValue("roomList", roomList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getAllRoomOfBindingWaterMeter】error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 *
	 * 项目下所有房间
	 *
	 * @author zhangyl2
	 * @created 2018年02月07日 15:05
	 * @param
	 * @return
	 */
    @Override
    public String findAllRoom(String projectid) {
        LogUtil.info(LOGGER, "【findAllRoom】projectid={}", projectid);
        DataTransferObject dto = new DataTransferObject();
        List<RoomInfoEntity> list = roomInfoServiceImpl.findAllRoom(projectid);
        dto.putValue("list", list);
        return dto.toJsonString();
    }
}
