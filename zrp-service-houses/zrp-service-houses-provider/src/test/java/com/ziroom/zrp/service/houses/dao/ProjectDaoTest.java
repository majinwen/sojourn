package com.ziroom.zrp.service.houses.dao;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>单元测试</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月06日 10:18
 * @since 1.0
 */
public class ProjectDaoTest extends BaseTest {

    @Resource(name = "houses.projectDao")
    private ProjectDao projectDao;

    @Test
    public void testupdateProjectByFid(){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setFid("ff80808148e9eea40148ed6f764f0003");
        projectEntity.setFname("测试公寓123");
        projectDao.updateProjectByFid(projectEntity);

    }

    @Test
    public void testFindProjectByCode(){
        ProjectEntity projectByCode = projectDao.findProjectByCode("0100");
        System.err.println(JSONObject.toJSONString(projectByCode));
    }
    @Test
    public void testFindProjectDetail(){
        ProjectEntity projectByCode = projectDao.findProjectById("2c908d174f6e5d0b014f8c0185c106a8");
        System.err.println(JSONObject.toJSONString(projectByCode));
    }

    @Test
    public void testfindProjectListForPage(){
        AddHouseGroupDto addHouseGroupDto = new AddHouseGroupDto();
        addHouseGroupDto.setCityid("310000");
        PagingResult<ProjectEntity> projectEntityPagingResult = projectDao.findProjectListForPage(addHouseGroupDto);
        System.err.print(projectEntityPagingResult.toString());
    }


    @Test
    public void  testQueryAllPro(){

        AddHouseGroupDto addHouseGroupDto = new AddHouseGroupDto();
        List<ProjectEntity> list = projectDao.queryAllPro(addHouseGroupDto);
        System.err.print(list);
    }

}
