package com.ziroom.minsu.activity.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.activity.common.utils.ActHelperUtils;
import com.ziroom.minsu.activity.constant.RandomEnum;
import com.ziroom.minsu.activity.enums.ActivityCodeDicEnum;
import com.ziroom.minsu.activity.service.CouponService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.api.inner.InviteService;
import com.ziroom.minsu.services.cms.api.inner.MobileCouponService;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.cms.constant.CouponConst;
import com.ziroom.minsu.services.cms.constant.InviterCreateOrderConst;
import com.ziroom.minsu.services.cms.dto.InviteCouponRequest;
import com.ziroom.minsu.services.cms.dto.InviteStateUidRequest;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.SentinelJedisUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.valenum.cms.InviteSourceEnum;
import com.ziroom.minsu.valenum.cms.InviteStatusEnum;
import com.ziroom.minsu.valenum.cms.UserInviteStatesEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>优惠券相关的操作</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/9.
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/coupon")
@Controller
public class CouponController {
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(CouponController.class);



    @Resource(name = "api.couponService")
    private CouponService couponService;

    @Resource(name="sentinelJedisClient")
    private SentinelJedisUtil sentinelJedisClient;

    @Autowired
    private RedisOperations redisOperations;
    /*private final CountDownLatch downLatch = new CountDownLatch(1);
    private final ExecutorService service = Executors.newCachedThreadPool();*/

    @Resource(name = "cms.activityService")
    private ActivityService activityService;

    @Resource(name = "cms.inviteService")
    private InviteService inviteService;

    @Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
    private  String CUSTOMER_DETAIL_URL;

    @Value("#{'${detail_big_pic}'.trim()}")
    private String detail_big_pic;

    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;
    
	@Value("#{'${JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN}'.trim()}")
	private String JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN;

	@Value("#{'${JUMP_TO_MINSU_HOME_PAGE_URL}'.trim()}")
	private String JUMP_TO_MINSU_HOME_PAGE_URL;
	
	@Value("#{'${INVITEE_CAN_WIN_COUPON}'.trim()}")
	private String INVITEE_CAN_WIN_COUPON;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

    @Resource(name = "cms.mobileCouponService")
    private MobileCouponService mobileCouponService;


    /**
     * 获取当前时间
     * @param request
     * @throws IOException
     */
    @RequestMapping("time")
    public @ResponseBody
    void time(HttpServletRequest request,HttpServletResponse response) throws IOException{
        DataTransferObject dto = new DataTransferObject();
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
        dto.putValue("time",System.currentTimeMillis());
        response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    }

    /**
     * 领取优惠券
     * @param response
     * @param mobile
     * @param groupSn
     * @param request
     * @throws IOException
     */
    @RequestMapping("pullGroupCoupon")
    public @ResponseBody
    void pullGroupCoupon(HttpServletResponse response, String mobile, String groupSn,String code, HttpServletRequest request) throws IOException{
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "领取组优惠券 mobile:{}，vcode:{} group:{}",mobile,code,groupSn);
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
        //领取优惠券
        dto =  couponService.pullCoupon(mobile,code,null,groupSn);
        //返回验证码
        response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    }



    /**
     * 领取优惠券
     * @param response
     * @param mobile
     * @param actSn
     * @param request
     * @throws IOException
     */
    @RequestMapping("pullActCoupon")
    public @ResponseBody
    void pullActCoupon(HttpServletResponse response, String mobile, String actSn,String code, HttpServletRequest request) throws IOException{
        LogUtil.info(LOGGER, "领取活动优惠券 mobile:{}，vcode:{} actSn:{}",mobile,code,actSn);
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
        //领取优惠券
        DataTransferObject dto=  couponService.pullCoupon(mobile,code,actSn,null);
        //返回验证码
        response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    }


    /**
     * 领取随机活动
     * @param response
     * @param mobile
     * @param groupSn
     * @param request
     * @throws IOException
     */
    @RequestMapping("pullEmptyGroup")
    public @ResponseBody
    void pullEmptyGroup(HttpServletResponse response, String mobile, String groupSn,String code, HttpServletRequest request) throws IOException{
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "领取随机活动优惠券 mobile:{}，vcode:{} groupSn:{}",mobile,code,groupSn);
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
        if(Check.NuNStr(mobile)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("领取电话为空");
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return ;
        }
        if (Check.NuNStr(groupSn)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("组为空");
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return ;
        }
        //校验是否领取过
        couponService.checkEmptyGroup(dto,mobile,groupSn);
        if (dto.getCode() != DataTransferObject.SUCCESS){
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return ;
        }
        RandomEnum  random = RandomEnum.getByCode(groupSn);
        if (Check.NuNObj(random)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("未支持的组code");
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return ;
        }
        //是否能随机领取
        boolean pullFlag = random.checkRandomOk();
        if (!pullFlag){
            //走轮空的逻辑
            DataTransferObject  emptyDto =couponService.saveEmptyGroup(mobile,groupSn);
            if (emptyDto.getCode() != DataTransferObject.SUCCESS){
                dto.setErrCode(emptyDto.getCode());
                dto.setMsg(emptyDto.getMsg());
                response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
                return ;
            }
            dto.setErrCode(CouponConst.COUPON_NO_SMOKE.getCode());
            dto.setMsg(CouponConst.COUPON_NO_SMOKE.getName());
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return ;
        }

        //走领券的逻辑
        DataTransferObject dtoPull =couponService.pullCouponOnly(mobile,null,groupSn);

        response.getWriter().write(callBackName + "("+dtoPull.toJsonString()+")");
        return ;
    }

    /**
     * 不同组互斥，只能选一个组，如果其中的组已经领取过 直接返回已领取
     * 有随机概率的活动 相对随机，可以控制的（100次抽取中 概率完全一致）
     * @param response
     * @param mobile
     * @param code
     * @param request
     * @throws IOException
     */
    @RequestMapping("pullBagGroupCoupon")
    public @ResponseBody
    void pullBagGroupCoupon(HttpServletResponse response, String mobile,String code,String groupCode,HttpServletRequest request) throws IOException{
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "pullBagGroupCoupon入参 mobile:{}，vcode:{} groupCode:{} ",mobile,code,groupCode);
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
        //先校验组中的活动是否被领取，如果组中的活动存在直接返回已领取
        dto = couponService.checkCodeAndParam(mobile, code, null, groupCode);
        if (dto.getCode() == DataTransferObject.ERROR){
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return;
        }

        ActivityCodeDicEnum dicEnum = ActivityCodeDicEnum.getByCode(groupCode);
        if (Check.NuNObj(dicEnum)){
            dto.setErrCode(CouponConst.COUPON_ACT_NO.getCode());
            dto.setMsg(CouponConst.COUPON_ACT_NO.getName());
            LogUtil.info(LOGGER,"pullBagGroupCoupon 活动不存在");
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return;
        }
        
      
        List<String> groupSnList = new ArrayList<>();
        Map<String, Integer> actRateMap = dicEnum.getActRateMap();
        for (String key:actRateMap.keySet()){
            groupSnList.add(key);
        }
        couponService.checkGroupSns(dto,mobile,groupSnList);
      
        if (dto.getCode() == DataTransferObject.SUCCESS){
        	
        	  if(dicEnum.getCode().equals(ActivityCodeDicEnum.DUANWU2017.getCode())){
        		dto = getFixedGroupCoupon(dto, mobile, code, groupCode);
              	response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
              	return;
              }
            //校验都通过，先领取随机一个组码
            String randomCode = randomCode(groupCode);
            if (Check.NuNStr(randomCode)){
                dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
                dto.setMsg(CouponConst.COUPON_ERROR.getName());
                LogUtil.info(LOGGER,"pullBagGroupCoupon 领取失败，随机码为空");
                response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
                return;
            }
            //领取优惠券
            dto =  couponService.pullCouponOnly(mobile,null,randomCode);
            LogUtil.info(LOGGER,"领取优惠券结果={}",dto.toJsonString());
        }
        //返回验证码
        response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    }


    
    /**
     * 
     * 端午活动 直接灵活 活动组 礼包
     *
     * @author yd
     * @created 2017年5月3日 下午9:24:16
     *
     * @param dto
     * @param mobile
     * @param code
     * @param groupCode
     */
    private DataTransferObject getFixedGroupCoupon(DataTransferObject dto,String mobile,String code,String groupCode){
    	//领取优惠券
        return couponService.pullCouponOnly(mobile,null,groupCode);
    }
    /**
     * 获取活动随机组码
     * @author jixd
     * @created 2017年04月01日 09:48:48
     * @param
     * @return
     */
    public String randomCode(String groupCode){
        String randomCode = null;
        List<String> list = sentinelJedisClient.lrange(groupCode, 0, 100);
        if (Check.NuNCollection(list)){
            //如果redis数据为空 则放一份进去
            ActivityCodeDicEnum codeDicEnum = ActivityCodeDicEnum.getByCode(groupCode);
            if (Check.NuNObj(codeDicEnum)){
                return randomCode;
            }
            Map<String, Integer> actRateMap = codeDicEnum.getActRateMap();
            List<String> allCode = new ArrayList<>();
            for (String key: actRateMap.keySet()){
                for(int i = 0;i<actRateMap.get(key);i++){
                    allCode.add(key);
                }
            }
            allCode = ActHelperUtils.getRandomList(allCode);
            String[] arr = new String[allCode.size()];
            LogUtil.info(LOGGER,"redis中没有数据，放进去一份list={},size={}",JsonEntityTransform.Object2Json(allCode),allCode.size());
            sentinelJedisClient.lpush(groupCode, allCode.toArray(arr));
        }
        LogUtil.info(LOGGER,"redis获取活动随机列表list={},size={}",JsonEntityTransform.Object2Json(list),list.size());
        randomCode = sentinelJedisClient.lpop(groupCode);
        if (!Check.NuNStr(randomCode)){
            sentinelJedisClient.rpush(groupCode,randomCode);
        }
        LogUtil.info(LOGGER,"获取随机码randomCode={}",randomCode);
        return randomCode;
    }



    /**
     * 获取当前的概率
     * @param chance
     * @return
     */
    public static boolean checkRandomOk(int chance,int base){
        if (base < 1){
            return false;
        }
        int x=(int)(Math.random()*base);
        if (x < chance){
            return true;
        }
        return false;
    }
    
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
	 * @author busj
	 * @created 2017年5月4日 下午8:22:20
	 *
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("bindUserGroupCoupon")
	@ResponseBody
	public void bindUserGroupCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String groupSn = request.getParameter("groupSn");
		String uid = request.getParameter("uid");
		String actSn = request.getParameter("actSn");
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");

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
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			
			redisOperations.setex(key.toString(), RedisKeyConst.GROUP_ACTSN_UID_TIME, groupSn+uid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【海燕计划活动礼包领取失败】groupSn={},actSn={},uid={},redis错误,e:{}",groupSn,actSn,uid, e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
		

		try {
			if(Check.NuNStr(groupSn)
					||Check.NuNStr(uid)){
				dto.setErrCode(CouponConst.COUPON_PAR_ERROR.getCode());
				dto.setMsg(CouponConst.COUPON_PAR_ERROR.getName());
				LogUtil.info(LOGGER,"【bindUserConpuns 参数错误】groupSn={},uid={}",groupSn,uid);
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			
			dto = this.couponService.pullCouponByUid(uid, null, groupSn);
			
			try {
				redisOperations.del(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "【海燕计划活动礼包领取失败】groupSn={},actSn={},uid={},redis清楚错误,e:{}",groupSn,actSn,uid, e);
			}
			
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
		} catch (IOException e) {
			LogUtil.error(LOGGER, "【uid邦定优惠卷礼包异常】groupSn={},uid={},e={}", groupSn,uid,e);
			dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
			dto.setMsg(CouponConst.COUPON_ERROR.getName());
			response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			return;
		}
	}

    /**
     * 邀请好友活动-接受邀请
     * @author yanb
     * @created 2017年12月04日 17:37:17
     */
    @RequestMapping("acceptInvitation")
    @ResponseBody
    public void acceptInvitation(HttpServletRequest request, String uid, String inviteUid, String inviteCode, String phone, HttpServletResponse response) throws IOException {
        DataTransferObject dto = new DataTransferObject();
        response.setContentType("text/plain");
        String callBackName = request.getParameter("callback");
        try {
            LogUtil.info(LOGGER, "邀请好友活动-接受邀请[acceptInvitation]参数:uid:{},inviteUid:{},inviteCode:{}", uid,inviteUid,inviteCode);
            if(Check.NuNStr(uid)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("用户uid为空");
                response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
                return ;
            }
            /** //注释掉因为现在不传inviteUid为空也一样能灌券
            if(Check.NuNStr(inviteUid)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("邀请人Uid为空");
                response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
                return ;
            }
             */
            if(Check.NuNStr(inviteCode)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("邀请码为空");
                response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
                return ;
            }
			/*
			 *根据被邀请人的uid校验用户的当前邀请状态
			 *校验方法, 结果 0.VALID可以灌券 1.HAVE_RECEVIED已经参加过活动的 2.INVITER是邀请人自己
			 */
            Integer userInviteState = null;
            if (uid.equals(inviteUid)) {
                //校验结果是2时返回
                userInviteState = UserInviteStatesEnum.INVITER.getCode();
                dto.putValue("inviteResult",userInviteState);
                dto.setMsg("不能接受自己的邀请哦，分享给好友吧！");
                response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
                LogUtil.info(LOGGER,"邀请好友活动-接受邀请[acceptInvitation] 用户是自己邀请自己 uid:{}",uid);
                return;
            }

            InviteStateUidRequest inviteStateUidRequest = new InviteStateUidRequest();
            inviteStateUidRequest.setUid(uid);
            //新邀请好友下单活动的inviteSource为1
            inviteStateUidRequest.setInviteSource(InviteSourceEnum.NEW_INVITE.getCode());
            inviteStateUidRequest.setInviteStatus(InviteStatusEnum.SEND_OTHER.getCode());
            String userInviteStateJson = activityService.checkUserInviteStateByUid(JsonEntityTransform.Object2Json(inviteStateUidRequest));
            userInviteState = SOAResParseUtil.getIntFromDataByKey(userInviteStateJson, "userInviteState");
            //校验结果为1是返回
            if (userInviteState.equals(UserInviteStatesEnum.HAVE_RECEVIED.getCode())) {
                String yaoQingRenUid = SOAResParseUtil.getStrFromDataByKey(userInviteStateJson, "inviteUid");
                //查询邀请人的昵称
                StringBuffer url = new StringBuffer();
                url.append(CUSTOMER_DETAIL_URL).append(yaoQingRenUid);
                String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
                LogUtil.info(LOGGER, "用户已经接受过邀请,调用接口：{}，返回用户信息：{}", url.toString(), getResult);
                if (Check.NuNStrStrict(getResult)) {
                    LogUtil.error(LOGGER, "CUSTOMER_ERROR:根据用户uid={},获取用户信息失败", uid);
                }
                Map<String, String> resultMap = new HashMap<String, String>();
                try {
                    resultMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
                } catch (Exception e) {
                    LogUtil.info(LOGGER, "用户信息转化错误，请求url={}，返回结果result={}，e={}", url.toString(), getResult, e);
                }
                Object code = resultMap.get("error_code");
                if (Check.NuNObj(code)) {
                    LogUtil.error(LOGGER, "【获取用户头像】获取用户头像错误code={}，请求url={}，返回结果result={}", code, url.toString(), getResult);
                }
                Map<String, String>  dataMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(resultMap.get("data")));
                String nickName = "自小如";
                if(!Check.NuNObj(dataMap) && !Check.NuNStr(dataMap.get("nick_name"))){
                	nickName = dataMap.get("nick_name");
                }
                dto.setMsg("您已接受过" + nickName + "的邀请，不可重复参与哦！");
                response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
                LogUtil.info(LOGGER,"邀请好友活动-接受邀请[acceptInvitation] 用户已经接受过邀请了 结果dto:{}",dto.toJsonString());
                return;
            }


            //校验结果是0,调用灌券接口, 返回0代表灌券成功
            if (userInviteState.equals(UserInviteStatesEnum.VALID.getCode())) {
                //初始化invite信息插入信息

                /**
                 * 新版邀请好友下单的groupSn
                 */
                String groupSn = InviterCreateOrderConst.beInviterGroupSn;
                /**进行灌券操作并且更新invite信息的邀请状态*/

                InviteCouponRequest inviteCoupon = new InviteCouponRequest();
                inviteCoupon.setInviteUid(inviteUid);
                inviteCoupon.setInviteSource(InviteSourceEnum.NEW_INVITE.getCode());
                inviteCoupon.setInviteCode(inviteCode);
                inviteCoupon.setUid(uid);
                inviteCoupon.setGroupSn(groupSn);

                String resultJson = mobileCouponService.acceptPullCouponByUid(JsonEntityTransform.Object2Json(inviteCoupon));
                LogUtil.info(LOGGER, "邀请好友活动-接受邀请[acceptInvitation]灌券返回结果:{}",resultJson);
                dto = JsonEntityTransform.json2DataTransferObject(resultJson);
                /**
                 * 优惠券的总价值
                 * 要把couponAmount做成可配置的
                 * 不需要了,改成从CMS中取值了
                 */
                //Integer couponAmount = 500;
                if (dto.getCode() == DataTransferObject.SUCCESS) {
                	//被邀请人接收邀请成功， 给双发都发送短信
                	String inviterUid = ValueUtil.getStrValue(dto.getData().get("inviteUid"));
                	sendMsg4InviteeAndInviter(uid, inviterUid,phone);
                	
                    dto.getData().remove("listActCoupon");
                    dto.putValue("inviteResult", userInviteState);
                  //  dto.putValue("couponAmount", couponAmount);
                    response.getWriter().write(callBackName + "(" + dto.toJsonString() + ")");
                    LogUtil.info(LOGGER, "邀请好友活动-接受邀请[acceptInvitation]灌券成功:{}",dto.toJsonString());
                    return;
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER,"e:{}",e);
            dto.setErrCode(CouponConst.COUPON_ERROR.getCode());
            dto.setMsg(CouponConst.COUPON_ERROR.getName());
            response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
            return;
        }

    }
    
    /**
     * 
     * 给邀请人和被邀请人发送短信
     *
     * @author loushuai
     * @created 2017年12月18日 上午11:57:18
     *
     * @param inviteeUid
     * @param inviterUid
     */
    @RequestMapping("sendMsg4InviteeAndInviter")
    @ResponseBody
	private void sendMsg4InviteeAndInviter(String inviteeUid, String inviterUid,String inviteePhone){
		LogUtil.info(LOGGER, "邀请人接受邀请，给邀请人和被邀请人发送短信入参，inviteeUid={},inviterUid={},inviteeNickname={},inviteePhone={}", inviteeUid,inviterUid,inviteePhone);
		try {
			// 被邀请人信息
			Map<String, String> inviteeMap = getNicknameAndHeadpicByUid(inviteeUid);
			if(Check.NuNStr(inviteePhone)){
				if(Check.NuNObj(inviteeMap) || Check.NuNStr(inviteeMap.get("mobile"))){
					return;
				}
				inviteePhone = inviteeMap.get("mobile");
			}
			String inviteeNickname = "自小如";
			if(!Check.NuNObj(inviteeMap) && !Check.NuNStr(inviteeMap.get("nick_name"))){
				 inviteeNickname = inviteeMap.get("nick_name");
				 LogUtil.info(LOGGER, "inviteeNickname={}", inviteeNickname);
            }

			// 邀请人信息
			Map<String, String> inviterMap = getNicknameAndHeadpicByUid(inviterUid);
			if(Check.NuNObj(inviterMap) || Check.NuNStr(inviterMap.get("mobile"))){
				return;
			}

			//给被邀请人送短信
			SmsRequest smsToInviteeRequest = new SmsRequest();
			Map<String, String> paramsToInviteeMap = new HashMap<>();
			paramsToInviteeMap.put("{1}", INVITEE_CAN_WIN_COUPON);
			paramsToInviteeMap.put("{2}", JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN);
			smsToInviteeRequest.setParamsMap(paramsToInviteeMap);
			smsToInviteeRequest.setMobile(inviteePhone);
			smsToInviteeRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.INVITEE_ACCEPT_SMS_TO_HESELF.getCode()));
			LogUtil.info(LOGGER, "sendMsg4InviteeAndInviter方法  给被邀请人发送短信参数， smsToInviteeRequest={} ", JsonEntityTransform.Object2Json(smsToInviteeRequest));
			smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsToInviteeRequest));
			
			//给邀请人送短信
			SmsRequest smsToInviterRequest = new SmsRequest();
			Map<String, String> paramsToInviterMap = new HashMap<>();
			paramsToInviterMap.put("{1}", inviteeNickname);
			paramsToInviterMap.put("{2}", JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN);
			smsToInviterRequest.setParamsMap(paramsToInviterMap);
			smsToInviterRequest.setMobile(inviterMap.get("mobile"));
			smsToInviterRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.INVITEE_ACCEPT_SMS_TO_INVITER.getCode()));
			LogUtil.info(LOGGER, "sendMsg4InviteeAndInviter方法  给邀请人发送短信参数，  smsToInviterRequest={}  ", JsonEntityTransform.Object2Json(smsToInviterRequest));
			smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsToInviterRequest));
		}catch (Exception e){
			LogUtil.error(LOGGER,"邀请下单活动，给邀请人和被邀请人发送短信失败：e：{}",e);
		}
	}

	/**
	 * 
	 * uid获取用户头像和昵称
	 *
	 * @author bushujie
	 * @created 2017年8月29日 下午6:50:02
	 *
	 * @return
	 * @throws IOException 
	 * @throws SOAParseException 
	 */
	public Map<String, String> getNicknameAndHeadpicByUid(String uid) throws IOException, SOAParseException{
		//查询自如客头像和昵称
		StringBuffer url = new StringBuffer();
		url.append(CUSTOMER_DETAIL_URL).append(uid);
		String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
		LogUtil.info(LOGGER, "调用接口：{}，返回用户信息：{}",url.toString(),getResult);
		if(Check.NuNStrStrict(getResult)){
			LogUtil.error(LOGGER, "CUSTOMER_ERROR:根据用户uid={},获取用户信息失败", uid);
		}
		Map<String, String> resultMap=new HashMap<String, String>();
		try {
			resultMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
		} catch (Exception e) {
			LogUtil.info(LOGGER, "用户信息转化错误，请求url={}，返回结果result={}，e={}",url.toString(),getResult,e);
		}
		Object code = resultMap.get("error_code");
		if(Check.NuNObj(code)){
			LogUtil.error(LOGGER, "【获取用户头像】获取用户头像错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
		}
		Map<String, String>  dataMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(resultMap.get("data")));
	    return dataMap;
	    		
	}
	
	@Resource(name = "cms.shortChainMapService")
	private ShortChainMapService shortChainMapService;
	
	@RequestMapping("getMinsuHomeJump")
    @ResponseBody
	public String getMinsuHomeJump() throws IOException, SOAParseException{
		String shortLink = null;
		String result = shortChainMapService.getMinsuHomeJump();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
		if(resultDto.getCode()==DataTransferObject.SUCCESS){
		     shortLink = resultDto.parseData("shortLink", new TypeReference<String>() {});
			//SOAResParseUtil.getValueFromDataByKey(result, "shortLink", String.class);
		}
	    return 	shortLink;	
	}

}
