package com.ziroom.minsu.services.cms.api.inner;

import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/11 15:34
 * @version 1.0
 * @since 1.0
 */
public interface NpsService {

    /**
     * 获取Nps
     * @param paramJson
     * @return
     */
    String getNps(String paramJson);


    /**
     * troy分页查询Nps参与信息
     * @param paramJson
     * @return
     */
    String getNpsAttendForPage(String paramJson);

    /**
     * troy分页查询Nps汇总信息
     * @param paramJson
     * @return
     */
    String getNpsForPage(String paramJson);


    /**
     * 参与nps调查
     * @param paramJson
     * @return
     */
    String saveNpsAttend(String paramJson);

    /**
     * 修改nps状态
     * @param
     * @return
     */
    String editNpsStatus(NpsEntiy nps);

    /**
     * @Description: 分时间段计算NPS值
     * @Author:lusp
     * @Date: 2017/7/11 10:33
     * @Params: nps
     */
    String calculateNPS(NpsGetCondiRequest nps);

    /**
     * @Description: 获取NPS name code集合
     * @Author: lusp
     * @Date: 2017/7/13 18:54
     * @Params:
     */
    String npsNameList();
}
