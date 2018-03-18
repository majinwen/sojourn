package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.houses.entity.CostStandardEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.dao.CostStandardDao;
import com.ziroom.zrp.service.houses.dao.ProjectDao;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>事物处理层</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月06日 10:39
 * @since 1.0
 */
@Service("houses.projectServiceImpl")
public class ProjectServiceImpl {

    @Resource(name = "houses.projectDao")
    private ProjectDao projectDao;

    @Resource(name = "houses.costStandardDao")
    private CostStandardDao costStandardDao;

    /**
     * 更新
     * @author jixd
     * @created 2017年09月06日 10:42:02
     * @param
     * @return
     */
    public int updateProjectByFid(ProjectEntity projectEntity){
        return projectDao.updateProjectByFid(projectEntity);
    }

    /**
     * 根据项目编号查找
     * @author jixd
     * @created 2017年09月07日 09:20:36
     * @param
     * @return
     */
    public ProjectEntity findProjectByCode(String code){
        return projectDao.findProjectByCode(code);
    }
    /**
     * 
     * <p>根据项目ID查询项目详情</p>
     * @author xiangb
     * @created 2017年9月13日
     * @param
     * @return
     */
    public ProjectEntity findProjectById(String projectId){
    	return projectDao.findProjectById(projectId);
    }

    /**
     * 查询水电基本费用列表
     * @author jixd
     * @created 2017年09月25日 18:09:55
     * @param
     * @return
     */
    public List<CostStandardEntity> findCostStandardByProjectId(String projectId){
        return costStandardDao.findByProjectId(projectId);
    }

    /**
     *
     * 根据项目id和水电类型 更新水电费标准
     *
     * @author zhangyl2
     * @created 2018年02月09日 16:05
     * @param
     * @return
     */
    public int updateCostStandard(CostStandardEntity costStandardEntity){
        return costStandardDao.updateByProjectId(costStandardEntity);
    }

    /**
     * @description: 分页查询项目列表
     * @author: lusp
     * @date: 2017/10/19 下午 17:01
     * @params: addHouseGroupDto
     * @return: PagingResult<ProjectEntity>
     */
    public PagingResult<ProjectEntity> findProjectListForPage(AddHouseGroupDto addHouseGroupDto){
        return projectDao.findProjectListForPage(addHouseGroupDto);
    }

    /**
     *  条件查询 项目
     * @author yd
     * @created
     * @param
     * @return
     */
    public List<ProjectEntity> queryAllPro(AddHouseGroupDto addHouseGroupDto){
        return projectDao.queryAllPro(addHouseGroupDto);
    }
    
    /**
     * 
     *  查询用户项目权限列表
     *
     * @author bushujie
     * @created 2017年12月8日 下午3:51:25
     *
     * @param empCode
     * @return
     */
    public List<String> userProjectList(String empCode){
    	return projectDao.userProjectList(empCode);
    }
}
