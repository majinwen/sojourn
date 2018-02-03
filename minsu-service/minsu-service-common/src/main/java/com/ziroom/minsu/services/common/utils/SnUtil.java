package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>生产sn的工具 当前的重复率控制在 秒内下单的 1/千万</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/5.
 * @version 1.0
 * @since 1.0
 */
public class SnUtil {


    /** 时间戳的类型 */
    private static final Map<String, ThreadLocal<SimpleDateFormat>> timestampFormatPool = new HashMap();

    private static String timestampPattern = "yyyyMMddHHmmss";

    /** 初始化map容器的锁 */
    private static final Object timestampFormatLock = new Object();

    /** 11位数字的校验 */
    private static String regCheck = "^1[0-9]{10}$";


    //线程不安全的，所以才这么做
    private static SimpleDateFormat getTimestampFormat() {
        ThreadLocal local = (ThreadLocal)timestampFormatPool.get(timestampPattern);
        if(null == local) {
            synchronized(timestampFormatLock) {
                local = (ThreadLocal)timestampFormatPool.get(timestampPattern);
                if(null == local) {
                    local = new ThreadLocal() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampPattern);
                        }
                    };
                    timestampFormatPool.put(timestampPattern, local);
                }
            }
        }

        return (SimpleDateFormat)local.get();
    }
    /**
     * 获取订单编号
     * demo：YYMMdd（6） + 随机数（8）+HHmm(6) = 20位
     * @author afi
     * @return
     */
    public static String getOrderSn(){
        StringBuffer sb = new StringBuffer();
        String dateStr = getTimestampFormat().format(new Date());
        String pre = dateStr.substring(2, 8);
        String after = dateStr.substring(8);
        sb.append(pre);
        sb.append(getCharAndNumr(8));
        sb.append(after);
        return sb.toString();
    }


    /**
     * 获取日期的中间部分
     * @return
     */
    public static String getDatePre(){
        String dateStr = getTimestampFormat().format(new Date());
        String pre = dateStr.substring(2, 8);
        return pre;
    }


    
    /**
     * 获取付款单号
     * @author lishaochuan
     * @create 2016年4月20日
     * @return
     */
    public static String getPvSn(){
    	return "FK" + getOrderSn();
    }
    
    /**
     * 获取收入单号
     * @author lishaochuan
     * @create 2016年5月11日下午9:42:17
     * @return
     */
    public static String getIncomeSn(){
    	return "SR" + getOrderSn();
    }
    
    /**
     * 获取扣款单号
     * @author lishaochuan
     * @create 2016年4月20日
     * @return
     */
    public static String getPunishSn(){
    	return "KK" + getOrderSn();
    }

    /**
     * 获取充值编号
     * @author liyingjie
     * @create 2016年5月4日
     * @return
     */
    public static String getFillSn(){
    	return "FL" + getOrderSn();
    }
    
    /**
     * 获取收款单编号
     * @author liyingjie
     * @create 2016年5月4日
     * @return
     */
    public static String getPaymentSn(){
    	return "SK" + getOrderSn();
    }

    /**
     * 
     * 获取摄影师订单编号
     *
     * @author yd
     * @created 2016年11月4日 下午3:52:10
     *
     * @return
     */
    public static String getBookOrderSn(){
    	return "PG" + getOrderSn();
    }

    /**
     * 获取优惠券编号
     * @author afi
     * @return
     */
    public static String getCouponSn(){
        return getChar(6);
    }



    public static void doTest(){
        Long a = System.currentTimeMillis();
        Set<String> set = new HashSet<>();
        for (int i=0;i<1000000L;i++){
            String sn = getOrderSn();
            set.add(sn);
        }
        Long b = System.currentTimeMillis();
        System.out.println("100万需要的时间");
        System.out.println(b-a);
        System.out.println("100万的数量" + set.size());
    }

    /**
     * 获取随机数
     * @author afi
     * @param length
     * @return
     */
    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母65:大写 97
//                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (65 + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    /**
     * 获取随机的大写字母
     * @author afi
     * @param length
     * @return
     */
    public static String getChar(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += (char) (65 + random.nextInt(26));
        }
        return val;
    }

    /**
     * 校验当前的活动码是否合法
     * @author afi
     * @param actSn
     * @return
     */
    public static boolean checkActSn(String actSn) {
        boolean checkFlag = true;
        if (ValueUtil.getStrValue(actSn).length() < 4){
            checkFlag = false;
        }
        return checkFlag;
    }

    /**
     * 校验是否邀请码
     * @param inviteSn
     * @return
     */
    public static boolean isInviteSn(String inviteSn){
        boolean flag = false;
        if (ValueUtil.getStrValue(inviteSn).length() == 8){
            flag = true;
        }
        return flag;
    }


    /**
     * 生成当前的邀请码
     * @author afi
     * @param userTel
     * @return
     */
    public static String getInviteSn(String userTel) {
        String lastStr = null;
        if (ValueUtil.getStrValue(userTel).length() == 11){
            lastStr = userTel.substring(7);
        }else {
            lastStr = getChar(4);
        }
        return getChar(4)+lastStr;
    }
    

    /**
     * 生成当前的邀请码(新的邀请下单活动--可兑换积分--2017-12-15)
     * @author afi
     * @param userTel
     * @return
     */
    public static String getInviteSnNew(String userTel) {
    	
         String phoneStr = null;
         if (ValueUtil.getStrValue(userTel).length() == 11){
        	 phoneStr = userTel.substring(7);
         }else {
        	 String dateStr = getTimestampFormat().format(new Date());
             phoneStr = dateStr.substring(10);
         }
        return getChar(3) + phoneStr;
    }

    /**
     * 校验是否邀请码(新的邀请下单活动--可兑换积分--2017-12-15)
     * @param inviteSn
     * @return
     */
    public static boolean isInviteSnNew(String inviteSn){
        boolean flag = false;
        if (ValueUtil.getStrValue(inviteSn).length() == 7){
            flag = true;
        }
        return flag;
    }



    public static void main(String[] args) {
    	String orderSn = getInviteSnNew(null);
    	boolean inviteSnNew = isInviteSnNew(orderSn);
        System.out.println(orderSn);
        System.out.println(inviteSnNew);
    }

}
