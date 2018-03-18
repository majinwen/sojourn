/**
 * @FileName: IntellectWaterMeterServiceProxy.java
 * @Package com.ziroom.zrp.service.trading.proxy
 * 
 * @author bushujie
 * @created 2018年1月22日 下午2:45:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.IntellectWaterMeterService;
import com.ziroom.zrp.service.trading.dto.waterwatt.IntellectWatermeterReadDto;
import com.ziroom.zrp.service.trading.entity.IntellectWatermeterReadVo;
import com.ziroom.zrp.service.trading.service.IntellectWaterMeterBillLogServiceImpl;
import com.ziroom.zrp.service.trading.service.IntellectWaterMeterReadServiceImpl;
import com.ziroom.zrp.trading.entity.IntellectWaterMeterBillLogEntity;
import com.ziroom.zrp.trading.entity.IntellectWatermeterReadEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Slf4j
@Component("trading.intellectWaterMeterServiceProxy")
public class IntellectWaterMeterServiceProxy implements IntellectWaterMeterService {

    @Resource(name="trading.intellectWatermeterReadServiceImpl")
    private IntellectWaterMeterReadServiceImpl intellectWaterMeterReadService;
	
	@Resource(name="trading.intellectWaterMeterBillLogServiceImpl")
	private IntellectWaterMeterBillLogServiceImpl intellectWaterMeterBillLogService;

    /**
     *
     * 查询最后一个定时抄水表记录
     *
     * @author bushujie
     * @created 2018年1月22日 下午2:44:17
     *
     * @param paramJson
     * @return
     */
    @Override
    public String getLastIntellectWatermeterReadByRoomId(String paramJson) {
        DataTransferObject dto=new DataTransferObject();
        IntellectWatermeterReadEntity inDto= JsonEntityTransform.json2Object(paramJson, IntellectWatermeterReadEntity.class);
        IntellectWatermeterReadEntity intellectWatermeterRead=intellectWaterMeterReadService.getLastIntellectWatermeterReadByRoomId(inDto);
        dto.putValue("obj", intellectWatermeterRead);
        return dto.toJsonString();
    }

    /**
     *
     * 查询应收账单生成明细记录根据应收账单fid
     *
     * @author bushujie
     * @created 2018年1月31日 下午2:47:08
     *
     * @param billFid
     * @return
     */
	@Override
	public String getIntellectWaterMeterBillLogByBillFid(String billFid) {
		DataTransferObject dto=new DataTransferObject();
		IntellectWaterMeterBillLogEntity intellectWaterMeterBillLog=intellectWaterMeterBillLogService.getIntellectWaterMeterBillLogBybillFid(billFid);
		dto.putValue("obj", intellectWaterMeterBillLog);
		return dto.toJsonString() ;
	}

    /**
     *
     * 分页查询定时任务的水表抄表记录
     *
     * @author zhangyl2
     * @created 2018年02月24日 15:42
     * @param
     * @return
     */
    @Override
    public String getIntellectWatermeterReadByPage(String paramJson) {
        LogUtil.info(log, "【getIntellectWatermeterReadByPage】入参={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        IntellectWatermeterReadDto intellectWatermeterReadDto = JsonEntityTransform.json2Object(paramJson, IntellectWatermeterReadDto.class);
        if (Check.NuNObjs(intellectWatermeterReadDto.getPage(),
                intellectWatermeterReadDto.getRows())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }

        PagingResult<IntellectWatermeterReadVo> pagingResult = intellectWaterMeterReadService.getIntellectWatermeterReadByPage(intellectWatermeterReadDto);
        dto.putValue("list", pagingResult.getRows());
        dto.putValue("total", pagingResult.getTotal());
        return dto.toJsonString();
    }


}
