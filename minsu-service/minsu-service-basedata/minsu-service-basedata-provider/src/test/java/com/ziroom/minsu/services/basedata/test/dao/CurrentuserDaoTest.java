package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.dao.CurrentuserDao;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>用户测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class CurrentuserDaoTest extends BaseTest {

    @Resource(name = "basedata.currentuserDao")
    private CurrentuserDao currentuserDao;

    @Test
    public void TestGetCurrentuserByFid() {
        CurrentuserEntity currentuserEntity = currentuserDao.getCurrentuserByFid("5c3cf8c1-e884-11e5-9cf9-0050568f07f8");

        System.out.println(JsonEntityTransform.Object2Json(currentuserEntity));
    }

    @Test
    public void insertCurrentuserTest() {
        CurrentuserEntity currentuserEntity = new CurrentuserEntity();
        currentuserEntity.setFid(UUIDGenerator.hexUUID());
        currentuserEntity.setUserAccount("busj");
        currentuserEntity.setUserPassword("123456");
        currentuserEntity.setEmployeeFid("ddddd");
        currentuserEntity.setNationCode("US");
        currentuserEntity.setProvinceCode("dddd");
        currentuserEntity.setCityCode("meiguo");
        currentuserEntity.setAreaCode("area");
        currentuserDao.insertCurrentuser(currentuserEntity);
    }

    @Test
    public void findCurrentuserPageTest() {
        CurrentuserRequest currentuserRequest = new CurrentuserRequest();
        /*currentuserRequest.setLimit(5);
        currentuserRequest.setPage(1);*/
        currentuserRequest.setAccountStatus(0);
        PagingResult<CurrentuserVo> list = currentuserDao.findCurrentuserPageList(currentuserRequest);
        System.err.println(JsonEntityTransform.Object2Json(list.getRows()));
    }

    @Test
    public void findCurrentuserByAccountTest(){
        CurrentuserEntity currentuserEntity=currentuserDao.getCurrentuserEntityByAccount("busj");
        System.err.println(JsonEntityTransform.Object2Json(currentuserEntity));
    }

    @Test
    public void updateCurrentuserByFidTest(){
    	CurrentuserEntity user = new CurrentuserEntity();
    	user.setFid("8a9e9aaf5456a3aa015456a3ab8b0001");
    	user.setAccountStatus(1);
    	int upNum = currentuserDao.updateCurrentuserByFid(user);
    	System.err.println(upNum);
    }

}
