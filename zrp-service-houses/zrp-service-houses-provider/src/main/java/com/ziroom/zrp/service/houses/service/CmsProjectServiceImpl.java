package com.ziroom.zrp.service.houses.service;

import com.ziroom.zrp.houses.entity.CmsProjectEntity;
import com.ziroom.zrp.houses.entity.CmsProjectLabelEntity;
import com.ziroom.zrp.service.houses.dao.CmsProjectDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月16日 15:07
 * @since 1.0
 */
@Service("houses.cmsProjectServiceImpl")
public class CmsProjectServiceImpl {
    @Resource(name = "houses.cmsProjectDao")
    private CmsProjectDao cmsProjectDao;

    /**
     * 查询项目信息
     * @param projectId 项目标识
     * @return
     * @author cuigh6
     * @Date 2018年3月5日
     */
    public CmsProjectEntity getCmsProjectLabels(String projectId) {
        CmsProjectEntity cmsProject = cmsProjectDao.getCmsProject(projectId);
        return cmsProject;
    }

}
