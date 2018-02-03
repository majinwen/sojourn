package com.ziroom.minsu.services.basedata.test.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.base.StaticResourceEntity;
import com.ziroom.minsu.services.basedata.dao.StaticResourceDao;
import com.ziroom.minsu.services.basedata.dto.StaticResourceRequest;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.constant.StaticResConst;
import com.ziroom.minsu.valenum.top.StaticResourceTypeEnum;

/**
 * 
 * <p>静态资源dao测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class StaticResourceDaoTest extends BaseTest {

    @Resource(name = "basedata.staticResourceDao")
    private StaticResourceDao staticResourceDao;

    @Test
    public void insertStaticResourceTest() {
		StaticResourceEntity staticResourceEntity = new StaticResourceEntity();
		staticResourceEntity.setResTitle("2017年度TOP50主题");
		staticResourceEntity.setResCode("2017-TOP50");
		staticResourceEntity.setResType(StaticResourceTypeEnum.TEXT.getCode());
		staticResourceEntity.setResContent("测试一下");
		staticResourceEntity.setResRemark("备注一下");
		staticResourceEntity.setCreateFid("00300CB2213DDACBE05010AC69062479");
		int upNum = staticResourceDao.insertStaticResource(staticResourceEntity);
		System.err.println(upNum);
    }
    
    @Test
    public void findStaticResourceByFidTest() {
    	String staticResourceFid = "8a9e99c75adb5dae015adb5daea40000";
		StaticResourceEntity entity = staticResourceDao.findStaticResourceByFid(staticResourceFid);
    	System.err.println(JsonEntityTransform.Object2Json(entity));
    }
    
    @Test
    public void findStaticResourceListByPageTest() {
    	StaticResourceRequest paramRequest = new StaticResourceRequest();
		paramRequest.setResCode("2017-TOP50");
		paramRequest.setResTitle("2017年度");
		paramRequest.setResType(StaticResourceTypeEnum.TEXT.getCode());
		paramRequest.setCreateDateStart("2017-01-01");
		paramRequest.setCreateDateEnd("2017-04-01");
		List<String> createFidList = new ArrayList<String>();
		createFidList.add("00300CB2213DDACBE05010AC69062479");
		paramRequest.setCreateFidList(createFidList );
		PagingResult<StaticResourceEntity> pagingResult = staticResourceDao.findStaticResourceListByPage(paramRequest);
    	System.err.println(JsonEntityTransform.Object2Json(pagingResult));
    }
    
    @Test
    public void findStaticResListByResCodeTest(){
    	 List<StaticResourceVo> kkList=staticResourceDao.findStaticResListByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_TITLE_PIC);
    	 System.err.println(JsonEntityTransform.Object2Json(kkList));
    }
    
    @Test
    public void findStaticResByResCodeTest(){
    	StaticResourceVo vo=staticResourceDao.findStaticResByResCode(StaticResConst.HosueTopCode.TOP_HOUSE_TITLE_PIC);
    	System.err.println(JsonEntityTransform.Object2Json(vo));
    }
}
