package com.ziroom.minsu.services.common.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import com.asura.framework.base.util.Check;

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
public class randomUtil {

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
	 * 
	 * 获取随机字符串或者数字
	 *
	 * @author yd
	 * @created 2016年5月9日 下午11:15:54
	 *
	 * @param length
	 * @param type
	 * @return
	 */
	public static String getNumrOrChar(int length,String type) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if(!Check.NuNStr(type)){
				charOrNum = type;
			}
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
	 * 获取四位不重复的随机数字
	 *
	 * @author yd
	 * @created 2016年5月18日 上午1:56:31
	 *
	 * @return
	 */
	public static String getFourNum(){
		Random ran=new Random();
		int random=0;
		//获取四位不重复的随机数字
		m:while(true){
			int n=ran.nextInt(10000);
			random=n;
			int[] bs=new int[4];
			for(int i=0;i<bs.length;i++){
				bs[i]=n%10;
				n/=10;
			}
			Arrays.sort(bs);
			for(int i=1;i<bs.length;i++){
				if(bs[i-1]==bs[i]){
					continue m;
				}
			}
			break;
		}
		
		return  ""+random;
	}
}
