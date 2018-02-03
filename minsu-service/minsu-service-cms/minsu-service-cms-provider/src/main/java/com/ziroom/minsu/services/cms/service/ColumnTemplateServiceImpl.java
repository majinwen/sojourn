/**
 * @FileName: ColumnTemplateServiceImpl.java
 * @Package com.ziroom.minsu.services.cms.service
 * 
 * @author bushujie
 * @created 2016年11月7日 下午7:22:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;
import com.ziroom.minsu.services.cms.dao.ColumnTemplateDao;
import com.ziroom.minsu.services.cms.dto.ColumnTemplateRequest;
import com.ziroom.minsu.services.cms.entity.ColumnTemplateVo;

/**
 * <p>专栏模板业务操作</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Service("cms.columnTemplateServiceImpl")
public class ColumnTemplateServiceImpl {
	
	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ColumnTemplateServiceImpl.class);
    
    @Resource(name="cms.columnTemplateDao")
    private ColumnTemplateDao columnTemplateDao;
    
    /**
     * 
     * 分页查询专栏模板列表
     *
     * @author bushujie
     * @created 2016年11月7日 下午7:25:17
     *
     * @param templateRequest
     * @return
     */
    public PagingResult<ColumnTemplateVo> findColumnTemplateList(ColumnTemplateRequest templateRequest){
    	return columnTemplateDao.findColumnTemplateList(templateRequest);
    }
    
    /**
     * 
     * 插入专栏模板
     *
     * @author bushujie
     * @created 2016年11月7日 下午8:02:41
     *
     * @param columnTemplateEntity
     */
    public void insertColumnTemplate(ColumnTemplateEntity columnTemplateEntity){
    	columnTemplateDao.insertColumnTemplate(columnTemplateEntity);
    }
    
    /**
     * 
     * fid查询专栏模板
     *
     * @author bushujie
     * @created 2016年11月8日 下午6:22:58
     *
     * @param tempFid
     * @return
     */
    public ColumnTemplateEntity getColumnTemplateEntityByFid(String tempFid){
    	return columnTemplateDao.getColumnTemplateEntityByFid(tempFid);
    }
    
    /**
     * 
     * 更新专栏模板
     *
     * @author bushujie
     * @created 2016年11月8日 下午9:05:24
     *
     * @param columnTemplateEntity
     * @return
     */
    public int updateColumnTemplate(ColumnTemplateEntity columnTemplateEntity){
    	return columnTemplateDao.updateColumTemplate(columnTemplateEntity);
    }
    
    /**
     * 
     * 查询所有注册模板
     *
     * @author bushujie
     * @created 2016年11月9日 下午7:58:34
     *
     * @return
     */
    public List<ColumnTemplateEntity> findAllRegTemplate(){
    	return columnTemplateDao.findAllRegTemplate();
    }
}
