package com.ziroom.minsu.services.basedata.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.base.StaticResourcePicEntity;

/**
 * 
 * <p>静态资源到</p>
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
@Repository("basedata.staticResourcePicDao")
public class StaticResourcePicDao {

	private String SQLID = "basedata.staticResourcePicDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 
	 * 插入静态资源图片
	 *
	 * @author liujun
	 * @created 2017年3月16日
	 *
	 * @param staticResourceEntity
	 */
	public int insertStaticResourcePic(StaticResourcePicEntity staticResourcePicEntity) {
		if (Check.NuNObj(staticResourcePicEntity)) {
			return 0;
		}
		if (Check.NuNStrStrict(staticResourcePicEntity.getResFid())) {
			throw new BusinessException("StaticResourcePicEntity resFid is null or blank");
		}
		return mybatisDaoContext.save(SQLID + "insertStaticResourcePic", staticResourcePicEntity);
	}
	
	/**
	 * 
	 * 查询静态资源图片
	 *
	 * @author liujun
	 * @created 2017年3月16日
	 *
	 * @param staticResourceEntity
	 */
	public StaticResourcePicEntity findStaticResourcePicByResFid(String resFid) {
		if (Check.NuNObj(resFid)) {
			return null;
		}
		return mybatisDaoContext.findOneSlave(SQLID + "findStaticResourcePicByResFid", StaticResourcePicEntity.class, resFid);
	}
	
	/**
	 * 
	 * 更新静态资源图片
	 *
	 * @author liujun
	 * @created 2017年3月16日
	 *
	 * @param staticResourceEntity
	 */
	public int updateStaticResourcePicByResFid(StaticResourcePicEntity staticResourcePicEntity) {
		if (Check.NuNObj(staticResourcePicEntity)) {
			return 0;
		}
		return mybatisDaoContext.update(SQLID + "updateStaticResourcePicByResFid", staticResourcePicEntity);
	}

}
