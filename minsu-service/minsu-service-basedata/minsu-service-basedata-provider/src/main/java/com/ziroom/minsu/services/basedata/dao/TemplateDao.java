package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.TemplateEntity;
import com.ziroom.minsu.services.basedata.entity.TemplateEntityVo;
import com.ziroom.minsu.services.common.dto.PageRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>配置模板</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/19.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.templateDao")
public class TemplateDao {


    private String SQLID="basedata.templateDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(TemplateDao.class);



    /**
     * 更新模板信息
     * @param templateEntity
     * @return
     */
    public void updateTemplateByFid(TemplateEntity templateEntity) {
        if(templateEntity == null){
            return;
        }
        if(Check.NuNObj(templateEntity.getFid())){
        	LogUtil.info(logger,"the fid is null on update the templateEntity");
            throw  new BusinessException("the fid is null on update the templateEntity");
        }
        mybatisDaoContext.update(SQLID + "updateTemplateByFid", templateEntity);
    }


    /**
     * 获取模板列表
     * @return
     */
    public List<TemplateEntityVo>  getTemplateList(){
        return mybatisDaoContext.findAll(SQLID + "getTemplateList", TemplateEntityVo.class);
    }

    public PagingResult<TemplateEntityVo> getTemplateListByPage(PageRequest pageRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(pageRequest.getLimit());
        pageBounds.setPage(pageRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getTemplateList", TemplateEntityVo.class, pageRequest,pageBounds);

    }




    /**
     * 保存模板信息
     * @param templateEntity
     */
    public void insertTemplate(TemplateEntity templateEntity){
        if(templateEntity == null){
            return;
        }
        if(Check.NuNObj(templateEntity.getFid())){
            templateEntity.setFid(UUIDGenerator.hexUUID());
        }
        if(Check.NuNStr(templateEntity.getTemplateName())){
        	LogUtil.info(logger,"the name is null on insert the templateEntity");
            throw  new BusinessException("the name is null on insert the templateEntity");
        }
        mybatisDaoContext.save(SQLID + "insertTemplate", templateEntity);
    }
}
