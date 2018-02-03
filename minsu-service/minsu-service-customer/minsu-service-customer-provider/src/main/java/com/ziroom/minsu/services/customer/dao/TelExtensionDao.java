package com.ziroom.minsu.services.customer.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.customer.TelExtensionEntity;
import com.ziroom.minsu.services.customer.dto.TelExtensionDto;
import com.ziroom.minsu.services.customer.entity.TelExtensionVo;

/**
 * <p>房东分机号码绑定</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
@Repository("customer.telExtensionDao")
public class TelExtensionDao {

    private String SQLID="customer.telExtensionDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    public Long getMaxZiroomPhone(){
       return mybatisDaoContext.findOne(SQLID + "getMaxZiroomPhone", Long.class);
    }


    /**
     * 更新状态
     * @author afi
     * @param uid
     * @param code
     */
    public void updateStatus(String uid,Integer code,Integer extStatus,String createUid){
        if(Check.NuNStr(uid)){
            throw new BusinessException("uid is null");
        }
        if(Check.NuNObj(extStatus)){
            throw new BusinessException("extStatus is null");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("uid",uid);
        par.put("errorCode",code);
        par.put("extStatus",extStatus);
        par.put("createUid",createUid);
        mybatisDaoContext.update(SQLID + "updateStatus", par);
    }

    /**
     * 调用失败
     * @author afi
     * @param uid
     * @param code
     */
    public void updateStatus(String uid,Integer code,Integer extStatus){
        updateStatus( uid, code, extStatus,null);
    }

    /**
     * 绑定房东的分机号
     * @author afi
     * @param extensionEntity
     */
    public void insertExtension(TelExtensionEntity extensionEntity){
        if(Check.NuNObj(extensionEntity)){
            throw new BusinessException("extensionEntity is null");
        }
        if(Check.NuNStr(extensionEntity.getFid())){
            extensionEntity.setFid(UUIDGenerator.hexUUID());
        }
        mybatisDaoContext.save(SQLID+"insertExtension", extensionEntity);
    }

    /**
     * 查看当前用户的分机绑定情况
     * @author afi
     * @param uid
     */
    public TelExtensionEntity getExtensionByUid(String uid){
        if(Check.NuNStr(uid)) {
            throw new BusinessException("uid is null");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("uid", uid);
        return mybatisDaoContext.findOne(SQLID + "getExtensionByUid",TelExtensionEntity.class, par);
    }



    /**
     * 查看当前用户的分机绑定情况
     * @author afi
     * @param uid
     */
    public TelExtensionVo getExtensionVoByUid(String uid){
        if(Check.NuNStr(uid)) {
            throw new BusinessException("uid is null");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("uid", uid);
        return mybatisDaoContext.findOne(SQLID + "getExtensionVoByUid", TelExtensionVo.class, par);
    }
    
    
    /**
     * 通过uid获取唯一绑定成功的电话
     * @author lishaochuan
     * @create 2016年5月12日下午10:12:35
     * @param uid
     * @return
     */
    public TelExtensionVo getHaveExtensionByUid(String uid){
        if(Check.NuNStr(uid)) {
            throw new BusinessException("uid is null");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("uid", uid);
        return mybatisDaoContext.findOne(SQLID + "getHaveExtensionByUid", TelExtensionVo.class, par);
    }


    /**
     * 分页查询绑定试图
     * @param telExtensionDto
     * @return
     */
    public PagingResult<TelExtensionVo> getExtensionVOByPage(TelExtensionDto telExtensionDto){
        if(Check.NuNObj(telExtensionDto)){
            throw new BusinessException("telExtensionDto is null");
        }
        PageBounds pageBounds=new PageBounds();
        pageBounds.setLimit(telExtensionDto.getLimit());
        pageBounds.setPage(telExtensionDto.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getExtensionVOByPage", TelExtensionVo.class, telExtensionDto, pageBounds);
    }


}
