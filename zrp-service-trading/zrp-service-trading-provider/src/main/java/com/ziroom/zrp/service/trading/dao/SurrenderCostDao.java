package com.ziroom.zrp.service.trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.SurrenderCostEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月13日 14时32分
 * @Version 1.0
 * @Since 1.0
 */
@Repository("trading.surrenderCostDao")
public class SurrenderCostDao {
    private String SQLID = "com.ziroom.zrp.service.trading.dao.SurrenderCostDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 插入tsurrendercos
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时19分19秒
     */
    public int insertSelective(SurrenderCostEntity surrenderCostEntity) {
       return mybatisDaoContext.save(SQLID + "insertSelective", surrenderCostEntity);
    }

    /**
     * 更新tsurrendercost
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时18分59秒
     */
    public int updateBySurId(SurrenderCostEntity surrenderCostEntity) {
        return mybatisDaoContext.update(SQLID + "updateBySurId", surrenderCostEntity);
    }

    /**
     * 删除tsurrendercost
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时32分17秒
     */
    public int deleteBatch(List<String> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("costIds", list);
        return mybatisDaoContext.update(SQLID + "deleteBatch", map);
    }
    /**
     * <p>根据合同ID和解约ID查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public List<SurrenderCostEntity> selectSurrenderCostByParam(String contractId,String surrenderId){
    	Map<String,String> param = new HashMap<String, String>();
    	param.put("contractId", contractId);
    	param.put("surrenderId", surrenderId);
    	return mybatisDaoContext.findAll(SQLID+"selectSurrenderCostByParam",SurrenderCostEntity.class,param);
    }
    
    /**
     * <p>根据surrendercostId查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public SurrenderCostEntity findSurrenderCostByFid(String surrendercostId){
    	return mybatisDaoContext.findOne(SQLID+"findSurrenderCostByFid",SurrenderCostEntity.class,surrendercostId);
    }
}
