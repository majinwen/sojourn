package com.ziroom.minsu.valenum.version;

import com.asura.framework.base.util.Check;

/**
 * <p>版本号</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/23.
 * @version 1.0
 * @since 1.0
 */
public enum VersionCodeEnum {

    V_815(100000,69, "4.4.6"),
    V_830(100001,70, "4.4.7"),
    V_927(100002,71, "4.4.8"),
    V_1027(100004,76, "5.0.1"),
    V_104(100008,77, "5.0.9"),
    V_20170627(100017,78, "5.2.8"),
    V_20170822(100020,79, "5.3.3"),
    V_20170924(100022,80, "5.3.4");

    VersionCodeEnum(int code, int version, String name) {
        this.code = code;
        this.version = version;
        this.name = name;
    }


    /**
     * 校验当前版本是否支持原生
     * @author afi
     * @param code
     * @return
     */
    public static boolean checkOrg(Integer code){
        if (Check.NuNObj(code)){
            return false;
        }
        if (code < V_927.getCode()){
            return false;
        }else {
            return true;
        }
    }


    /**
     * 校验当前版本是否支持清洁费
     * @author afi
     * @param code
     * @return
     */
    public static boolean checkClean(Integer code){
        if (Check.NuNObj(code)){
            return false;
        }
        if (code < V_830.getCode()){
            return false;
        }else {
            return true;
        }
    }

    
    /**
     * 校验当前版本是否支持 首单立减
     * @author yd
     * @param code
     * @return
     */
    public static boolean checkFirstOrderCut(Integer code){
        if (Check.NuNObj(code)){
            return false;
        }
        if (code < V_20170627.getCode()){
            return false;
        }else {
            return true;
        }
    }
    /**
     * 检验当前版本是否支持长租退订
     * @param code
     * @return
     */
    public static boolean checkLongTd(Integer code){
        if (Check.NuNObj(code)){
            return false;
        }
        if (code < V_104.getCode()){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 通过code过去当前的
     * @param code
     * @return
     */
    public static VersionCodeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return null;
        }
        for(final VersionCodeEnum codeEnum : VersionCodeEnum.values()){
            if(codeEnum.getCode() == code){
                return codeEnum;
            }
        }
        return null;
    }
    
    /**
     * 
     * 判断当前版本兼容指定版本（包含指定版本或在指定版本之后）
     *
     * @author zhangyl
     * @created 2017年8月11日 下午2:06:47
     *
     * @param currentVersionCode
     * @param compatibleVersionCode
     * @return
     */
    public static boolean checkCompatibleVersion(Integer currentVersionCode, Integer compatibleVersionCode){
        if (Check.NuNObj(currentVersionCode) || Check.NuNObj(compatibleVersionCode)){
            return false;
        }
        
        return currentVersionCode.compareTo(compatibleVersionCode) >= 0;
    }




    /** code */
    private int code;

    /** 名称 */
    private String name;

    /** 路径 */
    private int version;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }
}
