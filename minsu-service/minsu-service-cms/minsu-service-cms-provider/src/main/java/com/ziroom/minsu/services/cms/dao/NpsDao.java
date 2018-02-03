package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;
import com.ziroom.minsu.services.cms.dto.NpsQuantumVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
 * @author lishaochuan on 2016/11/11 15:00
 * @version 1.0
 * @since 1.0
 */
@Repository("cms.npsDao")
public class NpsDao {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(NpsDao.class);

    private String SQLID = "cms.npsDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取nps
     * @param
     * @return
     */
    public NpsEntiy getNps() {
        return mybatisDaoContext.findOne(SQLID + "getNps", NpsEntiy.class);
    }

    /**
     * 修改nps状态
     * @param nps
     */
    public void updateNps(NpsEntiy nps){
        mybatisDaoContext.update(SQLID + "updateNps",nps);
    }

    /**
     * @Description: 分时间段计算NPS值
     * @Author: lusp
     * @Date: 2017/7/11 14:42
     * @Params: npsEntiy
     */
    public NpsQuantumVo getCalculateNPS(NpsGetCondiRequest npsGetCondiRequest) {
        return mybatisDaoContext.findOne(SQLID + "getCalculateNPS",NpsQuantumVo.class, npsGetCondiRequest);
    }

    /**
     * @Description: 获取NPS name code集合
     * @Author: lusp
     * @Date: 2017/7/13 19:01
     * @Params:
     */
    public List<NpsEntiy> getNpsNameList() {
        return mybatisDaoContext.findAll(SQLID + "getNpsNameList",NpsEntiy.class);
    }

}
