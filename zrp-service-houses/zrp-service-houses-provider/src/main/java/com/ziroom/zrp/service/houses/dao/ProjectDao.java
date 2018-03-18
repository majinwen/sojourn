package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 项目信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017年09月06日
 * @version 1.0
 * @since 1.0
 */
@Repository("houses.projectDao")
public class ProjectDao {

	private String SQLID = "houses.projectDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 保存项目信息
	 * @author jixd
	 * @created 2017年09月06日 09:59:58
	 * @param
	 * @return
	 */
	public int saveProject(ProjectEntity projectEntity){
		if (Check.NuNStr(projectEntity.getFid())){
			projectEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "saveProject",projectEntity);
	}

	/**
	 * 更新项目信息
	 * @author jixd
	 * @created 2017年09月06日 10:21:32
	 * @param
	 * @return
	 */
	public int updateProjectByFid(ProjectEntity projectEntity){
		return mybatisDaoContext.update(SQLID + "updateProjectByFid",projectEntity);


	}

	/**
	 * 根据code查找项目信息
	 * @author jixd
	 * @created 2017年09月07日 09:19:31
	 * @param
	 * @return
	 */
	public ProjectEntity findProjectByCode(String code){
		return mybatisDaoContext.findOne(SQLID + "findProjectByCode",ProjectEntity.class,code);

	}
	
	public ProjectEntity findProjectById(String projectId){
		return mybatisDaoContext.findOne(SQLID+"findProjectById",ProjectEntity.class,projectId);
	}

	/**
	  * @description: 分页查询项目列表
	  * @author: lusp
	  * @date: 2017/10/19 下午 16:59
	  * @params: addHouseGroupDto
	  * @return: PagingResult<ProjectEntity>
	  */
	public PagingResult<ProjectEntity> findProjectListForPage(AddHouseGroupDto addHouseGroupDto){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(addHouseGroupDto.getLimit());
		pageBounds.setPage(addHouseGroupDto.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findProjectListForPage",ProjectEntity.class,addHouseGroupDto,pageBounds);
	}


    /**
     *  条件查询项目
     * @author yd
     * @created
     * @param
     * @return
     */
	public List<ProjectEntity> queryAllPro(AddHouseGroupDto addHouseGroupDto){
		return mybatisDaoContext.findAll(SQLID+"queryAllPro",ProjectEntity.class,addHouseGroupDto);
	}
	
	/**
	 * 
	 * 查询用户项目权限列表
	 *
	 * @author bushujie
	 * @created 2017年12月8日 下午3:33:41
	 *
	 * @param empCode
	 * @return
	 */
	public List<String> userProjectList(String empCode){
		return mybatisDaoContext.findAll(SQLID+"userProjectList", String.class, empCode);
	}
}
