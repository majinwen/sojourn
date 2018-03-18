package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.CostStandardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>基本信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月25日 17:58
 * @since 1.0
 */
@Repository("houses.costStandardDao")
public class CostStandardDao {

    private String SQLID = "houses.costStandardDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存数据
     * @author jixd
     * @created 2017年09月25日 18:02:32
     * @param
     * @return
     */
    public int insert(CostStandardEntity costStandardEntity){
        if (Check.NuNStr(costStandardEntity.getFid())){
            costStandardEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert",costStandardEntity);
    }

    /**
     * 根据项目id查询 水电
     * @author jixd
     * @created 2017年09月25日 18:07:31
     * @param
     * @return
     */
    public List<CostStandardEntity> findByProjectId(String projectId){
        return mybatisDaoContext.findAll(SQLID + "findByProjectId",CostStandardEntity.class,projectId);
    }

    /**
     *
     * 根据项目id和水电类型 更新水电费标准
     *
     * @author zhangyl2
     * @created 2018年02月09日 16:04
     * @param
     * @return
     */
    public int updateByProjectId(CostStandardEntity costStandardEntity){
        return mybatisDaoContext.update(SQLID + "updateByProjectId", costStandardEntity);
    }


}
