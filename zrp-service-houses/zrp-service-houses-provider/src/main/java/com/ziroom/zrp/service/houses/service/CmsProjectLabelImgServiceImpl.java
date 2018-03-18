package com.ziroom.zrp.service.houses.service;

import com.ziroom.zrp.houses.entity.CmsProjectLabelEntity;
import com.ziroom.zrp.houses.entity.CmsProjectLabelImgEntity;
import com.ziroom.zrp.service.houses.dao.CmsProjectLabelImgDao;
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
@Service("houses.cmsProjectLabelImgServiceImpl")
public class CmsProjectLabelImgServiceImpl {
    @Resource(name = "houses.cmsProjectLabelImgDao")
    private CmsProjectLabelImgDao cmsProjectLabelImgDao;

    /**
     * 查询项目标签信息
     * @param projectLabelFid 项目标签标识
     * @return
     * @author cuigh6
     * @Date 2018年3月5日
     */
    public List<CmsProjectLabelImgEntity> getCmsProjectLabels(String projectLabelFid) {
        List<CmsProjectLabelImgEntity> cmsProjectLabelImgEntities = cmsProjectLabelImgDao.getCmsProjectLabelImgs(projectLabelFid);
        return cmsProjectLabelImgEntities;
    }

}
