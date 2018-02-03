package com.ziroom.minsu.services.order.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>生产订单sn的工具 当前的重复率控制在 秒内下单的 1/千万</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/5.
 * @version 1.0
 * @since 1.0
 */
public class OrderSnUtil {


    /** 时间戳的类型 */
    private static final Map<String, ThreadLocal<SimpleDateFormat>> timestampFormatPool = new HashMap();

    private static String timestampPattern = "yyyyMMddHHmmss";

    /** 初始化map容器的锁 */
    private static final Object timestampFormatLock = new Object();

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
     * @see com.ziroom.minsu.valenum.order.OrderSourceEnum
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
     * 获取房东罚款单编号
     * @author jixd
     * @created 2017年05月10日 15:29:28
     * @param
     * @return
     */
    public static String getPenaltySn(){
        return "PL" + getOrderSn();
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
     * 获取优惠券编号
     * @author afi
     * @return
     */
    public static String getCouponSn(){
        return getChar(6);
    }


    public static void main(String[] args) {

        String aa = getCouponSn();
        System.out.println(aa);
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
//            String charOrNum = "num";
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
}
