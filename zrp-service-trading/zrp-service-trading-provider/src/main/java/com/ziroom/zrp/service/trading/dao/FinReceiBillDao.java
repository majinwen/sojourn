package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.service.trading.dto.PaymentBillsDto;
import com.ziroom.zrp.trading.entity.FinReceiBillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>应收账单dao</p>
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
@Repository("trading.finReceiBillDao")
public class FinReceiBillDao {

    private String SQLID = "trading.finReceiBillDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * @description: 保存应收账单
     * @author: lusp
     * @date: 2017/10/13 17:44
     * @params: finReceiBillEntity
     * @return: int
     */
    public int saveFinReceiBill(FinReceiBillEntity finReceiBillEntity){
        if(Check.NuNStr(finReceiBillEntity.getFid())){
            finReceiBillEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insertSelective",finReceiBillEntity);
    }

    /**
     * 更新根据fid
     * @author jixd
     * @created 2017年11月30日 10:44:41
     * @param
     * @return
     */
    public int updateByFid(FinReceiBillEntity finReceiBillEntity){
        return mybatisDaoContext.update(SQLID +"updateByFid", finReceiBillEntity);
    }

    /**
      * @description: 获取应收账单序列号
      * @author: lusp
      * @date: 2017/10/13 下午 20:02
      * @params:
      * @return: int
      */
    public int selectPayBillSeq(){
        return mybatisDaoContext.findOne(SQLID + "selectPayBillSeq",int.class);
    }


    public int deleteBySurrenderId(String surrenderId) {
        Map map = new HashMap();
        map.put("surrenderId", surrenderId);
        return mybatisDaoContext.update(SQLID + "deleteBySurrenderId", map);
    }

    /**
     * 查询指定日期要收款的合同号
     * @param paramMap
     * @return
     * @author cuigh6
     * @Date 2017年11月14日
     */
    public List<String> getReceiptContractByDate(Map<String, Object> paramMap) {
        return this.mybatisDaoContext.findAll(SQLID + "getReceiptContractByDate", String.class, paramMap);
    }

    /**
      * @description: 根据条件查出需要收款的合同号，供去财务查询收款单列表使用
      * @author: lusp
      * @date: 2017/11/21 下午 15:35
      * @params: paymentBillsDto
      * @return: List<String>
      */
    public List<String> getZRAReceiptContractCode(PaymentBillsDto paymentBillsDto) {
        if(Check.NuNObj(paymentBillsDto.getStartDate())){
            paymentBillsDto.setStartDate(new Date());
        }
        if(Check.NuNObj(paymentBillsDto.getEndDate())){
            paymentBillsDto.setEndDate(DateUtil.getTime(31));
        }
        return this.mybatisDaoContext.findAll(SQLID + "getZRAReceiptContractCode", String.class, paymentBillsDto);
    }

    /**
     * 删除应收账单
     * @author jixd
     * @created 2017年11月15日 19:27:57
     * @param
     * @return
     */
    public int deleteReceiptBillByContractId(String contractId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractId);
        return mybatisDaoContext.update(SQLID + "deleteReceiptBillByContractId",paramMap);
    }

    /**
     * 查询应收账单
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<FinReceiBillEntity> selectByContractId(String contractId) {
        return mybatisDaoContext.findAll(SQLID + "selectByContractId", FinReceiBillEntity.class, contractId);
    }

    /**
     * 根据应收账单id查询
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public FinReceiBillEntity selectByFid(String fid) {
        return mybatisDaoContext.findOneSlave(SQLID + "selectByFid", FinReceiBillEntity.class, fid);
    }

    /**
     * 根据收款单id作废收款单，定时任务用
     * @author xiangb
     * @return
     */
    public int deleteReceiptBillByFid(List<String> fids){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("fids",fids);
        return mybatisDaoContext.update(SQLID + "deleteReceiptBillByFid", paramMap);
    }
    
    /**
     * 根据应收账单编号查询
     * @param billNum
     * @return
     */
    public FinReceiBillEntity selectByBillNum(String billNum){
        return mybatisDaoContext.findOneSlave(SQLID+"selectByBillNum",FinReceiBillEntity.class,billNum);
    }
}
