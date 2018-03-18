package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.DeliveryNotifyLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>物业交割提示信息记录表</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月28日 15:32
 * @since 1.0
 */
@Repository("trading.deliveryNotifyLogDao")
public class DeliveryNotifyLogDao {

    private String SQLID = "trading.deliveryNotifyLogDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存物业交割记录提示消息
     * @author jixd
     * @created 2017年09月28日 15:33:40
     * @param
     * @return
     */
    public int save(DeliveryNotifyLogEntity deliveryNotifyLogEntity){
        return mybatisDaoContext.save(SQLID + "insert",deliveryNotifyLogEntity);
    }


    public List<DeliveryNotifyLogEntity> findByContractId(String contractId){
        return mybatisDaoContext.findAllByMaster(SQLID + "findByContractId",DeliveryNotifyLogEntity.class,contractId);
    }

}
