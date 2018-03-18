package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.HouseSignInviteRecordEntity;
import com.ziroom.zrp.service.houses.api.HouseSignInviteRecordService;
import com.ziroom.zrp.service.houses.dto.QueryInvitereCordParamDto;
import com.ziroom.zrp.service.houses.dto.SignInviteDto;
import com.ziroom.zrp.service.houses.service.HouseSignInviteRecordServiceImpl;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.utils.ZraConst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sun.util.resources.cldr.mas.CalendarData_mas_KE;

import javax.annotation.Resource;

import java.util.*;

/**
 * <p>签约邀请</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月20日 18:52
 * @since 1.0
 */
@Component("houses.houseSignInviteRecordServiceProxy")
public class HouseSignInviteRecordServiceProxy implements HouseSignInviteRecordService {


    private static Logger LOGGER = LoggerFactory.getLogger(HouseSignInviteRecordServiceProxy.class);

    @Resource(name="houses.houseSignInviteRecordServiceImpl")
    private HouseSignInviteRecordServiceImpl houseSignInviteRecordServiceImpl;


    @Override
    public String countByRoomIds(String roomIds) {
        LogUtil.info(LOGGER,"countByRoomIds:{}",roomIds);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(roomIds)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("roomIds参数为空！");
            return dto.toJsonString();
        }
        Map<String, Long> map = houseSignInviteRecordServiceImpl.countByRoomIds(roomIds);
        dto.putValue("signInvite", map);
        return dto.toJsonString();
    }

    @Override
    public String findListByRoomId(String roomId) {
        LogUtil.info(LOGGER,"findListByRoomId:{}",roomId);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(roomId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("roomIds参数为空！");
            return dto.toJsonString();
        }
        List<HouseSignInviteRecordEntity> list = null;

        dto.putValue("list", list);
        return dto.toJsonString();
    }

    @Override
    public String saveSignInviteRecord(String signInviteInfo) {

        LogUtil.info(LOGGER,"saveSignInviteRecord:{}",signInviteInfo);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStr(signInviteInfo)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("signInviteInfo参数为空！");
                return dto.toJsonString();
            }


            SignInviteDto signInviteDto = JsonEntityTransform.json2Entity(signInviteInfo, SignInviteDto.class);
            //转为邀请记录
            List<HouseSignInviteRecordEntity> signInviteList = transferToHouseSignInviteRecords(signInviteDto);
            houseSignInviteRecordServiceImpl.saveSignInviteRecords(signInviteList);

            return dto.toJsonString();

        } catch (Exception e) {
            LogUtil.error(LOGGER,"saveSignInviteRecord", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("saveSignInviteRecord 异常");
            return dto.toJsonString();
        }


    }

    /**
     * 签约邀请拆分为记录信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private List<HouseSignInviteRecordEntity> transferToHouseSignInviteRecords(SignInviteDto signInviteDto) {

        String roomIds = signInviteDto.getRoomIds();
        List<String> roomIdList = Arrays.asList(roomIds.split(","));

        String groupId = UUIDGenerator.hexUUID();
        Integer isDel = ZraConst.NOT_DEL_INT;
        Integer isDeal = 0;
        String customerUid = signInviteDto.getCustomerUid();
        String customerPhone = signInviteDto.getPhone();


        Date createTime = new Date();

        String projectId = signInviteDto.getProjectId();
        Date conStartDate =  signInviteDto.getConStartDate();
        Date inviteDate =  new Date();

        String conType = signInviteDto.getConType();
        Integer rentPeriod = signInviteDto.getRentPeriod();

        String zoId =  signInviteDto.getEmployeeId();
        String zoName = signInviteDto.getHandZo();

        String preContractId = signInviteDto.getPreContractId();
        Map<String, String> roomContractMap = signInviteDto.getRoomContractMap();


        List<HouseSignInviteRecordEntity> signInviteList = new ArrayList<>();

        roomIdList.forEach( roomId -> {

            String signInviteId = UUIDGenerator.hexUUID();
            String contractId = roomContractMap.get(roomId); //根据房间号获取contractId

            String signType = "0";//TODO
            LOGGER.info("transferToHouseSignInviteRecords preContractId:" + preContractId);
            if (Check.NuNStr(preContractId)) {
                signType = "0";
            } else {
                signType = "1";
            }
            LOGGER.info("transferToHouseSignInviteRecords signType:" + signType);
            HouseSignInviteRecordEntity houseSignInviteRecord =  new HouseSignInviteRecordEntity(signInviteId, groupId, customerUid, customerPhone, signType, projectId, roomId, conStartDate, inviteDate, conType, rentPeriod, contractId, zoId, zoName, createTime, isDel, preContractId, isDeal);
            signInviteList.add(houseSignInviteRecord);

        });

        return signInviteList;

    }

    @Override
    public String findPageListByRoomId(String paramDto) {
        DataTransferObject dto = new DataTransferObject();
        QueryInvitereCordParamDto queryInvitereCordParamDto = JsonEntityTransform.json2Entity(paramDto, QueryInvitereCordParamDto.class);
        PagingResult<HouseSignInviteRecordEntity> pr  =  this.houseSignInviteRecordServiceImpl.findListByRoomId(queryInvitereCordParamDto);
        dto.putValue("data",pr);
        return dto.toJsonString();
    }

	@Override
	public String updateIsDealByContractId(String contractId) {
		LogUtil.info(LOGGER, "【updateIsDealByContractId】入参:{}", contractId);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(contractId)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		try{
			houseSignInviteRecordServiceImpl.updateIsDealByContractId(contractId);
			return dto.toJsonString();
		}catch(Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("更新邀约记录异常！");
            return dto.toJsonString();
		}
	}
}
