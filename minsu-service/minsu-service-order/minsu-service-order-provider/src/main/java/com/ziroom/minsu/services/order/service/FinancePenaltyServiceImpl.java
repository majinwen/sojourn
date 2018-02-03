package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.dto.PenaltyCancelRequest;
import com.ziroom.minsu.services.order.dto.PenaltyRequest;
import com.ziroom.minsu.services.order.entity.PenaltyRelVo;
import com.ziroom.minsu.services.order.entity.PenaltyVo;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jixd
 * @created 2017年05月10日 15:48:35
 * @param
 * @return
 */
@Service("order.financePenaltyServiceImpl")
public class FinancePenaltyServiceImpl {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FinancePenaltyServiceImpl.class);

    @Resource(name = "order.financePenaltyDao")
    private FinancePenaltyDao financePenaltyDao;

    @Resource(name = "order.financePenaltyLogDao")
    private FinancePenaltyLogDao financePenaltyLogDao;

    @Resource(name = "order.financePayIncomeRelDao")
    private FinancePayIncomeRelDao financePayIncomeRelDao;

    @Resource(name = "order.financePenaltyPayRelDao")
    private FinancePenaltyPayRelDao financePenaltyPayRelDao;

    @Resource(name = "order.financePayVouchersLogDao")
    private FinancePayVouchersLogDao financePayVouchersLogDao;

    @Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;

    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;

    @Resource(name = "order.financePayVouchersDetailDao")
    private FinancePayVouchersDetailDao financePayVouchersDetailDao;


    /**
     * 查询有效罚款单数量
     * @author jixd
     * @created 2017年05月10日 16:10:59
     * @param
     * @return
     */
    public long countAvaliableFinancePenaltyByUid(String uid){
        return financePenaltyDao.countAvaliableFinancePenaltyByUid(uid);
    }

    /**
     * 罚款单冲抵付款单 并转收入
     * 1.罚款单=付款单
     * 2.罚款单<付款单
     * 3.罚款单>付款单
     *
     * 入口后记录日志 罚款单对应 付款单 多对多
     *                付款单对应 收入 1v1
     *
     *
     * @author jixd
     * @created 2017年05月10日 17:23:46
     * @param
     * @param payVouchers
     * @return
     */
    public void penaltyOffsetPayAndTran2Income(FinancePayVouchersEntity payVouchers){
        LogUtil.info(LOGGER,"【FinancePenaltyServiceImpl.penaltyOffsetPayAndTran2Income】开始处理罚款单与付款单冲抵转收入逻辑");
        List<FinancePenaltyEntity> financePenaltyEntities = financePenaltyDao.listAvaliableFinancePenaltyByUid(payVouchers.getReceiveUid());
        if (Check.NuNCollection(financePenaltyEntities)){
            return;
        }
        List<FinancePenaltyEntity> offsetPenaltyList = new ArrayList<>();
        Integer payFee = payVouchers.getTotalFee();
        int addPenaltyFee = 0;

        for (FinancePenaltyEntity financePenalty : financePenaltyEntities){
            addPenaltyFee += financePenalty.getPenaltyLastFee();
            offsetPenaltyList.add(financePenalty);
            if (addPenaltyFee == payFee){
                LogUtil.info(LOGGER,"【罚款单处理】罚款单与付款单相等,pvSn={},payFee={}",payVouchers.getPvSn(),payVouchers.getTotalFee());
                //罚款单列表完成 付款单取消
                this.canclePenaltysAndPay2Income(offsetPenaltyList,payVouchers);
                break;
            }
            if (addPenaltyFee > payFee){
                LogUtil.info(LOGGER,"【罚款单处理】罚款单大于付款单,pvSn={},payFee={}",payVouchers.getPvSn(),payVouchers.getTotalFee());
                //罚款单列表需要设置已完成 当前罚款单更新余额 付款单取消
                offsetPenaltyList.remove(financePenalty);
                int last = addPenaltyFee - payFee;
                LogUtil.info(LOGGER,"【罚款单处理】当前罚款单更新剩余金额,penaltySn={},last={}",financePenalty.getPenaltySn(),last);
                //更新单个进行中的罚款单
                this.updatePenaltyLastFee(financePenalty,payVouchers,last);
                this.canclePenaltysAndPay2Income(offsetPenaltyList,payVouchers);
                break;
            }
        }

        if (addPenaltyFee < payFee){
            //罚款单列表完成状态  付款单拆分成两个付款单（金额与当前罚款单一致）
            LogUtil.info(LOGGER,"【罚款单处理】罚款单小于付款单,生成新付款单,取消当前付款单,pvSn={},payFee={}",payVouchers.getPvSn(),payVouchers.getTotalFee());
            //1.生成两个付款单
            FinancePayVouchersEntity offSetPay = new FinancePayVouchersEntity();
            FinancePayVouchersEntity normalPay = new FinancePayVouchersEntity();
            //拆分付款单
            splitPayVouchers(offSetPay,normalPay,payVouchers,addPenaltyFee);
            //取消罚款单转收入
            canclePenaltysAndPay2Income(offsetPenaltyList,offSetPay);

        }

    }


    /**
     * 付款单金额大于罚款单
     *  当前付款单拆分成两个付款单，一个用于冲抵罚款单 ，一个走正常流程，当前付款单取消
     * @author jixd
     * @created 2017年05月11日 15:48:42
     * @param
     * @return
     */
    private void splitPayVouchers(FinancePayVouchersEntity offSetPay,FinancePayVouchersEntity normalPay,FinancePayVouchersEntity currentPay,int addPenaltyFee){
        Integer totalFee = currentPay.getTotalFee();
        int offsetFee = addPenaltyFee;
        int normalFee = totalFee - addPenaltyFee;
        List<FinancePayVouchersDetailEntity> payVouchersDetails = financePayVouchersDetailDao.findPayVouchersDetailByPvSn(currentPay.getPvSn());
        int feeItemCode = FeeItemCodeEnum.CHECK.getCode();
        if (!Check.NuNCollection(payVouchersDetails) && payVouchersDetails.size() == 1){
            feeItemCode = payVouchersDetails.get(0).getFeeItemCode();
        }
        int upCount = financePayVouchersDao.updatePvOffsetReproCancel(currentPay.getPvSn());
        if (upCount < 1){
            LogUtil.error(LOGGER,"【FinancePenaltyServiceImpl.splitPayVouchers】取消付款单未更新,pvSn={}",currentPay.getPvSn());
            throw new BusinessException("【FinancePenaltyServiceImpl.splitPayVouchers】取消付款单未更新,pvSn="+currentPay.getPvSn());
        }

        BeanUtils.copyProperties(currentPay,offSetPay);
        offSetPay.setId(null);
        String pv1Sn = currentPay.getPvSn() + "_1";
        offSetPay.setPvSn(pv1Sn);
        offSetPay.setParentPvSn(currentPay.getPvSn());
        offSetPay.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
        offSetPay.setActualPayTime(null);
        offSetPay.setRunTime(new Date());
        offSetPay.setCreateTime(new Date());
        offSetPay.setLastModifyDate(new Date());
        offSetPay.setIsSend(YesOrNoEnum.NO.getCode());
        offSetPay.setIsDel(YesOrNoEnum.NO.getCode());
        offSetPay.setTotalFee(offsetFee);

        FinancePayVouchersDetailEntity offsetDetail = new FinancePayVouchersDetailEntity();
        offsetDetail.setPvSn(pv1Sn);
        offsetDetail.setFeeItemCode(feeItemCode);
        offsetDetail.setItemMoney(offsetFee);
        int offCount = financePayVouchersDao.insertPayVouchers(offSetPay);
        if (offCount < 1){
            throw new BusinessException("【FinancePenaltyServiceImpl.splitPayVouchers】生成付款单未更新,pvSn="+currentPay.getPvSn());
        }
        financePayVouchersDetailDao.insertPayVouchersDetail(offsetDetail);

        BeanUtils.copyProperties(offSetPay,normalPay);
        String pv2Sn = currentPay.getPvSn() + "_2";
        normalPay.setPvSn(pv2Sn);
        normalPay.setTotalFee(normalFee);

        FinancePayVouchersDetailEntity normalDetail = new FinancePayVouchersDetailEntity();
        normalDetail.setPvSn(pv2Sn);
        normalDetail.setFeeItemCode(feeItemCode);
        normalDetail.setItemMoney(normalFee);

        int normalCount = financePayVouchersDao.insertPayVouchers(normalPay);
        if (normalCount < 1){
            throw new BusinessException("【FinancePenaltyServiceImpl.splitPayVouchers】生成付款单未更新,pvSn="+currentPay.getPvSn());
        }
        financePayVouchersDetailDao.insertPayVouchersDetail(normalDetail);
        LogUtil.info(LOGGER,"【罚款单处理】付款单{}重新生成两个付款单{} & {}.",currentPay.getPvSn(),pv1Sn,pv2Sn);
    }

    /**
     * 更新未冲抵完的罚款单
     * @author jixd
     * @created 2017年05月10日 20:17:55
     * @param last 剩余金额 分
     * @param financePenalty
     * @param payVouchers
     * @return
     */
    private int updatePenaltyLastFee(FinancePenaltyEntity financePenalty,FinancePayVouchersEntity payVouchers,int last){
        int count = 0;
        int oldFee = financePenalty.getPenaltyLastFee();
        //更新日志
        FinancePenaltyLogEntity financePenaltyLogEntity = new FinancePenaltyLogEntity();
        financePenaltyLogEntity.setPenaltySn(financePenalty.getPenaltySn());
        financePenaltyLogEntity.setFromFee(financePenalty.getPenaltyLastFee());
        financePenaltyLogEntity.setToFee(last);
        financePenaltyLogEntity.setFromStatus(financePenalty.getPenaltyStatus());
        financePenaltyLogEntity.setToStatus(PenaltyStatusEnum.DOING.getCode());
        financePenaltyLogEntity.setRemark("定时任务冲抵");
        count += financePenaltyLogDao.saveFinancePenaltyLog(financePenaltyLogEntity);

        //更新罚款单
        financePenalty.setPenaltyStatus(PenaltyStatusEnum.DOING.getCode());
        financePenalty.setPenaltyLastFee(last);
        count += financePenaltyDao.updateFinancePenaltyByOrderSn(financePenalty);

        //保存对应关系
        FinancePenaltyPayRelEntity financePenaltyPayRelEntity = new FinancePenaltyPayRelEntity();
        financePenaltyPayRelEntity.setPenaltySn(financePenalty.getPenaltySn());
        financePenaltyPayRelEntity.setPenaltyOrderSn(financePenalty.getOrderSn());
        financePenaltyPayRelEntity.setPvSn(payVouchers.getPvSn());
        financePenaltyPayRelEntity.setPvOrderSn(payVouchers.getOrderSn());
        financePenaltyPayRelEntity.setOffsetFee(oldFee - last);
        count += financePenaltyPayRelDao.saveFinancePenaltyPayRel(financePenaltyPayRelEntity);
        return count;
    }

    /**
     * 取消罚款单与付款单 转收入
     * @author jixd
     * @created 2017年05月11日 09:03:51
     * @param
     * @return
     */
    private int canclePenaltysAndPay2Income(List<FinancePenaltyEntity> financePenaltys,FinancePayVouchersEntity payVouchers){
        int count = 0;
        //付款单转收入
        count += offsetPayTran2Income(payVouchers);
        //处理罚款单
        for (FinancePenaltyEntity financePenalty : financePenaltys){

            int oldStatus = financePenalty.getPenaltyStatus();
            int oldFee = financePenalty.getPenaltyLastFee();

            financePenalty.setPenaltyStatus(PenaltyStatusEnum.FINISH.getCode());
            financePenalty.setPenaltyLastFee(0);
            count += financePenaltyDao.updateFinancePenaltyByOrderSn(financePenalty);

            //设置罚款单与付款单对应关系
            FinancePenaltyPayRelEntity financePenaltyPayRelEntity = new FinancePenaltyPayRelEntity();
            financePenaltyPayRelEntity.setPenaltySn(financePenalty.getPenaltySn());
            financePenaltyPayRelEntity.setPenaltyOrderSn(financePenalty.getOrderSn());
            financePenaltyPayRelEntity.setPvSn(payVouchers.getPvSn());
            financePenaltyPayRelEntity.setPvOrderSn(payVouchers.getOrderSn());
            financePenaltyPayRelEntity.setOffsetFee(oldFee);
            count += financePenaltyPayRelDao.saveFinancePenaltyPayRel(financePenaltyPayRelEntity);

            //更新罚款单日志
            FinancePenaltyLogEntity financePenaltyLogEntity = new FinancePenaltyLogEntity();
            financePenaltyLogEntity.setPenaltySn(financePenalty.getPenaltySn());
            financePenaltyLogEntity.setFromFee(oldFee);
            financePenaltyLogEntity.setToFee(0);
            financePenaltyLogEntity.setFromStatus(oldStatus);
            financePenaltyLogEntity.setToStatus(PenaltyStatusEnum.FINISH.getCode());
            financePenaltyLogEntity.setRemark("定时任务冲抵");
            count += financePenaltyLogDao.saveFinancePenaltyLog(financePenaltyLogEntity);
        }
        return count;
    }

    /**
     * 罚款单 冲抵付款单转收入
     * @author jixd
     * @created 2017年05月11日 09:44:48
     * @param
     * @return
     */
    private int offsetPayTran2Income(FinancePayVouchersEntity payVouchers){
        if (Check.NuNObj(payVouchers)){
            LogUtil.error(LOGGER, "【FinancePenaltyServiceImpl.offsetPayTran2Income】付款单为空");
            throw new BusinessException("【FinancePenaltyServiceImpl.offsetPayTran2Income】付款单为空");
        }
        //将当前的付款取消
        int num = financePayVouchersDao.updatePvOffsetCancel(payVouchers.getPvSn());
        if(num != 1){
            LogUtil.error(LOGGER, "【FinancePenaltyServiceImpl.offsetPayTran2Income】更新状态记录：pvsn：{},num:{}",payVouchers.getPvSn(),num);
            throw new BusinessException("异常的付款单信息");
        }
        //将当前的付款单转化成收入
        FinanceIncomeEntity incomeEntity = new FinanceIncomeEntity();
        String incomeSn = OrderSnUtil.getIncomeSn();
        incomeEntity.setIncomeSn(incomeSn);
        incomeEntity.setIncomeSourceType(IncomeSourceTypeEnum.USER_SETTLEMENT.getCode());
        incomeEntity.setIncomeType(IncomeTypeEnum.PENALTY.getCode());
        incomeEntity.setOrderSn(payVouchers.getOrderSn());
        incomeEntity.setCityCode(payVouchers.getCityCode());
        incomeEntity.setPayUid(payVouchers.getPayUid());
        incomeEntity.setPayType(UserTypeEnum.LANDLORD.getUserType());
        incomeEntity.setTotalFee(payVouchers.getTotalFee());
        num += financeIncomeDao.insertFinanceIncome(incomeEntity);

        //处理付款单的操作记录
        FinancePayVouchersLogEntity payVouchersLogEntity = new FinancePayVouchersLogEntity();
        payVouchersLogEntity.setFid(UUIDGenerator.hexUUID());
        payVouchersLogEntity.setPvSn(payVouchers.getPvSn());
        payVouchersLogEntity.setOrderSn(payVouchers.getOrderSn());
        payVouchersLogEntity.setCallStatus(YesOrNoEnum.YES.getCode());
        payVouchersLogEntity.setResultCode("未请求");
        payVouchersLogEntity.setResultMsg("罚款单冲抵付款单，该付款单已取消");
        num += financePayVouchersLogDao.insertFinancePayVouchersLog(payVouchersLogEntity);

        //记录付款单与收入之间的对应关系
        FinancePayIncomeRelEntity financePayIncomeRelEntity = new FinancePayIncomeRelEntity();
        financePayIncomeRelEntity.setPvSn(payVouchers.getPvSn());
        financePayIncomeRelEntity.setPvOrderSn(payVouchers.getOrderSn());
        financePayIncomeRelEntity.setIncomeSn(incomeSn);
        financePayIncomeRelEntity.setTranFee(payVouchers.getTotalFee());
        num += financePayIncomeRelDao.saveFinancePayIncomeRel(financePayIncomeRelEntity);
        return num;
    }

    /**
     * 根据条件分页查询罚款单列表
     * @author jixd
     * @created 2017年05月15日 13:44:09
     * @param
     * @return
     */
    public PagingResult<PenaltyVo> listPenaltyPageByCondition(PenaltyRequest penaltyRequest){
        return financePenaltyDao.listPenaltyPageByCondition(penaltyRequest);
    }

    /**
     * 只作废未开始罚款单，进行中罚款单不能作废
     * @author jixd
     * @created 2017年05月15日 15:30:13
     * @param
     * @return
     */
    public int cancelPenalty(PenaltyCancelRequest penaltyCancelRequest){
        FinancePenaltyEntity oldPenalty = financePenaltyDao.findPenaltyByPenaltySn(penaltyCancelRequest.getPenaltySn());
        if (Check.NuNObj(oldPenalty)){
            return 0;
        }
        if (oldPenalty.getPenaltyStatus() != PenaltyStatusEnum.WAITING.getCode()){
            return 0;
        }

        FinancePenaltyEntity financePenaltyEntity = new FinancePenaltyEntity();
        financePenaltyEntity.setPenaltySn(penaltyCancelRequest.getPenaltySn());
        financePenaltyEntity.setOldPenaltyStatus(PenaltyStatusEnum.WAITING.getCode());
        financePenaltyEntity.setPenaltyStatus(PenaltyStatusEnum.ABOLISH.getCode());
        int count = financePenaltyDao.updatePenaltyByPenaltySnAndStatus(financePenaltyEntity);
        if (count > 0){
            FinancePenaltyLogEntity penaltyLogEntity = new FinancePenaltyLogEntity();
            penaltyLogEntity.setPenaltySn(oldPenalty.getPenaltySn());
            penaltyLogEntity.setFromFee(oldPenalty.getPenaltyLastFee());
            penaltyLogEntity.setToFee(oldPenalty.getPenaltyLastFee());
            penaltyLogEntity.setFromStatus(oldPenalty.getPenaltyStatus());
            penaltyLogEntity.setToStatus(PenaltyStatusEnum.ABOLISH.getCode());
            penaltyLogEntity.setEmpCode(penaltyCancelRequest.getEmpCode());
            penaltyLogEntity.setEmpName(penaltyCancelRequest.getEmpName());
            penaltyLogEntity.setRemark(penaltyCancelRequest.getRemark());
            financePenaltyLogDao.saveFinancePenaltyLog(penaltyLogEntity);
        }
        return count;
    }

    /**
     * 查询罚款单与付款单与收入关系
     * @author jixd
     * @created 2017年05月16日 11:59:23
     * @param
     * @return
     */
    public List<PenaltyRelVo> listPenaltyPayAndIncomeRel(String penaltySn){
        return financePenaltyDao.listPenaltyPayAndIncomeRel(penaltySn);
    }


}
