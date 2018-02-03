package com.ziroom.minsu.services.cms.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.entity.cms.ShortChainMapEntity;
import com.ziroom.minsu.services.cms.dao.ShortChainMapDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

/**
 * 
 * <p>短链映射dao</p>
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
public class ShortChainMapDaoTest extends BaseTest{


	@Resource(name = "cms.shortChainMapDao")
	private ShortChainMapDao shortChainMapDao;
    
    /**
     * 
     * 新增短链映射信息
     *
     * @author liujun
     * @created 2016年10月28日
     *
     * @param shortChainMapEntity
     */
	@Test
    public void insertShortChainMapEntityTest() {
		ShortChainMapEntity shortChainMapEntity = new ShortChainMapEntity();
		shortChainMapEntity.setOriginalLink("http://troy.ziroom.com/index");
		shortChainMapEntity.setShortLink("troy.ziroom.com?code=00000002");
		shortChainMapEntity.setUniqueCode("00000002");
		shortChainMapEntity.setCheckCode(MD5Util.MD5Encode("http://troy.ziroom.com/index"));
		shortChainMapDao.insertShortChainMapEntity(shortChainMapEntity);
		System.err.println(shortChainMapEntity.getId());
	}
    
    /**
     * 
     * 更新短链映射信息
     *
     * @author liujun
     * @created 2016年10月28日
     *
     * @param houseBedMsg
     * @return
     */
	@Test
    public void updateByIdTest() {
    	ShortChainMapEntity shortChainMapEntity = new ShortChainMapEntity();
    	shortChainMapEntity.setId(3);
    	shortChainMapEntity.setOriginalLink("troy.t.ziroom.com/index");
    	shortChainMapEntity.setShortLink("troy.ziroom.com?code=00000003");
    	shortChainMapEntity.setUniqueCode("00000003");
    	shortChainMapEntity.setCheckCode(MD5Util.MD5Encode("troy.t.ziroom.com/index"));
    	int upNum = shortChainMapDao.updateById(shortChainMapEntity);
    	System.err.println(upNum);
    }

    /**
     * 
     * 根据主键id查询短链信息
     *
     * @author liujun
     * @created 2016年10月28日
     *
     * @param id
     * @return
     */
	@Test
	public void findByIdTest() {
		Integer id = 1;
		ShortChainMapEntity shortChainMapEntity = shortChainMapDao.findById(id);
		System.err.println(JsonEntityTransform.Object2Json(shortChainMapEntity));
	}
	
	/**
	 * 
	 * 根据短链编号查询短链信息
	 *
	 * @author liujun
	 * @created 2016年10月28日
	 *
	 * @param uniqueCode
	 * @return
	 */
	@Test
	public void findByUniqueCodeTest() {
		String uniqueCode = "00000001";
		ShortChainMapEntity shortChainMapEntity = shortChainMapDao.findByUniqueCode(uniqueCode);
		System.err.println(JsonEntityTransform.Object2Json(shortChainMapEntity));
	}

	/**
	 * 
	 * 根据校验码查询短链集合
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param checkCode
	 * @return
	 */
	@Test
	public void findByCheckCodeTest() {
		String checkCode = "87097bf1a07452a060bbb5b80db5effe";
		List<ShortChainMapEntity> list = shortChainMapDao.findByCheckCode(checkCode);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

}