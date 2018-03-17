package com.ziroom.minsu.activity.constant;


/**
 * <p>TODO</p>
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
public enum  SmsTemplateEnum {

    CHRISTMAS_APPLY_SUCCESS("christmas_apply_success", "恭喜您成功申请了【{activityDate}】+【{houseName}】的奇妙一夜，自如民宿将于{awardDate}在自如民宿官方微博公布中奖名单并短信通知，别忘了在活动页面还可以领取150元优惠券哟，更多100%惊喜在等你。【http://h.ziroom.com/YNnmya】"),
    CHRISTMAS_SEND_COUPON("ZRSDKN", "恭喜您领到了自如民宿150元优惠券，有效入住日期为2016年12月21日~2017年1月20日，预订入住时间为圣诞跨年奇妙夜期间的房源还可百分百获得入住惊喜哟，快打开自如APP预订淳美民居吧！【http://t.cn/RqOAHxW】"),
    CHUNJIE17("CJPFYH", "恭喜你获得100元春节房卡！自如民宿全国25城超过6000套美宿召唤你开启春节焕心之旅咯！下载自如APP http://t.cn/RqOAHxW，注册或登陆后即可在个人中心优惠券中查看哦！截止2017年2月28日前预订任意民宿订单满500元即可减免相应房费。如有问题可在APP内咨询在线客服。"),
    CJHD17("CJHD17", "恭喜你找到了所有的春节锦囊！ 解锁了{money}元自如民宿春节旅行基金！预订入住日期为2017年1月21日~2月11日的自如民宿即可无门槛减免房费，全平台房源可用。现在就下载自如APP http://t.cn/RqOAHxW，开启春节焕心之旅吧！如有问题可在APP内咨询在线客服。"),
    SHUANGZHOU("SHUANGZHOU", "恭喜！您的{money}元自如民宿旅行基金已放入个人中心，还在等什么，快去自如APP http://t.cn/RqOAHxW，自如民宿界面预订美宿吧！"),
    QMQM01("QMQM01", "十里花开，春风得意！恭喜你获得{money}元自如民宿踏青基金。下载自如APP：http://t.cn/RqOAHxW，预订一间赏花美宿开启你的春日之旅吧！"),
    //五一多动短信文案
    WUYI500("WUYI500", "哇塞，恭喜你获得了500元自如民宿年中奖！优惠券使用有效期截止2017年6月30日，全平台美宿可用，不限入住时间哦。快下载自如APP：http://t.cn/RqOAHxW，五一假期到来前，为自己预订一间民宿，酝酿一场旅行吧！"),
    WUYI800("WUYI800", "哇塞，恭喜你获得了800元自如民宿加薪奖！优惠券使用有效期截止2017年6月30日，全平台美宿可用，不限入住时间哦。快下载自如APP：http://t.cn/RqOAHxW，五一假期到来前，为自己预订一间民宿，酝酿一场旅行吧！"),
    WUYI1000("WUYI1000", "哇塞，恭喜你获得了1000元自如民宿升职奖！优惠券使用有效期截止2017年6月30日，全平台美宿可用，不限入住时间哦。快下载自如APP：http://t.cn/RqOAHxW，五一假期到来前，为自己预订一间民宿，酝酿一场旅行吧！"),
    DUANWU2017("DUANWU2017", "恭喜你获得500元自如民宿放粽基金！优惠券使用有效期至2017年6月30日。下载自如APP：http://t.cn/RqOAHxW，端午佳节一起乘着龙舟去旅行吧！"),
    SHUANGZHOU02("SHUANGZHOU02", "恭喜你获得500元自如民宿旅行基金！优惠券使用有效期180天，还可与50元新人首单立减叠加使用，折上折哦！下载自如APP：http://t.cn/RqOAHxW，立即使用吧！"),
    G2017GUOQING("2017GUOQING", "您已成功申请自如民宿旅游局签发的Z Passport，并获得了500元自如民宿通用优惠券，现在就来自如APP：http://t.cn/RqOAHxW ，挑选一间美宿成为自如民宿星球居民吧！");


    SmsTemplateEnum(String code, String name){
        this.code = code;
        this.name = name;
    }
    /** code */
    private String code;

    /** 名称 */
    private String name;

    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }


    public static SmsTemplateEnum getByCode(String msgCode) {
        for (final SmsTemplateEnum code : SmsTemplateEnum.values()) {
            if (code.getCode().equals(msgCode)) {
                return code;
            }
        }
        return null;
    }
}
