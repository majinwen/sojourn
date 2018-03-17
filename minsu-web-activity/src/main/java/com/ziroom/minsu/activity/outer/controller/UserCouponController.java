/**
 * @FileName: UserCouponController.java
 * @Package com.ziroom.minsu.activity.outer.controller
 * 
 * @author yd
 * @created 2017年5月4日 上午10:07:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.outer.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.activity.service.CouponService;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/conpon")
@Controller
public class UserCouponController {

	

    @Resource(name = "api.couponService")
    private CouponService couponService;
    
	@Autowired
	private RedisOperations redisOperations;
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(UserCouponController.class);

	/**
	 * 
	 * uid邦定优惠卷礼包
	 * 说明：
	 * 入参　
	 * 活动组号: groupSn   用户uid: uid
	 * 1. 校验参数
	 * 2. 校验当前活动信息
	 * 3. 教养当前用户是否已经领取
	 * 4. 用户领取优惠卷返回
	 * @author yd
	 * @created 2017年5月4日 下午8:22:20
	 *
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("bindUserConpons")
	@ResponseBody
	public void bindUserConpuns(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String groupSn = request.getParameter("groupSn");
		String uid = request.getParameter("uid");
		String actSn = request.getParameter("actSn");

		LogUtil.info(LOGGER, "uid邦定优惠卷礼包:groupSn={},uid={},actSn={}", groupSn,uid,actSn);

		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getGroupAcnUid(groupSn,actSn,uid);
		try {
			String listJson = null;
			listJson = redisOperations.get(key);
			// 判断缓存是否存在
			if (!Check.NuNStrStrict(listJson)) {
				dto.setErrCode(CouponConst.COUPON_HAS.getCode());
				dto.setMsg(CouponConst.COUPON_HAS.getName());
				LogUtil.info(LOGGER,"【reids已存在，重复请求】groupSn={},uid={},listJson={}",groupSn,uid,listJson);
				response.getWriter().write(dto.toJsonString());
				return;
			}
			
			redisOperations.setex(key.toString(), RedisKeyConst.GROUP_ACTSN_UID_TIME, groupSn+uid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【海燕计划活动礼包领取失败】groupSn={},actSn={},uid={},redis错误,e:{}",groupSn,actSn,uid, e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
			response.getWriter().write(dto.toJsonString());
			return;
		}
		

		try {
			if(Check.NuNStr(groupSn)
					||Check.NuNStr(uid)){
				dto.setErrCode(CouponConst.COUPON_PAR_ERROR.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_ERROR.getName());
				LogUtil.info(LOGGER,"【bindUserConpuns 参数错误】groupSn={},uid={}",groupSn,uid);
				response.getWriter().write(dto.toJsonString());
				return;
			}
			
			dto = this.couponService.pullCouponByUid(uid, null, groupSn);
			
			try {
				redisOperations.del(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "【海燕计划活动礼包领取失败】groupSn={},actSn={},uid={},redis清楚错误,e:{}",groupSn,actSn,uid, e);
			}
			
			response.getWriter().write(dto.toJsonString());
		} catch (IOException e) {
			LogUtil.error(LOGGER, "【uid邦定优惠卷礼包异常】groupSn={},uid={},e={}", groupSn,uid,e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
			response.getWriter().write(dto.toJsonString());
			return;
		}
	}

}
