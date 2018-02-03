package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.services.cms.dao.InviteDao;
import com.ziroom.minsu.services.cms.dto.InviteListRequest;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.InviteStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/1 17:58
 * @version 1.0
 * @since 1.0
 */
@Service("cms.inviteServiceImpl")
public class InviteServiceImpl {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InviteServiceImpl.class);

    @Resource(name = "cms.inviteDao")
    private InviteDao inviteDao;


    /**
     * 查询列表
     * @param request
     * @author afi
     */
    public PagingResult<InviteEntity> getInvitePageByCondition(InviteListRequest request) {
        if (Check.NuNObj(request)) {
            throw new BusinessException("request is null");
        }
        return inviteDao.getInvitePageByCondition(request);
    }

    /**
     * 初始化邀请人 幂等操作
     *
     * @param uid
     * @param userTel
     * @author afi
     */
    public InviteEntity inviterInitIdempotency(String uid, String userTel) {
        if (Check.NuNStr(uid)) {
            throw new BusinessException("uid is null");
        }
        InviteEntity has = inviteDao.getByUid(uid);
        if (!Check.NuNObj(has)) {
            //当前用户已经存在
            return has;
        }
        //生成邀请码
        String inviteCode = this.genInviteCode(userTel, 100);
        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setInviteCode(inviteCode);
        inviteEntity.setUid(uid);
        inviteEntity.setInviteStatus(InviteStatusEnum.INIT.getCode());
        inviteDao.saveInvite(inviteEntity);
        return inviteEntity;
    }

    /**
     * 递归生成邀请码
     *
     * @param userTel
     * @param num
     * @return
     */
    private String genInviteCode(String userTel, Integer num) {
        if (ValueUtil.getintValue(num) < 0) {
            throw new BusinessException("获取邀请码异常");
        }
        num = num--;
        //生成邀请码
        String inviteCode = SnUtil.getInviteSn(userTel);
        InviteEntity codeHas = inviteDao.getByCode(inviteCode);
        if (Check.NuNObj(codeHas)) {
            return inviteCode;
        } else {
            return genInviteCode(userTel, num);
        }
    }


    /**
     * 根据uid获取邀请码信息
     *
     * @param uid
     * @return
     * @author lishaochuan
     */
    public InviteEntity getByUid(String uid) {
        return inviteDao.getByUid(uid);
    }

    /**
     * 根据邀请码获取邀请码信息
     *
     * @param inviteCode
     * @return
     * @author lishaochuan
     */
    public InviteEntity getByCode(String inviteCode) {
        return inviteDao.getByCode(inviteCode);
    }


    /**
     * 获取未给邀请人送券的list
     *
     * @return
     */
    public List<InviteEntity> getUnCouponList() {
        return inviteDao.getUnCouponList();
    }


    /**
     *
     * @author yanb
     * @created 2017年12月14日 00:28:32
     * @param  * @param null
     * @return
     */
    public Integer saveInvite(InviteEntity invite) {
        return inviteDao.saveInvite(invite);
    }


}
