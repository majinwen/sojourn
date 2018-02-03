package com.ziroom.minsu.services.basedata.test.api.inner;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.proxy.ConfTagServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @since 1.0
 */
public class ConfTagServiceTest extends BaseTest {

    @Resource(name = "basedata.confTagServiceProxy")
    private ConfTagServiceProxy confTagService;

    @Test
    public void findByConfTagRequest(){
    	
    	 ConfTagRequest request = new ConfTagRequest();
//		 request.setTagName("海");
//		 request.setIsValid(YesOrNoEnum.YES.getCode());
    	 String result = confTagService.findByConfTagRequest(JsonEntityTransform.Object2Json(request));
    	 System.out.println(result);	
    }
    
    
}
