package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.houses.entity.HouseSignInviteRecordEntity;
import com.ziroom.zrp.service.houses.dao.HouseSignInviteRecordDao;

import com.ziroom.zrp.service.houses.dto.QueryInvitereCordParamDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service("houses.houseSignInviteRecordServiceImpl")
public class HouseSignInviteRecordServiceImpl {

    @Resource(name="houses.houseSignInviteRecordDao")
    private HouseSignInviteRecordDao houseSignInviteRecordDao;

    public Map<String, Long> countByRoomIds(String roomIds) {
        List<String> roomIdList = Arrays.asList(roomIds.split(","));
        if (roomIdList == null || roomIdList.isEmpty()) {
            return null;
        }

        List<Map> resultList = houseSignInviteRecordDao.countByRoomIds(roomIdList);

        Map<String, Long> resultMap = new HashMap<>();

        roomIdList.forEach((String roomId) -> {
            resultMap.put(roomId, 0L);
        });

        if (resultList == null || resultList.isEmpty()) {
            return resultMap;
        }

        resultList.forEach((Map map) -> {
            String roomId = (String)map.get("roomId");
            Long num = (Long)map.get("num");
            resultMap.put(roomId, num);
        });


        return resultMap;
    }

    public PagingResult<HouseSignInviteRecordEntity> findListByRoomId(QueryInvitereCordParamDto paramDto) {
        return this.houseSignInviteRecordDao.findListByRoomId(paramDto);
    }

    public void saveSignInviteRecords(List<HouseSignInviteRecordEntity> signInviteList) {
        signInviteList.forEach( houseSignInviteRecordEntity -> {
            houseSignInviteRecordDao.insert(houseSignInviteRecordEntity);
        });
    }
    /**
     * <p>通过合同ID更新邀约记录为已成交</p>
     * @author xiangb
     * @created 2017年10月17日
     * @param contractId合同ID
     * @return
     */
    public int updateIsDealByContractId(String contractId){
    	return houseSignInviteRecordDao.updateIsDealByContractId(contractId);
    }

}
