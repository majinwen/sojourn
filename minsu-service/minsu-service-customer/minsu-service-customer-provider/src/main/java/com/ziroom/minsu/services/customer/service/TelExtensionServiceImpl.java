package com.ziroom.minsu.services.customer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.TelExtensionEntity;
import com.ziroom.minsu.services.customer.dao.TelExtensionDao;
import com.ziroom.minsu.services.customer.dto.TelExtensionDto;
import com.ziroom.minsu.services.customer.entity.TelExtensionVo;
import com.ziroom.minsu.valenum.customer.ExtStatusEnum;

/**
 * <p>400电话的绑定情况</p>
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
@Service("customer.telExtensionServiceImpl")
public class TelExtensionServiceImpl {


    @Resource(name="customer.telExtensionDao")
    private TelExtensionDao telExtensionDao;


    /**
     * 解绑成功
     * @author afi
     * @param uid
     * @param code
     */
    public void breakSuccess(String uid,Integer code,String createUid){
        if(Check.NuNStr(uid)){
            throw new BusinessException("uid is null");
        }

        telExtensionDao.updateStatus(uid, code, ExtStatusEnum.HAS_BREAK.getCode(),createUid);
    }



    /**
     * 更新状态
     * @author afi
     * @param uid
     * @param code
     */
    public void updateStatus(String uid,Integer code,Integer status,String createUid){
        if(Check.NuNStr(uid)){
            throw new BusinessException("uid is null");
        }
        if(Check.NuNObj(status)){
            throw new BusinessException("status is null");
        }
        //更改当前状态
        telExtensionDao.updateStatus(uid, code, status);
        //更新记录TODO

    }


    /**
     * 幂等保存当前绑定信息
     * @param extensionEntity
     * @return
     */
    public TelExtensionVo saveExtensionIdempotent(TelExtensionEntity extensionEntity){
        //获取对象
        TelExtensionEntity org = this.getExtensionByUid(extensionEntity.getUid());
        if(Check.NuNObj(org)){
            //添加
            this.addStatus(extensionEntity);
        }else {
            //更新
            if(extensionEntity.getExtStatus() != org.getExtStatus().intValue()){
                this.updateStatus(extensionEntity.getUid(), extensionEntity.getErrorCode(), extensionEntity.getExtStatus(), extensionEntity.getCreateUid());
            }
        }
        TelExtensionVo extensionVo = this.getExtensionVoByUid(extensionEntity.getUid());
        return extensionVo;
    }


    /**
     * 增加状态
     * @author afi
     * @param extensionEntity
     */
    public void addStatus(TelExtensionEntity extensionEntity){
        if(Check.NuNObj(extensionEntity)){
            throw new BusinessException("extensionEntity is null");
        }
        if(Check.NuNStr(extensionEntity.getUid())){
            throw new BusinessException("uid is null");
        }
        if(Check.NuNObj(extensionEntity.getExtStatus())){
            throw new BusinessException("status is null");
        }
        telExtensionDao.insertExtension(extensionEntity);
    }

    /**
     * 调用成功
     * @author afi
     * @param uid
     * @param code
     */
    public void updateSuccess(String uid,Integer code){
        if(Check.NuNStr(uid)){
            throw new BusinessException("uid is null");
        }

        telExtensionDao.updateStatus(uid, code, ExtStatusEnum.HAS_OK.getCode());
    }

    /**
     * 调用失败
     * @author afi
     * @param uid
     * @param code
     */
    public void updateError(String uid,Integer code){
        if(Check.NuNStr(uid)){
            throw new BusinessException("uid is null");
        }
        telExtensionDao.updateStatus(uid, code, ExtStatusEnum.ERROR.getCode());
    }

    /**
     * 绑定房东的分机号
     * @param uid
     * @param createUid
     */
    public void insertExtension(String uid,String createUid){
        //校验参数
        if(Check.NuNObjs(uid,createUid)){
            throw new BusinessException("par is null");
        }
        TelExtensionEntity entity = new TelExtensionEntity();
        entity.setUid(uid);
        entity.setCreateUid(createUid);
        if(Check.NuNObj(entity)){
            throw new BusinessException("extensionEntity is null");
        }
        if(Check.NuNStr(entity.getFid())){
            entity.setFid(UUIDGenerator.hexUUID());
        }
        telExtensionDao.insertExtension(entity);
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
        return telExtensionDao.getExtensionByUid(uid);
    }


    /**
     * 查看当前用户的分机绑定情况
     * @author afi
     * @param uid
     */
    public TelExtensionVo getExtensionVoByUid(String uid){
        return telExtensionDao.getExtensionVoByUid(uid);
    }
    
    
    /**
     * 通过uid获取唯一绑定成功的电话
     * @author lishaochuan
     * @create 2016年5月12日下午10:13:36
     * @param uid
     * @return
     */
    public TelExtensionVo getHaveExtensionByUid(String uid){
        return telExtensionDao.getHaveExtensionByUid(uid);
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
        return telExtensionDao.getExtensionVOByPage(telExtensionDto);
    }

}
