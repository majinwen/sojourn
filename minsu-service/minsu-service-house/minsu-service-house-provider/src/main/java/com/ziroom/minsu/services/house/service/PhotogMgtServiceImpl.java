/**
 * @FileName: photographerMgtServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author liujun
 * @created 2016年11月7日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.services.house.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgExtEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;
import com.ziroom.minsu.services.house.dao.PhotographerBaseMsgDao;
import com.ziroom.minsu.services.house.dao.PhotographerBaseMsgExtDao;
import com.ziroom.minsu.services.house.dao.PhotographerBaseMsgPicDao;
import com.ziroom.minsu.services.house.photog.dto.PhotogDetailDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogPicDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogRequestDto;

/**
 * <p>摄影师管理实现</p>
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
@Service("photog.photogMgtServiceImpl")
public class PhotogMgtServiceImpl {
	
	@Resource(name="photographer.photographerBaseMsgDao")
	private PhotographerBaseMsgDao photogBaseMsgDao;
	
	@Resource(name="photographer.photographerBaseMsgExtDao")
	private PhotographerBaseMsgExtDao photographerBaseMsgExtDao;
	
	@Resource(name="photographer.photographerBaseMsgPicDao")
	private PhotographerBaseMsgPicDao photographerBaseMsgPicDao;

	/**
	 * 分页查询摄影师基本信息
	 *
	 * @author liujun
	 * @created 2016年11月7日
	 *
	 * @param photogDto
	 * @return
	 */
	public PagingResult<PhotographerBaseMsgEntity> findPhotographerListByPage(PhotogRequestDto photogDto) {
		return photogBaseMsgDao.findPhotographerListByPage(photogDto);
	}

	/**
	 * 新增摄影师信息
	 * 1.摄影师基本信息(非空)
	 * 2.摄影师扩展信息
	 *
	 * @author liujun
	 * @created 2016年11月7日
	 *
	 * @param photogDto
	 * @return 
	 */
	public int insertPhotographerMsg(PhotogDto photogDto) {
		int upNum = 0;
		PhotographerBaseMsgEntity base = photogDto.getBase();
		String photographerUid = UUIDGenerator.hexUUID();
		base.setPhotographerUid(photographerUid);
		base.setCreateDate(new Date());
		upNum += photogBaseMsgDao.savePhotographerBaseMsg(base);
		
		PhotographerBaseMsgExtEntity ext = photogDto.getExt();
		if (Check.NuNObj(ext)) {
			ext = new PhotographerBaseMsgExtEntity();
		}
		ext.setPhotographerUid(photographerUid);
		ext.setCreateDate(new Date());
		upNum += photographerBaseMsgExtDao.savePhotographerBaseMsgExt(ext);
		return upNum;
	}

	/**
	 * 根据摄影师uid查询信息
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param photographerUid
	 * @return
	 */
	public PhotogDetailDto findPhotogDetailByUid(String photographerUid) {
		return photogBaseMsgDao.findPhotogDetailByUid(photographerUid);
	}

	/**
	 * 修改摄影师信息
	 * 1.摄影师基本信息(非空)
	 * 2.摄影师扩展信息(为空则不修改)
	 *
	 * @author liujun
	 * @created 2016年11月8日
	 *
	 * @param photogDto
	 * @return 
	 */
	public int updatePhotographerMsg(PhotogDto photogDto) {
		int upNum = 0;
		PhotographerBaseMsgEntity base = photogDto.getBase();
		base.setLastModifyDate(new Date());
		upNum += photogBaseMsgDao.updatePhotographerBaseMsgByUid(base);
		
		PhotographerBaseMsgExtEntity ext = photogDto.getExt();
		if (!Check.NuNObj(ext)) {
			if (!base.getPhotographerUid().equals(ext.getPhotographerUid())) {
				ext.setPhotographerUid(base.getPhotographerUid());
			}
			upNum += photographerBaseMsgExtDao.updatePhotographerBaseMsgExtByUid(ext);
		}
		return upNum;
	}

	/**
	 * 根据uid和picType查询摄影师图片
	 *
	 * @author liujun
	 * @created 2016年11月9日
	 *
	 * @param picDto
	 * @return
	 */
	public PhotographerBaseMsgPicEntity findPhotogPicByUidAndType(PhotogPicDto picDto) {
		return photographerBaseMsgPicDao.findPhotogPicByUidAndType(picDto);
	}

	/**
	 * 修改摄影师图片信息
	 *
	 * @author liujun
	 * @created 2016年11月9日
	 *
	 * @param picEntity
	 * @return
	 */
	public int updatePhotographerPicMsg(PhotographerBaseMsgPicEntity picEntity) {
		return photographerBaseMsgPicDao.updatePhotographerBaseMsgPicByFid(picEntity);
	}

	/**
	 * 新增摄影师图片信息
	 *
	 * @author liujun
	 * @created 2016年11月9日
	 *
	 * @param picEntity
	 * @return
	 */
	public int insertPhotographerPicMsg(PhotographerBaseMsgPicEntity picEntity) {
		return photographerBaseMsgPicDao.savePhotographerBaseMsgPic(picEntity);
	}

}
