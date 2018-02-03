
package com.ziroom.minsu.services.message.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.message.dto.LandlordComplainRequest;
import com.ziroom.minsu.services.message.entity.SysComplainVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.message.SysComplainEntity;

/**
 * <p>投诉建议表持久化层</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @since 1.0
 */
@Repository("message.sysComplainDao")
public class SysComplainDao {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(SysComplainDao.class);

    private String SQLID = "message.sysComplainDao.";

    @Autowired
    @Qualifier("message.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 保存投诉建议实体
     *
     * @param sysComplainEntity
     * @return
     * @author yd
     * @created 2016年5月4日 上午11:53:29
     */
    public int save(SysComplainEntity sysComplainEntity) {

        if (Check.NuNObj(sysComplainEntity)) return -1;
        return this.mybatisDaoContext.save(SQLID + "insertSelective", sysComplainEntity);
    }

    /**
     *
     * @author wangwentao 2017/4/25 14:18
     */
    public SysComplainEntity selectByPrimaryKey(Integer id) {
        if(Check.NuNObj(id)){
            return null;
        }
        return this.mybatisDaoContext.findOne(SQLID + "selectByPrimaryKey", SysComplainEntity.class, id);
    }

    /**
     * 根据条件查询投诉表
     *
     * @author wangwentao 2017/4/24 19:32
     */
    public PagingResult<SysComplainVo> queryByCondition(LandlordComplainRequest request) {
        if (Check.NuNObj(request)) {
            LogUtil.info(logger, "queryByCondition 参数对象为空");
            throw new BusinessException("queryByCondition 参数对象为空");
        }
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "queryByCondition", SysComplainVo.class, request, pageBounds);
    }
}
