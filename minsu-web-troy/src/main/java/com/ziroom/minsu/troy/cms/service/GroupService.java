package com.ziroom.minsu.troy.cms.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityGroupService;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>组修改</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
@Service("api.groupService")
public class GroupService {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    @Resource(name="cms.activityGroupService")
    private ActivityGroupService activityGroupService;

    /**
     * 分页获取组信息
     * @author afi
     * @created 2016年9月15日
     * @param
     */
    public PagingResult<ActivityGroupEntity> getGroupByPage(GroupRequest request){
        PagingResult<ActivityGroupEntity> pagingResult = new PagingResult<>(0L,new ArrayList<ActivityGroupEntity>());
        try{
            String resultJson =  activityGroupService.getGroupByPage(JsonEntityTransform.Object2Json(request));
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (DataTransferObject.SUCCESS == resultDto.getCode()){
                pagingResult.setRows(resultDto.parseData("list", new TypeReference<List<ActivityGroupEntity>>() {}));
                pagingResult.setTotal(resultDto.parseData("total", new TypeReference<Long>() {}));
            }

        }catch(Exception ex){
            LogUtil.error(LOGGER, "error:{}", ex);
        }
        return pagingResult;
    }

    /**
     * 获取当前的所有组列表
     * @author afi
     * @created 2016年9月15日
     * @param
     */
    public List<ActivityGroupEntity> getAllGroup(){
        List<ActivityGroupEntity> groupList = null;
        try{
            String resultJson =  activityGroupService.getAllGroup();
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            groupList = resultDto.parseData("list", new TypeReference<List<ActivityGroupEntity>>() {});
        }catch(Exception ex){
            LogUtil.error(LOGGER, "error:{}", ex);
            groupList = new ArrayList<>();
        }
        return groupList;
    }


    /**
     * 获取当前的组
     * @author afi
     * @created 2016年9月15日
     * @param
     */
    public ActivityGroupEntity getGroupBySN(String groupSn){
        ActivityGroupEntity group = null;
        try{
            String resultJson =  activityGroupService.getGroupBySN(groupSn);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            group = resultDto.parseData("obj", new TypeReference<ActivityGroupEntity>() {});
        }catch(Exception ex){
            LogUtil.error(LOGGER, "error:{}", ex);
        }
        return group;
    }

}
