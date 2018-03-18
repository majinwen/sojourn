package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.zrp.service.trading.dao.*;
import com.ziroom.zrp.service.trading.dto.PaymentBillsDto;
import com.ziroom.zrp.trading.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月13日 20:12
 * @since 1.0
 */
@Service("trading.finReceiBillServiceImpl")
public class FinReceiBillServiceImpl {

    @Resource(name = "trading.finReceiBillDao")
    private FinReceiBillDao finReceiBillDao;

    @Resource(name = "trading.finReceiBillDetailDao")
    private FinReceiBillDetailDao finReceiBillDetailDao;

    @Resource(name = "trading.rentContractDao")
    private RentContractDao rentContractDao;

    @Resource(name = "trading.rentDetailDao")
    private RentDetailDao rentDetailDao;

    @Resource(name = "trading.rentContractActivityDao")
    private RentContractActivityDao rentContractActivityDao;

    /**
     * @description: 保存应收账单
     * @author: lusp
     * @date: 2017/10/13 17:47
     * @params: finReceiBillEntity
     * @return: int
     */
    public int saveFinReceiBill(FinReceiBillEntity finReceiBillEntity){
        return finReceiBillDao.saveFinReceiBill(finReceiBillEntity);
    }

    /**
     * 保存应收 和对应的明细
     * @author jixd
     * @created 2017年11月08日 10:58:23
     * @param
     * @return
     */
    public int saveFinReceiBillAndDetail(FinReceiBillEntity finReceiBillEntity, List<FinReceiBillDetailEntity> list){
        int count = 0;
        count += finReceiBillDao.saveFinReceiBill(finReceiBillEntity);
        if (!Check.NuNCollection(list)){
            for (FinReceiBillDetailEntity finReceiBillDetailEntity : list){
                count += finReceiBillDetailDao.saveFinReceiBillDetail(finReceiBillDetailEntity);
            }
        }
        return count;
    }

    /**
     *
     * @author jixd
     * @created 2017年11月30日 10:40:52
     * @param
     * @return
     */
    public int updateFinReceiBillAndSaveDetails(FinReceiBillEntity finReceiBillEntity,List<FinReceiBillDetailEntity> list){
        int count = 0;
        count += finReceiBillDao.updateByFid(finReceiBillEntity);
        if (!Check.NuNCollection(list)){
            for (FinReceiBillDetailEntity finReceiBillDetailEntity : list){
                count += finReceiBillDetailDao.saveFinReceiBillDetail(finReceiBillDetailEntity);
            }
        }
        return count;
    }

    /**
     * @description: 获取应收账单序列号
     * @author: lusp
     * @date: 2017/10/13 下午 20:05
     * @params:
     * @return: int
     */
    public int selectPayBillSeq(){
        return finReceiBillDao.selectPayBillSeq();
    }

    /**
     * 根据父合同id,显示每个合同(房间)的费用信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public void selectGroupFinReceiBillInfoByByParentId() {

    }

    /**
     * 更新合同信息并保存应收账单
     * @author jixd
     * @created 2017年11月14日 10:46:17
     * @param
     * @return
     */
    public int saveEnterpriceReceiBills(List<FinReceiBillEntity> saveFinReceiBills,List<FinReceiBillDetailEntity> saveFinReceiDetailBills,
                                        List<RentContractEntity> updateContracts,List<RentDetailEntity> upRentDetails,List<RentContractActivityEntity> saveActList){
        int count = 0;
        if (!Check.NuNCollection(updateContracts)){
            for (RentContractEntity rentContractEntity : updateContracts){
                //先删除合同对应的插入的账单数据和活动数据
                count += finReceiBillDao.deleteReceiptBillByContractId(rentContractEntity.getContractId());
                count += rentContractActivityDao.deleteActivityByContractId(rentContractEntity.getContractId());

                count += rentContractDao.updateBaseContractById(rentContractEntity);
            }
        }
        if (!Check.NuNCollection(upRentDetails)){
            for (RentDetailEntity rentDetailEntity : upRentDetails){
                count += rentDetailDao.updateRentDetailByContractId(rentDetailEntity);
            }
        }

        if (!Check.NuNCollection(saveActList)){
            for (RentContractActivityEntity activityEntity : saveActList){
                count += rentContractActivityDao.insertSelective(activityEntity);
            }
        }
        if (!Check.NuNCollection(saveFinReceiBills)){
            for (FinReceiBillEntity finReceiBillEntity : saveFinReceiBills){
                count += finReceiBillDao.saveFinReceiBill(finReceiBillEntity);
            }
        }
        if (!Check.NuNCollection(saveFinReceiDetailBills)){
            for (FinReceiBillDetailEntity finReceiBillDetailEntity : saveFinReceiDetailBills){
                count += finReceiBillDetailDao.saveFinReceiBillDetail(finReceiBillDetailEntity);
            }
        }
        return count;
    }

    /**
     * 查询指定日期需要收款的合同号
     * @param projectIds 项目标识 逗号分割
     * @return
     * @author cuigh6
     * @Date 2017年11月15日
     */
    public List<String> getReceiptContractByDate(String projectIds,String startDate,String endDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("projectIds", projectIds.split(","));
        return this.finReceiBillDao.getReceiptContractByDate(paramMap);
    }

    /**
      * @description: 根据条件查出需要收款的合同号，供去财务查询收款单列表使用
      * @author: lusp
      * @date: 2017/11/21 下午 15:41
      * @params: paymentBillsDto
      * @return: List<String>
      */
    public List<String> getZRAReceiptContractCode(PaymentBillsDto paymentBillsDto) {
        return this.finReceiBillDao.getZRAReceiptContractCode(paymentBillsDto);
    }

    /**
     * 查询应收账单
     * @author cuiyuhui
     * @created  
     * @param
     * @return 
     */
    public List<FinReceiBillEntity> selectByContractId(String contractId) {
        return this.finReceiBillDao.selectByContractId(contractId);
    }

    /**
     * 查询应收明细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public FinReceiBillEntity selectByFid(String fid) {
        return this.finReceiBillDao.selectByFid(fid);
    }

    /**
     *  更新
     * @author jixd
     * @created 2018年02月08日 11:40:32
     * @param
     * @return
     */
    public int updateFinReceiBillByFid(FinReceiBillEntity finReceiBillEntity){
       return finReceiBillDao.updateByFid(finReceiBillEntity);
    }

    /**
     * 根据收款单id作废收款单，定时任务用
     * @author xiangb
     * @return
     */
    public int deleteReceiptBillByFid(List<String> fids){
        return this.finReceiBillDao.deleteReceiptBillByFid(fids);
    }
    
    /**
     * 根据应收账单编号查询
     * @param billNum
     * @return
     */
    public FinReceiBillEntity selectByBillNum(String billNum){
        return finReceiBillDao.selectByBillNum(billNum);
    }
}
