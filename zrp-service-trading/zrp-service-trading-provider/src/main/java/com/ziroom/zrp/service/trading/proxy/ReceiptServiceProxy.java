package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.ReceiptService;
import com.ziroom.zrp.service.trading.service.ReceiptServiceImpl;
import com.ziroom.zrp.trading.entity.FinReceiptRelEntity;
import com.ziroom.zrp.trading.entity.ReceiptEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
 * @Date Created in 2017年11月10日 09:31
 * @since 1.0
 */

@Component("trading.receiptServiceProxy")
public class ReceiptServiceProxy implements ReceiptService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptServiceProxy.class);

    @Resource(name = "trading.receiptServiceImpl")
    private ReceiptServiceImpl receiptServiceImpl;

    /**
     * @description: 根据收款单号批量更新收款单为已收款
     * @author: lusp
     * @date: 2017/11/10 上午 9:33
     * @params: paramJson
     * @return: String
     */
    @Override
    public String updateBatchByBillNum(String paramJson) {
        LogUtil.info(LOGGER, "【updateByBillNum】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            List<ReceiptEntity> tradingReceiptEntities = JsonEntityTransform.json2List(paramJson,ReceiptEntity.class);
            if (Check.NuNCollection(tradingReceiptEntities)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            int num = receiptServiceImpl.updateBatchByBillNum(tradingReceiptEntities);
            dto.putValue("num",num);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【updateByBillNum】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }


    /**
     * 保存收款单
     * @param paramJson json
     * @return
     * @author cuigh6
     * @Date 2017年11月11日
     *
     */
    public String saveBatchReceipt(String paramJson) {
        LogUtil.info(LOGGER, "【updateByBillNum】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();

        try {
            List<ReceiptEntity> receiptEntityList = JsonEntityTransform.json2List(paramJson, ReceiptEntity.class);
            if (Check.NuNCollection(receiptEntityList)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            int affect = receiptServiceImpl.saveBatchReceipt(receiptEntityList);
            dto.putValue("affect", affect);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【saveBatchReceipt】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("保存收款单失败");
        }
        return dto.toJsonString();
    }

    /**
     * @description: 保存收款单并返回fid（保存收款单主记录时使用）
     * @author: lusp
     * @date: 2017/12/1 下午 15:50
     * @params: receiptEntity
     * @return: String
     */
    public String saveReceiptAndReturnFid(String paramJson) {
        LogUtil.info(LOGGER, "【saveReceiptAndReturnFid】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ReceiptEntity receiptEntity = JsonEntityTransform.json2Entity(paramJson, ReceiptEntity.class);
            if (Check.NuNObj(receiptEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            String fid = receiptServiceImpl.saveReceiptAndReturnFid(receiptEntity);
            dto.putValue("fid", fid);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【saveReceiptAndReturnFid】paramJson={}, error:{}", paramJson, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("保存收款单失败");
        }
        return dto.toJsonString();
    }

    @Override
    public String saveBatchReceiptAndRel(String receipts, String receiptRels) {
        LogUtil.info(LOGGER, "【saveBatchReceiptAndRel】入参receipts={}，receiptRels={}", receipts,receiptRels);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(receipts) || Check.NuNStr(receiptRels)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        List<ReceiptEntity> receiptEntities = JsonEntityTransform.json2List(receipts, ReceiptEntity.class);
        List<FinReceiptRelEntity> finReceiptRelEntities = JsonEntityTransform.json2List(receiptRels, FinReceiptRelEntity.class);

        int count = receiptServiceImpl.saveBatchReceiptAndRel(receiptEntities,finReceiptRelEntities);
        dto.putValue("count",count);
        LogUtil.info(LOGGER,"【saveBatchReceiptAndRel】保存结果={}",dto.toJsonString());
        return dto.toJsonString();
    }

    @Override
    public String getReceiptByBillNum(String receiptBillNum) {
        LogUtil.info(LOGGER, "【selectReceiptByBillNum】入参 receiptBillNum:{}", receiptBillNum);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStrStrict(receiptBillNum)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            ReceiptEntity receiptEntity = receiptServiceImpl.getReceiptByBillNum(receiptBillNum);
            dto.putValue("receiptEntity", receiptEntity);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【selectReceiptByBillNum】receiptBillNum={}, error:{}", receiptBillNum, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("根据收款单编号查询收款单信息失败");
        }
        return dto.toJsonString();
    }

    @Override
    public String getReceivableBillNumsByBillNum(String receiptBillNum) {
        LogUtil.info(LOGGER, "【getReceivableBillNumByBillNum】入参 receiptBillNum:{}", receiptBillNum);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStrStrict(receiptBillNum)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            List<String> receivableBillNums = receiptServiceImpl.getReceivableBillNumsByBillNum(receiptBillNum);
            dto.putValue("receivableBillNums", receivableBillNums);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【getReceivableBillNumByBillNum】receiptBillNum={}, error:{}", receiptBillNum, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("根据收款单编号查询对应的应收账单集合失败");
        }
        return dto.toJsonString();
    }

    @Override
    public String deleteReceiptAndRel(String receiptBillNum) {
        LogUtil.info(LOGGER, "【deleteReceiptAndRel】入参 receiptBillNum:{}", receiptBillNum);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStrStrict(receiptBillNum)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            receiptServiceImpl.deleteReceiptAndRel(receiptBillNum);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【deleteReceiptAndRel】receiptBillNum={}, error:{}", receiptBillNum, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("根据收款单编号逻辑删除收款单记录以及对应的应收账单记录失败");
        }
        return dto.toJsonString();
    }
}
