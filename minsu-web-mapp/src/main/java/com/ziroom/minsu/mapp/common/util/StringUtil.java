package com.ziroom.minsu.mapp.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import com.alibaba.fastjson.util.Base64;
/**
 * 
 * <p>字符串工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class StringUtil {
	/**
	 * 对字符串进行sha1加密操作
	 * @param strSrc
	 * @return
	 */
	public static String encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = StringUtil.bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
		return strDes;

	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";

			}
			des += tmp;
		}
		return des;
	}
	/**
	 * 创建随机字符串 
	 * @param length
	 * @return
	 */
	public static String createNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			sb.append(chars.charAt(rd.nextInt(chars.length()-1)));
		}
		return sb.toString();
	}
	
	
	/**
	 * 获取签名
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getSign(String[] arr){
		Arrays.sort(arr);
		StringBuffer sb = new StringBuffer();
		for(String str : arr){
			sb.append(str);
		}
		return encrypt(sb.toString()).toUpperCase();
	}
	
	/**
	 * 判断字符串是否为空
	 *
	 * @param s
	 * @return 是否为空
	 */
	public static boolean isNullStr(String s) {
		if (s == null || s.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将Object对象转变为String类型对象，对于null值返回空字符串.
	 * 
	 * @param inObj
	 *            待处理的对象.
	 */
	public static String killNull(Object inObj) {
		if (inObj == null) {
			return "";
		}
		return inObj.toString().trim();
	}


	/**
	 * 返回32位唯一编码
	 * 
	 */
	public static String getUUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}


	/**
	 * 将指定字符串转成指定格式
	 * 
	 * @param str
	 * @return
	 */
	public static String convertStr(String str, String format) {
		try {
			return new String(str.getBytes(), format);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		String[] str = new String[]{"sssss","222222222"};
			String qq = "uid";
			System.out.println();
			try {
				String w = new String(org.apache.commons.codec.binary.Base64.encodeBase64(qq.getBytes()),"utf-8");
				System.out.println(w);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
