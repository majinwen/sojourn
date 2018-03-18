package com.ziroom.zrp.service.houses.proxy;

import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;

import com.ziroom.zrp.houses.entity.IntellectSmartLockEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.dto.RoomSmartLockDto;

import com.ziroom.zrp.service.houses.service.RoomInfoServiceImpl;


import com.ziroom.zrp.service.houses.api.IntellectSmartLockService;


import com.ziroom.zrp.service.houses.entity.IntellectSmartLockVo;
import com.ziroom.zrp.service.houses.service.IntellectSmartLockServiceImpl;


import com.ziroom.zrp.service.houses.valenum.IsBindLockEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 20:42
 * @since 1.0
 */
@Component("houses.intellectSmartLockServiceProxy")
public class IntellectSmartLockServiceProxy implements IntellectSmartLockService {

    private final  static Logger LOGGER  = LoggerFactory.getLogger(IntellectSmartLockServiceProxy.class);

    @Resource(name = "houses.intellectSmartLockServiceImpl")
    private IntellectSmartLockServiceImpl intellectSmartLockServiceImpl;

    @Resource(name = "houses.roomInfoServiceImpl")
    private RoomInfoServiceImpl roomInfoServiceImpl;
    @Override
    public String saveRoomSmartLock(String paramsJson) {

        DataTransferObject dto = new DataTransferObject();
        IntellectSmartLockEntity roomSmartLock = JsonEntityTransform.json2Object(paramsJson,IntellectSmartLockEntity.class);

        if(Check.NuNObj(roomSmartLock)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("保存对象不存在");
            return  dto.toJsonString();
        }


        RoomInfoEntity roomInfoEntity = this.roomInfoServiceImpl.getRoomInfoByFid(roomSmartLock.getRoomId());

        if (Check.NuNObj(roomInfoEntity) || roomInfoEntity.getIsBindLock() == IsBindLockEnum.IS_NOT_BIND.getCode()) {
            LogUtil.info(LOGGER, "当前房间未有智能锁roomInfoEntity={}", roomInfoEntity == null ? "" : roomInfoEntity.toJsonStr());
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("当前房间未有智能锁");
            return dto.toJsonString();
        }
        roomSmartLock.setProjectId(roomInfoEntity.getProjectid());
        try {
            int i = intellectSmartLockServiceImpl.saveRoomSmartLock(roomSmartLock);
            dto.putValue("i",i);
            dto.putValue("projectId",roomInfoEntity.getProjectid());
        }catch (Exception e){

            LogUtil.error(LOGGER,"保存异常paramsJson={},e={}",paramsJson,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("保存异常");
            return  dto.toJsonString();
        }

        return dto.toJsonString();
    }

    /**
     * 分页获取密码锁记录
     *
     * @param paramsJson
     * @return
     */
    @Override
    public String pagingSmartLock(String paramsJson) {

        DataTransferObject dto = new DataTransferObject();

        if (Check.NuNStrStrict(paramsJson)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }

        RoomSmartLockDto roomStmartDto = JsonEntityTransform.json2Object(paramsJson,RoomSmartLockDto.class);

        PagingResult<IntellectSmartLockVo> pagingResult = intellectSmartLockServiceImpl.paging(roomStmartDto);

        List<IntellectSmartLockVo> list = pagingResult.getRows();

        dto.putValue("list",list);
        dto.putValue("total",pagingResult.getTotal());

        return dto.toJsonString();
    }
	/* (non-Javadoc)
	 * @see com.ziroom.zrp.service.houses.api.IntellectSmartLockService#delRoomSmartLock(java.lang.String)
	 */
	@Override
	public String delRoomSmartLock(String paramsJson) {
		DataTransferObject dto = new DataTransferObject();
		IntellectSmartLockEntity roomSmartLock=JsonEntityTransform.json2Object(paramsJson, IntellectSmartLockEntity.class);
		//查询要删除智能锁列表
		List<IntellectSmartLockEntity> lockList=intellectSmartLockServiceImpl.getDelRoomSmartLockByCondition(roomSmartLock);
		//条件删除智能锁
		int upNum=intellectSmartLockServiceImpl.delRoomSmartLock(roomSmartLock);

		if(upNum>0){
			for(IntellectSmartLockEntity lock:lockList){
				//调用接口删除智能锁
			}
		}
		dto.putValue("upNum", upNum);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.zrp.service.houses.api.IntellectSmartLockService#upSmartLockStatusByServiceId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String upSmartLockStatusByServiceId(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
			int upNum=intellectSmartLockServiceImpl.upSmartLockStatusByServiceId(paramMap);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
            LogUtil.error(LOGGER,"更新智能锁密码下发状态 错误：paramsJson={},e={}",paramJson,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return  dto.toJsonString();
		}
		return dto.toJsonString();
	}

    /**
     * 获取需要补偿的失败记录
     *
     * @return
     * @author zhangyl2
     * @created 2017年12月19日 17:11
     */
    @Override
    public String getFailSmartLockRecord(String paramJson) {
        LogUtil.info(LOGGER, "【getFailSmartLockRecord】参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        List<Integer> sourceTypes = JsonEntityTransform.json2ObjectList(paramJson, Integer.class);
        List<IntellectSmartLockEntity> list = intellectSmartLockServiceImpl.getFailSmartLockRecord(sourceTypes);
        dto.putValue("list", list);
        return dto.toJsonString();
    }

    /**
     * 根据fid更新智能锁
     *
     * @return
     * @author zhangyl2
     * @created 2017年12月20日 14:45
     */
    @Override
    public String updateIntellectSmartLockEntity(String paramJson) {
        LogUtil.info(LOGGER, "【updateIntellectSmartLockEntity】参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        IntellectSmartLockEntity entity = JsonEntityTransform.json2Object(paramJson, IntellectSmartLockEntity.class);
        int count = intellectSmartLockServiceImpl.updateIntellectSmartLockEntity(entity);
        dto.putValue("count", count);
        return dto.toJsonString();
    }


}
