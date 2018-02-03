package com.ziroom.minsu.services.basedata.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.CityTemplateEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.entity.conf.TemplateEntity;
import com.ziroom.minsu.services.basedata.dao.CityTemplateDao;
import com.ziroom.minsu.services.basedata.dao.ConfDicDao;
import com.ziroom.minsu.services.basedata.dao.DicItemDao;
import com.ziroom.minsu.services.basedata.dao.TemplateDao;
import com.ziroom.minsu.services.basedata.entity.CityTemplateVo;
import com.ziroom.minsu.services.basedata.entity.TemplateEntityVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.dto.PageRequest;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>城市模板-test</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/21.
 * @version 1.0
 * @since 1.0
 */
@Service("basedata.cityTemplateServiceImpl")
public class CityTemplateServiceImpl {


    @Resource(name = "basedata.cityTemplateDao")
    private CityTemplateDao cityTemplateDao;

    @Resource(name = "basedata.templateDao")
    private TemplateDao templateDao;

    @Resource(name = "basedata.confDicDao")
    private ConfDicDao confDicDao;

    @Resource(name = "basedata.dicItemDao")
    private DicItemDao dicItemDao;

    /**
     * 获取当前城市的关联的信息
     * @param cityCode
     * @return
     */
    public CityTemplateVo getCityTemplateByCityCode(String cityCode) {
        CityTemplateVo vo = cityTemplateDao.getCityTemplateByCityCode(cityCode);
        if(Check.NuNObj(vo)){
            vo = new CityTemplateVo();
        }
        return vo;
    }

    /**
     * 保存城市关系
     * @param cityTemplateEntity
     */
    public void insertCityTemplate(CityTemplateEntity cityTemplateEntity){
        cityTemplateDao.deleteByCityCode(cityTemplateEntity.getCityCode());
        cityTemplateDao.insertCityTemplate(cityTemplateEntity);
    }

    /**
     * 清空当前城市下的其他关联关系
     * @param cityCode
     */
    public void deleteByCityCode(String cityCode){
        cityTemplateDao.deleteByCityCode(cityCode);
    }

    /**
     * 获取模板列表
     * @return
     */
    public List<TemplateEntityVo> getTemplateList(){
        return templateDao.getTemplateList();
    }

    public PagingResult<TemplateEntityVo> getTemplateListByPage(PageRequest pageRequest){
        return templateDao.getTemplateListByPage(pageRequest);
    }

    /**
     *
     * @return
     */
    public List<TreeNodeVo> getDicTree(String line) {
        return confDicDao.getDicTree(line);
    }


    /**
     * 将原来的值信息赋值给新模板
     * @param templateFid
     * @param userFid
     */
    public void insertIntoDicItem(String templateFid,String userFid){
        if (Check.NuNStr(templateFid)){
            return;
        }
        List<DicItemEntity> itemEntityList = dicItemDao.getDicItemByTemplateFid(templateFid);
        if(!Check.NuNObj(itemEntityList)){
            //将信息重新插入到数据库
            for(DicItemEntity dicItemEntity: itemEntityList){
                dicItemEntity.setTemplateFid(templateFid);
                dicItemEntity.setFid(UUIDGenerator.hexUUID());
                dicItemEntity.setCreateDate(null);
                dicItemEntity.setLastModifyDate(null);
                dicItemEntity.setCreateFid(userFid);
                dicItemDao.insertDicItem(dicItemEntity);
            }
        }
    }

    /**
     * 继承原来的模板并新建模板
     * @param templateName
     * @param templateFid
     * @param userFid
     */
    public void insertTemplateAndCopyIntoDicItem(String templateName, String templateFid,String userFid){
        if (Check.NuNStr(templateFid)){
            return;
        }
        String templateFidNew = UUIDGenerator.hexUUID();
        TemplateEntity template = new TemplateEntity();
        template.setTemplateName(templateName);
        template.setCreateFid(userFid);
        template.setFid(templateFidNew);
        template.setPfid(templateFid);
        //过去当前的继承的模板，并新建一个模板
        List<DicItemEntity> itemEntityList = dicItemDao.getDicItemByTemplateFid(templateFid);
        if(!Check.NuNObj(itemEntityList)){
            //将信息重新插入到数据库
            for(DicItemEntity dicItemEntity: itemEntityList){
                dicItemEntity.setTemplateFid(templateFidNew);
                dicItemEntity.setFid(UUIDGenerator.hexUUID());
                dicItemEntity.setCreateDate(null);
                dicItemEntity.setLastModifyDate(null);
                dicItemEntity.setCreateFid(userFid);
                dicItemDao.insertDicItem(dicItemEntity);
            }
        }
        templateDao.insertTemplate(template);
    }


    /**
     * 保存模板信息
     * @param templateEntity
     */
    public void insertTemplate(TemplateEntity templateEntity){
        templateDao.insertTemplate(templateEntity);
    }

}
