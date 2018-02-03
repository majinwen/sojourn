package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public  final static String[] chineseNum = {"一","二", "三", "四","五","六","七","八","九","十","零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"};

	private static String KwRegex = "<.*?>|[_~:\\.\\?\\*\\+\\^\\$\\|\\(\\)\\{\\}\\[\\]\\\\\\&=;<>/,\"'%#!@`-]";

	private static String LgRegex = "\\b[aA][nN][dD]\\b|\b[tT][oO]\b|\\b[oO][rR]\\b";
	
	public final static String specialKey = "\\r\\n";
	
	public final static String specialKey_N = "\\n";

	/**
	 * 将查询词中的非汉字，非数字，非字母过滤掉
	 * 
	 * @param keyword
	 * @return
	 */
	public static String removeInvalidChar(String keyword) {
		return keyword.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();
	}

	/**
	 * 将查询词中的非汉字，非数字，非字母过滤掉,并不保留空格
	 * 
	 * @param keyword
	 * @return
	 */
	public static String removeInvalidCharNoSpace(String keyword) {
		return keyword.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", "").trim();
	}

	/**
	 * 将查询词中的非汉字，非数字，非字母，非*过滤掉
	 * 
	 * @param keyword
	 * @return
	 */
	public static String removeInvalidChar1(String keyword) {
		return keyword.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9\\*]", " ").replaceAll("\\s+", " ").trim();
	}

	/**
	 * 
	 * 从字符串中过滤数字  包括中文数字
	 *
	 * @author yd
	 * @created 2016年4月18日 下午10:49:47
	 *
	 * @param str
	 * @return
	 */
	public static String removeNum(String str) {
		String regEx = "[0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		//替换与模式匹配的所有字符（即数字的字符将被""替换）
		str = m.replaceAll("").trim();
		for (String st : chineseNum) {
			str = str.replaceAll(st, "").trim();
		}
		return str;
	}

	/**
	 * 
	 * 注释掉： = ; <> / + <.*> \ . , ' ` " | () {} [] % ^ #$!@ * ? & - OR TO AND
	 *
	 * @author zhangshaobin  copy by yd
	 * @created 2016年4月19日 下午4:18:41
	 *
	 * @param keyword
	 * @return
	 */
	public static String Parse(String keyword){
		// 目前原样返回
		StringBuffer keyStr = new StringBuffer();
		if (keyword != null) {
			// 注释掉： = ; <> / + <.*> \ . , ' ` " | () {} [] % ^ #$!@ * ? & - OR TO AND
			// 两个regex分开处理,以便防止*and* 导致 * 变成空格
			keyword = keyword.replaceAll(KwRegex, " ").replaceAll(LgRegex, " ").replaceAll("\\s+", " ").trim();

			keyStr.append(keyword);

			if (keyStr.length() > 50) {
				keyStr.delete(50, keyStr.length());
			}
		}
		String k = keyStr.toString(); keyStr = null;
		return k;
	}

	/**
	 * 
	 * 过滤特殊字符和数字 (包括中文数字)
	 *
	 * @author yd
	 * @created 2016年4月18日 下午11:05:50
	 *
	 * @param keyword
	 * @return
	 */
	public static String removeNumAndParse(String keyword){
		keyword = Parse(keyword);
		return removeNum(keyword);
	}

	/**
	 * 
	 * 获取带**的电话号码
	 *
	 * @author jixd
	 * @created 2016年5月19日 上午2:28:24
	 *
	 * @param phoneStr
	 * @return
	 */
	public static String getPhoneSecret(String phoneStr){
		if(Check.NuNStr(phoneStr)){
			return "";
		}
		if(phoneStr.length()<=3){
			return phoneStr;
		}
		return phoneStr.substring(0, 3)+"********";
	}

	/**
	 * 
	 * 根据身份证号计算年龄
	 *
	 * @author jixd
	 * @created 2016年5月19日 下午9:53:11
	 *
	 * @param idCard
	 * @return
	 */
	public static int getAgeByIdCard(String idCard){


		String birthday = idCard.substring(6,10);
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		return currentYear - Integer.parseInt(birthday);
	}

	/**
	 * 
	 * 格式化金额
	 *
	 * @author jixd
	 * @created 2016年5月20日 上午12:07:24
	 *
	 * @param price
	 * @return
	 */
	public static String getPriceFormat(int price) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,##0.00");

		double priceD = price / 100.00;
		return myformat.format(priceD);
	}

	/**
	 * 智能锁加密处理
	 * 1.^是异或运算符，异或运算满足交换律，结合律和自反性，交换律是指异或顺序无所谓，结合律是说(a^b)^c = a^(b^c)，自反性是指a^b^b = a。
	 * 2.也就是说如果a是明文，b是密钥，c是密文，并且有a^b=c，则一定有c^b=(a^b)^b=a
	 * 3.所以keyBytes看成密钥，密文是由明文和密钥每个字节连续异或得到，则明文可以由密文和密钥每个字节连续异或得到。这个证明转换成公式就是：
     * enc^b0^b1^...^bn = enc^(b0^b1^...^bn) = dec
     * dec^b0^b1^...^bn = dec^(b0^b1^...^bn) = enc^(b0^b1^...^bn)^(b0^b1^...^bn) = enc。
	 */
	private static final String key0 = "FECOI()*&<MNCXZPKL";  
	private static final Charset charset = Charset.forName("UTF-8");  
	private static byte[] keyBytes = key0.getBytes(charset);  

	/**
	 * 
	 * 简单的加密功能 使用异或运算
	 *
	 * @author yd
	 * @created 2016年6月24日 上午10:45:11
	 *
	 * @param enc
	 * @return
	 */
	public static String encode(String enc){  
		
		if(Check.NuNStr(enc)) return null;
		byte[] b = enc.getBytes(charset);  
		for(int i=0,size=b.length;i<size;i++){  
			for(byte keyBytes0:keyBytes){  
				b[i] = (byte) (b[i]^keyBytes0);  
			}  
		}  
		return new String(b);  
	}  

	/**
	 * 
	 * 解密
	 *
	 * @author yd
	 * @created 2016年6月24日 上午10:45:30
	 *
	 * @param dec
	 * @return
	 */
	public static String decode(String dec){  
		
		if(Check.NuNStr(dec)) return null;
		byte[] e = dec.getBytes(charset);  
		byte[] dee = e;  
		for(int i=0,size=e.length;i<size;i++){  
			for(byte keyBytes0:keyBytes){  
				e[i] = (byte) (dee[i]^keyBytes0);  
			}  
		}  
		return new String(e);  
	} 

	/**
	 * 
	 * 智能锁传参code
	 *
	 * @author jixd
	 * @created 2016年6月27日 下午3:42:58
	 *
	 * @param fid
	 * @return
	 */
	public static String getSmartLockCode(String fid){
		return "MS"+fid;
	}

	/**
	 * 
	 * 是否是数字
	 *
	 * @author yd
	 * @created 2016年6月27日 下午10:30:04
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false; 
		} 
		return true; 
	}

	/**
	 * 
	 * 是否是数字
	 *
	 * @author yd
	 * @created 2016年6月27日 下午10:30:04
	 *
	 * @param str
	 * @return
	 */
	public static boolean isHaveNumeric(String string){ 
		if (string != null && Pattern.compile("(?i)[0-9]").matcher(string).find()) { 
			return true;
		} 
		return false;
	}

	/**
	 * 
	 * 隐藏电话号
	 *
	 * @author yd
	 * @created 2016年6月27日 下午10:14:31
	 *
	 * @param string
	 * @return
	 */
	public static String hidPhone(String string) {
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[34578]\\d{9})|(?:861[34578]\\d{9}))(?!\\d)");
		Matcher matcher = pattern.matcher(string);
		while (matcher.find()) {
			string = string.replace(matcher.group(), matcher.group().substring(0,3)+"********");
		}
		return string;
	}

	/**
	 * 
	 * 是否有字母
	 *
	 * @author yd
	 * @created 2016年6月27日 下午10:25:03
	 *
	 * @param string
	 * @return
	 */
	public static boolean isHaveLetters(String string){
		if (string != null && Pattern.compile("(?i)[a-z]").matcher(string).find()) { 
			return true;
		} 
		return false;
	}
	
	/**
	 * 
	 * 过滤特殊字符串
	 *
	 * @author yd
	 * @created 2017年3月23日 上午9:47:41
	 *
	 * @param keyWord
	 * @param specialCharacter
	 * @return
	 */
	public static String filterSpecialCharacter(String keyWord,String specialKey){
		
		if(!Check.NuNStr(keyWord)
				&&!Check.NuNStr(specialKey)){
			keyWord = keyWord.replaceAll(specialKey, "");
		}
		return keyWord;
	}
	public static void main(String[] args) {
		String str = "12345s";
		//System.out.println(isNumeric(str));
		//System.out.println(hidPhone("fdsfdsfdsfdsf5sd56f4sd5f64sd56f13269874521fdsafd阳光的s15614705820"));

		System.out.println(isHaveNumeric("fsdfdsf"));
         
	}

	/**
	 * @description: 字符串和一些常用类转换
	 * @author: lusp
	 * @date: 2017/8/2 16:53
	 * @params:
	 * @return:
	 */
	public static <T extends Object> T string2Object(String str,Class<T> clazz) throws ParseException{
		if(clazz == String.class){
			return (T)str;
		}
		if(Check.NuNObj(clazz)){
			return null;
		}
		if(Integer.class == clazz){
			return (T)Integer.valueOf(str);
		}
		if(Double.class == clazz){
			return (T)Double.valueOf(str);
		}
		if(Date.class == clazz){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return (T)sdf.parse(str);
		}
		if(Float.class == clazz){
			return (T)Float.valueOf(str);
		}
		return null;
	}

}
