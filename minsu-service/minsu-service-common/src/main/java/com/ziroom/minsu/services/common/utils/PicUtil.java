/**
 * @FileName: PicUtil.java
 * @Package com.ziroom.minsu.api.common.util
 * 
 * @author jixd
 * @created 2016年5月14日 上午10:54:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;

/**
 * <p>图片url工具类</p>
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
public class PicUtil {


    public static  final  String picLastSuffix = ".jpg";


	public static  final  String picSplit = "/";


	
	/**
	 * 
	 * 获取特定图片格式url
	 *
	 * @author jixd
	 * @created 2016年5月14日 上午10:56:22
	 *
	 * @param picBaseAddrMona
	 * @param picUrl
	 * @param picSize
	 * @return
	 */
	public static String getSpecialPic(String picBaseAddrMona,String picUrl,String picSize){
		if(Check.NuNStr(picUrl)){
			return "";
		}
//		String suffix = picUrl.substring(picUrl.indexOf("."));
		StringBuffer sb= new StringBuffer(picBaseAddrMona);
		sb.append(picUrl);
		sb.append(picSize);
		sb.append(picLastSuffix);
		return sb.toString();
	}
	
	/**
	 * 
	 * 
	 *
	 * @author jixd
	 * @created 2016年5月14日 上午10:56:22
	 *
	 * @param picBaseAddrMona
	 * @param picUrl
	 * @param picSize
	 * @return
	 */
	public static String getFullPic(String picBaseAddrMona,String basePicUrl,String suffix,String picSize){
		
		if(Check.NuNStr(basePicUrl)){
			return "";
		}
		
		if(basePicUrl.startsWith("http:") || basePicUrl.startsWith("https:")){
            return basePicUrl;
		}
		
		if(Check.NuNStr(suffix)){
			return "";
		}
		
		StringBuffer sb= new StringBuffer(picBaseAddrMona);
		sb.append(basePicUrl);
		sb.append(suffix);
		sb.append(picSize);
		sb.append(picLastSuffix);
		return sb.toString();
	}
	
	/**
	 * 
	 * 
	 *
	 * @author yd
	 * @created 2016年5月14日 上午10:56:22
	 *
	 * @param picBaseAddrMona
	 * @param picUrl
	 * @param picSize
	 * @return
	 */
	public static String getFullPic(String picBaseAddrMona,String basePicUrl,String picSize){
		if(Check.NuNStr(basePicUrl)){
			return "";
		}
		if(basePicUrl.startsWith("http:") || basePicUrl.startsWith("https:")){
            return basePicUrl;
		}
		StringBuffer sb= new StringBuffer(picBaseAddrMona);
		sb.append(basePicUrl);
		sb.append(picSize);
		sb.append(picLastSuffix);
		return sb.toString();
	}


	/**
	 * @author afi
	 * @param picBaseAddr
	 * @param basePicUrl
	 * @param suffix
	 * @return
	 */
	public static String getOrgPic(String picBaseAddr,String basePicUrl,String suffix){
		if(Check.NuNStr(basePicUrl)||Check.NuNStr(suffix)){
			return "";
		}
		if(basePicUrl.startsWith("http:") || basePicUrl.startsWith("https:")){
			return basePicUrl;
		}
		StringBuffer sb= new StringBuffer(picBaseAddr);
		sb.append(basePicUrl);
		if (!ValueUtil.getStrValue(picBaseAddr).endsWith(picSplit)){
			sb.append(picSplit);
		}
		sb.append(suffix);
		return sb.toString();
	}
	

	/**
	 * @author afi
	 * @param picBaseAddr
	 * @param basePicUrl
	 * @param suffix
	 * @return
	 */
	public static String getOrgPic(String picBaseAddr,String basePicUrl){
		if(Check.NuNStr(basePicUrl)){
			return "";
		}
		if(basePicUrl.startsWith("http:") || basePicUrl.startsWith("https:")){
			return basePicUrl;
		}
		StringBuffer sb= new StringBuffer(picBaseAddr);
		sb.append(basePicUrl);
		if (!ValueUtil.getStrValue(picBaseAddr).endsWith(picSplit)){
			sb.append(picSplit);
		}
		return sb.toString();
	}
	/**
	 * 
	 * 获取图片访问基础地址
	 *
	 * @author liujun
	 * @created 2016年8月2日
	 *
	 * @param fullPicUrl http://img1.ziroomstatic.com/minsu/g2/M00/00/DC/ChAFFVee3jmAM3kcAA-k08IpPfg458.jpg_Z_720_480.jpg
	 * @param picBaseAddrMona http://img1.ziroomstatic.com/minsu/
	 * @return basePicUrl g2/M00/00/DC/ChAFFVee3jmAM3kcAA-k08IpPfg458.jpg
	 */
	public static String getBasePic(String fullPicUrl, String picBaseAddrMona){
		String basePicUrl = "";
		if (Check.NuNStr(fullPicUrl) || Check.NuNStr(picBaseAddrMona)) {
			return basePicUrl;
		}
		
		if(fullPicUrl.startsWith(picBaseAddrMona)){
			basePicUrl = fullPicUrl.substring(picBaseAddrMona.length());
		}
		
		int off = basePicUrl.indexOf("_");
		if(off > -1){
			basePicUrl = basePicUrl.substring(0, off);
		}
		return basePicUrl;
	}
	
	/**
	 * 
	 * 获取图片访问基础地址
	 *
	 * @author liujun
	 * @created 2016年8月2日
	 *
	 * @param fullPicUrl http://img1.ziroomstatic.com/minsu/g2/M00/00/DC/ChAFFVee3jmAM3kcAA-k08IpPfg458.jpg_Z_720_480.jpg
	 * @param picBaseAddrMona http://img1.ziroomstatic.com/minsu/
	 * @param picSize _Z_720_480
	 * @return basePicUrl g2/M00/00/DC/ChAFFVee3jmAM3kcAA-k08IpPfg458.jpg
	 */
	public static String getBasePic(String fullPicUrl, String picBaseAddrMona, String picSize){
		String basePicUrl = "";
		if (Check.NuNStr(fullPicUrl) || Check.NuNStr(picBaseAddrMona) || Check.NuNStr(picSize)) {
			return basePicUrl;
		}
		
		if(fullPicUrl.startsWith(picBaseAddrMona)){
			basePicUrl = fullPicUrl.substring(picBaseAddrMona.length());
		}
		
		StringBuilder sb = new StringBuilder(picSize);
		sb.append(picLastSuffix);
		if(basePicUrl.endsWith(sb.toString())){
			basePicUrl = basePicUrl.substring(0, basePicUrl.lastIndexOf(sb.toString()));
		}
		
		int off = basePicUrl.indexOf("_");
		if(off > -1){
			basePicUrl = basePicUrl.substring(0, off);
		}
		return basePicUrl;
	}
	
	/**
	 * 获得不修改后缀
	 * @param picBaseAddrMona
	 * @param picUrl
	 * @param picSize
	 * @param suffix
	 * @return
	 */
	public static String getSpecialPic(String picBaseAddrMona,String picUrl,String picSize,String suffix ){
		if(Check.NuNStr(picUrl)){
			return "";
		}
//		String suffix = picUrl.substring(picUrl.indexOf("."));
		StringBuffer sb= new StringBuffer(picBaseAddrMona);
		sb.append(picUrl);
		sb.append(picSize);
		sb.append(suffix);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		getOrgPic("http://10.16.34.48:8080/", "group1/M00/02/51/ChAiMFithByAOESRAAXUD2pQnP828", picLastSuffix);
	}
}
