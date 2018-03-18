package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.houses.entity.HouseSignInviteRecordEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月20日 16:59
 * @since 1.0
 */
public class HouseSignInviteRecordDaoTest extends BaseTest {

    @Resource(name="houses.houseSignInviteRecordDao")
    private  HouseSignInviteRecordDao houseSignInviteRecordDao;

    @Test
    public void testSelectByPrimaryKey() {
        HouseSignInviteRecordEntity recordEntity = houseSignInviteRecordDao.selectByPrimaryKey(1L);
        System.out.println(recordEntity.getContractId());
    }
    
    @Test
    public void testupdateIsDealByContractId() {
        int isSuccess = houseSignInviteRecordDao.updateIsDealByContractId("8a9e98b75ec7a533015ec7a533d60000");
        System.out.println("======================="+isSuccess);
    }
}
