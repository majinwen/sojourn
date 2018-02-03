package com.ziroom.minsu.services.customer.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.TelExtensionEntity;
import com.ziroom.minsu.services.customer.dao.TelExtensionDao;
import com.ziroom.minsu.services.customer.dto.TelExtensionDto;
import com.ziroom.minsu.services.customer.entity.TelExtensionVo;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
public class TelExtensionDaoTest extends BaseTest {

    @Resource(name="customer.telExtensionDao")
    private TelExtensionDao telExtensionDao;


    @Test
    public void TestinsertExtension(){
        TelExtensionEntity entity = new TelExtensionEntity();
        entity.setUid("uid");
        entity.setCreateUid("createUid");
        telExtensionDao.insertExtension(entity);
    }


    @Test
    public void TestgetExtensionByUid(){
        TelExtensionEntity entity = telExtensionDao.getExtensionByUid("uid");
        System.out.println(JsonEntityTransform.Object2Json(entity));
    }

    @Test
    public void TestgetExtensionVOByPage(){
        TelExtensionDto telExtensionDto = new TelExtensionDto();
        PagingResult<TelExtensionVo> pagingResult =  telExtensionDao.getExtensionVOByPage(telExtensionDto);
        System.out.println(JsonEntityTransform.Object2Json(pagingResult));
    }



    @Test
    public void TestupdateError(){
        telExtensionDao.updateStatus("uid",12321,1);

    }

}
