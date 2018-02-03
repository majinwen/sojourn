package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>配置值表</p>
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
@Repository("basedata.dicItemDao")
public class DicItemDao {


    private String SQLID="basedata.dicItemDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(DicItemDao.class);


    /**
     * 获取模板的值信息
     *
     * @author afi
     * @created 2016年3月21日
     *
     */
    public List<DicItemEntity> getDicItemByTemplateFid(String templateFid) {
        if(Check.NuNStr(templateFid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getDicItemByTemplateFid", DicItemEntity.class, templateFid);
    }

    /**
     * 获取字典值信息
     *
     * @author afi
     * @created 2016年3月21日
     *
     */
    public DicItemEntity getDicItemByFid(String fid) {
        if(Check.NuNStr(fid)){
            return null;
        }
        return mybatisDaoContext.findOne(SQLID + "getDicItemByFid", DicItemEntity.class, fid);
    }


    /**
     * 获取当前模板 当前code下的值信息
     * @param code
     * @param templateFid
     * @return
     */
    public List<DicItemEntity> getDicItemListByCodeAndTemplate(String code,String templateFid) {
        if(Check.NuNStr(code) || Check.NuNStr(templateFid)){
            return null;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("code",code);
        par.put("templateFid",templateFid);
        return mybatisDaoContext.findAll(SQLID + "getDicItemListByCodeAndTemplate", DicItemEntity.class, par);
    }

    /**
     * 获取字典值信息
     *
     * @author afi
     * @created 2016年3月21日
     *
     */
    public Long countCodeNum(String dicCode) {
        if(Check.NuNStr(dicCode)){
            return null;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("dicCode",dicCode);
        return mybatisDaoContext.count(SQLID + "countCodeNum", par);
    }
    
    /**
     * 获取字典值信息
     *
     * @author afi
     * @created 2016年3月24日
     *
     */
    public Long countCodeItemNum(String templateId,String dicCode) {
        if(Check.NuNStr(dicCode)){
            return null;
        }
        
        if(Check.NuNStr(templateId)){
            return null;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("dicCode",dicCode);
        par.put("templateFid",templateId);
        return mybatisDaoContext.count(SQLID + "countCodeItemNum", par);
    }

    
    /**
     * 保存字典值
     * @param dicItemEntity
     */
    public void insertDicItem(DicItemEntity dicItemEntity){
        if(dicItemEntity == null){
            return;
        }
        if(Check.NuNObj(dicItemEntity.getFid())){
            dicItemEntity.setFid(UUIDGenerator.hexUUID());
        }
        
        // 配置项为'房源分类'时需插入配置项排序字段
        if (ProductRulesEnum.ProductRulesEnum001.getValue().equals(dicItemEntity.getDicCode())) {
			synchronized (this) {
				Long itemNum = this.countItemNum(dicItemEntity.getTemplateFid(), ProductRulesEnum.ProductRulesEnum001.getValue());
				int itemIndex = Check.NuNObj(itemNum) ? 0 : itemNum.intValue();
				++itemIndex;
				dicItemEntity.setItemIndex(itemIndex);
				mybatisDaoContext.save(SQLID + "insertDicItem", dicItemEntity);
			}
		} else {
			mybatisDaoContext.save(SQLID + "insertDicItem", dicItemEntity);
		}
    }



    /**
	 * 根据templateId及dicCode查询未删除配置项目总数
	 *
	 * @author liujun
	 * @created 2017年1月10日
	 *
	 * @param templateId
	 * @param dicCode
	 * @return
	 */
	public Long countItemNum(String templateId, String dicCode) {
		if (Check.NuNStr(dicCode)) {
			return null;
		}

		if (Check.NuNStr(templateId)) {
			return null;
		}
		Map<String, Object> par = new HashMap<>();
		par.put("dicCode", dicCode);
		par.put("templateFid", templateId);
		return mybatisDaoContext.count(SQLID + "countItemNum", par);
	}

	/**
     * 修改字典结构信息
     * @param dicItemEntity
     */
    public void updateDicItemByFid(DicItemEntity dicItemEntity){
        if(dicItemEntity == null){
            return;
        }
        if(Check.NuNObj(dicItemEntity.getFid())){
        	LogUtil.info(logger, "the fid is null on update the dicItemEntity");
            throw  new BusinessException("the fid is null on update the dicItemEntity");
        }
        mybatisDaoContext.save(SQLID + "updateDicItemByFid",dicItemEntity);
    }
    
    /**
     * 
     * 配置枚举列表
     *
     * @author bushujie
     * @created 2016年3月22日 下午3:36:45
     *
     * @return
     */
    public  List<EnumVo> selectEnumList(String cityCode,String dicCode){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("dicCode", dicCode);
    	paramMap.put("cityCode", cityCode);
    	return mybatisDaoContext.findAll(SQLID+"getSelectEnumList", EnumVo.class, paramMap);
    }
    
    /**
     * 配置枚举列表（有效的）
     * @author lishaochuan
     * @create 2016年5月31日下午9:02:25
     * @param cityCode
     * @param dicCode
     * @return
     */
	public List<EnumVo> selectEffectiveEnumList(String cityCode, String dicCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dicCode", dicCode);
		paramMap.put("cityCode", cityCode);
		paramMap.put("itemStatus", YesOrNoEnum.YES.getCode());
		return mybatisDaoContext.findAll(SQLID + "getSelectEnumList", EnumVo.class, paramMap);
	}
    
    /**
     * 
     * 获得配置项唯一值
     *
     * @author bushujie
     * @created 2016年3月26日 下午8:38:28
     *
     * @param cityCode
     * @param dicCode
     * @return
     */
    public List<String> getTextValue(String cityCode,String dicCode){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("dicCode", dicCode);
    	paramMap.put("cityCode", cityCode);
    	return mybatisDaoContext.findAll(SQLID+"getTextValue", String.class, paramMap);
    }


    /**
     * 获得配置项列表
     * @author afi
     * @created 2016年4月21日
     *
     * @param cityCode
     * @param dicCodePre
     * @return
     */
    public List<MinsuEleEntity> getListByLike(String cityCode,String dicCodePre){
        Map<String, Object> paramMap=new HashMap<String, Object>();
        paramMap.put("dicCodeLike", dicCodePre);
        paramMap.put("cityCode", cityCode);
       
        return mybatisDaoContext.findAll(SQLID + "getListByLike", MinsuEleEntity.class, paramMap);
    }


    /**
     * 
     * 默认配置枚举列表
     *
     * @author bushujie
     * @created 2016年3月22日 下午3:36:45
     *
     * @return
     */
    public List<EnumVo> selectDefaultEnumList(String templateFid, String dicCode) {
        Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("dicCode", dicCode);
    	paramMap.put("templateFid", templateFid);
    	return mybatisDaoContext.findAll(SQLID + "getDefaultSelectEnumList", EnumVo.class, paramMap);
    }


    /**
     * 默认配置枚举列表（有效的）
     * @author lishaochuan
     * @create 2016年5月31日下午9:00:04
     * @param templateFid
     * @param dicCode
     * @return
     */
	public List<EnumVo> selectEffectiveDefaultEnumList(String templateFid, String dicCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dicCode", dicCode);
		paramMap.put("templateFid", templateFid);
		paramMap.put("itemStatus", YesOrNoEnum.YES.getCode());
		return mybatisDaoContext.findAll(SQLID + "getDefaultSelectEnumList", EnumVo.class, paramMap);
	}
    
    /**
     * 
     * 获得默认配置项唯一值
     *
     * @author bushujie
     * @created 2016年3月26日 下午8:38:28
     *
     * @param templateFid
     * @param dicCode
     * @return
     */
    public List<String> getDefaultTextValue(String templateFid,String dicCode){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("dicCode", dicCode);
    	paramMap.put("templateFid", templateFid);
    	return mybatisDaoContext.findAll(SQLID+"getDefaultTextValue", String.class, paramMap);
    }



    /**
     * 获得默认配置项列表
     * @author afi
     * @created 2016年4月21日
     *
     * @param templateFid
     * @param dicCodePre
     * @return
     */
    public List<MinsuEleEntity> getDefaultListByLike(String templateFid,String dicCodePre){
        Map<String, Object> paramMap=new HashMap<String, Object>();
        paramMap.put("dicCodeLike",dicCodePre);
        paramMap.put("templateFid", templateFid);
        return mybatisDaoContext.findAll(SQLID + "getDefaultListByLike", MinsuEleEntity.class, paramMap);
    }


}