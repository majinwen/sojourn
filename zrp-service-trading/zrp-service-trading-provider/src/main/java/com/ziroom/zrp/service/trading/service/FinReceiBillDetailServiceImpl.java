package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.ziroom.zrp.service.trading.dao.FinReceiBillDetailDao;
import com.ziroom.zrp.service.trading.dto.RentFinReceiBillDto;
import com.ziroom.zrp.service.trading.pojo.SynFinBillDetailPojo;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.trading.entity.RentContractBillEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;

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
 * @Date Created in 2017年10月13日 20:15
 * @since 1.0
 */
@Service("trading.finReceiBillDetailServiceImpl")
public class FinReceiBillDetailServiceImpl {

    @Resource(name = "trading.finReceiBillDetailDao")
    private FinReceiBillDetailDao finReceiBillDetailDao;

    /**
     * @description: 保存应收账单明细
     * @author: lusp
     * @date: 2017/10/13 17:50
     * @params: finReceiBillDetailEntity
     * @return: int
     */
    public int saveFinReceiBillDetail(FinReceiBillDetailEntity finReceiBillDetailEntity){
        return finReceiBillDetailDao.saveFinReceiBillDetail(finReceiBillDetailEntity);
    }

    /**
     * @description: 根据财务应收账单编号更新应收账单明细
     * @author: lusp
     * @date: 2017/10/16 下午 14:58
     * @params: finReceiBillDetailEntity
     * @return: int
     */
    public int updateFinReceiBillDetailByBillNum(FinReceiBillDetailEntity finReceiBillDetailEntity){
        return finReceiBillDetailDao.updateFinReceiBillDetailByBillNum(finReceiBillDetailEntity);
    }


    /**
     * 更新应收账单的同步财务状态及财务产生的应收账单编号
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public int updateFinReceiBillStatusAndBillNum(FinReceiBillDetailEntity finReceiBillDetailEntity) {
        return finReceiBillDetailDao.updateFinReceiBillStatusAndBillNum(finReceiBillDetailEntity);
    }

    /**
     * 更新应收账单的同步财务状态及财务产生的应收账单编号
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public int updateFinReceiBillStatus(FinReceiBillDetailEntity finReceiBillDetailEntity) {
        return finReceiBillDetailDao.updateFinReceiBillStatus(finReceiBillDetailEntity);
    }

    /**
     * 根据应收账单号查询账单创建日期
     * @param fBillNum 应收账单号
     * @return
     * @author cuigh6
     * @Date 2017年10月20日
     */
    public FinReceiBillDetailEntity getReceiBillDetailByFBillNum(String fBillNum) {
        return this.finReceiBillDetailDao.getReceiBillDetailByFBillNum(fBillNum);
    }

    /**
     * 支付校验 查询合同信息根据账单
     * @param billNum 账单号
     * @return 对象
     * @author cuigh6
     * @Date 2017年10月
     */
    public RentContractBillEntity getContractInfoForValid(String billNum) {
        return this.finReceiBillDetailDao.getContractInfoForValid(billNum);
    }

    /**
     * 查询费用明细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<FinReceiBillDetailEntity> selectGroupExpenseItemByContractIds(List<String> contractIdList) {
        return this.finReceiBillDetailDao.selectGroupExpenseItemByContractIds(contractIdList);
    }

    /**
     * 只查询房租的应收信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<RentFinReceiBillDto> selectRentFinReceiBillByContractIds(List<String> contractIdList) {
        return this.finReceiBillDetailDao.selectRentFinReceiBillByContractIds(contractIdList);
    }

    /**
     * 查询应收账单明细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<FinReceiBillDetailEntity> selectByBillFid(String billFid) {
        return this.finReceiBillDetailDao.selectByBillFid(billFid);
    }

    /**
     * 查询应收账单明细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public FinReceiBillDetailEntity selectByReceiBillDetailFid(String receiBillDetailFid) {
        return this.finReceiBillDetailDao.selectByReceiBillDetailFid(receiBillDetailFid);
    }

    /**
     * 查询需要同步应收账单明细数据
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public PagingResult<SynFinBillDetailPojo> findFinBillDetailNotSyncToFin(Date queryDate, PageBounds pageBounds) {
        return this.finReceiBillDetailDao.findFinBillDetailNotSyncToFin(queryDate, pageBounds);
    }

    /**
     * 查询
     * @author jixd
     * @created 2017年11月30日 09:38:00
     * @param
     * @return
     */
    public List<FinReceiBillDetailEntity> listFinReceiBillDetailByContractId(String contractId,String billType,Integer payNum){
        return finReceiBillDetailDao.listFinReceiBillDetailByContractId(contractId,billType,payNum);
    }
    
    /**
     * 
     * 合同和费用类型查询未付款应收账单详情 
     *
     * @author bushujie
     * @created 2018年1月31日 下午3:32:16
     *
     * @param contractId
     * @param billType
     * @return
     */
    public List<FinReceiBillDetailEntity> getFinReceiBillByContractId(String contractId,Integer itemId){
    	return finReceiBillDetailDao.getFinReceiBillByContractId(contractId, itemId);
    }

    /**
     * 查询已过期的智能电费的应收账单，作废用
     * author xiangb
     * @return
     */
    public List<FinReceiBillDetailEntity> selectOutTimeFinReceiBillDetail(){
        return this.finReceiBillDetailDao.selectOutTimeFinReceiBillDetail();
    }
}
