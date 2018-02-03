package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.GroupEntity;
import com.ziroom.minsu.services.cms.dto.ActivityGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>组关联</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月10日 15:40
 * @since 1.0
 */
@Repository("cms.groupDao")
public class GroupDao {

    private String SQLID = "cms.groupDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存组
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月10日 15:43:18
     */
    public int save(GroupEntity groupEntity) {
        if (Check.NuNStr(groupEntity.getFid())) {
            groupEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert", groupEntity);
    }

    /**
      * @description: 根据分组名称、产品线、组类型去查询组的数量
      * @author: lusp
      * @date: 2017/10/17 下午 20:26
      * @params: groupEntity
      * @return: Long
      */
    public Long selectCountByGroupName(GroupEntity groupEntity){
        return mybatisDaoContext.findOne(SQLID+"selectCountByGroupName",Long.class,groupEntity);
    }

    /**
     * 查询组列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月11日 11:01:20
     */
    public PagingResult<GroupEntity> listGroup(ActivityGroupRequest activityGroupRequest) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(activityGroupRequest.getLimit());
        pageBounds.setPage(activityGroupRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "listGroup", GroupEntity.class, activityGroupRequest, pageBounds);
    }

    /**
     * 查找活动组
     *
     * @param
     * @return
     * @author jixd
     * @created 2018年01月26日 13:39:50
     */
    public GroupEntity findGroupByFid(String groupFid) {
        return mybatisDaoContext.findOneSlave(SQLID + "findGroupByFid", GroupEntity.class, groupFid);
    }

}
