package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.SurMeterDetailDao;
import com.ziroom.zrp.trading.entity.SurMeterDetailEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
 * @Date 2017年10月13日
 * @Version 1.0
 * @Since 1.0
 */
@Service("trading.surMeterDetailServiceImpl")
public class SurMeterDetailServiceImpl {
    @Resource(name = "trading.surMeterDetailDao")
    private SurMeterDetailDao surMeterDetailDao;

    /**
     * 退租水电交割表的水电交割费用
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日
     */
    public SurMeterDetailEntity getSDPriceBySurrenderId(String surrenderId) {
        return surMeterDetailDao.getSDPriceBySurrenderId(surrenderId);
    }
    /**
     * <p>保存退租水电交割实体</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int saveSurMeterDetailEntity(SurMeterDetailEntity surMeterDetailEntity){
    	return surMeterDetailDao.saveSurMeterDetailEntity(surMeterDetailEntity);
    }
    /**
     * <p>更新退租水电交割实体</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int updateSurMeterDetailEntity(SurMeterDetailEntity surMeterDetailEntity){
    	return surMeterDetailDao.updateSurMeterDetailEntity(surMeterDetailEntity);
    }
}
