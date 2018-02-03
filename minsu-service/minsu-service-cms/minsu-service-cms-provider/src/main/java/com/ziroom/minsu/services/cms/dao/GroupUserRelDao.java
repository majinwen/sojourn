package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.GroupUserRelEntity;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>用户组关系</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月10日 15:44
 * @since 1.0
 */
@Repository("cms.groupUserRelDao")
public class GroupUserRelDao {

    private String SQLID = "cms.groupUserRelDao.";

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
    public int save(GroupUserRelEntity groupUserRelEntity) {
        if (Check.NuNStr(groupUserRelEntity.getFid())) {
            groupUserRelEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert", groupUserRelEntity);
    }

    /**
     * 删除关联关系
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月10日 16:23:54
     */
    public int deleteUserRel(String fid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fid", fid);
        return mybatisDaoContext.update(SQLID + "deleteUserRel", paramMap);
    }

    /**
     * 批量删除用户组
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月17日 17:59:41
     */
    public int deleteUserRelBatch(List<String> fids) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fids", fids);
        return mybatisDaoContext.update(SQLID + "deleteUserRelBatch", paramMap);
    }

    /**
     * 分页查询用户关系列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月10日 17:23:40
     */
    public PagingResult<GroupUserRelEntity> listUserRelByPage(GroupRequest groupRequest) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(groupRequest.getLimit());
        pageBounds.setPage(groupRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "listUserRelByPage", GroupUserRelEntity.class, groupRequest, pageBounds);
    }

    /**
     * 查询用户列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月16日 15:02:15
     */
    public List<GroupUserRelEntity> listUserRelFilter(List<String> groupFids, String uid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupFids", groupFids);
        paramMap.put("uid", uid);
        return mybatisDaoContext.findAll(SQLID + "listUserRelFilter", GroupUserRelEntity.class, paramMap);
    }

    /**
     * 查询
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月18日 18:11:12
     */
    public List<GroupUserRelEntity> listUserRelByUid(String groupFid, String uid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupFid", groupFid);
        paramMap.put("uid", uid);
        return mybatisDaoContext.findAll(SQLID + "listUserRelByUid", GroupUserRelEntity.class, paramMap);

    }

    /**
     * 查询组下得人数
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年12月15日 17:33:40
     */
    public long countUserByGroupId(String groupFid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupFid", groupFid);
        return mybatisDaoContext.count(SQLID + "countUserByGroupId", paramMap);
    }

}
