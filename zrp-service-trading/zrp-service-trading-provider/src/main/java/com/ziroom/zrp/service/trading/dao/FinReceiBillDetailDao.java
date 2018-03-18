package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.service.trading.dto.RentFinReceiBillDto;
import com.ziroom.zrp.service.trading.pojo.SynFinBillDetailPojo;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.trading.entity.RentContractBillEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>应收账单明细dao</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月13日 17:12
 * @since 1.0
 */
@Repository("trading.finReceiBillDetailDao")
public class FinReceiBillDetailDao {

    private String SQLID = "trading.finReceiBillDetailDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * @description: 保存应收账单明细
     * @author: lusp
     * @date: 2017/10/13 17:45
     * @params: finReceiBillDetailEntity
     * @return: int
     */
    public int saveFinReceiBillDetail(FinReceiBillDetailEntity finReceiBillDetailEntity){
        if(Check.NuNStr(finReceiBillDetailEntity.getFid())){
            finReceiBillDetailEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insertSelective",finReceiBillDetailEntity);
    }

    /**
      * @description: 根据财务应收账单编号更新应收账单明细
      * @author: lusp
      * @date: 2017/10/16 下午 14:57
      * @params: finReceiBillDetailEntity
      * @return: int
      */
    public int updateFinReceiBillDetailByBillNum(FinReceiBillDetailEntity finReceiBillDetailEntity){
        return mybatisDaoContext.update(SQLID + "updateFinReceiBillDetailByBillNum",finReceiBillDetailEntity);
    }

    /**
     * 更新应收账单的同步财务状态及财务产生的应收账单编号
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public int updateFinReceiBillStatusAndBillNum(FinReceiBillDetailEntity finReceiBillDetailEntity) {
        return mybatisDaoContext.update(SQLID + "updateFinReceiBillStatusAndBillNum", finReceiBillDetailEntity);
    }

    /**
     * 更新应收账单的同步财务状态及财务产生的应收账单编号
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public int updateFinReceiBillStatus(FinReceiBillDetailEntity finReceiBillDetailEntity) {
        return mybatisDaoContext.update(SQLID + "updateFinReceiBillStatus", finReceiBillDetailEntity);
    }

    /**
     * 根据应收账单号查询账单创建日期
     * @param fBillNum 应收账单号
     * @return
     * @author cuigh6
     * @Date 2017年10月20日
     */
    public FinReceiBillDetailEntity getReceiBillDetailByFBillNum(String fBillNum) {
        return this.mybatisDaoContext.findOneSlave(SQLID + "getReceiBillDetailByFBillNum", FinReceiBillDetailEntity.class, fBillNum);
    }

    /**
     * 支付校验 查询合同信息根据账单
     * @param billNum 账单号
     * @return 对象
     * @author cuigh6
     * @Date 2017年10月
     */
    public RentContractBillEntity getContractInfoForValid(String billNum) {
        return this.mybatisDaoContext.findOneSlave(SQLID + "getContractInfoForValid", RentContractBillEntity.class, billNum);
    }

    /**
     * 查询费用明细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<FinReceiBillDetailEntity> selectGroupExpenseItemByContractIds(List<String> contractIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("contractIds", contractIdList);
        return this.mybatisDaoContext.findAll(SQLID + "selectGroupExpenseItemByContractIds", FinReceiBillDetailEntity.class, map);
    }

    /**
     * 只查询房租的应收信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<RentFinReceiBillDto> selectRentFinReceiBillByContractIds(List<String> contractIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("contractIds", contractIdList);
        return this.mybatisDaoContext.findAll(SQLID + "selectRentFinReceiBillByContractIds", RentFinReceiBillDto.class, map);
    }

    /**
     * 查询应收账单明细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<FinReceiBillDetailEntity> selectByBillFid(String billFid) {
        return this.mybatisDaoContext.findAll(SQLID + "selectByBillFid", FinReceiBillDetailEntity.class, billFid);
    }

    /**
     * 查询应收账单明细
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public FinReceiBillDetailEntity selectByReceiBillDetailFid(String receiBillDetailFid) {
        return this.mybatisDaoContext.findOneSlave(SQLID + "selectByReceiBillDetailFid", FinReceiBillDetailEntity.class, receiBillDetailFid);
    }


    /**
     * 查询需要同步应收账单明细数据
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public PagingResult<SynFinBillDetailPojo> findFinBillDetailNotSyncToFin(Date queryDate, PageBounds pageBounds) {
        Map map = new HashMap();
        map.put("queryDate", queryDate);
        return mybatisDaoContext.findForPage(SQLID+"findFinBillDetailNotSyncToFin", SynFinBillDetailPojo.class, map, pageBounds);
    }

    /**
     * 根据合同号 和billType查询 账单
     * @author jixd
     * @created 2017年11月30日 09:36:09
     * @param
     * @return
     */
    public List<FinReceiBillDetailEntity> listFinReceiBillDetailByContractId(String contractId,String billType,Integer payNum){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractId);
        paramMap.put("billType",billType);
        paramMap.put("payNum",payNum);
        return mybatisDaoContext.findAll(SQLID + "listFinReceiBillDetailByContractId",FinReceiBillDetailEntity.class,paramMap);
    }
    
    /**
     * 
     * 合同和费用类型查询未付款应收账单详情 
     *
     * @author bushujie
     * @created 2018年1月31日 下午3:22:56
     *
     * @param contractId
     * @param billType
     * @return
     */
    public List<FinReceiBillDetailEntity> getFinReceiBillByContractId(String contractId,Integer itemId){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
        paramMap.put("contractId",contractId);
        paramMap.put("itemId",itemId);
        return mybatisDaoContext.findAll(SQLID+"getFinReceiBillByContractId",FinReceiBillDetailEntity.class,paramMap);
    }
    /**
     * @author xiangb
     * @created 2018年2月8日10:44:56
     * @return
     */
    public List<FinReceiBillDetailEntity> selectOutTimeFinReceiBillDetail(){
        return mybatisDaoContext.findAll(SQLID+"selectOutTimeFinReceiBillDetail");
    }
}
