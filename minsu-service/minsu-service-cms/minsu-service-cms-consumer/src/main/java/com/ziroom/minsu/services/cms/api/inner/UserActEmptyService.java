package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p>活动轮空</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/10.
 * @version 1.0
 * @since 1.0
 */
public interface UserActEmptyService {


    /**
     * 保存用户的轮空
     * @param paramJson
     * @return
     */
    String saveUserEmpty(String paramJson);


    /**
     * 校验当前活动是否参加过
     * @param mobile
     * @param groupSn
     * @return
     */
    String countEmptyByMobileAndGroupSn(String mobile,String groupSn);


    /**
     * 校验当前活动是否参加过
     * @param mobile
     * @param actSn
     * @return
     */
    String countEmptyEmptyByMobileAndActSn(String mobile,String actSn);

}
