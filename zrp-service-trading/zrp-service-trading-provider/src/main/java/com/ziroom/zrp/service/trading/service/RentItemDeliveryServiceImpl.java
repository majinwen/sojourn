package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.zrp.service.trading.dao.*;
import com.ziroom.zrp.service.trading.dto.ContractPageDto;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.MeterDeliverySaveDto;
import com.ziroom.zrp.service.trading.entity.DeliveryContractNotifyVo;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;
import com.ziroom.zrp.service.trading.valenum.ContractSignTypeEnum;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryFromEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.exception.ZrpServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>物业交割相关</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月21日 15:16
 * @since 1.0
 */
@Service("trading.rentItemDeliveryServiceImpl")
public class RentItemDeliveryServiceImpl {

    @Resource(name = "trading.rentItemDeliveryDao")
    private RentItemDeliveryDao rentItemDeliveryDao;

    @Resource(name = "trading.meterDetailDao")
    private MeterDetailDao meterDetailDao;

    @Resource(name="trading.rentLifeItemDetailDao")
    private RentLifeItemDetailDao rentLifeItemDetailDao;

    @Resource(name = "trading.deliveryNotifyLogDao")
    private DeliveryNotifyLogDao deliveryNotifyLogDao;

    @Resource(name = "trading.rentDetailDao")
    private RentDetailDao rentDetailDao;

    @Resource(name = "trading.rentContractDao")
    private RentContractDao rentContractDao;




    /**
     * 查询有效的物品数据  根据合同id和房间id
     * @author jixd
     * @created 2017年09月21日 15:34:52
     * @param
     * @return
     */
    public List<RentItemDeliveryEntity> listValidItemByContractIdAndRoomId(ContractRoomDto contractRoomDto){
        return rentItemDeliveryDao.listValidItemByContractIdAndRoomId(contractRoomDto);
    }

    /**
     * 查询有效的物品数据
     * @author cuiyh9
     * @created 2017年09月21日 15:34:52
     * @param
     * @return
     */
    public List<RentItemDeliveryEntity> listValidItemByContractIds(List<String> contractIdList){
        return rentItemDeliveryDao.listValidItemByContractIds(contractIdList);
    }

    /**
     * 查询水电录入数据
     * @author jixd
     * @created 2017年09月25日 18:16:33
     * @param
     * @return
     */
    public MeterDetailEntity findMeterDetailById(ContractRoomDto contractRoomDto){
        return meterDetailDao.findByContractIdAndRoomId(contractRoomDto);
    }

    /**
     * 查询水电录入数据
     * @author cuiyh9
     * @created 2017年09月25日 18:16:33
     * @param
     * @return
     */
    public List<MeterDetailEntity> findMeterDetailsByContractIds(List<String> contractIdList){
        return meterDetailDao.findByContractIds(contractIdList);
    }

    /**
     * <p>根据合同ID和房间ID查询其他费用项</p>
     * @author xiangb
     * @created 2017年9月28日
     * @param
     * @return
     */
    public List<LifeItemVo> findLifeItemByContractIdAndRoomId(ContractRoomDto contractRoomDto){
    	return rentLifeItemDetailDao.listLifeItemByContractIdAndRoomId(contractRoomDto);
    }

    /**
     * 查询生活费用
     * @author cuiyh9
     * @created 2017年11月03日 16:20:02
     * @param
     * @return
     */
    public List<LifeItemVo> listLifeItemByContractIds(List<String> contractIdList){
        return rentLifeItemDetailDao.listLifeItemByContractIds(contractIdList);
    }

    /**
     * 查询物业交割提示记录表
     * @author jixd
     * @created 2017年09月28日 15:40:58
     * @param
     * @return
     */
    public List<DeliveryNotifyLogEntity> listDeliveryNotifyLogByContractId(String contractId){
        return deliveryNotifyLogDao.findByContractId(contractId);
    }

    /**
     *
     * @author jixd
     * @created 2017年09月28日 17:38:17
     * @param
     * @return
     */
    public PagingResult<DeliveryContractNotifyVo> listUnDeliveryContract(ContractPageDto contractPageDto){
        return rentDetailDao.listUnDeliveryContract(contractPageDto);
    }

    /**
     * 查询物业交割提示日志
     * @author jixd
     * @created 2017年10月09日 11:35:22
     * @param
     * @return
     */
    public List<DeliveryNotifyLogEntity> listDeliveryNotifyLog(String contractId){
        return deliveryNotifyLogDao.findByContractId(contractId);
    }

    /**
     * 保存物业交割提示日志
     * @author jixd
     * @created 2017年10月09日 11:40:14
     * @param
     * @return
     */
    public int saveDeliveryNotifyLog(DeliveryNotifyLogEntity deliveryNotifyLogEntity){
        return deliveryNotifyLogDao.save(deliveryNotifyLogEntity);
    }

    /**
     * 保存物业交割信息
     * @author jixd
     * @created 2017年11月03日 17:01:10
     * @param
     * @return
     */
    public int saveDeliveryInfo(MeterDeliverySaveDto meterDeliverySave){
        //保存或者更新水电信息
        MeterDetailEntity meterDetail = meterDeliverySave.getMeterDetail();
        ContractRoomDto contractRoomDto = new ContractRoomDto();
        contractRoomDto.setContractId(meterDeliverySave.getContractId());
        contractRoomDto.setRoomId(meterDeliverySave.getRoomId());

        //如果数据库已存在 则直接更新
        MeterDetailEntity hasMeterDetail = meterDetailDao.findByContractIdAndRoomId(contractRoomDto);
        if (!Check.NuNObj(hasMeterDetail)){
            meterDetail.setFid(hasMeterDetail.getFid());
        }
        int count = meterDetailDao.saveOrUpdate(meterDetail);

        //先删除之前的生活费用信息
        count += rentLifeItemDetailDao.deleteLifeItem(contractRoomDto);
        count += rentLifeItemDetailDao.batchInsert( meterDeliverySave.getLifeFeeItems());
        //处理物品信息
        count += rentItemDeliveryDao.deleteItemByContractIdAndRoomId(contractRoomDto);

        if (!Check.NuNCollection(meterDeliverySave.getRentItems())){
            meterDeliverySave.getRentItems().forEach(a -> {
                a.setUpdatetime(new Date());
                a.setCreatetime(new Date());
                a.setCreaterid(meterDeliverySave.getCreateId());
            });
        }
        count += rentItemDeliveryDao.batchInsert(meterDeliverySave.getRentItems());

        //如果是企业签约录入以后就变成已交割
        if (meterDeliverySave.getFrom() == DeliveryFromEnum.ENTERPRISE.getCode()){
            count += rentDetailDao.updateHasDelivery(contractRoomDto);
        }

        return count;
    }

    /**
     * 根据多个物品ID查询相应物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public List<RentItemDeliveryEntity> listItemDeliverysByItemIds(List<String> itemIds){
        return rentItemDeliveryDao.listItemDeliverysByItemIds(itemIds);
    }
    
    /**
     * 更新物品项
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int update(RentItemDeliveryEntity rentItemDeliveryEntity){
        return rentItemDeliveryDao.update(rentItemDeliveryEntity);
    }
    /**
     * 新增项
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int insert(RentItemDeliveryEntity rentItemDeliveryEntity){
        return rentItemDeliveryDao.insert(rentItemDeliveryEntity);
    }
    /**
     * <p>根据多个参数查询合同物品项</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public RentItemDeliveryEntity selectRentItemDeliveryEntityByparam(String contractId,
    		String itemId,String surrenderId,String isNewFlag){
    	return rentItemDeliveryDao.selectRentItemDeliveryEntityByparam(contractId, itemId, surrenderId, isNewFlag);
    }

    /**
     * copy前一份合同的物业交割数据
     * @param currentContractId
     * @return
     */
    public int copyPreContractDeliveryInfo(String currentContractId){
        int count = 0;
        RentContractEntity rentContractEntity = rentContractDao.findContractBaseByContractId(currentContractId);
        String fsigntype = rentContractEntity.getFsigntype();
        if (!fsigntype.equals(ContractSignTypeEnum.RENEW.getValue())){
            throw new ZrpServiceException("当前合同不是续约合同");
        }
        if (Check.NuNStr(rentContractEntity.getPreConRentCode())){
            throw new ZrpServiceException("前合同号不存在");
        }
        RentContractEntity preContractEntity = rentContractDao.findValidContractByRentCode(rentContractEntity.getPreConRentCode());
        if (Check.NuNObj(preContractEntity)){
            throw new ZrpServiceException("前合同不存在");
        }
        String preContractId = preContractEntity.getContractId();
        String preRoomId = preContractEntity.getRoomId();
        ContractRoomDto contractReq = ContractRoomDto.builder().contractId(preContractId).roomId(preRoomId).build();
        MeterDetailEntity preMeter = meterDetailDao.findByContractIdAndRoomId(contractReq);
        if (Check.NuNObj(preMeter)){
            throw new ZrpServiceException("前合同水电信息不存在");
        }

        List<RentLifeItemDetailEntity> preLifeItems = rentLifeItemDetailDao.listLifeItemEntityByContractIdAndRoomId(contractReq);
        List<RentItemDeliveryEntity> preItems = rentItemDeliveryDao.listValidItemByContractIdAndRoomId(contractReq);

        //更新操作
        contractReq.setContractId(currentContractId);
        MeterDetailEntity currentMeter = meterDetailDao.findByContractIdAndRoomId(contractReq);
        if (Check.NuNObj(currentMeter)){
            preMeter.setFid(null);
        }else{
            preMeter.setFid(currentMeter.getFid());
        }
        preMeter.setFcontractid(currentContractId);
        count += meterDetailDao.saveOrUpdate(preMeter);

        count += rentLifeItemDetailDao.deleteLifeItem(contractReq);
        count += rentItemDeliveryDao.deleteItemByContractIdAndRoomId(contractReq);

        preLifeItems.forEach(p -> {p.setContractId(currentContractId);p.setId(null);});
        preItems.forEach(p -> {p.setContractid(currentContractId);p.setFid(null);});

        count += rentLifeItemDetailDao.batchInsert(preLifeItems);
        count += rentItemDeliveryDao.batchInsert(preItems);
        //更新当前合同已交割状态
        count += rentDetailDao.updateHasDelivery(contractReq);

        return count;
    }

    /**
     * 更新水电信息
     * @author jixd
     * @created 2018年01月08日 14:03:25
     * @param
     * @return
     */
    public int updateMeterInfoByContractId(MeterDetailEntity meterDetailEntity){
        return meterDetailDao.updateByContractId(meterDetailEntity);
    }
}
