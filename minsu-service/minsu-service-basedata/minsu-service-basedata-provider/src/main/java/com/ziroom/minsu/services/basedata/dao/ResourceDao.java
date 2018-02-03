package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.services.basedata.dto.ResourceRequest;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;

/**
 *
 * <p>
 * 后台菜单操作类
 * </p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Repository("basedata.resourceDao")
public class ResourceDao {

	private String SQLID = "basedata.resourceDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ResourceDao.class);

	/**
	 * 更新菜单信息
	 * 
	 * @param resourceEntity
	 */
	public void updateMenuByFid(ResourceEntity resourceEntity) {
		if (Check.NuNObj(resourceEntity)) {
			return;
		}
		if (Check.NuNStr(resourceEntity.getFid())) {
			LogUtil.info(logger,"on updateMenuByFid the fid is null ");
			throw new BusinessException("on updateMenuByFid the fid is null ");
		}
		mybatisDaoContext.save(SQLID + "updateMenuByFid", resourceEntity);
	}

	/**
	 *
	 * 插入菜单表记录
	 *
	 * @author liyingjie
	 * @created 2016年3月8日 下午4:10:16
	 *
	 * @param resourceEntity
	 */
	public void insertMenuResource(ResourceEntity resourceEntity) {
		mybatisDaoContext.save(SQLID + "insertMenuResource", resourceEntity);
	}

	/**
	 *
	 * 分页查询菜单列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 上午10:54:08
	 *
	 * @param resourceRequest
	 * @return
	 */
	public PagingResult<ResourceEntity> findMenuOperPageList(ResourceRequest resourceRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(resourceRequest.getLimit());
		pageBounds.setPage(resourceRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findMenuOperByCondition", ResourceEntity.class, resourceRequest,
				pageBounds);
	}

	/**
	 *
	 * 查询所有菜单
	 *
	 * @author liyingjie
	 * @created 2016年3月11日 下午10:21:41
	 *
	 * @return
	 */
	public List<ResourceEntity> findAllMenuClasterChildren() {
		return mybatisDaoContext.findAll(SQLID + "findMenuClasterChildren");
	}

	/**
	 *
	 * 资源树查询
	 *
	 * @author bushujie
	 * @created 2016年3月13日 下午7:00:37
	 *
	 * @return
	 */
	public List<TreeNodeVo> findTreeNodeVoList() {
		return mybatisDaoContext.findAll(SQLID + "findMenuTreeVo", TreeNodeVo.class);
	}
	
	/**
	 * 
	 * 用户权限树
	 *
	 * @author bushujie
	 * @created 2016年3月16日 下午9:56:57
	 *
	 * @param userAccount
	 * @return
	 */
	public List<ResourceVo> findResourceByCurrentuserId(String currentuserId){
		return mybatisDaoContext.findAll(SQLID+"findResouresByUser", ResourceVo.class, currentuserId);
	}
	
	/**
	 * 
	 * 根据url 查询资源
	 * 说明：
	 * 1.理论上 一个url对应一个菜单 
	 * 2. 对于存在一个url对应多个菜单的情况，给出日志
	 *
	 * @author yd
	 * @created 2016年10月31日 下午6:58:26
	 *
	 * @param url
	 * @return
	 */
	public List<ResourceEntity> findResourceByUrl(String resUrl){
		return mybatisDaoContext.findAll(SQLID+"findResourceByUrl", ResourceEntity.class, resUrl);
	}

}
