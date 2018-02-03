package com.ziroom.minsu.services.basedata.proxy;

import javax.annotation.Resource;

import com.ziroom.minsu.services.common.constant.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.OpLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.OpLogService;
import com.ziroom.minsu.services.basedata.dto.OpLogRequest;
import com.ziroom.minsu.services.basedata.entity.OpLogVo;
import com.ziroom.minsu.services.basedata.service.OpLogServiceImpl;

/**
 * <p>操作记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Component("basedata.opLogProxy")
public class OpLogProxy implements OpLogService{



    private static final Logger LOGGER = LoggerFactory.getLogger(OpLogProxy.class);


    @Resource(name = "basedata.messageSource")
    private MessageSource messageSource;


    @Resource(name = "basedata.opLogService")
    private OpLogServiceImpl opLogService;

    /**
     * 记录log
     * @param log
     */
    @Override
    public String saveOpLogInfo(OpLogEntity log) {
        DataTransferObject dto = new DataTransferObject();
        //非空校验
        if(Check.NuNObj(log)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        try{
            //保存操作
            opLogService.saveOpLogInfo(log);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }

    /**
     *
     * @param paramJson
     * @return
     */
	@Override
	public String findOpLogList(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			OpLogRequest opLogRequest = JsonEntityTransform.json2Object(paramJson, OpLogRequest.class);
			// 条件查询后台用户
			PagingResult<OpLogVo> pr = opLogService.findMenuResList(opLogRequest);
			dto.putValue("list", pr.getRows());
			dto.putValue("total", pr.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));

		}
		return dto.toJsonString();
	}
}
