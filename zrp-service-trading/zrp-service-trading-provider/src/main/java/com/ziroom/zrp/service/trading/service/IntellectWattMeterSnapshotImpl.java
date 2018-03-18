package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.service.trading.dao.IntellectWattMeterSnapshotDao;
import com.ziroom.zrp.trading.entity.IntellectWattMeterSnapshotEntity;
import com.zra.common.dto.base.BasePageParamDto;
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
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月24日 15:07
 * @since 1.0
 */
@Service("trading.intellectWattMeterSnapshotImpl")
public class IntellectWattMeterSnapshotImpl {

    @Resource(name = "trading.intellectWattMeterSnapshotDao")
    private IntellectWattMeterSnapshotDao intellectWattMeterSnapshotDao;


    /**
     *
     * 插入智能电表充电记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午5:10:04
     *
     * @param intellectWattMeterSnapshotEntity
     */
    public int insertIntellectWattMeterSnapshot(IntellectWattMeterSnapshotEntity intellectWattMeterSnapshotEntity){
        return  intellectWattMeterSnapshotDao.insertIntellectWattMeterSnapshot(intellectWattMeterSnapshotEntity);
    }

    /**
     * 查找
     * @param serviceId
     * @return
     */
    public IntellectWattMeterSnapshotEntity findIntellectWattMeterByServiceId(String serviceId){
        return intellectWattMeterSnapshotDao.findIntellectWattMeterByServiceId(serviceId);
    }

    /**
     * 更新智能水电快照
     * @author jixd
     * @created 2018年02月08日 14:22:44
     * @param
     * @return
     */
    public int updateIntellectWattMeterSnapshot(IntellectWattMeterSnapshotEntity intellectWattMeterSnapshotEntity){
        return intellectWattMeterSnapshotDao.updateIntellectWattMeterSnapshot(intellectWattMeterSnapshotEntity);
    }

    /**
     * 查询失败重试得记录
     * @author jixd
     * @created 2018年02月23日 10:10:22
     * @param
     * @return
     */
    public PagingResult<IntellectWattMeterSnapshotEntity> listRetryWattMeterPage(BasePageParamDto basePageParamDto){
        return intellectWattMeterSnapshotDao.listRetryWattMeterPage(basePageParamDto);
    }

}
