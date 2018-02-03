package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.cms.GroupActRelEntity;
import com.ziroom.minsu.entity.cms.GroupEntity;
import com.ziroom.minsu.entity.cms.GroupHouseRelEntity;
import com.ziroom.minsu.entity.cms.GroupUserRelEntity;
import com.ziroom.minsu.services.cms.dao.GroupActRelDao;
import com.ziroom.minsu.services.cms.dao.GroupDao;
import com.ziroom.minsu.services.cms.dao.GroupHouseRelDao;
import com.ziroom.minsu.services.cms.dao.GroupUserRelDao;
import com.ziroom.minsu.services.cms.dto.ActivityGroupRequest;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>组相关管理</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月11日 10:34
 * @since 1.0
 */
@Service("cms.groupServiceImpl")
public class GroupServiceImpl {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Resource(name = "cms.groupDao")
    private GroupDao groupDao;

    @Resource(name = "cms.groupHouseRelDao")
    private GroupHouseRelDao groupHouseRelDao;

    @Resource(name = "cms.groupUserRelDao")
    private GroupUserRelDao groupUserRelDao;

    @Resource(name = "cms.groupActRelDao")
    private GroupActRelDao groupActRelDao;

    /**
     * 保存组
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月11日 10:40:04
     */
    public int saveGroup(GroupEntity groupEntity) {
        return groupDao.save(groupEntity);
    }

    /**
     * 查询组列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月12日 16:35:26
     */
    public PagingResult<GroupEntity> listGroupByType(ActivityGroupRequest activityGroupRequest) {
        return groupDao.listGroup(activityGroupRequest);
    }

    /**
     * 用户关系列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月18日 18:20:14
     */
    public List<GroupUserRelEntity> listUserRelByUid(String groupFid, String uid) {
        return groupUserRelDao.listUserRelByUid(groupFid, uid);
    }

    /**
     * 保存用户关系
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月11日 10:41:17
     */
    public int saveUserRel(GroupUserRelEntity groupUserRelEntity) {
        return groupUserRelDao.save(groupUserRelEntity);
    }

    /**
     * 查询用户关系分页
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月17日 15:25:16
     */
    public PagingResult<GroupUserRelEntity> listUserRelByPage(GroupRequest groupRequest) {
        return groupUserRelDao.listUserRelByPage(groupRequest);
    }

    /**
     * 批量删除
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月17日 18:00:43
     */
    public int deleteUserRelBatch(List<String> fids) {
        return groupUserRelDao.deleteUserRelBatch(fids);
    }


    /**
     * 保存房源关系
     *
     * @param groupHouseRelEntity
     * @return
     */
    public int saveHouseRel(GroupHouseRelEntity groupHouseRelEntity) {
        return groupHouseRelDao.save(groupHouseRelEntity);
    }

    /**
      * @description: 批量保存房源组
      * @author: lusp
      * @date: 2017/10/20 上午 11:40
      * @params: groupHouseRelEntities
      * @return:
      */
    public void addHouseRelBatch(List<GroupHouseRelEntity> groupHouseRelEntities){
        for (GroupHouseRelEntity groupHouseRelEntity:groupHouseRelEntities){
            groupHouseRelDao.save(groupHouseRelEntity);
        }
    }


    /**
     * 查询活动 与组关系列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年10月13日 14:12:49
     */
    public List<GroupActRelEntity> listGroupActRelByActSn(String actSn) {
        return groupActRelDao.listGroupActRelByActSn(actSn);
    }

    /**
     * @description: 根据分组名称、产品线、组类型去查询组的数量
     * @author: lusp
     * @date: 2017/10/17 下午 20:27
     * @params: groupEntity
     * @return: Long
     */
    public Long selectCountByGroupName(GroupEntity groupEntity){
        return groupDao.selectCountByGroupName(groupEntity);
    }

    /**
      * @description: 分页查询房源组关系
      * @author: lusp
      * @date: 2017/10/18 下午 16:22
      * @params: groupRequest
      * @return: PagingResult<GroupUserRelEntity>
      */
    public PagingResult<GroupHouseRelEntity> listHouseRelByFidForPage(GroupRequest groupRequest) {
        return groupHouseRelDao.listHouseRelByFidForPage(groupRequest);
    }

    /**
     * @description: 查询和当前房源组有交集的房源组数量
     * @author: lusp
     * @date: 2017/10/20 下午 13:59
     * @params: groupHouseRelEntity
     * @return: Long
     */
    public Long selectExistCount(GroupHouseRelEntity groupHouseRelEntity){
        return groupHouseRelDao.selectExistCount(groupHouseRelEntity);
    }

    /**
      * @description: 根据id删除房源组成员
      * @author: lusp
      * @date: 2017/12/15 下午 14:27
      * @params: groupHouseRelEntity
      * @return: int
      */
    public int deleteHouseRel(GroupHouseRelEntity groupHouseRelEntity){
        return groupHouseRelDao.deleteHouseRel(groupHouseRelEntity);
    }


    /**
     * 计算活动组下面得人数
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年12月15日 17:42:18
     */
    public long countUserByGroupId(String groupFid) {
        return groupUserRelDao.countUserByGroupId(groupFid);
    }

    /**
     * 查找活动
     *
     * @param
     * @return
     * @author jixd
     * @created 2018年01月26日 13:54:35
     */
    public GroupEntity findGroupByFid(String groupFid) {
        return groupDao.findGroupByFid(groupFid);
    }
}
