package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.dao.NpsDao;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;
import com.ziroom.minsu.services.cms.dto.NpsQuantumVo;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @since 1.0
 */
public class NpsDaoTest extends BaseTest {

    @Resource(name = "cms.npsDao")
    private NpsDao npsDao;


    @Test
    public void calculateNPS(){
        NpsGetCondiRequest npsGetCondiRequest = new NpsGetCondiRequest();
        npsGetCondiRequest.setNpsCode("8a9e9aa8555711fd01555711fda20001");
        NpsQuantumVo npsQuantumVo = npsDao.getCalculateNPS(npsGetCondiRequest);
        System.out.print(npsQuantumVo);
    }
    @Test
    public void getNpsNameList(){
        List<NpsEntiy> result = npsDao.getNpsNameList();
        System.out.print(result);
    }
}
