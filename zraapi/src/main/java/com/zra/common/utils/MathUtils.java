package com.zra.common.utils;

import java.math.BigDecimal;

public class MathUtils {
	
	//默认除法运算精度  
	private static final int DEF_DIV_SCALE = 2;  
	  
	/** 
	* 提供精确的加法运算。 
	* @param v1 被加数 
	* @param v2 加数 
	* @return 两个参数的和 
	*/  
	  
	public static BigDecimal add(String v1, String v2) {  
		if (v1 == null) {
			v1 = "0";
		}
		if (v2 == null) {
			v2 = "0";
		}
	    BigDecimal b1 = new BigDecimal(v1);  
	    BigDecimal b2 = new BigDecimal(v2);  
	    return b1.add(b2); 
	}  
	
	public static BigDecimal add(int v1, int v2) {
		return add(Integer.toString(v1), Integer.toString(v2));
	}
	
	public static Integer add(Integer v1, Integer v2) {
		if (v1 == null) {
			v1 = 0;
		}
		if (v2 == null) {
			v2 = 0;
		}
		return v1.intValue() + v2.intValue();
	}
	  
	/** 
	* 提供精确的减法运算。 
	* @param v1 被减数 
	* @param v2 减数 
	* @return 两个参数的差 
	*/  
	  
	public static BigDecimal sub(String v1, String v2) {  
		if (v1 == null) {
			v1 = "0";
		}
		if (v2 == null) {
			v2 = "0";
		}
	    BigDecimal b1 = new BigDecimal(v1);  
	    BigDecimal b2 = new BigDecimal(v2);  
	    return b1.subtract(b2);
	}  
	
	public static BigDecimal sub(int v1, int v2) {
		return sub(Integer.toString(v1), Integer.toString(v2));
	}
	
	public static Integer sub(Integer v1, Integer v2) {
		if (v1 == null) {
			v1 = 0;
		}
		if (v2 == null) {
			v2 = 0;
		}
		return v1.intValue() - v2.intValue();
	}
	  
	/** 
	* 提供精确的乘法运算。 
	* @param v1 被乘数 
	* @param v2 乘数 
	* @return 两个参数的积 
	*/  
	public static BigDecimal mul(String v1, String v2) {  
		if (v1 == null) {
			v1 = "0";
		}
		if (v2 == null) {
			v2 = "0";
		}
	    BigDecimal b1 = new BigDecimal(v1);  
	    BigDecimal b2 = new BigDecimal(v2);  
	    return b1.multiply(b2);  
	}  
	  
	/** 
	* 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
	* 小数点以后10位，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @return 两个参数的商 
	*/  
	  
	public static BigDecimal div(String v1, String v2) {  
	   return div(v1, v2, DEF_DIV_SCALE);  
	}  
	  
	/** 
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
	* 定精度，以后的数字四舍五入。 
	* @param v1 被除数 
	* @param v2 除数 
	* @param scale 表示表示需要精确到小数点以后几位。 
	* @return 两个参数的商 
	*/  
	public static BigDecimal div(String v1, String v2, int scale) {  
	    if (scale < 0) {  
	     throw new IllegalArgumentException(  
	       "The scale must be a positive integer or zero");  
	    }  
	    if (v1 == null) {
			v1 = "0";
		}
		if (v2 == null) {
			throw new IllegalArgumentException(  
				      "The denominator cannot be null.");  
		}
		if (Integer.parseInt(v2) == 0) {
			return new BigDecimal(0).setScale(scale);
		}
	    BigDecimal b1 = new BigDecimal(v1);  
	    BigDecimal b2 = new BigDecimal(v2);  
	    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);  
	}
	
	public static BigDecimal div(int v1, int v2, int scale) {
		return div(Integer.toString(v1), Integer.toString(v2), scale);
	}
	
	public static BigDecimal div(Integer v1, Integer v2, int scale) {
		if (v1 == null) {
			v1 = 0;
		}
		if (v2 == null) {
			v2 = 0;
		}
		return div(v1.toString(), v2.toString(), scale);
	}
}
