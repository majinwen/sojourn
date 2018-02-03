package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.services.cms.api.inner.InviteService;
import com.ziroom.minsu.services.cms.constant.CmsMessageConst;
import com.ziroom.minsu.services.cms.dto.InviteAcceptRequest;
import com.ziroom.minsu.services.cms.dto.InviteListRequest;
import com.ziroom.minsu.services.cms.service.ActCouponServiceImpl;
import com.ziroom.minsu.services.cms.service.InviteAndCouponServiceImpl;
import com.ziroom.minsu.services.cms.service.InviteServiceImpl;
import com.ziroom.minsu.services.common.constant.ActivityConst;
import com.ziroom.minsu.services.common.constant.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>邀请好友下单代理层</p>
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
@Service("cms.inviteProxy")
public class InviteProxy implements InviteService {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InviteProxy.class);

    @Resource(name = "cms.messageSource")
    private MessageSource messageSource;

    @Resource(name = "cms.inviteServiceImpl")
    private InviteServiceImpl inviteServiceImpl;

    @Resource(name = "cms.actCouponServiceImpl")
    private ActCouponServiceImpl actCouponServiceImpl;

    @Resource(name = "cms.inviteAndCouponServiceImpl")
    private InviteAndCouponServiceImpl inviteAndCouponServiceImpl;


    /**
     * 获取当前可用的优惠券金额
     * @author afi
     * @return
     */
    public String getInviteCouponInfo(String groupSn){
        DataTransferObject dto = new DataTransferObject();
        try {
            //获取当前的优惠券信息
            ActCouponEntity actCouponEntity = inviteAndCouponServiceImpl.getInviteCouponInfo(groupSn);
            dto.putValue("actCouponEntity", actCouponEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * 根据邀请码获取邀请信息
     *
     * @param inviteCode
     * @return
     */
    @Override
    public String getInviteByCode(String inviteCode) {
        LogUtil.info(LOGGER, "参数:{}", inviteCode);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStr(inviteCode)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("邀请码为空");
                return dto.toJsonString();
            }

            // 获取邀请人信息
            InviteEntity inviter = inviteServiceImpl.getByCode(inviteCode);
            if (Check.NuNObj(inviter)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("邀请码信息不存在");
                return dto.toJsonString();
            }
            dto.putValue("inviter", inviter);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * 根据uid获取邀请信息
     *
     * @param uid
     * @return
     */
    @Override
    public String getInviteByUid(String uid, String mobile) {
        LogUtil.info(LOGGER, "参数:{}", uid);
        DataTransferObject dto = new DataTransferObject();
        try {

            InviteEntity invite = inviteServiceImpl.inviterInitIdempotency(uid, mobile);
            dto.putValue("invite", invite);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 被邀请人接受邀请
     *
     * @param paramJson
     * @return
     * @author lishaochuan
     */
    @Override
    public String accept(String paramJson) {
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            InviteAcceptRequest request = JsonEntityTransform.json2Object(paramJson, InviteAcceptRequest.class);
            if (Check.NuNObj(request)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CmsMessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            if (Check.NuNStr(request.getUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("未登录");
                return dto.toJsonString();
            }
            if (Check.NuNStr(request.getInviteCode())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("邀请码为空");
                return dto.toJsonString();
            }

            // 校验给被邀请人送券活动信息，必须有进行中的活动，才继续
            Long count = actCouponServiceImpl.getNoExchangeCountByGroupSn(ActivityConst.InviteConst.INVITEE_GROUP_SN);
            if (count == null || count <= 0) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("活动已失效");
                return dto.toJsonString();
            }

            // 获取邀请人信息
            InviteEntity inviter = inviteServiceImpl.getByCode(request.getInviteCode());
            if (Check.NuNObj(inviter)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("邀请码信息不存在");
                return dto.toJsonString();
            }

            // 校验邀请人不能为自己
            if (request.getUid().equals(inviter.getUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("不能自己邀请自己哦");
                return dto.toJsonString();
            }

            // 获取被邀请人信息
            InviteEntity invitee = inviteServiceImpl.inviterInitIdempotency(request.getUid(), request.getMobile());
            if (!Check.NuNStr(invitee.getInviteUid())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("您已经被邀请过了");
                return dto.toJsonString();
            }


            // 接受邀请
            InviteEntity updateInvite = new InviteEntity();
            updateInvite.setUid(request.getUid());
            updateInvite.setInviteUid(inviter.getUid());
            inviteAndCouponServiceImpl.acceptInviteAndCoupon(updateInvite, ActivityConst.InviteConst.INVITEE_GROUP_SN, dto);

            dto.putValue("inviteUid", inviter.getUid());

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();

    }


    /**
     * 查询未给邀请人送券list
     *
     * @return
     */
    @Override
    public String getUnCouponList() {
        DataTransferObject dto = new DataTransferObject();
        try {

            List<InviteEntity> unCouponList = inviteServiceImpl.getUnCouponList();
            dto.putValue("unCouponList", unCouponList);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * 给邀请人送券
     *
     * @param paramJson
     */
    @Override
    public String giveInviterCoupon(String paramJson) {
        LogUtil.info(LOGGER, "参数：{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            InviteEntity inviteEntity = JsonEntityTransform.json2Object(paramJson, InviteEntity.class);
            if (Check.NuNObj(inviteEntity)) {
                LogUtil.error(LOGGER, "参数为空,{}", paramJson);
                return dto.toJsonString();
            }
            inviteAndCouponServiceImpl.giveInviterCoupon(inviteEntity, ActivityConst.InviteConst.INVITER_GROUP_SN, dto);

            LogUtil.info(LOGGER, "结果：{}", dto.toJsonString());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    /**
     * 获取受邀请列表查询
     *
     * @param paramJson
     * @author afi
     */
    @Override
    public String getInviteListByCondition(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        try {
            InviteListRequest request = JsonEntityTransform.json2Entity(paramJson, InviteListRequest.class);

            PagingResult<InviteEntity> pagingResult = inviteServiceImpl.getInvitePageByCondition(request);
            dto.putValue("pagingResult", pagingResult);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
     * 保存invite信息
     * @author yanb
     * @created 2017年12月14日 00:36:11
     * @param  * @param paramJson
     * @return java.lang.String
     */
    @Override
    public String saveInvite(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        try {
            InviteEntity invite = JsonEntityTransform.json2Object(paramJson,InviteEntity.class);
            Integer result = inviteServiceImpl.saveInvite(invite);
            dto.putValue("result",result);
            LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "[saveInvite]出现异常error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();

    }


}
