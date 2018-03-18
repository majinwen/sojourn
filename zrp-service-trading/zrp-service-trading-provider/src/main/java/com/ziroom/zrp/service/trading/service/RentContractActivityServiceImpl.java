package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.RentContractActivityDao;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
 * @Date 2017年11月03日 11时45分
 * @Version 1.0
 * @Since 1.0
 */
@Service("trading.rentContractActivityServiceImpl")
public class RentContractActivityServiceImpl {

    @Resource(name = "trading.rentContractActivityDao")
    private RentContractActivityDao rentContractActivityDao;

    /**
     * 是否参加海燕其灵计划
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时42分07秒
     */
    public Integer havePlanOfHaiYanOfQiLing(String contractId, String haiyan_plan_qiling) {
        return rentContractActivityDao.havePlanOfHaiYanOfQiLing(contractId, haiyan_plan_qiling);
    }

    public List<RentContractActivityEntity> getContractActivityList(String contractId){
        return rentContractActivityDao.getContractActivityList(contractId);
    }
}
