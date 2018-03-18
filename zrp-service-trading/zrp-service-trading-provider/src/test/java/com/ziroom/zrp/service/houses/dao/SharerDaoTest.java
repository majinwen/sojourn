package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.SharerDao;
import com.ziroom.zrp.trading.entity.SharerEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月18日 16:21
 * @since 1.0
 */
public class SharerDaoTest extends BaseTest{

    @Resource(name="trading.sharerDao")
    private SharerDao sharerDao;
    @Test
    public void testSaveSharer(){
        SharerEntity sharerEntity = new SharerEntity();
        sharerEntity.setFname("张三");
        sharerEntity.setFcerttype("1");
        sharerEntity.setFcertnum("22222");
        sharerDao.saveSharer(sharerEntity);
    }

    @Test
    public void testfindByFid(){
        SharerEntity byFid = sharerDao.findByFid("2c908d174c9d1b3d014c9d1cde120002");
    }

    @Test
    public void testlistSharerByContractId(){
        List<SharerEntity> sharerEntities = sharerDao.listSharerByContractId("8a90a3ab5d170d92015d1c1fa2b30281");
    }

    @Test
    public void testDeleteByContractId() {
        String contractId = "8a9099cb57d21f2e0157d22d6c6f0004";

        System.err.println(this.sharerDao.deleteByContractId(contractId));
    }

    @Test
    public void testDeleteByContractIdAndRecord() {
        SharerEntity sharerEntity = new SharerEntity();
        sharerEntity.setFcontractid("8a9eae535f248565015f2485677b0001");
        sharerEntity.setFupdaterid("60001029");
        sharerEntity.setFupdatername("张扬乐");

        System.err.println(this.sharerDao.deleteByContractIdAndRecord(sharerEntity));
    }

    @Test
    public void testlistSharerByRentId(){
        sharerDao.listSharerByRentId(1);
    }

}
