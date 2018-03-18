package com.zra.cms.logic;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.cms.entity.CmsHousetype;
import com.zra.cms.entity.CmsProject;
import com.zra.cms.entity.CmsProjectLabel;
import com.zra.cms.entity.CmsProjectLabelImg;
import com.zra.cms.entity.CmsProjectTraffic;
import com.zra.cms.entity.CmsProjectZspaceImg;
import com.zra.cms.entity.dto.CmsAppProjectDto;
import com.zra.cms.factory.CmsAppProjectDtoFactory;
import com.zra.cms.service.CmsHousetypeLabelService;
import com.zra.cms.service.CmsHousetypeService;
import com.zra.cms.service.CmsProjectLabelImgService;
import com.zra.cms.service.CmsProjectLabelService;
import com.zra.cms.service.CmsProjectService;
import com.zra.cms.service.CmsProjectTrafficService;
import com.zra.cms.service.CmsProjectZspaceImgService;
import com.zra.common.constant.CacheKeyCons;
import com.zra.common.dto.house.ProjectDto;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.RedisUtil;
import com.zra.common.utils.ZraApiConst;
import com.zra.house.entity.dto.HouseTypeLabDto;
import com.zra.house.logic.ProjectLogic;

/**
 * @author cuiyh9
 *
 */
@Component
public class CmsAppLogic {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CmsAppLogic.class);

    @Autowired
    private CmsProjectService cmsProjectService;
    
    @Autowired
    private CmsProjectLabelService cmsProjectLabelService ;
    
    @Autowired
    private CmsProjectLabelImgService cmsProjectLabelImgService;
    
    @Autowired
    private CmsProjectTrafficService cmsProjectTrafficService;
    
    @Autowired
    private CmsProjectZspaceImgService cmsProjectZspaceImgService;
    
    @Autowired
    private ProjectLogic projectLogic;
    
    @Autowired
    private CmsHousetypeService cmsHousetypeService;
    
    @Autowired
    private CmsHousetypeLabelService cmsHousetypeLabelService;
    
    
    /**
     * 根据户型id查询cms户型配置信息
     * @author tianxf9
     * @param housetypeId
     * @return
     */
    public CmsHousetype getCmsHousetypeByHousetypeId(String housetypeId) {
    	String key =  CacheKeyCons.CMS.CMS_APP_HOUSETYPE_V1 + housetypeId;
    	Object obj = RedisUtil.getObject(key);
    	CmsHousetype cmsHousetype = null;
    	if(obj==null) {
    		
    		cmsHousetype  = this.cmsHousetypeService.getCmsHousetypeEntity(housetypeId);
    	}else {
    		cmsHousetype = (CmsHousetype)obj;
    		return cmsHousetype;
    	}
    	
        if (cmsHousetype != null) {
            RedisUtil.setObjectExpire(key, cmsHousetype);
        }
    	
        return cmsHousetype;
    }
    
    /**
     * 根据户型id和户型标签类型获取户型标签
     * @author tianxf9
     * @param housetypeId
     * @param labType
     * @return
     */
    public List<HouseTypeLabDto> getCmsHousetypeLab(String housetypeId,Integer labType) {
    	
     	String key =  CacheKeyCons.CMS.CMS_APP_HOUSETYPE_LAB_V1 + housetypeId+labType;
    	Object obj = RedisUtil.getObject(key);
    	List<HouseTypeLabDto> cmsHousetypeLabs = null;
    	if(obj!=null)  {
    		cmsHousetypeLabs = (List<HouseTypeLabDto>)obj;
    	}
    	
    	if(CollectionUtils.isEmpty(cmsHousetypeLabs)) {
    		String picPrefixUrl = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
    		cmsHousetypeLabs  = this.cmsHousetypeLabelService.getLabs(housetypeId,labType);
    		if(CollectionUtils.isNotEmpty(cmsHousetypeLabs)) {
    			for(HouseTypeLabDto dto:cmsHousetypeLabs) {
    				if(StringUtils.isNotBlank(dto.getImgUrl())) {
    					dto.setImgUrl(picPrefixUrl+dto.getImgUrl().trim());
    				}
    			}
    		}
    	}else {
    		return cmsHousetypeLabs;
    	}
    	
        if (CollectionUtils.isEmpty(cmsHousetypeLabs)) {
            RedisUtil.setObjectExpire(key, cmsHousetypeLabs);
        }
    	
        return cmsHousetypeLabs;
    	
    }
    
    /**
     * 删除户型配置信息缓存.
     * @author tianxf9
     * @param housetypeId
     * @return
     */
    public boolean deleteCmsHousetypeCache(String housetypeId) {
        String key =  CacheKeyCons.CMS.CMS_APP_HOUSETYPE_V1 + housetypeId;
        boolean flag = RedisUtil.deleteData(key);
        return flag;
    }
    
 
   /**
    * 删除户型详情标签缓存
    * @author tianxf9
    * @param housetypeId
    * @param labType
    * @return
    */
    public boolean deleteCmsHousetypeLabCache(String housetypeId,Integer labType) {
        String key =  CacheKeyCons.CMS.CMS_APP_HOUSETYPE_LAB_V1 + housetypeId+labType;
        boolean flag = RedisUtil.deleteData(key);
        return flag;
    }
    
    
    
    /**
     * 根据projectId查找项目配置信息实体(包括相关联实体，存在缓存).
     * @param pid 项目id.
     * @return CmsAppProjectDto.
     */
    public CmsAppProjectDto queryCmsAppProjectDtoByPid(String projectId) {
        String key =  CacheKeyCons.CMS.CMS_APP_PROJECT_V1 + projectId;
        //1、从缓存中取数据,存在直接返回
        Object obj = RedisUtil.getObject(key);
        CmsAppProjectDto cmsAppProjectDto = null;
        if (obj == null) {
            //2、取数据组装
            cmsAppProjectDto = buildCmsAppProjectDto(projectId);
        } else {
            cmsAppProjectDto = (CmsAppProjectDto) obj;
            return cmsAppProjectDto; 
        }
        
        //3、放入缓存
        /*if (cmsAppProjectDto != null) {
            RedisUtil.setObjectExpire(key, cmsAppProjectDto);
        }*/
        return cmsAppProjectDto;
    }
    
    /**
     * 构建项目配置信息实体.
     * @param projectId.
     * @return
     */
    private CmsAppProjectDto buildCmsAppProjectDto(String projectId) {
        
        CmsAppProjectDto cmsAppProjectDto = null;
        //理论上 CmsProject不许为空,但上线的一瞬间可能会由于发布问题导致
        CmsProject cmsProject = cmsProjectService.getByProjectId(projectId);
        //added by wangxm113
        String replace = null;
        if (cmsProject.getZoServiceDesc() != null) {
            String lineSeparator = System.getProperty("line.separator", "\n");
            LOGGER.info("[separator]:" + lineSeparator);
            LOGGER.info("[separator]替换前:" + cmsProject.getZoServiceDesc());
            replace = cmsProject.getZoServiceDesc().replace(lineSeparator, "\n");
            while (replace.endsWith("\n")) {
                replace = replace.substring(0, replace.length() - 1);
            }
            replace = replace.replace("\r", "");
            LOGGER.info("[separator]替换后:" + replace);
        }
        cmsProject.setZoServiceDesc(replace);
        cmsProject = wrapCmsProject(cmsProject, projectId);
        
        List<CmsProjectLabel> cmsProjectLabelList = cmsProjectLabelService.findByProjectId(projectId);
        
        String[] projectLabelFids = null;
        if (cmsProjectLabelList != null && cmsProjectLabelList.size() > 0) {
            int projectLabelSize = cmsProjectLabelList.size();
            projectLabelFids = new String[projectLabelSize];
            for (int i = 0; i < projectLabelSize ; i++) {
                projectLabelFids[i] = cmsProjectLabelList.get(i).getFid();
            }
        }
        
        List<CmsProjectLabelImg> cmsProjectLabelImgList = cmsProjectLabelImgService.findByProjectLabelFids(projectLabelFids == null ? new String[]{""} : projectLabelFids);
        List<CmsProjectTraffic> cmsProjectTrafficList = cmsProjectTrafficService.findByProjectId(projectId);
        List<CmsProjectZspaceImg>  cmsProjectZspaceImgList = cmsProjectZspaceImgService.findByProjectId(projectId);
        
        cmsAppProjectDto = CmsAppProjectDtoFactory.INSTANCE.buildCmsAppProjectDto(cmsProject, cmsProjectLabelList, cmsProjectLabelImgList, cmsProjectTrafficList, cmsProjectZspaceImgList);
        
        return cmsAppProjectDto;
    }
    
    /**
     * 将Project(业务强关联)实体中CMS需要使用的属性拷贝到CmsProject中.
     * CmsProject目前还在建设，部分项目CMS属性在Project类中
     * @param cmsProject
     * @param projectId
     * @return
     */
    private CmsProject wrapCmsProject(CmsProject cmsProject, String projectId) {
        
        
        ProjectDto projectDto = projectLogic.getProjectDtoById(projectId);
        if (projectDto == null) {
            return cmsProject;
        }
        
        if (cmsProject == null) {
            cmsProject = new CmsProject();
        }
        cmsProject.setProjectTitle(projectDto.getTitle());
        cmsProject.setProjectDescribe(projectDto.getProjectDescribe());
        cmsProject.setShowImg(projectDto.getProjectShowImage());
        cmsProject.setPanoramicUrl(projectDto.getPanoramicUrl());
        cmsProject.setPeripheralUrl(projectDto.getPeripheralUrl());
        cmsProject.setShareUrl(projectDto.getShareUrl());
        cmsProject.setHeadImg(projectDto.getHeadFigureUrl());
        
        return cmsProject;
    }
    
    
    /**
     * 删除项目配置信息缓存.
     * @param projectId
     * @return
     */
    public boolean deleteCmsAppCache(String projectId) {
        String key =  CacheKeyCons.CMS.CMS_APP_PROJECT_V1 + projectId;
        boolean flag = RedisUtil.deleteData(key);
        return flag;
    }
}

