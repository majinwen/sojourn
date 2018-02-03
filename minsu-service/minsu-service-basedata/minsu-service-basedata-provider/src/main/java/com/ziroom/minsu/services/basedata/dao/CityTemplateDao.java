package com.ziroom.minsu.services.basedata.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityTemplateEntity;
import com.ziroom.minsu.services.basedata.entity.CityTemplateVo;

/**
 * <p>城市模板关系</p>
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
@Repository("basedata.cityTemplateDao")
public class CityTemplateDao {


    private String SQLID="basedata.cityTemplateDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CityTemplateDao.class);




    /**
     * 获取当前城市的关联的信息
     * @param cityCode
     * @return
     */
    public CityTemplateVo getCityTemplateByCityCode(String cityCode) {

        return mybatisDaoContext.findOne(SQLID + "getCityTemplateByCityCode", CityTemplateVo.class, cityCode);
    }

    /**
     * 保存城市关系
     * @param cityTemplateEntity
     */
    public void insertCityTemplate(CityTemplateEntity cityTemplateEntity){
        if(cityTemplateEntity == null){
            return;
        }

        if(Check.NuNObj(cityTemplateEntity.getCityCode()) || Check.NuNStr(cityTemplateEntity.getTemplateFid())){
        	LogUtil.info(logger,"the cityCode  or templateFid is null on insert the cityTemplateEntity ");
            throw  new BusinessException("the cityCode  or templateFid is null on insert the cityTemplateEntity");
        }
        mybatisDaoContext.save(SQLID + "insertCityTemplate", cityTemplateEntity);
    }

    /**
     * 清空当前城市下的其他关联关系
     * @param cityCode
     */
    public void deleteByCityCode(String cityCode){
        if(cityCode == null){
            return;
        }
        Map<String,Object> delPar = new HashMap<>();
        delPar.put("cityCode",cityCode);
        mybatisDaoContext.delete(SQLID + "deleteByCityCode", delPar);
    }

}
