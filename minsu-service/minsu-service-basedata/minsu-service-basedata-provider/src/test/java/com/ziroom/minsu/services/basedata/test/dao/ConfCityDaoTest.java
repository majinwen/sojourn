package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.services.basedata.dao.ConfCityDao;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>城市表测试管理</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext-basedata.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfCityDaoTest extends BaseTest {



	@Resource(name="basedata.confCityDao")
	private ConfCityDao confCityDao;




	@Test
	public void getAllCityList(){
		List<ConfCityEntity> reVos=confCityDao.getAllCityList();
		System.out.println(JsonEntityTransform.Object2Json(reVos));
	}


	@Test
	public void getOpenCity(){
		List<ConfCityEntity> reVos=confCityDao.getOpenCity();
		System.out.println(JsonEntityTransform.Object2Json(reVos));
	}


	@Test
	public void findConfCityTreeVoTest(){
		List<TreeNodeVo> reVos=confCityDao.findTreeNodeVoList();
		System.out.println(JsonEntityTransform.Object2Json(reVos));
	}

	@Test
	public void findConfCitySelect(){
		List<TreeNodeVo> selectTree=confCityDao.findConfCitySelect();
		System.err.println(JsonEntityTransform.Object2Json(selectTree));
	}

	@Test
	public void findConfCitySelectForLandlord(){
		List<TreeNodeVo> selectTree=confCityDao.findConfCitySelectForLandlord();
		System.err.println(JsonEntityTransform.Object2Json(selectTree));
	}
	
	@Test
	public void findConfCitySelectForTenant(){
		List<TreeNodeVo> selectTree=confCityDao.findConfCitySelectForTenant();
		System.err.println(JsonEntityTransform.Object2Json(selectTree));
	}

	@Test
	public void findConfCitySelectForAll() {
		List<TreeNodeVo> reVos = confCityDao.findConfCitySelectForAll();
		System.out.println(JsonEntityTransform.Object2Json(reVos));
	}

	@Test
	public void testgetCityNameByCode(){
		String cityName = confCityDao.getCityNameByCode("GD");
		System.out.println(cityName);
	}

	@Test
	public void testfindConfCityChildrenByPcode(){
		String pCode = "110000";
		List<TreeNodeVo> list = confCityDao.findConfCityChildrenByPcode(pCode);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

	@Test
	public void testfindConfCityParentByCode(){
		String pCode = "110000";
		List<TreeNodeVo> list = confCityDao.findConfCityParentByCode(pCode);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

	@Test
	public void testGetCityNameByCodeList(){
		List<String> codeList = new ArrayList<String>();
		codeList.add("110112");
		codeList.add("120000");
		codeList.add("130434");
		codeList.add("130600");

		List<ConfCityEntity> list = confCityDao.getCityNameByCodeList(codeList);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

	@Test
	public void getOpenCityWithFileTest(){
		List<ConfCityEntity> list = confCityDao.getOpenCityWithFile();
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

	@Test
	public void findConfCitySelectForAllTest(){
		
		List<TreeNodeVo> selectTree=confCityDao.findConfCitySelectForAll();
		System.err.println(JsonEntityTransform.Object2Json(selectTree));
	}
}
