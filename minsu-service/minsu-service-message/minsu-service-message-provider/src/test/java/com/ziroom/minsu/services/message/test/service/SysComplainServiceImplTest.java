/**
 * @FileName: MsgBaseServiceImplTest.java
 * @Package com.ziroom.minsu.services.message.test.service
 * @author yd
 * @created 2016年4月18日 下午3:06:09
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.message.dto.LandlordComplainRequest;
import com.ziroom.minsu.services.message.entity.SysComplainVo;
import com.ziroom.minsu.services.message.service.SysComplainServiceImpl;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author wangwentao 2017/4/25 13:06
 */
public class SysComplainServiceImplTest extends BaseTest {

    @Resource(name = "message.sysComplainServiceImpl")
    private SysComplainServiceImpl sysComplainServiceImpl;

    @Test
    public void queryByConditionTest() {
        LandlordComplainRequest request = new LandlordComplainRequest();
        DataTransferObject dto = new DataTransferObject();
        PagingResult<SysComplainVo> pagingResult = sysComplainServiceImpl.queryByCondition(request);
        dto.putValue("total", pagingResult.getTotal());
        dto.putValue("rows", pagingResult.getRows());
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(dto.toJsonString());
        List<SysComplainVo> list = resultDto.parseData("list", new TypeReference<List<SysComplainVo>>() {
        });
        System.out.println("*************************");
        System.out.println(list);
    }

}
