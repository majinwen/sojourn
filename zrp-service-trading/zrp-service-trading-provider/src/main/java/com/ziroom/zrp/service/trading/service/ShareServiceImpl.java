package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.util.Check;
import com.ziroom.zrp.service.trading.dao.SharerDao;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.SharerEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>合租人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月18日 14:18
 * @since 1.0
 */
@Service("trading.shareServiceImpl")
public class ShareServiceImpl {

    @Resource(name = "trading.sharerDao")
    private SharerDao sharerDao;

    @Resource(name = "trading.rentCheckinPersonServiceImpl")
    private RentCheckinPersonServiceImpl rentCheckinPersonServiceImpl;
    /**
     * 保存合住人
     * @author jixd
     * @created 2017年09月18日 14:25:03
     * @param
     * @return
     */
    public String saveSharer(SharerEntity sharerEntity){
        RentCheckinPersonEntity rentCheckinPersonEntity = rentCheckinPersonServiceImpl.findCheckinPersonByContractId(sharerEntity.getFcontractid());
        if (!Check.NuNObj(rentCheckinPersonEntity)){
            sharerEntity.setRentId(rentCheckinPersonEntity.getId());
            sharerEntity.setUid(rentCheckinPersonEntity.getUid());
        }
        return sharerDao.saveSharer(sharerEntity);
    }

    /**
     * 更新合租人信息
     * @author jixd
     * @created 2017年09月18日 14:25:49
     * @param
     * @return
     */
    public int updateByFid(SharerEntity sharerEntity){
       return  sharerDao.updateByFid(sharerEntity);
    }

    /**
     * 查找合租人信息
     * @author jixd
     * @created 2017年09月18日 14:26:31
     * @param
     * @return
     */
    public SharerEntity findByFid(String fid){
        return sharerDao.findByFid(fid);
    }

    /**
     * 根据合同号查询相关的合租人
     * @author jixd
     * @created 2017年09月18日 16:17:19
     * @param
     * @return
     */
    public List<SharerEntity> listSharerByContractId(String contractId){
        return sharerDao.listSharerByContractId(contractId);
    }
    /**
     *
     * @author jixd
     * @created 2018年01月29日 13:20:13
     * @param
     * @return
     */
    public List<SharerEntity> listSharerByRentId(Integer rentId){
        return sharerDao.listSharerByRentId(rentId);
    }

    /**
     * 逻辑删除合住人信息
     * @author jixd
     * @created 2017年09月18日 16:11:02
     * @param
     * @return
     */
    public int deleteByFid(String fid){
        return sharerDao.deleteByFid(fid);
    }

}
