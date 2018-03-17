package com.ziroom.minsu.mapp.activity.controller;

import static com.ziroom.minsu.valenum.finance.PaymentSourceTypeEnum.customer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.InviteService;
import com.ziroom.minsu.services.cms.dto.InviteAcceptRequest;
import com.ziroom.minsu.services.common.constant.ActivityConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>邀请好友活动</p>
 * <p>
 * <PRE>
 * <BR>	修改记录 
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/1 16:25
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/invite")
@Controller
public class InviteController {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InviteController.class);

    @Resource(name = "mapp.messageSource")
    private MessageSource messageSource;

    @Resource(name = "cms.inviteService")
    private InviteService inviteService;

    @Resource(name="basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Value("#{'${MAPP_URL}'.trim()}")
    private String MAPP_URL;

    private String shareUrlPre = "/invite/ee5f86/share?code=";
    // 已经接受过邀请页面
    private String invitedUrl = "activity/invite/invited";
    // 错误页面
    private String errorUrl = "activity/invite/error";



    /**
     * 跳转邀请页面
     *
     * @param request
     * @return
     */
    @RequestMapping("${NO_LOGIN_AUTH}/init")
    public String init(HttpServletRequest request) throws Exception{

        String initStatus="";
        String sourceType = request.getParameter("sourceType");//请求来源

        // 获取受邀人活动信息
        String inviteeCouponJson = inviteService.getInviteCouponInfo(ActivityConst.InviteConst.INVITEE_GROUP_SN);
        ActCouponEntity inviteeActCouponEntity = SOAResParseUtil.getValueFromDataByKey(inviteeCouponJson, "actCouponEntity", ActCouponEntity.class);
        if (Check.NuNObj(inviteeActCouponEntity)) {
            request.setAttribute("error", "活动已结束");
            return errorUrl;
        }
        request.setAttribute("money", inviteeActCouponEntity.getActCut() / 100);
        request.setAttribute("actLimit", inviteeActCouponEntity.getActLimit() / 100);

        // 获取邀请人人活动信息
        String inviterCouponJson = inviteService.getInviteCouponInfo(ActivityConst.InviteConst.INVITER_GROUP_SN);
        ActCouponEntity inviterActCouponEntity = SOAResParseUtil.getValueFromDataByKey(inviterCouponJson, "actCouponEntity", ActCouponEntity.class);
        if (Check.NuNObj(inviterActCouponEntity)) {
            request.setAttribute("inviterMoney", "0");
        }else{
            request.setAttribute("inviterMoney", inviterActCouponEntity.getActCut() / 100);
        }

        //默认未登陆
        initStatus = "1";
        // 判断是否登陆
        CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
        if (!Check.NuNObj(customerVo) && !Check.NuNStr(customerVo.getUid())) {
            initStatus = "2";
            // 判断是否已经接受邀请
            String inviteeJson = inviteService.getInviteByUid(customerVo.getUid(), customerVo.getShowMobile());
            InviteEntity invitee = SOAResParseUtil.getValueFromDataByKey(inviteeJson, "invite", InviteEntity.class);
            if (!Check.NuNObjs(invitee) && !Check.NuNStr(invitee.getInviteCode())) {
                //获取邀请码
                request.setAttribute("nickName",customerVo.getNickName());
                request.setAttribute("inviteCode",invitee.getInviteCode());
                request.setAttribute("shareUrl",MAPP_URL+shareUrlPre+invitee.getInviteCode());
            }else {
                request.setAttribute("error","获取邀请码异常");
                return errorUrl;
            }
        }
        if (Check.NuNStr(sourceType)){
            initStatus = "3";
        }
        try {
            request.setAttribute("sourceType", sourceType);
            request.setAttribute("initStatus", initStatus);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "服务错误：{}", e);
        }
        return "activity/invite/init";
    }


    /**
     * 跳转邀请页面
     *
     * @param request
     * @return
     */
    @RequestMapping("${NO_LOGIN_AUTH}/share")
    public String share(HttpServletRequest request) {
        try {
            String url = this.check(request);
            if(invitedUrl.equals(url)){
                return "redirect:/invite/ee5f86/invited";
            } if (!Check.NuNStr(url)) {
                return url;
            }

        } catch (Exception e) {
            LogUtil.error(LOGGER, "服务错误：{}", e);
        }
        return "activity/invite/invite";
    }

    /**
     * 接受邀请
     *
     * @param request
     * @return
     * @author lishaochuan
     */
    @RequestMapping("${LOGIN_UNAUTH}/accept")
    public String accept(HttpServletRequest request) {
        String inviteCode = request.getParameter("code");
        try {
            String url = this.check(request);
            if(invitedUrl.equals(url)){
                return "redirect:/invite/ee5f86/invited";
            } else if (!Check.NuNStr(url)) {
                return url;
            }

            CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);

            // 接受邀请
            InviteAcceptRequest acceptRequest = new InviteAcceptRequest();
            acceptRequest.setUid(customerVo.getUid());
            acceptRequest.setMobile(customerVo.getShowMobile());
            acceptRequest.setInviteCode(inviteCode);
            String acceptResponse = inviteService.accept(JsonEntityTransform.Object2Json(acceptRequest));

            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(acceptResponse);
            if (dto.getCode() != DataTransferObject.SUCCESS) {
                request.setAttribute("error", dto.getMsg());
                return errorUrl;
            }


            // 给受邀人发短信
            this.sendMsg4Invitee(customerVo, request);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "服务错误：{}", e);
        }

        return "redirect:/invite/ee5f86/go?code="+inviteCode+"&money="+request.getAttribute("money");
    }


    /**
     * 给受邀人发短信
     * @param customerVo
     * @param request
     */
    private void sendMsg4Invitee(CustomerVo customerVo, HttpServletRequest request){
        try {
            LogUtil.info(LOGGER, "给受邀人发短信开始");
            if(Check.NuNStr(customerVo.getShowMobile())) {
                LogUtil.info(LOGGER, "此邀请人未绑定手机号，不发短信，customer:{}", JsonEntityTransform.Object2Json(customer));
                return;
            }

            String name = "";
            if(!Check.NuNObj(request.getAttribute("name"))){
                name = ValueUtil.getStrValue(request.getAttribute("name"));
            }

            SmsRequest smsRequest = new SmsRequest();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("{1}", name);
            smsRequest.setParamsMap(paramsMap);
            smsRequest.setMobile(customerVo.getShowMobile());
            smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.ACTIVITY_SYRSQ.getCode()));

            LogUtil.info(LOGGER, "发送短信参数:{}", JsonEntityTransform.Object2Json(smsRequest));
            smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        }catch (Exception e){
            LogUtil.error(LOGGER,"给受邀人发送短信失败：e：{}",e);
        }
    }


    /**
     * 跳转已经邀请过页面（兼容用其浏览器打开，不校验登陆）
     * @param request
     * @return
     */
    @RequestMapping("${NO_LOGIN_AUTH}/invited")
    public String invited(HttpServletRequest request) {
        return invitedUrl;
    }

    /**
     * 跳转启程页面（兼容用其浏览器打开，不校验登陆）
     * @param request
     * @return
     */
    @RequestMapping("${NO_LOGIN_AUTH}/go")
    public String go(HttpServletRequest request) {
        try {
            // 校验邀请码
            String inviteCode = request.getParameter("code");
            if (Check.NuNStr(inviteCode)) {
                request.setAttribute("error", "邀请码不存在哦");
                return errorUrl;
            }

            // 根据邀请码获取邀请人信息
            String inviterJson = inviteService.getInviteByCode(inviteCode);
            InviteEntity inviter = SOAResParseUtil.getValueFromDataByKey(inviterJson, "inviter", InviteEntity.class);
            if (Check.NuNObj(inviter)) {
                request.setAttribute("error", "邀请码不存在哦");
                return errorUrl;
            }

            // 获取邀请人用户信息
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(inviter.getUid());
            CustomerBaseMsgEntity customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customer", CustomerBaseMsgEntity.class);
            if (!Check.NuNObj(customer) && !Check.NuNStr(customer.getNickName())) {
                request.setAttribute("name", customer.getNickName());
            }

            request.setAttribute("money", request.getParameter("money"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "服务错误：{}", e);
        }
        return "activity/invite/go";
    }

    /**
     * 校验
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String check(HttpServletRequest request) throws Exception {
        // 获取活动信息
        String couponJson = inviteService.getInviteCouponInfo(ActivityConst.InviteConst.INVITEE_GROUP_SN);
        ActCouponEntity actCouponEntity = SOAResParseUtil.getValueFromDataByKey(couponJson, "actCouponEntity", ActCouponEntity.class);
        if (Check.NuNObj(actCouponEntity)) {
            request.setAttribute("error", "活动已结束");
            return errorUrl;
        }
        request.setAttribute("money", actCouponEntity.getActCut() / 100);


        // 判断是否登陆
        CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
        if (!Check.NuNObj(customerVo) && !Check.NuNStr(customerVo.getUid())) {
            request.setAttribute("isLogin", "yes");

            // 判断是否已经接受邀请
            String inviteeJson = inviteService.getInviteByUid(customerVo.getUid(), customerVo.getShowMobile());
            InviteEntity invitee = SOAResParseUtil.getValueFromDataByKey(inviteeJson, "invite", InviteEntity.class);
            if (!Check.NuNObj(invitee) && !Check.NuNStr(invitee.getInviteUid())) {
                return invitedUrl;
            }
        } else {
            request.setAttribute("isLogin", "no");
        }

        // 校验邀请码
        String inviteCode = request.getParameter("code");
        if (Check.NuNStr(inviteCode)) {
            request.setAttribute("error", "邀请码不存在哦");
            return errorUrl;
        }
        request.setAttribute("inviteCode", inviteCode);

        // 根据邀请码获取邀请人信息
        String inviterJson = inviteService.getInviteByCode(inviteCode);
        InviteEntity inviter = SOAResParseUtil.getValueFromDataByKey(inviterJson, "inviter", InviteEntity.class);
        if (Check.NuNObj(inviter)) {
            request.setAttribute("error", "邀请码不存在哦");
            return errorUrl;
        }

        // 校验邀请人不能为自己
        if (!Check.NuNObj(customerVo) && !Check.NuNStr(customerVo.getUid()) && customerVo.getUid().equals(inviter.getUid())) {
            request.setAttribute("error", "自己不能接受自己的邀请哦！<br>赶紧邀请好友开启焕心之旅吧！");
            return errorUrl;
        }


        // 获取邀请人用户信息
        String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(inviter.getUid());
        CustomerBaseMsgEntity customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customer", CustomerBaseMsgEntity.class);
        if (!Check.NuNObj(customer) && !Check.NuNStr(customer.getNickName())) {
            request.setAttribute("name", customer.getNickName());
        }


        return null;
    }

}
