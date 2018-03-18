package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.service.trading.dto.ContractPageDto;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.entity.DeliveryContractNotifyVo;
import com.ziroom.zrp.trading.entity.RentDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>合同明细表</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月22日 15:58
 * @since 1.0
 */
@Repository("trading.rentDetailDao")
public class RentDetailDao {
    private String SQLID = "trading.rentDetailDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     *
     * @author jixd
     * @created 2017年09月22日 16:04:41
     * @param
     * @return
     */
    public int save(RentDetailEntity rentDetailEntity){
        if (Check.NuNStr(rentDetailEntity.getId())){
            rentDetailEntity.setId(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert",rentDetailEntity);
    }

    /**
     * 查找 根据合同和房间信息
     * @author jixd
     * @created 2017年09月22日 17:14:35
     * @param
     * @return
     */
    public RentDetailEntity findRentDetailById(ContractRoomDto contractRoomDto){
        return mybatisDaoContext.findOneSlave(SQLID + "findRentDetailById",RentDetailEntity.class,contractRoomDto);

    }

    /**
      * @description: 根据合同id查询合同详情集合
      * @author: lusp
      * @date: 2018/1/3 下午 17:46
      * @params: contractId
      * @return: List<RentDetailEntity>
      */
    public List<RentDetailEntity> findRentDetailByContractId(String contractId){
        return mybatisDaoContext.findAll(SQLID + "findRentDetailByContractId",RentDetailEntity.class,contractId);
    }
    
    /**
     * 更新已物业交割状态
     * @author jixd
     * @created 2017年09月26日 14:00:51
     * @param
     * @return
     */
    public int updateHasDelivery(ContractRoomDto contractRoomDto){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractRoomDto.getContractId());
        paramMap.put("roomId",contractRoomDto.getRoomId());
        return mybatisDaoContext.update(SQLID + "updateHasDelivery",paramMap);
    }

    /**
     * 更新已物业交割状态为未交割
     * @author lusp
     * @created 2017年12月12日 14:00:51
     * @param
     * @return
     */
    public int updateHaveNotDelivery(ContractRoomDto contractRoomDto){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractRoomDto.getContractId());
        paramMap.put("roomId",contractRoomDto.getRoomId());
        return mybatisDaoContext.update(SQLID + "updateHaveNotDelivery",paramMap);
    }


    /**
     * 查询未交割的合同
     * @author jixd
     * @created 2017年09月28日 17:02:34
     * @param
     * @return
     */
    public PagingResult<DeliveryContractNotifyVo> listUnDeliveryContract(ContractPageDto contractPageDto){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(contractPageDto.getPage());
        pageBounds.setLimit(contractPageDto.getRows());
        return mybatisDaoContext.findForPage(SQLID + "listUnDeliveryContract",DeliveryContractNotifyVo.class,contractPageDto,pageBounds);
    }

    /**
     * 根据合同id批量查询合同明细表信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<RentDetailEntity> findRentDetailByContractIds(List<String> contractIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("contractIds", contractIds);
        return mybatisDaoContext.findAll(SQLID + "findRentDetailByContractIds", map);
    }

    /**
      * @description: 更具合同id更新方法
      * @author: lusp
      * @date: 2017/10/27 下午 14:08
      * @params: rentDetailEntity
      * @return: int
      */
    public int updateRentDetailByContractId(RentDetailEntity rentDetailEntity){
        return mybatisDaoContext.update(SQLID + "updateRentDetailByContractId",rentDetailEntity);
    }


    /**
     * 根据父id分页获取下面的子表数据
     * @author jixd
     * @created 2017年11月09日 14:15:47
     * @param
     * @return
     */
    public PagingResult<RentDetailEntity> listRentDetailBySurParentRentId(ContractPageDto contractPageDto){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(contractPageDto.getPage());
        pageBounds.setLimit(contractPageDto.getRows());
        return mybatisDaoContext.findForPage(SQLID + "listRentDetailBySurParentRentId",RentDetailEntity.class,contractPageDto,pageBounds);
    }

    /**
     * 查询列表
     * @author jixd
     * @created 2017年11月13日 11:20:11
     * @param
     * @return
     */
    public List<RentDetailEntity> listRentDetailBySurParentRentId(String surParentRentId){
        return mybatisDaoContext.findAll(SQLID + "listRentDetailBySurParentRentId",RentDetailEntity.class,surParentRentId);
    }

    /**
     * @description: 此方法为app提交合同失败回滚使用,将提交合同时存的合同子表数据还原
     * @author: lusp
     * @date: 2018/1/16 下午 16:25
     * @params: contractId
     * @return: int
     */
    public int updateRentDetailRollBackForSubmit(String contractId){
        RentDetailEntity rentDetailEntity = new RentDetailEntity();
        rentDetailEntity.setContractId(contractId);
        return mybatisDaoContext.update(SQLID + "updateRentDetailRollBackForSubmit",rentDetailEntity);
    }

    /**
     *
     * 根据合同id和房间id，更新子表uid
     *
     * @author zhangyl2
     * @created 2018年01月30日 15:14
     * @param
     * @return
     */
    public int updateUidByContractIdAndRoomId(RentDetailEntity rentDetailEntity) {
        if (Check.NuNObjs(rentDetailEntity,
                rentDetailEntity.getContractId(),
                rentDetailEntity.getRoomId(),
                rentDetailEntity.getPersonUid())) {
            throw new RuntimeException();
        }
        return mybatisDaoContext.update(SQLID + "updateUidByContractIdAndRoomId", rentDetailEntity);
    }

}
