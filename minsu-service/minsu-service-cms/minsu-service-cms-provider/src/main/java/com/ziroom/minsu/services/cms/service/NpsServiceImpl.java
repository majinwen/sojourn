package com.ziroom.minsu.services.cms.service;

import com.ziroom.minsu.entity.cms.NpsAttendEntiy;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.dao.NpsAttendDao;
import com.ziroom.minsu.services.cms.dao.NpsDao;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;
import com.ziroom.minsu.services.cms.dto.NpsGetRequest;
import com.ziroom.minsu.services.cms.dto.NpsQuantumVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
 * @author lishaochuan on 2016/11/11 15:03
 * @version 1.0
 * @since 1.0
 */
@Service("cms.npsServiceImpl")
public class NpsServiceImpl {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InviteServiceImpl.class);

    @Resource(name = "cms.npsDao")
    private NpsDao npsDao;

    @Resource(name = "cms.npsAttendDao")
    private NpsAttendDao npsAttendDao;


    /**
     * 获取nps
     *
     * @param
     * @return
     */
    public NpsEntiy getNps() {
        return npsDao.getNps();
    }

    /**
     * 修改nps状态
     * @param nps
     */
    public void updateNps(NpsEntiy nps){
        npsDao.updateNps(nps);
    }

    /**
     * 获取nps参与信息
     *
     * @param npsGetRequest
     * @return
     */
    public NpsAttendEntiy getByUidType(NpsGetRequest npsGetRequest) {
        return npsAttendDao.getByUidType(npsGetRequest);
    }





    /**
     * 保存当前nps的参与情况
     * @author afi
     * @param npsAttendEntiy
     * @return
     */
    public int  saveNpsAttend(NpsAttendEntiy npsAttendEntiy) {
        return npsAttendDao.saveNpsAttend(npsAttendEntiy);
    }



    /**
     * 分时间段计算NPS值
     *
     * @param npsEntiy
     * @return
     */
    public NpsQuantumVo getCalculateNPS(NpsGetCondiRequest npsGetCondiRequest) {
        return npsDao.getCalculateNPS(npsGetCondiRequest);
    }

    /**
     * @Description: 获取NPS name code集合
     * @Author: lusp
     * @Date: 2017/7/13 19:01
     * @Params:
     */
    public List<NpsEntiy> npsNameList() {
        return npsDao.getNpsNameList();
    }


}
