package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.GroupActRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>组管理</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月10日 15:54
 * @since 1.0
 */
@Repository("cms.groupActRelDao")
public class GroupActRelDao {

    private String SQLID = "cms.groupActRelDao.";

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
    public int save(GroupActRelEntity groupActRelEntity) {
        if (Check.NuNStr(groupActRelEntity.getFid())) {
            groupActRelEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert", groupActRelEntity);
    }

    /**
     * 查询活动关系
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月13日 14:10:06
     */
    public List<GroupActRelEntity> listGroupActRelByActSn(String actSn) {
        return mybatisDaoContext.findAll(SQLID + "listGroupActRelByActSn", actSn);
    }

    /**
     * 逻辑删除关系
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月13日 16:35:43
     */
    public int delGroupRelByActSn(String actSn) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("actSn", actSn);
        return mybatisDaoContext.update(SQLID + "delGroupRelByActSn", paramMap);
    }
}
