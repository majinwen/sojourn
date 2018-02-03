package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.GroupHouseRelEntity;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import com.ziroom.minsu.services.cms.dto.ZrpActRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月10日 15:49
 * @since 1.0
 */
@Repository("cms.groupHouseRelDao")
public class GroupHouseRelDao {


    private String SQLID = "cms.groupHouseRelDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月10日 15:49:18
     */
    public int save(GroupHouseRelEntity groupHouseRelEntity) {
        if (Check.NuNStr(groupHouseRelEntity.getFid())) {
            groupHouseRelEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert", groupHouseRelEntity);
    }

    /**
     * 删除房源关系
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月10日 16:39:15
     */
    public int deleteHouseRel(GroupHouseRelEntity groupHouseRelEntity) {
        return mybatisDaoContext.update(SQLID + "deleteHouseRel", groupHouseRelEntity);
    }

    /**
     * 分页房源关系列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月10日 17:23:40
     */
    public PagingResult<GroupHouseRelEntity> listHouseRelByFidForPage(GroupRequest groupRequest) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(groupRequest.getLimit());
        pageBounds.setPage(groupRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "listHouseRelByFidForPage", GroupHouseRelEntity.class, groupRequest, pageBounds);
    }

    /**
     * 查询房源列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月16日 15:12:14
     */
    public List<GroupHouseRelEntity> listHouseRelFilter(List<String> groupFids, ZrpActRequest zrpActRequest) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupFids", groupFids);
        paramMap.put("projectId", zrpActRequest.getProjectId());
        paramMap.put("layoutId", zrpActRequest.getLayoutId());
        paramMap.put("roomId", zrpActRequest.getRoomId());
        return mybatisDaoContext.findAll(SQLID + "listHouseRelFilter", GroupHouseRelEntity.class, paramMap);
    }

    /**
      * @description: 查询和当前房源组有交集的房源组数量
      * @author: lusp
      * @date: 2017/10/20 下午 13:57
      * @params: groupHouseRelEntity
      * @return: Long
      */
    public Long selectExistCount(GroupHouseRelEntity groupHouseRelEntity){
        return mybatisDaoContext.findOne(SQLID+"selectExistCount",Long.class,groupHouseRelEntity);
    }

}
