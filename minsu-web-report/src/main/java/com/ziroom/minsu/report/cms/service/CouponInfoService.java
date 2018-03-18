package com.ziroom.minsu.report.cms.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.cms.dao.CouponInfoDao;
import com.ziroom.minsu.report.cms.dto.CouponInfoRequest;
import com.ziroom.minsu.report.cms.vo.CouponInfoVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/15 11:09
 * @version 1.0
 * @since 1.0
 */
@Service("report.couponInfoService")
public class CouponInfoService  implements ReportService<CouponInfoVo,CouponInfoRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponInfoService.class);

    @Resource(name = "report.couponInfoDao")
    private CouponInfoDao couponInfoDao;


    /**
     * 获取优惠券信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/15 11:19
     */ 
	public PagingResult<CouponInfoVo> getPageInfo(CouponInfoRequest par) {
		PagingResult<CouponInfoVo> couponInfo = couponInfoDao.getCouponInfo(par);
        for (CouponInfoVo couponInfoVo : couponInfo.getRows()) {
            //翻译优惠券状态
            CouponStatusEnum couponStatusEnum = CouponStatusEnum.getByCode(ValueUtil.getintValue(couponInfoVo.getCouponStatus()));
            if(!Check.NuNObj(couponStatusEnum)){
                couponInfoVo.setCouponStatus(couponStatusEnum.getName());
            }
            //翻译优惠券类型
            ActTypeEnum actTypeEnum = ActTypeEnum.getByCode(ValueUtil.getintValue(couponInfoVo.getActType()));
            if(!Check.NuNObj(actTypeEnum)){
                couponInfoVo.setActType(actTypeEnum.getName());
                if(actTypeEnum.equals(ActTypeEnum.CACHE)){
                    couponInfoVo.setActCut(DataFormat.formatHundredPrice(ValueUtil.getintValue(couponInfoVo.getActCut())));
                }else if(actTypeEnum.equals(ActTypeEnum.CUT)){
                    couponInfoVo.setActCut(DataFormat.formatHundredPrice(ValueUtil.getintValue(couponInfoVo.getActCut())));
                }else if(actTypeEnum.equals(ActTypeEnum.FREE)){
                }
            }

        }
        return couponInfoDao.getCouponInfo(par);
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(CouponInfoRequest par) {
		// TODO Auto-generated method stub
		return null;
	}


}
