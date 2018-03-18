package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.SurMeterDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
 * @Date 2017年10月13日 14时31分
 * @Version 1.0
 * @Since 1.0
 */
@Repository("trading.surMeterDetailDao")
public class SurMeterDetailDao {
    private String SQLID = "com.ziroom.zrp.service.trading.dao.SurMeterDetailDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 退租水电交割表的水电交割费用
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时18分43秒
     */
    public SurMeterDetailEntity getSDPriceBySurrenderId(String surrenderId) {
        return mybatisDaoContext.findOne(SQLID + "getSDPriceBySurrenderId", SurMeterDetailEntity.class, surrenderId);
    }
    /**
     * <p>保存退租水电交割实体</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int saveSurMeterDetailEntity(SurMeterDetailEntity surMeterDetailEntity){
    	return mybatisDaoContext.save(SQLID+"insert",surMeterDetailEntity);
    }
    /**
     * <p>更新退租水电交割实体</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int updateSurMeterDetailEntity(SurMeterDetailEntity surMeterDetailEntity){
    	return mybatisDaoContext.save(SQLID+"update",surMeterDetailEntity);
    }
}
