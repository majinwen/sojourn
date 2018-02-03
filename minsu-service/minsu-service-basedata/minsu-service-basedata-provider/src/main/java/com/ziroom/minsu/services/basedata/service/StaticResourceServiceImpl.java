package com.ziroom.minsu.services.basedata.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.base.StaticResourceEntity;
import com.ziroom.minsu.entity.base.StaticResourcePicEntity;
import com.ziroom.minsu.services.basedata.dao.StaticResourceDao;
import com.ziroom.minsu.services.basedata.dao.StaticResourcePicDao;
import com.ziroom.minsu.services.basedata.dto.StaticResourceRequest;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;

import java.util.List;

/**
 * 
 * <p>静态资源ServiceImpl</p>
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
@Service("basedata.staticResourceServiceImpl")
public class StaticResourceServiceImpl {

	@Resource(name = "basedata.staticResourceDao")
	private StaticResourceDao staticResourceDao;
	
	@Resource(name = "basedata.staticResourcePicDao")
	private StaticResourcePicDao staticResourcePicDao;

	/**
	 * 分页查询静态资源列表
	 *
	 * @author liujun
	 * @created 2017年3月17日
	 *
	 * @param resourceRequest
	 * @return
	 */
	public PagingResult<StaticResourceEntity> findStaticResourceListByPage(StaticResourceRequest paramRequest) {
		return staticResourceDao.findStaticResourceListByPage(paramRequest);
	}
	
	/**
	 * 添加静态资源主题
	 *
	 * @author lunan
	 * @created 2017年3月20日 下午4:33:39
	 * @param staticResource
	 * @return
	 */
	public int saveStaticEntity(StaticResourceVo staticResourceVo){
		//保存资源
		int result = 0;
		StaticResourceEntity staticResource = new StaticResourceEntity();
		BeanUtils.copyProperties(staticResourceVo, staticResource);
		staticResource.setFid(UUIDGenerator.hexUUID());
		result = this.staticResourceDao.insertStaticResource(staticResource);
		if(result>0){
			if(!Check.NuNStr(staticResourceVo.getMainPicParam())){
				StaticResourcePicEntity staticPicEntity = new StaticResourcePicEntity();
				String [] picParams = staticResourceVo.getMainPicParam().split("\\|");
				staticPicEntity.setFid(UUIDGenerator.hexUUID());
				staticPicEntity.setResFid(staticResource.getFid());		
				staticPicEntity.setPicBaseUrl(picParams[1]);
				staticPicEntity.setPicServerUuid(picParams[0]);
				staticPicEntity.setPicSuffix(picParams[2]);
				result = this.staticResourcePicDao.insertStaticResourcePic(staticPicEntity);
			}
		}
		return result;
	}

	/**
	 * 查询静态资源主题
	 *
	 * @author lunan
	 * @created 2017年3月20日 下午4:33:39
	 * @param staticResourceFid
	 * @return
	 */
	public StaticResourceEntity findStaticResourceByFid(String staticResourceFid){
		return this.staticResourceDao.findStaticResourceByFid(staticResourceFid);
	}

	/**
	 * 查询静态资源主题
	 *
	 * @author lunan
	 * @created 2017年3月21日 下午4:33:39
	 * @param resCode
	 * @return
	 */
	public List<StaticResourceVo> findStaticResListByResCode(String resCode){
		return this.staticResourceDao.findStaticResListByResCode(resCode);
	}

	/**
	 * 根据code查询静态资源内容
	 * @author jixd
	 * @created 2017年06月21日 14:07:37
	 * @param
	 * @return
	 */
	public StaticResourceEntity findStaticResourceByCode(String resCode){
		return staticResourceDao.findStaticResourceByCode(resCode);
	}

	/**
	 * 根据父code查询列表
	 * @author jixd
	 * @created 2017年06月22日 09:10:08
	 * @param
	 * @return
	 */
	public List<StaticResourceEntity> listStaticResourceByParentCode(String parentCode){
		return staticResourceDao.listStaticResourceByParentCode(parentCode);
	}
	
	/**
	 * 
	 * 查询最新一个静态资源
	 *
	 * @author bushujie
	 * @created 2017年7月5日 下午2:47:49
	 *
	 * @param resCode
	 * @return
	 */
	public StaticResourceVo findStaticResByResCode(String resCode){
		return staticResourceDao.findStaticResByResCode(resCode);
	}
	
	/**
	 * 
	 * 更新一个静态资源
	 *
	 * @author bushujie
	 * @created 2017年7月6日 下午1:28:30
	 *
	 * @param staticResourceVo
	 */
	public void updateStaticResource(StaticResourceVo staticResourceVo){
		StaticResourceEntity staticResource = new StaticResourceEntity();
		BeanUtils.copyProperties(staticResourceVo, staticResource);
		staticResourceDao.updateStaticResourceByFid(staticResource);
		if(!Check.NuNStr(staticResourceVo.getMainPicParam())){
			StaticResourcePicEntity staticPicEntity = new StaticResourcePicEntity();
			String [] picParams = staticResourceVo.getMainPicParam().split("\\|");
			staticPicEntity.setResFid(staticResource.getFid());		
			staticPicEntity.setPicBaseUrl(picParams[1]);
			staticPicEntity.setPicServerUuid(picParams[0]);
			staticPicEntity.setPicSuffix(picParams[2]);
			staticResourcePicDao.updateStaticResourcePicByResFid(staticPicEntity);
		}
	}
}
