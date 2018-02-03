package com.ziroom.minsu.services.cms.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.services.cms.dao.ActivityGroupDao;
import com.ziroom.minsu.services.cms.dto.GroupRequest;

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
@Service("cms.activityGroupServiceImpl")
public class ActivityGroupServiceImpl {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityGroupServiceImpl.class);

    @Resource(name = "cms.activityGroupDao")
    private ActivityGroupDao activityGroupDao;
    

    /**
     * 分页查询活动信息
     * @author afi
     * @param request
     * @return
     */
    public PagingResult<ActivityGroupEntity> getGroupByPage(GroupRequest request){
        return activityGroupDao.getGroupByPage(request);
    }

    /**
     * 获取当前的所有的组
     * @author afi
     * @return
     */
    public List<ActivityGroupEntity> getAllGroup(){
        return activityGroupDao.getAllGroup();
    }

    /**
     * 通过组号获取但前的组
     * @author afi
     * @return
     */
    public ActivityGroupEntity getGroupBySN(String groupSn){
        return activityGroupDao.getGroupBySN(groupSn);
    }

	/**
	 * 新增活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param activityGroupEntity
	 * @return
	 */
	public int insertActivityGroupEntity(ActivityGroupEntity activityGroupEntity) {
		activityGroupEntity.setFid(UUIDGenerator.hexUUID());
		return activityGroupDao.saveActivityGroup(activityGroupEntity);
	}

	/**
	 * 修改活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param activityGroupEntity
	 * @return
	 */
	public int updateActivityGroupEntityByFid(ActivityGroupEntity activityGroupEntity) {
		return activityGroupDao.updateActivityGroupEntityByFid(activityGroupEntity);
	}
}
