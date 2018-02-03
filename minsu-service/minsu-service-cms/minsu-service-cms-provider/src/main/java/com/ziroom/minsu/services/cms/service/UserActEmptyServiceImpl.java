package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.UserActEmptyEntity;
import com.ziroom.minsu.services.cms.dao.UserActEmptyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/10.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.userActEmptyServiceImpl")
public class UserActEmptyServiceImpl {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserActEmptyServiceImpl.class);

    @Resource(name = "cms.userActEmptyDao")
    private UserActEmptyDao userActEmptyDao;


    /**
     * 保存
     * @param userActEmptyEntity
     * @return
     */
    public int saveUserEmpty(UserActEmptyEntity userActEmptyEntity) {
        if (Check.NuNObj(userActEmptyEntity)){
            throw new BusinessException("保存的对象为空");
        }
        if (Check.NuNStr(userActEmptyEntity.getFid())){
            userActEmptyEntity.setFid(UUIDGenerator.hexUUID());
        }
        return userActEmptyDao.saveUserEmpty(userActEmptyEntity);
    }


    /**
     * 验证当前活动是否领取过
     * @author afi
     * @param mobile
     * @param actSn
     * @return
     */
    public Long countByMobileAndActSn(String mobile,String actSn) {
        return userActEmptyDao.countByMobileAndActSn(mobile,actSn);
    }

    /**
     * 验证当前活动是否领取过
     * @author afi
     * @param mobile
     * @param groupSn
     * @return
     */
    public Long countByMobileAndGroupSn(String mobile,String groupSn) {
        return userActEmptyDao.countByMobileAndGroupSn(mobile,groupSn);
    }

}
