package com.ziroom.minsu.services.cms.api.inner;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/2 9:36
 * @version 1.0
 * @since 1.0
 */
public interface InviteService {

    /**
     * 获取当前可用的优惠券金额
     * @author afi
     * @return
     */
    public String getInviteCouponInfo(String groupSn);

    /**
     * 根据邀请码获取邀请信息
     *
     * @param inviteCode
     * @return
     */
    public String getInviteByCode(String inviteCode);


    /**
     * 根据uid获取邀请信息
     *
     * @param uid
     * @return
     */
    public String getInviteByUid(String uid, String mobile);


    /**
     * 接受邀请
     *
     * @param paramJson
     * @return
     * @author lishaochuan
     */
    public String accept(String paramJson);


    /**
     * 查询未给邀请人送券list
     *
     * @return
     */
    public String getUnCouponList();


    /**
     * 给邀请人送券
     *
     * @param paramJson
     */
    public String giveInviterCoupon(String paramJson);


    /**
     * 获取受邀请列表查询
     *
     * @param paramJson
     * @author afi
     */
    public String getInviteListByCondition(String paramJson);

    /**
     * 保存invite信息
     * @author yanb
     * @created 2017年12月14日 00:36:11
     * @param  * @param paramJson
     * @return java.lang.String
     */
    public String saveInvite(String paramJson);
}
