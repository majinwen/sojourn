package com.ziroom.minsu.services.cms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ShortChainMapEntity;

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
@Repository("cms.shortChainMapDao")
public class ShortChainMapDao {


    private String SQLID="cms.shortChainMapDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 新增短链映射信息
     *
     * @author liujun
     * @created 2016年10月27日
     *
     * @param shortChainMapEntity
     */
    public void insertShortChainMapEntity(ShortChainMapEntity shortChainMapEntity) {
		mybatisDaoContext.save(SQLID+"insertShortChainMapEntity", shortChainMapEntity);
	}
    
    /**
     * 
     * 更新短链映射信息
     *
     * @author liujun
     * @created 2016年10月27日
     *
     * @param houseBedMsg
     * @return
     */
    public int updateById(ShortChainMapEntity shortChainMapEntity) {
    	return mybatisDaoContext.update(SQLID+"updateById", shortChainMapEntity);
    }

    /**
     * 
     * 根据主键id查询短链信息
     *
     * @author liujun
     * @created 2016年10月27日
     *
     * @param id
     * @return
     */
	public ShortChainMapEntity findById(Integer id) {
		return mybatisDaoContext.findOne(SQLID + "findById", ShortChainMapEntity.class, id);
	}
	
	/**
	 * 
	 * 根据短链编号查询短链信息
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param uniqueCode
	 * @return
	 */
	public ShortChainMapEntity findByUniqueCode(String uniqueCode) {
		return mybatisDaoContext.findOne(SQLID + "findByUniqueCode", ShortChainMapEntity.class, uniqueCode);
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
	public List<ShortChainMapEntity> findByCheckCode(String checkCode) {
		return mybatisDaoContext.findAll(SQLID + "findByCheckCode", ShortChainMapEntity.class, checkCode);
	}

}