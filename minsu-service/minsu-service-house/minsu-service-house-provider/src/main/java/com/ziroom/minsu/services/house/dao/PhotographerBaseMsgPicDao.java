/**
 * @FileName: PhotographerBaseMsgPicDao.java
 * @Package com.ziroom.minsu.services.house.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午3:18:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;
import com.ziroom.minsu.services.house.photog.dto.PhotogPicDto;

/**
 * <p>摄影师图片</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Component("photographer.photographerBaseMsgPicDao")
public class PhotographerBaseMsgPicDao {

	
	private String SQLID="photographer.photographerBaseMsgPicDao.";

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年11月4日 下午2:07:57
	 *
	 * @param photographerBaseMsg
	 * @return
	 */
	public int savePhotographerBaseMsgPic(PhotographerBaseMsgPicEntity photographerBaseMsgPic){
		
		if(!Check.NuNObj(photographerBaseMsgPic)&&!Check.NuNStrStrict(photographerBaseMsgPic.getPhotographerUid())){
			if(Check.NuNStrStrict(photographerBaseMsgPic.getFid())) photographerBaseMsgPic.setFid(UUIDGenerator.hexUUID());
		    return this.mybatisDaoContext.save(SQLID+"savePhotographerBaseMsgPic", photographerBaseMsgPic);
		}
		return 0;
	}
	
	/**
	 * 
	 * 根据摄影师uid 修改摄影师基本信息
	 *
	 * @author yd
	 * @created 2016年11月4日 下午2:11:42
	 *
	 * @param photographerBaseMsg
	 * @return
	 */
	public int updatePhotographerBaseMsgPicByFid(PhotographerBaseMsgPicEntity photographerBaseMsgPic){
		
		if(Check.NuNObj(photographerBaseMsgPic)||Check.NuNStrStrict(photographerBaseMsgPic.getFid())){
			throw new BusinessException("修改对象异常");
		}
		return this.mybatisDaoContext.update(SQLID+"updatePhotographerBaseMsgPicByFid", photographerBaseMsgPic);
	}

	/**
	 * 根据uid和picType查询摄影师图片
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param photographerUid
	 * @return
	 */
	public PhotographerBaseMsgPicEntity findPhotogPicByUidAndType(PhotogPicDto picDto) {
		return mybatisDaoContext.findOneSlave(SQLID+"findPhotogPicByUidAndType", PhotographerBaseMsgPicEntity.class, picDto);
	}
}
