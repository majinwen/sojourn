package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.cms.NpsAttendEntiy;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/11 15:32
 * @version 1.0
 * @since 1.0
 */
@Repository("cms.npsAttendDao")
public class NpsAttendDao {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(NpsAttendDao.class);

    private String SQLID = "cms.npsAttendDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取用户nps参与信息
     *
     * @param npsGetRequest
     * @return
     */
    public NpsAttendEntiy getByUidType(NpsGetRequest npsGetRequest) {
        if (Check.NuNObj(npsGetRequest)) {
            LogUtil.error(LOGGER, "getByUidType:{}", npsGetRequest);
            throw new BusinessException("getByCode参数为空");
        }
        return mybatisDaoContext.findOne(SQLID + "getByUidType", NpsAttendEntiy.class, npsGetRequest);
    }


    /**
     * troy分页查询Nps参与信息
     *
     * @param request
     * @return
     */
    public PagingResult<NpsAttendVo> getByCondition(NpsGetCondiRequest request) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());

        return mybatisDaoContext.findForPage(SQLID + "getByCondition", NpsAttendVo.class, request.toMap(), pageBounds);
    }

    /**
     * troy分页查询Nps汇总
     *
     * @param request
     * @return
     */
    public PagingResult<NpsCollectVo> getNpsCollect(NpsGetCondiRequest request) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());

        return mybatisDaoContext.findForPage(SQLID + "getNpsCollect", NpsCollectVo.class, request.toMap(), pageBounds);
    }


    /**
     * 保存用户的参与情况
     * @param npsAttendEntiy
     * @return
     */
    public int saveNpsAttend(NpsAttendEntiy npsAttendEntiy) {
        if (Check.NuNObj(npsAttendEntiy)) {
            LogUtil.error(LOGGER, "npsAttendEntiy 保存对象为空:{}");
            throw new BusinessException("saveNpsAttend参数为空");
        }
        if (Check.NuNStr(npsAttendEntiy.getFid())){
            npsAttendEntiy.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "saveNpsAttend", npsAttendEntiy);
    }


}
