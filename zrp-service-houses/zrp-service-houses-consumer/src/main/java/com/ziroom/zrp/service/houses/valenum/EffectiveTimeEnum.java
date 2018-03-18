package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>智能锁有效时间枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 21:22
 * @since 1.0
 */
public enum EffectiveTimeEnum {

    HALF_HOUR(1,0.5,30*60*1000,"半小时"),
    ONE_HOUR(2,1D,60*60*1000,"1小时"),
    TWO_HOUR(3,2D,2*60*60*1000,"2小时"),
    TWENTY_FOUR_HOUR(4,24D,24*60*60*1000,"24小时"),
    FORTY_EIGHT_HOUR(5,48D,48*60*60*1000,"48小时");


     EffectiveTimeEnum(int code,Double time,int milliSecond,String desc){

        this.code = code;
        this.time = time;
        this.milliSecond = milliSecond;
        this.desc = desc;
    }

    private int code;

    private Double time;

    private int milliSecond;

    private String desc;


    public int getMilliSecond() {
        return milliSecond;
    }

    public void setMilliSecond(int milliSecond) {
        this.milliSecond = milliSecond;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public  static  EffectiveTimeEnum getEffectiveTimeEnumByCode(int code){
        for (EffectiveTimeEnum effectiveTimeEnum:EffectiveTimeEnum.values()
             ) {
            if (code ==effectiveTimeEnum.getCode() ){
                return  effectiveTimeEnum;
            }
        }

        return  null;
    }
}
