package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.zrp.service.trading.dao.RentCheckinPersonDao;
import com.ziroom.zrp.service.trading.dao.RentDetailDao;
import com.ziroom.zrp.service.trading.dao.SharerDao;
import com.ziroom.zrp.service.trading.dto.checkin.RentCheckinPersonSearchDto;
import com.ziroom.zrp.service.trading.entity.CheckSignCusInfoVo;
import com.ziroom.zrp.service.trading.entity.RentCheckinPersonVo;
import com.ziroom.zrp.service.trading.pojo.PersonAndSharerPojo;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.ziroom.zrp.trading.entity.RentDetailEntity;
import com.ziroom.zrp.trading.entity.SharerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年09月12日 20:03
 * @since 1.0
 */
@Slf4j
@Service("trading.rentCheckinPersonServiceImpl")
public class RentCheckinPersonServiceImpl {

    @Resource(name = "trading.rentCheckinPersonDao")
    private RentCheckinPersonDao rentCheckinPersonDao;

	@Resource(name = "trading.sharerDao")
	private SharerDao sharerDao;

	@Resource(name = "trading.rentDetailDao")
	private RentDetailDao rentDetailDao;

    /**
     * @description: 根据合同id查询用户验签信息
     * @author: lusp
     * @date: 2017/9/12 15:44
     * @params: customerUid
     * @return: CheckSignCusInfoVo
     */
    public CheckSignCusInfoVo findCheckSignCusInfoVoByUid(RentContractEntity rentContractEntity){
        return rentCheckinPersonDao.findCheckSignCusInfoVoByUid(rentContractEntity);
    }

    public int saveCheckinPerson(RentCheckinPersonEntity rentCheckinPersonEntity){
    	return rentCheckinPersonDao.saveCheckinPerson(rentCheckinPersonEntity);
    }
    
    public RentCheckinPersonEntity findCheckinPersonByContractId(String contractId){
    	return rentCheckinPersonDao.findCheckinPersonByContractId(contractId);
    }

    /**
     *
     * 根据合同id和uid查询入住人，为了兼容老数据的企业合同id是一合同对多个入住人的问题
     *
     * @author zhangyl2
     * @created 2018年01月26日 20:42
     * @param
     * @return
     */
    public RentCheckinPersonEntity findCheckinPerson(String contractId, String uid){
        return rentCheckinPersonDao.findCheckinPerson(contractId, uid);
    }

    public int updateCheckinPerson(RentCheckinPersonEntity rentCheckinPersonEntity){
    	return rentCheckinPersonDao.updateCheckinPerson(rentCheckinPersonEntity);
    }
	/**
	 * 保存或者更新个人用户信息
	 *
	 * @param checkInPerson 入住人
	 * @return sharers 合住人
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	public void saveOrUpdatePersonCustomerInfo(RentCheckinPersonEntity checkInPerson, List<SharerEntity> sharers) {
		// 查询是否存在
		RentCheckinPersonEntity checkinPerson1 = this.rentCheckinPersonDao.findCheckinPersonByContractId(checkInPerson.getContractId());
		if (checkinPerson1 == null) {
			// 保存
			this.saveCheckinPerson(checkInPerson);
		}else{
			// 更新
			checkInPerson.setId(checkinPerson1.getId());
			this.updateCheckinPerson(checkInPerson);
		}
		if (!Check.NuNCollection(sharers)){
			// 删除合住人信息
			sharerDao.deleteByContractId(checkInPerson.getContractId());
			// 保存
			sharers.forEach(v->{
				v.setRentId(checkInPerson.getId());
				sharerDao.saveSharer(v);
			});
		}
	}

	/**
	 *
	 * 分页查询入住人列表
	 *
	 * @author bushujie
	 * @created 2017年12月5日 下午3:56:38
	 *
	 * @param dto
	 * @return
	 */
	public PagingResult<RentCheckinPersonVo> searchByCriteria(RentCheckinPersonSearchDto dto){
		return rentCheckinPersonDao.searchByCriteria(dto);
	}

    /**
     *
     * 历史入住人
     *
     * @author zhangyl2
     * @created 2017年12月07日 21:19
     * @param
     * @return
     */
    public PagingResult<PersonAndSharerPojo> selectHistoryPersonAndSharer(RentCheckinPersonSearchDto dto) {
        return rentCheckinPersonDao.selectHistoryPersonAndSharer(dto);
    }

    /**
     *
     * 根据合同与uid查询入住人详情
     *
     * @author zhangyl2
     * @created 2017年12月07日 21:19
     * @param
     * @return
     */
    public RentCheckinPersonEntity selectHistoryCheckinPerson(Integer id) {
        return rentCheckinPersonDao.selectHistoryCheckinPerson(id);
    }

	/**
	 * 保存入住人和合住人
	 * 		删除原来的入住人和合住人
	 * @param rentCheckinPersonEntity
	 * @param sharerEntities
	 */
	public void saveAndDeteletCheckinAndSharer(String rentedetailId, String oldUid, RentCheckinPersonEntity rentCheckinPersonEntity, List<SharerEntity> sharerEntities) throws Exception {
		// 先删除老的
        if(!Check.NuNStr(oldUid)){
		    deleteCheckinPersonAndSharer(oldUid, rentCheckinPersonEntity);
        }
		// 保存
		saveCheckinAndSharer(rentedetailId, rentCheckinPersonEntity, sharerEntities);
    }

	public List<RentContractEntity> findCheckInPersonContractList(String uid) {
		return this.rentCheckinPersonDao.findCheckInPersonContractList(uid);
	}


    /**
     * 逻辑删除 入住人 和 合住人 根据合同Id
     * 记录删除人员
     * @param
     * @return
     */
    private void deleteCheckinPersonAndSharer(String oldUid, RentCheckinPersonEntity rentCheckinPersonEntity) throws Exception{

        int tenantFlag, sharerFlag;
        String contractId = rentCheckinPersonEntity.getContractId();

        try{
            RentCheckinPersonEntity oldCheckinPerson = findCheckinPerson(contractId, oldUid);
            if(!Check.NuNObj(oldCheckinPerson)){
                tenantFlag = rentCheckinPersonDao.deleteByContractIdAndRecord(
                        RentCheckinPersonEntity.builder()
                                .updateName(rentCheckinPersonEntity.getUpdateName())
                                .updaterId(rentCheckinPersonEntity.getUpdaterId())
                                .contractId(contractId)
                                .uid(oldUid)
                                .build()
                );
                sharerFlag = sharerDao.deleteByContractIdAndRecord(
                        SharerEntity.builder()
                                .fupdaterid(rentCheckinPersonEntity.getUpdaterId())
                                .fupdatername(rentCheckinPersonEntity.getUpdateName())
                                .rentId(oldCheckinPerson.getId())
                                .build()
                );
                log.debug("update rentCheckinPerson {} by contractId : {}", tenantFlag, contractId);
                log.debug("update sharer : {} by contractId : {}", sharerFlag, contractId);
            }
        } catch (Exception e) {
            log.error("deleteCheckinPersonAndSharer e={}", e);
            String mesg = "deleteCheckinPersonAndSharerByContractId has error!";
            throw new Exception(mesg + "contractId :" + contractId);
        }
    }

    private void saveCheckinAndSharer(String rentedetailId, RentCheckinPersonEntity rentCheckinPersonEntity, List<SharerEntity> sharerEntities) throws Exception {

        int reuslt = saveCheckinPerson(rentCheckinPersonEntity);

        if (reuslt != 1) {
            throw new Exception("checkin person insert fail");
        }

        // 查询该入住人
        RentCheckinPersonEntity dbCheckinPerson = rentCheckinPersonDao.findCheckinPerson(rentCheckinPersonEntity.getContractId(), rentCheckinPersonEntity.getUid());

        // 更新合同 录入状态
        RentDetailEntity entity = RentDetailEntity.builder().contractId(rentCheckinPersonEntity.getContractId()).build();
        entity.putInPersonData(true);
        entity.setId(rentedetailId);
        rentDetailDao.updateRentDetailByContractId(entity);

        // 保存
        sharerEntities.forEach(v->{
            v.setRentId(dbCheckinPerson.getId());
            v.setUid(rentCheckinPersonEntity.getUid());// 维护合住人与入住人的关系，方便查询历史入住记录
            sharerDao.saveSharer(v);
        });
    }
	/**
	 * 保存企业 入住人合住人数据
	 * @author jixd
	 * @created 2018年01月29日 14:00:51
	 * @param
	 * @return
	 */
    public void saveEnterpriseCheckinAndSharer(Map<RentCheckinPersonEntity,List<SharerEntity>> saveMap) throws Exception{
		for (Map.Entry<RentCheckinPersonEntity,List<SharerEntity>> entry : saveMap.entrySet()){
			RentCheckinPersonEntity rentCheckinPersonEntity = entry.getKey();
			List<SharerEntity> sharerEntities = entry.getValue();
			deleteCheckinPersonAndSharer(rentCheckinPersonEntity.getUid(),rentCheckinPersonEntity);

			if (!Check.NuNObj(rentCheckinPersonEntity)){
				saveCheckinPerson(rentCheckinPersonEntity);
			}
			if (!Check.NuNObj(rentCheckinPersonEntity) && !Check.NuNCollection(sharerEntities)){
				sharerEntities.forEach(a -> {
					a.setRentId(rentCheckinPersonEntity.getId());
					sharerDao.saveSharer(a);
				});
			}

		}
	}

}
