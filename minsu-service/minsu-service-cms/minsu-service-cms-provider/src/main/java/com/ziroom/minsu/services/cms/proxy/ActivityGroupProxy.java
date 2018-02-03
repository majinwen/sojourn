package com.ziroom.minsu.services.cms.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityGroupService;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import com.ziroom.minsu.services.cms.service.ActivityGroupServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/10.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityGroupProxy")
public class ActivityGroupProxy implements ActivityGroupService {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityGroupProxy.class);


    @Resource(name = "cms.messageSource")
    private MessageSource messageSource;

    @Resource(name = "cms.activityGroupServiceImpl")
    private ActivityGroupServiceImpl activityGroupService;

    /**
     * 获取当前的活动组
     * @author afi
     * @author afi
     * @return
     */
    @Override
    public String getGroupByPage(String parJson) {
        DataTransferObject dto = new DataTransferObject();
        GroupRequest request = JsonEntityTransform.json2Object(parJson, GroupRequest.class);

        if(Check.NuNObj(request)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("参数异常");

            return dto.toJsonString();
        }
        PagingResult<ActivityGroupEntity> pagingResult = activityGroupService.getGroupByPage(request);
        dto.putValue("total", pagingResult.getTotal());
        dto.putValue("list", pagingResult.getRows());
        return dto.toJsonString();
    }



    /**
     * 获取当前的活动组
     * @author afi
     * @return
     */
    @Override
    public String getAllGroup() {
        DataTransferObject dto = new DataTransferObject();
        List<ActivityGroupEntity> list = this.activityGroupService.getAllGroup();
        dto.putValue("list", list);
        return dto.toJsonString();
    }

    /**
     * 通过组号获取但前的组
     * @author afi
     * @return
     */
    public String getGroupBySN(String groupSn){
        DataTransferObject dto = new DataTransferObject();
        ActivityGroupEntity obj = this.activityGroupService.getGroupBySN(groupSn);
        dto.putValue("obj", obj);
        return dto.toJsonString();
    }

	/**
	 * 新增活动组信息
	 */
	@Override
	public String insertActivityGroupEntity(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		ActivityGroupEntity activityGroupEntity = JsonEntityTransform.json2Object(paramJson, ActivityGroupEntity.class);
		DataTransferObject dto = new DataTransferObject();
		
		if (Check.NuNObj(activityGroupEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		if (!Check.NuNObj(activityGroupEntity.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("逻辑fid非空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = activityGroupService.insertActivityGroupEntity(activityGroupEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "insertActivityGroupEntity error:{},paramJson={}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		}
		return dto.toJsonString();
	}

	/**
	 * 修改活动组信息
	 */
	@Override
	public String updateActivityGroupEntity(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		ActivityGroupEntity activityGroupEntity = JsonEntityTransform.json2Object(paramJson, ActivityGroupEntity.class);
		DataTransferObject dto = new DataTransferObject();
		
		if (Check.NuNObj(activityGroupEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		if (Check.NuNObj(activityGroupEntity.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("逻辑fid为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = activityGroupService.updateActivityGroupEntityByFid(activityGroupEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updateActivityGroupEntity error:{},paramJson={}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		}
		return dto.toJsonString();
	}
}
