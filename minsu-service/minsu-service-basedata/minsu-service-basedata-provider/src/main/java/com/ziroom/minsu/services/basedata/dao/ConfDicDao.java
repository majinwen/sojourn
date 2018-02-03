package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>配置结构表</p>
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
@Repository("basedata.confDicDao")
public class ConfDicDao {


    private String SQLID="basedata.confDicDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    public Long countChildNumBuCode(String dicCode) {
        if(Check.NuNStr(dicCode)){
            return null;
        }
        Map<String,Object> dealPar = new HashMap<>();
        dealPar.put("dicCode",dicCode);
        return mybatisDaoContext.count(SQLID + "countChildNumBuCode", dealPar);
    }


    /**
     * 获取字典结构
     *
     * @author afi
     * @created 2016年3月12日
     *
     */
    public ConfDicEntity getConfDicByFid(String fid) {
        if(Check.NuNStr(fid)){
            return null;
        }
        return mybatisDaoContext.findOne(SQLID + "getConfDicByFid", ConfDicEntity.class, fid);
    }


    /**
     * 通过父节点获取当前节点的所有的子信息
     * @param pfid
     * @return
     */
    public List<ConfDicEntity>  getConfDicByPfid(String pfid){
        if(Check.NuNStr(pfid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getConfDicByPfid", ConfDicEntity.class, pfid);

    }

    /**
     * 校验当前的dicCode是否被占用
     * @param dicCode
     * @return
     */
    public ConfDicEntity getConfDicByCode(String dicCode) {
        if(Check.NuNStr(dicCode)){
            return null;
        }
        return mybatisDaoContext.findOne(SQLID + "getConfDicByCode", ConfDicEntity.class, dicCode);
    }

    /**
     * 获取当前的树形结构
     * @return
     */
    public List<TreeNodeVo> getDicTree(String dicLine) {

        return mybatisDaoContext.findAll(SQLID + "getDicTree", TreeNodeVo.class, dicLine);
    }




    /**
     * 保存字典结构
     * @param confDicEntity
     */
    public void insertConfDic(ConfDicEntity confDicEntity){
        if(confDicEntity == null){
            return;
        }
        if(Check.NuNObj(confDicEntity.getFid())){
            confDicEntity.setFid(UUIDGenerator.hexUUID());
        }
        mybatisDaoContext.save(SQLID + "insertConfDic",confDicEntity);
    }


    /**
     * 修改字典结构信息
     * @param confDicEntity
     */
    public void updateConfDicByFid(ConfDicEntity confDicEntity){
        if(confDicEntity == null){
            return;
        }
        if(Check.NuNObj(confDicEntity.getFid())){
            throw  new BusinessException("the fid is null on update the confDicEntity");
        }
        mybatisDaoContext.save(SQLID + "updateConfDicByFid",confDicEntity);
    }
    
    /**
     * 
     * 父项code查询子项列表
     *
     * @author bushujie
     * @created 2016年3月26日 下午5:27:08
     *
     * @param dicCode
     * @return
     */
    public List<EnumVo> getEnumDicList(String dicCode){
    	return mybatisDaoContext.findAll(SQLID+"getEnumDicList", EnumVo.class, dicCode);
    }
}
