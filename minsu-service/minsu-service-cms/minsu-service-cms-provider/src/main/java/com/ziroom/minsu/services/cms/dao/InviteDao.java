package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.services.cms.dto.InviteCmsRequest;
import com.ziroom.minsu.services.cms.dto.InviteListRequest;
import com.ziroom.minsu.services.cms.dto.InviteStateUidRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>邀请码DAO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/1 17:48
 * @version 1.0
 * @since 1.0
 */
@Repository("cms.inviteDao")
public class InviteDao {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(CouponMobileLogDao.class);

    private String SQLID = "cms.invite.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 分页获取当前的邀请关系
     * @author afi
     * @param request
     * @return
     */
    public PagingResult<InviteEntity> getInvitePageByCondition(InviteListRequest request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        Map<String,Object> par = new HashMap<>();
        if (!Check.NuNCollection(request.getUids())){
            par.put("uids",request.getUids());
        }
        if (!Check.NuNCollection(request.getInviteUids())){
            par.put("inviteUids",request.getInviteUids());
        }
        if (!Check.NuNStr(request.getInviteCode())){
            par.put("inviteCode",request.getInviteCode());
        }
        return mybatisDaoContext.findForPage(SQLID + "getInvitePageByCondition", InviteEntity.class, par, pageBounds);
    }

    /**
     * 根据uid获取邀请码信息
     *
     * @param uid
     * @return
     * @author lishaochuan
     */
    public InviteEntity getByUid(String uid) {
        if (Check.NuNStr(uid)) {
            LogUtil.error(LOGGER, "getByUid:{}", uid);
            throw new BusinessException("getByUid参数为空");
        }
        return mybatisDaoContext.findOne(SQLID + "getByUid", InviteEntity.class, uid);
    }


    /**
     * 根据邀请码获取邀请码信息
     *
     * @param inviteCode
     * @return
     * @author lishaochuan
     */
    public InviteEntity getByCode(String inviteCode) {
        if (Check.NuNStr(inviteCode)) {
            LogUtil.error(LOGGER, "getByCode:{}", inviteCode);
            throw new BusinessException("getByCode参数为空");
        }
        return mybatisDaoContext.findOne(SQLID + "getByCode", InviteEntity.class, inviteCode);
    }

    /**
     * 获取未给邀请人送券的list
     *
     * @return
     */
    public List<InviteEntity> getUnCouponList() {
        return mybatisDaoContext.findAll(SQLID + "getUnCouponList", InviteEntity.class);
    }


    /**
     * 保存邀请信息
     *
     * @author yanb
     * @created 2017年12月14日 00:28:32
     * @param  * @param null
     * @return
     */
    public Integer saveInvite(InviteEntity invite) {
        if (Check.NuNObj(invite)) {
            LogUtil.error(LOGGER, "invite参数为空");
            throw new BusinessException("invite参数为空");
        }
        return mybatisDaoContext.save(SQLID + "save", invite);
    }


    /**
     * 保存邀请人uid
     *
     * @param invite
     * @return
     * @author lishaochuan
     */
    public int updateInviteUid(InviteEntity invite) {
        if (Check.NuNObj(invite)) {
            LogUtil.error(LOGGER, "invite参数为空");
            throw new BusinessException("invite参数为空");
        }
        return mybatisDaoContext.update(SQLID + "updateInviteUid", invite);
    }


    /**
     * 给被邀请人送券后，更新邀请状态
     *
     * @param invite
     * @return
     */
    public int updateStatusSendBack(InviteEntity invite) {
        if (Check.NuNObj(invite)) {
            LogUtil.error(LOGGER, "invite参数为空");
            throw new BusinessException("invite参数为空");
        }
        return mybatisDaoContext.update(SQLID + "updateStatusSendBack", invite);
    }


    /**
     * 当前的用户的邀请码
     *
     * @param uid
     * @return
     */
    public Long countInviteByUid(String uid) {
        if (Check.NuNObj(uid)) {
            LogUtil.error(LOGGER, "uid参数为空");
            throw new BusinessException("uid参数为空");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        return mybatisDaoContext.findOne(SQLID + "countInviteByUid", Long.class, map);
    }

	/**
	 * 根据uid(在t_invite表中，对应invite_uid),，获取已邀请的好友列表,
	 *
	 * @author loushuai
	 * @created 2017年12月2日 下午3:20:39
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	public PagingResult<InviteEntity> getBeInvitersByPage(InviteCmsRequest inviterDetailRequest) {
		if(Check.NuNObj(inviterDetailRequest) || Check.NuNStr(inviterDetailRequest.getUid())){
			 LogUtil.error(LOGGER, "uid参数为空");
			 throw new BusinessException("uid参数为空");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(inviterDetailRequest.getPage());
		pageBounds.setLimit(inviterDetailRequest.getLimit());
		return mybatisDaoContext.findForPage(SQLID+"getBeInvitersByPage", InviteEntity.class, inviterDetailRequest, pageBounds);
	}

    /**
     * 根据uid查询用户是否已经参加过邀请活动
     * 必须传入uid
     * @author yanb
     * @created 2017年12月06日 17:38:28
     * @param * @param uid
     * @param * @param inviteSource
     * @return java.lang.Integer
     */
    public Integer isInvitedByUid(InviteStateUidRequest inviteStateUidRequest) {
        if (Check.NuNObj(inviteStateUidRequest)) {
            LogUtil.error(LOGGER, "参数为空");
            throw new BusinessException("参数为空");
        }
        if (Check.NuNStr(inviteStateUidRequest.getUid())) {
            LogUtil.error(LOGGER, "uid为空");
            throw new BusinessException("uid为空");
        }
        return mybatisDaoContext.findOne(SQLID + "isInvitedByUid", Integer.class, inviteStateUidRequest);
    }

    /**
     * 根据用户uid更新
     * @author yanb
     * @param inviteEntity
     * @return
     */
    public Integer updateByUid(InviteEntity inviteEntity) {
        return mybatisDaoContext.update(SQLID + "updateByUid", inviteEntity);
    }

    /**
     * 根据用户uid和活动行类型获取邀请人uid
     *
     * @author yanb
     * return string
     */
    public String getInviteUidByUid(InviteStateUidRequest inviteStateUidRequest) {
        if (Check.NuNObj(inviteStateUidRequest)) {
            LogUtil.error(LOGGER, "参数为空");
            throw new BusinessException("参数为空");
        }
        if (Check.NuNStr(inviteStateUidRequest.getUid())) {
            LogUtil.error(LOGGER, "uid为空");
            throw new BusinessException("uid为空");
        }
        return mybatisDaoContext.findOne(SQLID+"getInviteUidByUid",String.class,inviteStateUidRequest);
    }

}
