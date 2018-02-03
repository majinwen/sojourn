package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.cms.dao.NpsAttendDao;
import com.ziroom.minsu.services.cms.dto.NpsAttendVo;
import com.ziroom.minsu.services.cms.dto.NpsCollectVo;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/14 11:00
 * @version 1.0
 * @since 1.0
 */
@Service("cms.npsAttendServiceImpl")
public class NpsAttendServiceImpl {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InviteServiceImpl.class);

    @Resource(name = "cms.npsAttendDao")
    private NpsAttendDao npsAttendDao;


    /**
     * troy分页查询Nps参与信息
     *
     * @param request
     * @return
     */
    public PagingResult<NpsAttendVo> getByCondition(NpsGetCondiRequest request) {
        return npsAttendDao.getByCondition(request);
    }

    /**
     * troy分页查询Nps参与信息
     *
     * @param request
     * @return
     */
    public PagingResult<NpsCollectVo> getNpsCollect(NpsGetCondiRequest request) {
        return npsAttendDao.getNpsCollect(request);
    }

}
