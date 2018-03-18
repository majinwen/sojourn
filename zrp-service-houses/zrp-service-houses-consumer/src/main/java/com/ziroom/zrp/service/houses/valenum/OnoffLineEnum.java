package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>门锁在线状态：1：在线2：离线</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月22日 15:21
 * @since 1.0
 */
public enum OnoffLineEnum {

    ON_LINE(1,"在线"),
    OFF_LINE(2,"离线");

    private  int code;

    private  String val;

    OnoffLineEnum(int code,String val){
        this.code = code;
        this.val = val;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


    public  static  OnoffLineEnum getOnoffLineEnumByCode(int code){

        for (OnoffLineEnum onoffLineEnum:OnoffLineEnum.values()){
            if(onoffLineEnum.getCode() == code){
                return  onoffLineEnum;
            }
        }

        return  null;
    }
}
