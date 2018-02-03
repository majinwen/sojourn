package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.entity.cms.UserActEmptyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
@Repository("cms.userActEmptyDao")
public class UserActEmptyDao {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UserActEmptyDao.class);

    private String SQLID = "cms.userActEmptyDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;



    /**
     * 保存
     * @param userActEmptyEntity
     * @return
     */
    public int saveUserEmpty(UserActEmptyEntity userActEmptyEntity) {
        if (Check.NuNStr(userActEmptyEntity.getFid())){
            userActEmptyEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "saveUserEmpty", userActEmptyEntity);
    }


    /**
     * 验证当前活动是否领取过
     * @author afi
     * @param mobile
     * @param actSn
     * @return
     */
    public Long countByMobileAndActSn(String mobile,String actSn) {
        Map<String,Object> par = new HashMap<>();
        par.put("customerMobile",mobile);
        par.put("actSn",actSn);
        return mybatisDaoContext.findOne(SQLID + "countByMobileAndActSn",Long.class, par);
    }



    /**
     * 验证当前活动是否领取过
     * @author afi
     * @param mobile
     * @param groupSn
     * @return
     */
    public Long countByMobileAndGroupSn(String mobile,String groupSn) {
        Map<String,Object> par = new HashMap<>();
        par.put("customerMobile",mobile);
        par.put("groupSn",groupSn);
        return mybatisDaoContext.findOne(SQLID + "countByMobileAndGroupSn",Long.class, par);
    }


}
