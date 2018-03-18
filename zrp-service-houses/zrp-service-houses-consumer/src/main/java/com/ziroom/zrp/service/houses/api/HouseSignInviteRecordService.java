package com.ziroom.zrp.service.houses.api;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.houses.entity.HouseSignInviteRecordEntity;
import com.ziroom.zrp.service.houses.dto.QueryInvitereCordParamDto;

import java.util.List;

/**
 * <p>签约邀请记录service</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月20日 19:23
 * @since 1.0
 */
public interface HouseSignInviteRecordService {

    /**
     * 查询房间签约邀约数量
     * @author cuiyuhui
     * @created  
     * @param roomIds 多个房间id
     * @return 
     */
    String countByRoomIds(String roomIds);

    /**
     * TODO 加分页
     * 查询邀约记录
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String findListByRoomId(String roomId);

    /**
     * 保存签约邀请
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String saveSignInviteRecord(String signInviteInfo);


    String findPageListByRoomId(String paramDto);
    /**
     * <p>通过合同ID更新邀约记录为已成交</p>
     * @author xiangb
     * @created 2017年10月17日
     * @param
     * @return
     */
    String updateIsDealByContractId(String contractId);
}
