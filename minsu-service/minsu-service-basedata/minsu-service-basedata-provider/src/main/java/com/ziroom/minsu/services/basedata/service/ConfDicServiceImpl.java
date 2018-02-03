package com.ziroom.minsu.services.basedata.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.basedata.dao.ConfDicDao;
import com.ziroom.minsu.services.basedata.dao.DicItemDao;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>获取字典和字典值信息</p>
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
@Service("basedata.confDicServiceImpl")
public class ConfDicServiceImpl {


    @Resource(name = "basedata.confDicDao")
    private ConfDicDao confDicDao;


    @Resource(name = "basedata.dicItemDao")
    private DicItemDao dicItemDao;
    
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ConfDicServiceImpl.class);



    /**
     * 插入字典值信息
     * @param dicItemEntity
     * @return
     */
    public void insertDicItem(DicItemEntity dicItemEntity){
        dicItemDao.insertDicItem(dicItemEntity);
    }

    /**
     * 插入信息
     * @param dicEntity
     */
    public void insertConfDic(ConfDicEntity dicEntity){
    	//设置配置项目级别
    	if(Check.NuNStr(dicEntity.getPfid())){
    		dicEntity.setDicLevel(0);
        }else {
            ConfDicEntity parentDic = this.getConfDicByFid(dicEntity.getPfid());
            if(parentDic == null){
            	LogUtil.info(logger,"the pfid is not exit on insert the confDicEntity");
                throw  new BusinessException("the pfid is not exit on insert the confDicEntity");
            }else{
            	dicEntity.setDicLevel(parentDic.getDicLevel() + 1);
            }
        }
    	if(dicEntity.getDicLevel()>1){
    		ConfDicEntity pDic=confDicDao.getConfDicByFid(dicEntity.getPfid());
    		dicEntity.setDicCode(pDic.getDicCode()+"00"+(confDicDao.countChildNumBuCode(pDic.getDicCode())+1));
    	}
        confDicDao.insertConfDic(dicEntity);
    }

    /**
     * 更新信息
     * @param dicEntity
     */
    public void updateConfDicByFid(ConfDicEntity dicEntity){
        confDicDao.updateConfDicByFid(dicEntity);
    }

    /**
     * 更新信息
     * @param dicItemEntity
     */
    public void updateDicItemByFid(DicItemEntity dicItemEntity){
        dicItemDao.updateDicItemByFid(dicItemEntity);
    }

    /**
     * 获取当前模板的cod的值信息
     * @param code
     * @param templateFid
     * @return
     */
    public List<DicItemEntity>  getDicItemListByCodeAndTemplate(String code,String templateFid){
        return  dicItemDao.getDicItemListByCodeAndTemplate(code,templateFid);
    }


    /**
     * 获取字典配置信息
     * @param fid
     * @return
     */
    public ConfDicEntity  getConfDicByFid(String fid){
        ConfDicEntity dicEntity =  confDicDao.getConfDicByFid(fid);

        if(!Check.NuNObj(dicEntity)){
            //校验当前的code是否在当前的树中被引用
            dicEntity.setIsEdit(this.checkCode(dicEntity.getDicCode(),0)?1:0);
        }
        return dicEntity;
    }

    /**
     * 校验当前code是否可以修改
     * @param dicCode
     * @return
     */
    public boolean checkCode(String dicCode,Integer level){

        boolean rst = true;
        if(Check.NuNObj(dicCode)&&level==0){
            return false;
        }
        //校验当前的code是否在当前的树中被引用
        Long dicCount = confDicDao.countChildNumBuCode(dicCode);
        if(!Check.NuNObj(dicCount) && dicCount > 0){
            rst = false;
        }else  {
            //校验当前的值是否被引用
            Long itemCount = dicItemDao.countCodeNum(dicCode);
            if(!Check.NuNObj(itemCount) && itemCount > 0) {
                rst = false;
            }
        }
        return rst;
    }
    
    /**
     * 校验当前code是否可以修改
     * @param dicCode
     * @return
     */
    public long countItemCodeNum(String templateId,String dicCode){
        //校验当前的code是否在当前的树中被引用
        Long dicCount = dicItemDao.countCodeItemNum(templateId, dicCode);
        
        return dicCount;
    }
    

    /**
     * 获取当前pfid下的信息
     * @param pfid
     * @return
     */
    public List<ConfDicEntity>  getConfDicByPfid(String pfid){
        return confDicDao.getConfDicByPfid(pfid);
    }

    /**
     *
     * @return
     */
    public List<TreeNodeVo> getDicTree(String line) {
        return confDicDao.getDicTree(line);
    }
    
    /**
     * 
     * 枚举值列表查询
     *
     * @author bushujie
     * @created 2016年3月22日 下午4:07:40
     *
     * @param templateFid
     * @param dicCode
     * @return
     */
    public List<EnumVo> selectEnumList(String cityCode,String dicCode){
    	if(Check.NuNStr(cityCode)){
    		return dicItemDao.selectDefaultEnumList(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, dicCode);
    	}
    	return dicItemDao.selectEnumList(cityCode, dicCode);
    }
    
    /**
     * 枚举值列表查询（有效的）
     * @author lishaochuan
     * @create 2016年5月31日下午8:54:53
     * @param cityCode
     * @param dicCode
     * @return
     */
    public List<EnumVo> selectEffectiveEnumList(String cityCode,String dicCode){
    	if(Check.NuNStr(cityCode)){
    		return dicItemDao.selectEffectiveDefaultEnumList(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, dicCode);
    	}
    	return dicItemDao.selectEffectiveEnumList(cityCode, dicCode);
    }
    
    
    /**
     * 
     * 父项code获取子项列表
     *
     * @author bushujie
     * @created 2016年3月26日 下午5:30:42
     *
     * @param dicCode
     * @return
     */
    public List<EnumVo> getEnumDicList(String dicCode,String cityCode){
    	List<EnumVo> subDicEnumVos=confDicDao.getEnumDicList(dicCode);
    	for(EnumVo vo:subDicEnumVos){
    		if(Check.NuNStr(cityCode)){
    			vo.setSubEnumVals(dicItemDao.selectDefaultEnumList(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, vo.getKey()));
    		}else {
    			vo.setSubEnumVals(dicItemDao.selectEnumList(cityCode, vo.getKey()));
			}
    	}
    	return subDicEnumVos;
    }

    /**
     * 通用配置列表获取
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年09月12日 19:32:12
     */
    public List<EnumVo> listEnumDicForCommon(String templateFid, String dicCode) {
        return dicItemDao.selectEffectiveDefaultEnumList(templateFid, dicCode);
    }

    /**
     * 通用获取配置值
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年09月12日 19:35:02
     */
    public List<String> getTextValueForCommon(String templateFid, String dicCode) {
        return dicItemDao.getDefaultTextValue(templateFid, dicCode);
    }




    
    /**
     * 
     * 属性项唯一值获取
     *
     * @author bushujie
     * @created 2016年3月22日 下午4:07:40
     *
     * @param cityCode
     * @param dicCode
     * @return
     */
    public List<String> getTextValue(String cityCode,String dicCode){
    	if(Check.NuNStr(cityCode)){
    		return dicItemDao.getDefaultTextValue(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, dicCode);
    	}
    	return dicItemDao.getTextValue(cityCode, dicCode);
    }




    /**
     * 属性项唯一值获取 前缀匹配
     * @author afi
     * @created 2016年4月21日
     * @param cityCode
     * @param dicCodePre
     * @return
     */
    public List<MinsuEleEntity> getListByLike(String cityCode,String dicCodePre){
        if(Check.NuNStr(cityCode)){
            return dicItemDao.getDefaultListByLike(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, dicCodePre);
        }
        return dicItemDao.getListByLike(cityCode, dicCodePre);
    }

    /**
     * 属性项唯一值获取
     * @author afi
     * @created 2016年4月21日
     * @param cityCode
     * @param dicCode
     * @return
     */
    public MinsuEleEntity getTextValueByCode(String cityCode,String dicCode){
        MinsuEleEntity conf = new MinsuEleEntity();
        String value = null;
        List<String> list = null;
        if(Check.NuNStr(cityCode)){
            list= dicItemDao.getDefaultTextValue(BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE, dicCode);
        }else {
            list = dicItemDao.getTextValue(cityCode, dicCode);
        }

        if(!Check.NuNCollection(list) && list.size() == 1 ){
            value = list.get(0);
        }
        conf.setEleKey(dicCode);
        conf.setEleValue(value);
        return conf;
    }

	/**
	 * 更新配置项列表
	 *
	 * @author liujun
	 * @created 2017年1月10日
	 *
	 * @param itemList
	 * @return
	 */
	public void updateDicItemList(List<DicItemEntity> itemList) {
		for (DicItemEntity dicItemEntity : itemList) {
			dicItemDao.updateDicItemByFid(dicItemEntity);
		}
	}


}
